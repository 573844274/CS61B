/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        String[] newArr = asciis.clone();
        int maxLength = 0;
        for (String str : asciis) {
            if (str.length() > maxLength) {
                maxLength = str.length();
            }
        }
        for (int i = 0; i < maxLength; i += 1) {
            newArr = sortHelperLSD(newArr, i, maxLength);
        }
        return newArr;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    public static String[] sortHelperLSD(String[] asciis, int index, int maxLength) {
        // Optional LSD helper method for required LSD radix sort
        String[] newArr = new String[asciis.length];
        final int R = 256;
        int[] counts = new int[R];
        for (String str : asciis) {
            counts[index(str, index, maxLength)] += 1;
        }
        for (int i = 1; i < counts.length; i += 1) {
            counts[i] = counts[i] + counts[i - 1];
        }
        for (int i = asciis.length - 1; i >= 0; i -= 1) {
            newArr[counts[index(asciis[i], index, maxLength)] - 1] = asciis[i];
            counts[index(asciis[i], index, maxLength)] -= 1;
        }
        return newArr;
    }

    /**
     * Return the i th index of a string provided that the length is L
     * beginning from the tail.
     * @param i
     * @param L
     * @return
     */
    public static int index(String str, int i, int L) {
        int strLength = str.length();
        if (i < L - strLength) {
            return 0;
        }
        return str.charAt(L - i - 1);
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
