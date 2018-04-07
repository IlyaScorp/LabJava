package ru.spbstu.telematics.java;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class TestMatrix extends PutConfig implements MatrixInterface {
    private int one_n;
    private int one_m;
    private int two_n;
    private int two_m;
    private int[][] one;
    private int[][] two;
    int caseTest = 0;

    private static int[][] multiplied;

    private MultiplyMatrix matrix;
    private WebElement element;

    private Random rand;
    private String SETSIZE = "#frtabl > p:nth-child(%d) > select:nth-child(%d) > option:nth-child(%d)";
    private String SETELEMENT = "#oms_%s_%d_%d";
    private String GETWEBRESULT = "#res > div:nth-child(2) > mfenced:nth-child(6) > mtable > mtr:nth-child(%d) > mtd:nth-child(%d)";


    @BeforeClass
    public void initMainElements() {
        System.setProperty("webdriver.chrome.driver", config.pathToDriver());
        Configuration.browser = "CHROME";
        Configuration.headless = true;
        rand = new Random();

    }

    @BeforeMethod
    public void initMatrices() {
        one_n = rand.nextInt(4) + 1;    //кол-во строк 1 матрицы.
        one_m = rand.nextInt(4) + 1;    //кол-во столбцов 1 матрицы.
        two_m = rand.nextInt(4) + 1;    //кол-во столбцов 2 матрицы.
        // Кол-во столбцов 1-ой = кол-ву строк 2-ой. one_m = two_n
        switch (caseTest) {
            case 0:
                two_n = one_m;              //Check the correct result will be received
                break;
            case 1:
                two_n = one_m + 1;          //Check the IllegalExp will be thrown
                break;
            case 2:
                one = null;                 //Check the NullPointerExp will be thrown
                caseTest = 0;
                return;
        }
        one = new int[one_n][one_m];
        two = new int[two_n][two_m];
        initMatrix(one, one_n, one_m);
        initMatrix(two, two_n, two_m);
        caseTest++;

    }


    @Test(priority = 0)
    public void mainTest() {
        Thread joinMult;

        matrix = new MultiplyMatrix(one, two);
        TestMatrix tm = new TestMatrix();
        matrix.setInterface(tm);
        joinMult = matrix.multiply();
        /*
       У меня есть экземпляр калсса MultiplyMatrix,
       в нем метода run, завершения котрого я жду.
       Почему если сделать matrix.join() это не помогает?
       Приходится поступать так, как показано выше, создавая переменную треда и явно
       возвращать ему выполняемый тред
         */

        webMultiply();                          //It multiplies matrices on site

        try {
            joinMult.join();                    //We are waiting user's multiply will be finished
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //We take a matrix element from site and assert with element of multiplied matrix
        int tmp;
        for (int i = 0; i < one_n; i++) {
            for (int j = 0; j < two_m; j++) {
                element = $(String.format(GETWEBRESULT, i + 1, j + 1));
                tmp = Integer.parseInt(element.getText());
                Assert.assertEquals(multiplied[i][j], tmp);
            }
        }


    }

    @Test(priority = 1, expectedExceptions = IllegalArgumentException.class, enabled = true)
    public void checkIllegalExp() {

        matrix = new MultiplyMatrix(one, two);
    }

    @Test(priority = 2, expectedExceptions = NullPointerException.class, enabled = true)
    public void checkNullExp() {

        matrix = new MultiplyMatrix(one, two);
    }

    @Test(priority = 3, enabled = true)
    public void testCheckDimension() {
        matrix = new MultiplyMatrix(one, two);
        int[][] correctArr = new int[2][4];
        int[][] incorrectArr = new int[2][];
        incorrectArr[0] = new int[4];
        incorrectArr[1] = new int[5];
        Method method;

        try {
            method = MultiplyMatrix.class.getDeclaredMethod("checkDimension", int[][].class);
            method.setAccessible(true);
            Assert.assertEquals(method.invoke(matrix, (Object) correctArr), 4,
                    "Reply is incorrect");
            Assert.assertEquals(method.invoke(matrix, (Object) incorrectArr), -1,
                    "Reply is incorrect");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void initMatrix(int[][] arr, int rows, int columns) {
        int put;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                put = rand.nextInt(40);
                arr[i][j] = ((put & 0x1) == 1) ? put : -put;
            }
        }
    }

    private void webMultiply() {
        open("http://ru.onlinemschool.com/math/assistance/matrix/multiply/");
        setDimension(2, 1, one_n);
        setDimension(2, 2, one_m);
        setDimension(3, 1, two_n);
        setDimension(3, 2, two_m);
        setElements(one, 'a', one_n, one_m);
        setElements(two, 'b', two_n, two_m);
        element.sendKeys(Keys.TAB, Keys.ENTER);

    }

    private void setDimension(int number, int rowcolumn, int size) {

        element = $(String.format(SETSIZE, number, rowcolumn, size));
        element.click();

    }

    private void setElements(int[][] instMatrix, char nameMatrix, int row, int column) {

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                element = $(String.format(SETELEMENT, nameMatrix, i + 1, j + 1));
                element.sendKeys(Integer.toString(instMatrix[i][j]));
            }
        }
    }


    @Override
    public void result(int[][] arr) {

        multiplied = arr;
    }
}
