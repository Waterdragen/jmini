package org.example.cmds;

import org.example.clsutil.Command;

import java.util.Random;

public class eightBall extends Command {
    // randomly return one of the responses
    private static final String[] responses = {
            "Yes", "Count on it",
            "No doubt",
            "Absolutely", "Very likely",
            "Maybe", "Perhaps",
            "No", "No chance", "Unlikely",
            "Doubtful", "Probably not"
    };

    @Override
    public boolean isRestricted() {
        return false;
    }

    @Override
    public String exec(String args) {
        Random random = new Random();
        int index = random.nextInt(responses.length);
        return responses[index];
    }
}
