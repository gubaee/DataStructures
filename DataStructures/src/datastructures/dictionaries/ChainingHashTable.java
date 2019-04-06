package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. You must implement a generic chaining hashtable. You may not
 * restrict the size of the input domain (i.e., it must accept
 * any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should resizing as appropriate (use load factor as
 * shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 * than 200,000 elements. After more than 200,000 elements, it should
 * continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 * list: http://primes.utm.edu/lists/small/100000.txt
 * NOTE: Do NOT copy the whole list!
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    
    private final static double LOAD_FACTOR = 0.8; 
    private final int[] BUCKET_SIZE = {  31, 61, 151, 359, 701, 1481, 2851, 6073, 12437, 24029 , 48049, 94117, 211859};
    
    private int sizeIndex;  
    
    private final Supplier<Dictionary<K, V>> newChain;
    private Dictionary<K, V>[] bucket;

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;

  
        bucket = new Dictionary[17];
        sizeIndex = 0;
    }

    @Override
    public V insert(K key, V value) {
        if (((double) this.size / (double) this.bucket.length) >= this.LOAD_FACTOR) {
            resizing();
        }
        
        
        int index = hash(key, bucket.length);

        if (bucket[index] == null) {
          
            bucket[index] = this.newChain.get();
        }
    
        Dictionary<K, V> currentChain = bucket[index];

        int previousSize = currentChain.size();
        V returnValue = currentChain.insert(key, value);
        
        if(previousSize!=currentChain.size()) {
            this.size++;
        }

        return returnValue;
    }

    @Override
    public V find(K key) {
         
        return this.bucket[hash(key,bucket.length)] == null ? null : this.bucket[hash(key,bucket.length)].find(key);
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
  
        Iterator iter = new Iterator() {
            int bucketNum = -1;  
            Iterator<Item<K, V>> bucketIterator; 

            @Override
            public boolean hasNext() {
                if (bucketNum == -1||!bucketIterator.hasNext()) {
                    seeNext();
                }

                if (bucketIterator == null) {
                    return false;
                }
                return bucketIterator.hasNext();
            }

            @Override
            public Object next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return bucketIterator.next();
            }


            private void seeNext() {
                bucketNum++;
                while (bucketNum < bucket.length && (bucket[bucketNum] == null || bucket[bucketNum].isEmpty())) {
                    bucketNum++;
                }
                if (bucketNum >= bucket.length) {
                    bucketIterator = null;
                    return;
                }
                bucketIterator = bucket[bucketNum].iterator();
            }
        };

        return iter;

    }

    /**
     * Assign the key to appropriate hashing number
     *
     * @param key the key to be hashed
     * @return hash number
     */
    private int hash(K key, int size) {
        int hashcode = key.hashCode() * 31;

        return hashcode <0 ? Math.abs(hashcode)%size : hashcode%size ;
    }

    private void resizing() {

        Dictionary<K, V>[] temp;

        if (this.sizeIndex < BUCKET_SIZE.length) {
            temp = new Dictionary[BUCKET_SIZE[sizeIndex++]];
        } else {
            temp = new Dictionary[this.bucket.length * 2];
        }

        Iterator<Item<K, V>> iterator = iterator();
        while (iterator.hasNext()) {
            Item<K, V> item = iterator.next();
            int index = hash(item.key, temp.length);
            if (temp[index] == null) {
                temp[index] = this.newChain.get();
            }
            Dictionary<K, V> chain = temp[index];
            chain.insert(item.key, item.value);
        }

        
        this.bucket = temp;
    }
}
