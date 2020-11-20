package com.hgy.paser.node;

import com.hgy.common.GrammerNodeTypes;
import com.hgy.common.PeekTokenIterator;
import com.hgy.exception.ParseException;
import com.hgy.lexer.Token;


/**
 * 赋值语句
 * name = 123
 * age = a + b
 *  variable = Expr
 */
public class AssignStmt extends Stmtment {

    public AssignStmt() {
        super(GrammerNodeTypes.ASSIGN_STMT, "designStatement");
    }

    public static GrammerNode parse(PeekTokenIterator it) throws ParseException {
        AssignStmt assignStmt = new AssignStmt();
        // 1.首先赋值语句一定是一个variable开头
        Token peek = it.peek();
        if (!peek.isVariable()) {
            throw new ParseException(peek);
        }
        GrammerNode variable = Factor.parse(it);
        assignStmt.addChild(variable);

        //变量名过后一定为一个赋值符号
        Token equalSymbol = it.peek();
        it.nextMatch("=");
        assignStmt.setToken(equalSymbol);

        // 等号后面为一个表达式Expr
        assignStmt.addChild(Expr.parse(it));
        return assignStmt;
    }


}
