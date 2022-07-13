package io.github.zouzhiy.excel.metadata.config;

import io.github.zouzhiy.excel.annotation.ExcelFont;
import io.github.zouzhiy.excel.enums.FontTypeOffset;
import org.apache.poi.common.usermodel.fonts.FontCharset;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontUnderline;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

class ExcelFontConfigTest {

    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void getDefaultExcelFontConfig() {
        ExcelFontConfig excelFontConfig = ExcelFontConfig.builder().build();

        Assertions.assertEquals(excelFontConfig.getFontName(), "宋体");
        Assertions.assertEquals(excelFontConfig.getFontHeight(), -1);
        Assertions.assertEquals(excelFontConfig.getFontHeightInPoints(), -1);
        Assertions.assertFalse(excelFontConfig.isItalic());
        Assertions.assertFalse(excelFontConfig.isStrikeout());
        Assertions.assertEquals(excelFontConfig.getColor(), Font.COLOR_NORMAL);
        Assertions.assertEquals(excelFontConfig.getTypeOffset(), FontTypeOffset.NONE);
        Assertions.assertEquals(excelFontConfig.getUnderline(), FontUnderline.NONE);
        Assertions.assertEquals(excelFontConfig.getCharset(), FontCharset.DEFAULT);
        Assertions.assertFalse(excelFontConfig.isBold());
    }

    @Test
    void getDefaultExcelFontConfigTitle() {
        ExcelFontConfig excelFontConfig = ExcelFontConfig.getDefaultExcelFontConfigTitle();

        Assertions.assertEquals(excelFontConfig.getFontName(), "宋体");
        Assertions.assertEquals(excelFontConfig.getFontHeight(), -1);
        Assertions.assertEquals(excelFontConfig.getFontHeightInPoints(), 16);
        Assertions.assertFalse(excelFontConfig.isItalic());
        Assertions.assertFalse(excelFontConfig.isStrikeout());
        Assertions.assertEquals(excelFontConfig.getColor(), Font.COLOR_NORMAL);
        Assertions.assertEquals(excelFontConfig.getTypeOffset(), FontTypeOffset.NONE);
        Assertions.assertEquals(excelFontConfig.getUnderline(), FontUnderline.NONE);
        Assertions.assertEquals(excelFontConfig.getCharset(), FontCharset.DEFAULT);
        Assertions.assertTrue(excelFontConfig.isBold());
    }

    @Test
    void getDefaultExcelFontConfigHead() {
        ExcelFontConfig excelFontConfig = ExcelFontConfig.getDefaultExcelFontConfigHead();

        Assertions.assertEquals(excelFontConfig.getFontName(), "宋体");
        Assertions.assertEquals(excelFontConfig.getFontHeight(), -1);
        Assertions.assertEquals(excelFontConfig.getFontHeightInPoints(), 13);
        Assertions.assertFalse(excelFontConfig.isItalic());
        Assertions.assertFalse(excelFontConfig.isStrikeout());
        Assertions.assertEquals(excelFontConfig.getColor(), Font.COLOR_NORMAL);
        Assertions.assertEquals(excelFontConfig.getTypeOffset(), FontTypeOffset.NONE);
        Assertions.assertEquals(excelFontConfig.getUnderline(), FontUnderline.NONE);
        Assertions.assertEquals(excelFontConfig.getCharset(), FontCharset.DEFAULT);
        Assertions.assertTrue(excelFontConfig.isBold());
    }

    @Test
    void getDefaultExcelFontConfigData() {
        ExcelFontConfig excelFontConfig = ExcelFontConfig.getDefaultExcelFontConfigData();

        Assertions.assertEquals(excelFontConfig.getFontName(), "宋体");
        Assertions.assertEquals(excelFontConfig.getFontHeight(), -1);
        Assertions.assertEquals(excelFontConfig.getFontHeightInPoints(), 10);
        Assertions.assertFalse(excelFontConfig.isItalic());
        Assertions.assertFalse(excelFontConfig.isStrikeout());
        Assertions.assertEquals(excelFontConfig.getColor(), Font.COLOR_NORMAL);
        Assertions.assertEquals(excelFontConfig.getTypeOffset(), FontTypeOffset.NONE);
        Assertions.assertEquals(excelFontConfig.getUnderline(), FontUnderline.NONE);
        Assertions.assertEquals(excelFontConfig.getCharset(), FontCharset.DEFAULT);
        Assertions.assertFalse(excelFontConfig.isBold());
    }

    @RepeatedTest(10)
    void buildByExcelFont() {
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

        ExcelFontConfig excelFontConfig = ExcelFontConfig.buildByExcelFont(excelFont);

        Assertions.assertEquals(excelFontConfig.getFontName(), excelFont.fontName());
        Assertions.assertEquals(excelFontConfig.getFontHeight(), excelFont.fontHeight());
        Assertions.assertEquals(excelFontConfig.getFontHeightInPoints(), excelFont.fontHeightInPoints());
        Assertions.assertEquals(excelFontConfig.isItalic(), excelFont.italic());
        Assertions.assertEquals(excelFontConfig.isStrikeout(), excelFont.strikeout());
        Assertions.assertEquals(excelFontConfig.getColor(), excelFont.color());
        Assertions.assertEquals(excelFontConfig.getTypeOffset(), excelFont.typeOffset());
        Assertions.assertEquals(excelFontConfig.getUnderline(), excelFont.underline());
        Assertions.assertEquals(excelFontConfig.getCharset(), excelFont.charset());
        Assertions.assertEquals(excelFontConfig.isBold(), excelFont.bold());
    }
}
