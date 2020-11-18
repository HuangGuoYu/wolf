package paser;

import com.hgy.common.PeekTokenIterator;
import com.hgy.exception.ParseException;
import com.hgy.lexer.Lexer;
import com.hgy.lexer.Token;
import com.hgy.paser.node.Expr;
import com.hgy.paser.node.GrammerNode;
import org.junit.Test;

import java.util.List;

public class Paser {

    @Test
    public void testBinaryExpr() throws ParseException {
        String expr = "1+2*3";
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.analyse(expr.chars().mapToObj(o -> (char) o));

        GrammerNode parse = Expr.parse(new PeekTokenIterator(tokens.stream()));
        System.out.println();
    }
}
