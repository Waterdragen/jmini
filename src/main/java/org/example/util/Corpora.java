package org.example.util;

import java.io.IOException;
import java.util.HashMap;

public class Corpora {
    private static final HashMap<String, Corpus> LOADED = new HashMap<>();
    private static final String[] NGRAMS = {"monograms", "bigrams", "trigrams"};
    private static final String CORPUS = "mt-quotes";

    private static class PrefCorpus extends HashMap<String, String> {}
    public static class Corpus extends HashMap<String, Integer> {}

    public static Corpus loadCorpus(String path) throws IOException {
        if (LOADED.containsKey(path))
            return LOADED.get(path);

        Corpus corpus = (Corpus) JSON.loadJSON(path, Corpus.class);
        LOADED.put(path, corpus);
        return corpus;
    }

    public static Corpus ngrams(int n, String id) throws IOException {
        String corpusName = getPrefCorpusName(id);
        String path = String.format("corpora/%s/%s.json", corpusName, NGRAMS[n - 1]);
        return loadCorpus(path);
    }

    public static Corpus words(String id) throws IOException {
        String corpusName = getPrefCorpusName(id);
        String path = String.format("corpora/%s/words.json", corpusName);
        return loadCorpus(path);
    }

    public static String getPrefCorpusName(String id) throws IOException {
        PrefCorpus prefCorpus = (PrefCorpus) JSON.loadJSON("corpora.json", PrefCorpus.class);
        return prefCorpus.getOrDefault(id, CORPUS);
    }
}
