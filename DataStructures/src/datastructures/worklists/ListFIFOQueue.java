package datastructures.worklists;

import java.util.NoSuchElementException;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
//
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
	
	private static class ListNode<E> {
		
		E work;
		ListNode next;
		
		public ListNode(E work){
			
			this.work = work;
			this.next = null;
		}
//		public ListNode(E work, ListNode next){
//			this.work = work;
//			this.next = next;
//		}
	}

	ListNode front;
	ListNode back;
	int size;
	
    public ListFIFOQueue() {
    	
    	this.front = null;
    	this.back = null;
    	this.size = 0;
   
    }
    @Override
    public void add(E work) {
    	
    	ListNode node = new ListNode(work);
  	
    	if(this.back == null) {
    		this.front = node;
    		this.back = node;
    	}
    	else {
    		this.back.next = node;
    		this.back = this.back.next;
    	}
    	this.size++;
    }

    @Override
    public E peek() {
    	
    	if(this.hasWork()) {
    		ListNode node = this.front;
    		return (E) node.work;
    	}
    	else {
    		throw new NoSuchElementException();
    	}
    	
    }

    @Override
    public E next() {
    	
    	if(this.hasWork()) {
        	ListNode node = this.front;
        	this.front = this.front.next;
        	this.size--;
        	return (E) node.work;
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
    }
}
