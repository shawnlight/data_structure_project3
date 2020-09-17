import java.io.File;
import java.io.IOException;
import student.TestCase;

/**
 * @author {Ziqi Xiao}
 * @version {2020 fall}
 */
public class MemManTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing Here
    }


    /**
     * Get code coverage of the class declaration.
     * 
     * @throws IOException
     */
    public void testRInit() throws IOException {
        MemMan manager = new MemMan();
        assertNotNull(manager);
        String[] agrs1 = { "32", "32", "local" };
        MemMan.main(agrs1);
        String[] agrs2 = { "10", "32", "local" };
        MemMan.main(agrs2);
        String[] agrs3 = { "10", "32" };
        MemMan.main(agrs3);
        String[] agrs4 = { "32", "0", "local" };
        MemMan.main(agrs4);
        String[] agrs5 = { "32", "A", "local" };
        MemMan.main(agrs5);
        String[] agrs6 = { "-10", "A", "local" };
        MemMan.main(agrs6);
        World world = new World(32, 32);
        Parser parser = new Parser(world);
        String curPath = System.getProperty("user.dir");
        String filePath = curPath + "/src/input.txt";
        File file = new File(filePath);
        assertEquals(parser.readCmdFile(file), true);

    }
}
