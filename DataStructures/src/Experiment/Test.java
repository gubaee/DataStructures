package Experiment;

import java.util.Random;

import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;

public class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
//        long elapsedTime = stopTime - startTime;
//        System.out.println(elapsedTime);

        
        
        int NUM_TEST = 10;
        int NUM_ITEM = 8000;
        int NUM_WARM_UP = 5;
        
        double averageinsertAVL = 0;
        double averageinsertBST = 0;
        double averagefindAVL = 0;
        double averagefindBST = 0;

        for(int i = 0; i < NUM_TEST; i++) {
            double avlinsertTime = 0; 
            double btsinsertTime = 0;
            double avlfindTime = 0;
            double btsfindTime = 0;
            AVLTree avl = new AVLTree();
            BinarySearchTree bts = new BinarySearchTree();
            
            for(int j = 0; j < NUM_ITEM; j++) {
                
                
                long avlinsertStart = System.currentTimeMillis();
                avl.insert(j,j);
                long avlinsertStop = System.currentTimeMillis();
                avlinsertTime += (avlinsertStop - avlinsertStart);
                
                
                
                long btsinsertStart = System.currentTimeMillis();
                bts.insert(j,j);
                long btsinsertStop = System.currentTimeMillis();
                btsinsertTime += (btsinsertStop - btsinsertStart);


//                long avlfindStart = System.currentTimeMillis();
//                avl.find(j,j);
//                long avlfindStop = System.currentTimeMillis();
//                avlfindTime += (avlfindStop - avlfindStart);
//                
//                
//                
//                long btsfindStart = System.currentTimeMillis();
//                bts.find(j,j);
//                long btsfindStop = System.currentTimeMillis();
//                btsfindTime += (btsfindStop - btsfindStart);
                
                

            }
            Random rand = new Random();
            
            for(int j = 0; j < NUM_ITEM; j++) {
                int value = rand.nextInt(NUM_ITEM);
                long avlfindStart = System.currentTimeMillis();
                avl.find(value);
                long avlfindStop = System.currentTimeMillis();
                avlfindTime += (avlfindStop - avlfindStart);
                
                
                
                long btsfindStart = System.currentTimeMillis();
                bts.find(value);
                long btsfindStop = System.currentTimeMillis();
                btsfindTime += (btsfindStop - btsfindStart);
            }

            if( NUM_WARM_UP <= i ) {
                averageinsertAVL +=  avlinsertTime;
                averageinsertBST += btsinsertTime;
                averagefindAVL +=  avlfindTime;
                averagefindBST += btsfindTime;
            }
            
           if(i % 2 == 0) {
               System.out.print(" . ");
           }
            
        }
        System.out.println();

        double averageinsertRuntime = averageinsertAVL / (NUM_TEST - NUM_WARM_UP);
        double averageinsertRuntimeBTS = averageinsertBST / (NUM_TEST - NUM_WARM_UP);
        
        double averagefindRuntime = averagefindAVL / (NUM_TEST - NUM_WARM_UP);
        double averagefindRuntimeBTS = averagefindBST / (NUM_TEST - NUM_WARM_UP);

        System.out.println("AVL insert TIME: " + averageinsertRuntime);
        System.out.println("BTS insert TIME: " + averageinsertRuntimeBTS);
        System.out.println("AVL find TIME: " + averagefindRuntime);
        System.out.println("BTS find TIME: " + averagefindRuntimeBTS);
//
        
        

    }

}
