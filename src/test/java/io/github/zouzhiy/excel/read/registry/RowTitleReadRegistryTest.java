package io.github.zouzhiy.excel.read.registry;

import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.read.RowTitleRead;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RowTitleReadRegistryTest {

    @Test
    void getConfiguration() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowTitleReadRegistry rowTitleReadRegistry = new RowTitleReadRegistry(configuration);
        Assertions.assertEquals(rowTitleReadRegistry.getConfiguration(), configuration);
    }


    @Test
    void getMappingRowRead1() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowTitleReadRegistry rowTitleReadRegistry = new RowTitleReadRegistry(configuration);

        Assertions.assertNotNull(rowTitleReadRegistry.getMappingRowRead(RowTitleReadRegistry.DEFAULT_ROW_TITLE_READ_CLASS));
    }

    @Test
    void register() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowTitleReadRegistry rowTitleReadRegistry = new RowTitleReadRegistry(configuration);
        RowTitleRead rowTitleRead = Mockito.mock(RowTitleRead.class);

        Assertions.assertThrows(ExcelException.class, () -> rowTitleReadRegistry.getMappingRowRead(rowTitleRead.getClass()));

        rowTitleReadRegistry.register(rowTitleRead);

        Assertions.assertEquals(rowTitleReadRegistry.getMappingRowRead(rowTitleRead.getClass()), rowTitleRead);
    }
}
