package io.github.zouzhiy.excel.parsing;

import io.github.zouzhiy.excel.annotation.ExcelClass;
import io.github.zouzhiy.excel.annotation.ExcelField;
import io.github.zouzhiy.excel.annotation.ExcelFont;
import io.github.zouzhiy.excel.annotation.ExcelStyle;
import io.github.zouzhiy.excel.cellstyle.RowStyleRead;
import io.github.zouzhiy.excel.cellstyle.defaults.DefaultRowStyleRead;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.enums.FontTypeOffset;
import io.github.zouzhiy.excel.enums.StyleHorizontalAlignment;
import io.github.zouzhiy.excel.enums.StyleVerticalAlignment;
import io.github.zouzhiy.excel.handler.date.DateStringHandler;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.config.ExcelFontConfig;
import io.github.zouzhiy.excel.metadata.config.ExcelStyleConfig;
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
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontUnderline;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class ExcelAnnotationParseTest {


    @Test
    void isConfigCacheEnabled() {
        ExcelAnnotationParse excelAnnotationParse = new ExcelAnnotationParse(new Configuration());
        Assertions.assertTrue(excelAnnotationParse.isConfigCacheEnabled());

    }

    @Test
    void setConfigCacheEnabled() {
        ExcelAnnotationParse excelAnnotationParse = new ExcelAnnotationParse(new Configuration());
        excelAnnotationParse.setConfigCacheEnabled(false);
        Assertions.assertFalse(excelAnnotationParse.isConfigCacheEnabled());

        excelAnnotationParse.setConfigCacheEnabled(true);
        Assertions.assertTrue(excelAnnotationParse.isConfigCacheEnabled());
    }

    @Test
    void findForClass1() {
        ExcelAnnotationParse excelAnnotationParse = new ExcelAnnotationParse(new Configuration());
        excelAnnotationParse.setConfigCacheEnabled(false);

        ExcelClassConfig excelClassConfig = excelAnnotationParse.findForClass(Demo1.class);

        Assertions.assertEquals(excelClassConfig.getRowTitleWrite(), DefaultRowTitleWrite.class);
        Assertions.assertEquals(excelClassConfig.getRowHeadWrite(), DefaultRowHeadWrite.class);
        Assertions.assertEquals(excelClassConfig.getRowFootWrite(), DefaultRowFootWrite.class);
        Assertions.assertEquals(excelClassConfig.getRowTitleRead(), DefaultRowTitleRead.class);
        Assertions.assertEquals(excelClassConfig.getRowHeadRead(), DefaultRowHeadRead.class);
        Assertions.assertEquals(excelClassConfig.getRowFootRead(), DefaultRowFootRead.class);
        Assertions.assertEquals(excelClassConfig.getTitleFormat(), "@");
        Assertions.assertEquals(excelClassConfig.getTitleStyle(), ExcelStyleConfig.getDefaultExcelStyleConfigTitle());
        Assertions.assertEquals(excelClassConfig.getRowStyleRead(), DefaultRowStyleRead.class);
        Assertions.assertEquals(excelClassConfig.getItemList().size(), 3);
    }

    @Test
    void findForClass2() {
        ExcelAnnotationParse excelAnnotationParse = new ExcelAnnotationParse(new Configuration());
        excelAnnotationParse.setConfigCacheEnabled(false);

        ExcelClassConfig excelClassConfig = excelAnnotationParse.findForClass(Demo2.class);

        Assertions.assertEquals(excelClassConfig.getRowTitleWrite(), RowTitleWrite.class);
        Assertions.assertEquals(excelClassConfig.getRowHeadWrite(), RowHeadWrite.class);
        Assertions.assertEquals(excelClassConfig.getRowFootWrite(), RowFootWrite.class);
        Assertions.assertEquals(excelClassConfig.getRowTitleRead(), RowTitleRead.class);
        Assertions.assertEquals(excelClassConfig.getRowHeadRead(), RowHeadRead.class);
        Assertions.assertEquals(excelClassConfig.getRowFootRead(), RowFootRead.class);
        Assertions.assertEquals(excelClassConfig.getTitleFormat(), "@@");
        Assertions.assertEquals(excelClassConfig.getTitleStyle().getRotation(), 22);
        Assertions.assertEquals(excelClassConfig.getRowStyleRead(), RowStyleRead.class);
        Assertions.assertEquals(excelClassConfig.getItemList().size(), 4);
    }


    @Test
    void findForClassTestStyle1() {
        ExcelAnnotationParse excelAnnotationParse = new ExcelAnnotationParse(new Configuration());
        excelAnnotationParse.setConfigCacheEnabled(false);

        ExcelClassConfig excelClassConfig = excelAnnotationParse.findForClass(Demo3.class);

        ExcelStyleConfig excelStyleConfig = excelClassConfig.getTitleStyle();

        Assertions.assertTrue(excelStyleConfig.isHidden());
        Assertions.assertTrue(excelStyleConfig.isLocked());
        Assertions.assertTrue(excelStyleConfig.isQuotePrefix());
        Assertions.assertEquals(excelStyleConfig.getHorizontalAlignment(), StyleHorizontalAlignment.CENTER_SELECTION);
        Assertions.assertFalse(excelStyleConfig.isWrapText());
        Assertions.assertEquals(excelStyleConfig.getVerticalAlignment(), StyleVerticalAlignment.BOTTOM);
        Assertions.assertEquals(excelStyleConfig.getRotation(), 33);
        Assertions.assertEquals(excelStyleConfig.getIndent(), 10);

        Assertions.assertEquals(excelStyleConfig.getBorderLeft(), BorderStyle.HAIR);
        Assertions.assertEquals(excelStyleConfig.getBorderRight(), BorderStyle.DASH_DOT);
        Assertions.assertEquals(excelStyleConfig.getBorderTop(), BorderStyle.DASHED);
        Assertions.assertEquals(excelStyleConfig.getBorderBottom(), BorderStyle.MEDIUM_DASH_DOT);

        Assertions.assertEquals(excelStyleConfig.getLeftBorderColor(), 11);
        Assertions.assertEquals(excelStyleConfig.getRightBorderColor(), 111);
        Assertions.assertEquals(excelStyleConfig.getTopBorderColor(), 1111);
        Assertions.assertEquals(excelStyleConfig.getBottomBorderColor(), 11111);

        Assertions.assertEquals(excelStyleConfig.getFillPattern(), FillPatternType.ALT_BARS);

        Assertions.assertEquals(excelStyleConfig.getFillBackgroundColor(), 55);
        Assertions.assertEquals(excelStyleConfig.getFillForegroundColor(), 66);

        Assertions.assertTrue(excelStyleConfig.isShrinkToFit());

    }

    @Test
    void findForClassTestStyle2() {
        ExcelAnnotationParse excelAnnotationParse = new ExcelAnnotationParse(new Configuration());
        excelAnnotationParse.setConfigCacheEnabled(false);

        ExcelClassConfig excelClassConfig = excelAnnotationParse.findForClass(Demo4.class);

        ExcelStyleConfig excelStyleConfig = excelClassConfig.getTitleStyle();

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
    void findForClassTestFont() {
        ExcelAnnotationParse excelAnnotationParse = new ExcelAnnotationParse(new Configuration());
        excelAnnotationParse.setConfigCacheEnabled(false);

        ExcelClassConfig excelClassConfig = excelAnnotationParse.findForClass(Demo5.class);

        ExcelStyleConfig excelStyleConfig = excelClassConfig.getTitleStyle();
        ExcelFontConfig excelFontConfig = excelStyleConfig.getFont();

        Assertions.assertEquals(excelFontConfig.getFontName(), "test");
        Assertions.assertEquals(excelFontConfig.getFontHeight(), 11);
        Assertions.assertEquals(excelFontConfig.getFontHeightInPoints(), 111);
        Assertions.assertTrue(excelFontConfig.isItalic());
        Assertions.assertTrue(excelFontConfig.isStrikeout());
        Assertions.assertEquals(excelFontConfig.getColor(), Font.COLOR_RED);
        Assertions.assertEquals(excelFontConfig.getTypeOffset(), FontTypeOffset.SUB);
        Assertions.assertEquals(excelFontConfig.getUnderline(), FontUnderline.DOUBLE);
        Assertions.assertEquals(excelFontConfig.getCharset(), FontCharset.ARABIC);
        Assertions.assertTrue(excelFontConfig.isBold());

    }

    @Test
    void findForClassTestSort() {
        ExcelAnnotationParse excelAnnotationParse = new ExcelAnnotationParse(new Configuration());
        excelAnnotationParse.setConfigCacheEnabled(false);

        ExcelClassConfig excelClassConfig = excelAnnotationParse.findForClass(Demo6.class);
        List<ExcelFieldConfig> excelFieldConfigList = excelClassConfig.getItemList();
        Assertions.assertEquals(excelFieldConfigList.get(0).getPropertyName(), "title");
        Assertions.assertEquals(excelFieldConfigList.get(0).getSort(), 3);
        Assertions.assertEquals(excelFieldConfigList.get(1).getPropertyName(), "name");
        Assertions.assertEquals(excelFieldConfigList.get(1).getSort(), 5);
        Assertions.assertEquals(excelFieldConfigList.get(2).getPropertyName(), "bigDecimal");
        Assertions.assertEquals(excelFieldConfigList.get(2).getSort(), 7);
        Assertions.assertEquals(excelFieldConfigList.get(3).getPropertyName(), "bigDecimal2");
        Assertions.assertEquals(excelFieldConfigList.get(3).getSort(), 7);
    }

    @Test
    void findForClassTestField() {
        ExcelAnnotationParse excelAnnotationParse = new ExcelAnnotationParse(new Configuration());
        excelAnnotationParse.setConfigCacheEnabled(false);

        ExcelClassConfig excelClassConfig = excelAnnotationParse.findForClass(Demo7.class);
        List<ExcelFieldConfig> excelFieldConfigList = excelClassConfig.getItemList();

        Assertions.assertEquals(excelFieldConfigList.size(), 3);
        ExcelFieldConfig excelFieldConfig = excelFieldConfigList.get(0);
        Assertions.assertEquals(excelFieldConfig.getTitle(), "name-title");
        Assertions.assertEquals(excelFieldConfig.getExcelType(), ExcelType.STRING);
        Assertions.assertEquals(excelFieldConfig.getCellHandler().length, 1);
        Assertions.assertEquals(excelFieldConfig.getCellHandler()[0], DateStringHandler.class);
        Assertions.assertEquals(excelFieldConfig.getColspan(), 2);
        Assertions.assertEquals(excelFieldConfig.getColspan(), 2);
        Assertions.assertEquals(excelFieldConfig.getHeadFormat(), "@@");
        Assertions.assertEquals(excelFieldConfig.getJavaFormat(), "yyyy");
        Assertions.assertEquals(excelFieldConfig.getExcelFormat(), "mm");


    }

    @Test
    void findForClassTestFieldStyle() {
        ExcelAnnotationParse excelAnnotationParse = new ExcelAnnotationParse(new Configuration());
        excelAnnotationParse.setConfigCacheEnabled(false);

        ExcelClassConfig excelClassConfig = excelAnnotationParse.findForClass(Demo8.class);
        List<ExcelFieldConfig> excelFieldConfigList = excelClassConfig.getItemList();

        Assertions.assertEquals(excelFieldConfigList.size(), 4);
        ExcelFieldConfig excelFieldConfig = excelFieldConfigList.get(0);
        ExcelStyleConfig headStyle = excelFieldConfig.getHeadStyle();
        Assertions.assertTrue(headStyle.isHidden());

        ExcelStyleConfig dataStyle = excelFieldConfig.getDataStyle();
        Assertions.assertEquals(dataStyle.getRotation(), 22);

    }

    private static class Demo1 {

        private String name;

        private String title;

        private BigDecimal bigDecimal;
    }

    @ExcelClass(rowTitleWrite = RowTitleWrite.class, rowHeadWrite = RowHeadWrite.class, rowFootWrite = RowFootWrite.class, rowTitleRead = RowTitleRead.class, rowHeadRead = RowHeadRead.class
            , rowFootRead = RowFootRead.class, titleFormat = "@@", rowStyleRead = RowStyleRead.class, titleStyle = @ExcelStyle(rotation = 22))
    private static class Demo2 {

        @ExcelField
        private String name;

        @ExcelField
        private String title;

        @ExcelField
        private BigDecimal bigDecimal;

        @ExcelField
        private BigDecimal bigDecimal2;
    }

    @ExcelClass(titleStyle = @ExcelStyle(
            hidden = true,
            locked = true,
            quotePrefix = true
            , horizontalAlignment = StyleHorizontalAlignment.CENTER_SELECTION
            , wrapText = false
            , verticalAlignment = StyleVerticalAlignment.BOTTOM
            , rotation = 33
            , indent = 10
            , borderLeft = BorderStyle.HAIR
            , borderRight = BorderStyle.DASH_DOT
            , borderTop = BorderStyle.DASHED
            , borderBottom = BorderStyle.MEDIUM_DASH_DOT
            , leftBorderColor = 11
            , rightBorderColor = 111
            , topBorderColor = 1111
            , bottomBorderColor = 11111
            , fillPattern = FillPatternType.ALT_BARS
            , fillBackgroundColor = 55
            , fillForegroundColor = 66
            , shrinkToFit = true

    ))
    private static class Demo3 {

    }

    private static class Demo4 {

    }

    @ExcelClass(titleStyle = @ExcelStyle(font = @ExcelFont(
            fontName = "test"
            , fontHeight = 11
            , fontHeightInPoints = 111
            , italic = true
            , strikeout = true
            , color = Font.COLOR_RED
            , typeOffset = FontTypeOffset.SUB,
            underline = FontUnderline.DOUBLE
            , charset = FontCharset.ARABIC
            , bold = true
    )))
    private static class Demo5 {

    }

    private static class Demo6 {

        @ExcelField(sort = 5)
        private String name;

        @ExcelField(sort = 3)
        private String title;

        @ExcelField(sort = 7)
        private BigDecimal bigDecimal;

        @ExcelField(sort = 7)
        private BigDecimal bigDecimal2;
    }

    private static class Demo7 {

        @ExcelField(sort = 5
                , title = "name-title"
                , excelType = ExcelType.STRING
                , cellHandler = DateStringHandler.class
                , colspan = 2
                , headFormat = "@@"
                , javaFormat = "yyyy"
                , excelFormat = "mm"
        )
        private String name;

        @ExcelField(sort = 3, ignore = true)
        private String title;

        @ExcelField(sort = 7)
        private BigDecimal bigDecimal;

        @ExcelField(sort = 7)
        private BigDecimal bigDecimal2;
    }

    private static class Demo8 {

        @ExcelField(headStyle = @ExcelStyle(hidden = true), sort = 1,
                dataStyle = @ExcelStyle(rotation = 22)
        )
        private String name;

        @ExcelField(sort = 3)
        private String title;

        @ExcelField(sort = 7)
        private BigDecimal bigDecimal;

        @ExcelField(sort = 7)
        private BigDecimal bigDecimal2;
    }
}
