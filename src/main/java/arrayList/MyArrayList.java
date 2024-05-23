package arrayList;

import java.util.Arrays;
import java.util.Comparator;

public class MyArrayList<E> {
    private E[] array;
    private int size;

    public MyArrayList() {
        array = (E[]) new Object[0];
        size = 0;
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: "+ initialCapacity);
        }

        array = (E[]) new Object[initialCapacity];
        size = 0;
    }

    public MyArrayList(E[] array) {
        this.array = array;
        size = array.length;
    }

    @Override
    public String toString() {
        return "MyArrayList{" +
                "array=" + Arrays.toString(array) +
                ", cap=" + array.length +
                ", size=" + size +
                '}';
    }

    public void print() {
        System.out.println(this);
    }

    // Добавляем элемент в конец списка
    public void add(E element) {
        if (array.length == size) {
            int newCap = (array.length + 1) * 2;
            E[] newArray = (E[]) new Object[newCap];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
        array[size++] = element;
    }

    public void add(int index, E element) {
        checkOutOfBound(index, size - 1);

        if (array.length == size) {
            int newCap = (array.length + 1) * 2;
            E[] newArray = (E[]) new Object[newCap];
            System.arraycopy(array, 0, newArray, 0, index);
            System.arraycopy(array, index, newArray, index + 1, array.length - index);
            array = newArray;
        } else {
            System.arraycopy(array, index, array, index + 1, array.length - index);
        }
        array[index] = element;
    }

    public void addAll(MyArrayList<E> myArrayList) {
        if (size + myArrayList.size > array.length) {
            int newCap = size + myArrayList.size;
            E[] newArray = (E[]) new Object[newCap];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
        System.arraycopy(myArrayList.array, 0, array, size, myArrayList.size);
        size += myArrayList.size;
    }

    // Получаем элемент по индексу
    public E get(int index) {
        checkOutOfBound(index, size - 1);

        return array[index];
    }

    // Устанавливаем элемент по индексу
    public void set(int index, E value) {
        checkOutOfBound(index, size - 1);

        array[index] = value;
    }

    // Удаляем элемент по индексу
    public boolean remove(int index) {
        checkOutOfBound(index, size - 1);

        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;
        return true;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E[] toArray() {
        E[] res = (E[]) new Object[(array.length)];
        System.arraycopy(array, 0, res, 0, size);
        return res;
    }

    public void quickSort(Comparator<? super E> comparator) {
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

    public void clear() {
        final Object[] es = array;
        for (int i = 0; i < size; i++)
            es[i] = null;
        size = 0;
    }

    private void checkOutOfBound(int index, int size) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+ size;
    }
}