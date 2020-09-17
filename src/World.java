
/**
 * world database class
 * 
 * @author light
 * @version 2020 fall
 */
public class World {
    private HashTable hashtable;
    private MemManage mem;

    /**
     * world data base constructor
     * initialize the hashtable and memory pool
     * 
     * @param iniHash
     *            initial hashtable size
     * @param iniMem
     *            initial memory pool size
     */
    public World(int iniHash, int iniMem) {
        hashtable = new HashTable(iniHash);
        mem = new MemManage(iniMem);

    }


    /**
     * Insert record in the database
     * 
     * @param name
     *            the name of record
     */
    public void insert(String name) {
        Record record = new Record();
        record.setName(name);
        int result = hashtable.put(record);
        if (result == 0) {
            return;
        }
        if (result == -1) {
            System.out.println("|" + record.getName() + "|"
                + " duplicates a record already " + "in the Name database.");
        }
        if (result == 1) {
            mem.insert(record);
            if (hashtable.isRehashed()) {
                System.out.println("Name hash table size doubled to "
                    + hashtable.getSize() + " slots.");
                hashtable.setRehashed(false);
            }
            System.out.println("|" + record.getName() + "|" + " has been added "
                + "to the Name database.");
        }

    }


    /**
     * delete the record in the database
     * 
     * @param name
     *            the name of record
     */
    public void delete(String name) {
        int slot = hashtable.containValue(name);
        Record recordIn = null;
        if (slot != -1) {
            recordIn = hashtable.getHash()[slot];
        }
        Record record = new Record();
        record.setName(name);
        int result = hashtable.delete(record);
        if (result == 0) {
            return;
        }

        if (result == -1) {
            System.out.println("|" + record.getName() + "|"
                + " not deleted because it does not "
                + " exist in the Name database.");

        }
        if (result == 1) {
            mem.release(recordIn);
            System.out.println("|" + record.getName() + "|"
                + " has been deleted " + "from the Name database.");
        }
    }


    /**
     * update the record with field information
     * 
     * @param name
     *            name of record
     * @param fieldName
     *            fieldName of record
     * @param fieldValue
     *            fieldValue of record
     */
    public void updateAdd(String name, String fieldName, String fieldValue) {
        name = name.replaceAll("\\s+", " ").trim();
        fieldName = fieldName.replaceAll("\\s+", " ").trim();
        fieldValue = fieldValue.replaceAll("\\s+", " ").trim();
        if (hashtable.containValue(name) == -1) {
            System.out.println("|" + name + "|" + " not updated "
                + "because it does not " + "exist in the Name database.");
            return;
        }

        int slot = hashtable.containValue(name);
        Record record = hashtable.getHash()[slot];
        mem.release(record);
        MyList<String> fieldN = record.getFieldName();
        if (fieldN.getSize() == 0) {
            record.addFieldName(fieldName);
            record.addFieldValue(fieldValue);
        }
        else {
            for (int i = 0; i < fieldN.getSize(); i++) {
                if (fieldName.equals(fieldN.get(i))) {
                    record.removeField(i);
                    record.addFieldName(fieldName);
                    record.addFieldValue(fieldValue);
                    break;
                }

                if (i == fieldN.getSize() - 1 && !fieldName.equals(fieldN.get(
                    i))) {
                    record.addFieldName(fieldName);
                    record.addFieldValue(fieldValue);
                    break;
                }

            }
        }
        mem.insert(record);

        String result = record.getName();
        for (int i = 0; i < record.getFieldName().getSize(); i++) {
            String fName = record.getFieldName().get(i);
            String fValue = record.getFieldValue().get(i);
            result = result + "<SEP>" + fName + "<SEP>" + fValue;
        }

        System.out.println("Updated Record: " + "|" + result + "|");

    }


    /**
     * delete the record field information
     * 
     * @param name
     *            name of record
     * @param fieldName
     *            fieldName of record
     */
    public void updateDelete(String name, String fieldName) {
        name = name.replaceAll("\\s+", " ").trim();
        fieldName = fieldName.replaceAll("\\s+", " ").trim();
        if (hashtable.containValue(name) == -1) {
            System.out.println("|" + name + "|" + " not updated "
                + "because it does not " + "exist in the Name database.");
            return;
        }

        int slot = hashtable.containValue(name);
        Record record = hashtable.getHash()[slot];
        MyList<String> fieldN = record.getFieldName();
        if (fieldN.getSize() == 0) {
            System.out.println("|" + name + "|" + " not updated "
                + "because the field |" + fieldName + "| does not exist");
            return;
        }
        for (int i = 0; i < fieldN.getSize(); i++) {

            if (fieldName.equals(fieldN.get(i))) {
                mem.release(record);
                record.removeField(i);
                break;

            }
            if (i == fieldN.getSize() - 1 && !fieldName.equals(fieldN.get(i))) {
                System.out.println("|" + name + "|" + " not updated "
                    + "because the field |" + fieldName + "| does not exist");
                return;
            }

        }
        String result = record.getName();
        mem.insert(record);
        for (int i = 0; i < record.getFieldName().getSize(); i++) {
            String fName = record.getFieldName().get(i);
            String fValue = record.getFieldValue().get(i);
            result = result + "<SEP>" + fName + "<SEP>" + fValue;

        }

        System.out.println("Updated Record: " + "|" + result + "|");

    }


    /**
     * print depending on the type
     * block: print the memory pool block information
     * hash-table: print the hash-table record
     * 
     * @param type
     *            hashtable or blocks
     */
    public void print(String type) {
        if (type.equals("hashtable")) {
            hashtable.hashPrint();
            return;
        }

        if (type.equals("blocks")) {
            mem.printBlock();

            return;
        }

        else {
            return;
        }
    }

}
