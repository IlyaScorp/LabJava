package ru.spbstu.telematics.java;

import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class TestCollection {

    private ArrayList<Integer> arrSys;          // system's collection
    private ArrayCollection<Integer> arrUser;   // user's collection
    private Random random;
    private int caseNumber;


    @BeforeTest
    public void beforeTest() throws Exception {
        caseNumber = 0;
        arrSys = new ArrayList<>();
        arrUser = new ArrayCollection<>();
        random = new Random(System.currentTimeMillis());
    }

    // it invokes several times to check all cases and generate different number lists
    @Test(enabled = true)
    public void testAddRemoveScale() {
        int amount;
        boolean flag = true;    //This flag split the different methods to be tested
        int put;
        int idx;
        for (int k = 0; k < 11; k++) {              //нужен для смены флага добалние/удаление элемента
            amount = random.nextInt(400) + 1;
            for (int i = 0; i < amount; i++) {
                put = random.nextInt(256);
                put = ((put & 0x1) == 1) ? put : -put;      //it generates negative and positive numbers
                if (flag) {
                    if (i <= amount / 2) {
                        Assert.assertEquals(arrUser.add(put), arrSys.add(put), "Error during adding elements by Item");

                    } else {
                        idx = getIndex();
                        arrUser.add(idx, put);
                        arrSys.add(idx, put);
                        Assert.assertEquals(arrUser.size(), arrSys.size(), "Error during adding elements by Item and index. Checking size");
                    }
                } else {
                    if ((idx = getIndex()) == -1) {
                        continue;
                    }

                    if (i < amount / 2) {
                        Assert.assertEquals(arrUser.remove(idx), arrSys.remove(idx), "Error during removing elements by index");
                    } else {
                        Assert.assertEquals(arrUser.remove(new Integer(put)), arrSys.remove(new Integer(put)), "Error during removing elements by Item");
                    }

                }
                if ((idx = getIndex()) == -1) {
                    continue;
                }
                Assert.assertEquals(arrUser.size(), arrSys.size(), "Error during checking the size");
                Assert.assertEquals(arrUser.get(idx), arrSys.get(idx));
                Assert.assertEquals(arrUser.contains(put), arrSys.contains(put), "Error during checking data");
            }
            flag = !flag;
        }


    }

    private int getIndex() {
        switch (arrSys.size()) {    // генерируем индекс в границах массива, чтоб
            case 0:
                return -1;
            case 1:
                return 0;            // тест не падал раньше времени
            default:
                return random.nextInt(arrSys.size() - 1);
        }

    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class, invocationCount = 2)
    public void testNullExp() {
        switch (caseNumber) {
            case 0:
                arrUser.add(arrUser.size() + 1,1);
                break;
            case 1:
                arrUser.remove(arrUser.size());
                break;
        }
        caseNumber++;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalExp() {
        new ArrayCollection<>(-12);
    }

    @Test
    public void testIterators() {
        Iterator<Integer> iterUser = arrUser.iterator();
        Iterator<Integer> iterSys = arrSys.iterator();
        while (iterSys.hasNext() && iterUser.hasNext()) {

            Assert.assertEquals(iterSys.hasNext(), iterUser.hasNext());
            Assert.assertEquals(iterSys.next(), iterUser.next());

        }

    }
}
