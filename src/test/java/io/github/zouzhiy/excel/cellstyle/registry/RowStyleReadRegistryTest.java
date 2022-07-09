package io.github.zouzhiy.excel.cellstyle.registry;

import io.github.zouzhiy.excel.cellstyle.RowStyleRead;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.result.CellStyleResultSet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RowStyleReadRegistryTest {


    @Test
    void register() {
        RowStyleRead rowStyleRead = new RowStyleRead() {
            @Override
            public CellStyle readTitle(SheetContext sheetContext) {
                return null;
            }

            @Override
            public CellStyleResultSet readHead(SheetContext sheetContext) {
                return null;
            }

            @Override
            public CellStyleResultSet readData(SheetContext sheetContext) {
                return null;
            }
        };

        RowStyleReadRegistry rowStyleReadRegistry = new RowStyleReadRegistry(new Configuration());
        rowStyleReadRegistry.register(rowStyleRead);
        RowStyleRead rowStyleRead1 = rowStyleReadRegistry.getMappingRowStyleRead(rowStyleRead.getClass());

        assert rowStyleRead1 == rowStyleRead;
    }


    @Test
    void getMappingRowStyleRead() {
        RowStyleRead rowStyleRead = new RowStyleRead() {
            @Override
            public CellStyle readTitle(SheetContext sheetContext) {
                return null;
            }

            @Override
            public CellStyleResultSet readHead(SheetContext sheetContext) {
                return null;
            }

            @Override
            public CellStyleResultSet readData(SheetContext sheetContext) {
                return null;
            }
        };
        RowStyleReadRegistry rowStyleReadRegistry = new RowStyleReadRegistry(new Configuration());

        try {
            rowStyleReadRegistry.getMappingRowStyleRead(rowStyleRead.getClass());
            assert false;
        } catch (ExcelException e) {
            Assertions.assertEquals(e.getMessage(), "不存在的：CellStyleRead");
        }

    }


}
