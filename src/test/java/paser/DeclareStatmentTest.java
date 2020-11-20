package paser;

import com.hgy.common.ParserUtils;
import com.hgy.common.PeekTokenIterator;
import com.hgy.exception.ParseException;
import com.hgy.lexer.Lexer;
import com.hgy.lexer.Token;
import com.hgy.paser.node.DeclareStmtment;
import com.hgy.paser.node.GrammerNode;
import org.junit.Test;

import java.util.List;

public class DeclareStatmentTest {


    @Test
    public void test() throws ParseException {
        String declareStr = "var name = id + 1 + 1";
        GrammerNode node = execParse(declareStr);
        String s = ParserUtils.toPostfixExpression(node);
        System.out.println(s);
    }


    public GrammerNode execParse(String code) throws ParseException {
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.analyse(code.chars().mapToObj(o -> (char) o));
        GrammerNode resNode = DeclareStmtment.parse(new PeekTokenIterator(tokens.stream()));
        return resNode;
    }
}
