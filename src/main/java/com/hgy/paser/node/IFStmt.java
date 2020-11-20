package com.hgy.paser.node;

import com.hgy.common.GrammerNodeTypes;
import com.hgy.common.PeekTokenIterator;
import com.hgy.exception.ParseException;
import com.hgy.lexer.Token;

/**
 * IFStmt -> if(expr) block tail
 * tail -> else block | else IFStmt | null
 */
public class IFStmt extends Stmtment {
    public IFStmt() {
        super(GrammerNodeTypes.IF_STMT, "if");
    }

    public static GrammerNode parse(PeekTokenIterator it) throws ParseException {
        IFStmt ifStmt = new IFStmt();

        Token peek = it.peek();
        it.nextMatch("if");
        ifStmt.setToken(peek);

        // 解析表达式
        it.nextMatch("(");
        GrammerNode expr = Expr.parse(it);
        if (null == expr) {
            throw new ParseException("if表达式解析错误");
        }
        ifStmt.addChild(expr);
        it.nextMatch(")");

        // 解析语句块儿
        GrammerNode block = BlockStmt.parse(it);
        if (null == block) {
            throw new ParseException("语句块儿不可为空");
        }
        ifStmt.addChild(block);

        // 解析tail
        GrammerNode tail = parseTail(it);
        if (null != tail) {
            ifStmt.addChild(tail);
        }
        return ifStmt;
    }

    /**
     * tail -> else block | else IFStmt | null
     * 实际上此处只有两种类型结果
     * 1. block
     * 2. ifStmt
     * @param it
     * @return
     */
    private static GrammerNode parseTail(PeekTokenIterator it) throws ParseException {
        if(!it.hasNext() || !it.peek().getValue().equals("else")) {
            return null;
        }
        //消费掉else
        it.nextMatch("else");
        Token lookahead = it.peek();
        if("{".equals(lookahead.getValue())) {
            // 语句块儿
            return BlockStmt.parse(it);
        } else if ("if".equals(lookahead.getValue())) {
            return IFStmt.parse(it);
        }
        return null;
    }
}
