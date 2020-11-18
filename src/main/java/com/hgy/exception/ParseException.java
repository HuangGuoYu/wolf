package com.hgy.exception;

import com.hgy.lexer.Token;
import lombok.Data;

@Data
public class ParseException extends Exception{

    private String msg;

    public ParseException(String msg) {
        this.msg = msg;
    }

    public ParseException(Token token) {
        msg = String.format("Syntax Error, unexpected token %s", token.getValue());
    }
}
