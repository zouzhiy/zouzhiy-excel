/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.zouzhiy.excel.handler;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.handler.bigdecimal.BigDecimalBooleanHandler;
import io.github.zouzhiy.excel.handler.bigdecimal.BigDecimalNumberHandler;
import io.github.zouzhiy.excel.handler.bigdecimal.BigDecimalStringHandler;
import io.github.zouzhiy.excel.handler.booleans.BooleanStringHandler;
import io.github.zouzhiy.excel.handler.bytes.ByteArrayBoxStringHandler;
import io.github.zouzhiy.excel.handler.bytes.ByteArrayStringHandler;
import io.github.zouzhiy.excel.handler.bytes.ByteStringHandler;
import io.github.zouzhiy.excel.handler.doubles.DoubleStringHandler;
import io.github.zouzhiy.excel.handler.floats.FloatStringHandler;
import io.github.zouzhiy.excel.handler.image.ImageByteCellHandler;
import io.github.zouzhiy.excel.handler.ints.IntegerStringHandler;
import io.github.zouzhiy.excel.handler.localdatetime.LocalDateTimeDateHandler;
import io.github.zouzhiy.excel.handler.localdatetime.LocalDateTimeNumberHandler;
import io.github.zouzhiy.excel.handler.localdatetime.LocalDateTimeStringHandler;
import io.github.zouzhiy.excel.handler.longs.LongStringHandler;
import io.github.zouzhiy.excel.handler.shorts.ShortStringHandler;
import io.github.zouzhiy.excel.handler.string.StringBooleanHandler;
import io.github.zouzhiy.excel.handler.string.StringDateHandler;
import io.github.zouzhiy.excel.handler.string.StringNumberHandler;
import io.github.zouzhiy.excel.handler.string.StringStringHandler;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/3
 */
class CellHandlerRegistryTest {


//    @Test
//    void testGetCellHandler0() {
//        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(SingleItemCellHandler.class, SingleItem.class, ExcelType.NONE));
//
//        cellHandlerRegistry.register(new SingleItemCellHandler());
//
//        Assertions.assertEquals(SingleItemCellHandler.class, cellHandlerRegistry.getCellHandler(SingleItemCellHandler.class, SingleItem.class, ExcelType.NONE).getClass());
//        Assertions.assertEquals(SingleItemCellHandler.class, cellHandlerRegistry.getCellHandler(SingleItemCellHandler.class, SingleItem.class, ExcelType.BLANK).getClass());
//        Assertions.assertEquals(SingleItemCellHandler.class, cellHandlerRegistry.getCellHandler(Collections.singletonList(SingleItemCellHandler.class), SingleItem.class, ExcelType.BLANK).getClass());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(Collections.emptyList(), SingleItem.class, ExcelType.NONE));
//    }
//
//    @Test
//    void testGetCellHandler00() {
//        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());
//        cellHandlerRegistry.register(new SingleItemCellHandler());
//
//        Assertions.assertEquals(BooleanStringHandler.class, cellHandlerRegistry.getCellHandler(boolean.class, ExcelType.STRING).getClass());
//        Assertions.assertEquals(ByteStringHandler.class, cellHandlerRegistry.getCellHandler(byte.class, ExcelType.STRING).getClass());
//        Assertions.assertEquals(ShortStringHandler.class, cellHandlerRegistry.getCellHandler(short.class, ExcelType.STRING).getClass());
//        Assertions.assertEquals(IntegerStringHandler.class, cellHandlerRegistry.getCellHandler(int.class, ExcelType.STRING).getClass());
//        Assertions.assertEquals(LongStringHandler.class, cellHandlerRegistry.getCellHandler(long.class, ExcelType.STRING).getClass());
//        Assertions.assertEquals(FloatStringHandler.class, cellHandlerRegistry.getCellHandler(float.class, ExcelType.STRING).getClass());
//        Assertions.assertEquals(DoubleStringHandler.class, cellHandlerRegistry.getCellHandler(double.class, ExcelType.STRING).getClass());
//
//    }
//
//
//    @Test
//    void testGetCellHandler1() {
//        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(SingleItemCellHandler.class, ExcelType.NONE));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(SingleItemCellHandler.class, ExcelType.BLANK));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(SingleItemCellHandler.class, ExcelType.NUMERIC));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(SingleItemCellHandler.class, ExcelType.STRING));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(SingleItemCellHandler.class, ExcelType.BOOLEAN));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(SingleItemCellHandler.class, ExcelType.DATE));
//
//        cellHandlerRegistry.register(new SingleItemCellHandler());
//        Assertions.assertEquals(SingleItemCellHandler.class, cellHandlerRegistry.getCellHandler(Collections.singletonList(SingleItemCellHandler.class), ExcelType.NONE).getClass());
//        Assertions.assertEquals(SingleItemCellHandler.class, cellHandlerRegistry.getCellHandler(Collections.singletonList(SingleItemCellHandler.class), ExcelType.BLANK).getClass());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(Collections.singletonList(SingleItemCellHandler.class), ExcelType.NUMERIC));
//        Assertions.assertEquals(SingleItemCellHandler.class, cellHandlerRegistry.getCellHandler(Collections.singletonList(SingleItemCellHandler.class), ExcelType.STRING).getClass());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(Collections.singletonList(SingleItemCellHandler.class), ExcelType.BOOLEAN));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(Collections.singletonList(SingleItemCellHandler.class), ExcelType.DATE));
//    }
//
//    @Test
//    void testGetCellHandler11() {
//        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItemNumberCellHandler.class, ExcelType.NONE));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItemNumberCellHandler.class, ExcelType.BLANK));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItemNumberCellHandler.class, ExcelType.NUMERIC));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItemNumberCellHandler.class, ExcelType.STRING));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItemNumberCellHandler.class, ExcelType.BOOLEAN));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItemNumberCellHandler.class, ExcelType.DATE));
//
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItemStringCellHandler.class, ExcelType.NONE));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItemStringCellHandler.class, ExcelType.BLANK));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItemStringCellHandler.class, ExcelType.NUMERIC));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItemStringCellHandler.class, ExcelType.STRING));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItemStringCellHandler.class, ExcelType.BOOLEAN));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItemStringCellHandler.class, ExcelType.DATE));
//
//        List<Class<? extends CellHandler<MulItem>>> cellHandlerList = new ArrayList<>();
//        cellHandlerList.add(MulItemNumberCellHandler.class);
//        cellHandlerList.add(MulItemStringCellHandler.class);
//
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.NONE));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.BLANK));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.NUMERIC));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.STRING));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.BOOLEAN));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.BLANK));
//
//
//        cellHandlerRegistry.register(new MulItemNumberCellHandler());
//
//        Assertions.assertEquals(MulItemNumberCellHandler.class, cellHandlerRegistry.getCellHandler(Collections.singletonList(MulItemNumberCellHandler.class), ExcelType.NONE).getClass());
//        Assertions.assertEquals(MulItemNumberCellHandler.class, cellHandlerRegistry.getCellHandler(Collections.singletonList(MulItemNumberCellHandler.class), ExcelType.BLANK).getClass());
//        Assertions.assertEquals(MulItemNumberCellHandler.class, cellHandlerRegistry.getCellHandler(Collections.singletonList(MulItemNumberCellHandler.class), ExcelType.NUMERIC).getClass());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(Collections.singletonList(MulItemNumberCellHandler.class), ExcelType.STRING));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(Collections.singletonList(MulItemNumberCellHandler.class), ExcelType.BOOLEAN));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(Collections.singletonList(MulItemNumberCellHandler.class), ExcelType.DATE));
//
//        Assertions.assertEquals(MulItemNumberCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.NONE).getClass());
//        Assertions.assertEquals(MulItemNumberCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.BLANK).getClass());
//        Assertions.assertEquals(MulItemNumberCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.NUMERIC).getClass());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.STRING));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.BOOLEAN));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.DATE));
//
//        cellHandlerRegistry.register(new MulItemStringCellHandler());
//
//        Assertions.assertEquals(MulItemStringCellHandler.class, cellHandlerRegistry.getCellHandler(Collections.singletonList(MulItemStringCellHandler.class), ExcelType.NONE).getClass());
//        Assertions.assertEquals(MulItemStringCellHandler.class, cellHandlerRegistry.getCellHandler(Collections.singletonList(MulItemStringCellHandler.class), ExcelType.BLANK).getClass());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(Collections.singletonList(MulItemStringCellHandler.class), ExcelType.NUMERIC));
//        Assertions.assertEquals(MulItemStringCellHandler.class, cellHandlerRegistry.getCellHandler(Collections.singletonList(MulItemStringCellHandler.class), ExcelType.STRING).getClass());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(Collections.singletonList(MulItemStringCellHandler.class), ExcelType.BOOLEAN));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(Collections.singletonList(MulItemStringCellHandler.class), ExcelType.DATE));
//
//
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.NONE));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.BLANK));
//        Assertions.assertEquals(MulItemNumberCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.NUMERIC).getClass());
//        Assertions.assertEquals(MulItemStringCellHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.STRING).getClass());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.BOOLEAN));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerList, ExcelType.DATE));
//    }
//
//    @Test
//    void testGetCellHandler111() {
//        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());
//
//        List<Class<? extends CellHandler<String>>> cellHandlerClazzList = new ArrayList<>();
//        cellHandlerClazzList.add(StringDateHandler.class);
//        cellHandlerClazzList.add(StringNumberHandler.class);
//        cellHandlerClazzList.add(StringStringHandler.class);
//        cellHandlerClazzList.add(StringBooleanHandler.class);
//
//
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(cellHandlerClazzList, ExcelType.NONE));
//        Assertions.assertEquals(StringStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClazzList, ExcelType.BLANK).getClass());
//        Assertions.assertEquals(StringNumberHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClazzList, ExcelType.NUMERIC).getClass());
//        Assertions.assertEquals(StringStringHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClazzList, ExcelType.STRING).getClass());
//        Assertions.assertEquals(StringBooleanHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClazzList, ExcelType.BOOLEAN).getClass());
//        Assertions.assertEquals(StringDateHandler.class, cellHandlerRegistry.getCellHandler(cellHandlerClazzList, ExcelType.DATE).getClass());
//
//    }
//
//    @Test
//    void testGetCellHandler2() {
//        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(SingleItemCellHandler.class));
//        cellHandlerRegistry.register(new SingleItemCellHandler());
//        Assertions.assertEquals(SingleItemCellHandler.class, cellHandlerRegistry.getCellHandler(SingleItemCellHandler.class).getClass());
//    }
//
//    @Test
//    void testGetCellHandler3() {
//        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());
//        ByteArrayBoxStringHandler byteArrayBoxStringHandler = new ByteArrayBoxStringHandler() {
//            @Override
//            public ExcelType getExcelType() {
//                return ExcelType.STRING;
//            }
//        };
//        cellHandlerRegistry.register(byteArrayBoxStringHandler);
//
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(BigDecimal.class, ExcelType.NONE));
//        Assertions.assertEquals(BigDecimalNumberHandler.class, cellHandlerRegistry.getCellHandler(BigDecimal.class, ExcelType.BLANK).getClass());
//        Assertions.assertEquals(BigDecimalNumberHandler.class, cellHandlerRegistry.getCellHandler(BigDecimal.class, ExcelType.NUMERIC).getClass());
//        Assertions.assertEquals(BigDecimalStringHandler.class, cellHandlerRegistry.getCellHandler(BigDecimal.class, ExcelType.STRING).getClass());
//        Assertions.assertEquals(BigDecimalBooleanHandler.class, cellHandlerRegistry.getCellHandler(BigDecimal.class, ExcelType.BOOLEAN).getClass());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(BigDecimal.class, ExcelType.DATE));
//
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(LocalDateTime.class, ExcelType.NONE));
//        Assertions.assertEquals(LocalDateTimeDateHandler.class, cellHandlerRegistry.getCellHandler(LocalDateTime.class, ExcelType.BLANK).getClass());
//        Assertions.assertEquals(LocalDateTimeNumberHandler.class, cellHandlerRegistry.getCellHandler(LocalDateTime.class, ExcelType.NUMERIC).getClass());
//        Assertions.assertEquals(LocalDateTimeStringHandler.class, cellHandlerRegistry.getCellHandler(LocalDateTime.class, ExcelType.STRING).getClass());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(LocalDateTime.class, ExcelType.BOOLEAN));
//        Assertions.assertEquals(LocalDateTimeDateHandler.class, cellHandlerRegistry.getCellHandler(LocalDateTime.class, ExcelType.DATE).getClass());
//
//
//        Assertions.assertEquals(byteArrayBoxStringHandler.getClass(), cellHandlerRegistry.getCellHandler(Byte[].class, ExcelType.NONE).getClass());
//        Assertions.assertEquals(byteArrayBoxStringHandler.getClass(), cellHandlerRegistry.getCellHandler(Byte[].class, ExcelType.BLANK).getClass());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(Byte[].class, ExcelType.NUMERIC));
//        Assertions.assertEquals(byteArrayBoxStringHandler.getClass(), cellHandlerRegistry.getCellHandler(Byte[].class, ExcelType.STRING).getClass());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(Byte[].class, ExcelType.BOOLEAN));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(Byte[].class, ExcelType.DATE));
//
//        Assertions.assertEquals(ImageByteCellHandler.class, cellHandlerRegistry.getCellHandler(byte[].class, ExcelType.NONE).getClass());
//        Assertions.assertEquals(ByteArrayStringHandler.class, cellHandlerRegistry.getCellHandler(byte[].class, ExcelType.BLANK).getClass());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(byte[].class, ExcelType.NUMERIC));
//        Assertions.assertEquals(ByteArrayStringHandler.class, cellHandlerRegistry.getCellHandler(byte[].class, ExcelType.STRING).getClass());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(byte[].class, ExcelType.BOOLEAN));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(byte[].class, ExcelType.DATE));
//    }
//
//    @Test
//    void testGetCellHandler4() {
//        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());
//        cellHandlerRegistry.register(new SingleItemCellHandler());
//
//        Assertions.assertEquals(SingleItemCellHandler.class, cellHandlerRegistry.getCellHandler(SingleItem.class, ExcelType.NONE).getClass());
//        Assertions.assertEquals(SingleItemCellHandler.class, cellHandlerRegistry.getCellHandler(SingleItem.class, ExcelType.BLANK).getClass());
//
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(SingleItem.class, ExcelType.NUMERIC));
//        Assertions.assertEquals(SingleItemCellHandler.class, cellHandlerRegistry.getCellHandler(SingleItem.class, ExcelType.STRING).getClass());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(SingleItem.class, ExcelType.BOOLEAN));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(SingleItem.class, ExcelType.DATE));
//    }
//
//    @Test
//    void testGetCellHandler5() {
//        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(new Configuration());
//
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItem.class, ExcelType.NONE));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItem.class, ExcelType.BLANK));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItem.class, ExcelType.NUMERIC));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItem.class, ExcelType.STRING));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItem.class, ExcelType.BOOLEAN));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItem.class, ExcelType.DATE));
//
//        cellHandlerRegistry.register(new MulItemStringCellHandler());
//        cellHandlerRegistry.register(new MulItemNumberCellHandler());
//
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItem.class, ExcelType.NONE));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItem.class, ExcelType.BLANK));
//
//        Assertions.assertEquals(MulItemNumberCellHandler.class, cellHandlerRegistry.getCellHandler(MulItem.class, ExcelType.NUMERIC).getClass());
//        Assertions.assertEquals(MulItemStringCellHandler.class, cellHandlerRegistry.getCellHandler(MulItem.class, ExcelType.STRING).getClass());
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItem.class, ExcelType.BOOLEAN));
//        Assertions.assertThrows(ExcelException.class, () -> cellHandlerRegistry.getCellHandler(MulItem.class, ExcelType.DATE));
//    }
//
//    @Test
//    void getConfiguration() {
//        Configuration configuration = new Configuration();
//        CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(configuration);
//
//        Assertions.assertEquals(configuration, cellHandlerRegistry.getConfiguration());
//    }
//
//    @Data
//    public static class SingleItem {
//        private String name;
//    }
//
//    public static class SingleItemCellHandler extends AbstractCellHandler<SingleItem> {
//        @Override
//        protected SingleItem getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
//            return null;
//        }
//
//        @Override
//        protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, SingleItem value) {
//
//        }
//
//        @Override
//        public ExcelType getExcelType() {
//            return ExcelType.STRING;
//        }
//    }
//
//    @Data
//    public static class MulItem {
//        private String name;
//    }
//
//    public static class MulItemStringCellHandler extends AbstractCellHandler<MulItem> {
//        @Override
//        protected MulItem getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
//            return null;
//        }
//
//        @Override
//        protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, MulItem value) {
//
//        }
//
//        @Override
//        public ExcelType getExcelType() {
//            return ExcelType.STRING;
//        }
//    }
//
//    public static class MulItemNumberCellHandler extends AbstractCellHandler<MulItem> {
//        @Override
//        protected MulItem getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
//            return null;
//        }
//
//        @Override
//        protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, MulItem value) {
//
//        }
//
//        @Override
//        public ExcelType getExcelType() {
//            return ExcelType.NUMERIC;
//        }
//    }
}