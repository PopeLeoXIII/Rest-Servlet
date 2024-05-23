import arrayList.MyArrayList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortTest {
    @Test
    void sortTest() {
        int arraySize = 1000;

        // Создание массива
        int[] numbers = new int[arraySize];

        // Генератор случайных чисел
        Random random = new Random();

        for (int i = 0; i < arraySize; i++) {
            // Заполнение массива случайными значениями от 0 до 99
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

}

