import student.TestCase;

/**
 * unit test for record class
 * 
 * @author light
 * @version 2020 fall
 * 
 */
public class RecordTest extends TestCase {
    /**
     * record test focus on the
     * get length method
     * 
     */
    public void testInsert() {
        Record record = new Record();
        record.setName("light");
        assertEquals(record.getLength(), 5);
        String fieldName1 = "gene";
        String fieldName2 = "hobby";
        String fieldValue1 = "beast";
        String fieldValue2 = "game";
        record.addFieldName(fieldName1);
        record.addFieldValue(fieldValue1);
        assertEquals(record.getLength(), 24);
        record.addFieldName(fieldName2);
        record.addFieldValue(fieldValue2);
        assertEquals(record.getLength(), 43);

    }
}
