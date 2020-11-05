import java.io.File;
import java.io.IOException;
import student.TestCase;

/**
 * unit test for replacement selection class
 * 
 * @author light
 * @version {2020 fall}
 * 
 */
public class ReplacementSelectionTest extends TestCase {
    /**
     * test the replacement selection class
     * with add, remove, get .etc method
     * 
     * @throws IOException
     */
    public void testInsert() throws IOException {
        String curPath = System.getProperty("user.dir");
        String input = curPath + "/src/sample.bin";
        String[] args = new String[2];
        args[0] = input;
        args[1] = "100";
        Genfile.main(args);
        String output = curPath + "/src/run.bin";
        File inputfile = new File(input);
        File outputfile = new File(output);
        ReplacementSelection test = new ReplacementSelection(inputfile,
            outputfile, 8192);
        test.startrun();
        assertNotNull(test);
        MyList<Integer> list = test.getLengthList();
        MyList<Integer> list1 = test.getStartList();
        for (int i = 0; i < list.getSize(); i++) {
            System.out.println(list.get(i));
        }
        for (int i = 0; i < list1.getSize(); i++) {
            System.out.println(list1.get(i));
        }

    }
}
