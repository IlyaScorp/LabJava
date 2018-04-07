package ru.spbstu.telematics.java;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Check implements MatrixInterface {

    private int[][] listResult;
    private Thread th;

    @Override
    public void result(int[][] arr) {
        listResult = arr;
    }

    int[][] getResult(){
        return listResult;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*int[][] arr = new int[][]{{1, 2, 3}, {5, 5, 2}, {1,1,1}};
        int[][] arr2 = new int[][]{{1, 2}, {5, 5}, {3, 0}}; //2x2
        MultiplyMatrix mm =  new MultiplyMatrix(arr,arr2);
        Check ck = new Check();
        mm.setInterface(ck);
        mm.multiply().join();

        for(int[] row : ck.getResult()){
            System.out.println(Arrays.toString(row));
        }*/
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
