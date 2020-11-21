package com.hgy.paser.node;

import com.hgy.common.GrammerNodeTypes;
import com.hgy.common.PeekTokenIterator;
import com.hgy.exception.ParseException;
import com.hgy.lexer.Token;

/**
 * while循环语句
 */
public class WhileStmt extends Stmtment {
    public WhileStmt() {
        super(GrammerNodeTypes.WHILE_STMT, "WHILE");
    }


    public static GrammerNode parse(PeekTokenIterator it) throws ParseException {

        WhileStmt whileStmt = new WhileStmt();

        Token peek = it.peek();
        it.nextMatch("while");

        // curNode
        whileStmt.setToken(peek);

        //解析条件表达式  left node
        it.nextMatch("(");
        GrammerNode expr = Expr.parse(it);
        if (null == expr) {
            throw new ParseException("while需要条件表达式");
        }
        whileStmt.addChild(expr);
        it.nextMatch(")");

        //解析循环体 Block
        GrammerNode whileBlock = BlockStmt.parse(it);
        if (null == whileBlock) {
            throw new ParseException("while必须包含循环体");
        }
        whileStmt.addChild(whileBlock);

        return whileStmt;

    }
}
