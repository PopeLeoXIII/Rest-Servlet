package array_list;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddTest {
    @Test
    void addIntegerTest() {
        MyArrayList<Integer> arr = new MyArrayList<>(3);

        int items = 10;
        for (int i = 0; i < items; i++) {
            arr.add(i);
        }

        assertEquals(items, arr.size());
    }

    @Test
    void addStringTest() {
        MyArrayList<String> arr = new MyArrayList<>();

        int items = 10;
        for (int i = 0; i < items; i++) {
            arr.add("str");
        }

        assertEquals(items, arr.size());
    }
    @Test
    void addSomeDataTest() {
        MyArrayList<DataForTest> arr = new MyArrayList<>();

        arr.add(new DataForTest(15, "first"));
        arr.add(new DataForTest(2, "second"));
        arr.add(new DataForTest(6, "third"));

        assertEquals(3, arr.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void addByIndexTest(int index) {
        Integer newItem = 9;

        MyArrayList<Integer> arr = new MyArrayList<>(new Integer[]{1, 2, 3, 4, 5});
        arr.add(index, newItem);

        assertEquals(newItem, arr.get(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, -2, -1, 5, 6, 100})
    void addOutOfBounds(int index) {
        MyArrayList<Integer> arr = new MyArrayList<>(new Integer[]{1, 2, 3, 4, 5});

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            arr.add(index, 6);
        });

        assertEquals("Index: " + index + ", Size: " + arr.size(),
                exception.getMessage());
    }

}