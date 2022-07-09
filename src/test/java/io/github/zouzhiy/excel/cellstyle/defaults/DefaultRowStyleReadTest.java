package io.github.zouzhiy.excel.cellstyle.defaults;

import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactory;
import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactoryBuilder;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.context.defualts.DefaultSheetContext;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.metadata.result.CellStyleResultSet;
import io.github.zouzhiy.excel.read.WorkbookRead;
import io.github.zouzhiy.excel.support.metadata.DemoDefault;
import io.github.zouzhiy.excel.support.utils.TestFileUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultRowStyleReadTest {

    private final ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().build();

    @Test
    void readTitle1() {
        InputStream inputStream = TestFileUtils.getInputStream("statics/import/data-title-style-read.xlsx");
        WorkbookParameter workbookParameter = WorkbookParameter.builder()
                .input(inputStream)
                .sheetParameter(SheetParameter.builder().sheet(0).titleRowStartIndex(-1).headRowStartIndex(1).dataRowStartIndex(3).build())
                .build();
        WorkbookRead workbookRead = zouzhiyExcelFactory.getWorkbookRead(workbookParameter, DemoDefault.class);

        SheetContext sheetContext = new DefaultSheetContext(workbookRead.getWorkbookContext(), workbookRead.getExcelClassConfig(), workbookParameter.getSheetParameter());


        DefaultRowStyleRead defaultRowStyleRead = new DefaultRowStyleRead();
        CellStyle cellStyle = defaultRowStyleRead.readTitle(sheetContext);
        assert cellStyle == null;
    }

    @Test
    void readTitle2() {
        SheetContext sheetContext = this.getSheetContext();

        DefaultRowStyleRead defaultRowStyleRead = new DefaultRowStyleRead();
        CellStyle cellStyle = defaultRowStyleRead.readTitle(sheetContext);
        assert cellStyle != null;
    }

    @Test
    void readHead1() {
        InputStream inputStream = TestFileUtils.getInputStream("statics/import/data-title-style-read.xlsx");
        WorkbookParameter workbookParameter = WorkbookParameter.builder()
                .input(inputStream)
                .sheetParameter(SheetParameter.builder().sheet(0).titleRowStartIndex(-1).headRowStartIndex(-1).dataRowStartIndex(3).build())
                .build();
        WorkbookRead workbookRead = zouzhiyExcelFactory.getWorkbookRead(workbookParameter, DemoDefault.class);

        SheetContext sheetContext = new DefaultSheetContext(workbookRead.getWorkbookContext(), workbookRead.getExcelClassConfig(), workbookParameter.getSheetParameter());

        DefaultRowStyleRead defaultRowStyleRead = new DefaultRowStyleRead();
        CellStyleResultSet cellStyleResultSet = defaultRowStyleRead.readHead(sheetContext);

        assertNotNull(cellStyleResultSet);
        assertTrue(cellStyleResultSet.getCellStyleResultList().isEmpty());
    }


    @Test
    void readHead2() {
        SheetContext sheetContext = this.getSheetContext();

        DefaultRowStyleRead defaultRowStyleRead = new DefaultRowStyleRead();
        CellStyleResultSet cellStyleResultSet = defaultRowStyleRead.readHead(sheetContext);

        assertNotNull(cellStyleResultSet);
        assertTrue(cellStyleResultSet.getCellStyleResultList().size() > 0);
    }

    @Test
    void readData1() {
        InputStream inputStream = TestFileUtils.getInputStream("statics/import/data-title-style-read.xlsx");
        WorkbookParameter workbookParameter = WorkbookParameter.builder()
                .input(inputStream)
                .sheetParameter(SheetParameter.builder().sheet(0).titleRowStartIndex(-1).headRowStartIndex(-1).dataRowStartIndex(32).build())
                .build();
        WorkbookRead workbookRead = zouzhiyExcelFactory.getWorkbookRead(workbookParameter, DemoDefault.class);

        SheetContext sheetContext = new DefaultSheetContext(workbookRead.getWorkbookContext(), workbookRead.getExcelClassConfig(), workbookParameter.getSheetParameter());


        DefaultRowStyleRead defaultRowStyleRead = new DefaultRowStyleRead();
        CellStyleResultSet cellStyleResultSet = defaultRowStyleRead.readHead(sheetContext);

        assertNotNull(cellStyleResultSet);
        assertTrue(cellStyleResultSet.getCellStyleResultList().isEmpty());
    }

    @Test
    void readData2() {
        SheetContext sheetContext = this.getSheetContext();
        DefaultRowStyleRead defaultRowStyleRead = new DefaultRowStyleRead();
        CellStyleResultSet cellStyleResultSet = defaultRowStyleRead.readHead(sheetContext);

        assertNotNull(cellStyleResultSet);
        assertTrue(cellStyleResultSet.getCellStyleResultList().size() > 0);
    }


    private SheetContext getSheetContext() {

        InputStream inputStream = TestFileUtils.getInputStream("statics/import/data-title-style-read.xlsx");
        WorkbookParameter workbookParameter = WorkbookParameter.builder()
                .input(inputStream)
                .sheetParameter(SheetParameter.builder().sheet(0).titleRowStartIndex(0).headRowStartIndex(1).dataRowStartIndex(3).build())
                .build();
        WorkbookRead workbookRead = zouzhiyExcelFactory.getWorkbookRead(workbookParameter, DemoDefault.class);

        return new DefaultSheetContext(workbookRead.getWorkbookContext(), workbookRead.getExcelClassConfig(), workbookParameter.getSheetParameter());

    }
}
