package ExternalSorting;

/**
 * @author Mayur Dhepe
 * @author Sushil Khairnar
 * @version Nov 25 2021
 *          Helper Class to store information about the run
 */
public class RunHelper {

    private static int recordsPerBlock = 512;
    public long start = 0;
    public int length = 0;
    public int numberOfBlocks = 0;

    /**
     * Constructor
     * 
     * @param start
     *            start of run
     * @param length
     *            length of run
     */
    public RunHelper(long start, int length) {
        this.start = start;
        this.length = length;
        this.numberOfBlocks = length / recordsPerBlock;
        if (length % recordsPerBlock != 0) {
            this.numberOfBlocks += 1;
        }
    }
}
