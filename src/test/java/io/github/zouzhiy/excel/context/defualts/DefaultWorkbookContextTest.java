package io.github.zouzhiy.excel.context.defualts;

import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactory;
import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactoryBuilder;
import io.github.zouzhiy.excel.context.WorkbookContext;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.metadata.result.CellStyleResult;
import io.github.zouzhiy.excel.metadata.result.CellStyleResultSet;
import io.github.zouzhiy.excel.parsing.ExcelAnnotationParse;
import io.github.zouzhiy.excel.support.metadata.DemoDefault;
import io.github.zouzhiy.excel.support.utils.TestFileUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Random;

class DefaultWorkbookContextTest {

    private final ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().build();

    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void getConfiguration() {
        InputStream inputStream = TestFileUtils.getInputStream("statics/import/data-title-style-read.xlsx");
        WorkbookParameter workbookParameter = WorkbookParameter.builder()
                .input(inputStream)
                .sheetParameter(SheetParameter.builder().sheet(0).titleRowStartIndex(0).headRowStartIndex(1).dataRowStartIndex(3).build())
                .build();
        Configuration configuration = new Configuration();

        DefaultWorkbookContext defaultWorkbookContext = new DefaultWorkbookContext(configuration, workbookParameter);

        Assertions.assertEquals(configuration, defaultWorkbookContext.getConfiguration());
    }

    @Test
    void getWorkbookParameter() {

        InputStream inputStream = TestFileUtils.getInputStream("statics/import/data-title-style-read.xlsx");
        WorkbookParameter workbookParameter = WorkbookParameter.builder()
                .input(inputStream)
                .sheetParameter(SheetParameter.builder().sheet(0).titleRowStartIndex(0).headRowStartIndex(1).dataRowStartIndex(3).build())
                .build();
        Configuration configuration = new Configuration();

        DefaultWorkbookContext defaultWorkbookContext = new DefaultWorkbookContext(configuration, workbookParameter);

        Assertions.assertEquals(workbookParameter, defaultWorkbookContext.getWorkbookParameter());
    }

    @Test
    void getWorkbook() {

        WorkbookContext workbookContext = this.getWorkbookContext();
        Assertions.assertNotNull(workbookContext.getWorkbook());
    }

    @Test
    void getFormulaEvaluator() {
        WorkbookContext workbookContext = this.getWorkbookContext();
        Assertions.assertNotNull(workbookContext.getFormulaEvaluator());
    }

    @Test
    void putTitleCellStyle() {
        WorkbookContext workbookContext = this.getWorkbookContext();

        ExcelClassConfig excelClassConfig = this.getExcelClassConfig();
        Workbook workbook = workbookContext.getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setLeftBorderColor((short) random.nextInt(Short.MAX_VALUE));

        workbookContext.putTitleCellStyle(excelClassConfig, cellStyle);
        Assertions.assertEquals(cellStyle, workbookContext.getTitleCellStyle(excelClassConfig));
    }

    @Test
    void putHeadCellStyle() {

        for (int i = 0; i < 10; i++) {
            WorkbookContext workbookContext = this.getWorkbookContext();

            Workbook workbook = workbookContext.getWorkbook();
            ExcelClassConfig excelClassConfig = this.getExcelClassConfig();
            CellStyleResultSet cellStyleResultSet = CellStyleResultSet.empty();
            for (ExcelFieldConfig excelFieldConfig : excelClassConfig.getItemList()) {
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setLeftBorderColor((short) random.nextInt(Short.MAX_VALUE));
                cellStyleResultSet.cellStyleResult(excelFieldConfig, cellStyle);
            }
            workbookContext.putHeadCellStyle(cellStyleResultSet);

            for (ExcelFieldConfig excelFieldConfig : excelClassConfig.getItemList()) {
                CellStyle cellStyle = workbookContext.getHeadCellStyle(excelFieldConfig);
                CellStyle cellStyleResult = cellStyleResultSet.getCellStyleResultList().stream().filter(item -> item.getExcelFieldConfig().equals(excelFieldConfig)).findAny().map(CellStyleResult::getCellStyle).orElse(null);
                Assertions.assertNotNull(cellStyleResult);
                Assertions.assertEquals(cellStyle.getLeftBorderColor(), cellStyleResult.getLeftBorderColor());
            }
        }
    }

    @Test
    void putDataCellStyle() {
        for (int i = 0; i < 10; i++) {
            WorkbookContext workbookContext = this.getWorkbookContext();

            Workbook workbook = workbookContext.getWorkbook();
            ExcelClassConfig excelClassConfig = this.getExcelClassConfig();
            CellStyleResultSet cellStyleResultSet = CellStyleResultSet.empty();
            for (ExcelFieldConfig excelFieldConfig : excelClassConfig.getItemList()) {
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setLeftBorderColor((short) random.nextInt(Short.MAX_VALUE));
                cellStyleResultSet.cellStyleResult(excelFieldConfig, cellStyle);
            }
            workbookContext.putDataCellStyle(cellStyleResultSet);

            for (ExcelFieldConfig excelFieldConfig : excelClassConfig.getItemList()) {
                CellStyle cellStyle = workbookContext.getDataCellStyle(excelFieldConfig, "");
                CellStyle cellStyleResult = cellStyleResultSet.getCellStyleResultList().stream().filter(item -> item.getExcelFieldConfig().equals(excelFieldConfig)).findAny().map(CellStyleResult::getCellStyle).orElse(null);
                Assertions.assertNotNull(cellStyleResult);
                Assertions.assertEquals(cellStyle.getLeftBorderColor(), cellStyleResult.getLeftBorderColor());
            }
        }
    }

    @Test
    void getTitleCellStyle() {
        WorkbookContext workbookContext = this.getWorkbookContext();
        ExcelClassConfig excelClassConfig = this.getExcelClassConfig();
        CellStyle cellStyle = workbookContext.getTitleCellStyle(excelClassConfig);
        Assertions.assertNotNull(cellStyle);
    }

    @Test
    void getHeadCellStyle() {

        WorkbookContext workbookContext = this.getWorkbookContext();
        ExcelClassConfig excelClassConfig = this.getExcelClassConfig();
        for (ExcelFieldConfig excelFieldConfig : excelClassConfig.getItemList()) {
            CellStyle cellStyle = workbookContext.getHeadCellStyle(excelFieldConfig);
            Assertions.assertNotNull(cellStyle);
        }
    }

    @Test
    void getDataCellStyle() {
        for (int i = 0; i < 10; i++) {
            WorkbookContext workbookContext = this.getWorkbookContext();

            ExcelClassConfig excelClassConfig = this.getExcelClassConfig();

            for (ExcelFieldConfig excelFieldConfig : excelClassConfig.getItemList()) {
                if (!BigDecimal.class.equals(excelFieldConfig.getJavaType())) {
                    continue;
                }
                CellStyle cellStyle = workbookContext.getDataCellStyle(excelFieldConfig, "0.00");
                assert cellStyle.getDataFormat() == workbookContext.getWorkbook().createDataFormat().getFormat("0.00");
            }
        }
    }


    private ExcelClassConfig getExcelClassConfig() {
        return new ExcelAnnotationParse(new Configuration()).findForClass(DemoDefault.class);
    }

    private WorkbookContext getWorkbookContext() {
        InputStream inputStream = TestFileUtils.getInputStream("statics/statics/import/data-title-style-read.xlsx");
        WorkbookParameter workbookParameter = WorkbookParameter.builder()
                .input(inputStream)
                .sheetParameter(SheetParameter.builder().sheet(0).titleRowStartIndex(0).headRowStartIndex(1).dataRowStartIndex(3).build())
                .build();

        return new DefaultWorkbookContext(new Configuration(), workbookParameter);
    }
}
