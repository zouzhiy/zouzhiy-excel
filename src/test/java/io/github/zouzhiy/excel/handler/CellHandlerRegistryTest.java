package io.github.zouzhiy.excel.handler;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.handler.booleans.BooleanStringHandler;
import io.github.zouzhiy.excel.handler.bytes.ByteArrayBoxStringHandler;
import io.github.zouzhiy.excel.handler.bytes.ByteArrayStringHandler;
import io.github.zouzhiy.excel.handler.bytes.ByteStringHandler;
import io.github.zouzhiy.excel.handler.doubles.DoubleStringHandler;
import io.github.zouzhiy.excel.handler.floats.FloatStringHandler;
import io.github.zouzhiy.excel.handler.head.HeadStringHandler;
import io.github.zouzhiy.excel.handler.image.ImageByteCellHandler;
import io.github.zouzhiy.excel.handler.image.ImageUrlCellHandler;
import io.github.zouzhiy.excel.handler.ints.IntegerStringHandler;
import io.github.zouzhiy.excel.handler.longs.LongStringHandler;
import io.github.zouzhiy.excel.handler.shorts.ShortStringHandler;
import io.github.zouzhiy.excel.handler.string.StringBooleanHandler;
import io.github.zouzhiy.excel.handler.string.StringDateHandler;
import io.github.zouzhiy.excel.handler.string.StringNumberHandler;
import io.github.zouzhiy.excel.handler.string.StringStringHandler;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import lombok.Builder;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CellHandlerRegistryTest {

    @Test
    void getConfiguration() {
        Configuration configuration = new Configuration();
        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(configuration);
        assertEquals(configuration, cellHandlerRegistry.getConfiguration());
    }

    @Test
    void classMapping() {
        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());

        List<CellHandler<?>> cellHandlerList = cellHandlerRegistry.getAllHandler();
        for (CellHandler<?> cellHandler : cellHandlerList) {
            //noinspection unchecked
            assertEquals(cellHandler, cellHandlerRegistry.getCellHandler((Class<? extends CellHandler<?>>) cellHandler.getClass()));
        }
    }

    @Test
    void unMapping1() {
        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());

        assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(StandardMappingItem.class, ExcelType.STRING));
    }

    @Test
    void unMapping2() {
        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());
        cellHandlerRegistry.register(new StandardMappingStringHandler());

        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(StandardMappingItem.class, ExcelType.NONE).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(StandardMappingItem.class, ExcelType.BLANK).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(StandardMappingItem.class, ExcelType.STRING).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(StandardMappingItem.class, ExcelType.NUMERIC).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(StandardMappingItem.class, ExcelType.BOOLEAN).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(StandardMappingItem.class, ExcelType.DATE).getClass());

        List<Class<? extends CellHandler<?>>> cellHandlerClassList = new ArrayList<>();
        cellHandlerClassList.add(StandardMappingStringHandler.class);
        cellHandlerClassList.add(StandardMappingNumberHandler.class);

        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.NONE).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.BLANK).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.STRING).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.NUMERIC).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.BOOLEAN).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.DATE).getClass());

    }

    @Test
    void stringHandler() {
        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());

        assertEquals(ImageUrlCellHandler.class, cellHandlerRegistry.getCellHandler(String.class, ExcelType.NONE).getClass());
        assertEquals(StringStringHandler.class, cellHandlerRegistry.getCellHandler(String.class, ExcelType.BLANK).getClass());
        assertEquals(StringStringHandler.class, cellHandlerRegistry.getCellHandler(String.class, ExcelType.STRING).getClass());
        assertEquals(StringNumberHandler.class, cellHandlerRegistry.getCellHandler(String.class, ExcelType.NUMERIC).getClass());
        assertEquals(StringBooleanHandler.class, cellHandlerRegistry.getCellHandler(String.class, ExcelType.BOOLEAN).getClass());
        assertEquals(StringDateHandler.class, cellHandlerRegistry.getCellHandler(String.class, ExcelType.DATE).getClass());
    }

    @Test
    void stringListHandler() {
        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());

        List<Class<? extends CellHandler<?>>> cellHandlerClassList = new ArrayList<>();
        cellHandlerClassList.add(StringStringHandler.class);
        cellHandlerClassList.add(StringNumberHandler.class);
        cellHandlerClassList.add(StringBooleanHandler.class);
        cellHandlerClassList.add(StringDateHandler.class);
        cellHandlerClassList.add(HeadStringHandler.class);

        assertEquals(StringStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.NONE).getClass());
        assertEquals(StringStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.BLANK).getClass());
        assertEquals(StringStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.STRING).getClass());
        assertEquals(StringNumberHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.NUMERIC).getClass());
        assertEquals(StringBooleanHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.BOOLEAN).getClass());
        assertEquals(StringDateHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.DATE).getClass());
    }

    @Test
    void stringListEmptyHandler() {
        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());

        List<Class<? extends CellHandler<?>>> cellHandlerClassList = new ArrayList<>();

        assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.NONE));
        assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.BLANK));
        assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.STRING));
        assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.NUMERIC));
        assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.BOOLEAN));
        assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.DATE));

    }

    @Test
    void stringListEmptyClassHandler() {
        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());

        List<Class<? extends CellHandler<?>>> cellHandlerClassList = new ArrayList<>();

        assertEquals(ImageUrlCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.NONE).getClass());
        assertEquals(StringStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.BLANK).getClass());
        assertEquals(StringStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.STRING).getClass());
        assertEquals(StringNumberHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.NUMERIC).getClass());
        assertEquals(StringBooleanHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.BOOLEAN).getClass());
        assertEquals(StringDateHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.DATE).getClass());
    }

    @Test
    void stringArrayHandler() {
        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());

        //noinspection unchecked
        Class<? extends CellHandler<?>>[] cellHandlerClassList = new Class[]{
                StringStringHandler.class
                , StringNumberHandler.class
                , StringBooleanHandler.class
                , StringDateHandler.class
                , HeadStringHandler.class
        };


        assertEquals(StringStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.NONE).getClass());
        assertEquals(StringStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.BLANK).getClass());
        assertEquals(StringStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.STRING).getClass());
        assertEquals(StringNumberHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.NUMERIC).getClass());
        assertEquals(StringBooleanHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.BOOLEAN).getClass());
        assertEquals(StringDateHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.DATE).getClass());
    }

    @Test
    void stringEmptyArrayHandler() {
        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());

        //noinspection unchecked
        Class<? extends CellHandler<?>>[] cellHandlerClassList = new Class[0];

        assertEquals(ImageUrlCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.NONE).getClass());
        assertEquals(StringStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.BLANK).getClass());
        assertEquals(StringStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.STRING).getClass());
        assertEquals(StringNumberHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.NUMERIC).getClass());
        assertEquals(StringBooleanHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.BOOLEAN).getClass());
        assertEquals(StringDateHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, String.class, ExcelType.DATE).getClass());
    }

    @Test
    void imageHandler() {
        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());

        List<Class<? extends CellHandler<?>>> cellHandlerClassList = new ArrayList<>();
        cellHandlerClassList.add(ImageUrlCellHandler.class);

        assertEquals(ImageUrlCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.NONE).getClass());
        assertEquals(ImageUrlCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.BLANK).getClass());
        assertEquals(ImageUrlCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.STRING).getClass());
        assertEquals(ImageUrlCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.NUMERIC).getClass());
        assertEquals(ImageUrlCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.BOOLEAN).getClass());
        assertEquals(ImageUrlCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.DATE).getClass());
    }

    @Test
    void standardMapping() {
        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());
        cellHandlerRegistry.register(new StandardMappingStringHandler());
        cellHandlerRegistry.register(new StandardMappingNumberHandler());

        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(StandardMappingItem.class, ExcelType.NONE).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(StandardMappingItem.class, ExcelType.BLANK).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(StandardMappingItem.class, ExcelType.STRING).getClass());
        assertEquals(StandardMappingNumberHandler.class, cellHandlerRegistry.getCellHandler(StandardMappingItem.class, ExcelType.NUMERIC).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(StandardMappingItem.class, ExcelType.BOOLEAN).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(StandardMappingItem.class, ExcelType.DATE).getClass());

        List<Class<? extends CellHandler<?>>> cellHandlerClassList = new ArrayList<>();
        cellHandlerClassList.add(StandardMappingStringHandler.class);
        cellHandlerClassList.add(StandardMappingNumberHandler.class);

        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.NONE).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.BLANK).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.STRING).getClass());
        assertEquals(StandardMappingNumberHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.NUMERIC).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.BOOLEAN).getClass());
        assertEquals(StandardMappingStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.DATE).getClass());
    }

    @Test
    void mulItem() {
        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());
        cellHandlerRegistry.register(new MulItemStringCellHandler());
        cellHandlerRegistry.register(new MulItemNumberCellHandler());

        List<Class<? extends CellHandler<?>>> cellHandlerClassList = new ArrayList<>();
        cellHandlerClassList.add(MulItemStringCellHandler.class);
        cellHandlerClassList.add(MulItemNumberCellHandler.class);

        assertEquals(MulItemStringCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.NONE).getClass());
        assertEquals(MulItemStringCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.BLANK).getClass());
        assertEquals(MulItemStringCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.STRING).getClass());
        assertEquals(MulItemNumberCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.NUMERIC).getClass());
        assertEquals(MulItemStringCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.BOOLEAN).getClass());
        assertEquals(MulItemStringCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClassList, ExcelType.DATE).getClass());

    }

    @Test
    void unBoxByteMapping() {
        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());
        assertEquals(ImageByteCellHandler.class, cellHandlerRegistry.getCellHandler(byte[].class, ExcelType.NONE).getClass());
        assertEquals(ByteArrayStringHandler.class, cellHandlerRegistry.getCellHandler(byte[].class, ExcelType.BLANK).getClass());
        assertEquals(ByteArrayStringHandler.class, cellHandlerRegistry.getCellHandler(byte[].class, ExcelType.STRING).getClass());
        assertEquals(ByteArrayStringHandler.class, cellHandlerRegistry.getCellHandler(byte[].class, ExcelType.NUMERIC).getClass());
        assertEquals(ByteArrayStringHandler.class, cellHandlerRegistry.getCellHandler(byte[].class, ExcelType.BOOLEAN).getClass());
        assertEquals(ByteArrayStringHandler.class, cellHandlerRegistry.getCellHandler(byte[].class, ExcelType.DATE).getClass());
    }

    @Test
    void boxByteMapping() {
        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());
        assertEquals(ByteArrayBoxStringHandler.class, cellHandlerRegistry.getCellHandler(Byte[].class, ExcelType.NONE).getClass());
        assertEquals(ByteArrayBoxStringHandler.class, cellHandlerRegistry.getCellHandler(Byte[].class, ExcelType.BLANK).getClass());
        assertEquals(ByteArrayBoxStringHandler.class, cellHandlerRegistry.getCellHandler(Byte[].class, ExcelType.STRING).getClass());
        assertEquals(ByteArrayBoxStringHandler.class, cellHandlerRegistry.getCellHandler(Byte[].class, ExcelType.NUMERIC).getClass());
        assertEquals(ByteArrayBoxStringHandler.class, cellHandlerRegistry.getCellHandler(Byte[].class, ExcelType.BOOLEAN).getClass());
        assertEquals(ByteArrayBoxStringHandler.class, cellHandlerRegistry.getCellHandler(Byte[].class, ExcelType.DATE).getClass());
    }

    @Test
    void testUnBox() {
        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());
        cellHandlerRegistry.register(new SingleItemCellHandler());

        assertEquals(BooleanStringHandler.class, cellHandlerRegistry.getCellHandler(boolean.class, ExcelType.STRING).getClass());
        assertEquals(ByteStringHandler.class, cellHandlerRegistry.getCellHandler(byte.class, ExcelType.STRING).getClass());
        assertEquals(ShortStringHandler.class, cellHandlerRegistry.getCellHandler(short.class, ExcelType.STRING).getClass());
        assertEquals(IntegerStringHandler.class, cellHandlerRegistry.getCellHandler(int.class, ExcelType.STRING).getClass());
        assertEquals(LongStringHandler.class, cellHandlerRegistry.getCellHandler(long.class, ExcelType.STRING).getClass());
        assertEquals(FloatStringHandler.class, cellHandlerRegistry.getCellHandler(float.class, ExcelType.STRING).getClass());
        assertEquals(DoubleStringHandler.class, cellHandlerRegistry.getCellHandler(double.class, ExcelType.STRING).getClass());

    }

    @Data
    @Builder
    public static class StandardMappingItem {

        private String stringValue;

        private BigDecimal numberValue;


    }

    @ExcelStandardMapping
    public static class StandardMappingStringHandler extends AbstractCellHandler<StandardMappingItem> {

        @Override
        protected StandardMappingItem getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
            return StandardMappingItem.builder()
                    .stringValue(firstCellResult.getStringValue())
                    .build();
        }

        @Override
        protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, StandardMappingItem value) {
            cell.setCellValue(value.getStringValue());
        }

        @Override
        public ExcelType getExcelType() {
            return ExcelType.STRING;
        }
    }

    public static class StandardMappingNumberHandler extends AbstractCellHandler<StandardMappingItem> {

        @Override
        protected StandardMappingItem getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
            return StandardMappingItem.builder()
                    .numberValue(firstCellResult.getNumberValue())
                    .build();
        }

        @Override
        protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, StandardMappingItem value) {
            cell.setCellValue(value.toString());
        }

        @Override
        public ExcelType getExcelType() {
            return ExcelType.NUMERIC;
        }
    }

    @Data
    public static class SingleItem {
        private String name;
    }

    public static class SingleItemCellHandler extends AbstractCellHandler<SingleItem> {
        @Override
        protected SingleItem getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
            return null;
        }

        @Override
        protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, SingleItem value) {

        }

        @Override
        public ExcelType getExcelType() {
            return ExcelType.STRING;
        }
    }

    @Data
    public static class MulItem {
        private String name;
    }

    public static class MulItemStringCellHandler extends AbstractCellHandler<MulItem> {
        @Override
        protected MulItem getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
            return null;
        }

        @Override
        protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, MulItem value) {

        }

        @Override
        public ExcelType getExcelType() {
            return ExcelType.STRING;
        }
    }

    public static class MulItemNumberCellHandler extends AbstractCellHandler<MulItem> {
        @Override
        protected MulItem getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
            return null;
        }

        @Override
        protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, MulItem value) {

        }

        @Override
        public ExcelType getExcelType() {
            return ExcelType.NUMERIC;
        }
    }
}