package ExternalSorting;

import java.nio.file.Paths;
import java.nio.file.Files;

/**
 * @author Mayur Dhepe
 * @author Sushil Khairnar
 * @version Nov 25 2021
 *          Main class for external sorting
 */
public class ExternalSort {

    /**
     * Main function
     * 
     * @param args
     *            contains args for main function
     */
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println(
                "Please specify the record file name as argument!");
            return;
        }

        Sort sort = new Sort(args[0]);

        sort.replacementSelection();
        sort.nWayMerge();

        String output = "./Data/outputFile";

        if (!Files.exists(Paths.get(output))) {
            return;
        }

        FileHandler outputFile = new FileHandler(output, 16, 512);

        long[] fileInfo = outputFile.getFileInfo();
        int blockSize = (int)fileInfo[2];
        Record[] record = new Record[blockSize];

        boolean isValidRecord;
        for (int i = 0; i < blockSize; i++) {
            byte[] b = new byte[16];
            isValidRecord = outputFile.hasRecord(b, i * 8192);
            if (isValidRecord) {
                record[i] = new Record(b);
            }
        }
        outputFile.closeFile();

        int maxRecords = 5;
        int lines = record.length / maxRecords + (record.length
            % maxRecords != 0 ? 1 : 0);

        for (int i = 0; i < lines; i++) {
            int j = 0;
            int result = 0;

            if (i != lines - 1) {
                result = maxRecords;
            }
            else {
                if (record.length % maxRecords == 0) {
                    result = maxRecords;
                }
                else {
                    result = record.length % maxRecords;
                }
            }

            while (((maxRecords * i + j) < record.length) && (j < result)) {
                System.out.print(record[maxRecords * i + j].toString() + " ");
                j++;
            }

            System.out.println("");
        }
    }
}
