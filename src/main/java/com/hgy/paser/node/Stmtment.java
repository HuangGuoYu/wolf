package com.hgy.paser.node;

import com.hgy.common.GrammerNodeTypes;
import com.hgy.common.PeekTokenIterator;
import com.hgy.exception.ParseException;
import com.hgy.lexer.Token;
import jdk.nashorn.internal.ir.BlockStatement;

/**
 * 语句节点
 */
public abstract class Stmtment extends GrammerNode {

    public Stmtment(GrammerNodeTypes type, String label) {
        super(type, label);
    }

    protected static GrammerNode parse(PeekTokenIterator it) throws ParseException {
        if(!it.hasNext()) {
            return null;
        }
        Token peek = it.next();
        // 向前看两位，用于判断是否为赋值语句
        Token lookahead = it.peek();
        // 退还next位
        it.putBack();

        if ("var".equals(peek.getValue())) {
            return DeclareStmtment.parse(it);
        } else if (peek.isVariable() && lookahead != null && "=".equals(lookahead.getValue())) {
            return AssignStmt.parse(it);
        }
        return null;
    }
}
