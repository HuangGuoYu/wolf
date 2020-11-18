package com.hgy.paser.node;

import com.hgy.common.GrammerNodeTypes;
import com.hgy.lexer.Token;

/**
 * 变量
 */
public class Variable extends Factor{

    public Variable(Token token) {
        super(token);
        this.type = GrammerNodeTypes.VARIABLE;
    }
}
