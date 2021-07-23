package com.algorithm;

import com.algorithm.Trees.BST;
import com.algorithm.Trees.RedBlackBST;
import com.algorithm.Trees.SplayBST;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        String insertSet11Path = "/src/com/algorithm/data/insert/set1/data_1.txt";
        String insertSet12Path = "/src/com/algorithm/data/insert/set1/data_2.txt";
        String insertSet13Path = "/src/com/algorithm/data/insert/set1/data_3.txt";

        String insertSet21Path = "/src/com/algorithm/data/insert/set2/data_1.txt";
        String insertSet22Path = "/src/com/algorithm/data/insert/set2/data_2.txt";
        String insertSet23Path = "/src/com/algorithm/data/insert/set2/data_3.txt";

        String deleteSet11Path = "/src/com/algorithm/data/delete/set1/data_1.txt";
        String deleteSet12Path = "/src/com/algorithm/data/delete/set1/data_2.txt";
        String deleteSet13Path = "/src/com/algorithm/data/delete/set1/data_3.txt";

        String deleteSet21Path = "/src/com/algorithm/data/delete/set1/data_1.txt";
        String deleteSet22Path = "/src/com/algorithm/data/delete/set1/data_2.txt";
        String deleteSet23Path = "/src/com/algorithm/data/delete/set1/data_3.txt";

        String searchSet11Path = "/src/com/algorithm/data/search/set1/data_1.txt";
        String searchSet12Path = "/src/com/algorithm/data/search/set1/data_2.txt";
        String searchSet13Path = "/src/com/algorithm/data/search/set1/data_3.txt";

        String searchSet21Path = "/src/com/algorithm/data/search/set1/data_1.txt";
        String searchSet22Path = "/src/com/algorithm/data/search/set1/data_2.txt";
        String searchSet23Path = "/src/com/algorithm/data/search/set1/data_3.txt";

        BST<Integer, Integer> bst = new BST<Integer, Integer>();
        RedBlackBST<Integer, Integer> redBlackBST = new RedBlackBST<Integer, Integer>();
        SplayBST<Integer, Integer> splayBST = new SplayBST<Integer, Integer>();
//        bst.put(1,1);
//        bst.put(21,21);
//        bst.put(12,12);
//        bst.put(41,41);
//        bst.put(13,13);
//        bst.put(10,10);

        bst.print();


	    System.out.println("Hello");

        String currentDirectory = System.getProperty("user.dir");

        File file = new File(currentDirectory + insertSet11Path);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String st;
        while (true) {
            try {
                if (!((st = br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
