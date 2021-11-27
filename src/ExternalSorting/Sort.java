package ExternalSorting;

/**
 ** @author Mayur Dhepe
 * @author Sushil Khairnar
 * @version Nov 25 2021
 *          Class to perform replacement-selection and n-way merge logic
 */
public class Sort {

    private static int recordsPerBlock = 512;
    private static int recordSize = 16;
    private static int minHeapSize = 8;

    private Record[] input;
    private Record[] output;
    private Record[] ramSize;
    private FileHandler runFile;
    private FileHandler pointer;
    private String runFileName = "./Data/runFile";
    private RunManager<RunHelper> runManager;

    /**
     * Constructor
     * 
     * @param binFile
     *            file to read data from
     */
    public Sort(String binFile) {
        this.runManager = new RunManager<RunHelper>();
        this.input = new Record[this.recordsPerBlock];
        this.output = new Record[this.recordsPerBlock];
        this.ramSize = new Record[this.minHeapSize * this.recordsPerBlock];
        this.pointer = new FileHandler(binFile, this.recordSize,
            this.recordsPerBlock);
    }


    /**
     * To enumerate input block
     * 
     * @param srcFile
     *            read data from this file
     * @param offset
     *            file offset
     * @param blockLength
     *            block size
     * @return number of records
     */
    private int getBlockFromFile(
        FileHandler srcFile,
        long offset,
        int blockLength) {

        byte[][] block = new byte[this.recordsPerBlock][this.recordSize];
        int numRecords = 0;

        if ((this.input != null) && (srcFile != null)) {
            numRecords = srcFile.getCurrentOffset(block, offset, blockLength);
        }

        if (numRecords != 0) {
            for (int i = 0; i < numRecords; i++) {
                this.input[i] = new Record(block[i]);
            }
        }
        return numRecords;
    }


    /**
     * Read data into input block from the file
     * 
     * @param srcFile
     *            read data from this file
     * @return status
     */
    private boolean insertIntoInputBlock(FileHandler srcFile) {

        byte[][] block = new byte[this.recordsPerBlock][this.recordSize];

        boolean hasNext = false;
        if ((srcFile != null) && (this.input != null)) {
            hasNext = srcFile.hasNextBlock(block);
        }
        if (hasNext) {
            for (int i = 0; i < this.recordsPerBlock; i++) {
                this.input[i] = new Record(block[i]);
            }
        }
        return hasNext;
    }


    /**
     * Find and return minimum index from records
     * 
     * @param records
     *            array of records
     * @return minimum index
     */
    private int findMinimumIndex(Record[] records) {

        int mimimumIndex = 0;
        Record minRecord = records[0];

        for (int i = 0; i < records.length; i++) {
            if (records[i] != null) {
                mimimumIndex = i;
                minRecord = records[i];
                break;
            }
        }

        for (int j = mimimumIndex + 1; j < records.length; j++) {

            if ((records[j] != null) && (minRecord.compareTo(records[j]) > 0)) {
                mimimumIndex = j;
                minRecord = records[j];
            }
        }
        return mimimumIndex;
    }


    /**
     * Replacement selection logic
     */
    public void replacementSelection() {

        int lengthOfRun = 0;
        boolean insertStatus = true;
        int blockPointer = this.input.length;
        long offset = 0;
        int outputPointer = 0;
        int nextElements = 0;
        FileHandler.removeFile(this.runFileName);
        this.runFile = new FileHandler(this.runFileName, this.recordSize,
            this.recordsPerBlock);

        for (int i = 0; i < this.minHeapSize; i++) {
            insertIntoInputBlock(this.pointer);
            System.arraycopy(this.input, 0, this.ramSize, i
                * this.recordsPerBlock, this.recordsPerBlock);
        }

        MinHeap<Record> minHeap = new MinHeap<Record>(this.ramSize,
            this.ramSize.length);
        do {
            while (!minHeap.isEmpty()) {
                lengthOfRun++;
                if (outputPointer == this.output.length) {
                    for (int i = 0; i < this.output.length; i++) {
                        this.runFile.writeRecord(this.output[i].getRecord());
                    }
                    outputPointer = 0;
                }
                if (blockPointer == this.input.length) {
                    insertStatus = insertIntoInputBlock(this.pointer);
                    blockPointer = 0;
                }
                if (!insertStatus) {
                    this.output[outputPointer] = minHeap.removeMin();
                }
                else {
                    this.output[outputPointer] = minHeap.getTop();
                    if (this.input[blockPointer].compareTo(
                        this.output[outputPointer]) <= 0) {
                        minHeap.replaceRoot(this.input[blockPointer]);
                        nextElements++;
                    }
                    else {
                        minHeap.insert(this.input[blockPointer]);
                    }
                    blockPointer++;
                }
                outputPointer++;
            }
            if (outputPointer > 0) {
                for (int idx = 0; idx < outputPointer; idx++) {
                    this.runFile.writeRecord(this.output[idx].getRecord());
                }
                outputPointer = 0;
            }

            this.runManager.insert(new RunHelper(offset, lengthOfRun));

            offset = this.runFile.getFileOffset();
            if (nextElements != 0) {
                System.arraycopy(this.ramSize, this.ramSize.length
                    - nextElements, this.ramSize, 0, nextElements);
                minHeap.setSize(nextElements);
                minHeap.createHeap();
                nextElements = 0;
                lengthOfRun = 0;
            }
        }
        while (!minHeap.isEmpty());
        this.runFile.closeFile();
    }


    /**
     * Logic to merge the runs
     * 
     * @param runHelper
     *            details of run
     * @param numberOfRuns
     *            number of runs
     */
    private void merge(RunHelper[] runHelper, int numberOfRuns) {

        FileHandler fileHandler = new FileHandler(this.runFileName,
            this.recordSize, this.recordsPerBlock);

        int readCount = 0;
        int recordLength = 0;
        int[] blockPointer = new int[numberOfRuns];
        Record[] runRecords = new Record[numberOfRuns];
        int[] recordCount = new int[numberOfRuns];
        int blockLength = 0;
        long offSetBeforeCurrent = fileHandler.getEndingOffset();
        long tempOffSet = offSetBeforeCurrent;

        for (int i = 0; i < numberOfRuns; i++) {

            if (runHelper[i].numberOfBlocks > 1) {
                blockLength = this.recordsPerBlock;
            }
            else {
                blockLength = runHelper[i].length;
            }

            readCount = getBlockFromFile(fileHandler, runHelper[i].start,
                blockLength);

            System.arraycopy(this.input, 0, this.ramSize, i
                * this.recordsPerBlock, readCount);

            recordLength += runHelper[i].length;
            runHelper[i].start = fileHandler.getFileOffset();
            runHelper[i].length -= readCount;
            runHelper[i].numberOfBlocks -= 1;
            recordCount[i] = readCount;
            blockPointer[i] = 0;
            runRecords[i] = this.ramSize[i * this.recordsPerBlock];
        }

        int runLength = recordLength;
        int outputPointer = 0;
        while (recordLength > 0) {
            if (outputPointer == this.output.length) {
                for (int i = 0; i < this.output.length; i++) {
                    fileHandler.writeRecord(tempOffSet, this.output[i]
                        .getRecord());
                    tempOffSet += this.recordSize;
                }
                outputPointer = 0;
            }

            int minIndex = findMinimumIndex(runRecords);
            this.output[outputPointer] = runRecords[minIndex];
            outputPointer++;
            blockPointer[minIndex] = blockPointer[minIndex] + 1;
            for (int i = 0; i < numberOfRuns; i++) {
                if ((blockPointer[i] == recordCount[i])
                    && (runHelper[i].numberOfBlocks > 0)) {

                    if (runHelper[i].numberOfBlocks > 1) {
                        blockLength = this.recordsPerBlock;
                    }
                    else {
                        blockLength = runHelper[i].length;
                    }

                    readCount = getBlockFromFile(fileHandler,
                        runHelper[i].start, blockLength);

                    System.arraycopy(this.input, 0, this.ramSize, i
                        * this.recordsPerBlock, readCount);

                    runHelper[i].length -= readCount;
                    recordCount[i] = readCount;
                    runHelper[i].numberOfBlocks -= 1;
                    runHelper[i].start = fileHandler.getFileOffset();
                    blockPointer[i] = 0;
                }
                else if ((blockPointer[i] == recordCount[i])
                    && (runHelper[i].numberOfBlocks == 0)) {
                    runRecords[i] = null;
                }
            }
            if (runRecords[minIndex] != null) {
                runRecords[minIndex] = this.ramSize[minIndex
                    * this.recordsPerBlock + blockPointer[minIndex]];
            }
            recordLength--;
        }
        if (outputPointer > 0) {
            for (int i = 0; i < outputPointer; i++) {
                fileHandler.writeRecord(tempOffSet, this.output[i].getRecord());
                tempOffSet += this.recordSize;
            }
        }

        this.runManager.insert(new RunHelper(offSetBeforeCurrent, runLength));
        fileHandler.closeFile();
    }


    /**
     * Logic to perform n-way merge on run manager list
     */
    public void nWayMerge() {

        RunHelper[] runHelpers = new RunHelper[this.minHeapSize];

        while (this.runManager.getLength() != 1) {
            int i = 0;
            while ((!this.runManager.isEmpty()) && (i != this.minHeapSize)) {
                runHelpers[i] = this.runManager.getAndDelete();
                i++;
            }
            // call merge
            merge(runHelpers, i);
        }

        RunHelper finalRec = this.runManager.getAndDelete();

        String outputFileName = "./Data/outputFile";
        FileHandler.removeFile(outputFileName);

        this.runFile = new FileHandler(this.runFileName, this.recordSize,
            this.recordsPerBlock);

        FileHandler outputFile = new FileHandler(outputFileName,
            this.recordSize, this.recordsPerBlock);

        outputFile.writeToOutputFile(this.runFile, finalRec.start,
            finalRec.length * this.recordSize);

        outputFile.closeFile();
        this.runFile.closeFile();

    }

}
