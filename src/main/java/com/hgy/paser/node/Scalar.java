package com.hgy.paser.node;

import com.hgy.common.GrammerNodeTypes;
import com.hgy.lexer.Token;

/**
 * 值 1， 100 ， true ， false ..
 */
public class Scalar extends Factor{
    public Scalar(Token token) {
        super(token);
        this.type = GrammerNodeTypes.SCALAR;
    }
}