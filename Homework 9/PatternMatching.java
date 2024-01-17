import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Felipe Bergmerman
 * @version 1.0
 * @userid fbergerman3
 * @GTID 903785770
 * <p>
 * Collaborators: N/A
 * <p>
 * Resources: N/A
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm relies on the failure table (also
     * called failure function). Works better with small alphabets.
     * <p>
     * Make sure to implement the buildFailureTable() method before implementing
     * this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot have a length of 0");
        } else if (text == null || comparator == null) {
            throw new IllegalArgumentException("Neither the text nor the comparator can be null");
        }
        ArrayList<Integer> res = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return res;
        }
        int[] ft = buildFailureTable(pattern, comparator);
        int i = 0;
        int t = 0;
        while (t < text.length() && (pattern.length() - i + t) <= text.length()) {
            if (comparator.compare(pattern.charAt(i), text.charAt(t)) == 0) {
                if (i == pattern.length() - 1) {
                    res.add(t - pattern.length() + 1);
                    if (i - 1 > 0) {
                        i = ft[i];
                        t++;
                    } else {
                        t++;
                    }

                } else {
                    i++;
                    t++;
                }
            } else if (i == 0) {
                t++;
            } else {
                i = ft[i - 1];
            }
        }
        return res;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     * <p>
     * The table built should be the length of the input pattern.
     * <p>
     * Note that a given index i will contain the length of the largest prefix
     * of the pattern indices [0..i] that is also a suffix of the pattern
     * indices [1..i]. This means that index 0 of the returned table will always
     * be equal to 0
     * <p>
     * Ex.
     * pattern:       a  b  a  b  a  c
     * failureTable: [0, 0, 1, 2, 3, 0]
     * <p>
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this to check if characters are equal
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("Neither the pattern nor the comparator can be null");
        }
        int[] ft = new int[pattern.length()];
        if (ft.length == 0) {
            return ft;
        }
        ft[0] = 0;
        int l = 0;
        int r = 1;
        while (r < pattern.length()) {
            if (comparator.compare(pattern.charAt(l), pattern.charAt(r)) == 0) {
                ft[r] = l + 1;
                l++;
                r++;
            } else if (l == 0) {
                ft[r] = 0;
                r++;
            } else {
                l = ft[l - 1];
            }
        }
        return ft;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     * <p>
     * Make sure to implement the buildLastTable() method before implementing
     * this method. Do NOT implement the Galil Rule in this method.
     * <p>
     * Note: You may find the getOrDefault() method from Java's Map class
     * useful.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Neither the pattern can be null or have a length of 0");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Neither the text nor the comparator can be null");
        }
        Map<Character, Integer> lot = buildLastTable(pattern);
        ArrayList<Integer> res = new ArrayList<>();
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            int t = pattern.length() - 1;
            while (t >= 0 && comparator.compare(text.charAt(i + t), pattern.charAt(t)) == 0) {
                t--;
            }
            if (t == -1) {
                res.add(i);
                i++;
            } else {
                int shift = lot.getOrDefault(text.charAt(i + t), -1);
                if (shift < t) {
                    i = i + t - shift;
                } else {
                    i = i + 1;
                }
            }
        }
        return res;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     * <p>
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     * <p>
     * Ex. pattern = octocat
     * <p>
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     * <p>
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern cannot be null");
        }
        Map<Character, Integer> lot = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            lot.put(pattern.charAt(i), i);
        }
        return lot;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     * <p>
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     * <p>
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     * <p>
     * sum of: c * BASE ^ (pattern.length - 1 - i)
     * c is the integer value of the current character, and
     * i is the index of the character
     * <p>
     * We recommend building the hash for the pattern and the first m characters
     * of the text by starting at index (m - 1) to efficiently exponentiate the
     * BASE. This allows you to avoid using Math.pow().
     * <p>
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to calculate BASE^(m - 1).
     * <p>
     * Ex. Hashing "bunn" as a substring of "bunny" with base 113
     * = (b * 113 ^ 3) + (u * 113 ^ 2) + (n * 113 ^ 1) + (n * 113 ^ 0)
     * = (98 * 113 ^ 3) + (117 * 113 ^ 2) + (110 * 113 ^ 1) + (110 * 113 ^ 0)
     * = 142910419
     * <p>
     * Another key point of this algorithm is that updating the hash from
     * one substring to the next substring must be O(1). To update the hash,
     * subtract the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar as shown by this formula:
     * (oldHash - oldChar * BASE ^ (pattern.length - 1)) * BASE + newChar
     * <p>
     * Ex. Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y
     * = (142910419 - 98 * 113 ^ 3) * 113 + 121
     * = 170236090
     * <p>
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^(m - 1) is for updating the hash.
     * <p>
     * Do NOT use Math.pow() in this method.
     * Do NOT implement your own version of Math.pow().
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be null or have length 0");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Both text and comparator must not be null");
        }

        ArrayList<Integer> occur = new ArrayList<>();
        int baseM1 = 1;
        int i = 0;
        if (pattern.length() > text.length()) {
            return occur;
        }
        int pHash = pattern.charAt(pattern.length() - 1);
        int tHash = text.charAt(pattern.length() - 1);

        for (int j = pattern.length() - 2; j >= 0; j--) {
            baseM1 *= BASE;
            tHash += text.charAt(j) * baseM1;
            pHash += pattern.charAt(j) * baseM1;
        }

        while (i <= text.length() - pattern.length()) {
            if (pHash == tHash) {
                int j = 0;
                while (j < pattern.length() && comparator.compare(pattern.charAt(j), text.charAt(i + j)) == 0) {
                    j++;
                }
                if (j == pattern.length()) {
                    occur.add(i);
                }
            }

            if (i + pattern.length() >= text.length()) {
                break;
            }

            tHash = (tHash - (text.charAt(i) * baseM1)) * BASE + text.charAt(i + pattern.length());
            i++;
        }
        return occur;
    }

    /**
     * This method is OPTIONAL and for extra credit only.
     * <p>
     * The Galil Rule is an addition to Boyer Moore that optimizes how we shift the pattern
     * after a full match. Please read Extra Credit: Galil Rule section in the homework pdf for details.
     * <p>
     * Make sure to implement the buildLastTable() method and buildFailureTable() method
     * before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMooreGalilRule(CharSequence pattern,
                                                    CharSequence text,
                                                    CharacterComparator comparator) {
        return null; // if you are not implementing this method, do NOT modify this line
    }
}
