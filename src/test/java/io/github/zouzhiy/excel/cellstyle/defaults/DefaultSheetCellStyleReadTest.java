package io.github.zouzhiy.excel.cellstyle.defaults;

import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactory;
import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactoryBuilder;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.context.defualts.DefaultSheetContext;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.read.WorkbookRead;
import io.github.zouzhiy.excel.support.metadata.DemoDefault;
import io.github.zouzhiy.excel.support.utils.TestFileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

class DefaultSheetCellStyleReadTest {

    private final ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().build();

    @Test
    void getSheetContext1() {
        SheetContext sheetContext = this.getSheetContext();

        DefaultSheetCellStyleRead defaultSheetCellStyleRead = new DefaultSheetCellStyleRead(sheetContext);
        Assertions.assertEquals(sheetContext, defaultSheetCellStyleRead.getSheetContext());

    }

    @Test
    void read1() {
        SheetContext sheetContext = this.getSheetContext();

        DefaultSheetCellStyleRead defaultSheetCellStyleRead = new DefaultSheetCellStyleRead(sheetContext);

        defaultSheetCellStyleRead.read();

        assert true;

    }

    @Test
    void read2() {
        SheetContext sheetContext = this.getSheetContext();

        DefaultSheetCellStyleRead defaultSheetCellStyleRead = new DefaultSheetCellStyleRead(sheetContext);

        defaultSheetCellStyleRead.read();

        assert true;

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
