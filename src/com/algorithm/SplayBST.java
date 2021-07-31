package com.algorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/******************************************************************************
 *  Compilation:  javac SplayBST.java
 *  Execution:    java SplayBST
 *  Dependencies: none
 *
 *  Splay tree. Supports splay-insert, -search, and -delete.
 *  Splays on every operation, regardless of the presence of the associated
 *  key prior to that operation.
 *
 *  Written by Josh Israel.
 *
 *  Modified by Sanath Jayasena
 *
 ******************************************************************************/


public class SplayBST<Key extends Comparable<Key>, Value> {

    private Node root;   // root of the BST

    // BST helper node data type
    private class Node {
        private Key key;            // key
        private Value value;        // associated data
        private Node left, right;   // left and right subtrees

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    // return value associated with the given key
    // if no such value, return null
    public Value get(Key key) {
        root = splay(root, key);
        int cmp = key.compareTo(root.key);
        if (cmp == 0) return root.value;
        else return null;
    }

    /***************************************************************************
     *  Splay tree insertion.
     ***************************************************************************/
    public void put(Key key, Value value) {
        // splay key to root
        if (root == null) {
            root = new Node(key, value);
            return;
        }

        root = splay(root, key);

        int cmp = key.compareTo(root.key);

        // Insert new node at root
        if (cmp < 0) {
            Node n = new Node(key, value);
            n.left = root.left;
            n.right = root;
            root.left = null;
            root = n;
        }

        // Insert new node at root
        else if (cmp > 0) {
            Node n = new Node(key, value);
            n.right = root.right;
            n.left = root;
            root.right = null;
            root = n;
        }

        // It was a duplicate key. Simply replace the value
        else {
            root.value = value;
        }

    }

    /***************************************************************************
     *  Splay tree deletion.
     ***************************************************************************/
    /* This splays the key, then does a slightly modified Hibbard deletion on
     * the root (if it is the node to be deleted; if it is not, the key was
     * not in the tree). The modification is that rather than swapping the
     * root (call it node A) with its successor, it's successor (call it Node B)
     * is moved to the root position by splaying for the deletion key in A's
     * right subtree. Finally, A's right child is made the new root's right
     * child.
     */
    public void remove(Key key) {
        if (root == null) return; // empty tree

        root = splay(root, key);

        int cmp = key.compareTo(root.key);

        if (cmp == 0) {
            if (root.left == null) {
                root = root.right;
            } else {
                Node x = root.right;
                root = root.left;
                splay(root, key);
                root.right = x;
            }
        }

        // else: it wasn't in the tree to remove
    }


    /***************************************************************************
     * Splay tree function.
     * **********************************************************************/
    // splay key in the tree rooted at Node h. If a node with that key exists,
    //   it is splayed to the root of the tree. If it does not, the last node
    //   along the search path for the key is splayed to the root.
    private Node splay(Node h, Key key) {
        if (h == null) return null;

        int cmp1 = key.compareTo(h.key);

        if (cmp1 < 0) {
            // key not in tree, so we're done
            if (h.left == null) {
                return h;
            }
            int cmp2 = key.compareTo(h.left.key);
            if (cmp2 < 0) {
                h.left.left = splay(h.left.left, key);
                h = rotateRight(h);
            } else if (cmp2 > 0) {
                h.left.right = splay(h.left.right, key);
                if (h.left.right != null)
                    h.left = rotateLeft(h.left);
            }

            if (h.left == null) return h;
            else return rotateRight(h);
        } else if (cmp1 > 0) {
            // key not in tree, so we're done
            if (h.right == null) {
                return h;
            }

            int cmp2 = key.compareTo(h.right.key);
            if (cmp2 < 0) {
                h.right.left = splay(h.right.left, key);
                if (h.right.left != null)
                    h.right = rotateRight(h.right);
            } else if (cmp2 > 0) {
                h.right.right = splay(h.right.right, key);
                h = rotateLeft(h);
            }

            if (h.right == null) return h;
            else return rotateLeft(h);
        } else return h;
    }


    /***************************************************************************
     *  Helper functions.
     ***************************************************************************/

    // height of tree (1-node tree has height 0)
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }


    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return 1 + size(x.left) + size(x.right);
    }

    // right rotate
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        return x;
    }

    // left rotate
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        return x;
    }

    public static void writeData(String file, String times) {
        try {
            File myObj = new File(file + ".txt");
            myObj.createNewFile();

        } catch (IOException e) {
            System.out.println("An error occurred In File creation.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(file + ".txt");
            myWriter.write(times);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred in file write.");
            e.printStackTrace();
        }
    }

    public static void timeCalculations(String currentDirectory, String file, String set, SplayBST bst) throws FileNotFoundException {
        String st;
        long k = 0;
        Long number;
        String insertPath = currentDirectory + "/src/com/algorithm/data/insert/"+ set +"/" + file+".txt";
        String searchPath = currentDirectory + "/src/com/algorithm/data/search/"+ set +"/" + file+".txt";
        String deletePath = currentDirectory + "/src/com/algorithm/data/delete/"+ set +"/" + file+".txt";

        BufferedReader insertBr = new BufferedReader(new FileReader(insertPath));
        BufferedReader searchBr = new BufferedReader(new FileReader(searchPath));
        BufferedReader deleteBr = new BufferedReader(new FileReader(deletePath));

        ArrayList<Long> insertArray = new ArrayList<>();
        ArrayList<Long> searchArray = new ArrayList<>();
        ArrayList<Long> deleteArray = new ArrayList<>();

        while (true) {
            try {
                if (!((st = insertBr.readLine()) != null)) break;
                for (String key: st.split(",")) {
                    number = Long.valueOf(key);
                    insertArray.add(number);
                    k = k + 1;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Number of insertions for file " + set +"/" + file + " is :- " + k);
        System.out.println("Number of Array Size for insertion file " + set +"/" + file + " is :- " + insertArray.size() + "\n");
        k = 0;

        while (true) {
            try {
                if (!((st = searchBr.readLine()) != null)) break;
                for (String key: st.split(",")) {
                    number = Long.valueOf(key);
                    searchArray.add(number);
                    k = k + 1;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Number of Search for file " + set +"/" + file + " is :- " + k);
        System.out.println("Number of Array Size for search file " + set +"/" + file + " is :- " + searchArray.size() + "\n");
        k = 0;

        while (true) {
            try {
                if (!((st = deleteBr.readLine()) != null)) break;
                for (String key: st.split(",")) {
                    number = Long.valueOf(key);
                    deleteArray.add(number);
                    k = k+ 1;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Number of Deletion for file " + set +"/" + file + " is :- " + k);
        System.out.println("Number of Array Size for deletions file " + set +"/" + file + " is :- " + deleteArray.size() + "\n");

        long startTime = System.nanoTime();

        for (long value: insertArray) {
            bst.put(value, value);
        }
        long insertTime = System.nanoTime() - startTime;

        long afterInsertTime = System.nanoTime();

        for (long value: searchArray) {
            bst.get(value);
        }

        long searchTime = System.nanoTime() - afterInsertTime;

        long afterSearchTime = System.nanoTime();

        for (long value: searchArray) {
            bst.remove(value);
        }
        long deleteTime = System.nanoTime() - afterSearchTime;

        writeData( currentDirectory + "/Times/Splay/" + set + "-" +file, String.valueOf(insertTime)+","+ String.valueOf(searchTime)+","+ String.valueOf(deleteTime));

        System.out.println("For "+ set + "\\" + file +"\n\tinsert Time "+ insertTime + "\n\tSearch Time " + searchTime + "\n\tDelete Time " + deleteTime);


    }

    // test client
    public static void main(String[] args) throws FileNotFoundException {
        SplayBST<Long, Integer> st = new SplayBST<>();

        String currentDirectory = System.getProperty("user.dir");

        File file1 = new File(currentDirectory + "/Times");
        File file2 = new File(currentDirectory + "/Times/Splay");
        boolean bool = file1.mkdir();
        bool = file2.mkdir();

        timeCalculations(currentDirectory, "data_1", "set1", st);
        timeCalculations(currentDirectory, "data_2", "set1", st);
        timeCalculations(currentDirectory, "data_3", "set1", st);

        timeCalculations(currentDirectory, "data_1", "set2", st);
        timeCalculations(currentDirectory, "data_2", "set2", st);
        timeCalculations(currentDirectory, "data_3", "set2", st);
    }

}