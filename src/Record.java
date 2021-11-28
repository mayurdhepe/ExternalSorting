
import java.nio.ByteBuffer;

/**
 * @author Mayur Dhepe
 * @author Sushil Khairnar
 * @version Nov 25 2021
 *          Class for Record
 */
public class Record implements Comparable<Record> {

    private byte[] recrd;

    /**
     * Get id from the record
     * 
     * @return ID
     */
    public long getID() {
        ByteBuffer b = ByteBuffer.wrap(recrd);
        return b.getLong();
    }


    /**
     * Get key from the record
     * 
     * @return key
     */
    public double getKey() {
        ByteBuffer b = ByteBuffer.wrap(recrd);
        return b.getDouble(8);
    }


    /**
     * Constructor
     * 
     * @param rec
     *            record
     */
    public Record(byte[] rec) {
        this.recrd = rec;
    }


    /**
     * Get the record
     * 
     * @return record
     */
    public byte[] getRecord() {
        return recrd;
    }


    /**
     * To compare two records based on key
     */
    @Override
    public int compareTo(Record rec) {
        return Double.compare(this.getKey(), rec.getKey());
    }


    /**
     * Convert record id and key to string
     * 
     * @return string converted record ID and key
     */
    public String toString() {
        return this.getID() + " " + this.getKey();
    }

}
