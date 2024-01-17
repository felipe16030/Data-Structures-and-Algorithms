import java.util.NoSuchElementException;

/**
 * Your implementation of an Array List.
 *
 * @author Felipe Bergerman
 * @version 1.0
 * @userid fbergerman3
 * @GTID 903785770
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     * <p>
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Creates a new array with double the size of the original filled with the original elements.
     * <p>
     * @param arr original array.
     * @param index represents the index at which an element is being added.
     * @param data represents the data being added.
     * @return a new array with the same elements and double the size of the old array.
     */
    private T[] resizeHelper(T[] arr, int index, T data) {
        T[] newArray = (T[]) new Object[arr.length * 2];
        for (int i = 0; i < index; i++) {
            newArray[i] = arr[i];
        }
        newArray[index] = data;
        for (int i = index; i < this.backingArray.length; i++) {
            newArray[i + 1] = this.backingArray[i];
        }
        return newArray;
    }

    /**
     * Adds the element to the specified index.
     * <p>
     * Remember that this add may require elements to be shifted.
     * <p>
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be less than 0");
        }
        if (index > this.size) {
            throw new IndexOutOfBoundsException("Index cannot be greater than ArrayList size");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (this.size == this.backingArray.length) {
            this.backingArray = this.resizeHelper(this.backingArray, index, data);
            this.size++;
        } else {
            for (int i = size - 1; i >= index; i--) {
                this.backingArray[i + 1] = this.backingArray[i];
            }
            this.backingArray[index] = data;
            this.size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     * <p>
     * Remember that this add may require elements to be shifted.
     * <p>
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        this.addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        this.addAtIndex(this.size, data);
    }

    /**
     * Removes and returns the element at the specified index.
     * <p>
     * Remember that this remove may require elements to be shifted.
     * <p>
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be less than 0");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index cannot be greater than or equal to ArrayList size");
        }
        T elementRemoved = this.backingArray[index];
        for (int i = index; i < this.size - 1; i++) {
            this.backingArray[i] = this.backingArray[i + 1];
        }
        this.backingArray[size - 1] = null;
        this.size--;
        return elementRemoved;
    }

    /**
     * Removes and returns the first element of the list.
     * <p>
     * Remember that this remove may require elements to be shifted.
     * <p>
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (this.size == 0) {
            throw new NoSuchElementException("List cannot be empty");
        }
        return this.removeAtIndex(0);
    }

    /**
     * Removes and returns the last element of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (this.size == 0) {
            throw new NoSuchElementException("List cannot be empty");
        }
        return this.removeAtIndex(this.size - 1);
    }

    /**
     * Returns the element at the specified index.
     * <p>
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index cannot be less than 0.");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("index cannot be greater than or equal to ArrayList size.");
        }
        return this.backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Clears the list.
     * <p>
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        this.backingArray = (T[]) new Object[this.INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Returns the backing array of the list.
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
     * Returns the size of the list.
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
