package p2.sorts;

import java.lang.reflect.Array;
import java.util.Comparator;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

public class QuickSort {
    final static int CUTOFF = 1;
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quicksort(array,0,array.length-1,comparator);
    }
    public static <E> void quicksort( E [] arr, int lo , int hi,Comparator<E> comparator) {

        if(lo<hi) {
            int partitionIndex = insertionSort(arr,lo,hi,comparator);
       
            quicksort(arr,lo,partitionIndex-1,comparator);
            quicksort(arr,partitionIndex+1,hi,comparator);
        }
    }
    public static <E> int insertionSort(E [] arr,int lo,int hi,Comparator<E> comparator) {
        E pivot = arr[hi];
        int i = lo-1;
        for (int j = lo; j < hi; j ++) {
            if (comparator.compare(pivot, arr[j]) >=0 ) {
                i++;
                
                E swaptemp = arr[i];
                
                arr[i] = arr[j];
                arr[j] = swaptemp;
            }
        }
        
        E swapTemp = arr[i+1];
        arr[i+1] = arr[hi];
        arr[hi] = swapTemp;
        
        return i+1;
    }
    
}
