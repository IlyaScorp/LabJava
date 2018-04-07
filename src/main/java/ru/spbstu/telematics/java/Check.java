package ru.spbstu.telematics.java;

import java.io.ObjectInputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class Check {

    public static void main(String[] args) {

        Random rnd = new Random();
        for (int i = 0; i < 40; i++) {
            System.out.println(rnd.nextInt(50));
        }
    }
}

