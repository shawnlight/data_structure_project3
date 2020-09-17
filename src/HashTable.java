/**
 * Hash Table class implementation
 * 
 * @author light
 * @version {2020 fall}
 * 
 */
public class HashTable {
    private int hashSize;
    private int occupySlot = 0;
    private Record[] table;
    private boolean rehashed = false;

    /**
     * hashtable constructor
     * 
     * @param size
     *            the size of hash table
     */
    public HashTable(int size) {
        hashSize = size;
        table = new Record[hashSize];
    }


    /**
     * get the hash table size for testing
     * 
     * @return hash table size
     */
    public int getSize() {
        return hashSize;
    }


    /**
     * get the array of hash table
     * 
     * @return hash table array
     */
    public Record[] getHash() {
        return table;
    }


    /**
     * rehash the hash table it's half full
     */
    public void rehash() {
        // deep copy original array
        Record[] copyTable = new Record[hashSize];
        for (int i = 0; i < table.length; i++) {
            copyTable[i] = table[i];
        }
        this.hashSize = 2 * hashSize;
        this.table = new Record[hashSize];
        for (int i = 0; i < copyTable.length; i++) {
            if (copyTable[i] != null) {
                put(copyTable[i]);
                occupySlot--;
            }
        }
        rehashed = true;

    }


    /**
     * @param record
     *            the record object
     * @return [0] null input value
     *         [1] successfully put into the hash table
     *         [-1] duplicate
     */
    public int put(Record record) {
        if (record == null || record.getName() == null) {
            return 0;
        }
        String recordName = record.getName();
        recordName = recordName.replaceAll("\\s+", " ").trim();
        record.setName(recordName);
        if (occupySlot >= hashSize / 2) {
            rehash();
        }

        Hash hash = new Hash();
        int hashValue = hash.h(recordName, hashSize);
        for (int i = 0; i < table.length; i++) {
            Record recordIn = table[(hashValue + i * i) % hashSize];

            if (recordIn != null) {
                String recordInName = recordIn.getName();
                if (recordName.equals(recordInName)) {
                    return -1;
                }
            }
            if (recordIn == null) {
                // tomb found, continue search for duplicated record
                if (containValue(record.getName()) != -1) {
                    return -1;
                }
                table[(hashValue + i * i) % hashSize] = record;
                occupySlot++;
                return 1;
            }
        }
        return 0;

    }


    /**
     * @param record
     *            the record object
     * @return [0] null input value
     *         [1] delete successfully
     *         [-1] not exist
     */
    public int delete(Record record) {
        if (record == null || record.getName() == null) {
            return 0;
        }
        String recordName = record.getName();
        recordName = recordName.replaceAll("\\s+", " ").trim();
        record.setName(recordName);
        Hash hash = new Hash();
        int hashValue = hash.h(recordName, hashSize);
        for (int i = 0; i < table.length; i++) {
            Record recordIn = table[(hashValue + i * i) % hashSize];
            if (recordIn != null) {
                String recordInName = recordIn.getName();
                if (recordName.equals(recordInName)) {
                    table[(hashValue + i * i) % hashSize] = null;
                    occupySlot--;
                    return 1;
                }
            }

        }
        return -1;
    }


    /**
     * check the hashtable contains the record
     * 
     * @param recordName
     *            the record object name field string
     * @return the slot number if hashtable contains record
     *         -1 if it does not contain
     */
    public int containValue(String recordName) {
        recordName = recordName.replaceAll("\\s+", " ").trim();
        Hash hash = new Hash();
        int hashValue = hash.h(recordName, hashSize);
        for (int i = 0; i < table.length; i++) {
            Record recordIn = table[(hashValue + i * i) % hashSize];
            if (recordIn != null) {
                String recordInName = recordIn.getName();
                if (recordName.equals(recordInName)) {
                    return (hashValue + i * i) % hashSize;
                }
            }

        }
        return -1;
    }


    /**
     * check if rehashed happen
     * 
     * @return true if rehashed
     */
    public boolean isRehashed() {
        return rehashed;
    }


    /**
     * set the rehashed true or false
     * 
     * @param rehashed
     *            if rehashed
     */
    public void setRehashed(boolean rehashed) {
        this.rehashed = rehashed;
    }


    /**
     * print the hashtable with record name and slot number
     * 
     */
    public void hashPrint() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                System.out.println("|" + table[i].getName() + "|" + " " + i);

            }
        }
        System.out.println("Total records: " + occupySlot);
    }

}
