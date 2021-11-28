
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

    private T[] heap;
    private int size = 0;

    /**
     * Constructor
     * 
     * @param heap
     *            stores heap
     * @param size
     *            heap size
     */
    public MinHeap(T[] heap, int size) {
        this.heap = heap;
        this.size = size;
        buildHeap();
    }


    /**
     * Get left child of a node
     * 
     * @param index
     *            index of the node
     * @return left child
     */
    private int getLeftChild(int index) {
        return 1 + index * 2;
    }


    /**
     * Get right child of a node
     * 
     * @param index
     *            index of the node
     * @return right child
     */
    private int getRightChild(int index) {
        return 2 + index * 2;
    }


    /**
     * Set size of heap
     * 
     * @param size
     *            size of Heap
     */
    public void setSize(int size) {
        this.size = size;
    }


    /**
     * Swap nodes at given indices
     * 
     * @param idx1
     *            node 1 index
     * @param idx2
     *            node 2 index
     */
    private void swapData(int idx1, int idx2) {
        T temp = this.heap[idx1];
        this.heap[idx1] = this.heap[idx2];
        this.heap[idx2] = temp;
    }


    /**
     * Method to Heapify
     * 
     * @param index
     *            given index
     */
    private void heapify(int index) {

        int leftChild = getLeftChild(index);
        int rightChild = getRightChild(index);

        int smallest = index;

        if (leftChild < this.size && this.heap[leftChild].compareTo(
            this.heap[smallest]) < 0) {
            smallest = leftChild;
        }

        if (rightChild < this.size && this.heap[rightChild].compareTo(
            this.heap[smallest]) < 0) {
            smallest = rightChild;
        }

        if (smallest != index) {
            swapData(index, smallest);
            heapify(smallest);
        }

    }


    /**
     * Builds a heap
     */
    public void buildHeap() {
        for (int idx = this.size / 2 - 1; idx >= 0; idx--) {
            heapify(idx);
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

        this.heap[0] = item;
        heapify(0);
    }


    /**
     * Remove minimum element
     * 
     * @return min element
     */
    public T removeMin() {
        if (this.size == 0) {
            return null;
        }

        swapData(0, --this.size);
        if (this.size > 0) {
            heapify(0);
        }
        return this.heap[this.size];
    }


    /**
     * Checks if a heap is empty or not
     * 
     * @return if empty or not
     */
    public boolean isEmpty() {
        return (this.size == 0);
    }


    /**
     * Replace root with given node
     * 
     * @param node
     *            given node
     */
    public void replaceRoot(T node) {
        if (isEmpty()) {
            return;
        }

        swapData(0, --this.size);
        this.heap[this.size] = node;

        if (this.size > 0) {
            heapify(0);
        }
    }


    /**
     * Get top element
     * 
     * @return top
     */
    public T getTop() {
        if (!isEmpty()) {
            return this.heap[0];
        }
        else {
            return null;
        }
    }
}
