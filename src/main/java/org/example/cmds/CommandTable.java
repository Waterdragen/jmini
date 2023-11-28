package org.example.cmds;

import java.util.HashMap;
import org.example.clsutil.Command;
import reactor.util.annotation.Nullable;

public class CommandTable {
    private static final HashMap<String, Command> _TABLE = initTable();

    @Nullable
    public static Command getCommandClass(String command) {
        return _TABLE.getOrDefault(command, null);
    }

    /**
     * get the commands class in `cmds` directory
     * `[command].java` must be in lower case
     * put class aliases here
     */
    private static HashMap<String, Command> initTable() {
        HashMap<String, Command> table = new HashMap<>();
        table.put("8ball", new eightBall());
        table.put("gh", new github());
        table.put("github", new github());
        return table;
    }
}
