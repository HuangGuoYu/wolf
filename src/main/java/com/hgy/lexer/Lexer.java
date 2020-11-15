package com.hgy.lexer;

import com.hgy.common.CharacterTypeDetermine;
import com.hgy.common.PeekIterator;
import com.hgy.common.TokenType;
import com.hgy.constant.CharConstant;
import com.hgy.constant.OperatorConstant;
import com.hgy.exception.LexicalException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Lexer {

    public List<Token> analyse(PeekIterator<Character> it) {
        List<Token> tokens = new ArrayList<>();
        while(it.hasNext()) {
            char c = it.next();
            // 0 代表源代码得最后一个字符
            if(c == 0) {
                break;
            }
            char lookahead = it.peek();
            // 如果是空格或回车不用处理
            if(c == CharConstant.SPACE || c == CharConstant.ENTER) {
                continue;
            }
            // 删除注释
            if(c == '/') {
                if(lookahead == '/') {
                    while(it.hasNext() && (c = it.next()) != CharConstant.ENTER) {};
                    continue;
                }
                else if(lookahead == '*') {
                    it.next();//多读一个* 避免/*/通过
                    boolean valid = false;
                    while(it.hasNext()) {
                        char p = it.next();
                        if(p == '*' && it.peek() == '/') {
                            it.next();
                            valid = true;
                            break;
                        }
                    }
                    if(!valid) {
                        throw new LexicalException("comments not match");
                    }
                    continue;
                }
            }
            // 括号直接提取
            if(c == '{' || c == '}' || c == '(' || c == ')') {
                tokens.add(new Token(TokenType.BRACKET, String.valueOf(c)));
                continue;
            }
            if(c == '"' || c == '\'') {
                it.putBack();
                tokens.add(DFA.buildString(it));
                continue;
            }
            if(CharacterTypeDetermine.isLetter(c)) {
                it.putBack();
                tokens.add(DFA.buildVarOrKeywords(it));
                continue;
            }
            if(CharacterTypeDetermine.isNumber(c)) {
                it.putBack();
                tokens.add(DFA.buildNumber(it));
                continue;
            }


            if((c == OperatorConstant.PLUS
                    || c == OperatorConstant.SUB
                    || c == OperatorConstant.DOT)
                    && CharacterTypeDetermine.isNumber(lookahead)) {
                Token lastToken = tokens.size() == 0 ? null : tokens.get(tokens.size() - 1);
                // 如果不是在做计算则当前符号是一个数字得开始
                if(lastToken == null || !lastToken.isValue() || lastToken.isOperator()) {
                    it.putBack();
                    tokens.add(DFA.buildNumber(it));
                    continue;
                }
            }

            if(CharacterTypeDetermine.isOperator(c)) {
                it.putBack();
                tokens.add(DFA.buildOperator(it));
                continue;
            }

            throw new LexicalException(c);
        } // end while
        return tokens;
    }

    public List<Token> analyse(Stream source){
        PeekIterator<Character> it = new PeekIterator<Character>(source, (char)0);
        return this.analyse(it);
    }
}