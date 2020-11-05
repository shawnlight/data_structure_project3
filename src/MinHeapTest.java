import student.TestCase;

/**
 * unit test for MinHeap class
 * 
 * @author light
 * @version {2020 fall}
 * 
 */
public class MinHeapTest extends TestCase {
    /**
     * test the list class
     * with add, remove, get .etc method
     */
    public void testInsert() {
        Integer[] arr = { 72, 73, 42, 6, 48, 99, 60, 88, 83, 85, 57 };
        MinHeap<Integer> test = new MinHeap<>(arr, 11, 11);
        test.modify(0, 10);
        test.dumpHeap();
        System.out.println("------------------------------------------------");
        test.discard(11);
        test.siftdown(12);
        test.dumpHeap();
        System.out.println("------------------------------------------------");
        assertEquals(42, test.removeMin());
        test.dumpHeap();
        System.out.println("------------------------------------------------");
        assertEquals(48, test.removeMin());
        test.dumpHeap();
        System.out.println("------------------------------------------------");
        test.reheap();
        test.dumpHeap();
        assertEquals(test.getSize(), 11);
        assertEquals(test.getMin(), 11);
        assertEquals(-1, test.parent(-1));
        assertEquals(0, test.parent(2));

    }
}
