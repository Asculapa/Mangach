package com.shakal.sources;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static ArrayList<Integer> getIntsFromStr(String s) throws NumberFormatException {
        ArrayList<Integer> integers = new ArrayList<Integer>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            integers.add(Integer.parseInt(matcher.group()));
        }
        return integers;
    }

    public static ArrayList<String> parseString(String reg, String s) {
        ArrayList<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }
}
