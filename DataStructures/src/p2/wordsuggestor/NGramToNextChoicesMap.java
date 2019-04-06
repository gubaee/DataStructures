package p2.wordsuggestor;

import java.util.Comparator;
import java.util.function.Supplier;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.Dictionary;
import cse332.misc.LargeValueFirstItemComparator;
import cse332.sorts.InsertionSort;
import cse332.types.AlphabeticString;
import cse332.types.NGram;
import p2.sorts.TopKSort;

public class NGramToNextChoicesMap {
    private final Dictionary<NGram, Dictionary<AlphabeticString, Integer>> map;
    private final Supplier<Dictionary<AlphabeticString, Integer>> newInner;

    public NGramToNextChoicesMap(
            Supplier<Dictionary<NGram, Dictionary<AlphabeticString, Integer>>> newOuter,
            Supplier<Dictionary<AlphabeticString, Integer>> newInner) {
        this.map = newOuter.get();
        this.newInner = newInner;
    }
  
    public void seenWordAfterNGram(NGram ngram, String word) {
        
        AlphabeticString aString = new AlphabeticString(word);

        /*If the map does not contain key, then add key and value (Initialize)*/
        if(this.map.find(ngram) == null) {
//            System.err.println("Result: " + this.map.find(ngram));

            this.map.insert(ngram, this.newInner.get());
//            System.err.println("Result22: " + this.map.find(ngram));

            this.map.find(ngram).insert(aString, 1);
//            System.err.println("Result: " + this.map.find(ngram));

        }
        
        
        
        /*If the map contains key, then and value and increase int*/
        else {

            if(this.map.find(ngram).find(aString) != null)
            {   
//                System.err.println("aString"+ aString);
                this.map.find(ngram).insert(aString, this.map.find(ngram).find(aString) + 1);
//                System.err.println("Result: " + this.map.find(ngram));
            }
            
            else {
                this.map.find(ngram).insert(aString, 1);
            }
        }
//        System.err.println("Result: " + this.map.find(ngram));

    }
    

    /**
     * Returns an array of the DataCounts for this particular ngram. Order is
     * not specified.
     *
     * @param ngram
     *            the ngram we want the counts for
     * 
     * @return An array of all the Items for the requested ngram.
     */
    public Item<String, Integer>[] getCountsAfter(NGram ngram) {
        
        Item<String, Integer>[] array;

         if(this.map.find(ngram) == null) {           
            array = (Item<String, Integer>[]) new Item[0];
            return array;
        }
        else {        
            
            array = (Item<String, Integer>[]) new Item[this.map.find(ngram).size()];
            Item<String, Integer> temp;
            
            String mapString = this.map.find(ngram).toString();
            String key = mapString.substring(mapString.indexOf("{") + 1,mapString.indexOf("}")).replace(" ", "");
            String[] test = key.split(",");
            
            for(int i = 0; i < test.length; i++) {
                String itemKey = test[i].substring(0,test[i].indexOf("="));
                String itemValue = test[i].substring(test[i].indexOf("=") + 1);
                temp = new Item(itemKey, Integer.valueOf(itemValue));
                array[i] = temp;
            }
            return array;
            
        }
      
    }

      


 

    public String[] getWordsAfter(NGram ngram, int k) {
        Item<String, Integer>[] afterNGrams = getCountsAfter(ngram);

        Comparator<Item<String, Integer>> comp = new LargeValueFirstItemComparator<String, Integer>();
        if (k < 0) {
            InsertionSort.sort(afterNGrams, comp);
        }
        else {
             TopKSort.sort(afterNGrams, k,comp.reversed());
             if(k > afterNGrams.length) { 
                 k = afterNGrams.length;
             }
             Item<String, Integer>[] passItem = new Item[k];
             
              for (int i = 0; i < k; i++) {
                 passItem[i] = afterNGrams[k-i-1];
             }
             afterNGrams = passItem;
        }

        String[] nextWords = new String[k < 0 ? afterNGrams.length : k];
        for (int l = 0; l < afterNGrams.length && l < nextWords.length
                && afterNGrams[l] != null; l++) {
            nextWords[l] = afterNGrams[l].key;
        }
        return nextWords;
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
