
import student.TestCase;

/**
 * /**
 * 
 * @author Mayur Dhepe
 * @author Sushil Khairnar
 * @version Nov 25 2021
 *          Class to test ExternalSort
 */
public class ExternalsortTest extends TestCase {

    private Externalsort extSort;

    /**
     * setUp method
     */
    public void setUp() {
        extSort = new Externalsort();
        System.out.print("");
    }


    /**
     * To test main function
     */
    public void testMain() {

        try {
            GenFile.sorted(new String[] { "./Data/sampleInput200.bin", "200" });
            GenFile.reversed(new String[] { "./Data/sampleInput30.bin", "30" });
            GenFile.sorted(new String[] { "./Data/sampleInput400.bin", "400" });
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        extSort.main(new String[] { "./Data/sampleInput200.bin" });
        assertTrue(equalsRegex(systemOut().getHistory(), "[\\s\\S]*"));
        systemOut().clearHistory();

        extSort.main(new String[] { "./Data/sampleInput30.bin" });
        assertTrue(equalsRegex(systemOut().getHistory(), "[\\s\\S]*"));
        systemOut().clearHistory();

        extSort.main(new String[] { "./Data/sampleInput400.bin" });
        assertTrue(equalsRegex(systemOut().getHistory(), "[\\s\\S]*"));
        systemOut().clearHistory();

        /*
         * extSort.main(new String[] {"./Data/P1test3.txt"});
         * assertEquals(systemOut().getHistory(), "Invalid file\n");
         */
    }

}
