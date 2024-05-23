import arrayList.MyArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GetSetTest {
    @Test
    void setAndGetTest() {
        int arraySize = 10000;
         // Создание массива
        MyArrayList<Integer> myArray = new MyArrayList<>(arraySize);

        // Генератор случайных чисел

        int[] numbers = new int[arraySize];
        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            // Заполнение массива случайными значениями от 0 до 99
            numbers[i] = random.nextInt(100);
            myArray.add(0);
        }

        for (int i = 0; i < arraySize; i++) {
            myArray.set(i, numbers[i]);
        }

        for (int i = 0; i < arraySize; i++) {
            assertEquals(numbers[i], myArray.get(i));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, -2, -1, 5, 6, 100})
    void getOutOfBounds(int getIndex) {
        MyArrayList<Integer> arr = new MyArrayList<>(new Integer[]{1, 2, 3, 4, 5});

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            arr.get(getIndex);
        });

        assertEquals("Index: " + getIndex + ", Size: " + arr.size(),
                exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, -2, -1, 5, 6, 100})
    void setOutOfBounds(int setIndex) {
        MyArrayList<Integer> arr = new MyArrayList<>(new Integer[]{1, 2, 3, 4, 5});

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            arr.set(setIndex, 6);
        });

        assertEquals("Index: " + setIndex + ", Size: " + arr.size(),
                exception.getMessage());
    }
}
