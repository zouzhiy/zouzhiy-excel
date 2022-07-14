package io.github.zouzhiy.excel.write.registry;

import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.write.RowFootWrite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RowFootWriteRegistryTest {

    @Test
    void getConfiguration() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowFootWriteRegistry rowFootWriteRegistry = new RowFootWriteRegistry(configuration);
        Assertions.assertEquals(rowFootWriteRegistry.getConfiguration(), configuration);
    }


    @Test
    void getMappingRowRead1() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowFootWriteRegistry rowFootWriteRegistry = new RowFootWriteRegistry(configuration);

        Assertions.assertNotNull(rowFootWriteRegistry.getMappingRowWrite(RowFootWriteRegistry.DEFAULT_ROW_FOOT_WRITE_CLASS));
    }

    @Test
    void register() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowFootWriteRegistry rowFootWriteRegistry = new RowFootWriteRegistry(configuration);
        RowFootWrite rowFootWrite = Mockito.mock(RowFootWrite.class);

        Assertions.assertThrows(ExcelException.class, () -> rowFootWriteRegistry.getMappingRowWrite(rowFootWrite.getClass()));

        rowFootWriteRegistry.register(rowFootWrite);

        Assertions.assertEquals(rowFootWriteRegistry.getMappingRowWrite(rowFootWrite.getClass()), rowFootWrite);
    }
}
