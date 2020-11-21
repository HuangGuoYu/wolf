package paser;

import com.hgy.common.ParserUtils;
import com.hgy.common.PeekTokenIterator;
import com.hgy.exception.ParseException;
import com.hgy.lexer.Lexer;
import com.hgy.lexer.Token;
import com.hgy.paser.node.FunctionStmt;
import com.hgy.paser.node.GrammerNode;
import com.hgy.paser.node.WhileStmt;
import org.junit.Test;

import java.util.List;


/**
 * 赋值语句
 * name = 123
 * age = a + b
 *  variable = Expr
 */
public class FuncStmtTest {

    @Test
    public void test() throws ParseException {
        String declareStr = "        func testfunc(int a, string b) float {\n" +
                            "            var a = 12 + 3\n" +
                            "        }";
        GrammerNode node = execParse(declareStr);
        String s = ParserUtils.toPostfixExpression(node);
        System.out.println(s);
    }


    public GrammerNode execParse(String code) throws ParseException {
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.analyse(code.chars().mapToObj(o -> (char) o));
        GrammerNode resNode = FunctionStmt.parse(new PeekTokenIterator(tokens.stream()));
        return resNode;
    }
}
