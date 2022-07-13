package io.github.zouzhiy.excel.metadata;

import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author zouzhiy
 * @since 2022/7/13
 */
class MergedRegionTest {

    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void getCellRangeAddress1() {
        List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();
        MergedRegion mergedRegion = new MergedRegion(cellRangeAddressList);

        Assertions.assertNull(mergedRegion.getCellRangeAddress(1, 1));
    }

    @Test
    void getCellRangeAddress2() {
        List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();

        CellRangeAddress cellRangeAddress1 = CellRangeAddress.valueOf("A1:A2");
        cellRangeAddressList.add(cellRangeAddress1);

        CellRangeAddress cellRangeAddress2 = CellRangeAddress.valueOf("B1:C5");
        cellRangeAddressList.add(cellRangeAddress2);

        MergedRegion mergedRegion = new MergedRegion(cellRangeAddressList);

        Assertions.assertEquals(mergedRegion.getCellRangeAddress(0, 0), cellRangeAddress1);
        Assertions.assertEquals(mergedRegion.getCellRangeAddress(0, 1), cellRangeAddress2);
        Assertions.assertNull(mergedRegion.getCellRangeAddress(2, 3));
    }

    @Test
    void getCellSpan1() {
        List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();
        MergedRegion mergedRegion = new MergedRegion(cellRangeAddressList);

        Assertions.assertEquals(mergedRegion.getCellSpan(1, 1), CellSpan.DEFAULT_CELL_SPAN);
    }

    @Test
    void getCellSpan2() {
        List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();

        CellRangeAddress cellRangeAddress1 = CellRangeAddress.valueOf("A1:A2");
        cellRangeAddressList.add(cellRangeAddress1);

        CellRangeAddress cellRangeAddress2 = CellRangeAddress.valueOf("B1:C5");
        cellRangeAddressList.add(cellRangeAddress2);

        MergedRegion mergedRegion = new MergedRegion(cellRangeAddressList);

        Assertions.assertEquals(mergedRegion.getCellSpan(0, 0), CellSpan.newInstance(2, 1));
        Assertions.assertEquals(mergedRegion.getCellSpan(0, 1), CellSpan.newInstance(5, 2));
        Assertions.assertEquals(mergedRegion.getCellSpan(2, 3), CellSpan.DEFAULT_CELL_SPAN);
    }

    @Test
    void isInMergedRegionAndNotFirst1() {
        List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();

        CellRangeAddress cellRangeAddress1 = CellRangeAddress.valueOf("A1:A2");
        cellRangeAddressList.add(cellRangeAddress1);

        CellRangeAddress cellRangeAddress2 = CellRangeAddress.valueOf("B1:E5");
        cellRangeAddressList.add(cellRangeAddress2);

        MergedRegion mergedRegion = new MergedRegion(cellRangeAddressList);

        Assertions.assertFalse(mergedRegion.isInMergedRegionAndNotFirst(0, 0));
        Assertions.assertFalse(mergedRegion.isInMergedRegionAndNotFirst(0, 1));
        Assertions.assertTrue(mergedRegion.isInMergedRegionAndNotFirst(2, 3));
    }


    @Test
    void unHasMergedRegion() {

        List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();

        CellRangeAddress cellRangeAddress1 = CellRangeAddress.valueOf("A1:A2");
        cellRangeAddressList.add(cellRangeAddress1);

        CellRangeAddress cellRangeAddress2 = CellRangeAddress.valueOf("B1:E5");
        cellRangeAddressList.add(cellRangeAddress2);

        MergedRegion mergedRegion = new MergedRegion(cellRangeAddressList);

        Assertions.assertFalse(mergedRegion.unHasMergedRegion(0, 0, 0, 0));
        Assertions.assertFalse(mergedRegion.unHasMergedRegion(0, 0, 1, 2));
        Assertions.assertFalse(mergedRegion.unHasMergedRegion(2, 2, 3, 3));
        Assertions.assertTrue(mergedRegion.unHasMergedRegion(5, 5, 3, 3));
    }


}
