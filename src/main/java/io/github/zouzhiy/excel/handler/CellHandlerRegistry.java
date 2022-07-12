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

import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.handler.bigdecimal.BigDecimalBooleanHandler;
import io.github.zouzhiy.excel.handler.bigdecimal.BigDecimalNumberHandler;
import io.github.zouzhiy.excel.handler.bigdecimal.BigDecimalStringHandler;
import io.github.zouzhiy.excel.handler.biginteger.BigIntegerBooleanHandler;
import io.github.zouzhiy.excel.handler.biginteger.BigIntegerNumberHandler;
import io.github.zouzhiy.excel.handler.biginteger.BigIntegerStringHandler;
import io.github.zouzhiy.excel.handler.booleans.BooleanBooleanHandler;
import io.github.zouzhiy.excel.handler.booleans.BooleanNumberHandler;
import io.github.zouzhiy.excel.handler.booleans.BooleanStringHandler;
import io.github.zouzhiy.excel.handler.bytes.*;
import io.github.zouzhiy.excel.handler.calendar.CalendarDateHandler;
import io.github.zouzhiy.excel.handler.calendar.CalendarNumberHandler;
import io.github.zouzhiy.excel.handler.calendar.CalendarStringHandler;
import io.github.zouzhiy.excel.handler.date.DateDateHandler;
import io.github.zouzhiy.excel.handler.date.DateNumberHandler;
import io.github.zouzhiy.excel.handler.date.DateStringHandler;
import io.github.zouzhiy.excel.handler.doubles.DoubleBooleanHandler;
import io.github.zouzhiy.excel.handler.doubles.DoubleDateHandler;
import io.github.zouzhiy.excel.handler.doubles.DoubleNumberHandler;
import io.github.zouzhiy.excel.handler.doubles.DoubleStringHandler;
import io.github.zouzhiy.excel.handler.floats.FloatBooleanHandler;
import io.github.zouzhiy.excel.handler.floats.FloatNumberHandler;
import io.github.zouzhiy.excel.handler.floats.FloatStringHandler;
import io.github.zouzhiy.excel.handler.head.HeadStringHandler;
import io.github.zouzhiy.excel.handler.image.ImageByteCellHandler;
import io.github.zouzhiy.excel.handler.image.ImageFileCellHandler;
import io.github.zouzhiy.excel.handler.image.ImageUrlCellHandler;
import io.github.zouzhiy.excel.handler.ints.IntegerBooleanHandler;
import io.github.zouzhiy.excel.handler.ints.IntegerNumberHandler;
import io.github.zouzhiy.excel.handler.ints.IntegerStringHandler;
import io.github.zouzhiy.excel.handler.list.ListStringStringJoinHandler;
import io.github.zouzhiy.excel.handler.list.ListStringStringSplitHandler;
import io.github.zouzhiy.excel.handler.localdate.LocalDateDateHandler;
import io.github.zouzhiy.excel.handler.localdate.LocalDateNumberHandler;
import io.github.zouzhiy.excel.handler.localdate.LocalDateStringHandler;
import io.github.zouzhiy.excel.handler.localdatetime.LocalDateTimeDateHandler;
import io.github.zouzhiy.excel.handler.localdatetime.LocalDateTimeNumberHandler;
import io.github.zouzhiy.excel.handler.localdatetime.LocalDateTimeStringHandler;
import io.github.zouzhiy.excel.handler.localtime.LocalTimeDateHandler;
import io.github.zouzhiy.excel.handler.localtime.LocalTimeNumberHandler;
import io.github.zouzhiy.excel.handler.localtime.LocalTimeStringHandler;
import io.github.zouzhiy.excel.handler.longs.LongBooleanHandler;
import io.github.zouzhiy.excel.handler.longs.LongNumberHandler;
import io.github.zouzhiy.excel.handler.longs.LongStringHandler;
import io.github.zouzhiy.excel.handler.shorts.ShortBooleanHandler;
import io.github.zouzhiy.excel.handler.shorts.ShortNumberHandler;
import io.github.zouzhiy.excel.handler.shorts.ShortStringHandler;
import io.github.zouzhiy.excel.handler.string.StringBooleanHandler;
import io.github.zouzhiy.excel.handler.string.StringDateHandler;
import io.github.zouzhiy.excel.handler.string.StringNumberHandler;
import io.github.zouzhiy.excel.handler.string.StringStringHandler;
import io.github.zouzhiy.excel.handler.timestamp.TimestampDateHandler;
import io.github.zouzhiy.excel.handler.timestamp.TimestampNumberHandler;
import io.github.zouzhiy.excel.handler.timestamp.TimestampStringHandler;
import io.github.zouzhiy.excel.metadata.Configuration;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class CellHandlerRegistry {

    private final ConcurrentHashMap<Class<?>, ExcelType> standardMapping = new ConcurrentHashMap<>(32);

    {
        standardMapping.put(BigDecimal.class, ExcelType.NUMERIC);
        standardMapping.put(BigInteger.class, ExcelType.NUMERIC);
        standardMapping.put(boolean.class, ExcelType.BOOLEAN);
        standardMapping.put(Boolean.class, ExcelType.BOOLEAN);
        standardMapping.put(byte.class, ExcelType.NUMERIC);
        standardMapping.put(Byte.class, ExcelType.NUMERIC);
        standardMapping.put(byte[].class, ExcelType.STRING);
        standardMapping.put(Byte[].class, ExcelType.STRING);
        standardMapping.put(Calendar.class, ExcelType.DATE);
        standardMapping.put(java.sql.Date.class, ExcelType.DATE);
        standardMapping.put(Date.class, ExcelType.DATE);
        standardMapping.put(double.class, ExcelType.NUMERIC);
        standardMapping.put(Double.class, ExcelType.NUMERIC);
        standardMapping.put(float.class, ExcelType.NUMERIC);
        standardMapping.put(Float.class, ExcelType.NUMERIC);
        standardMapping.put(int.class, ExcelType.NUMERIC);
        standardMapping.put(Integer.class, ExcelType.NUMERIC);
        standardMapping.put(LocalDate.class, ExcelType.DATE);
        standardMapping.put(LocalDateTime.class, ExcelType.DATE);
        standardMapping.put(LocalTime.class, ExcelType.DATE);
        standardMapping.put(long.class, ExcelType.NUMERIC);
        standardMapping.put(Long.class, ExcelType.NUMERIC);
        standardMapping.put(OffsetDateTime.class, ExcelType.DATE);
        standardMapping.put(OffsetTime.class, ExcelType.DATE);
        standardMapping.put(Short.class, ExcelType.NUMERIC);
        standardMapping.put(String.class, ExcelType.STRING);
        standardMapping.put(Time.class, ExcelType.DATE);
        standardMapping.put(Timestamp.class, ExcelType.DATE);
        standardMapping.put(URL.class, ExcelType.STRING);
        standardMapping.put(List.class, ExcelType.STRING);
    }

    private final Map<Class<?>, CellHandler<?>> allCellHandlerMap = new ConcurrentHashMap<>();

    private final Map<Class<?>, List<CellHandler<?>>> cellHandlerListMap = new ConcurrentHashMap<>();

    @Getter
    private final Configuration configuration;

    public CellHandlerRegistry(Configuration configuration) {
        this.configuration = configuration;

        register(new BigDecimalBooleanHandler());
        register(new BigDecimalNumberHandler());
        register(new BigDecimalStringHandler());

        register(new BigIntegerBooleanHandler());
        register(new BigIntegerNumberHandler());
        register(new BigIntegerStringHandler());

        register(new BooleanBooleanHandler());
        register(new BooleanNumberHandler());
        register(new BooleanStringHandler());

        register(new ByteArrayBoxStringHandler());
        register(new ByteArrayStringHandler());

        register(new ByteBooleanHandler());
        register(new ByteNumberHandler());
        register(new ByteStringHandler());

        register(new CalendarDateHandler());
        register(new CalendarNumberHandler());
        register(new CalendarStringHandler());

        register(new DateDateHandler());
        register(new DateNumberHandler());
        register(new DateStringHandler());

        register(new DoubleBooleanHandler());
        register(new DoubleDateHandler());
        register(new DoubleNumberHandler());
        register(new DoubleStringHandler());

        register(new FloatBooleanHandler());
        register(new FloatNumberHandler());
        register(new FloatStringHandler());

        register(new IntegerBooleanHandler());
        register(new IntegerNumberHandler());
        register(new IntegerStringHandler());

        register(new ListStringStringJoinHandler());
        register(new ListStringStringSplitHandler());

        register(new LocalDateDateHandler());
        register(new LocalDateNumberHandler());
        register(new LocalDateStringHandler());

        register(new LocalDateTimeDateHandler());
        register(new LocalDateTimeNumberHandler());
        register(new LocalDateTimeStringHandler());

        register(new LocalTimeDateHandler());
        register(new LocalTimeNumberHandler());
        register(new LocalTimeStringHandler());

        register(new LongBooleanHandler());
        register(new LongNumberHandler());
        register(new LongStringHandler());

        register(new ShortBooleanHandler());
        register(new ShortNumberHandler());
        register(new ShortStringHandler());

        register(new StringBooleanHandler());
        register(new StringDateHandler());
        register(new StringNumberHandler());
        register(new StringStringHandler());

        register(new TimestampDateHandler());
        register(new TimestampNumberHandler());
        register(new TimestampStringHandler());

        register(new HeadStringHandler());

        register(new ImageByteCellHandler());
        register(new ImageUrlCellHandler());
        register(new ImageFileCellHandler());
    }

    public void register(CellHandler<?> cellHandler) {
        ExcelStandardMapping excelStandardMapping = cellHandler.getClass().getAnnotation(ExcelStandardMapping.class);
        if (excelStandardMapping != null) {
            this.addStandardMapping(cellHandler.getJavaType(), cellHandler.getExcelType());
        }

        Class<?> javaType = cellHandler.getJavaType();
        List<CellHandler<?>> cellHandlerList = cellHandlerListMap.computeIfAbsent(javaType, k -> new ArrayList<>());

        if (!cellHandlerList.contains(cellHandler)) {
            cellHandlerList.add(cellHandler);
        }

        allCellHandlerMap.put(cellHandler.getClass(), cellHandler);
    }

    public <T> CellHandler<T> getCellHandler(Class<? extends CellHandler<?>> cellHandlerClazz) {
        //noinspection unchecked
        return (CellHandler<T>) this.getCellHandler(Collections.singletonList(cellHandlerClazz), ExcelType.BLANK);
    }

    public <T> CellHandler<T> getCellHandler(Class<? extends CellHandler<?>>[] cellHandlerClazz, Class<?> clazz, ExcelType excelType) {
        //noinspection unchecked
        return (CellHandler<T>) this.getCellHandler(Arrays.asList(cellHandlerClazz), clazz, excelType);
    }

    public CellHandler<?> getCellHandler(List<Class<? extends CellHandler<?>>> cellHandlerClazzList, Class<?> clazz, ExcelType excelType) {
        if (cellHandlerClazzList.isEmpty()) {
            return this.getCellHandler(clazz, excelType);
        } else {
            return this.getCellHandler(cellHandlerClazzList, excelType);
        }
    }

    public CellHandler<?> getCellHandler(List<Class<? extends CellHandler<?>>> cellHandlerClazzList, ExcelType excelType) {
        List<CellHandler<?>> cellHandlerList = new ArrayList<>();
        for (Class<? extends CellHandler<?>> cellHandlerClazz : cellHandlerClazzList) {
            CellHandler<?> cellHandler = allCellHandlerMap.get(cellHandlerClazz);
            if (cellHandler == null) {
                continue;
            }
            cellHandlerList.add(cellHandler);
        }
        return getWinnerCellHandler(cellHandlerList, null, excelType);
    }


    public <T> CellHandler<T> getCellHandler(Class<?> clazz, ExcelType excelType) {
        Class<?> boxClazz = this.getBoxClazz(clazz);
        List<CellHandler<?>> cellHandlerList = cellHandlerListMap.get(boxClazz);

        //noinspection unchecked
        return (CellHandler<T>) getWinnerCellHandler(cellHandlerList, clazz, excelType);
    }

    public List<CellHandler<?>> getAllHandler() {
        return new ArrayList<>(allCellHandlerMap.values());
    }

    /**
     * 根据条件，选出一个最佳匹配的 {@link io.github.zouzhiy.excel.handler.CellHandler}
     * 以不返回空为最大追求结果
     *
     * @param cellHandlerList 第一次筛选匹配的CellHandler列表
     * @param javaClazz       java类型
     * @param excelType       excle单元格格式
     * @return 最匹配的CellHandler
     */
    private CellHandler<?> getWinnerCellHandler(List<CellHandler<?>> cellHandlerList, Class<?> javaClazz, ExcelType excelType) {
        if (cellHandlerList == null || cellHandlerList.isEmpty()) {
            throw new ExcelException("无对应的转换器。%s,%s", javaClazz, excelType);
        }

        if (javaClazz == null) {
            javaClazz = cellHandlerList.get(0).getJavaType();
        }
        // 只有一个，则此元素胜出
        if (cellHandlerList.size() == 1) {
            return cellHandlerList.get(0);
        }

        List<CellHandler<?>> winnerList = new ArrayList<>();
        // excelType匹配的优先级最高
        for (CellHandler<?> cellHandler : cellHandlerList) {
            if (excelType.equals(cellHandler.getExcelType())) {
                winnerList.add(cellHandler);
            }
        }
        if (winnerList.size() > 0) {
            return winnerList.get(0);
        }

        // excelType不匹配的情况下，取标准匹配
        excelType = standardMapping.getOrDefault(javaClazz, ExcelType.NONE);
        for (CellHandler<?> cellHandler : cellHandlerList) {
            if (excelType.equals(cellHandler.getExcelType())) {
                winnerList.add(cellHandler);
            }
        }
        if (winnerList.size() > 0) {
            return winnerList.get(0);
        }

        // 实在匹配不上，就去第一个
        return cellHandlerList.get(0);
    }

    private Class<?> getBoxClazz(Class<?> clazz) {
        Class<?> newClazz = null;
        if (!clazz.isPrimitive()) {
            newClazz = clazz;
        } else if (clazz.equals(boolean.class)) {
            newClazz = Boolean.class;
        } else if (clazz.equals(byte.class)) {
            newClazz = Byte.class;
        } else if (clazz.equals(short.class)) {
            newClazz = Short.class;
        } else if (clazz.equals(int.class)) {
            newClazz = Integer.class;
        } else if (clazz.equals(long.class)) {
            newClazz = Long.class;
        } else if (clazz.equals(float.class)) {
            newClazz = Float.class;
        } else if (clazz.equals(double.class)) {
            newClazz = Double.class;
        }
        return newClazz;
    }

    private void addStandardMapping(Class<?> javaType, ExcelType excelType) {
        standardMapping.put(javaType, excelType);
    }
}
