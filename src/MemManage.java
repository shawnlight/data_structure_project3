
/**
 * memory allocation with buddy method
 * 
 * @author light
 * @version {2020 fall}
 *
 */
public class MemManage {

    /**
     * block class for recording
     * the memory location
     * 
     * @author light
     * @version {2020 fall}
     */
    class Block {
        private int start;
        private int end;

        /**
         * block constructor
         * 
         * @param s
         *            start position
         * @param e
         *            end position
         */
        Block(int s, int e) {
            start = s;
            end = e;
        }

    }

    private int size;
    @SuppressWarnings("rawtypes")
    private MyList[] list;

    /**
     * get the size of block list
     * 
     * @return size
     */
    public int getSize() {
        return size;
    }


    /**
     * get the memory list
     * 
     * @return memory list
     */
    @SuppressWarnings("rawtypes")
    public MyList[] getList() {
        return list;
    }


    /**
     * memory constructor
     * create all possible block list
     * initialize the first free block
     * 
     * @param iniSize
     *            initial size of memory pool
     */
    @SuppressWarnings("unchecked")
    public MemManage(int iniSize) {
        size = iniSize;
        int ex = (int)Math.ceil(Math.log(iniSize) / Math.log(2));
        list = new MyList[ex + 1];

        for (int i = 0; i <= ex; i++) {
            list[i] = new MyList<Block>(Block.class, 10);
        }

        list[ex].add(new Block(0, size - 1));

    }


    /**
     * sort MyList<Block> with start value
     * in each block, this will be used after
     * deletion completion, merged block will
     * always show in end position ,we need to
     * re-sort the list;
     * 
     * @param mylist
     *            the list we will sort
     * @param index
     *            we sort the list[index] element
     */
    @SuppressWarnings("rawtypes")
    public void sortList(MyList[] mylist, int index) {
        @SuppressWarnings("unchecked")
        MyList<Block> target = mylist[index];
        if (target.getSize() == 0) {
            return;
        }

        for (int i = 0; i < target.getSize() - 1; i++) {
            for (int j = 0; j < target.getSize() - i - 1; j++) {
                if (target.get(j).start > target.get(j + 1).start) {
                    Block temp = target.get(j);
                    target.set(target.get(j + 1), j);
                    target.set(temp, j + 1);

                }
            }
        }

    }


    /**
     * put the record into the memory pool
     * 
     * @param record
     *            the record insert into the memory pool
     */
    @SuppressWarnings("unchecked")
    public void insert(Record record) {
        int i;
        Block temp;
        int length = record.getLength();
        int ex = (int)Math.ceil(Math.log(length) / Math.log(2));

        if (ex > list.length - 1) {
            expand();
            insert(record);
            return;
        }

        if (list[ex].getSize() > 0) {
            temp = (Block)list[ex].remove(0);
            record.setStart(temp.start);
            record.setEnd(temp.end);
            return;
        }

        for (i = ex + 1; i < list.length; i++) {
            if (list[i].getSize() == 0) {
                continue;
            }
            else {
                break;
            }
        }

        if (i == list.length) {
            expand();
            insert(record);
            return;
        }

        temp = (Block)list[i].remove(0);
        i--;
        for (; i >= ex; i--) {
            Block newBlockL = new Block(temp.start, temp.start + (temp.end
                - temp.start) / 2);
            Block newBlockR = new Block(temp.start + (temp.end - temp.start + 1)
                / 2, temp.end);
            list[i].add(newBlockL);
            list[i].add(newBlockR);

            temp = (Block)list[i].remove(0);
        }

        record.setStart(temp.start);
        record.setEnd(temp.end);

    }


    /**
     * expand the memory pool if the record
     * length is too long or no full space
     * available
     * 
     */
    @SuppressWarnings("unchecked")
    public void expand() {
        @SuppressWarnings("rawtypes")
        MyList[] expandList = new MyList[list.length + 1];
        for (int j = 0; j < list.length; j++) {
            expandList[j] = list[j];
        }
        size = 2 * size;
        if (list[list.length - 1].getSize() != 0) {
            expandList[list.length] = new MyList<Block>(Block.class, 10);
            expandList[list.length].add(new Block(0, size - 1));
            expandList[list.length - 1] = new MyList<Block>(Block.class, 10);
            this.list = expandList;
        }
        else {
            expandList[list.length] = new MyList<Block>(Block.class, 10);
            expandList[list.length - 1].add(new Block(size / 2, size - 1));
            this.list = expandList;
        }
        System.out.println("Memory pool expanded to be " + size + " bytes.");

    }


    /**
     * release the memory space for the record
     * if it shows in the hashtable
     * 
     * @param record
     *            the record release from the memory pool
     */
    @SuppressWarnings("unchecked")
    public void release(Record record) {
        int start = record.getStart();
        int end = record.getEnd();
        int ex = (int)Math.ceil(Math.log(end - start + 1) / Math.log(2));
        list[ex].add(new Block(start, start + (int)Math.pow(2, ex) - 1));
        releaseHelp(ex, list.length, start, end, true);

    }


    /**
     * help function for release, recursive function
     * for merging the free block list
     * 
     * @param listPos
     *            which block we will search (list[listPos])
     * @param length
     *            the final list length
     * @param start
     *            the start point of released block
     * @param end
     *            the end point of released block
     * @param merge
     *            if the block is merged
     */
    @SuppressWarnings("unchecked")
    public void releaseHelp(
        int listPos,
        int length,
        int start,
        int end,
        boolean merge) {
        if (listPos == length || !merge) {
            // sort the start point
            for (int i = 0; i < list.length; i++) {
                sortList(list, i);
            }

            return;
        }
        boolean merged = false;
        int buddyNum;
        int buddyAddress;
        int newStart = 0;
        int newEnd = 0;
        buddyNum = start / (end - start + 1);
        if (buddyNum % 2 != 0) {
            buddyAddress = start - (int)Math.pow(2, listPos);
        }
        else {
            buddyAddress = start + (int)Math.pow(2, listPos);
        }

        for (int i = 0; i < list[listPos].getSize(); i++) {
            Block temp = (Block)list[listPos].get(i);
            if (temp.start == buddyAddress) {
                if (buddyNum % 2 == 0) {
                    list[listPos + 1].add(new Block(start, start + 2 * (int)Math
                        .pow(2, listPos) - 1));
                }

                else {
                    list[listPos + 1].add(new Block(buddyAddress, buddyAddress
                        + 2 * (int)Math.pow(2, listPos) - 1));
                }

                list[listPos].remove(i);
                list[listPos].remove(list[listPos].getSize() - 1);
                int lastIndex = list[listPos + 1].getSize();
                Block last = (Block)list[listPos + 1].get(lastIndex - 1);
                newStart = last.start;
                newEnd = last.end;
                merged = true;
                break;
            }
        }

        releaseHelp(listPos + 1, length, newStart, newEnd, merged);

    }


    /**
     * print the block by
     * free block length: start position
     */
    public void printBlock() {
        String result = "";
        for (int i = 0; i < list.length; i++) {
            if (list[i].getSize() != 0) {
                result += String.valueOf((int)Math.pow(2, i));
                result += ":";
                for (int j = 0; j < list[i].getSize(); j++) {
                    result += " ";
                    Block myBlock = (Block)list[i].get(j);
                    result += myBlock.start;
                }

                result += "\n";

            }
        }
        if (result.equals("")) {
            System.out.println("No free blocks are available");

        }
        // remove the last "\n" char
        else {
            System.out.println(result.substring(0, result.length() - 1));
        }

    }

}
