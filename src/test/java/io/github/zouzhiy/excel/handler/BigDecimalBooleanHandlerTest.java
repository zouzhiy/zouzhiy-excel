package io.github.zouzhiy.excel.handler;

import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.bigdecimal.BigDecimalBooleanHandler;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import io.github.zouzhiy.excel.utils.RegionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class BigDecimalBooleanHandlerTest extends CellHandlerTest {

    private final BigDecimalBooleanHandler cellHandler = new BigDecimalBooleanHandler();

    @Override
    @Test
    void getJavaType() {
        Assertions.assertEquals(cellHandler.getJavaType(), BigDecimal.class);
    }

    @Override
    @Test
    void getExcelType() {
        Assertions.assertEquals(cellHandler.getExcelType(), ExcelType.BOOLEAN);
    }

    @Test
    void readNone1() {
        Mockito.when(cellResultSet.isNone()).thenReturn(true);
        BigDecimal bigDecimalNone = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertNull(bigDecimalNone);
    }

    @Test
    void readNone2() {
        CellResult cellResultNone = CellResult.none();
        Mockito.when(cellResultSet.getFirstCellResult()).thenReturn(cellResultNone);
        BigDecimal bigDecimalNone = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertNull(bigDecimalNone);
    }

    @Test
    void readNoneBlank() {
        CellResult cellResultNone = CellResult.blank(cell, cellSpan);
        Mockito.when(cellResultSet.getFirstCellResult()).thenReturn(cellResultNone);
        BigDecimal bigDecimal = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertNull(bigDecimal);
    }

    @Override
    @RepeatedTest(5)
    void read() {
        CellResult cellResult = Mockito.mock(CellResult.class);
        BigDecimal value = BigDecimal.valueOf(random.nextDouble());
        Mockito.when(cellResult.getNumberValue()).thenReturn(value);
        Mockito.when(cellResultSet.getFirstCellResult()).thenReturn(cellResult);
        BigDecimal bigDecimal = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertEquals(bigDecimal, value);
    }

    @Override
    @RepeatedTest(10)
    void write() {
        int rowIndex = random.nextInt();
        int columnIndex = random.nextInt();
        int rowspan = random.nextInt();
        int colspan = random.nextInt();
        BigDecimal value = random.nextBoolean() ? null : BigDecimal.valueOf(random.nextInt(2));
        List<Row> rowList = new ArrayList<>();
        rowList.add(row);
        Mockito.when(rowContext.getRowList()).thenReturn(rowList);
        Mockito.when(row.createCell(columnIndex)).thenReturn(cell);
        Mockito.when(rowContext.getSheetContext()).thenReturn(sheetContext);
        Mockito.when(sheetContext.getDataCellStyle(excelFieldConfig, cellHandler.getDefaultExcelFormat())).thenReturn(cellStyle);
        Mockito.when(rowContext.getRowspan()).thenReturn(rowspan);
        Mockito.when(excelFieldConfig.getColspan()).thenReturn(colspan);
        Mockito.when(row.getRowNum()).thenReturn(rowIndex);

        cellHandler.write(rowContext, columnIndex, excelFieldConfig, value);

        if (value == null) {
            Mockito.verify(cell, Mockito.times(0)).setCellValue(Mockito.anyBoolean());
        } else {
            Mockito.verify(cell).setCellValue(value.compareTo(BigDecimal.ONE) == 0);
        }
        Mockito.verify(cell).setCellStyle(cellStyle);
        regionUtilsMockedStatic.verify(() -> RegionUtils.addMergedRegionIfPresent(sheetContext, cellStyle, rowIndex, rowIndex + rowspan - 1, columnIndex, columnIndex + colspan - 1));
    }

    @Override
    @RepeatedTest(5)
    void getWriteRowspan() {
        Assertions.assertEquals(cellHandler.getWriteRowspan(BigDecimal.valueOf(random.nextDouble())), 1);
    }

    @Override
    @Test
    void getDefaultJavaFormat() {
        Assertions.assertEquals(cellHandler.getDefaultJavaFormat(), "");
    }

    @Override
    @Test
    void getDefaultExcelFormat() {
        Assertions.assertEquals(cellHandler.getDefaultExcelFormat(), "");
    }
}
