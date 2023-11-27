package org.example.cmds;

import org.example.util.Command;

public class github extends Command {
    @Override
    public boolean isRestricted() {
        return false;
    }

    @Override
    public String exec(String args) {
        return "<https://github.com/waterdragen/jmini>";
    }
}
