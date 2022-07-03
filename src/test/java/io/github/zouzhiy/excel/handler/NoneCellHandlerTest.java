package io.github.zouzhiy.excel.handler;

import io.github.zouzhiy.excel.exceptions.ExcelException;
import org.junit.jupiter.api.Test;

/**
 * @author zouzhiy
 * @since 2022/7/3
 */
class NoneCellHandlerTest {

    @Test
    void getJavaType() {
        NoneCellHandler noneCellHandler = new NoneCellHandler();
        try {
            noneCellHandler.getJavaType();
            assert false;
        } catch (ExcelException e) {
            assert true;
        }
    }

    @Test
    void getExcelType() {
        NoneCellHandler noneCellHandler = new NoneCellHandler();
        try {
            noneCellHandler.getExcelType();
            assert false;
        } catch (ExcelException e) {
            assert true;
        }
    }

    @Test
    void read() {
        NoneCellHandler noneCellHandler = new NoneCellHandler();
        try {
            noneCellHandler.read(null, null, null);
            assert false;
        } catch (ExcelException e) {
            assert true;
        }
    }

    @Test
    void write() {
        NoneCellHandler noneCellHandler = new NoneCellHandler();
        try {
            noneCellHandler.write(null, null, null, null);
            assert false;
        } catch (ExcelException e) {
            assert true;
        }
    }
}