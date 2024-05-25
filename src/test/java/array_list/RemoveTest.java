package array_list;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemoveTest {
    @Test
    void removeFromBeginTest() {
        Integer[] values = {1, 2, 3, 4, 5};
        MyArrayList<Integer> arr = new MyArrayList<>(values);

        for (Integer value : values) {
            Integer removeValue = arr.remove(0);
            assertEquals(value, removeValue);
        }

        assertEquals(0, arr.size());
        assertTrue(arr.isEmpty());
    }

    @Test
    void removeFromEndTest() {
        Integer[] values = {1, 2, 3, 4, 5};
        MyArrayList<Integer> arr = new MyArrayList<>(values);

        for (int i = arr.size() - 1; i >= 0; i--) {
            Integer removeValue = arr.remove(i);
            assertEquals(values[i], removeValue);
        }

        assertEquals(0, arr.size());
        assertTrue(arr.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, -2, -1, 5, 6, 100})
    void removeErrorTest(int getIndex) {
        MyArrayList<Integer> arr = new MyArrayList<>(new Integer[]{1, 2, 3, 4, 5});

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            arr.remove(getIndex);
        });

        assertEquals("Index: " + getIndex + ", Size: " + arr.size(),
                exception.getMessage());
    }

}
