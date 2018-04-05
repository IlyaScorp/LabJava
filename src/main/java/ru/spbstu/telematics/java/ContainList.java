package ru.spbstu.telematics.java;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;


/**
 * Hello world!
 */
public class ContainList {
    private File file;

    public ContainList(String str) {
        if (Objects.isNull(str)) {
            throw new NullPointerException();
        }

        file = new File(str);

        if (!file.isDirectory()){
            throw new IllegalArgumentException("Path isn't directory");
        }
    }

    public String[] list(){

        return file.list();

    }

    public static void main(String[] args) {

        ContainList list = new ContainList("/home/ilya/Documents/JavaParallel/");
        Arrays.stream(list.list()).forEach(System.out::println);


    }


}
