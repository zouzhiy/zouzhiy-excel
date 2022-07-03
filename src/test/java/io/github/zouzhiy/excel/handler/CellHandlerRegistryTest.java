package io.github.zouzhiy.excel.handler;

import io.github.zouzhiy.excel.builder.Demo;
import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;
import org.junit.jupiter.api.Test;

/**
 * @author zouzhiy
 * @since 2022/7/3
 */
class CellHandlerRegistryTest {

    private Configuration configuration = new Configuration();

    @Test
    void register() {

    }

    @Test
    void getMappingCellHandler() {
        CellHandlerRegistry cellHandlerRegistry = configuration.getCellHandlerRegistry();
        try {

            cellHandlerRegistry.getMappingCellHandler(new CellHandler<String>() {

                @Override
                public Class<String> getJavaType() {
                    return String.class;
                }

                @Override
                public ExcelType getExcelType() {
                    return ExcelType.STRING;
                }

                @Override
                public String read(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResultSet cellResultSet) {
                    return null;
                }

                @Override
                public void write(RowContext rowContext, Integer columnIndex, ExcelFieldConfig excelFieldConfig, String value) {

                }
            }.getClass());
            assert false;
        } catch (ExcelException e) {
            assert true;
        }
    }

    @Test
    void getCellHandler() {
        CellHandlerRegistry cellHandlerRegistry = configuration.getCellHandlerRegistry();
        try {
            cellHandlerRegistry.getCellHandler(Demo.class, ExcelType.STRING);
            assert false;
        } catch (ExcelException e) {
            assert true;
        }
    }

    @Test
    void testGetCellHandler() {
    }

    @Test
    void getConfiguration() {
    }
}