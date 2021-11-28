
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;

/**
 * @author Mayur Dhepe
 * @author Sushil Khairnar
 * @version Nov 25 2021
 *          binary file handler
 */
public class FileHandler {

    private RandomAccessFile file;

    private long block = 0;
    private long totalBlocks = 0;
    private int blockLength = 0;

    private int recordLength = 0;
    private long totalRecords = 0;

    private long fileLength = 0;
    private long fileOffset = 0;

    /**
     * Constructor
     * 
     * @param inputFile
     *            binary file name
     * @param recordSize
     *            size of record
     * @param blockSize
     *            size of a block
     */
    public FileHandler(String inputFile, int recordSize, int blockSize) {
        try {
            this.file = new RandomAccessFile(inputFile, "rw");
            this.fileLength = file.length();

            this.recordLength = recordSize;
            this.totalRecords = this.fileLength / this.recordLength;

            this.blockLength = blockSize;
            this.totalBlocks = this.fileLength / (this.recordLength
                * this.blockLength);

            if (this.fileLength % (this.recordLength * this.blockLength) != 0) {
                this.totalBlocks += 1;
            }
        }
        catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
    }


    /**
     * Get bin file info
     * 
     * @return return file information
     */
    public long[] getFileInfo() {
        long[] fileInfo = new long[3];
        fileInfo[0] = this.fileLength;
        fileInfo[1] = this.totalRecords;
        fileInfo[2] = this.totalBlocks;
        return fileInfo;
    }


    /**
     * Check if file has a valid record
     * 
     * @param record
     *            to store data
     * @param offset
     *            file offset to read record from
     * @return returns true if its a valid record
     */
    public boolean hasRecord(byte[] record, long offset) {
        try {
            if (offset < this.fileLength) {
                this.file.seek(offset);
                this.file.read(record);
            }
            else {
                return false;
            }
        }
        catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
        return true;
    }


    /**
     * Get next block from file
     * 
     * @param blocks
     *            to store data
     * @return returns the next block
     */
    public boolean hasNextBlock(byte[][] blocks) {
        if (blocks.length != this.blockLength
            || this.totalBlocks == this.block) {
            closeFile();
            return false;
        }
        try {
            for (int i = 0; i < this.blockLength; i++) {
                this.file.seek(fileOffset);
                this.file.read(blocks[i]);
                this.fileOffset = this.file.getFilePointer();
            }
            this.block++;
        }
        catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
        return true;
    }


    /**
     * Get ending offset from file
     * 
     * @return the ending offset
     */
    public long getEndingOffset() {
        return this.fileLength;
    }


    /**
     * Get the block from current offset
     * 
     * @param blocks
     *            to store block of data
     * @param offset
     *            file offset
     * @param length
     *            length of block
     * @return block from given position
     */
    public int getCurrentOffset(byte[][] blocks, long offset, int length) {
        this.fileOffset = offset;
        int count = 0;
        try {
            for (int i = 0; i < length; i++) {
                if (this.recordLength * this.totalRecords < this.fileOffset) {
                    break;
                }

                this.file.seek(fileOffset);
                this.file.read(blocks[i]);
                this.fileOffset = this.file.getFilePointer();
                count++;
            }
            if (this.blockLength == length) {
                this.block++;
            }
        }
        catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
        return count;
    }


    /**
     * Get file offset
     * 
     * @return file offset
     */
    public long getFileOffset() {
        try {
            return this.file.getFilePointer();
        }
        catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
        return -1;
    }


    /**
     * Writes data to output file
     * 
     * @param fileHandler
     *            file object
     * @param offset
     *            offset of file
     * @param length
     *            length of data to write
     */
    public void writeToOutputFile(
        FileHandler fileHandler,
        long offset,
        int length) {
        try {
            byte[] bytes = new byte[length];

            if (fileHandler.hasRecord(bytes, offset)) {
                this.file.write(bytes);
            }

        }
        catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
    }


    /**
     * Write record to bin file
     * 
     * @param record
     *            record
     */
    public void writeRecord(byte[] record) {
        try {
            this.file.write(record);
        }
        catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
    }


    /**
     * Write record to bin file to current offset
     * 
     * @param offset
     *            offset
     * @param record
     *            record
     */
    public void writeRecord(long offset, byte[] record) {
        try {
            this.file.seek(offset);
            this.file.write(record);
        }
        catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
    }


    /**
     * Remove bin file if it exists
     * 
     * @param fileName
     *            filename
     */
    public static void removeFile(String fileName) {
        try {
            Files.deleteIfExists(Paths.get(fileName));
        }
        catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
    }


    /**
     * Closes bin file
     */
    public void closeFile() {
        try {
            this.file.close();
        }
        catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
    }

}
