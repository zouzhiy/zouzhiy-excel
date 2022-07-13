package io.github.zouzhiy.excel.context.defualts;

import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.context.WorkbookContext;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.MergedRegion;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.metadata.result.CellStyleResultSet;
import io.github.zouzhiy.excel.parsing.ExcelAnnotationParse;
import io.github.zouzhiy.excel.support.metadata.DemoDefault;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class DefaultSheetContextTest {

    @Test
    void getConfiguration() {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Assertions.assertEquals(workbookContext, sheetContext.getWorkbookContext());
        Assertions.assertEquals(excelClassConfig, sheetContext.getExcelClassConfig());
        Assertions.assertEquals(sheetParameter, sheetContext.getSheetParameter());


        Configuration configuration = new Configuration();
        Mockito.when(workbookContext.getConfiguration()).thenReturn(configuration);
        Assertions.assertEquals(configuration, sheetContext.getConfiguration());

        Assertions.assertNotEquals(new Configuration(), sheetContext.getConfiguration());


    }

    @Test
    void getWorkbook() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(true);
        Mockito.when(workbookContext.getWorkbook()).thenReturn(workbook);
        Assertions.assertEquals(workbook, sheetContext.getWorkbook());

        Workbook workbook2 = WorkbookFactory.create(true);
        Assertions.assertNotEquals(workbook2, sheetContext.getWorkbook());
    }


    @Test
    void hasInputStream() {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = SheetParameter.builder().build();
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        WorkbookParameter workbookParameter = Mockito.mock(WorkbookParameter.class);
        Mockito.when(workbookContext.getWorkbookParameter()).thenReturn(workbookParameter);
        Mockito.when(workbookParameter.getInputStream()).thenReturn(null);
        Assertions.assertFalse(sheetContext.hasInputStream());

        Mockito.when(workbookParameter.getInputStream()).thenReturn(Mockito.mock(InputStream.class));
        Assertions.assertTrue(sheetContext.hasInputStream());
    }

    @Test
    void getSheet1() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(true);
        workbook.createSheet("sheet1");
        Mockito.when(workbookContext.getWorkbook()).thenReturn(workbook);

        Mockito.when(sheetParameter.getSheetName()).thenReturn("sheet1");
        Assertions.assertEquals(sheetContext.getSheet().getSheetName(), "sheet1");
        Assertions.assertEquals(sheetContext.getSheet().getSheetName(), sheetContext.getSheet().getSheetName());


    }

    @Test
    void getSheet2() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(true);
        workbook.createSheet("sheet1");
        Mockito.when(workbookContext.getWorkbook()).thenReturn(workbook);

        Mockito.when(sheetParameter.getSheetIndex()).thenReturn(0);
        Assertions.assertEquals(workbook.getSheetIndex(sheetContext.getSheet()), 0);
        Assertions.assertEquals(workbook.getSheetIndex(sheetContext.getSheet()), 0);
    }

    @Test
    void getSheet3() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(true);
        workbook.createSheet("sheet1");
        Mockito.when(workbookContext.getWorkbook()).thenReturn(workbook);

        Mockito.when(sheetParameter.getSheetIndex()).thenReturn(null);
        Mockito.when(sheetParameter.getTitle()).thenReturn("sheet1");
        Assertions.assertEquals(sheetContext.getSheet().getSheetName(), "sheet1");
    }

    @Test
    void getSheet4() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(true);
        workbook.createSheet("sheet1");
        Mockito.when(workbookContext.getWorkbook()).thenReturn(workbook);

        Mockito.when(sheetParameter.getSheetName()).thenReturn(null);
        Mockito.when(sheetParameter.getSheetIndex()).thenReturn(null);
        Mockito.when(sheetParameter.getTitle()).thenReturn(null);
        Assertions.assertEquals(sheetContext.getSheet(), sheetContext.getSheet());
        Assertions.assertEquals(sheetContext.getSheet().getSheetName(), "Sheet2");
    }

    @Test
    void getSheet5() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(true);
        Mockito.when(workbookContext.getWorkbook()).thenReturn(workbook);

        Mockito.when(sheetParameter.getSheetName()).thenReturn("sheet6");
        Mockito.when(sheetParameter.getSheetIndex()).thenReturn(null);
        Mockito.when(sheetParameter.getTitle()).thenReturn(null);
        Assertions.assertEquals(sheetContext.getSheet(), sheetContext.getSheet());
        Assertions.assertEquals(sheetContext.getSheet().getSheetName(), "sheet6");
        Assertions.assertEquals(workbook.getSheetIndex(sheetContext.getSheet()), 0);
    }

    @Test
    void getSheet6() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(true);
        Mockito.when(workbookContext.getWorkbook()).thenReturn(workbook);

        Mockito.when(sheetParameter.getSheetName()).thenReturn(null);
        Mockito.when(sheetParameter.getSheetIndex()).thenReturn(1);
        Mockito.when(sheetParameter.getTitle()).thenReturn(null);
        Assertions.assertEquals(sheetContext.getSheet(), sheetContext.getSheet());
        Assertions.assertEquals(sheetContext.getSheet().getSheetName(), "Sheet2");
        Assertions.assertEquals(workbook.getSheetIndex(sheetContext.getSheet()), 0);
    }

    @Test
    void getSheet7() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(true);
        Mockito.when(workbookContext.getWorkbook()).thenReturn(workbook);

        Mockito.when(sheetParameter.getSheetName()).thenReturn(null);
        Mockito.when(sheetParameter.getSheetIndex()).thenReturn(null);
        Mockito.when(sheetParameter.getTitle()).thenReturn("title");
        Assertions.assertEquals(sheetContext.getSheet(), sheetContext.getSheet());
        Assertions.assertEquals(sheetContext.getSheet().getSheetName(), "title");
        Assertions.assertEquals(workbook.getSheetIndex(sheetContext.getSheet()), 0);
    }

    @Test
    void getSheet8() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(true);
        Mockito.when(workbookContext.getWorkbook()).thenReturn(workbook);

        Mockito.when(sheetParameter.getSheetName()).thenReturn(null);
        Mockito.when(sheetParameter.getSheetIndex()).thenReturn(null);
        Mockito.when(sheetParameter.getTitle()).thenReturn(null);
        Assertions.assertEquals(sheetContext.getSheet(), sheetContext.getSheet());
        Assertions.assertEquals(sheetContext.getSheet().getSheetName(), "Sheet1");
        Assertions.assertEquals(workbook.getSheetIndex(sheetContext.getSheet()), 0);
    }


    @Test
    void getSheet9() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(true);
        workbook.createSheet("Sheet2");
        Mockito.when(workbookContext.getWorkbook()).thenReturn(workbook);

        Mockito.when(sheetParameter.getSheetName()).thenReturn(null);
        Mockito.when(sheetParameter.getSheetIndex()).thenReturn(null);
        Mockito.when(sheetParameter.getTitle()).thenReturn(null);
        Assertions.assertEquals(sheetContext.getSheet(), sheetContext.getSheet());
        Assertions.assertEquals(sheetContext.getSheet().getSheetName(), "Sheet3");
        Assertions.assertEquals(workbook.getSheetIndex(sheetContext.getSheet()), 1);
    }

    @Test
    void getMergedRegion() throws IOException {

        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);


        SheetContext sheetContext = Mockito.spy(new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter));
        Workbook workbook = WorkbookFactory.create(true);
        Sheet sheet = workbook.createSheet("sheet1");

        Mockito.when(workbookContext.getWorkbook()).thenReturn(workbook);
        Mockito.doReturn(sheet).when(sheetContext).getSheet();

        Assertions.assertEquals(sheetContext.getMergedRegion().getCellRangeAddressListMap(), new MergedRegion(sheet.getMergedRegions()).getCellRangeAddressListMap());
    }

    @Test
    void getDrawing() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);

        SheetContext sheetContext = Mockito.spy(new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter));
        Workbook workbook = WorkbookFactory.create(true);
        Sheet sheet = workbook.createSheet("sheet1");

        Mockito.when(workbookContext.getWorkbook()).thenReturn(workbook);
        Mockito.doReturn(sheet).when(sheetContext).getSheet();

        Assertions.assertNotNull(sheetContext.getDrawing());
    }

    @Test
    void getMaxRowspan1() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);

        SheetContext sheetContext = Mockito.spy(new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter));
        Workbook workbook = WorkbookFactory.create(true);
        Sheet sheet = workbook.createSheet("sheet1");
        int rowspan = new Random(System.currentTimeMillis()).nextInt(15) + 2;
        Row row = sheet.createRow(0);
        row.createCell(1);
        sheet.addMergedRegion(new CellRangeAddress(0, rowspan - 1, 1, 1));

        Mockito.doReturn(sheet).when(sheetContext).getSheet();
        Assertions.assertEquals(sheetContext.getMaxRowspan(0), rowspan);
    }

    @Test
    void getMaxRowspan2() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);

        SheetContext sheetContext = Mockito.spy(new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter));
        Workbook workbook = WorkbookFactory.create(true);
        Sheet sheet = workbook.createSheet("sheet1");

        Mockito.doReturn(sheet).when(sheetContext).getSheet();
        Assertions.assertEquals(sheetContext.getMaxRowspan(0), 1);
    }

    @Test
    void getRow() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);

        SheetContext sheetContext = Mockito.spy(new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter));
        Workbook workbook = WorkbookFactory.create(true);
        Sheet sheet = workbook.createSheet("sheet1");
        int rowspan = new Random(System.currentTimeMillis()).nextInt(15) + 2;
        Row row = sheet.createRow(rowspan);
        row.createCell(1);

        Mockito.doReturn(sheet).when(sheetContext).getSheet();
        Assertions.assertEquals(sheetContext.getRow(rowspan), row);
    }

    @Test
    void getRowList() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);

        SheetContext sheetContext = Mockito.spy(new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter));
        Workbook workbook = WorkbookFactory.create(true);
        Sheet sheet = workbook.createSheet("sheet1");
        int rowspan = new Random(System.currentTimeMillis()).nextInt(15) + 2;
        List<Row> rowList = new ArrayList<>();
        for (int i = 0; i < rowspan; i++) {
            Row row = sheet.createRow(i);
            rowList.add(row);
            row.createCell(new Random(System.currentTimeMillis()).nextInt(88));
        }
        sheet.addMergedRegion(new CellRangeAddress(0, rowspan - 1, 1, 1));

        Mockito.doReturn(sheet).when(sheetContext).getSheet();
        Assertions.assertEquals(sheetContext.getRowList(0, rowspan - 1).size(), rowspan);
        Assertions.assertEquals(sheetContext.getRowList(0, rowspan - 1), rowList);
    }

    @Test
    void getLasRowIndex() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);

        SheetContext sheetContext = Mockito.spy(new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter));
        Workbook workbook = WorkbookFactory.create(true);
        Sheet sheet = workbook.createSheet("sheet1");
        int rowspan = new Random(System.currentTimeMillis()).nextInt(15) + 2;
        List<Row> rowList = new ArrayList<>();
        for (int i = 0; i < rowspan; i++) {
            Row row = sheet.createRow(i);
            rowList.add(row);
            row.createCell(new Random(System.currentTimeMillis()).nextInt(88));
        }
        sheet.addMergedRegion(new CellRangeAddress(0, rowspan - 1, 1, 1));

        Mockito.doReturn(sheet).when(sheetContext).getSheet();
        Assertions.assertEquals(sheetContext.getLasRowIndex(), rowspan - 1);
    }


    @Test
    void getFormulaEvaluator() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = Mockito.mock(ExcelClassConfig.class);
        SheetParameter sheetParameter = Mockito.mock(SheetParameter.class);
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(true);

        FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
        Mockito.when(workbookContext.getFormulaEvaluator()).thenReturn(formulaEvaluator);
        Assertions.assertEquals(formulaEvaluator, sheetContext.getFormulaEvaluator());

        Workbook workbook2 = WorkbookFactory.create(true);
        Assertions.assertNotEquals(workbook2.getCreationHelper().createFormulaEvaluator(), sheetContext.getFormulaEvaluator());
    }

    @Test
    void putTitleCellStyle() throws IOException {

        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = new ExcelAnnotationParse(new Configuration()).findForClass(DemoDefault.class);
        SheetParameter sheetParameter = SheetParameter.builder().sheet("sheet1").build();
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(false);
        CellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setAlignment(HorizontalAlignment.FILL);
        sheetContext.putTitleCellStyle(excelClassConfig, titleCellStyle);
        Mockito.verify(workbookContext).putTitleCellStyle(excelClassConfig, titleCellStyle);

    }

    @Test
    void putHeadCellStyle() throws IOException {

        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = new ExcelAnnotationParse(new Configuration()).findForClass(DemoDefault.class);
        SheetParameter sheetParameter = SheetParameter.builder().sheet("sheet1").build();
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(false);
        CellStyle headCellStyle = workbook.createCellStyle();
        headCellStyle.setAlignment(HorizontalAlignment.DISTRIBUTED);
        CellStyleResultSet cellStyleResultSet = CellStyleResultSet.empty();
        cellStyleResultSet.cellStyleResult(Mockito.mock(ExcelFieldConfig.class), headCellStyle);
        cellStyleResultSet.cellStyleResult(Mockito.mock(ExcelFieldConfig.class), headCellStyle);
        sheetContext.putHeadCellStyle(cellStyleResultSet);
        Mockito.verify(workbookContext).putHeadCellStyle(cellStyleResultSet);

    }

    @Test
    void putDataCellStyle() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = new ExcelAnnotationParse(new Configuration()).findForClass(DemoDefault.class);
        SheetParameter sheetParameter = SheetParameter.builder().sheet("sheet1").build();
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(false);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderLeft(BorderStyle.DASHED);


        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setAlignment(HorizontalAlignment.DISTRIBUTED);
        CellStyleResultSet dataStyleResultSet = CellStyleResultSet.empty();
        dataStyleResultSet.cellStyleResult(Mockito.mock(ExcelFieldConfig.class), dataCellStyle);
        dataStyleResultSet.cellStyleResult(Mockito.mock(ExcelFieldConfig.class), dataCellStyle);
        sheetContext.putDataCellStyle(dataStyleResultSet);
        Mockito.verify(workbookContext).putDataCellStyle(dataStyleResultSet);

    }


    @Test
    void getTitleCellStyle() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = new ExcelAnnotationParse(new Configuration()).findForClass(DemoDefault.class);
        SheetParameter sheetParameter = SheetParameter.builder().sheet("sheet1").build();
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(false);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setLeftBorderColor((short) 22);

        CellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setAlignment(HorizontalAlignment.FILL);
        Mockito.when(workbookContext.getTitleCellStyle(excelClassConfig)).thenReturn(titleCellStyle);
        Assertions.assertEquals(titleCellStyle, sheetContext.getTitleCellStyle(excelClassConfig));
        Assertions.assertNotEquals(cellStyle, sheetContext.getTitleCellStyle(excelClassConfig));
        Assertions.assertNotEquals(titleCellStyle, sheetContext.getTitleCellStyle(Mockito.mock(ExcelClassConfig.class)));

    }

    @Test
    void getHeadCellStyle() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = new ExcelAnnotationParse(new Configuration()).findForClass(DemoDefault.class);
        SheetParameter sheetParameter = SheetParameter.builder().sheet("sheet1").build();
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(false);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setLeftBorderColor((short) 22);

        CellStyle headCellStyle = workbook.createCellStyle();
        headCellStyle.setAlignment(HorizontalAlignment.FILL);
        ExcelFieldConfig excelFieldConfig = Mockito.mock(ExcelFieldConfig.class);
        Mockito.when(workbookContext.getHeadCellStyle(excelFieldConfig)).thenReturn(headCellStyle);
        Assertions.assertEquals(headCellStyle, sheetContext.getHeadCellStyle(excelFieldConfig));
        Assertions.assertNotEquals(cellStyle, sheetContext.getHeadCellStyle(excelFieldConfig));
        Assertions.assertNotEquals(headCellStyle, sheetContext.getHeadCellStyle(Mockito.mock(ExcelFieldConfig.class)));

    }

    @Test
    void getDataCellStyle() throws IOException {
        WorkbookContext workbookContext = Mockito.mock(WorkbookContext.class);
        ExcelClassConfig excelClassConfig = new ExcelAnnotationParse(new Configuration()).findForClass(DemoDefault.class);
        SheetParameter sheetParameter = SheetParameter.builder().sheet("sheet1").build();
        SheetContext sheetContext = new DefaultSheetContext(workbookContext, excelClassConfig, sheetParameter);

        Workbook workbook = WorkbookFactory.create(false);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setLeftBorderColor((short) 22);

        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setAlignment(HorizontalAlignment.DISTRIBUTED);
        ExcelFieldConfig excelFieldConfig1 = Mockito.mock(ExcelFieldConfig.class);
        Mockito.when(workbookContext.getDataCellStyle(excelFieldConfig1, "@")).thenReturn(dataCellStyle);
        Assertions.assertEquals(dataCellStyle, sheetContext.getDataCellStyle(excelFieldConfig1, "@"));
        Assertions.assertNotEquals(dataCellStyle, sheetContext.getDataCellStyle(excelFieldConfig1, "@@"));

        Assertions.assertNotEquals(cellStyle, sheetContext.getDataCellStyle(excelFieldConfig1, "@"));
        Assertions.assertNotEquals(dataCellStyle, sheetContext.getDataCellStyle(Mockito.mock(ExcelFieldConfig.class), "@"));

    }


}
