
/**
 * heap class
 * 
 * @author light
 * @version Oct 21, 2020
 * @param <T>
 *            generic type
 *            the type of heap we created
 */
public class MinHeap<T extends Comparable<T>> {

    private Comparable<T>[] heap;
    private int capacity;
    private int size;

    /**
     * constructor for HeapMax
     * 
     * @param h
     *            Comparable array
     * @param num
     *            current size
     * @param max
     *            max capacity
     */
    public MinHeap(Comparable<T>[] h, int num, int max) {
        heap = h;
        size = num;
        capacity = max;
        buildheap();
    }


    /**
     * get the heap size
     * 
     * @return size
     */
    public int getSize() {
        return size;
    }


    /**
     * build the initial heap array
     */
    private void buildheap() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            siftdown(i);
        }
    }


    /**
     * sift down the element helping create the heap array
     * 
     * @param pos
     *            current position of number
     */
    @SuppressWarnings("unchecked")
    public void siftdown(int pos) {
        if (pos >= size || pos < 0) {
            return;
        }

        while (!isLeaf(pos)) {
            int j = leftchild(pos);
            if (j < (size - 1) && (heap[j].compareTo((T)heap[j + 1]) > 0)) {
                j++;
            }

            if (heap[pos].compareTo((T)heap[j]) <= 0) {
                return;
            }
            swap(heap, pos, j);
            pos = j;

        }

    }


    /**
     * check if the position element is a leaf
     * 
     * @param pos
     *            current position
     * @return true if the position is a leaf
     */
    private boolean isLeaf(int pos) {
        return pos >= size / 2 && pos < size;
    }


    /**
     * get the left child of chosen position
     * 
     * @param pos
     *            current position
     * @return its left child
     */
    private int leftchild(int pos) {
        if (pos >= size / 2) {
            return -1;
        }
        return 2 * pos + 1;
    }

// /**
// * get the right child of chosen position
// *
// * @param pos
// * current position
// * @return its right child
// */
// @SuppressWarnings("unused")
// private int rightchild(int pos) {
// if (pos >= (size - 1) / 2) {
// return -1;
// }
// return 2 * pos + 2;
// }


    /**
     * get the parent of chosen position
     * 
     * @param pos
     *            current position
     * @return its parent
     */
    public int parent(int pos) {
        if (pos <= 0) {
            return -1;
        }
        return (pos - 1) / 2;
    }


    /**
     * swap the position i and j
     * 
     * @param h
     *            heap array
     * @param i
     *            position i
     * @param j
     *            position j
     */
    @SuppressWarnings("rawtypes")
    private void swap(Comparable[] h, int i, int j) {
        Comparable temp = heap[i];
        h[i] = h[j];
        h[j] = temp;
    }


    /**
     * modify the chosen position with new value
     * 
     * @param pos
     *            position we choose
     * @param newVal
     *            new value we set
     */
    public void modify(int pos, Comparable<T> newVal) {
        if (pos < 0 || pos >= size) {
            return;
        }
        heap[pos] = newVal;
        update(pos);
    }


    /**
     * update the chosen position
     * 
     * @param pos
     *            position we choose
     */
    @SuppressWarnings("unchecked")
    private void update(int pos) {
        while (pos > 0 && heap[pos].compareTo((T)heap[parent(pos)]) > 0) {
            swap(heap, pos, parent(pos));
            pos = parent(pos);
        }
        siftdown(pos);
    }


    /**
     * add the value to the last and change the last the value
     * to the first, reheap the heap array
     * 
     * @param val
     *            value add to the last
     */
    public void discard(Comparable<T> val) {
        if (size == 0) {
            return;
        }
        Comparable<T> temp = heap[size - 1];
        heap[size - 1] = val;
        size--;
        modify(0, temp);
    }


    /**
     * remove the heap minimum element
     * 
     * @return minimum element
     */
    public Comparable<T> removeMin() {
        swap(heap, 0, --size);
        siftdown(0);
        return heap[size];

    }


    /**
     * re-heap the heap array
     */
    public void reheap() {
        size = capacity;
        buildheap();
    }


    /**
     * get the minimum element
     * 
     * @return minimum element
     */
    public Comparable<T> getMin() {
        return heap[0];
    }


    /**
     * print the heap array
     * 
     */
    public void dumpHeap() {
        for (int i = 0; i < size; i++) {
            System.out.println(heap[i].toString());
        }
    }

}
