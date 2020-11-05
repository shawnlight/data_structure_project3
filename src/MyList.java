
/**
 * array list class for
 * supporting the memory management
 * 
 * @author light
 * @version {2020 fall}
 * @param <T>
 *            type
 *            type of the list we create
 *
 */
public class MyList<T> {
    private int maxSize = 10;
    private int listSize;
    private Object[] obj;
// private Class<T> newObject;

    /**
     * constructor initialize the list
     * with specific size
     * 
     */
    public MyList() {
        listSize = 0;
        obj = new Object[maxSize];

    }


    /**
     * constructor to create arbitrary size list
     * 
     * @param max
     *            list max size
     */
    public MyList(int max) {
        this.maxSize = max;
        listSize = 0;
        obj = new Object[maxSize];

    }


    /**
     * add the element in the end of the list
     * 
     * @param element
     *            the element we add
     */
    public void add(T element) {
        if (isFull()) {
            expand();
        }
        obj[listSize++] = element;
    }


    /**
     * remove the element at location index
     * 
     * @param index
     *            the location of element we need to remove
     * @return removed element
     */
    @SuppressWarnings("unchecked")
    public T remove(int index) {
// T[] newArray = (T[]) myArray[maxSize];
        Object[] newArray = new Object[maxSize];
        T removed = (T)obj[index];
        obj[index] = null;
        int pos = 0;
        for (int i = 0; i < listSize; i++) {
            if (obj[i] != null) {
                newArray[pos++] = obj[i];
            }

        }
        listSize--;
        this.obj = newArray;
        return removed;

    }


    /**
     * check if the list is full
     * 
     * @return true if it is full
     */
    public boolean isFull() {

        return listSize >= maxSize;

    }


    /**
     * double the list size
     * put the original element
     * in the new list
     * 
     */
    public void expand() {
        maxSize = 2 * maxSize;
        Object[] expandArray = new Object[maxSize];
        for (int i = 0; i < maxSize / 2; i++) {
            expandArray[i] = obj[i];
        }
        obj = expandArray;
    }


    /**
     * get the element at location index
     * 
     * @param index
     *            the target element location
     * @return element object
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T)obj[index];
    }


    /**
     * set the list[index] as element
     * 
     * @param element
     *            set the list[index] as element
     * @param index
     *            target position
     */
    public void set(T element, int index) {
        obj[index] = element;
    }


    /**
     * get the size of the list
     * 
     * @return list size
     */
    public int getSize() {
        return listSize;
    }


    /**
     * get the maximum size
     * 
     * @return max size
     */
    public int getMaxSize() {
        return maxSize;
    }


    /**
     * get the last element of list
     * 
     * @return the last element
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        return (T)obj[listSize - 1];
    }


    /**
     * print the list
     * 
     */
    public void dump() {
        for (int i = 0; i < listSize; i++) {
            System.out.println(obj[i].toString());
        }
    }

}
