package io.github.zouzhiy.excel.handler;

import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.localtime.LocalTimeNumberHandler;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import io.github.zouzhiy.excel.utils.RegionUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

class LocalTimeNumberHandlerTest extends CellHandlerTest {

    private final LocalTimeNumberHandler cellHandler = new LocalTimeNumberHandler();

    @Override
    @Test
    void getJavaType() {
        Assertions.assertEquals(cellHandler.getJavaType(), LocalTime.class);
    }

    @Override
    @Test
    void getExcelType() {
        Assertions.assertEquals(cellHandler.getExcelType(), ExcelType.NUMERIC);
    }

    @Test
    void readNone1() {
        Mockito.when(cellResultSet.isNone()).thenReturn(true);
        LocalTime result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertNull(result);
    }

    @Test
    void readNone2() {
        CellResult cellResultNone = CellResult.none();
        Mockito.when(cellResultSet.getFirstCellResult()).thenReturn(cellResultNone);
        LocalTime result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertNull(result);
    }

    @Test
    void readNoneBlank() {
        CellResult cellResultNone = CellResult.blank(cell, cellSpan);
        Mockito.when(cellResultSet.getFirstCellResult()).thenReturn(cellResultNone);
        LocalTime result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertNull(result);
    }

    @Override
    @RepeatedTest(5)
    void read() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalTime value = localDateTime.toLocalTime();

        CellResult cellResult = Mockito.mock(CellResult.class);
        Mockito.when(cellResult.getDateValue()).thenReturn(localDateTime);
        Mockito.when(cellResultSet.getFirstCellResult()).thenReturn(cellResult);
        LocalTime result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertEquals(result, value);
    }

    @Override
    @RepeatedTest(10)
    void write() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalTime value = random.nextBoolean() ? null : localDateTime.toLocalTime();
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
            Mockito.verify(cell, Mockito.times(0)).setCellValue(Mockito.anyDouble());
        } else {
            Mockito.verify(cell).setCellValue(DateUtil.getExcelDate(DateUtil.getLocalDateTime(0)
                    .plusDays(1)
                    .withHour(value.getHour())
                    .withMinute(value.getMinute())
                    .withSecond(value.getSecond())
                    .withNano(value.getNano())));
        }
        Mockito.verify(cell).setCellStyle(cellStyle);
        regionUtilsMockedStatic.verify(() -> RegionUtils.addMergedRegionIfPresent(sheetContext, cellStyle, rowIndex, rowIndex + rowspan - 1, columnIndex, columnIndex + colspan - 1));
    }

    @Override
    @RepeatedTest(5)
    void getWriteRowspan() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalTime value = random.nextBoolean() ? null : localDateTime.toLocalTime();
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
        Assertions.assertEquals(cellHandler.getDefaultExcelFormat(), "");
    }


}
