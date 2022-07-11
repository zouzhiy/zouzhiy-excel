package io.github.zouzhiy.excel.handler;

import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.bytes.ByteArrayBoxStringHandler;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import io.github.zouzhiy.excel.utils.RegionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class ByteArrayBoxStringHandlerTest extends CellHandlerTest {

    private final ByteArrayBoxStringHandler cellHandler = new ByteArrayBoxStringHandler();

    @Test
    @Override
    void getJavaType() {
        Assertions.assertEquals(cellHandler.getJavaType(), Byte[].class);
    }

    @Test
    @Override
    void getExcelType() {
        Assertions.assertEquals(cellHandler.getExcelType(), ExcelType.STRING);
    }

    @Test
    void readNone1() {
        Mockito.when(cellResultSet.isNone()).thenReturn(true);
        Byte[] result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertNull(result);
    }

    @Test
    void readNone2() {
        CellResult cellResultNone = CellResult.none();
        Mockito.when(cellResultSet.getFirstCellResult()).thenReturn(cellResultNone);
        Byte[] result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertNull(result);
    }

    @Test
    void readNoneBlank() {
        CellResult cellResultNone = CellResult.blank(cell, cellSpan);
        Mockito.when(cellResultSet.getFirstCellResult()).thenReturn(cellResultNone);
        Byte[] result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertNull(result);
    }

    @Override
    @RepeatedTest(5)
    void read() {
        CellResult cellResult = Mockito.mock(CellResult.class);
        String value = "BigInteger.valueOf(random.nextInt())" + random.nextInt(9999922);
        Mockito.when(cellResult.getStringValue()).thenReturn(value);
        Mockito.when(cellResultSet.getFirstCellResult()).thenReturn(cellResult);
        Byte[] result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertEquals(new String(this.unbox(result), StandardCharsets.UTF_8), value);
    }

    @RepeatedTest(5)
    @Override
    void write() {
        String value = "BigInteger.valueOf(random.nextInt())" + random.nextInt(9999922);
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

        MockedStatic<RegionUtils> regionUtilsMockedStatic = Mockito.mockStatic(RegionUtils.class);
        cellHandler.write(rowContext, columnIndex, excelFieldConfig, this.box(value.getBytes(StandardCharsets.UTF_8)));

        Mockito.verify(cell).setCellValue(value);

        Mockito.verify(cell).setCellStyle(cellStyle);
        regionUtilsMockedStatic.verify(() -> RegionUtils.addMergedRegionIfPresent(sheetContext, cellStyle, rowIndex, rowIndex + rowspan - 1, columnIndex, columnIndex + colspan - 1));
        regionUtilsMockedStatic.close();
    }


    @Override
    @RepeatedTest(5)
    void getWriteRowspan() {
        Byte[] bytes = this.box(("new Byte[random.nextInt()]" + random.nextDouble()).getBytes(StandardCharsets.UTF_8));
        Assertions.assertEquals(cellHandler.getWriteRowspan(bytes), 1);
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

    private Byte[] box(byte[] bytes) {
        Byte[] bytes1 = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes1[i] = bytes[i];
        }
        return bytes1;
    }

    private byte[] unbox(Byte[] bytes) {
        byte[] bytes1 = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes1[i] = bytes[i];
        }
        return bytes1;
    }
}
