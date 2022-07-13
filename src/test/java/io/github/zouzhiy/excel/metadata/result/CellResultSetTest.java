package io.github.zouzhiy.excel.metadata.result;

import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
public class CellResultSetTest {

    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void empty() {
        CellResultSet cellResultSet = CellResultSet.none();
        Assertions.assertTrue(cellResultSet.isNone());
        Assertions.assertThrows(ExcelException.class, cellResultSet::getFirstCellResult);
        Assertions.assertThrows(ExcelException.class, cellResultSet::getExcelType);
        Assertions.assertTrue(cellResultSet.getCellResultListList().isEmpty());
    }

    @Test
    void newInstance() {
        List<List<CellResult>> cellResultListList = new ArrayList<>();

        List<CellResult> cellResultList1 = new ArrayList<>();
        CellResult cellResult1 = Mockito.mock(CellResult.class);
        cellResultList1.add(cellResult1);

        List<CellResult> cellResultList2 = new ArrayList<>();
        CellResult cellResult2 = Mockito.mock(CellResult.class);
        cellResultList2.add(cellResult2);

        cellResultListList.add(cellResultList1);
        cellResultListList.add(cellResultList2);

        CellResultSet cellResultSet = CellResultSet.newInstance(cellResultListList);

        Assertions.assertEquals(cellResultSet.getCellResultListList().size(), 2);
    }

    @Test
    void validated() {
        List<List<CellResult>> cellResultListList = new ArrayList<>();

        List<CellResult> cellResultList1 = new ArrayList<>();
        CellResult cellResult1 = Mockito.mock(CellResult.class);
        CellResult cellResult2 = Mockito.mock(CellResult.class);
        cellResultList1.add(cellResult1);
        cellResultList1.add(cellResult2);

        List<CellResult> cellResultList2 = new ArrayList<>();
        CellResult cellResult21 = Mockito.mock(CellResult.class);
        cellResultList2.add(cellResult21);

        cellResultListList.add(cellResultList1);
        cellResultListList.add(cellResultList2);

        Assertions.assertThrows(ExcelException.class, () -> CellResultSet.newInstance(cellResultListList));

    }

    @Test
    void getFirstCellResult1() {
        CellResult cellResult = Mockito.mock(CellResult.class);
        CellResultSet cellResultSet = CellResultSet.firstCellResult(cellResult);

        Assertions.assertEquals(cellResultSet.getFirstCellResult(), cellResult);
        Assertions.assertEquals(cellResultSet.getCellResultListList().size(), 1);
        Assertions.assertEquals(cellResultSet.getCellResultListList().get(0).size(), 1);
    }

    @Test
    void getFirstCellResult2() {
        List<List<CellResult>> cellResultListList = new ArrayList<>();

        List<CellResult> cellResultList1 = new ArrayList<>();
        List<CellResult> cellResultList2 = new ArrayList<>();

        cellResultListList.add(cellResultList1);
        cellResultListList.add(cellResultList2);
        CellResultSet cellResultSet = CellResultSet.newInstance(cellResultListList);

        Assertions.assertThrows(ExcelException.class, cellResultSet::getFirstCellResult);
    }

    @RepeatedTest(10)
    void getExcelType() {
        List<List<CellResult>> cellResultListList = new ArrayList<>();

        List<CellResult> cellResultList1 = new ArrayList<>();
        CellResult cellResult1 = Mockito.mock(CellResult.class);
        CellResult cellResult2 = Mockito.mock(CellResult.class);
        cellResultList1.add(cellResult1);
        cellResultList1.add(cellResult2);

        List<CellResult> cellResultList2 = new ArrayList<>();
        CellResult cellResult21 = Mockito.mock(CellResult.class);
        CellResult cellResult22 = Mockito.mock(CellResult.class);
        cellResultList2.add(cellResult21);
        cellResultList2.add(cellResult22);

        cellResultListList.add(cellResultList1);
        cellResultListList.add(cellResultList2);

        CellResultSet cellResultSet = CellResultSet.newInstance(cellResultListList);

        ExcelType excelType = ExcelType.values()[random.nextInt(ExcelType.values().length)];
        Mockito.when(cellResultSet.getExcelType()).thenReturn(excelType);

        Assertions.assertEquals(cellResultSet.getExcelType(), excelType);
    }

    @Test
    void isNone() {
        CellResultSet cellResultSet1 = CellResultSet.none();
        Assertions.assertTrue(cellResultSet1.isNone());

        CellResultSet cellResultSet2 = CellResultSet.newInstance(Collections.emptyList());
        Assertions.assertTrue(cellResultSet2.isNone());

        CellResultSet cellResultSet3 = CellResultSet.newInstance(Collections.singletonList(Collections.emptyList()));
        Assertions.assertTrue(cellResultSet3.isNone());
    }
}
