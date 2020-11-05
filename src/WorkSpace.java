import java.nio.ByteBuffer;

/**
 * work space class for multi-merge
 * 
 * @author light
 * @version Nov 5, 2020
 */
public class WorkSpace {
    private byte[] workblock;
    private int[] pointer;
    private int[] end;
    private int blocksize;
    private boolean[] complete;
    private int length;
    private MyList<Integer> lenArray;

    /**
     * constructor
     * 
     * @param blocklength
     *            block size
     * @param workLength
     *            work space length
     * @param start
     *            start array for run
     * @param len
     *            length array for run
     */
    public WorkSpace(
        int blocklength,
        int workLength,
        MyList<Integer> start,
        MyList<Integer> len) {
        lenArray = len;
        length = workLength;
        complete = new boolean[length];
        blocksize = blocklength;
        workblock = new byte[8 * blocksize];
        pointer = new int[length];
        end = new int[length];
        for (int i = 0; i < length; i++) {
            pointer[i] = blocksize * i;
        }
        for (int i = 0; i < length; i++) {
            end[i] = pointer[i] + blocksize;

        }
    }


    /**
     * get the work space byte array
     * 
     * @return work space array
     */
    public byte[] getWorkSpace() {
        return workblock;
    }


    /**
     * store the byte array in work space
     * 
     * @param bt
     *            byte array to store
     * @param index
     *            store index
     */
    public void store(byte[] bt, int index) {
        for (int i = 0; i < bt.length; i++) {
            workblock[pointer[index] + i] = bt[i];
        }
    }


    /**
     * get the pointer value at i
     * 
     * @param i
     *            pointer index
     * @return pointer value in index i
     */
    public int getpointer(int i) {
        return pointer[i];

    }


    /**
     * get the pointer array
     * 
     * @return pointer array
     */
    public int[] getpointerArray() {
        return pointer;

    }


    /**
     * reset the pointer at i
     * 
     * @param i
     *            pointer at i
     */
    public void resetPointer(int i) {
        pointer[i] = blocksize * i;

    }


    /**
     * get the minimum record in work space
     * 
     * @param startIndex
     *            start index in start array
     * @param len
     *            length of section
     * @return minimum record in work space
     */
    public Record getMin(int startIndex, int len) {
        byte[] minbytes = new byte[16];
        ByteBuffer.wrap(minbytes).putDouble(8, Double.MAX_VALUE);
        Record minRecord = new Record(minbytes);
        int pointerIndex = 0;
        for (int i = 0; i < len; i++) {
            if (complete[i]) {
                continue;
            }
            byte[] bytes = new byte[16];
            for (int j = 0; j < 16; j++) {
                bytes[j] = workblock[pointer[i] + j];
            }
            Record toCompare = new Record(bytes);
            if (toCompare.compareTo(minRecord) < 0) {
                minRecord = toCompare;
                pointerIndex = i;
            }
        }
        // update the pointer
        pointer[pointerIndex] = pointer[pointerIndex] + 16;
        // update the run length
        lenArray.set(lenArray.get(pointerIndex + startIndex) - 16, pointerIndex
            + startIndex);
        return minRecord;
    }


    /**
     * set the i position as true to represent run reading completed
     * 
     * @param i
     *            complete array index
     */
    public void setComplete(int i) {
        complete[i] = true;
    }


    /**
     * check if work space block is depleted, then we can read next block
     * 
     * @param i
     *            index of block
     * @return true if the pointer reach the end point
     */
    public boolean blockDeplete(int i) {
        return pointer[i] == end[i];

    }


    /**
     * set the end position value manually if the actual data length is less
     * than block size
     * 
     * @param num
     *            end value
     * @param index
     *            end index
     */
    public void setEnd(int num, int index) {
        end[index] = num;
    }


    /**
     * reset the work space
     */
    public void reset() {
        complete = new boolean[length];

        for (int i = 0; i < length; i++) {
            pointer[i] = blocksize * i;
        }

        for (int i = 0; i < length; i++) {
            end[i] = pointer[i] + blocksize;

        }

    }

}
