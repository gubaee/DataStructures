package datastructures.worklists;

import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;



/**
 * See cse332/interfaces/worklists/PriorityWorkList.java for method
 * specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E>{
    /*
     * Do not change the name of this field; the tests rely on it to work correctly.
     */
    public E[] data;
    public int size;
    public Comparator<E> comparator;

    public MinFourHeap(Comparator<E> temp) {
        
        this.data = (E[]) new Object[10];
        this.comparator = temp;
        this.size = 0; // Node that has data inside.
        
    }

    @Override
    public boolean hasWork() {

        return this.size > 0;
    }

    @Override
    public void add(E work) {
        if (size == data.length) {
            E[] temp = (E[]) new Object[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                temp[i] = data[i];
            }
            this.data = temp;
        }

        this.size++;
        int i = percolateUp(this.size-1 , work);
        data[i] = work;
    }

    @Override
    public E peek() {
        if (hasWork()) {
            return data[0];
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public E next() {

        if (hasWork()) {
            E temp = data[0];
            
            int hole = percolateDown(0, data[size - 1]);
            data[hole] = data[size - 1];
            
            size--;
            return temp;
        }
        else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.data = null;

    }
    //int i = percolateUp(this.size-1 , work);
    
    private int percolateUp(int hole, E val) {
        // CompareTo = left value is bigger then return 1
        //             left value is smaller then return -1
        //val.compareTo(data[((hole-1) / 4)]) < 0 ) {
        while (hole >0 && comparator.compare(val, data[((hole-1) / 4)])<0) {
              
            data[hole] = data[((hole-1) / 4)];
            hole = ((hole-1) / 4);
        }

        return hole;
    }


    

    
    private int percolateDown(int hole, E value) {
        while(hole * 4  <= (size-1)) {
            int target = hole * 4 + 1;
            int second = hole * 4 + 2;
            int third = hole * 4 + 3;
            int fourth = hole * 4 + 4;
            if(target >= size) {
                break;
            }
            // comparator.compare(val, data[((hole-1) / 4)])
            //comparator.compare(data[target], data[second])
            if(second < size && comparator.compare(data[target], data[second]) > 0) {
                target = second;
            }
            if(third < size && comparator.compare(data[target], data[third]) >0) {
                target = third;
            }
            if(fourth < size && comparator.compare(data[target], data[fourth]) >0) {
                target = fourth;
            }
            
            if(comparator.compare(value, data[target]) > 0 ) {
                // CompareTo = left value is bigger then return 1
                //             left value is smaller then return -1
                data[hole] = data[target];
                hole = target;
            }else {
                
                break;
            }
            
        }
        return hole;
                
    }
    }
