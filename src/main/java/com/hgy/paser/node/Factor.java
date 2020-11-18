package com.hgy.paser.node;

import com.hgy.common.PeekTokenIterator;
import com.hgy.common.TokenType;
import com.hgy.lexer.Token;

public class Factor extends GrammerNode {

    public Factor(Token token) {
        super();
        this.token = token;
        this.label = token.getValue();
    }

    public static GrammerNode parse(PeekTokenIterator it) {
        Token token = it.peek();
        TokenType type = token.getType();

        if(type == TokenType.VARIABLE) {
            it.next();
            return new Variable(token);
        } else if(token.isScalar()){
            it.next();
            return new Scalar(token);
        }
        return null;
    }
}
