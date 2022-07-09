package io.github.zouzhiy.excel.context.defualts;

import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactory;
import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactoryBuilder;
import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.read.WorkbookRead;
import io.github.zouzhiy.excel.support.metadata.DemoDefault;
import io.github.zouzhiy.excel.support.utils.TestFileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Random;

class DefaultRowContextTest {

    private final ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().build();
    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void getSheetContext1() {
        SheetContext sheetContext = this.getSheetContext();
        RowContext rowContext = new DefaultRowContext(sheetContext, null, null, 1);

        Assertions.assertEquals(sheetContext, rowContext.getSheetContext());
    }

    @Test
    void getRowData() {
        SheetContext sheetContext = this.getSheetContext();
        DemoDefault demoDefault = new DemoDefault();
        demoDefault.setName("name--" + System.currentTimeMillis());
        demoDefault.setTitle("title--" + System.currentTimeMillis());
        RowContext rowContext = new DefaultRowContext(sheetContext, demoDefault, null, 1);

        Assertions.assertEquals(demoDefault, rowContext.getRowData());
    }

    @Test
    void getRowList() {
        for (int i = 0; i < 10; i++) {
            SheetContext sheetContext = this.getSheetContext();

            int rowIndex = random.nextInt(10);
            List<Row> rowList = sheetContext.getRowList(rowIndex, rowIndex + random.nextInt(5));
            DemoDefault demoDefault = new DemoDefault();
            demoDefault.setName("name--" + System.currentTimeMillis());
            demoDefault.setTitle("title--" + System.currentTimeMillis());
            RowContext rowContext = new DefaultRowContext(sheetContext, demoDefault, rowList, 1);

            Assertions.assertEquals(rowList, rowContext.getRowList());
        }

    }

    @Test
    void getRowspan() {
        for (int i = 0; i < 10; i++) {
            SheetContext sheetContext = this.getSheetContext();

            int rowIndex = random.nextInt(10);
            List<Row> rowList = sheetContext.getRowList(rowIndex, rowIndex + random.nextInt(5));
            DemoDefault demoDefault = new DemoDefault();
            demoDefault.setName("name--" + System.currentTimeMillis());
            demoDefault.setTitle("title--" + System.currentTimeMillis());
            int rowspan = random.nextInt(10);
            RowContext rowContext = new DefaultRowContext(sheetContext, demoDefault, rowList, rowspan);

            Assertions.assertEquals(rowspan, rowContext.getRowspan());
        }

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
