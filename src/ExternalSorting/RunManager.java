package ExternalSorting;

/**
 * @author Mayur Dhepe
 * @author Sushil Khairnar
 * @version Nov 25 2021
 *          Class for Run Manager - a linked list of runs
 * @param <T>
 *            generic
 */
public class RunManager<T> {

    private Node<T> head, tail;

    /**
     * Constructor
     */
    public RunManager() {

        this.head = new Node<T>(null);
        this.tail = this.head;

    }


    /**
     * Insert record to the node
     * 
     * @param record
     *            given record
     */
    public void insert(T record) {
        Node<T> node = new Node<T>(record);
        Node<T> currNode;
        if (this.head.getNext() == null) {
            this.head.setNext(node);
            this.tail = node;
        }
        else {
            currNode = this.tail;
            currNode.setNext(node);
            this.tail = node;
        }
    }


    /**
     * Get length of the run manager
     * 
     * @return length
     */
    public int getLength() {

        Node<T> currNode = this.head.getNext();
        int sz = 0;
        while (currNode != null) {
            sz++;
            currNode = currNode.getNext();
        }
        return sz;
    }


    /**
     * Checks if list is empty
     * 
     * @return 1 if empty
     */
    public boolean isEmpty() {
        return this.tail == this.head;

    }


    /**
     * Get the record and deletes it from the list
     * 
     * @return record
     */
    public T getAndDelete() {
        Node<T> first = this.head.getNext(), tmpNode;

        if (first != null) {
            tmpNode = first.getNext();
            this.head.setNext(tmpNode);

            if (this.tail == first) {
                this.tail = this.head;
            }

            return first.getValue();
        }
        else {
            return null;
        }
    }

}
