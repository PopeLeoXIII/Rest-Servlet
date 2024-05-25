package array_list;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SortTest {
    @Test
    void sortTest() {
        int arraySize = 1000;
        int[] numbers = new int[arraySize];

        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            numbers[i] = random.nextInt(100);
        }

        MyArrayList<Integer> myArray = new MyArrayList<>(arraySize);
        for (int i = 0; i < arraySize; i++) {
            myArray.add(numbers[i]);
        }

        myArray.quickSort(Integer::compareTo);

        Arrays.sort(numbers);
        for (int i = 0; i < arraySize; i++) {
            assertEquals(numbers[i], myArray.get(i));
        }
    }

    @Test
    void sortAllZeroTest() {
        Integer[] values = {0, 0, 0, 0, 0, 0, 0, 0};
        MyArrayList<Integer> arr = new MyArrayList<>(values);

        arr.quickSort(Integer::compareTo);

        assertEquals(values.length, arr.size());
        assertArrayEquals(values, arr.toArray(new Integer[0]));
    }

    @Test
    void sortAscendingOrderTest() {
        Integer[] values = {-1, 0, 1, 2, 3, 4, 5, 6};
        MyArrayList<Integer> arr = new MyArrayList<>(values);

        arr.quickSort(Integer::compareTo);

        assertEquals(values.length, arr.size());
        assertArrayEquals(values, arr.toArray(new Integer[0]));
    }

    @Test
    void sortDescendingOrderTest() {
        Integer[] values = {6, 5, 4, 3, 2, 1, 0, -1};
        Integer[] sortValues = {-1, 0, 1, 2, 3, 4, 5, 6};
        MyArrayList<Integer> arr = new MyArrayList<>(values);

        arr.quickSort(Integer::compareTo);

        assertEquals(values.length, arr.size());
        assertArrayEquals(sortValues, arr.toArray(new Integer[0]));
    }

    @Test
    void sortAfterRemoveTest() {
        Integer[] values = {1, 28, 3, 4, 5, 1, 15, 6};
        Integer[] sortValues = {1, 3, 4, 5, 6, 15};
        MyArrayList<Integer> arr = new MyArrayList<>(values);

        arr.remove(0);
        arr.remove(0);

        arr.quickSort(Integer::compareTo);

        assertEquals(values.length - 2, arr.size());
        assertArrayEquals(sortValues, arr.toArray(new Integer[0]));
    }

    @Test
    void sortWithNullTest() {
        MyArrayList<Integer> arr = new MyArrayList<>(new Integer[]{1, 2, 3, 4, 5});

        arr.add(null);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            arr.quickSort(Integer::compareTo);
        });
    }

    @Test
    void sortSomeDataTest() {
        MyArrayList<DataForTest> arr = new MyArrayList<>(new DataForTest[]{
                new DataForTest(15, "first"),
                new DataForTest(2, "second"),
                new DataForTest(6, "third"),
        });

        arr.quickSort(new Comparator<DataForTest>() {
            @Override
            public int compare(DataForTest o1, DataForTest o2) {
                return Integer.compare(o1.n, o2.n);
            }
        });

        assertEquals(3, arr.size());

        assertEquals("second", arr.get(0).s);
        assertEquals("third", arr.get(1).s);
        assertEquals("first", arr.get(2).s);
    }

    @Test
    void sortWithNullComparatorTest() {
        Integer[] values = {0, 0, 0, 0, 0, 0, 0, 0};
        MyArrayList<Integer> arr = new MyArrayList<>(values);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            arr.quickSort(null);
        });

        assertEquals("Comparator can't be null",
                exception.getMessage());
    }

    @Test
    void toArrayTest() {
        Integer[] values = {1, 2, 3, 4, 5};
        MyArrayList<Integer> arrayList = new MyArrayList<>(values);

        Integer[] valuesFromArray = arrayList.toArray(new Integer[0]);
        assertArrayEquals(values, valuesFromArray);
    }
}

