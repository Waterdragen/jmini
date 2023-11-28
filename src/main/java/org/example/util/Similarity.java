package org.example.util;

import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import reactor.util.annotation.Nullable;

public class Similarity {
    private static final JaroWinklerSimilarity MATCHER = new JaroWinklerSimilarity();

    public static String getBestMatch(String base, String[] pool) {
        // 0: best ... 1: worst
        double closestDistance = Double.MAX_VALUE;
        String bestMatch = null;
        double currentDistance;

        for (String target : pool) {
            currentDistance = MATCHER.apply(base, target);
            if (currentDistance < closestDistance) {
                bestMatch = target;
                closestDistance = currentDistance;
            }
        }
        return bestMatch;
    }
}
