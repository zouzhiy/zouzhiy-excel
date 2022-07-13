package io.github.zouzhiy.excel.metadata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

class CellSpanTest {

    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void newInstance1() {
        int rowspan = random.nextInt();
        int colspan = random.nextInt();

        CellSpan cellSpan = CellSpan.newInstance(rowspan, colspan);

        Assertions.assertEquals(cellSpan.getRowspan(), rowspan);
        Assertions.assertEquals(cellSpan.getColspan(), colspan);
    }

    @Test
    void newInstance2() {
        int firstRow = random.nextInt();
        int lastRow = random.nextInt();
        int firstCol = random.nextInt();
        int lastCol = random.nextInt();

        CellSpan cellSpan = CellSpan.newInstance(firstRow, lastRow, firstCol, lastCol);

        Assertions.assertEquals(cellSpan.getRowspan(), lastRow - firstRow + 1);
        Assertions.assertEquals(cellSpan.getColspan(), lastCol - firstCol + 1);
    }
}
