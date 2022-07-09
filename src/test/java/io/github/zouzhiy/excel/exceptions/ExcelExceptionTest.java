package io.github.zouzhiy.excel.exceptions;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

class ExcelExceptionTest {

    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void excelException1() {
        ExcelException excelException = new ExcelException("errorMsg");
        Assertions.assertEquals(excelException.getMessage(), "errorMsg");
    }

    @Test
    void excelException2() {
        int value = random.nextInt(1000);
        ExcelException excelException = new ExcelException("errorMsg:%s", value);
        Assertions.assertEquals(excelException.getMessage(), "errorMsg:" + value);
    }

    @Test
    void excelException3() {
        RuntimeException runtimeException = new RuntimeException("test");
        ExcelException excelException = new ExcelException("errorMsg", runtimeException);
        Assertions.assertEquals(excelException.getMessage(), "errorMsg");
        Assertions.assertEquals(excelException.getCause(), runtimeException);
    }

    @Test
    void excelException4() {
        int value = random.nextInt(1000);
        RuntimeException runtimeException = new RuntimeException("test");
        ExcelException excelException = new ExcelException(runtimeException, "errorMsg:%s", value);
        Assertions.assertEquals(excelException.getMessage(), "errorMsg:" + value);
        Assertions.assertEquals(excelException.getCause(), runtimeException);
    }

    @Test
    void excelException5() {
        RuntimeException runtimeException = new RuntimeException("test");
        ExcelException excelException = new ExcelException(runtimeException);
        Assertions.assertEquals(excelException.getCause(), runtimeException);
    }

    @Test
    void excelException6() {
        RuntimeException runtimeException = new RuntimeException("test");
        ExcelException excelException = new ExcelException("errorMsg", runtimeException, false, true);
        Assertions.assertEquals(excelException.getMessage(), "errorMsg");
        Assertions.assertEquals(excelException.getCause(), runtimeException);
    }

}
