package com.hgy.paser.node;

import com.hgy.common.ExprFunc;
import com.hgy.common.GrammerNodeTypes;
import com.hgy.common.PeekTokenIterator;
import com.hgy.constant.PriorityTable;
import com.hgy.exception.ParseException;
import com.hgy.lexer.Token;

/**
 * 表达式
 * 一元表达式
 * 二元表达式
 */
public class Expr extends GrammerNode {

    //优先级存储对象
    private static PriorityTable priorityTable = new PriorityTable();

    public Expr(GrammerNodeTypes type, Token token) {
        super();
        this.type = type;
        this.label = token.getValue();
        this.token = token;
    }
    /**
     * 传入符号流
     * @param it 符号流迭代器
     * @return 语法树根节点
     * @throws ParseException 语法异常
     */
    public static GrammerNode parse(PeekTokenIterator it) throws ParseException {
        // 起始符E  E(K) -> E(k) Op(k) E(k + 1) | E(k + 1)
        return E(0, it);
    }

    /**
     * E(K)函数，其右递归表达式如下
     * E(k) -> E(k + 1) E_(k) 注意当 k 为最高优先级时为结束条件
     * 注意：此处默认表达式一定是一元或二元表达式
     * @param k 当前第几优先级
     * @param it 符号流
     * @return 语法根节点
     */
    private static GrammerNode E(int k, PeekTokenIterator it) throws ParseException {
        //当还未达到最高优先级时E(k) -> E(k + 1) E_(k)
        // 当前情况Expr.left = E(k + 1) Exper.right = E_(k)中的表达式部分  Expr.label为E_(k)的符号部分
        if (k < priorityTable.size() - 1) {
            GrammerNode expr = combine(it, () -> E(k + 1, it), () -> E_(k , it));
            return expr;
        } else {
            // 达到最高优先级
            //E(t) -> digital E_(t)
            //E(t) -> F E_(t) | U E_(t) 此处F相当于digital，但还有一种特俗情况一元表达式
            return race(
                    it,
                    () -> combine( it, () -> F(it), () -> E_( k, it)),
                    () -> combine( it, () -> U(it), () -> E_( k, it))
            );
        }
    }

    /**
     * E(t) -> U E_(t)
     * @param it
     * @return
     */
    private static GrammerNode U(PeekTokenIterator it) throws ParseException {
        Token token = it.peek();
        String value = token.getValue();

        if(value.equals("(")) {
            it.nextMatch("(");
            GrammerNode expr = E(0, it);
            it.nextMatch(")");
            return expr;
        }
        else if (value.equals("++") || value.equals("--") || value.equals("!")) {
            Token t = it.peek();
            it.nextMatch(value);
            Expr unaryExpr = new Expr(GrammerNodeTypes.UNARY_EXPR, t);
            unaryExpr.addChild(E(0, it));
            return unaryExpr;
        }
        return null;
    }

    /**
     * E(t) -> F E_(t)
     * @param it
     * @return
     */
    private static GrammerNode F(PeekTokenIterator it) {
        GrammerNode node = Factor.parse(it);
        if (null == node) {
            return null;
        }
        return node;
    }

    /**
     * E_(k) -> Op(k) E(k + 1) E_(k) | null
     * @param k 当前级
     * @param it 迭代器
     * @return 节点
     */
    private static GrammerNode E_(int k, PeekTokenIterator it) throws ParseException {
        // lookhead查看当前第一位是否为操作符
        Token token = it.peek();
        String value = token.getValue();
        //存在当前操作符不存在直接返回空
        if(priorityTable.get(k).indexOf(value) != -1) {
            Expr expr = new Expr(GrammerNodeTypes.BINARY_EXPR, it.nextMatch(value));
            expr.addChild(combine(it,
                    () -> E(k+1, it),
                    () -> E_(k, it)
            ));
            return expr;
        }
        return null;
    }


    /**
     * 结合时只看结果
     * @param a E(k + 1)
     *          E(t) -> digital E_(t)
     * @param b E_(k)   E_(k) -> Op(k) E(k + 1) E_(k) | null
     * @return 结合后的节点
     */
    private static GrammerNode combine(PeekTokenIterator it, ExprFunc a, ExprFunc b) throws ParseException {
        //如果a执行结果为空则时一元表达式
        GrammerNode aNode = a.exec();
        if(aNode == null) {
            return it.hasNext() ? b.exec() : null;
        }
        GrammerNode bNode = it.hasNext() ? b.exec() : null;
        if(bNode == null) {
            return aNode;
        }
        //E_(k) -> Op(k) E(k + 1) E_(k) 此处相当于Op(k) 是根节点
        Expr expr = new Expr(GrammerNodeTypes.BINARY_EXPR, bNode.token);
        expr.addChild(aNode);
        //E(k + 1)
        expr.addChild(bNode.getChild(0));
        //指定父节点
        return expr;
    }

    private static GrammerNode race(PeekTokenIterator it, ExprFunc aFunc, ExprFunc bFunc) throws ParseException {
        if(!it.hasNext()) {
            return null;
        }
        GrammerNode a = aFunc.exec();
        if(a != null) {
            return a;
        }
        return bFunc.exec();
    }



}
