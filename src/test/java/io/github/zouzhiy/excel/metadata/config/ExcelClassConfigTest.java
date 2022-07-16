package io.github.zouzhiy.excel.metadata.config;

import io.github.zouzhiy.excel.annotation.ExcelClass;
import io.github.zouzhiy.excel.annotation.ExcelFont;
import io.github.zouzhiy.excel.annotation.ExcelStyle;
import io.github.zouzhiy.excel.cellstyle.RowStyleRead;
import io.github.zouzhiy.excel.cellstyle.defaults.DefaultRowStyleRead;
import io.github.zouzhiy.excel.enums.FontTypeOffset;
import io.github.zouzhiy.excel.enums.StyleHorizontalAlignment;
import io.github.zouzhiy.excel.enums.StyleVerticalAlignment;
import io.github.zouzhiy.excel.read.RowFootRead;
import io.github.zouzhiy.excel.read.RowHeadRead;
import io.github.zouzhiy.excel.read.RowTitleRead;
import io.github.zouzhiy.excel.read.defaults.DefaultRowFootRead;
import io.github.zouzhiy.excel.read.defaults.DefaultRowHeadRead;
import io.github.zouzhiy.excel.read.defaults.DefaultRowTitleRead;
import io.github.zouzhiy.excel.write.RowFootWrite;
import io.github.zouzhiy.excel.write.RowHeadWrite;
import io.github.zouzhiy.excel.write.RowTitleWrite;
import io.github.zouzhiy.excel.write.defaults.DefaultRowFootWrite;
import io.github.zouzhiy.excel.write.defaults.DefaultRowHeadWrite;
import io.github.zouzhiy.excel.write.defaults.DefaultRowTitleWrite;
import org.apache.poi.common.usermodel.fonts.FontCharset;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.FontUnderline;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExcelClassConfigTest {

    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void getDefaultExcelClassConfig() {
        ExcelClassConfig excelClassConfig = ExcelClassConfig.builder().build();

        assertEquals(excelClassConfig.getRowTitleWrite(), DefaultRowTitleWrite.class);
        assertEquals(excelClassConfig.getRowHeadWrite(), DefaultRowHeadWrite.class);
        assertEquals(excelClassConfig.getRowFootWrite(), DefaultRowFootWrite.class);
        assertEquals(excelClassConfig.getRowTitleRead(), DefaultRowTitleRead.class);
        assertEquals(excelClassConfig.getRowHeadRead(), DefaultRowHeadRead.class);
        assertEquals(excelClassConfig.getRowFootRead(), DefaultRowFootRead.class);
        assertEquals(excelClassConfig.getTitleFormat(), "@");
        assertEquals(excelClassConfig.getTitleStyle(), ExcelStyleConfig.getDefaultExcelStyleConfigTitle());
        assertEquals(excelClassConfig.getRowStyleRead(), DefaultRowStyleRead.class);
        assertEquals(excelClassConfig.getAutoSizeColumn(), false);
        assertTrue(excelClassConfig.getItemList().isEmpty());

        assertEquals(excelClassConfig.getItemList().getClass(), Collections.emptyList().getClass());
    }

    @Test
    void getDefaultExcelClassConfigEmptyList() {
        ExcelClassConfig excelClassConfig = ExcelClassConfig.getDefaultExcelClassConfig(Collections.emptyList());

        assertEquals(excelClassConfig.getRowTitleWrite(), DefaultRowTitleWrite.class);
        assertEquals(excelClassConfig.getRowHeadWrite(), DefaultRowHeadWrite.class);
        assertEquals(excelClassConfig.getRowFootWrite(), DefaultRowFootWrite.class);
        assertEquals(excelClassConfig.getRowTitleRead(), DefaultRowTitleRead.class);
        assertEquals(excelClassConfig.getRowHeadRead(), DefaultRowHeadRead.class);
        assertEquals(excelClassConfig.getRowFootRead(), DefaultRowFootRead.class);
        assertEquals(excelClassConfig.getTitleFormat(), "@");
        assertEquals(excelClassConfig.getTitleStyle(), ExcelStyleConfig.getDefaultExcelStyleConfigTitle());
        assertEquals(excelClassConfig.getRowStyleRead(), DefaultRowStyleRead.class);
        assertEquals(excelClassConfig.getAutoSizeColumn(), false);
        assertTrue(excelClassConfig.getItemList().isEmpty());
    }

    @Test
    void getDefaultExcelClassConfigSingleList() {
        ExcelFieldConfig excelFieldConfig = Mockito.mock(ExcelFieldConfig.class);
        ExcelClassConfig excelClassConfig = ExcelClassConfig.getDefaultExcelClassConfig(Collections.singletonList(excelFieldConfig));

        assertEquals(excelClassConfig.getRowTitleWrite(), DefaultRowTitleWrite.class);
        assertEquals(excelClassConfig.getRowHeadWrite(), DefaultRowHeadWrite.class);
        assertEquals(excelClassConfig.getRowFootWrite(), DefaultRowFootWrite.class);
        assertEquals(excelClassConfig.getRowTitleRead(), DefaultRowTitleRead.class);
        assertEquals(excelClassConfig.getRowHeadRead(), DefaultRowHeadRead.class);
        assertEquals(excelClassConfig.getRowFootRead(), DefaultRowFootRead.class);
        assertEquals(excelClassConfig.getTitleFormat(), "@");
        assertEquals(excelClassConfig.getTitleStyle(), ExcelStyleConfig.getDefaultExcelStyleConfigTitle());
        assertEquals(excelClassConfig.getRowStyleRead(), DefaultRowStyleRead.class);
        assertEquals(excelClassConfig.getAutoSizeColumn(), false);
        assertEquals(excelClassConfig.getItemList().size(), 1);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    void buildByExcelClass() {
        ExcelClass excelClass = Mockito.mock(ExcelClass.class);


        ExcelStyle excelStyle = Mockito.mock(ExcelStyle.class);
        ExcelFont excelFont = Mockito.mock(ExcelFont.class);

        Mockito.when(excelFont.fontName()).thenReturn("" + random.nextDouble());
        Mockito.when(excelFont.fontHeight()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(excelFont.fontHeightInPoints()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(excelFont.italic()).thenReturn(random.nextBoolean());
        Mockito.when(excelFont.strikeout()).thenReturn(random.nextBoolean());
        Mockito.when(excelFont.color()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(excelFont.typeOffset()).thenReturn(FontTypeOffset.values()[random.nextInt(FontTypeOffset.values().length)]);
        Mockito.when(excelFont.underline()).thenReturn(FontUnderline.values()[random.nextInt(FontUnderline.values().length)]);
        Mockito.when(excelFont.charset()).thenReturn(FontCharset.values()[random.nextInt(FontCharset.values().length)]);
        Mockito.when(excelFont.bold()).thenReturn(random.nextBoolean());

        Mockito.when(excelStyle.font()).thenReturn(excelFont);
        Mockito.when(excelStyle.hidden()).thenReturn(random.nextBoolean());
        Mockito.when(excelStyle.locked()).thenReturn(random.nextBoolean());
        Mockito.when(excelStyle.quotePrefix()).thenReturn(random.nextBoolean());
        Mockito.when(excelStyle.horizontalAlignment()).thenReturn(StyleHorizontalAlignment.values()[random.nextInt(StyleHorizontalAlignment.values().length)]);
        Mockito.when(excelStyle.wrapText()).thenReturn(random.nextBoolean());
        Mockito.when(excelStyle.verticalAlignment()).thenReturn(StyleVerticalAlignment.values()[random.nextInt(StyleVerticalAlignment.values().length)]);
        Mockito.when(excelStyle.rotation()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(excelStyle.indent()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(excelStyle.borderLeft()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(excelStyle.borderRight()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(excelStyle.borderTop()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(excelStyle.borderBottom()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(excelStyle.leftBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(excelStyle.rightBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(excelStyle.topBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(excelStyle.bottomBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(excelStyle.fillPattern()).thenReturn(FillPatternType.values()[random.nextInt(FillPatternType.values().length)]);
        Mockito.when(excelStyle.fillBackgroundColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(excelStyle.fillForegroundColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(excelStyle.shrinkToFit()).thenReturn(random.nextBoolean());

        RowTitleWrite rowTitleWrite = Mockito.mock(RowTitleWrite.class);
        RowHeadWrite rowHeadWrite = Mockito.mock(RowHeadWrite.class);
        RowFootWrite rowFootWrite = Mockito.mock(RowFootWrite.class);
        RowTitleRead rowTitleRead = Mockito.mock(RowTitleRead.class);
        RowHeadRead rowHeadRead = Mockito.mock(RowHeadRead.class);
        RowFootRead rowFootRead = Mockito.mock(RowFootRead.class);
        RowStyleRead rowStyleRead = Mockito.mock(RowStyleRead.class);

        Class rowTitleWriteClass = rowTitleWrite.getClass();
        Class rowHeadWriteClass = rowHeadWrite.getClass();
        Class rowFootWriteClass = rowFootWrite.getClass();
        Class rowTitleReadClass = rowTitleRead.getClass();
        Class rowHeadReadClass = rowHeadRead.getClass();
        Class rowFootReadClass = rowFootRead.getClass();
        Class rowStyleReadClass = rowStyleRead.getClass();

        Mockito.when(excelClass.rowTitleWrite()).thenReturn(rowTitleWriteClass);
        Mockito.when(excelClass.rowHeadWrite()).thenReturn(rowHeadWriteClass);
        Mockito.when(excelClass.rowFootWrite()).thenReturn(rowFootWriteClass);
        Mockito.when(excelClass.rowTitleRead()).thenReturn(rowTitleReadClass);
        Mockito.when(excelClass.rowHeadRead()).thenReturn(rowHeadReadClass);
        Mockito.when(excelClass.rowFootRead()).thenReturn(rowFootReadClass);
        Mockito.when(excelClass.rowStyleRead()).thenReturn(rowStyleReadClass);
        Mockito.when(excelClass.titleFormat()).thenReturn("" + random.nextDouble());
        Mockito.when(excelClass.titleStyle()).thenReturn(excelStyle);
        Mockito.when(excelClass.autoSizeColumn()).thenReturn(random.nextBoolean());

        ExcelClassConfig excelClassConfig = ExcelClassConfig.buildByExcelClass(excelClass, Collections.emptyList());

        assertEquals(excelClassConfig.getRowTitleWrite(), excelClass.rowTitleWrite());
        assertEquals(excelClassConfig.getRowHeadWrite(), excelClass.rowHeadWrite());
        assertEquals(excelClassConfig.getRowFootWrite(), excelClass.rowFootWrite());
        assertEquals(excelClassConfig.getRowTitleRead(), excelClass.rowTitleRead());
        assertEquals(excelClassConfig.getRowHeadRead(), excelClass.rowHeadRead());
        assertEquals(excelClassConfig.getRowFootRead(), excelClass.rowFootRead());
        assertEquals(excelClassConfig.getTitleFormat(), excelClass.titleFormat());
        assertEquals(excelClassConfig.getTitleStyle(), ExcelStyleConfig.buildByExcelStyle(excelClass.titleStyle()));
        assertEquals(excelClassConfig.getRowStyleRead(), excelClass.rowStyleRead());
        assertEquals(excelClassConfig.getAutoSizeColumn(), excelClass.autoSizeColumn());
        assertEquals(excelClassConfig.getItemList().size(), 0);
    }


}
