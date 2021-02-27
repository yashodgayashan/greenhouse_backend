package com.teamgreen.greenhouse.utils;

public class MiscellaneousUtils {

    public static String withComma(String field) {
        return encapFieldWithBackTick(field) + ", ";
    }

    public static String getUpdateSyntax(String field) {
        return encapFieldWithBackTick(field) + " = " + "COALESCE(?," + encapFieldWithBackTick(field) + "), ";
    }

    public static String getUpdateSyntaxFinal(String field) {
        return removeLastChar(getUpdateSyntax(field), ',');
    }

    public static String encapFieldWithBackTick(String field) {
        return "`" + field + "`";
    }

    public static String removeLastChar(String str, char character) {
        if (str != null) {
            str = str.trim();
            if (str.length() > 0 && str.charAt(str.length() - 1) == character) { // 'x'
                str = str.substring(0, str.length() - 1);
            }
        }
        return str;
    }

    public static String getStatementParams(int numberOfParams) {
        String stmtParams = "(";
        int count = 0;

        while (count < numberOfParams) {
            stmtParams += "?,";
            count++;
        }

        stmtParams = stmtParams.substring(0, stmtParams.length() - 1) + ")";
        return stmtParams;
    }

}
