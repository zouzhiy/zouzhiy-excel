package io.github.zouzhiy.excel.write.registry;

import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.write.RowHeadWrite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RowHeadWriteRegistryTest {


    @Test
    void getConfiguration() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowHeadWriteRegistry rowHeadWriteRegistry = new RowHeadWriteRegistry(configuration);
        Assertions.assertEquals(rowHeadWriteRegistry.getConfiguration(), configuration);
    }


    @Test
    void getMappingRowRead1() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowHeadWriteRegistry rowHeadWriteRegistry = new RowHeadWriteRegistry(configuration);

        Assertions.assertNotNull(rowHeadWriteRegistry.getMappingRowWrite(RowHeadWriteRegistry.DEFAULT_ROW_HEAD_WRITE_CLASS));
    }

    @Test
    void register() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowHeadWriteRegistry rowHeadWriteRegistry = new RowHeadWriteRegistry(configuration);
        RowHeadWrite rowHeadWrite = Mockito.mock(RowHeadWrite.class);

        Assertions.assertThrows(ExcelException.class, () -> rowHeadWriteRegistry.getMappingRowWrite(rowHeadWrite.getClass()));

        rowHeadWriteRegistry.register(rowHeadWrite);

        Assertions.assertEquals(rowHeadWriteRegistry.getMappingRowWrite(rowHeadWrite.getClass()), rowHeadWrite);
    }
}
