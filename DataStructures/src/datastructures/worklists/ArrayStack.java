package datastructures.worklists;

import java.util.NoSuchElementException;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */

// For ArrayStack, you will use the following declaration: E[] array = (E[])new Object[SIZE];
//
public class ArrayStack<E> extends LIFOWorkList<E> {
	
	private int indexTop;
	private E[] array;
    
	public ArrayStack() {
      this.indexTop = 0;
      this.array = (E[])new Object[10];
    }

	@SuppressWarnings("unchecked")
    public void add(E work) {
    	
    	if((indexTop == array.length)) {
    		E[] temp = (E[]) new Object[array.length * 2];
    		for (int i = 0; i < array.length; i++) {
    			temp[i] = array[i];
    		}
    		this.array = temp;
    	}
        	array[this.indexTop++] = work;
       
    }

    @Override
    public E peek() {
    	
    	if(this.hasWork()) {
    		return array[indexTop - 1];
    	}
    	else {
    		throw new NoSuchElementException();
    	}
    }

    @Override
    public E next() { 	
    	
    	if(this.hasWork()) {
    		
    		E temp = array[indexTop - 1]; 		
        	array[indexTop - 1] = null;
        	this.indexTop--;
    		return temp;
    	}
    	else {
    		throw new NoSuchElementException();
    	}
    
    }

    @Override
    public int size() {
       return this.indexTop;
     
    }

    @Override
    public void clear() {
    	this.indexTop = 0;
        this.array = null;

    }
}
