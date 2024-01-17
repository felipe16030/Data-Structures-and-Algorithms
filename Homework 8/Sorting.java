import java.util.List;
import java.util.Random;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Felipe Bergerman
 * @version 1.0
 * @userid fbergerman3
 * @GTID 903785770
 * <p>
 * Collaborators: N/A
 * <p>
 * Resources: N/A
 */
public class Sorting {

    /**
     * Implement insertion sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Input parameters cannot be null");
        }
        for (int i = 1; i < arr.length; i++) {
            int currInd = i;
            while (currInd > 0 && comparator.compare(arr[currInd], arr[currInd - 1]) < 0) {
                T temp = arr[currInd];
                arr[currInd] = arr[currInd - 1];
                arr[currInd - 1] = temp;
                currInd -= 1;

            }
        }
    }

    /**
     * Implement cocktail sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Input parameters cannot be null");
        }
        boolean swapsMade = true;
        int start = 0;
        int end = arr.length - 1;
        while (swapsMade) {
            swapsMade = false;
            int propEnd = end;
            int propStart = start;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapsMade = true;
                    propEnd = i;
                }
            }
            end = propEnd;
            if (swapsMade) {
                swapsMade = false;
                for (int i = end; i > start; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T temp = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = temp;
                        swapsMade = true;
                        propStart = i;
                    }
                }
                start = propStart;
            }
        }
    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * <p>
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Input parameters cannot be null");
        }
        if (arr.length == 1 || arr.length == 0) {
            return;
        } else {
            int middle = arr.length / 2;
            T[] left = (T[]) new Object[middle];

            T[] right = (T[]) new Object[arr.length - middle];

            for (int i = 0; i < left.length; i++) {
                left[i] = arr[i];
            }
            for (int i = middle; i < arr.length; i++) {
                right[i - middle] = arr[i];
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            merge(left, right, arr, comparator);
        }
    }

    /**
     * Private helper method for the merge sort that merges together left and right arrays.
     *
     * @param left       the left array being merged
     * @param right      the right array being merged
     * @param arr        the target array that is being merged into
     * @param comparator the comparator used to check equality between values
     * @param <T>        the data type to sort
     */
    private static <T> void merge(T[] left, T[] right, T[] arr, Comparator<T> comparator) {
        int l = 0;
        int r = 0;
        int ind = 0;
        while (l < left.length && r < right.length) {
            if (comparator.compare(left[l], right[r]) <= 0) {
                arr[ind] = left[l];
                l++;
            } else {
                arr[ind] = right[r];
                r++;
            }
            ind++;
        }
        while (l < left.length) {
            arr[ind] = left[l];
            l++;
            ind++;
        }
        while (r < right.length) {
            arr[ind] = right[r];
            r++;
            ind++;
        }
    }

    /**
     * Implement quick sort.
     * <p>
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     * <p>
     * int pivotIndex = rand.nextInt(b - a) + a;
     * <p>
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     * <p>
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Input parameters cannot be null");
        }
        quickSortHelper(arr, 0, arr.length, comparator, rand);
    }

    /**
     * Private recursive helper method that performs a quick sort on the array while keeping track of a
     * left and right index.
     *
     * @param arr        the array being sorted
     * @param left       the left bound index
     * @param right      the right bound index
     * @param comparator the comparator used to compare objects
     * @param rand       the random object used to generate random indexes
     * @param <T>        the generic for objects in the array
     */

    private static <T> void quickSortHelper(T[] arr, int left, int right,
                                            Comparator<T> comparator, Random rand) {
        if (left >= right) {
            return;
        }
        int pivotInd = rand.nextInt(right - left) + left;
        T pivot = arr[pivotInd];
        arr[pivotInd] = arr[left];
        arr[left] = pivot;
        int i = left + 1;
        int j = right - 1;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivot) <= 0) {
                ++i;
            }
            while (i <= j && comparator.compare(arr[j], pivot) >= 0) {
                --j;
            }
            if (i <= j) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        T temp = arr[j];
        arr[j] = pivot;
        arr[left] = temp;
        quickSortHelper(arr, left, j, comparator, rand);
        quickSortHelper(arr, j + 1, right, comparator, rand);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     * <p>
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     * <p>
     * Refer to the PDF for more information on LSD Radix Sort.
     * <p>
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     * <p>
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array input cannot be null");
        }
        if (arr.length == 0) {
            return;
        }

        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }

        Integer maxNum = Math.abs(arr[0]);
        for (int i = 0; i < arr.length; i++) {
            if (Math.abs(arr[i]) > maxNum) {
                maxNum = Math.abs(arr[i]);
            }
        }

        int maxNumDigits = 0;
        while (maxNum != 0) {
            maxNum = maxNum / 10;
            maxNumDigits++;
        }

        int currPow = 1;
        int currDig = 0;
        for (int i = 0; i < maxNumDigits; i++) {
            for (int j = 0; j < arr.length; j++) {
                currDig = (arr[j] / currPow) % 10;
                buckets[currDig + 9].addLast(arr[j]);
            }
            int idx = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[idx++] = bucket.removeFirst();
                }
            }
            currPow *= 10;
        }
    }

    /**
     * Implement heap sort.
     * <p>
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     * <p>
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     * <p>
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data being analyzed cannot be null");
        }
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(data);
        int[] res = new int[data.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = minHeap.remove();
        }
        return res;
    }
}
