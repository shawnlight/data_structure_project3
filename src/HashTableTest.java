import student.TestCase;

/**
 * unit test for hash table
 * 
 * @author light
 * @version {2020 fall}
 * 
 */
public class HashTableTest extends TestCase {
    /**
     * test the hashtable method
     * put,delete,print
     */
    public void testInsert() {
        HashTable hashtable = new HashTable(10);
        Record record1 = new Record();
        record1.setName("Death Note");
        hashtable.put(record1);
        Record record2 = new Record();
        record2.setName("Death Note");
        hashtable.put(record2);
        Record record3 = new Record();
        record3.setName("light");
        hashtable.put(record3);
        assertEquals(hashtable.getSize(), 10);
        Record record4 = new Record();
        record4.setName("light1");
        hashtable.put(record4);
        Record record5 = new Record();
        record5.setName("light2");
        hashtable.put(record5);
        Record record6 = new Record();
        record6.setName("light3");
        hashtable.put(record6);
        Record record7 = new Record();
        record7.setName("light4");
        hashtable.put(record7);
        assertEquals(hashtable.getSize(), 20);
        assertEquals(hashtable.containValue("light8"), -1);
        assertEquals(hashtable.containValue("light3"), 12);
        hashtable.delete(record6);
        assertEquals(hashtable.containValue("light3"), -1);
        assertEquals(hashtable.delete(record6), -1);
        assertEquals(hashtable.delete(null), 0);
        hashtable.hashPrint();
        Record record8 = new Record();
        record8.setName("light1");
        hashtable.put(record8);

    }
}
