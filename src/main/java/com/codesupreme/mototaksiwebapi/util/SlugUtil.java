package com.codesupreme.mototaksiwebapi.util;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class SlugUtil {

    private static final Map<Character, String> charMap = new HashMap<>();

    static {
        charMap.put('ə', "e");
        charMap.put('ü', "u");
        charMap.put('ö', "o");
        charMap.put('ğ', "g");
        charMap.put('ç', "c");
        charMap.put('ş', "s");
        charMap.put('ı', "i");
        charMap.put('Ə', "e");
        charMap.put('Ü', "u");
        charMap.put('Ö', "o");
        charMap.put('Ğ', "g");
        charMap.put('Ç', "c");
        charMap.put('Ş', "s");
        charMap.put('İ', "i");
        charMap.put('I', "i");
    }

    public static String toSlug(String input) {
        if (input == null) return null;

        input = input.trim().toLowerCase();

        StringBuilder sb = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (charMap.containsKey(c)) {
                sb.append(charMap.get(c));
            } else if (Character.isLetterOrDigit(c)) {
                sb.append(c);
            } else if (Character.isSpaceChar(c) || c == '-') {
                sb.append("-");
            }
            // skip others (e.g. punctuation)
        }

        String slug = sb.toString().replaceAll("-+", "-");

        if (slug.startsWith("-")) slug = slug.substring(1);
        if (slug.endsWith("-")) slug = slug.substring(0, slug.length() - 1);

        return slug;
    }
}
