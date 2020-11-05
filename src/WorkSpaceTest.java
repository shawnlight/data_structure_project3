import student.TestCase;

/**
 * unit test for work space class
 * 
 * @author light
 * @version {2020 fall}
 * 
 */
public class WorkSpaceTest extends TestCase {
    /**
     * 
     * @throws IOException
     */
    public void testInsert() {
        MyList<Integer> startArray = new MyList<Integer>();
        MyList<Integer> lenArray = new MyList<Integer>();
        WorkSpace work = new WorkSpace(8192, 7, startArray, lenArray);
        byte[] b0 = new byte[8192];
        b0[8] = 6;
        b0[24] = 7;
        byte[] b1 = new byte[8192];
        b1[8] = 5;
        b1[24] = 6;
        byte[] b2 = new byte[8192];
        b2[8] = 8;
        b2[24] = 9;
        byte[] b3 = new byte[8192];
        b3[8] = 4;
        b3[24] = 5;
        byte[] b4 = new byte[8192];
        b4[8] = 3;
        b4[24] = 16;
        byte[] b5 = new byte[8192];
        b5[8] = 1;
        b5[24] = 3;
        byte[] b6 = new byte[8192];
        b6[8] = 2;
        b6[24] = 3;
        work.store(b0, 0);
        work.store(b1, 1);
        work.store(b2, 2);
        work.store(b3, 3);
        work.store(b4, 4);
        work.store(b5, 5);
        work.store(b6, 6);
        assertEquals(work.getpointer(0), 0);
        assertEquals(work.getWorkSpace()[8], 6);
    }

}
