package com.hgy.paser.node;

import com.hgy.common.GrammerNodeTypes;
import com.hgy.lexer.Token;
import lombok.Data;

/**
 * 变量
 */
public class Variable extends Factor{

    private Token typeToken;

    public Variable(Token token) {
        super(token);
        this.type = GrammerNodeTypes.VARIABLE;
    }

    public Token getTypeToken() {
        return typeToken;
    }

    public void setTypeToken(Token typeToken) {
        this.typeToken = typeToken;
    }
}
