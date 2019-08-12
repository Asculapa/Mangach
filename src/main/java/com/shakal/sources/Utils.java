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
}
