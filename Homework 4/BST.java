import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
    }

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Collection cannot be null");
        }
        for (T element : data) {
            this.add(element);
        }
    }

    /**
     * Adds the data to the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * The data becomes a leaf in the tree.
     * <p>
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        if (this.size == 0) {
            this.root = new BSTNode<T>(data);
            this.size++;
        } else {
            addHelper(data, this.root);
        }
    }

    /**
     * Recursive helper method to add data to the BST.
     * <p>
     * The data becomes a leaf in the tree
     *
     * @param data the data to be added
     * @param curr the current Node being inspected
     * @return a BSTNode for pointer reinforcement, eventually evaluates to the root
     */
    private BSTNode<T> addHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            this.size++;
            return new BSTNode<T>(data);
        }

        if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(this.addHelper(data, curr.getLeft()));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(this.addHelper(data, curr.getRight()));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        BSTNode<T> dummy = new BSTNode<T>(null);
        this.root = this.removeHelper(data, this.root, dummy);
        this.size--;
        return dummy.getData();
    }

    /**
     * private helper method for removing elements from the BST.
     *
     * @param data the data being removed
     * @param curr the current Node being processed
     * @param dummyNode updated to hold the data from the removed node
     * @return a Node representing the Node removed
     */
    private BSTNode<T> removeHelper(T data, BSTNode<T> curr, BSTNode<T> dummyNode) {
        if (curr == null) {
            throw new NoSuchElementException("data is not in the tree");
        }
        if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(this.removeHelper(data, curr.getLeft(), dummyNode));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(this.removeHelper(data, curr.getRight(), dummyNode));
        } else {
            dummyNode.setData(curr.getData());
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else {
                BSTNode<T> successorNode = this.findSuccessor(curr.getRight());
                this.removeHelper(successorNode.getData(), curr, new BSTNode<T>(null));
                curr.setData(successorNode.getData());
                return curr;
            }
        }
        return curr;
    }

    /**
     * recursive helper method to help find the successor of a given node.
     *
     * @param curr the node being processed
     * @return the successor node
     */
    private BSTNode<T> findSuccessor(BSTNode<T> curr) {
        if (curr.getLeft() == null) {
            return curr;
        }
        return this.findSuccessor(curr.getLeft());
    }

    /**
     * Returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return this.getHelper(data, this.root);
    }

    /**
     * Recursive helper method for performing the get() operation.
     *
     * @param data the data being retrieved
     * @param curr the current node being evaluated
     * @return the located data
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    private T getHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        if (curr.getData().compareTo(data) > 0) {
            return this.getHelper(data, curr.getLeft());
        } else if (curr.getData().compareTo(data) < 0) {
            return this.getHelper(data, curr.getRight());
        } else {
            return curr.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return this.containsHelper(data, this.root);
    }

    /**
     * Recursive helper method for determining whether a BST contains a some data.
     *
     * @param data the data being searched for
     * @param curr the current node being processed
     * @return true if the BST contains the data, false otherwise
     */
    private boolean containsHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            return false;
        }
        if (curr.getData().equals(data)) {
            return true;
        } else if (curr.getData().compareTo(data) > 0) {
            return this.containsHelper(data, curr.getLeft());
        } else {
            return this.containsHelper(data, curr.getRight());
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> preorderTraversal = new ArrayList<T>();
        return this.preorderHelper(preorderTraversal, this.root);
    }

    /**
     * recursive helper method to assemble a preorder traversal of the BST.
     *
     * @param preorderList the list being assembled
     * @param curr         the current node being processed
     * @return the list in containing the nodes in preorder traversal
     */
    private List<T> preorderHelper(List<T> preorderList, BSTNode<T> curr) {
        if (curr == null) {
            return preorderList;
        }

        preorderList.add(curr.getData());
        this.preorderHelper(preorderList, curr.getLeft());
        this.preorderHelper(preorderList, curr.getRight());
        return preorderList;
    }

    /**
     * Generate an in-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> inorderTraversal = new ArrayList<T>();
        return this.inorderHelper(inorderTraversal, this.root);
    }

    /**
     * recursive helper method to assemble an inorder traversal of the BST.
     *
     * @param inorderList the list being assembled
     * @param curr        the current node being processed
     * @return the list in containing the nodes in inorder traversal
     */
    private List<T> inorderHelper(List<T> inorderList, BSTNode<T> curr) {
        if (curr == null) {
            return inorderList;
        }

        this.inorderHelper(inorderList, curr.getLeft());
        inorderList.add(curr.getData());
        this.inorderHelper(inorderList, curr.getRight());
        return inorderList;
    }

    /**
     * Generate a post-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> postorderTraversal = new ArrayList<T>();
        return this.postorderHelper(postorderTraversal, this.root);
    }

    /**
     * recursive helper method to assemble a postorder traversal of the BST.
     *
     * @param postorderList the list being assembled
     * @param curr          the current node being processed
     * @return the list in containing the nodes in postorder traversal
     */
    private List<T> postorderHelper(List<T> postorderList, BSTNode<T> curr) {
        if (curr == null) {
            return postorderList;
        }

        this.postorderHelper(postorderList, curr.getLeft());
        this.postorderHelper(postorderList, curr.getRight());
        postorderList.add(curr.getData());
        return postorderList;
    }

    /**
     * Generate a level-order traversal of the tree.
     * <p>
     * This does not need to be done recursively.
     * <p>
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     * <p>
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> levelorderHelper = new LinkedList<BSTNode<T>>();
        List<T> levelorderTraversal = new ArrayList<T>();
        if (this.size == 0) {
            return levelorderTraversal;
        }
        levelorderHelper.add(this.root);
        while (!levelorderHelper.isEmpty()) {
            BSTNode<T> curr = levelorderHelper.remove();
            if (curr.getLeft() != null) {
                levelorderHelper.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                levelorderHelper.add(curr.getRight());
            }
            levelorderTraversal.add(curr.getData());
        }
        return levelorderTraversal;
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     * <p>
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return this.heightHelper(this.root);
    }

    /**
     * Recursive helper method for obtaining the height.
     *
     * @param curr the current node being processed
     * @return an int representing the height of the current node.
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr.getLeft() == null && curr.getRight() == null) {
            return 0;
        }
        int leftHeight = 0;
        int rightHeight = 0;
        if (curr.getLeft() != null) {
            leftHeight = heightHelper(curr.getLeft()) + 1;
        }
        if (curr.getRight() != null) {
            rightHeight = heightHelper(curr.getRight()) + 1;
        }
        return leftHeight > rightHeight ? leftHeight : rightHeight;
    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     * <p>
     * This must be done recursively.
     * <p>
     * A good way to start is by finding the deepest common ancestor (DCA) of both data
     * and add it to the list. You will most likely have to split off and
     * traverse the tree for each piece of data adding to the list in such a
     * way that it will return the path in the correct order without requiring any
     * list manipulation later. One way to accomplish this (after adding the DCA
     * to the list) is to then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list.
     * <p>
     * Please note that there is no relationship between the data parameters
     * in that they may not belong to the same branch.
     * <p>
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use considering the Big-O efficiency of the list
     * operations.
     * <p>
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     * <p>
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     * <p>
     * Ex:
     * Given the following BST composed of Integers
     * 50
     * /        \
     * 25         75
     * /    \
     * 12    37
     * /  \    \
     * 11   15   40
     * /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     * <p>
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        if (data1.equals(data2) && this.contains(data1)) {
            LinkedList<T> pathList = new LinkedList<>();
            pathList.add(data1);
            return pathList;
        }
        BSTNode<T> commonAncestor = this.findAncestor(data1, data2, this.root);
        LinkedList<T> pathList = new LinkedList<>();
        if (commonAncestor.getData().equals(data1)) {
            pathList.addFirst(commonAncestor.getData());
            if (commonAncestor.getData().compareTo(data2) > 0) {
                this.assembleBackList(commonAncestor.getLeft(), pathList, data2);
            } else {
                this.assembleBackList(commonAncestor.getRight(), pathList, data2);
            }
        } else if (commonAncestor.getData().equals(data2)) {
            pathList.addFirst(commonAncestor.getData());
            if (commonAncestor.getData().compareTo(data1) > 0) {
                this.assembleFrontList(commonAncestor.getLeft(), pathList, data1);
            } else {
                this.assembleFrontList(commonAncestor.getRight(), pathList, data1);
            }
        } else {
            pathList.addFirst(commonAncestor.getData());
            if (commonAncestor.getData().compareTo(data1) > 0) {
                this.assembleFrontList(commonAncestor.getLeft(), pathList, data1);
            } else {
                this.assembleFrontList(commonAncestor.getRight(), pathList, data1);
            }
            if (commonAncestor.getData().compareTo(data2) > 0) {
                this.assembleBackList(commonAncestor.getLeft(), pathList, data2);
            } else {
                this.assembleBackList(commonAncestor.getRight(), pathList, data2);
            }
        }
        return pathList;

    }

    /**
     * private helper that assembles the front of the pathList between 2 data points
     *
     * @param curr     the current Node being processed
     * @param pathList the list containing the path
     * @param data     the beginning of the path
     */
    private void assembleFrontList(BSTNode<T> curr, LinkedList<T> pathList, T data) {
        if (curr == null) {
            return;
        }
        pathList.addFirst(curr.getData());
        if (curr.getData().compareTo(data) > 0) {
            this.assembleFrontList(curr.getLeft(), pathList, data);
        } else if (curr.getData().compareTo(data) < 0) {
            this.assembleFrontList(curr.getRight(), pathList, data);
        }
    }

    /**
     * private helper that assembles the back of the pathList between 2 data points
     *
     * @param curr     the current Node being processed
     * @param pathList the list containing the path
     * @param data     the end of the path
     */
    private void assembleBackList(BSTNode<T> curr, LinkedList<T> pathList, T data) {
        if (curr == null) {
            return;
        }
        pathList.addLast(curr.getData());
        if (curr.getData().compareTo(data) > 0) {
            this.assembleBackList(curr.getLeft(), pathList, data);
        } else if (curr.getData().compareTo(data) < 0) {
            this.assembleBackList(curr.getRight(), pathList, data);
        }
    }

    /**
     * Recursive helper method that locates the most recent common ancestor of data1 and data2.
     *
     * @param data1 the first piece of data
     * @param data2 the second piece of data
     * @param curr  the current Node being evaluated
     * @return the most recent common ancestor
     */
    private BSTNode<T> findAncestor(T data1, T data2, BSTNode<T> curr) {
        if (curr == null || (curr.getRight() == null && curr.getLeft() == null)) {
            throw new NoSuchElementException("Either data1 or data2 is not in the BST");
        }
        if (curr.getData().compareTo(data1) > 0 && curr.getData().compareTo(data2) > 0) {
            return findAncestor(data1, data2, curr.getLeft());
        } else if (curr.getData().compareTo(data1) < 0 && curr.getData().compareTo(data2) < 0) {
            return findAncestor(data1, data2, curr.getRight());
        } else {
            return curr;
        }
    }

    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
