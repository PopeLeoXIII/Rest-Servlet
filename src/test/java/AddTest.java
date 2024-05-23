import arrayList.MyArrayList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddTest {
    @Test
    void addIntegerTest() {
        int items = 10;
        MyArrayList<Integer> arr = new MyArrayList<>(3);
        arr.print();
        for (int i = 0; i < items; i++) {
            arr.add(i);
            arr.print();
        }
        assertEquals(items, arr.size());
    }

    @Test
    void addStringTest() {
        int items = 10;
        MyArrayList<String> arr = new MyArrayList<>();
        arr.print();
        for (int i = 0; i < items; i++) {
            arr.add("str");
            arr.print();
        }
        assertEquals(items, arr.size());
    }

    @Test
    void addToBeginTest() {
        Integer newItem = 9;
        int index = 4;

        MyArrayList<Integer> arr = new MyArrayList<>(new Integer[]{1, 2, 3, 4, 5});
        arr.print();
        arr.add(index, newItem);
        arr.print();

        assertEquals(newItem, arr.get(index));
    }

    @Test
    void addOutOfBounds() {
        int insertIndex = 6;
        MyArrayList<Integer> arr = new MyArrayList<>(new Integer[]{1, 2, 3, 4, 5});

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            arr.add(insertIndex, 6);
        });

        assertEquals("Index: " + insertIndex + ", Size: " + arr.size(),
                exception.getMessage());
    }

}
