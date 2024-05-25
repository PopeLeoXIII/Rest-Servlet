package array_list;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConstructorTest {
    @Test
    void constructorTest() {
        MyArrayList<Integer> array = new MyArrayList<>();
        assertEquals(0, array.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 5, 100, 100000})
    void constructorWithCapacityTest(int capacity) {
        MyArrayList<Object> array = new MyArrayList<>(capacity);
        assertEquals(0, array.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, -2, -1})
    void constructorCapacityErrorTest(int capacity) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            MyArrayList<String> arr = new MyArrayList<>(capacity);
        });

        assertEquals("Illegal Capacity: " + capacity,
                exception.getMessage());
    }

    @Test
    void constructorWithArrayTest() {
        Integer[] values = {1, 2, 3, 4, 5};
        MyArrayList<Object> array = new MyArrayList<>(values);
        assertEquals(values.length, array.size());
        assertArrayEquals(values, array.toArray(new Integer[0]));
    }

    @Test
    void constructorNullArrayTest() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            MyArrayList<String> arr = new MyArrayList<>(null);
        });
    }

}
