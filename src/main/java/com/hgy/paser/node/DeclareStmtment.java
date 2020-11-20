package com.hgy.paser.node;

import com.hgy.common.GrammerNodeTypes;
import com.hgy.common.PeekTokenIterator;
import com.hgy.exception.ParseException;
import com.hgy.lexer.Token;

public class DeclareStmtment extends Stmtment {


    public DeclareStmtment() {
        super(GrammerNodeTypes.DECLARE_STMT, "declare");
    }

    /**
     * var age = 1 + pre
     * var age = 12
     * @param it
     * @return
     * @throws ParseException
     */
    public static GrammerNode parse(PeekTokenIterator it) throws ParseException {
        // 首先判断是否为var 并消费掉
        DeclareStmtment declareStmtment = new DeclareStmtment();
        Token token = it.peek();
        it.nextMatch("var");

        // left
        Token name = it.peek();
        GrammerNode declareName = Factor.parse(it);
        if (declareName == null) {
            throw new ParseException(name);
        }
        declareStmtment.addChild(declareName);

        //curNode
        Token lexme = it.nextMatch("=");
        declareStmtment.setToken(lexme);

        //right
        GrammerNode expr = Expr.parse(it);
        declareStmtment.addChild(expr);


        return declareStmtment;
    }
}
