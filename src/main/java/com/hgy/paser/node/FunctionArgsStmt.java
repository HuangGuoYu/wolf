package com.hgy.paser.node;

import com.hgy.common.GrammerNodeTypes;
import com.hgy.common.PeekTokenIterator;
import com.hgy.exception.ParseException;
import com.hgy.lexer.Token;

/**
 * 函数参数
 */
public class FunctionArgsStmt extends Stmtment {
    public FunctionArgsStmt() {
        super(GrammerNodeTypes.FUNC_ARGS_STMT, "FUNC_ARGS");
    }

    /**
     * args -> Type variable, args | Type variable | null
     */
    public static GrammerNode parse(PeekTokenIterator it) throws ParseException {
        FunctionArgsStmt args = new FunctionArgsStmt();

        while(it.peek().isType()) {
            Token type = it.next();
            Variable variable = (Variable)Factor.parse(it);
            if (null == variable) {
                throw new ParseException("func参数解析异常");
            }
            variable.setTypeToken(type);
            args.addChild(variable);

            if(!it.peek().getValue().equals(")")) {
                it.nextMatch(",");
            }
        }

        return args;
    }
}
