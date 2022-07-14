package io.github.zouzhiy.excel.read.registry;

import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.read.RowFootRead;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RowFootReadRegistryTest {

    @Test
    void getConfiguration() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowFootReadRegistry rowFootReadRegistry = new RowFootReadRegistry(configuration);
        Assertions.assertEquals(rowFootReadRegistry.getConfiguration(), configuration);
    }


    @Test
    void getMappingRowRead1() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowFootReadRegistry rowFootReadRegistry = new RowFootReadRegistry(configuration);

        Assertions.assertNotNull(rowFootReadRegistry.getMappingRowRead(RowFootReadRegistry.DEFAULT_ROW_FOOT_READ_CLASS));
    }

    @Test
    void register() {
        Configuration configuration = Mockito.mock(Configuration.class);
        RowFootReadRegistry rowFootReadRegistry = new RowFootReadRegistry(configuration);
        RowFootRead rowFootRead = Mockito.mock(RowFootRead.class);

        Assertions.assertThrows(ExcelException.class, () -> rowFootReadRegistry.getMappingRowRead(rowFootRead.getClass()));

        rowFootReadRegistry.register(rowFootRead);

        Assertions.assertEquals(rowFootReadRegistry.getMappingRowRead(rowFootRead.getClass()), rowFootRead);
    }
}
