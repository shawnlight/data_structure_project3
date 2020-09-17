import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import student.TestCase;

/**
 * unit test for world database
 * 
 * @author light
 * @version {2020 fall}
 * 
 */
public class MemManageTest extends TestCase {
    /**
     * test insert release print method
     */
    public void testInsert() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String out1 = "32: 0\n";
        MemManage mem = new MemManage(32);
        mem.printBlock();
        assertEquals(out1, outContent.toString());
        Record record1 = new Record();
        record1.setName("Death Note");
        mem.insert(record1);
        mem.release(record1);
        mem.printBlock();
        String fieldN1 = "Genre";
        String fieldV1 = "Anime";
        record1.addFieldName(fieldN1);
        record1.addFieldValue(fieldV1);
        mem.insert(record1);
        mem.printBlock();
        Record record2 = new Record();
        record2.setName("Can You Handle?");
        Record record3 = new Record();
        record3.setName("Another Test");
        Record record4 = new Record();
        record4.setName("Death Note");
        mem.insert(record2);
        mem.insert(record3);
        mem.printBlock();
        mem.release(record1);
        mem.printBlock();
        mem.insert(record4);
        mem.release(record3);
        mem.printBlock();
        String out2 = "32: 0\n" + "32: 0" + "\nNo free blocks are available"
            + "\nMemory pool expanded to be 64 bytes."
            + "\nNo free blocks are available\n" + "32: 0\n16: 16 48\n";
        assertEquals(out2, outContent.toString());

    }
}
