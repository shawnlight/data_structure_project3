import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import student.TestCase;

/**
 * main class test class
 * 
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class ExternalsortTest extends TestCase {

    /**
     * set up for tests
     */
    public void setUp() {
        // nothing to set up.
    }


    /**
     * get the output string of input file
     * 
     * @param path
     *            path of file
     * @return
     *         output string
     * @throws IOException
     *             IO exception for file reader
     */
    static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }


    /**
     * Get code coverage of the class declaration.
     * 
     * @throws IOException
     */
    public void testExternalsortInit() throws IOException {
        Externalsort sorter = new Externalsort();
        String curPath = System.getProperty("user.dir");
        String input = curPath + "/src/sample.bin";
        assertNotNull(sorter);
        String[] args1 = new String[2];
        args1[0] = input;
        args1[1] = "100";
        Genfile.main(args1);
        String[] args2 = new String[1];
        args2[0] = input;
        Externalsort.main(args2);

    }

}
