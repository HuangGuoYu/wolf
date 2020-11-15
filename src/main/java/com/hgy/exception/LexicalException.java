package com.hgy.exception;

public class LexicalException extends RuntimeException{

    private String msg;

    public LexicalException(char word) {
        this.msg = String.format("Unexpected character %c", word);
    }

    public LexicalException(String msg){
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
