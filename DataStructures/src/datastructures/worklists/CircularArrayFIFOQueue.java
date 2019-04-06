package datastructures.worklists;

import java.util.NoSuchElementException;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */

//For CircularArrayFIFOQueue and MinFourHeap in P1, you will use: E[] array = (E[])new Comparable[SIZE];
public class CircularArrayFIFOQueue<E extends Comparable> extends FixedSizeFIFOWorkList<E> {
    
    private int front,back,size;
    private E[] array;
    
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.front = 0;
        this.back = 0;
        this.size = 0;
        this.array = (E[])new Comparable[capacity];
    }

    @Override
    public void add(E work) {
        
        /*if(!this.isFull()) {
            this.array[back] = work;
            back = back+1;
        }
        else {
            throw new IllegalStateException();
        }
        */
        
        if(!this.isFull()) {
            this.array[back] = work;
            back = (back + 1)%array.length;
            this.size++;
        }else {
            throw new IllegalStateException();
        }
    }

    @Override
    public E peek() {
        if(this.hasWork()) {
            return this.array[front];
        }
        else {
            throw new NoSuchElementException();
        }
        
    }
    
    @Override
    public E peek(int i) {
        if( 0 <= i && i <size()) {
            if(this.hasWork()) {
                return this.array[front + i];
            }
            else {
                throw new NoSuchElementException();
            }
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }
    
    @Override
    public E next() {

        if(this.hasWork()) {
            E temp = this.array[front];
            this.array[front] = null;
            front = (front + 1)%array.length;
            this.size--;
            return temp;
        }else {
            throw new NoSuchElementException();
        }
    }
    
    
    @Override
    public void update(int i, E value) {
        if( 0 <= i && i <size()) {
            if(this.hasWork()) {
            this.array[front + i] = value;
            }
            else {
                throw new NoSuchElementException();
            }
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }
    
    @Override
    public int size() {

        return this.size;
    }
    
    @Override
    public void clear() {
        this.front = 0;
        this.back = 0;
        this.size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        
        int comSize = Math.min(this.size(), other.size());
        
        for (int i = 0; i < comSize; i++) {
           
            if(this.peek(i).compareTo(other.peek(i)) != 0) {
                return this.peek(i).compareTo(other.peek(i));
            }
            
        }
        
        if(this.size() > other.size()) {
            return 1;
        }if(this.size() < other.size()) {
            return -1;
        }       
        return 0;
        }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        }
        else {
            
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
           
            if(this.size() != other.size())
                return false;
            else {
                for(int i = 0; i < this.size(); i++) {
                    if(this.peek(i) != other.peek(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method
        // unchanged for project 1
        int prime = 31;
        int result = 1;
        
        for (int i = 0; i < this.array.length; i++) {
            result = (prime*result + ((this.array[i] == null) ? 0 : this.array[i].hashCode()));
            //System.err.println(result);
        }
        //System.err.println(result);
        return result;

    }
}
