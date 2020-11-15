package com.hgy.lexer;

import com.hgy.common.CharacterTypeDetermine;
import com.hgy.common.Keywords;
import com.hgy.common.PeekIterator;
import com.hgy.common.TokenType;
import com.hgy.constant.CharConstant;
import com.hgy.constant.OperatorConstant;
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

    /**
     * 提取数字
     */
    public static Token buildNumber(PeekIterator<Character> codeIterator) {
        int state = 0;
        StringBuilder value = new StringBuilder();
        while (codeIterator.hasNext()) {
            char c = codeIterator.peek();
            switch (state){
                case 0:
                    if (c == CharConstant.ZERO) {
                        state = 1;
                    } else if (CharacterTypeDetermine.isNumber(c)) {
                        state = 2;
                    } else if (c == OperatorConstant.PLUS || c == OperatorConstant.SUB) {
                        state = 3;
                    } else if (c == OperatorConstant.DOT) {
                        state = 5;
                    }
                    break;
                case 1:
                    if (c == CharConstant.ZERO) {
                        state = 1;
                    } else if (c == OperatorConstant.DOT) {
                        state = 4;
                    } else if (CharacterTypeDetermine.isNumber(c)) {
                        state = 2;
                    } else {
                        return new Token(TokenType.INTEGER, value.toString());
                    }
                    break;
                case 2:
                    if(CharacterTypeDetermine.isNumber(c)) {
                        state = 2;
                    } else if(c == OperatorConstant.DOT) {
                        state = 4;
                    } else {
                        return new Token(TokenType.INTEGER, value.toString());
                    }
                    break;
                case 3:
                    if(CharacterTypeDetermine.isNumber(c)) {
                        state = 2;
                    } else if(c == OperatorConstant.DOT) {
                        state = 5;
                    } else {
                        throw new LexicalException(c);
                    }
                    break;
                case 4:
                    if(c == OperatorConstant.DOT) {
                        throw new LexicalException(c);
                    }
                    else if(CharacterTypeDetermine.isNumber(c)) {
                        state = 6;
                    }
                    else {
                        return new Token(TokenType.FLOAT, value.toString());
                    }
                    break;
                case 5:
                    if(CharacterTypeDetermine.isNumber(c)) {
                        state = 6;
                    }
                    else {
                        throw new LexicalException(c);
                    }
                    break;
                case 6:
                    if(CharacterTypeDetermine.isNumber(c)) {
                        state = 6;
                    }
                    else if(c == OperatorConstant.DOT) {
                        throw new LexicalException(c);
                    }
                    else {
                        return new Token(TokenType.FLOAT, value.toString());
                    }
                    break;
            }
            // 真正得消耗掉流
            value.append(codeIterator.next());
        }
        throw new LexicalException("不能正确提取数字Token");
    }

    /**
     * 提取操作符
     */
    public static Token buildOperator(PeekIterator<Character> it) {
        int state = 0;

        while(it.hasNext()) {
            char c = it.next();

            switch (state) {
                case 0:
                    switch (c) {
                        case OperatorConstant.PLUS:
                            state = 1;
                            break;
                        case OperatorConstant.SUB:
                            state = 2;
                            break;
                        case OperatorConstant.MUL:
                            state = 3;
                            break;
                        case OperatorConstant.DIV:
                            state = 4;
                            break;
                        case OperatorConstant.GT:
                            state = 5;
                            break;
                        case OperatorConstant.LT:
                            state = 6;
                            break;
                        case OperatorConstant.EQ:
                            state = 7;
                            break;
                        case OperatorConstant.OPPOSE:
                            state = 8;
                            break;
                        case OperatorConstant.AND:
                            state = 9;
                            break;
                        case OperatorConstant.OR:
                            state = 10;
                            break;
                        case OperatorConstant.XOR:
                            state = 11;
                            break;
                        case OperatorConstant.MOD:
                            state = 12;
                            break;
                        case OperatorConstant.COMMA:
                            return new Token(TokenType.OPERATOR, String.valueOf(OperatorConstant.COMMA));
                        case OperatorConstant.SEMICOLON:
                            return new Token(TokenType.OPERATOR,  String.valueOf(OperatorConstant.SEMICOLON));
                    }
                    break;
                case 1:
                    if(c == OperatorConstant.PLUS) {
                        return new Token(TokenType.OPERATOR, "++");
                    } else if(c == OperatorConstant.EQ) {
                        return new Token(TokenType.OPERATOR, "+=");
                    }else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "+");
                    }
                case 2:
                    if(c == OperatorConstant.SUB) {
                        return new Token(TokenType.OPERATOR, "--");
                    } else if(c == OperatorConstant.EQ) {
                        return new Token(TokenType.OPERATOR, "-=");
                    }else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "-");
                    }
                case 3:
                    if(c == OperatorConstant.EQ) {
                        return new Token(TokenType.OPERATOR, "*=");
                    }else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "*");
                    }
                case 4:
                    if(c == OperatorConstant.EQ) {
                        return new Token(TokenType.OPERATOR, "/=");
                    }else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "/");
                    }
                case 5:
                    if(c == OperatorConstant.EQ) {
                        return new Token(TokenType.OPERATOR, ">=");
                    }else if(c== OperatorConstant.GT) {
                        return new Token(TokenType.OPERATOR, ">>");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, ">");

                    }
                case 6:
                    if(c == OperatorConstant.EQ) {
                        return new Token(TokenType.OPERATOR, "<=");
                    }else if(c== OperatorConstant.LT) {
                        return new Token(TokenType.OPERATOR, "<<");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "<");
                    }
                case 7:
                    if(c == OperatorConstant.EQ) {
                        return new Token(TokenType.OPERATOR, "==");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "=");
                    }
                case 8:
                    if(c == OperatorConstant.EQ) {
                        return new Token(TokenType.OPERATOR, "!=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "!");
                    }
                case 9:
                    if(c == OperatorConstant.AND) {
                        return new Token(TokenType.OPERATOR, "&&");
                    }  else if (c == OperatorConstant.EQ) {
                        return new Token(TokenType.OPERATOR, "&=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "&");
                    }
                case 10:
                    if(c == OperatorConstant.OR) {
                        return new Token(TokenType.OPERATOR, "||");
                    }  else if (c == OperatorConstant.EQ) {
                        return new Token(TokenType.OPERATOR, "|=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "|");
                    }
                case 11:
                    if(c == OperatorConstant.XOR) {
                        return new Token(TokenType.OPERATOR, "^^");
                    }  else if (c == OperatorConstant.EQ) {
                        return new Token(TokenType.OPERATOR, "^=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "^");
                    }
                case 12:
                    if (c == OperatorConstant.EQ) {
                        return new Token(TokenType.OPERATOR, "%=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "%");
                    }
            }

        }
        throw new LexicalException("不能正常提取符号");
    }

    /**
     * 提取字符串
     */
    public static Token buildString(PeekIterator<Character> it) {
        StringBuilder value = new StringBuilder();
        int state = 0;
        while(it.hasNext()) {
            char c = it.next();
            switch (state) {
                case 0:
                    if(c == '\"') {
                        state = 1;
                    } else {
                        state = 2;
                    }
                    value.append(c);
                    break;
                case 1:
                    if(c == '"') {
                        value.append(c);
                        return new Token(TokenType.STRING, value.toString());
                    }
                    else {
                        value.append(c);
                    }
                    break;
                case 2:
                    if(c == '\'') {
                        value.append(c);
                        return new Token(TokenType.STRING, value.toString());
                    }
                    else {
                        value.append(c);
                    }
                    break;
            }
        }
        throw new LexicalException("不能正常提取字符串");


    }
}
