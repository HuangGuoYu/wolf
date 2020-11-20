package com.hgy.paser.node;

import com.hgy.common.GrammerNodeTypes;
import com.hgy.common.PeekTokenIterator;
import com.hgy.exception.ParseException;

/**
 * 语句块儿， 在一对花括号中的代码
 *
 */
public class BlockStmt extends Stmtment {
    public BlockStmt() {
        super(GrammerNodeTypes.BLOCK_STMT, "blockStatement");
    }

    public static GrammerNode parse(PeekTokenIterator it) throws ParseException {
        BlockStmt blockStatement = new BlockStmt();
        //首先正确消费掉{
        it.nextMatch("{");
        //循环解析Stmt
        GrammerNode item = null;
        while ((item = Stmtment.parse(it)) != null) {
            blockStatement.addChild(item);
        }
        it.nextMatch("}");
        return blockStatement;
    }
}
