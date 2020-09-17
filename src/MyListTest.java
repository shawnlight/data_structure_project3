import student.TestCase;

/**
 * unit test for myList class
 * 
 * @author light
 * @version {2020 fall}
 * 
 */
public class MyListTest extends TestCase {
    /**
     * test the list class
     * with add, remove, get .etc method
     */
    public void testInsert() {
        MyList<Integer> list = new MyList<Integer>(Integer.class, 10);
        list.add(1);
        list.add(2);
        assertEquals((int)list.getSize(), 2);
        assertEquals((int)list.get(0), 1);
        assertEquals((int)list.remove(0), 1);
        assertEquals((int)list.getSize(), 1);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        assertEquals((int)list.getMaxSize(), 10);
        list.add(11);
        list.add(12);
        assertEquals((int)list.getMaxSize(), 20);
        assertEquals((int)list.getSize(), 11);
        assertEquals((int)list.remove((int)list.getSize() - 1), 12);
        @SuppressWarnings("rawtypes")
        MyList<MyList> listArray = new MyList<MyList>(MyList.class, 10);
        listArray.add(list);
        @SuppressWarnings("rawtypes")
        MyList ele = listArray.get(0);
        assertEquals(ele.get(0), 2);

    }
}
