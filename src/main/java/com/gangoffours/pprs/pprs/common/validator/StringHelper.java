package com.gangoffours.pprs.pprs.common.validator;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringHelper {
    public static String Capitalise(String value) {
        if (value == null) return null;
        if (value == "") return "";
        return Arrays.stream(value.split("\\s+"))
            .map(w -> w.trim())
            .map(w ->
                (w.length() > 0 ? w.substring(0, 1).toUpperCase() : "") +
                (w.length() > 1 ? w.substring(1) : "")
            )
            .collect(Collectors.joining(" "));
    }
}