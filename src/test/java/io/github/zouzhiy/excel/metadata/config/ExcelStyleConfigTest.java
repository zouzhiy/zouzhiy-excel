package io.github.zouzhiy.excel.metadata.config;

import io.github.zouzhiy.excel.annotation.ExcelFont;
import io.github.zouzhiy.excel.annotation.ExcelStyle;
import io.github.zouzhiy.excel.enums.FontTypeOffset;
import io.github.zouzhiy.excel.enums.StyleHorizontalAlignment;
import io.github.zouzhiy.excel.enums.StyleVerticalAlignment;
import org.apache.poi.common.usermodel.fonts.FontCharset;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.FontUnderline;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

class ExcelStyleConfigTest {

    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void getDefaultExcelStyleConfig() {
        ExcelStyleConfig excelStyleConfig = ExcelStyleConfig.builder().build();

        Assertions.assertEquals(excelStyleConfig.getFont(), ExcelFontConfig.getDefaultExcelFontConfigData());
        Assertions.assertFalse(excelStyleConfig.isHidden());
        Assertions.assertFalse(excelStyleConfig.isLocked());
        Assertions.assertFalse(excelStyleConfig.isQuotePrefix());
        Assertions.assertEquals(excelStyleConfig.getHorizontalAlignment(), StyleHorizontalAlignment.GENERAL);
        Assertions.assertTrue(excelStyleConfig.isWrapText());
        Assertions.assertEquals(excelStyleConfig.getVerticalAlignment(), StyleVerticalAlignment.CENTER);
        Assertions.assertEquals(excelStyleConfig.getRotation(), 0);
        Assertions.assertEquals(excelStyleConfig.getIndent(), -1);
        Assertions.assertEquals(excelStyleConfig.getBorderLeft(), BorderStyle.NONE);
        Assertions.assertEquals(excelStyleConfig.getBorderRight(), BorderStyle.NONE);
        Assertions.assertEquals(excelStyleConfig.getBorderTop(), BorderStyle.NONE);
        Assertions.assertEquals(excelStyleConfig.getBorderBottom(), BorderStyle.NONE);
        Assertions.assertEquals(excelStyleConfig.getLeftBorderColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getRightBorderColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getTopBorderColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getBottomBorderColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getFillPattern(), FillPatternType.NO_FILL);
        Assertions.assertEquals(excelStyleConfig.getFillBackgroundColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getFillForegroundColor(), -1);
        Assertions.assertFalse(excelStyleConfig.isShrinkToFit());
    }

    @Test
    void getDefaultExcelStyleConfigTitle() {
        ExcelStyleConfig excelStyleConfig = ExcelStyleConfig.getDefaultExcelStyleConfigTitle();

        Assertions.assertEquals(excelStyleConfig.getFont(), ExcelFontConfig.getDefaultExcelFontConfigTitle());
        Assertions.assertFalse(excelStyleConfig.isHidden());
        Assertions.assertFalse(excelStyleConfig.isLocked());
        Assertions.assertFalse(excelStyleConfig.isQuotePrefix());
        Assertions.assertEquals(excelStyleConfig.getHorizontalAlignment(), StyleHorizontalAlignment.CENTER);
        Assertions.assertTrue(excelStyleConfig.isWrapText());
        Assertions.assertEquals(excelStyleConfig.getVerticalAlignment(), StyleVerticalAlignment.CENTER);
        Assertions.assertEquals(excelStyleConfig.getRotation(), 0);
        Assertions.assertEquals(excelStyleConfig.getIndent(), -1);
        Assertions.assertEquals(excelStyleConfig.getBorderLeft(), BorderStyle.NONE);
        Assertions.assertEquals(excelStyleConfig.getBorderRight(), BorderStyle.NONE);
        Assertions.assertEquals(excelStyleConfig.getBorderTop(), BorderStyle.NONE);
        Assertions.assertEquals(excelStyleConfig.getBorderBottom(), BorderStyle.NONE);
        Assertions.assertEquals(excelStyleConfig.getLeftBorderColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getRightBorderColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getTopBorderColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getBottomBorderColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getFillPattern(), FillPatternType.NO_FILL);
        Assertions.assertEquals(excelStyleConfig.getFillBackgroundColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getFillForegroundColor(), -1);
        Assertions.assertFalse(excelStyleConfig.isShrinkToFit());
    }


    @Test
    void getDefaultExcelStyleConfigHead() {
        ExcelStyleConfig excelStyleConfig = ExcelStyleConfig.getDefaultExcelStyleConfigHead();

        Assertions.assertEquals(excelStyleConfig.getFont(), ExcelFontConfig.getDefaultExcelFontConfigHead());
        Assertions.assertFalse(excelStyleConfig.isHidden());
        Assertions.assertFalse(excelStyleConfig.isLocked());
        Assertions.assertFalse(excelStyleConfig.isQuotePrefix());
        Assertions.assertEquals(excelStyleConfig.getHorizontalAlignment(), StyleHorizontalAlignment.CENTER);
        Assertions.assertTrue(excelStyleConfig.isWrapText());
        Assertions.assertEquals(excelStyleConfig.getVerticalAlignment(), StyleVerticalAlignment.CENTER);
        Assertions.assertEquals(excelStyleConfig.getRotation(), 0);
        Assertions.assertEquals(excelStyleConfig.getIndent(), -1);
        Assertions.assertEquals(excelStyleConfig.getBorderLeft(), BorderStyle.NONE);
        Assertions.assertEquals(excelStyleConfig.getBorderRight(), BorderStyle.NONE);
        Assertions.assertEquals(excelStyleConfig.getBorderTop(), BorderStyle.NONE);
        Assertions.assertEquals(excelStyleConfig.getBorderBottom(), BorderStyle.NONE);
        Assertions.assertEquals(excelStyleConfig.getLeftBorderColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getRightBorderColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getTopBorderColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getBottomBorderColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getFillPattern(), FillPatternType.NO_FILL);
        Assertions.assertEquals(excelStyleConfig.getFillBackgroundColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getFillForegroundColor(), -1);
        Assertions.assertFalse(excelStyleConfig.isShrinkToFit());
    }

    @Test
    void getDefaultExcelStyleConfigData() {
        ExcelStyleConfig excelStyleConfig = ExcelStyleConfig.getDefaultExcelStyleConfigData();

        Assertions.assertEquals(excelStyleConfig.getFont(), ExcelFontConfig.getDefaultExcelFontConfigData());
        Assertions.assertFalse(excelStyleConfig.isHidden());
        Assertions.assertFalse(excelStyleConfig.isLocked());
        Assertions.assertFalse(excelStyleConfig.isQuotePrefix());
        Assertions.assertEquals(excelStyleConfig.getHorizontalAlignment(), StyleHorizontalAlignment.GENERAL);
        Assertions.assertTrue(excelStyleConfig.isWrapText());
        Assertions.assertEquals(excelStyleConfig.getVerticalAlignment(), StyleVerticalAlignment.CENTER);
        Assertions.assertEquals(excelStyleConfig.getRotation(), 0);
        Assertions.assertEquals(excelStyleConfig.getIndent(), -1);
        Assertions.assertEquals(excelStyleConfig.getBorderLeft(), BorderStyle.NONE);
        Assertions.assertEquals(excelStyleConfig.getBorderRight(), BorderStyle.NONE);
        Assertions.assertEquals(excelStyleConfig.getBorderTop(), BorderStyle.NONE);
        Assertions.assertEquals(excelStyleConfig.getBorderBottom(), BorderStyle.NONE);
        Assertions.assertEquals(excelStyleConfig.getLeftBorderColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getRightBorderColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getTopBorderColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getBottomBorderColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getFillPattern(), FillPatternType.NO_FILL);
        Assertions.assertEquals(excelStyleConfig.getFillBackgroundColor(), -1);
        Assertions.assertEquals(excelStyleConfig.getFillForegroundColor(), -1);
        Assertions.assertFalse(excelStyleConfig.isShrinkToFit());
    }

    @RepeatedTest(10)
    void buildByExcelStyle() {
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

        ExcelStyleConfig excelStyleConfig = ExcelStyleConfig.buildByExcelStyle(excelStyle);

        Assertions.assertEquals(excelStyleConfig.getFont(), ExcelFontConfig.buildByExcelFont(excelStyle.font()));
        Assertions.assertEquals(excelStyleConfig.isHidden(), excelStyle.hidden());
        Assertions.assertEquals(excelStyleConfig.isLocked(), excelStyle.locked());
        Assertions.assertEquals(excelStyleConfig.isQuotePrefix(), excelStyle.quotePrefix());
        Assertions.assertEquals(excelStyleConfig.getHorizontalAlignment(), excelStyle.horizontalAlignment());
        Assertions.assertEquals(excelStyleConfig.isWrapText(), excelStyle.wrapText());
        Assertions.assertEquals(excelStyleConfig.getVerticalAlignment(), excelStyle.verticalAlignment());
        Assertions.assertEquals(excelStyleConfig.getRotation(), excelStyle.rotation());
        Assertions.assertEquals(excelStyleConfig.getIndent(), excelStyle.indent());
        Assertions.assertEquals(excelStyleConfig.getBorderLeft(), excelStyle.borderLeft());
        Assertions.assertEquals(excelStyleConfig.getBorderRight(), excelStyle.borderRight());
        Assertions.assertEquals(excelStyleConfig.getBorderTop(), excelStyle.borderTop());
        Assertions.assertEquals(excelStyleConfig.getBorderBottom(), excelStyle.borderBottom());
        Assertions.assertEquals(excelStyleConfig.getLeftBorderColor(), excelStyle.leftBorderColor());
        Assertions.assertEquals(excelStyleConfig.getRightBorderColor(), excelStyle.rightBorderColor());
        Assertions.assertEquals(excelStyleConfig.getTopBorderColor(), excelStyle.topBorderColor());
        Assertions.assertEquals(excelStyleConfig.getBottomBorderColor(), excelStyle.bottomBorderColor());
        Assertions.assertEquals(excelStyleConfig.getFillPattern(), excelStyle.fillPattern());
        Assertions.assertEquals(excelStyleConfig.getFillBackgroundColor(), excelStyle.fillBackgroundColor());
        Assertions.assertEquals(excelStyleConfig.getFillForegroundColor(), excelStyle.fillForegroundColor());
        Assertions.assertEquals(excelStyleConfig.isShrinkToFit(), excelStyle.shrinkToFit());
    }
}
