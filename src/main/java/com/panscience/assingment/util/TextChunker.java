package com.panscience.assingment.util;

import java.util.ArrayList;
import java.util.List;

public class TextChunker {

    public static List<String> chunkText(String text) {
        List<String> chunks = new ArrayList<>();
        int chunkSize = 500;

        for (int i = 0; i < text.length(); i += chunkSize) {
            chunks.add(text.substring(i, Math.min(text.length(), i + chunkSize)));
        }

        return chunks;
    }
}