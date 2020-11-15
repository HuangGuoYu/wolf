package lexer;

import com.hgy.common.PeekIterator;
import com.hgy.common.TokenType;
import com.hgy.lexer.DFA;
import com.hgy.lexer.Token;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenTests {

    void assertToken(Token token, String value, TokenType type){
        assertEquals(value, token.getValue());
        assertEquals(type, token.getType());
    }

    @Test
    public void test_varOrKeyword() {
        PeekIterator<Character> it1 = new PeekIterator<>("if name".chars().mapToObj(x -> (char) x));
        PeekIterator<Character> it2 = new PeekIterator<>("true dda".chars().mapToObj(x -> (char) x));
        PeekIterator<Character> it3 = new PeekIterator<>("1name abc".chars().mapToObj(x -> (char) x));
        Token token1 = DFA.buildVarOrKeywords(it1);
        Token token2 = DFA.buildVarOrKeywords(it2);

        assertToken(token1, "if", TokenType.KEYWORDS);
        assertToken(token2, "true", TokenType.BOOLEAN);
        it1.next();
        Token token3 = DFA.buildVarOrKeywords(it1);

        assertToken(token3,"abc", TokenType.VARIABLE);
        // 应该抛出异常
//        Token token = DFA.buildVarOrKeywords(it3);

    }

    @Test
    public void test_makeNumber() {
        String[] tests = {
                "+0 aa",
                "-1 aa",
                "+.1 dd",
                ".3 ccc",
                "15.5555 ddd",
                "0.8888 ooo",
                "-12631.159 ",
        };

        for(String test:tests) {
            PeekIterator<Character> it = new PeekIterator<>(test.chars().mapToObj(x -> (char) x));
            Token token = DFA.buildNumber(it);
            String[] splitValue = test.split(" ");
            assertToken(token, splitValue[0],
                    (test.indexOf('.') != -1) ? TokenType.FLOAT : TokenType.INTEGER);
        }
    }

}