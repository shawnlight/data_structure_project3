import java.io.*;

/**
 * replacement selection class
 * 
 * @author light
 * @version Nov 5, 2020
 */
public class ReplacementSelection {
    private byte[] inputbuffer;
    private byte[] outputbuffer;
    private int blocksize;
    private File inputfile;
    private File runfile;
    private MyList<Integer> lengthList;
    private MyList<Integer> startList;
    private int inputPointer = 0;
    private int outputPointer = 0;
    private int runlength = 0;
    private final int recordsize = 16;

    /**
     * constructor
     * 
     * @param input
     *            input file
     * @param out
     *            output file
     * @param size
     *            block size
     * @throws FileNotFoundException
     */
    public ReplacementSelection(File input, File out, int size)
        throws FileNotFoundException {
        blocksize = size;
        inputfile = input;
        runfile = out;
        inputbuffer = new byte[blocksize];
        outputbuffer = new byte[blocksize];
        lengthList = new MyList<>();
        startList = new MyList<>();

    }


    /**
     * get the run length list
     * 
     * @return length list
     */
    public MyList<Integer> getLengthList() {
        return lengthList;
    }


    /**
     * get run start position list
     * 
     * @return start list
     */
    public MyList<Integer> getStartList() {
        return startList;
    }


    /**
     * replacement selection function
     * 
     * @throws IOException
     */
    @SuppressWarnings({ "unchecked", "resource" })
    public void startrun() throws IOException {
        // first fill the heap with 8 block long
        int heapsize = blocksize / recordsize * 8;
        Comparable<Record>[] arr = new Comparable[heapsize];
        InputStream inputStream = new FileInputStream(inputfile);
        OutputStream outputStream = new FileOutputStream(runfile);
        // put the data the heap to initialize
        for (int i = 0; i < heapsize; i++) {
            byte[] recordByte = new byte[recordsize];
            inputStream.read(recordByte);
            arr[i] = new Record(recordByte);
        }

        MinHeap<Record> heap = new MinHeap<>(arr, heapsize, heapsize);
        // read the data into input buffer and process
        while (inputStream.read(inputbuffer) != -1) {
            while (inputPointer != blocksize) {
                // run down case
                if (heap.getSize() == 0) {
                    heap.reheap();
                    lengthList.add(runlength);
                    runlength = 0;
                }
                Record tostore = (Record)heap.getMin();
                storeRecord(tostore);
                // write the outbuffer into disk case
                if (outputPointer == blocksize) {
                    outputStream.write(outputbuffer);
                    outputPointer = 0;

                }
                Record pushed = pushRecord();
                // discard the element if it is less than previous
                if (pushed.compareTo(tostore) < 0) {
                    heap.discard(pushed);
                }
                else {
                    heap.modify(0, pushed);
                }
            }

            inputPointer = 0;
        }

        // last step heap sort run if there is no more data to read
        // if the heap size did not reduce, last run is not over
        if (heap.getSize() == heapsize) {
            int listsize = lengthList.getSize();
            if (listsize == 0) {
                lengthList.add(runlength + 8 * blocksize);
            }
            else {
                lengthList.set(lengthList.get(listsize - 1) + 8 * blocksize,
                    listsize - 1);
            }
        }
        // whole heap is a new run
        else {
            lengthList.add(runlength);
            lengthList.add(8 * blocksize);
            heap.reheap();
        }
        // store the heap element
        for (int i = 0; i < heapsize; i++) {
            Record min = (Record)heap.removeMin();
            storeRecord(min);
            if (outputPointer == blocksize) {
                outputStream.write(outputbuffer);
                outputPointer = 0;
            }
        }
        genStartList();

    }


    /**
     * get the record form input buffer
     * 
     * @return record
     */
    private Record pushRecord() {
        byte[] recordByte = new byte[recordsize];
        for (int i = 0; i < recordsize; i++) {
            recordByte[i] = inputbuffer[inputPointer + i];
        }

        Record rec = new Record(recordByte);
        inputPointer += recordsize;
        return rec;
    }


    /**
     * store the record into the output buffer
     * 
     * @param rec
     *            record to store
     */
    private void storeRecord(Record rec) {

        byte[] recbyte = rec.getCompleteRecord();
        for (int i = 0; i < recordsize; i++) {
            outputbuffer[outputPointer + i] = recbyte[i];
        }
        outputPointer += recordsize;
        runlength += recordsize;
    }


    /**
     * generate the start index array using the length array
     */
    private void genStartList() {
        MyList<Integer> res = new MyList<>();
        res.add(0);
        for (int i = 0; i < lengthList.getSize() - 1; i++) {
            res.add(lengthList.get(i) + res.get(i));
        }
        startList = res;

    }

}
