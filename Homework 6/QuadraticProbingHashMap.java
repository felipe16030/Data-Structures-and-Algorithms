import java.util.NoSuchElementException;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * Your implementation of a QuadraticProbingHashMap.
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
public class QuadraticProbingHashMap<K, V> {

    /**
     * The initial capacity of the QuadraticProbingHashMap when created with the
     * default constructor.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the QuadraticProbingHashMap
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    private static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private QuadraticProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new QuadraticProbingHashMap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * <p>
     * Use constructor chaining.
     */
    public QuadraticProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new QuadraticProbingHashMap.
     * <p>
     * The backing array should have an initial capacity of initialCapacity.
     * <p>
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public QuadraticProbingHashMap(int initialCapacity) {
        this.table = (QuadraticProbingMapEntry<K, V>[]) new QuadraticProbingMapEntry[initialCapacity];
        this.size = 0;
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     * <p>
     * In the case of a collision, use quadratic probing as your resolution
     * strategy.
     * <p>
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     * <p>
     * You must also resize when there are not any valid spots to add a
     * (key, value) pair in the HashMap after checking table.length spots.
     * There is more information regarding this case in the assignment PDF.
     * <p>
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     * <p>
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Neither the key nor value can be null");
        }
        if (new Double(this.size + 1) / this.table.length > MAX_LOAD_FACTOR) {
            this.resizeBackingTable((this.table.length * 2) + 1);
        }
        int indexToProbe;
        int firstRemovedIndex = -1;
        for (int i = 0; i < this.table.length; i++) {
            if (key.hashCode() < 0) {
                int index = Math.abs(key.hashCode() % this.table.length);
                indexToProbe = Math.abs((int) (index + Math.pow(i, 2)) % this.table.length);
            } else {
                indexToProbe = Math.abs((int) (key.hashCode() + Math.pow(i, 2)) % this.table.length);
            }
            if (this.table[indexToProbe] == null) {
                if (firstRemovedIndex == -1) {
                    this.table[indexToProbe] = new QuadraticProbingMapEntry<>(key, value);
                    this.size++;
                    return null;
                } else {
                    this.table[firstRemovedIndex] = new QuadraticProbingMapEntry<>(key, value);
                    this.size++;
                    return null;
                }
            } else {
                if (this.table[indexToProbe].isRemoved()) {
                    if (firstRemovedIndex == -1) {
                        firstRemovedIndex = indexToProbe;
                    }
                } else if (this.table[indexToProbe].getKey().equals(key)) {
                    V removed = this.table[indexToProbe].getValue();
                    this.table[indexToProbe].setValue(value);
                    return removed;
                }
            }
        }
        if (firstRemovedIndex != -1) {
            this.table[firstRemovedIndex] = new QuadraticProbingMapEntry<>(key, value);
            this.size++;
            return null;
        }
        this.resizeBackingTable((this.table.length * 2) + 1);
        return this.put(key, value);
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        int indexToProbe;
        for (int i = 0; i < this.table.length; i++) {
            if (key.hashCode() < 0) {
                int index = Math.abs(key.hashCode() % this.table.length);
                indexToProbe = Math.abs((int) (index + Math.pow(i, 2)) % this.table.length);
            } else {
                indexToProbe = Math.abs((int) (key.hashCode() + Math.pow(i, 2)) % this.table.length);
            }
            if (this.table[indexToProbe] == null) {
                throw new NoSuchElementException("Key is not in the map.");
            } else {
                if (this.table[indexToProbe].getKey().equals(key) && !this.table[indexToProbe].isRemoved()) {
                    V removed = this.table[indexToProbe].getValue();
                    this.table[indexToProbe].setRemoved(true);
                    this.size--;
                    return removed;
                }
            }
        }
        throw new NoSuchElementException("Key is not in the map.");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        int indexToProbe;
        for (int i = 0; i < this.table.length; i++) {
            if (key.hashCode() < 0) {
                int index = Math.abs(key.hashCode() % this.table.length);
                indexToProbe = Math.abs((int) (index + Math.pow(i, 2)) % this.table.length);
            } else {
                indexToProbe = Math.abs((int) (key.hashCode() + Math.pow(i, 2)) % this.table.length);
            }
            if (this.table[indexToProbe] == null) {
                throw new NoSuchElementException("Key is not in the map.");
            } else {
                if (this.table[indexToProbe].getKey().equals(key) && !this.table[indexToProbe].isRemoved()) {
                    return this.table[indexToProbe].getValue();
                }
            }
        }
        throw new NoSuchElementException("Key is not in the map.");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        int indexToProbe;
        for (int i = 0; i < this.table.length; i++) {
            if (key.hashCode() < 0) {
                int index = Math.abs(key.hashCode() % this.table.length);
                indexToProbe = Math.abs((int) (index + Math.pow(i, 2)) % this.table.length);
            } else {
                indexToProbe = Math.abs((int) (key.hashCode() + Math.pow(i, 2)) % this.table.length);
            }
            if (this.table[indexToProbe] == null) {
                return false;
            } else {
                if (this.table[indexToProbe].getKey().equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * <p>
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        HashSet<K> res = new HashSet<>();
        for (int i = 0; i < this.table.length; i++) {
            if (this.table[i] != null && !this.table[i].isRemoved()) {
                res.add(this.table[i].getKey());
            }
        }
        return res;
    }

    /**
     * Returns a List view of the values contained in this map.
     * <p>
     * Use java.util.ArrayList or java.util.LinkedList.
     * <p>
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        ArrayList<V> res = new ArrayList<>();
        for (int i = 0; i < this.table.length; i++) {
            if (this.table[i] != null && !this.table[i].isRemoved()) {
                res.add(this.table[i].getValue());
            }
        }
        return res;
    }

    /**
     * Resize the backing table to length.
     * <p>
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     * <p>
     * Note: This method does not have to handle the case where the new length
     * results in collisions that cannot be resolved without resizing again. It
     * also does not have to handle the case where size = 0, and length = 0 is
     * passed into the function.
     * <p>
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     * <p>
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     * <p>
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash map
     */
    public void resizeBackingTable(int length) {
        if (length < this.size) {
            throw new IllegalArgumentException("length cannot be less than the number of items in the hash map.");
        }
        QuadraticProbingMapEntry<K, V>[] oldTable = this.table;
        this.table = (QuadraticProbingMapEntry<K, V>[]) new QuadraticProbingMapEntry[length];
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null && !oldTable[i].isRemoved()) {
                this.resizeHelper(oldTable[i]);
            }
        }
    }

    /**
     * Recursive helper method for resize that puts Key Value pairings into the new backing table.
     *
     * @param information the Key Value pair currently being inserted in the table
     */
    private void resizeHelper(QuadraticProbingMapEntry<K, V> information) {
        int indexToProbe;
        for (int i = 0; i < this.table.length; i++) {
            if (information.getKey().hashCode() < 0) {
                int index = Math.abs(information.getKey().hashCode() % this.table.length);
                indexToProbe = Math.abs((int) (index + Math.pow(i, 2)) % this.table.length);
            } else {
                indexToProbe = Math.abs((int) (information.getKey().hashCode() + Math.pow(i, 2)) % this.table.length);
            }
            if (this.table[indexToProbe] == null) {
                this.table[indexToProbe] = information;
                break;
            }
        }
    }

    /**
     * Clears the map.
     * <p>
     * Resets the table to a new array of the INITIAL_CAPACITY and resets the
     * size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        this.size = 0;
        this.table = (QuadraticProbingMapEntry<K, V>[]) new QuadraticProbingMapEntry[INITIAL_CAPACITY];
    }

    /**
     * Returns the table of the map.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public QuadraticProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
