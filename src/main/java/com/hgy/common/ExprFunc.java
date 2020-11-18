package com.hgy.common;

import com.hgy.exception.ParseException;
import com.hgy.paser.node.GrammerNode;

@FunctionalInterface
public interface ExprFunc {

    GrammerNode exec() throws ParseException;

}