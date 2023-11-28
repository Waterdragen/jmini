package org.example.util;

import org.example.clsutil.BoolOrStrings;
import org.example.clsutil.Kwargs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Parser {
    public static String[] asArgs(String arg) {
        return arg.split("\\s+");
    }

    public static Kwargs asKwargs(String arg, HashMap<String, BoolOrStrings> kwargTypes) {
        String[] args = asArgs(arg);
        if (args.length == 0)
            return new Kwargs("", new HashMap<>());

        int index = 0;
        while (index < args.length) {
            String word = args[index];
            if (!startsWithKeywordPrefix(word)) {
                index++;
                continue;
            }
            word = removeKeywordPrefix(word).toLowerCase();
            if (kwargTypes.containsKey(word)) {
                break;
            }
            index++;
        }

        String argString = String.join(" ", Arrays.copyOfRange(args, 0, index));
        String[] keywordArgs = Arrays.copyOfRange(args, index, args.length);

        // Make default map
        HashMap<String, BoolOrStrings> parsedKwargs = new HashMap<>();
        kwargTypes.forEach((String kwName, BoolOrStrings kwType) -> {
            if (kwType.isBool())
                parsedKwargs.put(kwName, BoolOrStrings.fromBool(false));
            else
                parsedKwargs.put(kwName, BoolOrStrings.fromStrings(new String[0]));
        });

        // Handle keybord
        ArrayList<String> tempList = new ArrayList<>();
        String prevWord = "";
        boolean inList = false;
        BoolOrStrings kwType;

        for (String word : keywordArgs) {
            word = removeKeywordPrefix(word).toLowerCase();

            if (!(kwargTypes.containsKey(word))) {
                if (inList)
                    tempList.add(word);
                continue;
            }

            kwType = kwargTypes.get(word);
            // encountered next keyword
            if (inList) {
                String[] lst = new String[tempList.size()];
                lst = tempList.toArray(lst);
                parsedKwargs.put(prevWord, BoolOrStrings.fromStrings(lst));
                inList = false;
            }
            if (kwType.isBool()) {
                parsedKwargs.put(word, BoolOrStrings.fromBool(true));
            }
            if (kwType.isStrings()) {
                inList = true;
                prevWord = word;
            }
        }

        return new Kwargs(argString, parsedKwargs);
    }

    private static boolean startsWithKeywordPrefix(String word) {
        return word.matches("^(--|—|––).+");
    }

    private static String removeKeywordPrefix(String word) {
        return word.replaceAll("^(--|—|––)", "");
    }
}
