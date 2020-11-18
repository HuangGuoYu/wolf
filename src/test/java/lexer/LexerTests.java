package lexer;

import com.hgy.common.TokenType;
import com.hgy.exception.LexicalException;
import com.hgy.lexer.Lexer;
import com.hgy.lexer.Token;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerTests {

    void assertToken(Token token, String value, TokenType type){
        assertEquals(value, token.getValue());
        assertEquals(type, token.getType());
    }

    @Test
    public void test_expression(){
        Lexer lexer = new Lexer();
        String source = "(a+b)^100.12==+100-20";
        List<Token> tokens = lexer.analyse(source.chars().mapToObj(x -> (char)x));
        assertEquals(11, tokens.size());
        assertToken(tokens.get(0), "(", TokenType.BRACKET);
        assertToken(tokens.get(1), "a", TokenType.VARIABLE);
        assertToken(tokens.get(2), "+", TokenType.OPERATOR);
        assertToken(tokens.get(3), "b", TokenType.VARIABLE);
        assertToken(tokens.get(4), ")", TokenType.BRACKET);
        assertToken(tokens.get(5), "^", TokenType.OPERATOR);
        assertToken(tokens.get(6), "100.12", TokenType.FLOAT);
        assertToken(tokens.get(7), "==", TokenType.OPERATOR);
        assertToken(tokens.get(8), "+100", TokenType.INTEGER);
        assertToken(tokens.get(9), "-", TokenType.OPERATOR);
        assertToken(tokens.get(10), "20", TokenType.INTEGER);
    }

    @Test
    public void test_function() {
        String source = "func foo(a, b){\n" +
                "print(a+b)\n" +
                "}\n" +
                "foo(-100.0, 100)";
        Lexer lexer = new Lexer();
        List<Token> tokens  = lexer.analyse(source.chars().mapToObj(x -> (char)x));

        assertToken(tokens.get(0), "func", TokenType.KEYWORDS);
        assertToken(tokens.get(1), "foo", TokenType.VARIABLE);
        assertToken(tokens.get(2), "(", TokenType.BRACKET);
        assertToken(tokens.get(3), "a", TokenType.VARIABLE);
        assertToken(tokens.get(4), ",", TokenType.OPERATOR);
        assertToken(tokens.get(5), "b", TokenType.VARIABLE);
        assertToken(tokens.get(6), ")", TokenType.BRACKET);
        assertToken(tokens.get(7), "{", TokenType.BRACKET);
        assertToken(tokens.get(8), "print", TokenType.VARIABLE);
        assertToken(tokens.get(9), "(", TokenType.BRACKET);
        assertToken(tokens.get(10), "a", TokenType.VARIABLE);
        assertToken(tokens.get(11), "+", TokenType.OPERATOR);
        assertToken(tokens.get(12), "b", TokenType.VARIABLE);
        assertToken(tokens.get(13), ")", TokenType.BRACKET);
        assertToken(tokens.get(14), "}", TokenType.BRACKET);
        assertToken(tokens.get(15), "foo", TokenType.VARIABLE);
        assertToken(tokens.get(16), "(", TokenType.BRACKET);
        assertToken(tokens.get(17), "-100.0", TokenType.FLOAT);
        assertToken(tokens.get(18), ",", TokenType.OPERATOR);
        assertToken(tokens.get(19), "100", TokenType.INTEGER);
        assertToken(tokens.get(20), ")", TokenType.BRACKET);


    }

    @Test
    public void test_deleteComment()  {
        String source = "/*123123123\n123123123*/a=1";
        Lexer lexer = new Lexer();
        List tokens = lexer.analyse(source.chars().mapToObj(x -> (char)x));
        assertEquals(3, tokens.size());
    }

    @Test
    public void test_deleteErrorComment() {
        String errorSource="/*/";
        Lexer lexer = new Lexer();
        try{
            List<Token> tokens = lexer.analyse(errorSource.chars().mapToObj(x ->(char)x));
        }catch (Exception e){
            return;
        }

        throw new LexicalException("error");
    }

    @Test
    public void test_deleteOneLine() throws LexicalException {
        String source = "//121212\na0=\'2\'";
        Lexer lexer = new Lexer();
        List tokens = lexer.analyse(source.chars().mapToObj(x -> (char)x));
        assertEquals(3, tokens.size());
    }

    @Test
    public void minus() throws LexicalException {
        String source = "n-1";
        Lexer lexer = new Lexer();
        List tokens = lexer.analyse(source.chars().mapToObj(x -> (char)x));
        assertEquals(3, tokens.size());
    }

}
