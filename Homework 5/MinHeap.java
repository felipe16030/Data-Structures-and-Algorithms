import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
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
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        this.backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * <p>
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     * <p>
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     * <p>
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data List cannot be null.");
        }
        this.backingArray = (T[]) new Comparable[(data.size() * 2) + 1];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Element within data List cannot be null.");
            }
            this.backingArray[i + 1] = data.get(i);
        }
        this.size = data.size();
        this.heapBuilder(1);
    }

    /**
     * Recursive helper method that performs the heap-builder method.
     *
     * @param index the index currently being processed
     */
    private void heapBuilder(int index) {
        if (index > this.size / 2) {
            return;
        }
        this.heapBuilder((index * 2) + 1);
        this.heapBuilder((index * 2));
        this.downHeap(index);
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        //EVALUATE THE EFFICIENCY OF THIS
        if (this.size == this.backingArray.length - 1) {
            T[] newBackingArray = (T[]) new Comparable[this.backingArray.length * 2];
            for (int i = 1; i < this.backingArray.length; i++) {
                newBackingArray[i] = this.backingArray[i];
            }
            this.backingArray = newBackingArray;
            this.add(data);
        } else {
            this.backingArray[this.size + 1] = data;
            this.upHeap(this.size + 1);
            this.size++;
        }
    }

    /**
     * Private recursive helper method that up-heaps the value at index.
     *
     * @param index the index of the value being up-heaped
     */
    private void upHeap(int index) {
        if (index <= 1) {
            return;
        }
        int parentIndex = index / 2;
        if (this.backingArray[index].compareTo(this.backingArray[parentIndex]) < 0) {
            T temp = this.backingArray[index];
            this.backingArray[index] = this.backingArray[parentIndex];
            this.backingArray[parentIndex] = temp;
            this.upHeap(parentIndex);
        }
    }

    /**
     * Private recursive helper method that down-heaps the value at index.
     *
     * @param index the index of the value being down-heaped
     */
    private void downHeap(int index) {
        if (index > this.size / 2) {
            return;
        }
        //FINDS THE INDEX OF THE MINIMUM (HIGHEST PRIORITY) CHILD
        int minChildIndex;
        if (this.backingArray[(index * 2) + 1] == null) {
            minChildIndex = (index * 2);
        } else {
            if (this.backingArray[index * 2].compareTo(this.backingArray[(index * 2) + 1]) > 0) {
                minChildIndex = (index * 2) + 1;
            } else {
                minChildIndex = index * 2;
            }
        }

        if (this.backingArray[index].compareTo(this.backingArray[minChildIndex]) > 0) {
            T temp = this.backingArray[index];
            this.backingArray[index] = this.backingArray[minChildIndex];
            this.backingArray[minChildIndex] = temp;
            this.downHeap(minChildIndex);
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("MinHeap cannot be empty");
        }
        if (this.size == 1) {
            T removed = this.backingArray[1];
            this.backingArray[1] = null;
            this.size--;
            return removed;
        }
        T removed = this.backingArray[1];
        this.backingArray[1] = this.backingArray[size];
        this.backingArray[size] = null;
        this.size--;
        this.downHeap(1);
        return removed;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("MinHeap cannot be empty.");
        }
        return this.backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Clears the heap.
     * <p>
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        this.size = 0;
        this.backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Returns the backing array of the heap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
