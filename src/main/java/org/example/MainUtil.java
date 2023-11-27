package org.example;

import java.io.*;
import java.util.Arrays;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.entity.channel.PrivateChannel;
import reactor.core.publisher.Mono;

public class MainUtil {

    public static String getBotToken() throws IOException {
        var file = new File("token.txt");
        var br = new BufferedReader(new FileReader(file));
        return br.readLine();
    }

    public static boolean isInCminiChannel(Message message) {
        return message.getChannelId().asLong() == Main.CMINI_CHANNEL;
    }

    public static boolean isDM(Message message) {
        return message.getChannel().block() instanceof PrivateChannel;
    }

    public static boolean isHumanMessage(MessageCreateEvent event) {
        var message = event.getMessage();
        return !message.getAuthor().map(User::isBot).orElse(false);
    }

    public static boolean isCommand(Message message) {
        String content = message.getContent();
        for (String trigger : Main.TRIGGERS) {
            if (isDM(message) || content.matches("^" + trigger + "\\b.*"))
                return true;
        }
        return false;
    }

    public static String removeFirstWord(String s) {
        return s.replaceFirst("^\\S+", "").trim();
    }

    public static String[] splitFirstWordAndRest(String s) {
        return s.split("\\s+", 2);
    }

    public static String getStackTrace(Throwable e) {
        var sw = new StringWriter();
        var pw = new PrintWriter(sw);

        // Find the last occurrence of our package
        StackTraceElement[] stackTrace = e.getStackTrace();
        int indexOfMain = 0;
        for (int i = 0; i < stackTrace.length; i++) {
            if (!stackTrace[i].getClassName().startsWith("org.example")) {
                indexOfMain = i;
                break;
            }
        }
        // Only keep the stack trace within our package
        StackTraceElement[] relevantStackTrace = Arrays.copyOfRange(stackTrace, 0, indexOfMain);

        e.setStackTrace(relevantStackTrace);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static Mono<Void> messageInSameChannel(MessageChannel messageChannel, String outMessage) {
        return messageChannel.createMessage(outMessage).then();
    }

    public static Mono<Void> messageInDM(User user, String outMessage) {
        return user.getPrivateChannel()
                   .flatMap(privateChannel -> privateChannel.createMessage(outMessage))
                   .then();
    }
}
