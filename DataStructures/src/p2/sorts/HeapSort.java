package p2.sorts;

import java.util.Comparator;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;
import datastructures.worklists.MinFourHeap;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        PriorityWorkList<E> heap = new MinFourHeap(comparator);

        for (int i = 0; i < array.length; i++) {
            heap.add(array[i]);
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = heap.next();
        }

    }
}
