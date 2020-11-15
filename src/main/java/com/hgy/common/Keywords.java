package com.hgy.common;

import java.util.HashSet;

public class Keywords {
    public static final HashSet KEYWORDS = new HashSet<String>(){
        {
                    add("var");
                    add("int");
                    add("float");
                    add("bool");
                    add("void");
                    add("string");
                    add("if");
                    add("else");
                    add("for");
                    add("while");
                    add("continue");
                    add("break");
                    add("func");
                    add("return");
        }
    };

    public static boolean isKeywords(String word) {
        return KEYWORDS.contains(word);
    }
}
