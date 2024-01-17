import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Comprehensive Tests for HWK 2
 *
 *
 * @author Ashwin Mudaliar
 * @version 1.0.0
 */

public class DoublyLinkedListTest {

    private static final int TIMEOUT = 500;
    private DoublyLinkedList<String> list;
    private static String str = "I LOVE CS1332!!!";

    private static final int NUMELEMENTS = 10;

    @Before
    public void setUp() {

        list = new DoublyLinkedList<>();

    }

    /**
     * Helper method for tests to populate list
     * @param list list to add to
     * @param numElements number of elements desired
     */
    private static void populateList(DoublyLinkedList<String> list, int numElements)  {

        for (int k = 0; k < numElements; k++) {

            list.addToBack(String.valueOf(k));

        }

    }

    // Test exceptions: ** Note a complex description has more than 3 spaces

    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void addAtIndexNegativeIndex() {

        list.addAtIndex(-1, str);

    }

    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void addAtIndexLargerThanSize() {

        list.addAtIndex(list.size() + 1, str);

    }


    @Test(expected = java.lang.IllegalArgumentException.class)
    public void addAtIndexDataIsNull() {

        list.addAtIndex(0, null);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void addToFrontDataIsNull() {

        list.addToFront(null);

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void addToBackDataIsNull() {

        list.addToFront(null);

    }

    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void removeAtIndexNegativeIndex() {

        list.removeAtIndex(-1);

    }

    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void removeAtIndexEqualOrGreaterSize() {

        list.removeAtIndex(list.size() + 1);
        list.removeAtIndex(list.size());

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void removeFromFrontListIsEmpty() {

        list.clear();
        list.removeFromFront();

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void removeFrontBackListIsEmpty() {

        list.clear();
        list.removeFromBack();

    }

    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void getNegativeIndex() {

        list.removeAtIndex(-1);

    }

    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void getEqualOrGreaterSize() {

        list.get(list.size() + 1);
        list.get(list.size());

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void removeLastOccurenceDataIsNull() {

        list.removeLastOccurrence(null);

    }

    // Testing addAtIndex

    @Test(timeout = TIMEOUT)
    public void addAtIndexSizeZero() {

        list.addAtIndex(0, str);

        assertEquals(list.getHead(), list.getTail());

    }

    @Test(timeout = TIMEOUT)
    public void addAtIndexSizeOneAtFront() {

        list.addAtIndex(0, "1");
        list.addAtIndex(0, "0");

        assertNotEquals(list.getHead(), list.getTail());
        assertEquals(list.getHead().getData(), "0");
        assertEquals(list.getTail().getData(), "1");

    }

    @Test(timeout = TIMEOUT)
    public void addAtIndexSizeOneAtBack() {

        list.addAtIndex(0, "0");
        list.addAtIndex(1, "1");

        assertNotEquals(list.getHead(), list.getTail());
        assertEquals(list.getHead().getData(), "0");
        assertEquals(list.getTail().getData(), "1");

    }

    @Test(timeout = TIMEOUT)
    public void addAtIndexStartsFromRightEnd() {

        // in progress

        assertEquals(true, true);

    }

    @Test(timeout = TIMEOUT)
    public void addAtIndexTest() {

        for (int k = 0; k < NUMELEMENTS; k++) {

            list.addAtIndex(k, String.valueOf(k));

        }

        assertEquals(NUMELEMENTS, list.size());

        DoublyLinkedListNode<String> curr = list.getHead();
        DoublyLinkedListNode<String> prev = curr.getPrevious();
        DoublyLinkedListNode<String> next = curr.getNext();

        for (int k = 0; k < NUMELEMENTS; k++) {

            System.out.println("Testing Index: " + k + " and Current Node: " + curr.getData());

            assertEquals(String.valueOf(k), curr.getData());


            if (k == 0) {

                assertEquals(null, curr.getPrevious());
                assertEquals("1", curr.getNext().getData());

            } else if (k == NUMELEMENTS - 1) {

                assertEquals(String.valueOf(k - 1), curr.getPrevious().getData());
                assertEquals(null, curr.getNext());

            } else {

                assertEquals(String.valueOf(k - 1), curr.getPrevious().getData());
                assertEquals(String.valueOf(k + 1), curr.getNext().getData());

            }

            curr = curr.getNext();

        }

    }

    @Test(timeout = TIMEOUT)
    public void addToFrontSizeZero() {

        list.addToFront(str);

        assertEquals(list.getHead(), list.getTail());

    }

    @Test(timeout = TIMEOUT)
    public void addToFrontSizeOneAtFront() {

        list.addToFront("1");
        list.addToFront("0");

        assertNotEquals(list.getHead(), list.getTail());
        assertEquals(list.getHead().getData(), "0");
        assertEquals(list.getTail().getData(), "1");

    }


    @Test(timeout = TIMEOUT)
    public void addToFrontTest() {

        for (int k = NUMELEMENTS - 1; k >= 0; k--) {

            list.addToFront(String.valueOf(k));

        }

        assertEquals(NUMELEMENTS, list.size());

        DoublyLinkedListNode<String> curr = list.getHead();
        DoublyLinkedListNode<String> prev = curr.getPrevious();
        DoublyLinkedListNode<String> next = curr.getNext();

        for (int k = 0; k < NUMELEMENTS; k++) {

            System.out.println("Testing Index: " + k + " and Current Node: " + curr.getData());

            assertEquals(String.valueOf(k), curr.getData());


            if (k == 0) {

                assertEquals(null, curr.getPrevious());
                assertEquals("1", curr.getNext().getData());

            } else if (k == NUMELEMENTS - 1) {

                assertEquals(String.valueOf(k - 1), curr.getPrevious().getData());
                assertEquals(null, curr.getNext());

            } else {

                assertEquals(String.valueOf(k - 1), curr.getPrevious().getData());
                assertEquals(String.valueOf(k + 1), curr.getNext().getData());

            }

            curr = curr.getNext();

        }

    }

    @Test(timeout = TIMEOUT)
    public void addToBackSizeZero() {

        list.addToBack(str);

        assertEquals(list.getHead(), list.getTail());

    }

    @Test(timeout = TIMEOUT)
    public void addToBackSizeOneAtFront() {

        list.addToBack("0");
        list.addToBack("1");

        assertNotEquals(list.getHead(), list.getTail());
        assertEquals(list.getHead().getData(), "0");
        assertEquals(list.getTail().getData(), "1");

    }


    @Test(timeout = TIMEOUT)
    public void addToBackTest() {

        for (int k = 0; k < NUMELEMENTS; k++) {

            list.addToBack(String.valueOf(k));

        }

        assertEquals(NUMELEMENTS, list.size());

        DoublyLinkedListNode<String> curr = list.getHead();
        DoublyLinkedListNode<String> prev = curr.getPrevious();
        DoublyLinkedListNode<String> next = curr.getNext();

        for (int k = 0; k < NUMELEMENTS; k++) {

            System.out.println("Testing Index: " + k + " and Current Node: " + curr.getData());

            assertEquals(String.valueOf(k), curr.getData());


            if (k == 0) {

                assertEquals(null, curr.getPrevious());
                assertEquals("1", curr.getNext().getData());

            } else if (k == NUMELEMENTS - 1) {

                assertEquals(String.valueOf(k - 1), curr.getPrevious().getData());
                assertEquals(null, curr.getNext());

            } else {

                assertEquals(String.valueOf(k - 1), curr.getPrevious().getData());
                assertEquals(String.valueOf(k + 1), curr.getNext().getData());

            }

            curr = curr.getNext();

        }

    }

    @Test(timeout = TIMEOUT)
    public void removeAtIndexTest() {

        populateList(list, NUMELEMENTS);

        DoublyLinkedListNode<String> curr = list.getHead();

        listToString(list);

        int increments = 0;

        for (int k = 0; k * 2 < NUMELEMENTS - 2; k++) {

            increments++;

            System.out.println("Testing Index: " + k + " and Current Node: " + curr.getData());

            String s = list.removeAtIndex(k);

            listToString(list);
            System.out.println(s);

            assertEquals(s, String.valueOf(k * 2));
            assertEquals(list.size(), NUMELEMENTS - (k + 1));

            curr = curr.getNext().getNext();

        }

    }

    @Test(timeout = TIMEOUT)
    public void removeAtIndexAtSizeOne() {

        list.addToFront(str);
        list.removeAtIndex(0);

        assertEquals(null, list.getHead());
        assertEquals(null, list.getTail());
        assertEquals(0, list.size());

    }

    @Test(timeout = TIMEOUT)
    public void removeAtIndexAtEnd() {

        populateList(list, NUMELEMENTS);

        assertEquals(list.removeAtIndex(list.size() - 1), String.valueOf(NUMELEMENTS - 1));

    }

    @Test(timeout = TIMEOUT)
    public void removeFromFrontTest() {

        populateList(list, NUMELEMENTS);

        DoublyLinkedListNode<String> curr = list.getHead();

        for (int k = 0; k < NUMELEMENTS; k++) {

            String s = list.removeFromFront();

            assertEquals(s, String.valueOf(k));
            assertEquals(list.size(), NUMELEMENTS - (k + 1));

            curr = curr.getNext();

        }

        assertEquals(0, list.size());

    }

    @Test(timeout = TIMEOUT)
    public void removeFrontFrontSizeOne() {

        list.addToFront(str);
        list.removeFromFront();

        assertEquals(null, list.getHead());
        assertEquals(null, list.getTail());
        assertEquals(0, list.size());

    }

    @Test(timeout = TIMEOUT)
    public void removeFromFrontHeadReset() {

        populateList(list, NUMELEMENTS);
        list.addToFront("I Hate my Veggies!");
        list.addToFront(str);
        list.addToFront("I Love Cake!");

        list.removeFromFront();

        assertEquals(list.getHead().getData(), str);
        assertEquals(list.getHead().getNext().getData(), "I Hate my Veggies!");
        assertEquals(list.getHead().getPrevious(), null);

    }

    @Test(timeout = TIMEOUT)
    public void removeAtIndexHeadReset() {

        populateList(list, NUMELEMENTS);
        list.addToFront("I Hate my Veggies!");
        list.addToFront(str);
        list.addToFront("I Love Cake!");

        list.removeAtIndex(0);

        assertEquals(list.getHead().getData(), str);
        assertEquals(list.getHead().getNext().getData(), "I Hate my Veggies!");
        assertEquals(list.getHead().getPrevious(), null);

    }

    @Test(timeout = TIMEOUT)
    public void removeFromBackTest() {

        populateList(list, NUMELEMENTS);

        DoublyLinkedListNode<String> curr = list.getTail();

        for (int k = NUMELEMENTS - 1; k >= 0; k--) {

            String s = list.removeFromBack();

            assertEquals(s, String.valueOf(k));
            assertEquals(list.size(), k);

            curr = curr.getPrevious();

        }

        assertEquals(0, list.size());

    }

    @Test(timeout = TIMEOUT)
    public void removeFromBackTestTailReset() {

        populateList(list, NUMELEMENTS);
        list.addToBack("I Hate my Veggies!");
        list.addToBack(str);
        list.addToBack("I Love Cake!");

        list.removeFromBack();

        assertEquals(list.getTail().getData(), str);
        assertEquals(list.getTail().getPrevious().getData(), "I Hate my Veggies!");
        assertEquals(list.getTail().getNext(), null);

    }

    @Test(timeout = TIMEOUT)
    public void removeAtIndexTestTailReset() {

        populateList(list, NUMELEMENTS);
        list.addToBack("I Hate my Veggies!");
        list.addToBack(str);
        list.addToBack("I Love Cake!");

        list.removeAtIndex(list.size() - 1);

        assertEquals(list.getTail().getData(), str);
        assertEquals(list.getTail().getPrevious().getData(), "I Hate my Veggies!");
        assertEquals(list.getTail().getNext(), null);

    }

    @Test(timeout = TIMEOUT)
    public void removeFromBackSizeOne() {

        list.addToFront(str);
        list.removeFromBack();

        assertEquals(null, list.getHead());
        assertEquals(null, list.getTail());
        assertEquals(0, list.size());

    }

    @Test(timeout = TIMEOUT)
    public void testGet() {

        populateList(list, NUMELEMENTS);

        DoublyLinkedListNode<String> curr = list.getHead();

        for (int k = 0; k < NUMELEMENTS; k++) {

            assertEquals(list.get(k), curr.getData());

            curr = curr.getNext();

        }

    }

    @Test(timeout = TIMEOUT)
    public void testEmpty() {

        populateList(list, NUMELEMENTS);

        DoublyLinkedListNode<String> curr = list.getTail();

        for (int k = NUMELEMENTS - 1; k >= 0; k--) {

            String s = list.removeFromBack();
            curr = curr.getPrevious();

        }
        assertEquals(list.isEmpty(), true);

    }

    @Test(timeout = TIMEOUT)
    public void testClear() {

        populateList(list, NUMELEMENTS * 50);

        list.clear();

        assertEquals(null, list.getHead());
        assertEquals(null, list.getTail());
        assertEquals(0, list.size());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccurenceSizeIsOne() {

        list.addToFront(str);

        assertEquals(str, list.removeLastOccurrence(str));

        assertEquals(null, list.getHead());
        assertEquals(null, list.getTail());
        assertEquals(0, list.size());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccurenceMultiple() {

        list.addToBack("I hate python..");
        list.addToBack(str);
        list.addToBack("I Love Java!");
        list.addToBack(str);
        list.addToBack("I love ice");

        assertEquals(str, list.removeLastOccurrence(str));

        assertEquals(4, list.size());
        assertEquals(str, list.get(1));

    }

    @Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
    public void testRemoveLastOccurenceNotFound() {

        populateList(list, NUMELEMENTS);

        assertNotEquals(String.valueOf(NUMELEMENTS + 1), list.removeLastOccurrence(String.valueOf(NUMELEMENTS + 1)));

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccurenceAtHead() {

        list.addToBack(str);
        list.addToBack("I really, really love ice cream");
        list.addToBack("I hate python..");
        list.addToBack("I Love Java!");

        assertEquals(str, list.removeLastOccurrence(str));
        assertEquals(list.size(), 3);

        assertEquals(list.getHead().getData(), "I really, really love ice cream");
        assertEquals(list.getHead().getNext().getData(), "I hate python..");
        assertEquals(list.getHead().getPrevious(), null);

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveLastOccurenceAtTail() {

        list.addToBack("I really, really love ice cream");
        list.addToBack("I hate python..");
        list.addToBack("I Love Java!");
        list.addToBack(str);

        assertEquals(str, list.removeLastOccurrence(str));
        assertEquals(list.size(), 3);

        assertEquals(list.getTail().getData(), "I Love Java!");
        assertEquals(list.getTail().getPrevious().getData(), "I hate python..");
        assertEquals(list.getTail().getNext(), null);

    }

    @Test(timeout = TIMEOUT)
    public void testToArray() {

        populateList(list, NUMELEMENTS);

        String[] arr = new String[NUMELEMENTS];

        for (int k = 0; k < NUMELEMENTS; k++) {

            arr[k] = String.valueOf(k);

        }

        assertArrayEquals(arr, list.toArray());

    }

    /**
     * helper method to print for debugging
     * @param list list to print
     */
    private void listToString(DoublyLinkedList<String> list) {

        DoublyLinkedListNode<String> current = list.getHead();

        for (int k = 0; k < list.size(); k++) {

            System.out.println("current: " + current.getData());

            current = current.getNext();

        }

    }


}