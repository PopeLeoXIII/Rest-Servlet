package array_list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Rationalization of a dynamically expanding array that can contain elements
 * of the type specified at creation. That class implements the main
 * methods of the {@link ArrayList} interface.
 *
 * Each {@code MyArrayList} instance has a <i>capacity</i>.  The capacity is
 * the size of the array used to store the elements in the list.  It is always
 * at least as large as the list size.
 *
 * @param <E> the type of elements in this list
 */
public class MyArrayList<E> {
    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer.
     */
    private E[] array;
    /**
     * The size of the ArrayList (the number of elements it contains).
     */
    private int size;

    /**
     *  Constructs an empty list with the initial capacity = 0.
     */
    public MyArrayList() {
        array = (E[]) new Object[0];
        size = 0;
    }

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param  initialCapacity  the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }

        array = (E[]) new Object[initialCapacity];
        size = 0;
    }

    /**
     * Constructs a list containing the elements of the array
     *
     * @param array the array whose elements are to be placed into this list
     * @throws NullPointerException if array null
     */
    public MyArrayList(E[] array) {
        size = array.length;
        this.array = (E[]) new Object[size];
        System.arraycopy(array, 0, this.array, 0, size);
    }


    /**
     * Appends the specified element to the end of this list.
     *
     * @param element element to be appended to this list
     * @return {@code true}
     */
    public boolean add(E element) {
        if (array.length == size) {
            int newCap = (array.length + 1) * 2;
            E[] newArray = (E[]) new Object[newCap];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
        array[size++] = element;
        return true;
    }

    /**
     * Inserts the specified element at the specified position in this
     * list. Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public void add(int index, E element) {
        checkOutOfBound(index, size);

        if (array.length == size) {
            int newCap = (array.length + 1) * 2;
            E[] newArray = (E[]) new Object[newCap];
            System.arraycopy(array, 0, newArray, 0, index);
            System.arraycopy(array, index, newArray, index + 1, array.length - index);
            array = newArray;
        } else {
//            if (index != array.length)
            System.arraycopy(array, index, array, index + 1, size - index);
        }
        array[index] = element;
        size++;
    }

    /**
     * Appends all elements in the specified MyArrayList to the end of
     * this list.
     *
     * @param myArrayList collection containing elements to be added to this list
     * @return {@code true} if this list changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    public boolean addAll(MyArrayList<E> myArrayList) {
        if (myArrayList == null)
            throw new NullPointerException();

        if (size + myArrayList.size > array.length) {
            int newCap = size + myArrayList.size;
            E[] newArray = (E[]) new Object[newCap];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
        System.arraycopy(myArrayList.array, 0, array, size, myArrayList.size);
        size += myArrayList.size;
        return true;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param  index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E get(int index) {
        checkOutOfBound(index, size - 1);
        return array[index];
    }

    /**
     * Replaces the element at the specified position in this list with
     * the specified element.
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E set(int index, E element) {
        checkOutOfBound(index, size - 1);
        E oldValue = array[index];
        array[index] = element;
        return oldValue;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E remove(int index) {
        checkOutOfBound(index, size - 1);
        E oldValue = array[index];

        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null;
        size--;
        return oldValue;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if this list contains no elements.
     *
     * @return {@code true} if this list contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns an array containing all of the elements in this list
     * in proper sequence (from first to last element).
     *
     * @param a the array into which the elements of the list are to
     *          be stored
     * @return an array containing the elements in this list in
     *          proper sequence (a new array of the same runtime type
     *          as a is allocated for this purpose)
     * @throws NullPointerException if the specified array is null
     */
    public E[] toArray(E[] a) {
        if (a == null)
            throw new NullPointerException("The specified array is null");

        return (E[]) Arrays.copyOf(array, size, a.getClass());
    }


    /**
     * Removes all of the elements from this list.  The list will
     * be empty after this call returns.
     */
    public void clear() {
        final Object[] es = array;
        for (int i = 0; i < size; i++)
            es[i] = null;
        size = 0;
    }

    /**
     * Sorts all elements of the array using the quicksort algorithm.
     * The first element is always chosen as the pivot element.
     *
     * @param comparator the comparator
     */
    public void quickSort(Comparator<? super E> comparator) {
        if (comparator == null)
            throw new IllegalArgumentException("Comparator can't be null");

        if (size <= 1) {
            return;
        }

        E pivot = get(0);

        MyArrayList<E> less = new MyArrayList<>(size / 2);
        MyArrayList<E> greater = new MyArrayList<>(size / 2);

        for (int i = 1; i < size; i++) {
            E el = get(i);
            if (comparator.compare(el, pivot) > 0) {
                greater.add(el);
            } else {
                less.add(el);
            }
        }

        less.quickSort(comparator);
        greater.quickSort(comparator);

        this.clear();
        this.addAll(less);
        this.add(pivot);
        this.addAll(greater);
    }

    /**
     * Check index between array bound
     */
    private void checkOutOfBound(int index, int size) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    /**
     * Create error message for IndexOutOfBoundsException
     */
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+ size;
    }


    @Override
    public String toString() {
        return "MyArrayList{" +
                "array=" + Arrays.toString(array) +
                ", cap=" + array.length +
                ", size=" + size +
                '}';
    }
}