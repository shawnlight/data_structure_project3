import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * multi-merge class
 * 
 * @author light
 * @version Nov 5, 2020
 */
public class MultiMerge {
    private MyList<Integer> startArray;
    private MyList<Integer> lenArray;
    private File runfile;
    private File outfile;
    private byte[] outbuffer;
    private int outputPointer = 0;
    private int blocksize;
    private WorkSpace work;
    private int recordsize = 16;
    private int fileID = 0;

    /**
     * constructor of class, initialize the work space
     * 
     * @param in
     *            input file
     * @param out
     *            output file
     * @param start
     *            start array after replacement selection
     * @param length
     *            length array after replacement selection
     * @param size
     *            block size
     * @throws IOException
     */
    public MultiMerge(
        File in,
        File out,
        MyList<Integer> start,
        MyList<Integer> length,
        int size)
        throws IOException {
        blocksize = size;
        runfile = in;
        outfile = out;
        outbuffer = new byte[blocksize];
        startArray = start;
        lenArray = length;
        work = new WorkSpace(blocksize, 8, startArray, lenArray);
    }


    /**
     * check if the merge process is complete
     * 
     * @param startIndex
     *            start index in length array
     * @param len
     *            length to choose in length array
     * @return true if the length period we choose are all zero
     */
    private boolean complete(int startIndex, int len) {
        for (int i = startIndex; i < startIndex + len; i++) {
            if (lenArray.get(i) != 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * merge function
     * 
     * @throws IOException
     */
    public void merge() throws IOException {
        // if the length less than the work memory size, just write
        if (lenArray.getSize() <= 8) {
            // since we update the length and start array, need to get a new
            // instance of work space
            work = new WorkSpace(blocksize, 8, startArray, lenArray);
            mergeHelp(0, lenArray.getSize(), runfile, outfile);
            // delete the runfile to free some disk space
            runfile.delete();
            return;
        }
        // super runs, need to create
        String curPath = System.getProperty("user.dir");
        String temp = curPath + "/src/temp" + fileID + ".bin";
        File tempfile = new File(temp);
        int startPos = 0;
        int lenArrayLen = lenArray.getSize();
        MyList<Integer> newLen = new MyList<>();
        while (lenArrayLen >= 8) {
            newLen.add(newLength(startPos, 8));
            mergeHelp(startPos, 8, runfile, tempfile);
            startPos += 8;
            lenArrayLen -= 8;
        }
        // final length added since last run number length maybe less than 8
        int finalLen = lenArrayLen;
        newLen.add(newLength(startPos, finalLen));
        mergeHelp(startPos, finalLen, runfile, tempfile);
        // get the new startArray
        lenArray = newLen;
        genStartList();
        // next run
        fileID++;
        File toDelete = runfile;
        runfile = tempfile;
        // delete the previous run file
        toDelete.delete();
        merge();

    }


    /**
     * merge help function
     * 
     * @param startindex
     *            start index to show which section we merge
     * @param len
     *            length of the section
     * @param in
     *            input file
     * @param out
     *            putput file
     * @throws IOException
     */
    @SuppressWarnings("resource")
    private void mergeHelp(int startindex, int len, File in, File out)
        throws IOException {
        if (len > 8) {
            return;
        }
        // if the output is the final output, overwrite the file, else append
        // the file for next merge pass
        OutputStream outputStream = new FileOutputStream(out, true);
        if (out == outfile) {
            outputStream = new FileOutputStream(out, false);
        }

        // first put n block into the work space, n = len
        initialize(in, startindex, len);
        RandomAccessFile fileToMerge = new RandomAccessFile(in, "rw");
        while (!complete(startindex, len)) {

            for (int i = 0; i < len; i++) {
                // if the length is 0, means this run data is all merged
                if (lenArray.get(startindex + i) == 0) {
                    work.setComplete(i);
                    continue;
                }
                // if the block pointer go to end, read next block
                if (work.blockDeplete(i)) {
                    work.resetPointer(i);
                    byte[] next = new byte[blocksize];
                    long pos = startArray.get(startindex + i);
                    fileToMerge.seek(pos);
                    fileToMerge.read(next);
                    work.store(next, i);
                    // if the remaining data in that run size is less than a
                    // block
                    if (lenArray.get(startindex + i) < blocksize) {
                        work.setEnd(lenArray.get(startindex + i) + blocksize
                            * i, i);

                    }
                    // update the start position in start array for next seek
                    // mathod
                    startArray.set(startArray.get(i + startindex) + blocksize, i
                        + startindex);
                }

            }
            // get the minimum record and push to output buffer
            Record minRec = work.getMin(startindex, len);
            storeRecord(minRec);
            if (outputPointer == blocksize) {
                outputStream.write(outbuffer);
                outputPointer = 0;
            }
        }

        // if there still some data in output buffer
        if (outputPointer != 0) {
            byte[] lastWrite = new byte[outputPointer];
            for (int i = 0; i < outputPointer; i++) {
                lastWrite[i] = outbuffer[i];
            }
            outputStream.write(lastWrite);
            outputPointer = 0;
        }
        // reset the work space after one pass
        work.reset();

    }


    /**
     * initialize the 8 block size work space
     * 
     * @param file
     *            input file
     * @param startindex
     *            start index for choose the section
     * @param len
     *            length of the section
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public void initialize(File file, int startindex, int len)
        throws IOException {
        RandomAccessFile fileToMerge = new RandomAccessFile(file, "rw");
        byte[] toRead = new byte[blocksize];
        for (int i = 0; i < len; i++) {
            long pos = startArray.get(i + startindex);
            fileToMerge.seek(pos);
            fileToMerge.read(toRead);
            work.store(toRead, i);
            // if the run length is less than a block, update the end position
            if (lenArray.get(startindex + i) < blocksize) {
                work.setEnd(lenArray.get(startindex + i) + blocksize * i, i);
            }
            startArray.set(startArray.get(i + startindex) + blocksize, i
                + startindex);
        }

    }


    /**
     * store the record into the output buffer as bytes
     * 
     * @param rec
     *            record to store
     */
    public void storeRecord(Record rec) {
        byte[] recbyte = rec.getCompleteRecord();
        for (int i = 0; i < recordsize; i++) {
            outbuffer[outputPointer + i] = recbyte[i];
        }
        outputPointer += recordsize;
    }


    /**
     * get the total length of section for next merge pass
     * 
     * @param start
     *            start position in length array
     * @param len
     *            length of the section
     * @return the total length
     */
    public int newLength(int start, int len) {
        int sum = 0;
        for (int i = 0; i < len; i++) {
            sum += lenArray.get(i + start);

        }
        return sum;
    }


    /**
     * update the start array using the new length array
     */
    public void genStartList() {
        MyList<Integer> res = new MyList<>();
        res.add(0);
        for (int i = 0; i < lenArray.getSize() - 1; i++) {
            res.add(lenArray.get(i) + res.get(i));
        }

        startArray = res;

    }


    /**
     * print the output in given format
     * 
     * @param file
     *            file to read
     * @throws IOException
     */
    public void dump(File file) throws IOException {
        RandomAccessFile fileToRead = new RandomAccessFile(file, "rw");
        long length = file.length();
        int j = 0;
        for (long i = 0; i < length; i += blocksize) {
            byte[] readRec = new byte[recordsize];
            fileToRead.seek(i);
            fileToRead.read(readRec);
            Record record = new Record(readRec);
            System.out.print(record.fullRecord());
            j++;
            if (j % 5 == 0) {
                System.out.println("");
            }

        }

    }

}
