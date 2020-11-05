import java.io.File;
import java.io.IOException;
import student.TestCase;

/**
 * unit test for multi-merge class
 * 
 * @author light
 * @version {2020 fall}
 * 
 */
public class MultiMergeTest extends TestCase {
    /**
     * test the merge class using random data
     * 
     * @throws IOException
     */
    public void testInsert() throws IOException {
        String curPath = System.getProperty("user.dir");
        String run = curPath + "/src/run.bin";
        String input = curPath + "/src/sample.bin";
        String[] args = new String[2];
        args[0] = input;
        args[1] = "200";
        Genfile.main(args);
        String output = curPath + "/src/out.bin";
        File runfile = new File(run);
        File inputfile = new File(input);
        File outfile = new File(output);
        ReplacementSelection replace = new ReplacementSelection(inputfile,
            runfile, 8192);
        replace.startrun();
        MultiMerge merge = new MultiMerge(runfile, outfile, replace
            .getStartList(), replace.getLengthList(), 8192);
        merge.merge();
        merge.dump(outfile);
        assertNotNull(merge);

    }
}
