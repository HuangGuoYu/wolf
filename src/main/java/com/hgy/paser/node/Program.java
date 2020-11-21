package com.hgy.paser.node;

import com.hgy.common.PeekTokenIterator;
import com.hgy.exception.ParseException;

/**
 * 程序代码块儿
 */
public class Program extends BlockStmt {
    public Program() {
        super();
    }

    public static GrammerNode parse(PeekTokenIterator it) throws ParseException {
        Program block = new Program();
        GrammerNode stmt = null;
        while( (stmt = Stmtment.parse(it)) != null) {
            block.addChild(stmt);
        }
        return block;

    }
}