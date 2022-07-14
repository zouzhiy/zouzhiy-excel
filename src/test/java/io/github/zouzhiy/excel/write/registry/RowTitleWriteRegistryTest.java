package io.github.zouzhiy.excel.write.registry;

import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.write.RowTitleWrite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RowTitleWriteRegistryTest {

    @Test
    void getConfiguration() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowTitleWriteRegistry rowTitleWriteRegistry = new RowTitleWriteRegistry(configuration);
        Assertions.assertEquals(rowTitleWriteRegistry.getConfiguration(), configuration);
    }


    @Test
    void getMappingRowRead1() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowTitleWriteRegistry rowTitleWriteRegistry = new RowTitleWriteRegistry(configuration);

        Assertions.assertNotNull(rowTitleWriteRegistry.getMappingRowWrite(RowTitleWriteRegistry.DEFAULT_ROW_TITLE_WRITE_CLASS));
    }

    @Test
    void register() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowTitleWriteRegistry rowTitleWriteRegistry = new RowTitleWriteRegistry(configuration);
        RowTitleWrite rowTitleWrite = Mockito.mock(RowTitleWrite.class);

        Assertions.assertThrows(ExcelException.class, () -> rowTitleWriteRegistry.getMappingRowWrite(rowTitleWrite.getClass()));

        rowTitleWriteRegistry.register(rowTitleWrite);

        Assertions.assertEquals(rowTitleWriteRegistry.getMappingRowWrite(rowTitleWrite.getClass()), rowTitleWrite);
    }
}
