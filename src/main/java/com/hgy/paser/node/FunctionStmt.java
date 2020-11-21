package com.hgy.paser.node;

import com.hgy.common.GrammerNodeTypes;
import com.hgy.common.PeekTokenIterator;
import com.hgy.common.TokenType;
import com.hgy.exception.ParseException;
import com.hgy.lexer.Token;

/**
 * 函数语句
 */
public class FunctionStmt extends Stmtment {
    public FunctionStmt() {
        super(GrammerNodeTypes.FUNC_STMT, "FUNC");
    }

    /**
     * Function -> func variable(args) returnType Block
     */
    public static GrammerNode parse(PeekTokenIterator it) throws ParseException {
        FunctionStmt functionStmt = new FunctionStmt();
        it.nextMatch("func");
        //解析函数名字
        Token funcNameToken = it.peek();
        Variable funcNameNode = (Variable) Factor.parse(it);
        functionStmt.setToken(funcNameToken);
        functionStmt.addChild(funcNameNode);

        //解析参数
        it.nextMatch("(");
        GrammerNode args = FunctionArgsStmt.parse(it);
        it.nextMatch(")");
        functionStmt.addChild(args);

        //解析返回值类型
        Token keyword = it.nextMatch(TokenType.KEYWORDS);
        if(!keyword.isType()) {
            throw new ParseException(keyword);
        }
        assert funcNameNode != null;
        funcNameNode.setTypeToken(keyword);
        //解析语句块儿
        functionStmt.addChild(BlockStmt.parse(it));
        return functionStmt;
    }
}
