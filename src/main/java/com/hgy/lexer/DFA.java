package com.hgy.lexer;

import com.hgy.common.CharacterTypeDetermine;
import com.hgy.common.Keywords;
import com.hgy.common.PeekIterator;
import com.hgy.common.TokenType;
import com.hgy.exception.LexicalException;

import java.util.stream.Stream;

/**
 * 有穷自动状态机，用于提取Token
 */
public class DFA {

    /**
     * 目前假定第一个字符一定是合法的标识符或关键字
     * 提取字符序列中的标识符和关键字
     * @param codeIterator 字符序列
     * @return 提取的Token
     */
    public static Token buildVarOrKeywords(PeekIterator<Character> codeIterator) {
        Token token = null;
        // 结果字串
        StringBuilder value = new StringBuilder();
        int state = 0;
        // 是否第一个字符
        boolean isFirstChar = true;
        //  是否结束程序
        boolean isDone = false;
        while (!isDone && codeIterator.hasNext()) {
            char c = codeIterator.peek();
            if (isFirstChar && !CharacterTypeDetermine.isLegalFirstChar(c)) {
                throw new LexicalException(c);
            }
            isFirstChar = false;
            switch (state) {
                case 0:
                    if (CharacterTypeDetermine.isLiteral(c)) {
                        state = 1;
                        value.append(c);
                    }
                    break;

                case 1:
                    if (CharacterTypeDetermine.isLiteral(c)) {
                        state = 2;
                        value.append(c);
                    } else {
                        //其他字符出现结束
                        isDone = true;
                    }
                    break;
                case 2:
                    if (CharacterTypeDetermine.isLiteral(c)) {
                        value.append(c);
                    } else {
                        //其他字符出现结束
                        isDone = true;
                    }
                    break;
            }
            if(!isDone) {
                codeIterator.next();
            }
        }

        // 确定是关键字还是标识符或则bool值
        String resValue = value.toString();
        if(Keywords.isKeywords(resValue)) {
            token = new Token(TokenType.KEYWORDS, resValue);
        } else if ("true".equals(resValue) || "false".equals(resValue)) {
            token = new Token(TokenType.BOOLEAN, resValue);
        } else {
            token = new Token(TokenType.VARIABLE, resValue);
        }
        return token;
    }
}
