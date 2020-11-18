package com.hgy.common;

import com.hgy.exception.ParseException;
import com.hgy.lexer.Token;

import java.util.stream.Stream;

public class PeekTokenIterator extends PeekIterator<Token> {

    public PeekTokenIterator(Stream<Token> stream) {
        super(stream);
    }

    /**
     * 判断下一个token的值是否等于value并返回token
     * @param value
     * @return
     * @throws ParseException
     */
    public Token nextMatch(String value) throws ParseException {
        Token token = this.next();
        if(!token.getValue().equals(value)) {
            throw new ParseException(token);
        }
        return token;
    }

    /**
     * 判断下一个token的类型是否等于type并返回token
     * @param type
     * @return
     * @throws ParseException
     */
    public Token nextMatch(TokenType type) throws ParseException {
        Token token = this.next();
        if(!token.getType().equals(type)) {
            throw new ParseException(token);
        }
        return token;
    }
}