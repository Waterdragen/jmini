package org.example;

import java.io.IOException;
import java.util.Optional;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.example.cmds.CommandTable;
import org.example.clsutil.Command;

public class Main {
    public static final String[] TRIGGERS = {"!amini", "!bmini", "!cmini", "!dvormini", "!cnini"};
    public static final long CMINI_CHANNEL = 1063291226243207268L;
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static boolean isCommandRestricted(String command) {
        command = command.toLowerCase();
        Command commandClass = CommandTable.getCommandClass(command);
        return commandClass != null && commandClass.isRestricted();
    }

    public static String handleCommand(String command, String arguments) throws Throwable {
        command = command.toLowerCase();
        if (command.equals(""))
            return "Try `!cmini help`";

        Command commandClass = CommandTable.getCommandClass(command);
        if (commandClass == null)
            return "Error: " + command + " is not an available command";
        return commandClass.exec(arguments);
    }

    public static Mono<Void> createMessage(Message inMessage) {
        System.out.println("handling message: " + inMessage.getContent());
        var messageChannel = inMessage.getChannel().block();

        if (messageChannel == null)
            return Mono.empty();

        String content = inMessage.getContent();
        if (!MainUtil.isDM(inMessage))
            content = MainUtil.removeFirstWord(content);
        String[] words = MainUtil.splitFirstWordAndRest(content);
        String command = words[0];
        String arguments = words.length > 1 ? words[1] : "";

        String outMessage;
        try {
            outMessage = handleCommand(command, arguments);
            logger.info(content);
        }
        catch (Throwable e) {
            logger.error(MainUtil.getStackTrace(e));
            return Mono.empty();
        }

        if (!isCommandRestricted(command) || MainUtil.isInCminiChannel(inMessage)) {
            return MainUtil.messageInSameChannel(messageChannel, outMessage);
        }
        else {
            Optional<User> possibleUser = inMessage.getAuthor();
            if (possibleUser.isEmpty())
                return Mono.empty();
            User user = possibleUser.get();
            return MainUtil.messageInDM(user, outMessage);
        }

    }

    public static void main(String[] args) {
        final String BOT_TOKEN;
        try {
            BOT_TOKEN = MainUtil.getBotToken();
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        var client = DiscordClient.create(BOT_TOKEN);
        var bot = client.login().block();
        logger.info("Bot is now ready");

        assert bot != null;

        bot.getEventDispatcher().on(MessageCreateEvent.class)
                .filter(MainUtil::isHumanMessage)
                .map(MessageCreateEvent::getMessage)
                .filter(MainUtil::isCommand)
                .flatMap(Main::createMessage)
                .subscribe();

        bot.onDisconnect().block();
    }
}