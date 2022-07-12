package io.github.zouzhiy.excel.handler;

import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.timestamp.TimestampDateHandler;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import io.github.zouzhiy.excel.utils.RegionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class TimestampDateHandlerTest extends CellHandlerTest {

    private final TimestampDateHandler cellHandler = new TimestampDateHandler();


    @Override
    @Test
    void getJavaType() {
        Assertions.assertEquals(cellHandler.getJavaType(), Timestamp.class);
    }

    @Override
    @Test
    void getExcelType() {
        Assertions.assertEquals(cellHandler.getExcelType(), ExcelType.DATE);
    }

    @Test
    void readNone1() {
        Mockito.when(cellResultSet.isNone()).thenReturn(true);
        Timestamp result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertNull(result);
    }

    @Test
    void readNone2() {
        CellResult cellResultNone = CellResult.none();
        Mockito.when(cellResultSet.getFirstCellResult()).thenReturn(cellResultNone);
        Timestamp result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertNull(result);
    }

    @Test
    void readNoneBlank() {
        CellResult cellResultNone = CellResult.blank(cell, cellSpan);
        Mockito.when(cellResultSet.getFirstCellResult()).thenReturn(cellResultNone);
        Timestamp result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertNull(result);
    }

    @Override
    @RepeatedTest(5)
    void read() {
        CellResult cellResult = Mockito.mock(CellResult.class);
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp value = Timestamp.valueOf(localDateTime);
        Mockito.when(cellResult.getDateValue(Mockito.anyString())).thenReturn(localDateTime);
        Mockito.when(cellResultSet.getFirstCellResult()).thenReturn(cellResult);
        Mockito.when(excelFieldConfig.getJavaFormat()).thenReturn("");
        Timestamp result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertEquals(result, value);
    }

    @Override
    @RepeatedTest(10)
    void write() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp value = random.nextBoolean() ? null : Timestamp.valueOf(localDateTime);
        int rowIndex = random.nextInt();
        int columnIndex = random.nextInt();
        int rowspan = random.nextInt();
        int colspan = random.nextInt();
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
            Mockito.verify(cell).setCellValue(value.toLocalDateTime());
        }
        Mockito.verify(cell).setCellStyle(cellStyle);
        regionUtilsMockedStatic.verify(() -> RegionUtils.addMergedRegionIfPresent(sheetContext, cellStyle, rowIndex, rowIndex + rowspan - 1, columnIndex, columnIndex + colspan - 1));
    }

    @Override
    @RepeatedTest(5)
    void getWriteRowspan() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp value = random.nextBoolean() ? null : Timestamp.valueOf(localDateTime);
        Assertions.assertEquals(cellHandler.getWriteRowspan(value), 1);
    }

    @Override
    @Test
    void getDefaultJavaFormat() {
        Assertions.assertEquals(cellHandler.getDefaultJavaFormat(), "");
    }

    @Override
    @Test
    void getDefaultExcelFormat() {
        Assertions.assertEquals(cellHandler.getDefaultExcelFormat(), "yyyy-MM-dd HH:mm:ss");
    }

}
