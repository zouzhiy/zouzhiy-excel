package io.github.zouzhiy.excel.exceptions;

import org.junit.jupiter.api.Test;

/**
 * @author zouzhiy
 * @since 2022/7/3
 */
class ExcelExceptionTest {

    @Test
    void test() {
        ExcelException excelException = new ExcelException();
        assert excelException.getMessage() == null;

        ExcelException excelException1 = new ExcelException("dd");
        assert excelException1.getMessage().equals("dd");

        ExcelException excelException2 = new ExcelException("dd%s", "d");
        assert excelException2.getMessage().equals("ddd");


        RuntimeException runtimeException3 = new RuntimeException("test");
        ExcelException excelException3 = new ExcelException("dd", runtimeException3);
        assert excelException3.getMessage().equals("dd");
        assert excelException3.getCause().equals(runtimeException3);

        ExcelException excelException4 = new ExcelException(runtimeException3);
        assert !excelException4.getMessage().equals(runtimeException3.getMessage());
        System.out.println(excelException4.getMessage());
        assert excelException4.getCause().equals(runtimeException3);

        ExcelException excelException5 = new ExcelException("dd", runtimeException3, false, false);
        assert excelException5.getCause().equals(runtimeException3);

    }

}