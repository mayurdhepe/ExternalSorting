
/**
 * @author Mayur Dhepe
 * @author Sushil Khairnar
 * @version Nov 25 2021
 *          Helper Class to store information about the run
 */

public class RunHelper {

    private static int recordsPerBlock = 512;
    private long start = 0;
    private int length = 0;
    private int numberOfBlocks = 0;

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


    /**
     * get Records Per Block
     * 
     * @return Records Per Block
     */
    public static int getRecordsPerBlock() {
        return recordsPerBlock;
    }


    /**
     * set Records Per Block
     * 
     * @param recordsPerBlock
     *            Records Per Block
     */
    public static void setRecordsPerBlock(int recordsPerBlock) {
        RunHelper.recordsPerBlock = recordsPerBlock;
    }


    /**
     * get start
     * 
     * @return start
     */
    public long getStart() {
        return start;
    }


    /**
     * set start
     * 
     * @param start
     *            start
     */
    public void setStart(long start) {
        this.start = start;
    }


    /**
     * get length
     * 
     * @return length
     */
    public int getLength() {
        return length;
    }


    /**
     * set length
     * 
     * @param length
     *            length
     */
    public void setLength(int length) {
        this.length = length;
    }


    /**
     * get numberOfBlocks
     * 
     * @return number Of Blocks
     */
    public int getNumberOfBlocks() {
        return numberOfBlocks;
    }


    /**
     * set number Of Blocks
     * 
     * @param numberOfBlocks
     *            number Of Blocks
     */
    public void setNumberOfBlocks(int numberOfBlocks) {
        this.numberOfBlocks = numberOfBlocks;
    }
}
