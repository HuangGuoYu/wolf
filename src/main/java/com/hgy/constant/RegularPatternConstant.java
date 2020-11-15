package com.hgy.constant;

import java.util.regex.Pattern;

public class RegularPatternConstant {
    public static Pattern ptnLetter = Pattern.compile("^[a-zA-Z]$");//字母
    public static Pattern ptnNumber = Pattern.compile("^[0-9]$");//数字
    public static Pattern ptnLiteral = Pattern.compile("^[_a-zA-Z0-9]$");//文字
    public static Pattern ptnOperator = Pattern.compile("^[*+\\-<>=!&|^%/,]$");//符号

    public static Pattern ptnVarOrKeywordsFirstChar = Pattern.compile("^[_a-zA-Z]$");//标识符或关键字第一个字符
}
