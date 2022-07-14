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
import io.github.zouzhiy.excel.utils.RegionUtils;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultCellStyleReadTest {

    private final Random random = new Random(System.currentTimeMillis());
    private final ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().build();
    private final DefaultCellStyleRead defaultCellStyleRead = DefaultCellStyleRead.getInstance();

    @Test
    void read() {
        SheetContext sheetContext = this.getSheetContext();

        int i = 1;
        while (i < 50) {
            int rowspan = testRow(sheetContext, i);
            i = i + rowspan;
        }
        assert true;

    }

    private int testRow(SheetContext sheetContext, int rowIndex) {
        int rowspan = random.nextBoolean() ? 1 : random.nextInt(3) + 1;
        int colStartIndex = random.nextInt(10);
        int lasCol = random.nextBoolean() ? colStartIndex : colStartIndex + random.nextInt(5);

        Sheet sheet = sheetContext.getSheet();
        List<Row> rowList = new ArrayList<>();
        for (int i = 0; i < rowspan; i++) {
            Row row = sheet.createRow(rowIndex + i);
            rowList.add(row);
        }
        Row row = rowList.get(0);
        Cell cell = row.createCell(colStartIndex);

        Workbook workbook = sheetContext.getWorkbookContext().getWorkbook();
        DataFormat dataFormat = workbook.createDataFormat();
        CellStyle cellStyleInsert = workbook.createCellStyle();
        if (random.nextBoolean()) {
            Font font = workbook.createFont();
            font.setFontName("宋体");
            cellStyleInsert.setFont(font);
        }
        if (random.nextBoolean()) {
            cellStyleInsert.setHidden(random.nextBoolean());
        }

        if (random.nextBoolean()) {
            cellStyleInsert.setLocked(random.nextBoolean());
        }

        if (random.nextBoolean()) {
            cellStyleInsert.setQuotePrefixed(random.nextBoolean());
        }

        if (random.nextBoolean()) {
            cellStyleInsert.setAlignment(HorizontalAlignment.values()[random.nextInt(HorizontalAlignment.values().length)]);
        }
        if (random.nextBoolean()) {
            cellStyleInsert.setWrapText(random.nextBoolean());
        }

        if (random.nextBoolean()) {
            cellStyleInsert.setVerticalAlignment(VerticalAlignment.values()[random.nextInt(VerticalAlignment.values().length)]);
        }

        if (random.nextBoolean()) {
            cellStyleInsert.setRotation((short) random.nextInt(Short.MAX_VALUE));
        }
        if (random.nextBoolean()) {
            cellStyleInsert.setIndention((short) random.nextInt(Short.MAX_VALUE));
        }

        if (random.nextBoolean()) {
            cellStyleInsert.setBorderLeft(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        }

        if (random.nextBoolean()) {
            cellStyleInsert.setBottomBorderColor((short) random.nextInt(Short.MAX_VALUE));
        }

        cell.setCellStyle(cellStyleInsert);
        RegionUtils.addMergedRegionIfPresent(sheetContext, cellStyleInsert, rowIndex, rowIndex + rowspan - 1, colStartIndex, lasCol);

        CellStyle cellStyle = defaultCellStyleRead.read(sheetContext, rowList, colStartIndex, lasCol);

        assertEquals(cellStyleInsert.getFontIndex(), cellStyle.getFontIndex());

        assertEquals(cellStyleInsert.getHidden(), cellStyle.getHidden());
        assertEquals(cellStyleInsert.getLocked(), cellStyle.getLocked());
        // assertEquals(cellStyleInsert.getQuotePrefixed(), cellStyle.getQuotePrefixed());
        assertEquals(cellStyleInsert.getAlignment(), cellStyle.getAlignment());
        assertEquals(cellStyleInsert.getWrapText(), cellStyle.getWrapText());
        assertEquals(cellStyleInsert.getVerticalAlignment(), cellStyle.getVerticalAlignment());
        assertEquals(cellStyleInsert.getRotation(), cellStyle.getRotation());
        assertEquals(cellStyleInsert.getIndention(), cellStyle.getIndention());
        assertEquals(cellStyleInsert.getBorderLeft(), cellStyle.getBorderLeft());
        assertEquals(cellStyleInsert.getBottomBorderColor(), cellStyle.getBottomBorderColor());

        return rowspan;
    }


    private SheetContext getSheetContext() {

        InputStream inputStream = TestFileUtils.getInputStream("import/empty.xlsx");
        WorkbookParameter workbookParameter = WorkbookParameter.builder()
                .input(inputStream)
                .sheetParameter(SheetParameter.builder().build())
                .build();
        WorkbookRead workbookRead = zouzhiyExcelFactory.getWorkbookRead(workbookParameter, DemoDefault.class);

        return new DefaultSheetContext(workbookRead.getWorkbookContext(), workbookRead.getExcelClassConfig(), workbookParameter.getSheetParameter());

    }
}
