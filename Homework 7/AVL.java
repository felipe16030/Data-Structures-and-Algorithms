import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     * <p>
     * This constructor should initialize an empty AVL.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     * <p>
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        for (T d : data) {
            if (d == null) {
                throw new IllegalArgumentException("Element within collection cannot be null");
            }
            this.add(d);
        }
        this.size = data.size();
    }

    /**
     * Private helper method that updates the height and balance factor of a node based on the height of its children.
     *
     * @param node the current node being processed
     */
    private void update(AVLNode<T> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            node.setHeight(0);
            node.setBalanceFactor(0);
        } else if (node.getLeft() == null) {
            node.setHeight(node.getRight().getHeight() + 1);
            node.setBalanceFactor(-1 - node.getRight().getHeight());
        } else if (node.getRight() == null) {
            node.setHeight(node.getLeft().getHeight() + 1);
            node.setBalanceFactor(node.getLeft().getHeight() + 1);
        } else {
            int maxChildNodeHeight;
            if (node.getRight().getHeight() > node.getLeft().getHeight()) {
                maxChildNodeHeight = node.getRight().getHeight();
            } else {
                maxChildNodeHeight = node.getLeft().getHeight();
            }
            node.setHeight(maxChildNodeHeight + 1);
            node.setBalanceFactor(node.getLeft().getHeight() - node.getRight().getHeight());
        }

    }

    /**
     * Adds the element to the tree.
     * <p>
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     * <p>
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * <p>
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data being added cannot be null");
        }
        this.root = this.addHelper(data, this.root);
    }

    /**
     * Private recursive method that adds to an AVL.
     *
     * @param data the data being added to the AVL
     * @param curr the current node being processed
     * @return an AVL node which eventually propagates down and back up the tree.
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> curr) {
        if (curr == null) {
            AVLNode<T> newNode = new AVLNode<>(data);
            this.update(newNode);
            this.size++;
            return newNode;
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(this.addHelper(data, curr.getLeft()));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(this.addHelper(data, curr.getRight()));
        }
        this.update(curr);
        if (curr.getBalanceFactor() >= 2) {
            if (curr.getLeft().getBalanceFactor() >= 0) {
                return this.rightRotation(curr);
            } else {
                return this.leftRightRotation(curr);
            }
        } else if (curr.getBalanceFactor() <= -2) {
            if (curr.getRight().getBalanceFactor() <= 0) {
                return this.leftRotation(curr);
            } else {
                return this.rightLeftRotation(curr);
            }
        }
        return curr;
    }

    /**
     * Private helper method that performs a right rotation.
     *
     * @param curr the node in which the right rotation is performed
     * @return the new sub "root" node
     */
    private AVLNode<T> rightRotation(AVLNode<T> curr) {
        AVLNode<T> newRoot = curr.getLeft();
        curr.setLeft(newRoot.getRight());

        newRoot.setRight(curr);
        //UPDATE CURR FIRST BECAUSE OTHERWISE HEIGHT OF NEW ROOT WOULD BE WRONG
        this.update(curr);
        this.update(newRoot);

        return newRoot;
    }

    /**
     * Private helper method which performs a left rotation.
     *
     * @param curr the node in which the left rotation is performed
     * @return the new sub "root node"
     */
    private AVLNode<T> leftRotation(AVLNode<T> curr) {
        AVLNode<T> newRoot = curr.getRight();
        curr.setRight(newRoot.getLeft());

        newRoot.setLeft(curr);
        this.update(curr);
        //UPDATE CURR FIRST BECAUSE OTHERWISE HEIGHT OF NEW ROOT WOULD BE WRONG
        this.update(newRoot);

        return newRoot;
    }

    /**
     * Private helper method to perform a right-left rotation on an unbalanced AVL node.
     *
     * @param curr the node in which the rotation is being performed
     * @return the new sub "root" node of the rotation
     */
    private AVLNode<T> rightLeftRotation(AVLNode<T> curr) {
        curr.setRight(this.rightRotation(curr.getRight()));
        return this.leftRotation(curr);
    }

    /**
     * Private helper method to perform a left-right rotation on an unbalanced AVL node.
     *
     * @param curr the node in which the rotation is being performed
     * @return the new sub "root" node of the rotation
     */
    private AVLNode<T> leftRightRotation(AVLNode<T> curr) {
        curr.setLeft(this.leftRotation(curr.getLeft()));
        return this.rightRotation(curr);
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     * <p>
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data being removed cannot be null");
        }
        AVLNode<T> remNode = new AVLNode<>(null);
        this.root = this.removeHelper(data, this.root, remNode);
        this.size--;
        return remNode.getData();
    }

    /**
     * Private helper method that removes the specified data from the AVL.
     *
     * @param data    the data being removed
     * @param curr    the current node being processed
     * @param removed the node containing the removed data
     * @return An AVLNode which eventually propagates back to the root
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> curr, AVLNode<T> removed) {
        if (curr == null) {
            throw new NoSuchElementException("Data was not located in the tree");
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(this.removeHelper(data, curr.getLeft(), removed));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(this.removeHelper(data, curr.getRight(), removed));
        } else {
            removed.setData(curr.getData());
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null) {
                curr = curr.getRight();
            } else if (curr.getRight() == null) {
                curr = curr.getLeft();
            } else {
                curr.setLeft(this.findPredAndUpdate(curr.getLeft(), curr));
            }
        }

        this.update(curr);

        if (curr.getBalanceFactor() >= 2) {
            if (curr.getLeft().getBalanceFactor() >= 0) {
                return this.rightRotation(curr);
            } else {
                return this.leftRightRotation(curr);
            }
        } else if (curr.getBalanceFactor() <= -2) {
            if (curr.getRight().getBalanceFactor() <= 0) {
                return this.leftRotation(curr);
            } else {
                return this.rightLeftRotation(curr);
            }
        }
        return curr;
    }

    /**
     * Private helper method that locates the predecessor node to be removed from the AVL,
     * removes it, and updates all ancestors.
     *
     * @param curr the current node being processed
     * @param root the root node of the predecessor finding process
     * @return a node that propagates to the original root
     */
    private AVLNode<T> findPredAndUpdate(AVLNode<T> curr, AVLNode<T> root) {
        if (curr.getRight() == null) {
            root.setData(curr.getData());
            if (curr.getLeft() != null) {
                return curr.getLeft();
            }
            return null;
        }
        curr.setRight(findPredAndUpdate(curr.getRight(), root));
        this.update(curr);

        if (curr.getBalanceFactor() >= 2) {
            if (curr.getLeft().getBalanceFactor() >= 0) {
                return this.rightRotation(curr);
            } else {
                return this.leftRightRotation(curr);
            }
        } else if (curr.getBalanceFactor() <= -2) {
            if (curr.getRight().getBalanceFactor() <= 0) {
                return this.leftRotation(curr);
            } else {
                return this.rightLeftRotation(curr);
            }
        }
        return curr;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data being retrieved cannot be null");
        }
        return this.getHelper(this.root, data);
    }

    /**
     * Private helper method for retrieving a given piece of data from the AVL.
     *
     * @param curr the current node being processed
     * @param data the data being searched for
     * @return the located data
     */
    private T getHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data was not located in the AVL");
        } else if (curr.getData().compareTo(data) > 0) {
            return getHelper(curr.getLeft(), data);
        } else if (curr.getData().compareTo(data) < 0) {
            return getHelper(curr.getRight(), data);
        } else {
            return curr.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data being retrieved cannot be null");
        }
        return containsHelper(this.root, data);
    }

    /**
     * Private helper method that determines if the AVL contains a certain element.
     *
     * @param curr the node being processed
     * @param data the data being located
     * @return true if the data is in the AVL, false otherwise
     */
    private boolean containsHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else if (curr.getData().compareTo(data) > 0) {
            return containsHelper(curr.getLeft(), data);
        } else if (curr.getData().compareTo(data) < 0) {
            return containsHelper(curr.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (this.size == 0) {
            return -1;
        }
        return (int) (Math.log(this.size) / Math.log(2));
    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     * <p>
     * Should be recursive.
     * <p>
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     * <p>
     * This should NOT be used in the remove method.
     * <p>
     * Ex:
     * Given the following AVL composed of Integers
     * 76
     * /    \
     * 34      90
     * \    /
     * 40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        AVLNode<T> pred = new AVLNode<>(null);
        return predecessorTotalHelper(data, this.root, pred).getData();
    }

    /**
     * Private helper method that determines the predecessor of a given data
     *
     * @param data the data for which its predecessor will be located
     * @param curr the current node being analyzed
     * @param pred dummy node that represents the predecessor in the end
     * @return the predecessor node
     */
    private AVLNode<T> predecessorTotalHelper(T data, AVLNode<T> curr, AVLNode<T> pred) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the AVL");
        } else if (curr.getData().compareTo(data) > 0) {
            return predecessorTotalHelper(data, curr.getLeft(), pred);
        } else if (curr.getData().compareTo(data) < 0) {
            if (curr.getRight() != null && curr.getRight().getData().compareTo(data) >= 0) {
                pred = curr;
            }
            return predecessorTotalHelper(data, curr.getRight(), pred);
        } else {
            if (curr.getLeft() != null) {
                pred = predecessorLeftTreeHelper(curr.getLeft());
            }
        }
        return pred;
    }

    /**
     * Private recursive helper method that finds the predecessor of a node given that its left tree is not empty
     *
     * @param curr the current node being processed
     * @return the predecessor node
     */
    private AVLNode<T> predecessorLeftTreeHelper(AVLNode<T> curr) {
        if (curr.getRight() == null) {
            return curr;
        }
        return predecessorLeftTreeHelper(curr.getRight());
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     * <p>
     * Should be recursive.
     * <p>
     * Must run in O(log n) for all cases.
     * <p>
     * Example
     * Tree:
     * 2
     * /    \
     * 0      3
     * \
     * 1
     * Max Deepest Node:
     * 1 because it is the deepest node
     * <p>
     * Example
     * Tree:
     * 2
     * /    \
     * 0      4
     * \    /
     * 1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        AVLNode<T> deepest = maxDeepestNodeHelper(this.root);
        if (deepest == null) {
            return null;
        }
        return maxDeepestNodeHelper(this.root).getData();
    }

    /**
     * Private helper method that locates the deepest node in the AVL by comparing balance factors
     *
     * @param curr the current node being processed
     * @return an AVL node representing the deepest node in the AVL
     */
    private AVLNode<T> maxDeepestNodeHelper(AVLNode<T> curr) {
        if (curr == null) {
            return null;
        }
        if (curr.getBalanceFactor() >= 1) {
            return maxDeepestNodeHelper(curr.getLeft());
        } else if (curr.getBalanceFactor() <= -1) {
            return maxDeepestNodeHelper(curr.getRight());
        } else {
            if (curr.getRight() != null) {
                return maxDeepestNodeHelper(curr.getRight());
            }
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
    public AVLNode<T> getRoot() {
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
