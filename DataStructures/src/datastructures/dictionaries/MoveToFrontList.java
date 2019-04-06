package datastructures.dictionaries;

import java.util.Iterator;

import cse332.datastructures.containers.*;
import cse332.interfaces.misc.DeletelessDictionary;

/**
 * TODO: Replace this comment with your own as appropriate. 
 * 1. The list is typically not sorted. 
 * 2. Add new items to the front of the list. 
 * 3. Whenever find is called on an item, move it to the front of the list. 
 *    This means you remove the node from its current position and make 
 *    it the first node in the list. 
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 *    elements to the front. The iterator should return elements 
 *    in the order they are stored in the list, starting with the first
 *    element in the list.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {

    Node front;
    Node back;

    public MoveToFrontList() {
        this.front = null;
        this.back = null;
        this.size = 0;
        
    }

    public static class Node<K, V>  {
        
        final K key;
        V value;
        Node next;
        Node prev;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }

    @Override
    public V insert(K key, V value) {

        if (key == null ) { 
            throw new IllegalArgumentException();
        }
        
        else {   
            
            V tempValue = this.find(key);
            
            if(tempValue == null) {
                
                Node node = new Node(key, value);
                V returnValue = null;
                if(this.size == 0) {
                    this.front = node;
                    this.back = node;
                    this.size++;

                    return value;
                }else {
                    returnValue = (V)this.front.value;
                    this.front.prev = node;
                    node.next = this.front;
                    this.front = node;
                    this.size++;

                    return returnValue;
                }
            }
            else {
                this.front.value = value;
                return tempValue;
            }
            
        }
        
    }
    @Override
    public V find(K key) {

        if(key == null) {
            throw new IllegalArgumentException();
        }
        else {
            

            Node current = this.front;
            
            while(current != null) {
                if(current.key.equals(key) && current.prev == null) {
                    return (V) current.value;
                }
                else if(current.key.equals(key)  && current.prev != null && current.next != null) {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    this.back = this.back.prev;
                    
                    current.next = this.front;
                    current.prev = null;
                    
                    this.front.prev = current;
                    this.front = current;
                    

                    
                    return (V) current.value;
                }
                else if(current.key.equals(key)  && current.next == null) {
                    current.prev.next = null;
                    this.back = this.back.prev;
                    current.next = this.front;
                    current.prev = null;
                    
                    this.front.prev = current;
                    this.front = current;
                    
                    return (V) current.value;
                    
                }
                else {
                    current = current.next;
                }
            }
            return null;
        }
    }

    @Override
    public Iterator<Item<K, V>> iterator() {

        return new ListIterator();
        
    }

    private class ListIterator implements Iterator<Item<K, V>> {

        Node current;

        public ListIterator() {
            
            current = MoveToFrontList.this.front;
        }

        @Override
        public boolean hasNext() {

            return current != null;
        }
        
        public Item<K, V> next() {

            if (hasNext()) {
                Item<K, V> item = new Item<>((K)this.current.key,(V)this.current.value);
                this.current = this.current.next;
                return item;

            }else {
                return null;
            }
            
        }
    }
}

