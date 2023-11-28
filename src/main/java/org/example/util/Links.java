package org.example.util;

import org.example.clsutil.LinksJSON;

import java.io.IOException;

public class Links {
    private static final LinksJSON LINKS;

    static {
        try {
            LINKS = LinksJSON.loadLinks("links.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getLink(String layoutName) {
        String externalLink = LINKS.getOrDefault(layoutName, "");
        if (externalLink.equals(""))
            return "";
        return "<" + externalLink + ">";
    }
}
