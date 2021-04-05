public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordDeque = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i += 1) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return checkPalindrome(wordDeque);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        return checkPalindrome(wordDeque, cc);
    }

    /**
     * A recursive helper function of isPalindrome
     */
    public boolean checkPalindrome(Deque<Character> wordDeque) {
        if (wordDeque.size() <= 1) {
            return true;
        } else {
            char first = wordDeque.removeFirst();
            char last = wordDeque.removeLast();
            return (first == last && checkPalindrome(wordDeque));
        }
    }

    /**
     * A recursive helper function of overloaded isPalindrome
     */
    public boolean checkPalindrome(Deque<Character> wordDeque, CharacterComparator cc) {
        if (wordDeque.size() <= 1) {
            return true;
        } else {
            char first = wordDeque.removeFirst();
            char last = wordDeque.removeLast();
            return (cc.equalChars(first, last) && checkPalindrome(wordDeque, cc));
        }
    }
}
