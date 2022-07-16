package io.github.zouzhiy.excel.metadata.config;

import io.github.zouzhiy.excel.annotation.ExcelField;
import io.github.zouzhiy.excel.annotation.ExcelFont;
import io.github.zouzhiy.excel.annotation.ExcelStyle;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.enums.FontTypeOffset;
import io.github.zouzhiy.excel.enums.StyleHorizontalAlignment;
import io.github.zouzhiy.excel.enums.StyleVerticalAlignment;
import io.github.zouzhiy.excel.handler.string.StringBooleanHandler;
import io.github.zouzhiy.excel.handler.string.StringNumberHandler;
import io.github.zouzhiy.excel.handler.string.StringStringHandler;
import io.github.zouzhiy.excel.support.metadata.DemoDefault;
import org.apache.poi.common.usermodel.fonts.FontCharset;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.FontUnderline;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;


class ExcelFieldConfigTest {

    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void getExcelFieldConfig() {

        ExcelFieldConfig excelFieldConfig = ExcelFieldConfig.builder().build();

        Assertions.assertEquals(excelFieldConfig.getTitle(), "");
        Assertions.assertEquals(excelFieldConfig.getPropertyName(), "");
        Assertions.assertEquals(excelFieldConfig.getJavaType(), Object.class);
        Assertions.assertEquals(excelFieldConfig.getExcelType(), ExcelType.BLANK);
        Assertions.assertEquals(excelFieldConfig.getCellHandler().length, 0);
        Assertions.assertEquals(excelFieldConfig.getColspan(), 1);
        Assertions.assertEquals(excelFieldConfig.getHeadFormat(), "@");
        Assertions.assertEquals(excelFieldConfig.getJavaFormat(), "");
        Assertions.assertEquals(excelFieldConfig.getExcelFormat(), "");
        Assertions.assertEquals(excelFieldConfig.getSort(), 0L);
        Assertions.assertEquals(excelFieldConfig.getHeadStyle(), ExcelStyleConfig.getDefaultExcelStyleConfigHead());
        Assertions.assertEquals(excelFieldConfig.getDataStyle(), ExcelStyleConfig.getDefaultExcelStyleConfigData());
    }

    @Test
    void getDefaultExcelFieldConfig() {
        String propertyName = "propertyNameTest";
        Class<?> javaType = DemoDefault.class;

        ExcelFieldConfig excelFieldConfig = ExcelFieldConfig.getDefaultExcelFieldConfig(propertyName, javaType);

        Assertions.assertEquals(excelFieldConfig.getTitle(), propertyName);
        Assertions.assertEquals(excelFieldConfig.getPropertyName(), propertyName);
        Assertions.assertEquals(excelFieldConfig.getJavaType(), javaType);
        Assertions.assertEquals(excelFieldConfig.getExcelType(), ExcelType.BLANK);
        Assertions.assertEquals(excelFieldConfig.getCellHandler().length, 0);
        Assertions.assertEquals(excelFieldConfig.getColspan(), 1);
        Assertions.assertEquals(excelFieldConfig.getHeadFormat(), "@");
        Assertions.assertEquals(excelFieldConfig.getJavaFormat(), "");
        Assertions.assertEquals(excelFieldConfig.getExcelFormat(), "");
        Assertions.assertEquals(excelFieldConfig.getSort(), 0L);
        Assertions.assertEquals(excelFieldConfig.getHeadStyle(), ExcelStyleConfig.getDefaultExcelStyleConfigHead());
        Assertions.assertEquals(excelFieldConfig.getDataStyle(), ExcelStyleConfig.getDefaultExcelStyleConfigData());
    }


    @SuppressWarnings("unchecked")
    @RepeatedTest(10)
    void buildByExcelFieldEmptyTitle() {
        String propertyName = "propertyNameTest";
        Class<?> javaType = DemoDefault.class;

        ExcelField excelField = Mockito.mock(ExcelField.class);

        ExcelFont headExcelFont = Mockito.mock(ExcelFont.class);
        ExcelStyle headExcelStyle = Mockito.mock(ExcelStyle.class);

        Mockito.when(headExcelFont.fontName()).thenReturn("" + random.nextDouble());
        Mockito.when(headExcelFont.fontHeight()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelFont.fontHeightInPoints()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelFont.italic()).thenReturn(random.nextBoolean());
        Mockito.when(headExcelFont.strikeout()).thenReturn(random.nextBoolean());
        Mockito.when(headExcelFont.color()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelFont.typeOffset()).thenReturn(FontTypeOffset.values()[random.nextInt(FontTypeOffset.values().length)]);
        Mockito.when(headExcelFont.underline()).thenReturn(FontUnderline.values()[random.nextInt(FontUnderline.values().length)]);
        Mockito.when(headExcelFont.charset()).thenReturn(FontCharset.values()[random.nextInt(FontCharset.values().length)]);
        Mockito.when(headExcelFont.bold()).thenReturn(random.nextBoolean());

        Mockito.when(headExcelStyle.font()).thenReturn(headExcelFont);
        Mockito.when(headExcelStyle.hidden()).thenReturn(random.nextBoolean());
        Mockito.when(headExcelStyle.locked()).thenReturn(random.nextBoolean());
        Mockito.when(headExcelStyle.quotePrefix()).thenReturn(random.nextBoolean());
        Mockito.when(headExcelStyle.horizontalAlignment()).thenReturn(StyleHorizontalAlignment.values()[random.nextInt(StyleHorizontalAlignment.values().length)]);
        Mockito.when(headExcelStyle.wrapText()).thenReturn(random.nextBoolean());
        Mockito.when(headExcelStyle.verticalAlignment()).thenReturn(StyleVerticalAlignment.values()[random.nextInt(StyleVerticalAlignment.values().length)]);
        Mockito.when(headExcelStyle.rotation()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelStyle.indent()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelStyle.borderLeft()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(headExcelStyle.borderRight()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(headExcelStyle.borderTop()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(headExcelStyle.borderBottom()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(headExcelStyle.leftBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelStyle.rightBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelStyle.topBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelStyle.bottomBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelStyle.fillPattern()).thenReturn(FillPatternType.values()[random.nextInt(FillPatternType.values().length)]);
        Mockito.when(headExcelStyle.fillBackgroundColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelStyle.fillForegroundColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelStyle.shrinkToFit()).thenReturn(random.nextBoolean());


        ExcelStyle dataExcelStyle = Mockito.mock(ExcelStyle.class);

        ExcelFont dataExcelFont = Mockito.mock(ExcelFont.class);

        Mockito.when(dataExcelFont.fontName()).thenReturn("" + random.nextDouble());
        Mockito.when(dataExcelFont.fontHeight()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelFont.fontHeightInPoints()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelFont.italic()).thenReturn(random.nextBoolean());
        Mockito.when(dataExcelFont.strikeout()).thenReturn(random.nextBoolean());
        Mockito.when(dataExcelFont.color()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelFont.typeOffset()).thenReturn(FontTypeOffset.values()[random.nextInt(FontTypeOffset.values().length)]);
        Mockito.when(dataExcelFont.underline()).thenReturn(FontUnderline.values()[random.nextInt(FontUnderline.values().length)]);
        Mockito.when(dataExcelFont.charset()).thenReturn(FontCharset.values()[random.nextInt(FontCharset.values().length)]);
        Mockito.when(dataExcelFont.bold()).thenReturn(random.nextBoolean());

        Mockito.when(dataExcelStyle.font()).thenReturn(dataExcelFont);
        Mockito.when(dataExcelStyle.hidden()).thenReturn(random.nextBoolean());
        Mockito.when(dataExcelStyle.locked()).thenReturn(random.nextBoolean());
        Mockito.when(dataExcelStyle.quotePrefix()).thenReturn(random.nextBoolean());
        Mockito.when(dataExcelStyle.horizontalAlignment()).thenReturn(StyleHorizontalAlignment.values()[random.nextInt(StyleHorizontalAlignment.values().length)]);
        Mockito.when(dataExcelStyle.wrapText()).thenReturn(random.nextBoolean());
        Mockito.when(dataExcelStyle.verticalAlignment()).thenReturn(StyleVerticalAlignment.values()[random.nextInt(StyleVerticalAlignment.values().length)]);
        Mockito.when(dataExcelStyle.rotation()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelStyle.indent()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelStyle.borderLeft()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(dataExcelStyle.borderRight()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(dataExcelStyle.borderTop()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(dataExcelStyle.borderBottom()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(dataExcelStyle.leftBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelStyle.rightBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelStyle.topBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelStyle.bottomBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelStyle.fillPattern()).thenReturn(FillPatternType.values()[random.nextInt(FillPatternType.values().length)]);
        Mockito.when(dataExcelStyle.fillBackgroundColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelStyle.fillForegroundColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelStyle.shrinkToFit()).thenReturn(random.nextBoolean());


        Mockito.when(excelField.title()).thenReturn("");
        Mockito.when(excelField.width()).thenReturn(random.nextDouble());
        Mockito.when(excelField.excelType()).thenReturn(ExcelType.values()[random.nextInt(ExcelType.values().length)]);
        Mockito.when(excelField.cellHandler()).thenReturn(new Class[]{
                StringStringHandler.class,
                StringNumberHandler.class
        });
        Mockito.when(excelField.colspan()).thenReturn(random.nextInt());
        Mockito.when(excelField.headFormat()).thenReturn("" + random.nextDouble());
        Mockito.when(excelField.javaFormat()).thenReturn("" + random.nextDouble());
        Mockito.when(excelField.excelFormat()).thenReturn("" + random.nextDouble());
        Mockito.when(excelField.sort()).thenReturn(random.nextLong());
        Mockito.when(excelField.headStyle()).thenReturn(headExcelStyle);
        Mockito.when(excelField.dataStyle()).thenReturn(dataExcelStyle);

        ExcelFieldConfig excelFieldConfig = ExcelFieldConfig.buildByExcelField(excelField, propertyName, javaType);

        Assertions.assertEquals(excelFieldConfig.getTitle(), propertyName);
        Assertions.assertEquals(excelFieldConfig.getWidth(), excelField.width());
        Assertions.assertEquals(excelFieldConfig.getPropertyName(), propertyName);
        Assertions.assertEquals(excelFieldConfig.getJavaType(), javaType);
        Assertions.assertEquals(excelFieldConfig.getExcelType(), excelField.excelType());
        Assertions.assertEquals(excelFieldConfig.getCellHandler(), excelField.cellHandler());
        Assertions.assertEquals(excelFieldConfig.getColspan(), excelField.colspan());
        Assertions.assertEquals(excelFieldConfig.getHeadFormat(), excelField.headFormat());
        Assertions.assertEquals(excelFieldConfig.getJavaFormat(), excelField.javaFormat());
        Assertions.assertEquals(excelFieldConfig.getExcelFormat(), excelField.excelFormat());
        Assertions.assertEquals(excelFieldConfig.getSort(), excelField.sort());
        Assertions.assertEquals(excelFieldConfig.getHeadStyle(), ExcelStyleConfig.buildByExcelStyle(excelField.headStyle()));
        Assertions.assertEquals(excelFieldConfig.getDataStyle(), ExcelStyleConfig.buildByExcelStyle(excelField.dataStyle()));
    }

    @RepeatedTest(10)
    void buildByExcelField() {
        String propertyName = "propertyNameTest";
        Class<?> javaType = DemoDefault.class;

        ExcelField excelField = Mockito.mock(ExcelField.class);

        ExcelFont headExcelFont = Mockito.mock(ExcelFont.class);
        ExcelStyle headExcelStyle = Mockito.mock(ExcelStyle.class);

        Mockito.when(headExcelFont.fontName()).thenReturn("" + random.nextDouble());
        Mockito.when(headExcelFont.fontHeight()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelFont.fontHeightInPoints()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelFont.italic()).thenReturn(random.nextBoolean());
        Mockito.when(headExcelFont.strikeout()).thenReturn(random.nextBoolean());
        Mockito.when(headExcelFont.color()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelFont.typeOffset()).thenReturn(FontTypeOffset.values()[random.nextInt(FontTypeOffset.values().length)]);
        Mockito.when(headExcelFont.underline()).thenReturn(FontUnderline.values()[random.nextInt(FontUnderline.values().length)]);
        Mockito.when(headExcelFont.charset()).thenReturn(FontCharset.values()[random.nextInt(FontCharset.values().length)]);
        Mockito.when(headExcelFont.bold()).thenReturn(random.nextBoolean());

        Mockito.when(headExcelStyle.font()).thenReturn(headExcelFont);
        Mockito.when(headExcelStyle.hidden()).thenReturn(random.nextBoolean());
        Mockito.when(headExcelStyle.locked()).thenReturn(random.nextBoolean());
        Mockito.when(headExcelStyle.quotePrefix()).thenReturn(random.nextBoolean());
        Mockito.when(headExcelStyle.horizontalAlignment()).thenReturn(StyleHorizontalAlignment.values()[random.nextInt(StyleHorizontalAlignment.values().length)]);
        Mockito.when(headExcelStyle.wrapText()).thenReturn(random.nextBoolean());
        Mockito.when(headExcelStyle.verticalAlignment()).thenReturn(StyleVerticalAlignment.values()[random.nextInt(StyleVerticalAlignment.values().length)]);
        Mockito.when(headExcelStyle.rotation()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelStyle.indent()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelStyle.borderLeft()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(headExcelStyle.borderRight()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(headExcelStyle.borderTop()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(headExcelStyle.borderBottom()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(headExcelStyle.leftBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelStyle.rightBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelStyle.topBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelStyle.bottomBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelStyle.fillPattern()).thenReturn(FillPatternType.values()[random.nextInt(FillPatternType.values().length)]);
        Mockito.when(headExcelStyle.fillBackgroundColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelStyle.fillForegroundColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(headExcelStyle.shrinkToFit()).thenReturn(random.nextBoolean());


        ExcelStyle dataExcelStyle = Mockito.mock(ExcelStyle.class);
        ExcelFont dataExcelFont = Mockito.mock(ExcelFont.class);
        Mockito.when(dataExcelFont.fontName()).thenReturn("" + random.nextDouble());
        Mockito.when(dataExcelFont.fontHeight()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelFont.fontHeightInPoints()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelFont.italic()).thenReturn(random.nextBoolean());
        Mockito.when(dataExcelFont.strikeout()).thenReturn(random.nextBoolean());
        Mockito.when(dataExcelFont.color()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelFont.typeOffset()).thenReturn(FontTypeOffset.values()[random.nextInt(FontTypeOffset.values().length)]);
        Mockito.when(dataExcelFont.underline()).thenReturn(FontUnderline.values()[random.nextInt(FontUnderline.values().length)]);
        Mockito.when(dataExcelFont.charset()).thenReturn(FontCharset.values()[random.nextInt(FontCharset.values().length)]);
        Mockito.when(dataExcelFont.bold()).thenReturn(random.nextBoolean());

        Mockito.when(dataExcelStyle.font()).thenReturn(dataExcelFont);
        Mockito.when(dataExcelStyle.hidden()).thenReturn(random.nextBoolean());
        Mockito.when(dataExcelStyle.locked()).thenReturn(random.nextBoolean());
        Mockito.when(dataExcelStyle.quotePrefix()).thenReturn(random.nextBoolean());
        Mockito.when(dataExcelStyle.horizontalAlignment()).thenReturn(StyleHorizontalAlignment.values()[random.nextInt(StyleHorizontalAlignment.values().length)]);
        Mockito.when(dataExcelStyle.wrapText()).thenReturn(random.nextBoolean());
        Mockito.when(dataExcelStyle.verticalAlignment()).thenReturn(StyleVerticalAlignment.values()[random.nextInt(StyleVerticalAlignment.values().length)]);
        Mockito.when(dataExcelStyle.rotation()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelStyle.indent()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelStyle.borderLeft()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(dataExcelStyle.borderRight()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(dataExcelStyle.borderTop()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(dataExcelStyle.borderBottom()).thenReturn(BorderStyle.values()[random.nextInt(BorderStyle.values().length)]);
        Mockito.when(dataExcelStyle.leftBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelStyle.rightBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelStyle.topBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelStyle.bottomBorderColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelStyle.fillPattern()).thenReturn(FillPatternType.values()[random.nextInt(FillPatternType.values().length)]);
        Mockito.when(dataExcelStyle.fillBackgroundColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelStyle.fillForegroundColor()).thenReturn((short) random.nextInt(Short.MAX_VALUE));
        Mockito.when(dataExcelStyle.shrinkToFit()).thenReturn(random.nextBoolean());


        Mockito.when(excelField.title()).thenReturn("" + random.nextDouble());
        Mockito.when(excelField.width()).thenReturn(random.nextDouble());
        Mockito.when(excelField.excelType()).thenReturn(ExcelType.values()[random.nextInt(ExcelType.values().length)]);
        //noinspection unchecked
        Mockito.when(excelField.cellHandler()).thenReturn(new Class[]{
                StringStringHandler.class,
                StringNumberHandler.class,
                StringBooleanHandler.class
        });
        Mockito.when(excelField.colspan()).thenReturn(random.nextInt());
        Mockito.when(excelField.headFormat()).thenReturn("" + random.nextDouble());
        Mockito.when(excelField.javaFormat()).thenReturn("" + random.nextDouble());
        Mockito.when(excelField.excelFormat()).thenReturn("" + random.nextDouble());
        Mockito.when(excelField.sort()).thenReturn(random.nextLong());
        Mockito.when(excelField.headStyle()).thenReturn(headExcelStyle);
        Mockito.when(excelField.dataStyle()).thenReturn(dataExcelStyle);

        ExcelFieldConfig excelFieldConfig = ExcelFieldConfig.buildByExcelField(excelField, propertyName, javaType);

        Assertions.assertEquals(excelFieldConfig.getTitle(), excelField.title());
        Assertions.assertEquals(excelFieldConfig.getWidth(), excelField.width());
        Assertions.assertEquals(excelFieldConfig.getPropertyName(), propertyName);
        Assertions.assertEquals(excelFieldConfig.getJavaType(), javaType);
        Assertions.assertEquals(excelFieldConfig.getExcelType(), excelField.excelType());
        Assertions.assertEquals(excelFieldConfig.getCellHandler(), excelField.cellHandler());
        Assertions.assertEquals(excelFieldConfig.getColspan(), excelField.colspan());
        Assertions.assertEquals(excelFieldConfig.getHeadFormat(), excelField.headFormat());
        Assertions.assertEquals(excelFieldConfig.getJavaFormat(), excelField.javaFormat());
        Assertions.assertEquals(excelFieldConfig.getExcelFormat(), excelField.excelFormat());
        Assertions.assertEquals(excelFieldConfig.getSort(), excelField.sort());
        Assertions.assertEquals(excelFieldConfig.getHeadStyle(), ExcelStyleConfig.buildByExcelStyle(excelField.headStyle()));
        Assertions.assertEquals(excelFieldConfig.getDataStyle(), ExcelStyleConfig.buildByExcelStyle(excelField.dataStyle()));
    }


}
