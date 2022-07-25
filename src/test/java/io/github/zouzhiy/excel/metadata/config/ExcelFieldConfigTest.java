package io.github.zouzhiy.excel.metadata.config;

import io.github.zouzhiy.excel.annotation.ExcelField;
import io.github.zouzhiy.excel.annotation.ExcelFont;
import io.github.zouzhiy.excel.annotation.ExcelStyle;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.string.StringBooleanHandler;
import io.github.zouzhiy.excel.handler.string.StringNumberHandler;
import io.github.zouzhiy.excel.handler.string.StringStringHandler;
import io.github.zouzhiy.excel.support.metadata.DemoDefault;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;


class ExcelFieldConfigTest {

    private final Random random = new Random(System.currentTimeMillis());

    static void mockExcelField(ExcelField excelField, Random random) {
        ExcelFont headExcelFont = Mockito.mock(ExcelFont.class);
        ExcelStyle headExcelStyle = Mockito.mock(ExcelStyle.class);
        ExcelFontConfigTest.mockExcelFont(headExcelFont, random);
        ExcelStyleConfigTest.mockExcelStyle(headExcelStyle, random);
        Mockito.when(headExcelStyle.font()).thenReturn(headExcelFont);

        ExcelFont dataExcelFont = Mockito.mock(ExcelFont.class);
        ExcelStyle dataExcelStyle = Mockito.mock(ExcelStyle.class);
        ExcelFontConfigTest.mockExcelFont(dataExcelFont, random);
        ExcelStyleConfigTest.mockExcelStyle(dataExcelStyle, random);
        Mockito.when(dataExcelStyle.font()).thenReturn(dataExcelFont);

        Mockito.when(excelField.width()).thenReturn(random.nextDouble());
        Mockito.when(excelField.excelType()).thenReturn(ExcelType.values()[random.nextInt(ExcelType.values().length)]);
        Mockito.when(excelField.colspan()).thenReturn(random.nextInt());
        Mockito.when(excelField.headFormat()).thenReturn("" + random.nextDouble());
        Mockito.when(excelField.javaFormat()).thenReturn("" + random.nextDouble());
        Mockito.when(excelField.excelFormat()).thenReturn("" + random.nextDouble());
        Mockito.when(excelField.sort()).thenReturn(random.nextLong());
        Mockito.when(excelField.headStyle()).thenReturn(headExcelStyle);
        Mockito.when(excelField.dataStyle()).thenReturn(dataExcelStyle);
    }

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
        mockExcelField(excelField, random);

        Mockito.when(excelField.title()).thenReturn("");
        Mockito.when(excelField.cellHandler()).thenReturn(new Class[]{
                StringStringHandler.class,
                StringNumberHandler.class
        });

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
        mockExcelField(excelField, random);


        Mockito.when(excelField.title()).thenReturn("" + random.nextDouble());
        //noinspection unchecked
        Mockito.when(excelField.cellHandler()).thenReturn(new Class[]{
                StringStringHandler.class,
                StringNumberHandler.class,
                StringBooleanHandler.class
        });


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
