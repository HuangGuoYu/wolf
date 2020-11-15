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

}