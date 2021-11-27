package ExternalSorting;

import java.nio.ByteBuffer;

/**
 * @author Mayur Dhepe
 * @author Sushil Khairnar
 * @version Nov 25 2021
 *          Class for Record
 */
public class Record implements Comparable<Record> {

    private byte[] record;

    /**
     * Get id from the record
     * 
     * @return
     */
    public long getID() {
        ByteBuffer b = ByteBuffer.wrap(record);
        return b.getLong();
    }


    /**
     * Get key from the record
     * 
     * @return key
     */
    public double getKey() {
        ByteBuffer b = ByteBuffer.wrap(record);
        return b.getDouble(8);
    }


    /**
     * Constructor
     * 
     * @param recd
     *            record
     */
    public Record(byte[] recd) {
        this.record = recd;
    }


    /**
     * Get the record
     * 
     * @return record
     */
    public byte[] getRecord() {
        return record;
    }


    /**
     * To compare two records based on key
     */
    @Override
    public int compareTo(Record cmpRecd) {
        return Double.compare(this.getKey(), cmpRecd.getKey());
    }


    /**
     * Convert record id and key to string
     */
    public String toString() {
        return this.getID() + " " + this.getKey();
    }

}
