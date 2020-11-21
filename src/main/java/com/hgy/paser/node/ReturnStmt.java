package com.hgy.paser.node;

import com.hgy.common.GrammerNodeTypes;
import com.hgy.common.PeekTokenIterator;
import com.hgy.exception.ParseException;
import com.hgy.lexer.Token;

/**
 * 解析返回语句
 */
public class ReturnStmt extends Stmtment {
    public ReturnStmt() {
        super(GrammerNodeTypes.RETURN_STMT, "RETURN_STMT");
    }

    /**
     * 基本形式 ReturnStmt -> return expr
     * @param it
     * @return
     */
    public static GrammerNode parse(PeekTokenIterator it) throws ParseException {
        Token returnToken = it.nextMatch("return");
        GrammerNode expr = Expr.parse(it);

        ReturnStmt returnStmt = new ReturnStmt();
        returnStmt.setToken(returnToken);
        if (null != expr)
            returnStmt.addChild(expr);
        return returnStmt;
    }
}
