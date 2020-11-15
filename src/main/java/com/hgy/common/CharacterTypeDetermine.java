package com.hgy.common;

import com.hgy.constant.RegularPatternConstant;

public class CharacterTypeDetermine {

    public static boolean isLetter(char c) {
        return RegularPatternConstant.ptnLetter.matcher(String.valueOf(c)).matches();
    }

    public static boolean isNumber(char c) {
        return RegularPatternConstant.ptnNumber.matcher(String.valueOf(c)).matches();
    }

    public static boolean isLiteral(char c) {
        return RegularPatternConstant.ptnLiteral.matcher(String.valueOf(c)).matches();
    }

    public static boolean isOperator(char c) {
        return RegularPatternConstant.ptnOperator.matcher(String.valueOf(c)).matches();
    }

    public static boolean isLegalFirstChar(char c) {
        return RegularPatternConstant.ptnVarOrKeywordsFirstChar.matcher(String.valueOf(c)).matches();
    }
}
