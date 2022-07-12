package io.github.zouzhiy.excel.handler;

import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.list.ListStringStringJoinHandler;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import io.github.zouzhiy.excel.utils.RegionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class ListStringStringJoinHandlerTest extends CellHandlerTest {

    private final ListStringStringJoinHandler cellHandler = new ListStringStringJoinHandler();

    @Override
    @Test
    void getJavaType() {
        Assertions.assertEquals(cellHandler.getJavaType(), List.class);
    }

    @Override
    @Test
    void getExcelType() {
        Assertions.assertEquals(cellHandler.getExcelType(), ExcelType.STRING);
    }

    @Test
    void readNone1() {
        Mockito.when(cellResultSet.isNone()).thenReturn(true);
        List<String> result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertNull(result);
    }

    @Test
    void readNone2() {
        CellResult cellResultNone = CellResult.none();
        Mockito.when(cellResultSet.getFirstCellResult()).thenReturn(cellResultNone);
        List<String> result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertNull(result);
    }

    @Test
    void readNoneBlank() {
        CellResult cellResultNone = CellResult.blank(cell, cellSpan);
        Mockito.when(cellResultSet.getFirstCellResult()).thenReturn(cellResultNone);
        List<String> result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertTrue(result.isEmpty());
    }

    @Override
    @RepeatedTest(5)
    void read() {
        List<String> value = new ArrayList<>();
        value.add(random.nextDouble() + "");
        value.add(random.nextDouble() + "");
        value.add(random.nextDouble() + "");
        CellResult cellResult = Mockito.mock(CellResult.class);
        Mockito.when(cellResult.getStringValue()).thenReturn(String.join(";", value));
        Mockito.when(cellResultSet.getFirstCellResult()).thenReturn(cellResult);
        List<String> result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertEquals(result, value);
    }

    @RepeatedTest(10)
    void writeNull() {
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

        cellHandler.write(rowContext, columnIndex, excelFieldConfig, null);

        Mockito.verify(cell, Mockito.times(0)).setCellValue(Mockito.anyString());

        Mockito.verify(cell).setCellStyle(cellStyle);
        regionUtilsMockedStatic.verify(() -> RegionUtils.addMergedRegionIfPresent(sheetContext, cellStyle, rowIndex, rowIndex + rowspan - 1, columnIndex, columnIndex + colspan - 1));
    }

    @RepeatedTest(10)
    void writeEmpty() {
        List<String> value = new ArrayList<>();
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

        Mockito.verify(cell).setCellValue(String.join(";", value));

        Mockito.verify(cell).setCellStyle(cellStyle);
        regionUtilsMockedStatic.verify(() -> RegionUtils.addMergedRegionIfPresent(sheetContext, cellStyle, rowIndex, rowIndex + rowspan - 1, columnIndex, columnIndex + colspan - 1));
    }

    @Override
    @RepeatedTest(10)
    void write() {
        List<String> value = new ArrayList<>();
        value.add(random.nextDouble() + "");
        value.add(random.nextDouble() + "");
        value.add(random.nextDouble() + "");
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

        Mockito.verify(cell).setCellValue(String.join(";", value));

        Mockito.verify(cell).setCellStyle(cellStyle);
        regionUtilsMockedStatic.verify(() -> RegionUtils.addMergedRegionIfPresent(sheetContext, cellStyle, rowIndex, rowIndex + rowspan - 1, columnIndex, columnIndex + colspan - 1));
    }


    @Override
    @RepeatedTest(5)
    void getWriteRowspan() {
        List<String> value = new ArrayList<>();
        value.add(random.nextDouble() + "");
        value.add(random.nextDouble() + "");
        value.add(random.nextDouble() + "");
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