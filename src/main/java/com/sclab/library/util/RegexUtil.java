package com.sclab.library.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    private static final Logger logger = LoggerFactory.getLogger(RegexUtil.class);

    public static Pattern getRegexPattern(Collection collection) {
        return getRegexPattern(collection, "");
    }

    public static Pattern getRegexPattern(Collection collection, String... regexPatterns) {
        StringBuilder builder = new StringBuilder();
        for (String p : regexPatterns) {
                builder.append("|"+p);
        }
        String regex = String.format(
                "^(%s%s)$",
                collection.toString()
                        .replace(",", "|")
                        .replace(" ", "")
                        .replace("[", "")
                        .replace("]", "") ,
                builder
        );
        logger.info("regex: "+regex);
        Pattern pattern = Pattern.compile(regex);
        return pattern;
    }

    public static boolean isPatternMatches(String value, Collection collection, String... regexPatterns) {
        Matcher matcher = getRegexPattern(collection, regexPatterns).matcher(value);
        return matcher.matches();
    }

    public static boolean isPatternMatches(String value, Pattern pattern) {
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

}