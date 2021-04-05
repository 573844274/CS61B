import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {

    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String strTrue = "racecar";
        String strFalse = "horse";
        assertTrue(palindrome.isPalindrome(strTrue));
        assertFalse(palindrome.isPalindrome(strFalse));
    }

    @Test
    public void testisPalindromeOffByOne() {
        String strTrue = "flake";
        String strFalse = "aka";
        CharacterComparator obo = new OffByOne();
        assertTrue(palindrome.isPalindrome(strTrue, obo));
        assertFalse(palindrome.isPalindrome(strFalse, obo));
    }
}
