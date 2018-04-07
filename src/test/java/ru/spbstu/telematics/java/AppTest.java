package ru.spbstu.telematics.java;


import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;


public class AppTest {

    ContainList check;
    File createFile;
    String NEWFOLDER = "/home/ilya/Documents/JavaParallel/tmp/";

    @BeforeTest
    public void init() throws Exception {
        createFile = new File(NEWFOLDER);
        createFile.mkdir();
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateObjException() {

        check = new ContainList("a random string");
    }

    @Test
    public void testCreateDirectory() {

        createFile = new File(NEWFOLDER + ".folder");
        createFile.mkdir();
        check = new ContainList(NEWFOLDER);
        Assert.assertEquals(check.list()[0], ".folder");

    }

    @AfterTest
    public void clean() {
        createFile.delete();
        createFile = new File(NEWFOLDER);
        createFile.delete();
    }
}
