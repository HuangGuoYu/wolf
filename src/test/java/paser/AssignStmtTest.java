package paser;

import com.hgy.common.GrammerNodeTypes;
import com.hgy.common.ParserUtils;
import com.hgy.common.PeekTokenIterator;
import com.hgy.exception.ParseException;
import com.hgy.lexer.Lexer;
import com.hgy.lexer.Token;
import com.hgy.paser.node.*;
import org.junit.Test;

import java.util.List;


/**
 * 赋值语句
 * name = 123
 * age = a + b
 *  variable = Expr
 */
public class AssignStmtTest {

    @Test
    public void test() throws ParseException {
        String declareStr = "name = id + 1 + 1";
        GrammerNode node = execParse(declareStr);
        String s = ParserUtils.toPostfixExpression(node);
        System.out.println(s);
    }


    public GrammerNode execParse(String code) throws ParseException {
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.analyse(code.chars().mapToObj(o -> (char) o));
        GrammerNode resNode = AssignStmt.parse(new PeekTokenIterator(tokens.stream()));
        return resNode;
    }
}
