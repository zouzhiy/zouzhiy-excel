package io.github.zouzhiy.excel.cellstyle.registry;

import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactory;
import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactoryBuilder;
import io.github.zouzhiy.excel.cellstyle.RowStyleRead;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.result.CellStyleResultSet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.junit.jupiter.api.Test;

/**
 * @author zouzhiy
 * @since 2022/7/3
 */
class RowStyleReadRegistryTest {
    private final ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().build();

    @Test
    void getConfiguration() {
        Configuration configuration = zouzhiyExcelFactory.getConfiguration();
        assert configuration.equals(configuration.getRowStyleReadRegistry().getConfiguration());
    }

    @Test
    void register() {
    }

    @Test
    void getMappingRowStyleRead() {
        try {
            RowStyleRead rowStyleRead = zouzhiyExcelFactory.getConfiguration().getRowStyleReadRegistry().getMappingRowStyleRead(null);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    void testGetMappingRowStyleRead() {
        try {
            RowStyleRead rowStyleRead = zouzhiyExcelFactory.getConfiguration().getRowStyleReadRegistry().getMappingRowStyleRead(new RowStyleRead() {
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
            }.getClass());
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }
}