package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.BString;
import cse332.interfaces.trie.TrieMap;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map.Entry;


/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<ChainingHashTable<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
//            this.pointers = new HashMap<A, HashTrieNode>();
            this.pointers = new ChainingHashTable<>(MoveToFrontList::new);
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieNode>> iterator() {
            
            Iterator<Item<A, HashTrieNode>> chainingHashIterator = pointers.iterator();
            
            Iterator<Entry<A, HashTrieNode>> returnIterator = new Iterator<Entry<A, HashTrieNode>>() {
                @Override
                public boolean hasNext() {
                    return chainingHashIterator.hasNext();
                }
                @Override
                public Entry<A, HashTrieNode> next() {
                    Item<A, HashTrieNode> returnItem = chainingHashIterator.next();
                    Entry<A, HashTrieNode> returnEntry =  new AbstractMap.SimpleEntry<>(returnItem.key, returnItem.value);
                    return returnEntry;
                }
            };
            return returnIterator;
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {

        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }

        // the current root to traverse
        HashTrieNode temp = (HashTrieMap<A, K, V>.HashTrieNode) root;

        for (A findKey : key) {
            if (temp.pointers.find(findKey) != null) {
                temp = temp.pointers.find(findKey);

            }else {
                temp.pointers.insert(findKey, new HashTrieNode());
                temp = temp.pointers.find(findKey);

            }
        }

        V returnvalue = temp.value;

        if (returnvalue == null) {
            size++;
        }
        
        temp.value = value;

        return returnvalue;
    }

    @Override
    public V find(K key) {
        
        if (key == null) {
            throw new IllegalArgumentException();
        }

        HashTrieNode tempHold = (HashTrieMap<A, K, V>.HashTrieNode) root;

        for (A tempKey : key) {
            
            if (tempHold.pointers.find(tempKey) != null) {
                tempHold = tempHold.pointers.find(tempKey);

            }else {
                return null;

            }
        }
        return tempHold.value;
    }

    @Override
    public boolean findPrefix(K key) {

        if (key == null) {
            throw new IllegalArgumentException();
        }else {
        
            HashTrieNode temp = (HashTrieMap<A, K, V>.HashTrieNode) root;

            boolean result = true;
            for (A findkey : key) {
               if (temp.pointers.find(findkey) != null) {
                  temp = temp.pointers.find(findkey);
               } else {

                  result = false;
               }
            }
            return result;
        }


    }


    @Override
    public void delete(K key) {
        HashTrieNode temp = (HashTrieMap<A, K, V>.HashTrieNode) this.root;
        HashTrieNode temp2 = (HashTrieMap<A, K, V>.HashTrieNode) this.root;
        HashTrieNode multichildnode = null;
        A holdch = null;
        A lastch = null;
    
        if(key == null) {
            throw new IllegalArgumentException();
        }
        else {
            for(A findkey: key) {
                if(temp.pointers.find(findkey) == null) {
                   return;
                }
                else {
                     if(temp.pointers.size() > 1 || temp.value != null) {
                         multichildnode = temp;
                         holdch = findkey;
                     }                     
                }
                temp = temp.pointers.find(findkey); 
                lastch = findkey;
            }              
            if(temp.pointers.size() > 0 ) {
                temp.value = null; 
            }

            else if(multichildnode != null) {
                multichildnode.pointers.delete(holdch);

            }
            if(temp.value == null) {
                return;
            }
            
           temp.value = null;
           this.size--;
        }
        if(temp2.pointers.size()<=1) {
            temp2.pointers.delete(lastch);
        }
        return;
    }
    


    @Override
    public void clear() {

        
        HashTrieNode temp = (HashTrieMap<A, K, V>.HashTrieNode) this.root;
        temp.pointers.clear();
        this.size = 0;

    }
}