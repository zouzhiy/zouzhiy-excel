package io.github.zouzhiy.excel.read.registry;

import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.read.RowHeadRead;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RowHeadReadRegistryTest {

    @Test
    void getConfiguration() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowHeadReadRegistry rowHeadReadRegistry = new RowHeadReadRegistry(configuration);
        Assertions.assertEquals(rowHeadReadRegistry.getConfiguration(), configuration);
    }


    @Test
    void getMappingRowRead1() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowHeadReadRegistry rowHeadReadRegistry = new RowHeadReadRegistry(configuration);

        Assertions.assertNotNull(rowHeadReadRegistry.getMappingRowRead(RowHeadReadRegistry.DEFAULT_ROW_HEAD_READ_CLASS));
    }

    @Test
    void register() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowHeadReadRegistry rowHeadReadRegistry = new RowHeadReadRegistry(configuration);
        RowHeadRead rowHeadRead = Mockito.mock(RowHeadRead.class);

        Assertions.assertThrows(ExcelException.class, () -> rowHeadReadRegistry.getMappingRowRead(rowHeadRead.getClass()));

        rowHeadReadRegistry.register(rowHeadRead);

        Assertions.assertEquals(rowHeadReadRegistry.getMappingRowRead(rowHeadRead.getClass()), rowHeadRead);
    }
}
