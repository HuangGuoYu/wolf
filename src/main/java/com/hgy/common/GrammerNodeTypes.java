package com.hgy.common;

public enum GrammerNodeTypes {
    BINARY_EXPR, // 1+1
    UNARY_EXPR, // ++i
    VARIABLE,
    SCALAR, // 1.0, true
    DECLARE_STMT, // var name = expr | Factor
    ASSIGN_STMT,//赋值语句
    BLOCK_STMT, //语句块儿
    IF_STMT,//IF 语句
    WHILE_STMT, //while
    FUNC_STMT,//func
    FUNC_ARGS_STMT,//func args
    RETURN_STMT,//RETURN_STMT
}