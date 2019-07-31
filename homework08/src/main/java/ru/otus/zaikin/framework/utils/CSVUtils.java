package ru.otus.zaikin.framework.utils;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Log4j2
public class CSVUtils {
    private static final char DEFAULT_SEPARATOR = ',';

    public static void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

    //https://tools.ietf.org/html/rfc4180
    private static String followCVSformat(String value) {
        String result = value;
        if (result != null && !result.isEmpty() && result.contains("\"")) result = result.replace("\"", "\"\"");
        return result;

    }

    public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {
        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') separators = DEFAULT_SEPARATOR;

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) sb.append(separators);
            if (customQuote == ' ') sb.append(followCVSformat(value));
            else sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            first = false;
        }
        sb.append(System.lineSeparator());
        w.append(sb.toString());
    }
}