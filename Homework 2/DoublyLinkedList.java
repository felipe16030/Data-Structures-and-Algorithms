import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     * <p>
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be less than (<) zero.");
        }
        if (index > this.size) {
            throw new IndexOutOfBoundsException("Index cannot be greater than (>) doubly linked list size.");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (this.size == 0) {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data, null, null);
            this.head = newNode;
            this.tail = newNode;
        } else if (index == 0) {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data, null, this.head);
            this.head.setPrevious(newNode);
            this.head = newNode;
        } else if (index == this.size) {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data, this.tail, null);
            this.tail.setNext(newNode);
            this.tail = newNode;
        } else if (index >= this.size / 2.0) {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data, null, null);
            DoublyLinkedListNode<T> curr = this.tail;
            for (int i = 0; i < (this.size - index - 1); i++) {
                curr = curr.getPrevious();
            }
            newNode.setNext(curr);
            newNode.setPrevious(curr.getPrevious());
            curr.getPrevious().setNext(newNode);
            curr.setPrevious(newNode);
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data, null, null);
            DoublyLinkedListNode<T> curr = this.head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            newNode.setNext(curr.getNext());
            newNode.setPrevious(curr);
            curr.getNext().setPrevious(newNode);
            curr.setNext(newNode);
        }
        this.size++;
    }

    /**
     * Adds the element to the front of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        this.addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        this.addAtIndex(this.size, data);
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     * <p>
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be less than 0");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index cannot be greater than or equal to DoublyLinkedList size");
        }
        DoublyLinkedListNode<T> removed;
        if (this.size == 1) {
            removed = this.head;
            this.head = null;
            this.tail = null;
        } else if (index == 0) {
            removed = this.head;
            this.head.getNext().setPrevious(null);
            this.head = this.head.getNext();
        } else if (index == this.size - 1) {
            removed = this.tail;
            this.tail.getPrevious().setNext(null);
            this.tail = this.tail.getPrevious();
        } else if (index >= this.size / 2.0) {
            DoublyLinkedListNode<T> curr = this.tail;
            for (int i = 0; i < this.size - index - 1; i++) {
                curr = curr.getPrevious();
            }
            removed = curr.getPrevious();
            curr.getPrevious().getPrevious().setNext(curr);
            curr.setPrevious(curr.getPrevious().getPrevious());
        } else {
            DoublyLinkedListNode<T> curr = this.head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            removed = curr.getNext();
            curr.getNext().getNext().setPrevious(curr);
            curr.setNext(curr.getNext().getNext());
        }
        this.size--;
        return removed.getData();
    }

    /**
     * Removes and returns the first element of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("List cannot be empty, having a size of 0");
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
        if (this.isEmpty()) {
            throw new NoSuchElementException("List cannot be empty, having a size of 0");
        }
        return this.removeAtIndex(this.size - 1);
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     * <p>
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index cannot be less than 0.");
        } else if (index >= this.size) {
            throw new IndexOutOfBoundsException("index cannot be greater than or equal to DoublyLinkedList size.");
        }
        if (index >= this.size / 2.0) {
            DoublyLinkedListNode<T> curr = this.tail;
            for (int i = 0; i < (this.size - index - 1); i++) {
                curr = curr.getPrevious();
            }
            return curr.getData();
        } else {
            DoublyLinkedListNode<T> curr = this.head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return curr.getData();
        }

    }

    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
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
     * Clears the list.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     * <p>
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (this.size == 0) {
            throw new NoSuchElementException("DoublyLinkedList size is 0 and, thus, the data cannot be located");
        } else if (this.get(this.size - 1).equals(data)) {
            return this.removeAtIndex(this.size - 1);
        } else {
            DoublyLinkedListNode<T> removed;
            DoublyLinkedListNode<T> curr = this.tail;
            while (!curr.getData().equals(data)) {
                curr = curr.getPrevious();
                if (curr == null) {
                    throw new NoSuchElementException("Data could not be located in the DoublyLinkedList");
                }
            }
            removed = curr;
            if (curr == this.head) {
                this.head.getNext().setPrevious(null);
                this.head = this.head.getNext();
            } else {
                curr.getPrevious().setNext(curr.getNext());
                curr.getNext().setPrevious(curr.getPrevious());
            }
            this.size--;
            return removed.getData();
        }
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     * <p>
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        if (this.size == 0) {
            return (T[]) new Object[0];
        } else {
            T[] arr = (T[]) new Object[this.size];
            DoublyLinkedListNode<T> curr = this.head;
            for (int i = 0; i < this.size; i++) {
                arr[i] = curr.getData();
                curr = curr.getNext();
            }
            return arr;
        }
    }

    /**
     * Returns the head node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
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
        // DO NOT MODIFY!
        return size;
    }
}
