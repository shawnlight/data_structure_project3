import java.lang.reflect.Array;

/**
 * array list class for
 * supporting the memory management
 * 
 * @author light
 * @version {2020 fall}
 * @param <E>
 *            type
 *            type of the list we create
 *
 */
public class MyList<E> {
    private E[] myArray;
    private int maxSize;
    private int listSize;
    private Class<E> newObject;

    /**
     * constructor initialize the list
     * with specific size
     * 
     * @param newObject
     *            the type of the list we create
     * @param size
     *            the maximum size of the list
     */
    @SuppressWarnings("unchecked")
    public MyList(Class<E> newObject, int size) {
        listSize = 0;
        maxSize = size;
        this.newObject = newObject;
        this.myArray = (E[])Array.newInstance(newObject, maxSize);

    }


    /**
     * add the element in the end of the list
     * 
     * @param element
     *            the element we add
     */
    public void add(E element) {
        if (isFull()) {
            expand();
        }
        myArray[listSize++] = element;
    }


    /**
     * remove the element at location index
     * 
     * @param index
     *            the location of element we need to remove
     * @return removed element
     */
    public E remove(int index) {
        @SuppressWarnings("unchecked")
        E[] newArray = (E[])Array.newInstance(newObject, maxSize);
        E removed = myArray[index];
        myArray[index] = null;
        int pos = 0;
        for (int i = 0; i < listSize; i++) {
            if (myArray[i] != null) {
                newArray[pos++] = myArray[i];
            }

        }
        listSize--;
        this.myArray = newArray;
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
        @SuppressWarnings("unchecked")
        E[] expandArray = (E[])Array.newInstance(newObject, maxSize);
        for (int i = 0; i < maxSize / 2; i++) {
            expandArray[i] = myArray[i];
        }
        myArray = expandArray;
    }


    /**
     * get the element at location index
     * 
     * @param index
     *            the target element location
     * @return element object
     */
    public E get(int index) {
        return myArray[index];
    }


    /**
     * set the list[index] as element
     * 
     * @param element
     *            set the list[index] as element
     * @param index
     *            target position
     */
    public void set(E element, int index) {
        myArray[index] = element;
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

}
