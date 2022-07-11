package io.github.zouzhiy.excel.handler;

import io.github.zouzhiy.excel.support.metadata.DemoDefault;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NoneCellHandlerTest extends CellHandlerTest {

    private final NoneCellHandler cellHandler = new NoneCellHandler();

    @Test
    @Override
    void getJavaType() {
        Assertions.assertThrows(Exception.class, cellHandler::getJavaType);
    }

    @Test
    @Override
    void getExcelType() {
        Assertions.assertThrows(Exception.class, cellHandler::getExcelType);
    }

    @Test
    @Override
    void read() {
        Assertions.assertThrows(Exception.class, () -> cellHandler.read(sheetContext, excelFieldConfig, cellResultSet));
    }

    @Test
    @Override
    void write() {
        Assertions.assertThrows(Exception.class, () -> cellHandler.write(rowContext, 1, excelFieldConfig, new DemoDefault()));
    }

    @Test
    @Override
    void getWriteRowspan() {
        Assertions.assertEquals(cellHandler.getWriteRowspan(new DemoDefault()), 1);

    }

    @Test
    @Override
    void getDefaultJavaFormat() {
        Assertions.assertEquals(cellHandler.getDefaultJavaFormat(), "");

    }

    @Test
    @Override
    void getDefaultExcelFormat() {
        Assertions.assertEquals(cellHandler.getDefaultExcelFormat(), "");
    }
}
