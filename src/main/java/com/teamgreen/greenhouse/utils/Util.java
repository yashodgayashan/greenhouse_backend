package com.teamgreen.greenhouse.utils;

public class Util {

    private Util(){}

    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().equals("");
    }

    public static boolean isValidInteger(String intValue) {
        return isNotEmpty(intValue) && Integer.parseInt(intValue) > 0;
    }
}
