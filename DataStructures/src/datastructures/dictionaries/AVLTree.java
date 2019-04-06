package datastructures.dictionaries;


import cse332.datastructures.trees.BinarySearchTree;

/**
 * TODO: Replace this comment with your own as appropriate.
 *
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 *
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 *    instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 *    children array or left and right fields in AVLNode.  This will 
 *    instead mask the super-class fields (i.e., the resulting node 
 *    would actually have multiple copies of the node fields, with 
 *    code accessing one pair or the other depending on the type of 
 *    the references used to access the instance).  Such masking will 
 *    lead to highly perplexing and erroneous behavior. Instead, 
 *    continue using the existing BSTNode children array.
 * 4. If this class has redundant methods, your score will be heavily
 *    penalized.
 * 5. Cast children array to AVLNode whenever necessary in your
 *    AVLTree. This will result a lot of casts, so we recommend you make
 *    private methods that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 */

public class AVLTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V>  {
    
    private static final int ALLOWED_IMBALACE = 1;

    private class AVLNode extends BSTNode{   
       
        public int height;
        
        public AVLNode(K key, V value) {
            super(key, value);
            this.height = 0;
        }
    }
    
    public AVLTree() {
        super();
        this.size = 0;
    }
  
    public int height(AVLNode t) {
        return t == null ? -1 : t.height;
    }
    
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        else {
            
            V returnValue = this.find(key);
            AVLNode newNode = new AVLNode(key,value);

            if(returnValue == null) {
                this.size++;
                returnValue = value;
            }
            this.root = makeAVLTree(newNode, (AVLNode) this.root);
            return returnValue;
        }
    }   
    
    public AVLNode makeAVLTree(AVLNode newNode, AVLNode root) {
        /*Base Case*/
        if (root == null) {
            return newNode;
        }
        
        /*Recursive Step*/
        /*If key < new node's key = make node into right position*/
        if(root.key.compareTo(newNode.key) < 0) {
            root.children[1] = makeAVLTree (newNode, (AVLNode) root.children[1]);
        }
        /*If key > new node's key = make node into left position*/
        else if(root.key.compareTo(newNode.key) > 0) { 
            root.children[0] = makeAVLTree (newNode, (AVLNode) root.children[0]);
        }
        /*If we have same key values, then update the value*/
        else {
            root.value = newNode.value;
        }
        return checkBalance(root);
    }
    
    private AVLNode rotateWithRightChild(AVLNode node1) {
        
        AVLNode node2 = (AVLNode) node1.children[1];
        node1.children[1] = node2.children[0];
        node2.children[0] = node1;
        node1.height = Math.max(height((AVLNode)node1.children[0]), height((AVLNode)node1.children[1])) + 1;
        node2.height = Math.max(height((AVLNode)node2.children[1]), node1.height) + 1;
        return node2;
    }
    
    private AVLNode doubleRotateWithRightChild(AVLNode node3) {
        node3.children[1] = rotateWithLeftChild((AVLNode) node3.children[1]);
        return rotateWithRightChild(node3);
    }
    
    
    private AVLNode rotateWithLeftChild(AVLNode node2) {
        
        AVLNode node1 = (AVLNode) node2.children[0];
        node2.children[0] = node1.children[1];
        node1.children[1] = node2;
        node2.height = Math.max(height((AVLNode) node2.children[0]), height((AVLNode) node2.children[1])) + 1;
        node1.height = Math.max(height((AVLNode) node1.children[0]), node2.height) + 1;
        return node1;
    }
    

    private AVLNode doubleRotateWithLeftChild(AVLNode node3) {
        node3.children[0] = rotateWithRightChild((AVLNode) node3.children[0]);
        return rotateWithLeftChild(node3);
    }
    
    
    private AVLNode checkBalance(AVLNode node) {
        
        if( node == null ) {
            return node;

        }
        
        if (height((AVLNode)node.children[0]) - height((AVLNode)node.children[1]) > ALLOWED_IMBALACE) {
            if( height((AVLNode)node.children[0].children[0]) >= height((AVLNode)node.children[0].children[1])) {
                node = rotateWithLeftChild(node);
            }
            else {
                node = doubleRotateWithLeftChild(node);
            }
                
        }
        else {
            if (height((AVLNode)node.children[1]) - height((AVLNode)node.children[0]) > ALLOWED_IMBALACE) {
                if( height((AVLNode)node.children[1].children[1]) >= height((AVLNode)node.children[1].children[0])) {
                    node = rotateWithRightChild(node);
                }
                else {
                    node = doubleRotateWithRightChild(node);
                }
                    
            }
        }
        node.height = Math.max(height((AVLNode)node.children[0]), height((AVLNode)node.children[1]))+1;
        return node;
    }
    
}
