package paser;

import com.hgy.common.ParserUtils;
import com.hgy.common.PeekTokenIterator;
import com.hgy.exception.ParseException;
import com.hgy.lexer.Lexer;
import com.hgy.lexer.Token;
import com.hgy.paser.node.Expr;
import com.hgy.paser.node.GrammerNode;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Paser {

    @Test
    public void testBinaryExpr() throws ParseException {
        String expr = "1+2*3";
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.analyse(expr.chars().mapToObj(o -> (char) o));

        GrammerNode parse = Expr.parse(new PeekTokenIterator(tokens.stream()));
        System.out.println();
    }

    @Test
    public void simple() throws Exception {
        GrammerNode expr = createExpr("1+1+1");
        assertEquals("1 1 1 + +", ParserUtils.toPostfixExpression(expr));
    }

    @Test
    public void simple1() throws Exception {
        GrammerNode expr = createExpr("\"1\" == \"\"");
        assertEquals("\"1\" \"\" ==", ParserUtils.toPostfixExpression(expr));
    }

    @Test
    public void complex() throws Exception {
        GrammerNode expr1 = createExpr("1+2*3");
        GrammerNode expr2 = createExpr("1*2+3");
        GrammerNode expr3 = createExpr("10 * (7 + 4)");
        GrammerNode expr4 = createExpr("(1*2!=7)==3!=4*5+6");

        assertEquals("1 2 3 * +", ParserUtils.toPostfixExpression(expr1));
        assertEquals("1 2 * 3 +", ParserUtils.toPostfixExpression(expr2));
        assertEquals("10 7 4 + *", ParserUtils.toPostfixExpression(expr3));
        assertEquals("1 2 * 7 != 3 4 5 * 6 + != ==", ParserUtils.toPostfixExpression(expr4));
        // i++ ++i
    }

    @Test
    public void unary() throws Exception {
        GrammerNode expr1 = createExpr("1 + ++i");
        expr1.print(0);
        GrammerNode expr2 = createExpr("1 + i++");
        expr2.print(0);
        GrammerNode expr3 = createExpr("!(a+b+c)");
        expr3.print(0);

    }


    private GrammerNode createExpr(String src) throws Exception {
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.analyse(src.chars().mapToObj(x -> (char) x));
        PeekTokenIterator tokenIt = new PeekTokenIterator(tokens.stream());
        return Expr.parse( tokenIt);
    }
}
