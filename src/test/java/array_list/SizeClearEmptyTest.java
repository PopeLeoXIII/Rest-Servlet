package array_list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SizeClearEmptyTest {
    @Test
    void sizeTest() {
        MyArrayList<Integer> arrayWith5Elements = new MyArrayList<>(new Integer[]{1, 2, 3, 4, 5});
        assertEquals(5, arrayWith5Elements.size());

        MyArrayList<Integer> arrayWith0Elements = new MyArrayList<>();
        assertEquals(0, arrayWith0Elements.size());

        MyArrayList<Integer> arrayCapacity3Elements = new MyArrayList<>(3);
        assertEquals(0, arrayCapacity3Elements.size());

        arrayWith0Elements.add(6);
        arrayWith0Elements.add(7);

        assertEquals(2, arrayWith0Elements.size());
    }

    @Test
    void isEmptyTest() {
        MyArrayList<String> arrayWith2Elements = new MyArrayList<>(new String[]{"One", "Two"});
        assertFalse(arrayWith2Elements.isEmpty());

        arrayWith2Elements.remove(0);
        arrayWith2Elements.remove(0);
        assertTrue(arrayWith2Elements.isEmpty());

        MyArrayList<Integer> arrayWith0Elements = new MyArrayList<>();
        assertTrue(arrayWith0Elements.isEmpty());

        arrayWith0Elements.add(1);
        assertFalse(arrayWith0Elements.isEmpty());

        MyArrayList<Integer> arrayCapacity3Elements = new MyArrayList<>(3);
        assertTrue(arrayCapacity3Elements.isEmpty());

        arrayCapacity3Elements.add(1);
        assertFalse(arrayCapacity3Elements.isEmpty());
    }


    @Test
    void clearTest() {
        MyArrayList<String> arrayWith2Elements = new MyArrayList<>(new String[]{"One", "Two"});
        arrayWith2Elements.clear();

        assertTrue(arrayWith2Elements.isEmpty());
    }
}