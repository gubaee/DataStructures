package Experiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.HashTrieMap;
import datastructures.dictionaries.MoveToFrontList;

public class TestFive {

    public static void main(String[] args) throws FileNotFoundException {
        
        File file = new File("alice.txt");

        int NUM_TEST = 5000;
        int NUM_WARM_UP = 15;
        Set<String> set = new HashSet<>();

        double insertBST = 0;
        double insertAVL = 0;
        double insertHash = 0;
        double insertTrie = 0;

        double findBST = 0;
        double findAVL = 0;
        double findHash = 0;
        double findTrie = 0;
        
        for (int i = 0; i < NUM_TEST; i++) {
            
                Scanner scanner = new Scanner(file);
                
                BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
                AVLTree<String, Integer> avl = new AVLTree<>();
                ChainingHashTable<String, Integer> chtable = new ChainingHashTable<>(MoveToFrontList::new);
                HashTrieMap<Character, AlphabeticString, Integer> hashTrie = new HashTrieMap<>(AlphabeticString.class);
                
                double bstInsertTime = 0;
                double avlInsertTime = 0;
                double hashInsertTime = 0;
                double trieInsertTime = 0;

                while (scanner.hasNext()) {
                    
                    String temp = scanner.next();
                    
                    set.add(temp);
                }

                double timeBSTFind = 0;
                double timeAVLFind = 0;
                double timeHashFind = 0;
                double timeTriesFind = 0;
                
                for (String string : set) {
                    
                    bstInsertTime += insert(bst, string);
                    avlInsertTime += insert(avl, string);
                    hashInsertTime += insert(chtable, string);

                    
//                    timeBSTFind += insert(bst, string);
//                    timeAVLFind += insert(avl, string);
//                    timeHashFind += insert(chtable, string);

                    AlphabeticString astr = new AlphabeticString(string);
                    
                    long time = System.currentTimeMillis();  // start timing for instertion
                    
                    if (hashTrie.find(astr) == null) hashTrie.insert(astr, 1);
                    else hashTrie.insert(astr, hashTrie.find(astr) + 1);
                   
                    long timeEnd = System.currentTimeMillis();  // end timing for insertion
                    
                    trieInsertTime += (timeEnd - time);


                }

                
                for (String string : set) {
                    
                    timeBSTFind += find(bst, string);
                    timeAVLFind += find(avl, string);
                    timeHashFind += find(chtable, string);

                    AlphabeticString astr = new AlphabeticString(string);

                    long time = System.currentTimeMillis();
                    
                    hashTrie.find(astr);
                    
                    long timeEnd = System.currentTimeMillis();

                    timeTriesFind += timeEnd - time;

                }

                if (NUM_WARM_UP <= i) {
                    
                    insertBST += bstInsertTime;
                    insertAVL += avlInsertTime;
                    insertHash += hashInsertTime;
                    insertTrie += trieInsertTime;

                    findBST += timeBSTFind;
                    findAVL += timeAVLFind;
                    findHash += timeHashFind;
                    findTrie += timeTriesFind;
                }
                
            if (i % 2 == 0) {
                System.out.print(".");
            }
        }

        System.out.println();
        int average = NUM_TEST - NUM_WARM_UP;
        
        System.out.println("INSERT: ");
        System.out.println("BST Insert: " + insertBST / average + " ms");
        System.out.println("AVL Insert: " + insertAVL / average + " ms");
        System.out.println("Hash Insert: " + insertHash / average + " ms");
        System.out.println("TRIE Insert: " + insertTrie / average + " ms");
        
        System.out.println();
        
        System.out.println("FIND: ");
        System.out.println("BST Find: " + findBST / average + " ms");
        System.out.println("AVL Find: " + findAVL / average + " ms");
        System.out.println("Hash Find: " + findHash / average + " ms");
        System.out.println("Trie Find: " + findTrie / average + " ms");
    }

    private static long insert(DeletelessDictionary<String, Integer> dic, String s) {
        
        long startTime = System.currentTimeMillis();
        if (dic.find(s) == null) {
            dic.insert(s, 1);
        } else {
            dic.insert(s, dic.find(s) + 1);
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private static long find(DeletelessDictionary<String, Integer> dic, String s) {
        long startTime = System.currentTimeMillis();
        dic.find(s);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    }
