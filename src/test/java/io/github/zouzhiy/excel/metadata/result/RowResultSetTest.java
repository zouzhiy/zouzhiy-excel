package io.github.zouzhiy.excel.metadata.result;

import io.github.zouzhiy.excel.support.metadata.DemoDefault;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

class RowResultSetTest {

    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void newInstance() {
        DemoDefault demoDefault = Mockito.mock(DemoDefault.class);
        int rowIndex = random.nextInt();
        int rowspan = random.nextInt();

        RowResultSet<DemoDefault> rowResultSet = RowResultSet.newInstance(demoDefault, rowIndex, rowspan);

        Assertions.assertEquals(rowResultSet.getData(), demoDefault);
        Assertions.assertEquals(rowResultSet.getRowIndex(), rowIndex);
        Assertions.assertEquals(rowResultSet.getRowspan(), rowspan);
    }
}
