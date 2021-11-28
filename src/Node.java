
/**
 * /**
 * 
 * @author Mayur Dhepe
 * @author Sushil Khairnar
 * @version Nov 25 2021
 *          Class for Node of a RunManager (Linked list)
 * @param <T>
 *            generic
 */
public class Node<T> {
    private T nodeVal;
    private Node<T> next;

    /**
     * Constructor
     * 
     * @param recd
     *            record
     */
    public Node(T recd) {
        this.nodeVal = recd;
        this.next = null;
    }


    /**
     * Get record from the node
     * 
     * @return value
     */
    public T getValue() {
        return this.nodeVal;
    }


    /**
     * Get next node
     * 
     * @return next node
     */
    public Node<T> getNext() {
        return this.next;
    }


    /**
     * Set next node
     * 
     * @param next
     *            the next node
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }
}
