package com.hgy.lexer;

import com.hgy.common.TokenType;
import com.hgy.constant.OperatorConstant;
import com.hgy.constant.TypeConstant;
import lombok.Data;

/**
 * 代码Token
 */
@Data
public class Token {
    // 字符类型
    private TokenType type;
    // 字符值
    private String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("type %s, value %s", type, value);
    }

    public boolean isVariable(){
        return type == TokenType.VARIABLE;
    }

    public boolean isScalar(){
        return type == TokenType.INTEGER || type == TokenType.FLOAT ||
                type == TokenType.STRING || type == TokenType.BOOLEAN;
    }

    public boolean isNumber() {
        return this.type == TokenType.INTEGER || this.type == TokenType.FLOAT;
    }

    public boolean isOperator() {
        return this.type == TokenType.OPERATOR;
    }

    public boolean isPostUnaryOperator() {
        return this.value.equals(OperatorConstant.PLUS_PLUS) || this.value.equals(OperatorConstant.SUB_SUB);
    }

    public boolean isType() {
        return this.value.equals(TypeConstant.BOOL)
                || this.value.equals(TypeConstant.INT)
                || this.value.equals(TypeConstant.FLOAT)
                || this.value.equals(TypeConstant.VOID)
                || this.value.equals(TypeConstant.STRING);

    }

    public boolean isValue() {
        return isVariable() || isScalar();
    }

}
