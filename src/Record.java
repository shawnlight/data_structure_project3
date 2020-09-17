
/**
 * the record class contains the necessary information
 * 
 * @author light
 * @version 2020 fall
 */
public class Record {
    private String name;
    private MyList<String> fieldName;
    private MyList<String> fieldValue;
    private int start;
    private int end;

    /**
     * constructor
     * initialize the field array
     * 
     */
    public Record() {
        // default array for field
        fieldName = new MyList<String>(String.class, 10);
        fieldValue = new MyList<String>(String.class, 10);

    }


    /**
     * get the record name
     * 
     * @return record name
     */
    public String getName() {
        return name;
    }


    /**
     * set the record name
     * 
     * @param name
     *            name of the record
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * get the field name list
     * 
     * @return field name list
     */
    public MyList<String> getFieldName() {
        return fieldName;
    }


    /**
     * add the field name in the record
     * 
     * @param fieldN
     *            the record fieldName
     */
    public void addFieldName(String fieldN) {
        this.fieldName.add(fieldN);
    }


    /**
     * get the field value list
     * 
     * @return field value
     */
    public MyList<String> getFieldValue() {
        return fieldValue;
    }


    /**
     * add the field value array in the record
     * 
     * @param fieldV
     *            the record fieldValue
     */
    public void addFieldValue(String fieldV) {
        this.fieldValue.add(fieldV);
    }


    /**
     * remove the field name and value
     * in the record
     * 
     * @param index
     *            the location of target field
     */
    public void removeField(int index) {
        fieldName.remove(index);
        fieldValue.remove(index);
    }


    /**
     * get the start position
     * in the memory pool
     * 
     * @return start position
     */
    public int getStart() {
        return start;
    }


    /**
     * set the start position
     * in the memory pool
     * 
     * @param start
     *            start position of record
     */
    public void setStart(int start) {
        this.start = start;
    }


    /**
     * get the end position
     * in the memory pool
     * 
     * @return end position
     */
    public int getEnd() {
        return end;
    }


    /**
     * set the end position
     * in the memory pool
     * 
     * @param end
     *            end position of record
     */
    public void setEnd(int end) {
        this.end = end;
    }


    /**
     * get the record total length
     * including name and field
     * 
     * @return record length
     */
    public int getLength() {
        int result = name.length();
        for (int i = 0; i < fieldName.getSize(); i++) {
            if (fieldName.get(i) != null) {
                result += "<SEP>".length();
                result += fieldName.get(i).length();
                result += "<SEP>".length();
                result += fieldValue.get(i).length();
            }
        }

        return result;
    }

}
