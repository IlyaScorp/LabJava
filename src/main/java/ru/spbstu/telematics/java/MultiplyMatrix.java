package ru.spbstu.telematics.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * Hello world!
 */
public class MultiplyMatrix extends Thread {

    private int[][] one;
    private int[][] two;
    private int one_n;
    private int one_m;
    private int two_m;
    private int[][] result;
    private Thread th;

    private static final int NUMBEROFCORES;
    private ThreadPoolExecutor poolThread;
    private List<Future<int[]>> future;

    private MatrixInterface inter;

    static {
        NUMBEROFCORES = Runtime.getRuntime().availableProcessors();
    }

    public MultiplyMatrix(int[][] one, int[][] two) {
        if (Objects.isNull(one) || Objects.isNull(two)) {
            throw new NullPointerException();
        }
        if ((one_m = checkDimension(one)) != two.length | (two_m = checkDimension(two)) == -1) {
            throw new IllegalArgumentException();
        }
        one_n = one.length;
        this.one = one;
        this.two = two;

    }
    /*
    The interface are required to return the result of multiplied matrices to user
     */
    public void setInterface(MatrixInterface inter) {
        if (!Objects.isNull(inter)) {
            this.inter = inter;
        } else {
            throw new NullPointerException();
        }
    }
    /*
    This method checks array rows contain the same number of elements
     */
    private int checkDimension(int[][] arr) {
        int length = arr[0].length;
        for (int i = 0; i < arr.length; i++) {
            if (length != arr[i].length) {
                return -1;
            }
        }
        return length;
    }

    Thread multiply() {
        future = new LinkedList<>();
        result = new int[one_n][];
        poolThread = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMBEROFCORES);
        th = new Thread(this);
        th.start();
        return th;

    }

    @Override
    public void run() {

        for (int k = 0; k < one_n; k++) {
            future.add(poolThread.submit(new ExecRow(k)));
        }

        for (int i = 0; i < one_n; i++) {
            try {
                result[i] = future.get(i).get();
//                System.out.println(future.get(i).isDone());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        inter.result(result);
        poolThread.shutdown();

    }

    class ExecRow implements Callable<int[]> {

        private int step;

        ExecRow(int k) {
            this.step = k;
        }

        @Override
        public int[] call() {
            int[] tmp = new int[two_m];
            int element = 0;
            for (int i = 0; i < two_m; i++) {

                for (int j = 0; j < one_m; j++) {
                    element += one[step][j] * two[j][i];
                }
                tmp[i] = element;
                element = 0;
            }
            return tmp;
        }
    }

    // threadpoolExecutors Callable Future
}
