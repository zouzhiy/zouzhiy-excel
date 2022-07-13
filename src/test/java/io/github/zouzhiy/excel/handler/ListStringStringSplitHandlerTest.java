package io.github.zouzhiy.excel.handler;

import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.handler.list.ListStringStringSplitHandler;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 无法区分null和empty,统一都返回empty
 */
class ListStringStringSplitHandlerTest extends CellHandlerTest {

    private final ListStringStringSplitHandler cellHandler = new ListStringStringSplitHandler();

    private final Configuration configuration = new Configuration();

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
    void readEmpty1() {
        Mockito.when(sheetContext.getConfiguration()).thenReturn(configuration);
        Mockito.when(cellResultSet.isNone()).thenReturn(true);
        List<String> result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void readEmpty2() {
        Mockito.when(sheetContext.getConfiguration()).thenReturn(configuration);
        Mockito.when(cellResultSet.getCellResultListList()).thenReturn(Collections.emptyList());
        List<String> result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void readNoneItem() {
        Mockito.when(sheetContext.getConfiguration()).thenReturn(configuration);
        Mockito.when(cellResultSet.getCellResultListList()).thenReturn(Collections.singletonList(Collections.singletonList(CellResult.none())));
        List<String> result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertEquals(1, result.size());
        Assertions.assertNull(result.get(0));
    }


    @Test
    void readBlankItem() {
        Mockito.when(sheetContext.getConfiguration()).thenReturn(configuration);
        List<List<CellResult>> listList = Collections.singletonList(Collections.singletonList(CellResult.blank(cell, cellSpan)));
        Mockito.when(cellResultSet.getCellResultListList()).thenReturn(listList);
        List<String> result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertEquals(1, result.size());
        Assertions.assertNull(result.get(0));
    }

    @Override
    @RepeatedTest(5)
    void read() {
        List<String> value = new ArrayList<>();
        value.add(random.nextDouble() + "");
        value.add(random.nextDouble() + "");
        value.add(random.nextDouble() + "");

        CellResult cellResult1 = Mockito.mock(CellResult.class);
        CellResult cellResult2 = Mockito.mock(CellResult.class);
        CellResult cellResult3 = Mockito.mock(CellResult.class);
        Mockito.when(cellResult1.getExcelType()).thenReturn(ExcelType.STRING);
        Mockito.when(cellResult2.getExcelType()).thenReturn(ExcelType.STRING);
        Mockito.when(cellResult3.getExcelType()).thenReturn(ExcelType.STRING);

        Mockito.when(cellResult1.getStringValue()).thenReturn(value.get(0));
        Mockito.when(cellResult2.getStringValue()).thenReturn(value.get(1));
        Mockito.when(cellResult3.getStringValue()).thenReturn(value.get(2));

        List<List<CellResult>> cellResultList = new ArrayList<>();
        cellResultList.add(Collections.singletonList(cellResult1));
        cellResultList.add(Collections.singletonList(cellResult2));
        cellResultList.add(Collections.singletonList(cellResult3));

        Mockito.when(sheetContext.getConfiguration()).thenReturn(configuration);
        Mockito.when(cellResultSet.getCellResultListList()).thenReturn(cellResultList);
        List<String> result = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        Assertions.assertEquals(result, value);
    }

    @RepeatedTest(10)
    void writeNull() {
        int columnIndex = random.nextInt();
        cellHandler.write(rowContext, columnIndex, excelFieldConfig, null);
        Mockito.verify(cell, Mockito.times(0)).setCellValue(Mockito.anyString());
        Mockito.verify(cell, Mockito.times(0)).setCellStyle(cellStyle);
    }

    @RepeatedTest(10)
    void writeEmpty() {
        int columnIndex = random.nextInt();
        Mockito.when(rowContext.getSheetContext()).thenReturn(sheetContext);
        Mockito.when(sheetContext.getConfiguration()).thenReturn(configuration);
        cellHandler.write(rowContext, columnIndex, excelFieldConfig, Collections.emptyList());
        Mockito.verify(cell, Mockito.times(0)).setCellValue(Mockito.anyString());
        Mockito.verify(cell, Mockito.times(0)).setCellStyle(cellStyle);
    }

    @RepeatedTest(10)
    void writeRowError() {
        int valueSize = random.nextInt(10);

        List<String> value = new ArrayList<>();
        List<Row> rowList = new ArrayList<>();
        for (int i = 0; i < valueSize; i++) {
            value.add("" + random.nextDouble());
            rowList.add(row);
        }
        value.add("" + random.nextDouble());
        int columnIndex = random.nextInt();
        Mockito.when(rowContext.getRowList()).thenReturn(rowList);
        Mockito.when(rowContext.getSheetContext()).thenReturn(sheetContext);
        Assertions.assertThrows(ExcelException.class, () -> cellHandler.write(rowContext, columnIndex, excelFieldConfig, value));
    }

    @RepeatedTest(10)
    void writeNullItem() {
        List<String> value = new ArrayList<>();
        value.add(null);
        int columnIndex = random.nextInt();

        List<Row> rowList = new ArrayList<>();
        rowList.add(row);
        Mockito.when(rowContext.getRowList()).thenReturn(rowList);
        Mockito.when(row.createCell(columnIndex)).thenReturn(cell);

        Mockito.when(rowContext.getSheetContext()).thenReturn(sheetContext);
        Mockito.when(sheetContext.getConfiguration()).thenReturn(configuration);
        cellHandler.write(rowContext, columnIndex, excelFieldConfig, value);
        Mockito.verify(cell, Mockito.times(0)).setCellValue(Mockito.anyString());
        Mockito.verify(cell, Mockito.times(0)).setCellStyle(cellStyle);
    }

    @Override
    @Test
    void write() {
        List<String> value = new ArrayList<>();
        value.add(random.nextDouble() + "");
        value.add(random.nextDouble() + "");
        value.add("");
        value.add(null);

        int columnIndex = random.nextInt(20);
        int rowSize = value.size() + (random.nextBoolean() ? 0 : random.nextInt(10));
        List<Row> rowList = new ArrayList<>();
        List<Cell> cellList = new ArrayList<>();
        int rowIndex = random.nextInt();
        for (int i = 0; i < rowSize; i++) {
            Row row = Mockito.mock(Row.class);
            rowList.add(row);
            cellList.add(cell);
        }

        Configuration configuration = Mockito.mock(Configuration.class);
        CellHandlerRegistry cellHandlerRegistry = Mockito.mock(CellHandlerRegistry.class);
        CellHandler cellHandlerChildren = Mockito.mock(CellHandler.class);
        Mockito.when(sheetContext.getConfiguration()).thenReturn(configuration);
        Mockito.when(configuration.getCellHandlerRegistry()).thenReturn(cellHandlerRegistry);
        Mockito.when(cellHandlerRegistry.getCellHandler(String.class, ExcelType.STRING)).thenReturn(cellHandlerChildren);

        Mockito.when(rowContext.getRowList()).thenReturn(rowList);
        Mockito.when(rowContext.getSheetContext()).thenReturn(sheetContext);

        cellHandler.write(rowContext, columnIndex, excelFieldConfig, value);

        Mockito.verify(cellHandlerChildren, Mockito.times(4)).write(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
        for (int i = 0; i < value.size(); i++) {
            Mockito.verify(cellHandlerChildren, Mockito.times(1)).write(Mockito.notNull(), Mockito.eq(columnIndex), Mockito.eq(excelFieldConfig), Mockito.eq(value.get(i)));

        }

    }


    @Override
    @RepeatedTest(5)
    void getWriteRowspan() {
        List<String> value = new ArrayList<>();
        value.add(random.nextDouble() + "");
        value.add(random.nextDouble() + "");
        value.add(random.nextDouble() + "");
        Assertions.assertEquals(cellHandler.getWriteRowspan(value), value.size());
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