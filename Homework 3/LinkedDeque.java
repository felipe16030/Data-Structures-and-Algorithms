import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedDeque.
 *
 * @author Felipe Bergerman
 * @version 1.0
 * @userid fbergerman3
 * @GTID 903785770
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class LinkedDeque<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the front of the deque.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        LinkedNode<T> newFront = new LinkedNode<>(data, null, this.head);
        if (this.size == 0) {
            this.head = newFront;
            this.tail = newFront;
        } else {
            this.head.setPrevious(newFront);
            this.head = newFront;
        }
        this.size++;
    }

    /**
     * Adds the element to the back of the deque.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        LinkedNode<T> newBack = new LinkedNode<>(data, this.tail, null);
        if (this.size == 0) {
            this.head = newBack;
            this.tail = newBack;
        } else {
            this.tail.setNext(newBack);
            this.tail = newBack;
        }
        this.size++;
    }

    /**
     * Removes and returns the first element of the deque.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (this.size == 0) {
            throw new NoSuchElementException("Deque cannot be empty");
        }
        LinkedNode<T> removed = this.head;
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.head = this.head.getNext();
            this.head.setPrevious(null);
        }
        this.size--;
        return removed.getData();
    }

    /**
     * Removes and returns the last element of the deque.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (this.size == 0) {
            throw new NoSuchElementException("Deque cannot be empty");
        }
        LinkedNode<T> removed = this.tail;
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.tail = this.tail.getPrevious();
            this.tail.setNext(null);
        }
        this.size--;
        return removed.getData();
    }

    /**
     * Returns the first data of the deque without removing it.
     * <p>
     * Must be O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (this.size == 0) {
            throw new NoSuchElementException("Deque cannot be empty");
        }
        return this.head.getData();
    }

    /**
     * Returns the last data of the deque without removing it.
     * <p>
     * Must be O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (this.size == 0) {
            throw new NoSuchElementException("Deque cannot be empty");
        }
        return this.tail.getData();
    }

    /**
     * Returns the head node of the deque.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the deque.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the deque.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
