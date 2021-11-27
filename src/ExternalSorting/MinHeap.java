package ExternalSorting;

/**
 * /**
 * 
 * @author Mayur Dhepe
 * @author Sushil Khairnar
 * @version Nov 25 2021
 *          Class to perform MinHeap operations
 * @param <T>
 */
public class MinHeap<T extends Comparable<T>> {

    private T[] minHeap;
    private int sz = 0;

    /**
     * Constructor
     * 
     * @param heapArray
     *            stores heap
     * @param size
     *            heap size
     */
    public MinHeap(T[] heapArray, int size) {
        this.minHeap = heapArray;
        this.sz = size;
        createHeap();
    }


    /**
     * Checks if node is leaf
     * 
     * @param position
     *            position of node
     * @return if leaf of not
     */
    private boolean isLeaf(int position) {
        return (this.sz > position) && (position >= this.sz / 2);
    }


    /**
     * Get left child of a node
     * 
     * @param position
     *            position of the node to get the leftchild of
     * @return non -ve value of left child exists
     */
    private int getLeftChild(int position) {
        if (position >= this.sz / 2) {
            return -1;
        }
        return 1 + position * 2;
    }


    /**
     * Get right child of a node
     * 
     * @param position
     *            position of the node to get the rightchild of
     * @return non -ve if child exists
     */
    private int getRightChild(int position) {
        if (position >= (this.sz - 1) / 2) {
            return -1;
        }
        return 2 + position * 2;
    }


    /**
     * Set heap size
     * 
     * @param sz
     *            size to be set
     */
    public void setSize(int sz) {
        this.sz = sz;
    }


    /**
     * Swap nodes at given positions
     * 
     * @param pos1
     *            node 1 position
     * @param pos2
     *            node 2 position
     */
    private void swapHeapNodes(int pos1, int pos2) {
        T tmpNode = this.minHeap[pos1];
        this.minHeap[pos1] = this.minHeap[pos2];
        this.minHeap[pos2] = tmpNode;
    }


    /**
     * Applies heapify procedure from given position
     * 
     * @param position
     *            given position
     */
    private void heapify(int position) {
        while (!isLeaf(position)) {

            int right = getRightChild(position);
            int left = getLeftChild(position);

            if (((this.sz - 1) > left) && (0 < this.minHeap[left].compareTo(
                this.minHeap[right]))) {
                left = right;
            }
            if (0 >= this.minHeap[position].compareTo(this.minHeap[left])) {
                return;
            }
            swapHeapNodes(position, left);
            position = left;
        }
    }


    /**
     * Creates heap by calling heapify
     */
    public void createHeap() {
        for (int position = this.sz / 2 - 1; position >= 0; position--) {
            heapify(position);
        }
    }


    /**
     * Insert item to heap
     * 
     * @param item
     *            given item
     */
    public void insert(T item) {
        if (isEmpty()) {
            return;
        }

        this.minHeap[0] = item;
        heapify(0);
    }


    /**
     * Remove minimum element
     * 
     * @return min element
     */
    public T removeMin() {
        if (this.sz == 0) {
            return null;
        }

        swapHeapNodes(0, --this.sz);
        if (this.sz > 0) {
            heapify(0);
        }
        return this.minHeap[this.sz];
    }


    /**
     * Checks if heap is empty
     * 
     * @return if empty or not
     */
    public boolean isEmpty() {
        return (this.sz == 0);
    }


    /**
     * Replace root with given item
     * 
     * @param item
     *            given item
     */
    public void replaceRoot(T item) {
        if (isEmpty()) {
            return;
        }

        swapHeapNodes(0, --this.sz);
        this.minHeap[this.sz] = item;

        if (this.sz > 0) {
            heapify(0);
        }
    }


    /**
     * Get top of the heap
     * 
     * @return top if exists
     */
    public T getTop() {
        if (!isEmpty()) {
            return this.minHeap[0];
        }
        else {
            return null;
        }
    }
}
