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

    private static final ConcurrentHashMap<Class<?>, ExcelType> STANDARD_MAPPING;

    static {
        STANDARD_MAPPING = new ConcurrentHashMap<>();
        STANDARD_MAPPING.put(BigDecimal.class, ExcelType.NUMERIC);
        STANDARD_MAPPING.put(BigInteger.class, ExcelType.NUMERIC);
        STANDARD_MAPPING.put(boolean.class, ExcelType.BOOLEAN);
        STANDARD_MAPPING.put(Boolean.class, ExcelType.BOOLEAN);
        STANDARD_MAPPING.put(byte.class, ExcelType.NUMERIC);
        STANDARD_MAPPING.put(Byte.class, ExcelType.NUMERIC);
        STANDARD_MAPPING.put(byte[].class, ExcelType.STRING);
        STANDARD_MAPPING.put(Byte[].class, ExcelType.STRING);
        STANDARD_MAPPING.put(Calendar.class, ExcelType.DATE);
        STANDARD_MAPPING.put(java.sql.Date.class, ExcelType.DATE);
        STANDARD_MAPPING.put(Date.class, ExcelType.DATE);
        STANDARD_MAPPING.put(double.class, ExcelType.NUMERIC);
        STANDARD_MAPPING.put(Double.class, ExcelType.NUMERIC);
        STANDARD_MAPPING.put(float.class, ExcelType.NUMERIC);
        STANDARD_MAPPING.put(Float.class, ExcelType.NUMERIC);
        STANDARD_MAPPING.put(int.class, ExcelType.NUMERIC);
        STANDARD_MAPPING.put(Integer.class, ExcelType.NUMERIC);
        STANDARD_MAPPING.put(LocalDate.class, ExcelType.DATE);
        STANDARD_MAPPING.put(LocalDateTime.class, ExcelType.DATE);
        STANDARD_MAPPING.put(LocalTime.class, ExcelType.DATE);
        STANDARD_MAPPING.put(long.class, ExcelType.NUMERIC);
        STANDARD_MAPPING.put(Long.class, ExcelType.NUMERIC);
        STANDARD_MAPPING.put(OffsetDateTime.class, ExcelType.DATE);
        STANDARD_MAPPING.put(OffsetTime.class, ExcelType.DATE);
        STANDARD_MAPPING.put(Short.class, ExcelType.NUMERIC);
        STANDARD_MAPPING.put(String.class, ExcelType.STRING);
        STANDARD_MAPPING.put(Time.class, ExcelType.DATE);
        STANDARD_MAPPING.put(Timestamp.class, ExcelType.DATE);
        STANDARD_MAPPING.put(URL.class, ExcelType.STRING);
        STANDARD_MAPPING.put(List.class, ExcelType.STRING);
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
        Class<?> javaType = cellHandler.getJavaType();
        List<CellHandler<?>> cellHandlerList = cellHandlerListMap.computeIfAbsent(javaType, k -> new ArrayList<>());

        if (!cellHandlerList.contains(cellHandler)) {
            cellHandlerList.add(cellHandler);
        }

        allCellHandlerMap.put(cellHandler.getClass(), cellHandler);
    }

    public <T> CellHandler<T> getCellHandler(Class<? extends CellHandler<T>> cellHandlerClazz, Class<T> clazz, ExcelType excelType) {
        return getCellHandler(Collections.singletonList(cellHandlerClazz), clazz, excelType);
    }

    public <T> CellHandler<T> getCellHandler(Class<? extends CellHandler<T>>[] cellHandlerClazz, Class<T> clazz, ExcelType excelType) {
        if (cellHandlerClazz == null || cellHandlerClazz.length == 0) {
            return this.getCellHandler(clazz, excelType);

        } else {
            return this.getCellHandler(Arrays.asList(cellHandlerClazz), excelType);
        }
    }

    public <T> CellHandler<T> getCellHandler(List<Class<? extends CellHandler<T>>> cellHandlerClazzList, Class<T> clazz, ExcelType excelType) {
        if (cellHandlerClazzList.isEmpty()) {
            return this.getCellHandler(clazz, excelType);
        } else {
            return this.getCellHandler(cellHandlerClazzList, excelType);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> CellHandler<T> getCellHandler(List<Class<? extends CellHandler<T>>> cellHandlerClazzList, ExcelType excelType) {
        List<CellHandler<T>> cellHandlerList = new ArrayList<>();
        for (Class<? extends CellHandler<T>> cellHandlerClazz : cellHandlerClazzList) {
            CellHandler<T> cellHandler = (CellHandler<T>) allCellHandlerMap.get(cellHandlerClazz);
            if (cellHandler == null) {
                continue;
            }
            cellHandlerList.add(cellHandler);
        }
        if (cellHandlerList.isEmpty()) {
            throw new ExcelException("无对应的转换器。%s,%s", cellHandlerClazzList, excelType);
        }
        if (cellHandlerList.size() == 1) {
            return cellHandlerList.get(0);
        }

        for (CellHandler<T> cellHandler : cellHandlerList) {
            if (excelType.equals(cellHandler.getExcelType())) {
                return cellHandler;
            }
        }

        List<CellHandler<?>> winnerList = new ArrayList<>();
        for (CellHandler<?> cellHandler : cellHandlerList) {
            if (excelType.equals(cellHandler.getExcelType())) {
                winnerList.add(cellHandler);
            }
        }
        if (winnerList.size() > 0) {
            return (CellHandler<T>) winnerList.get(0);
        }

        if (excelType.equals(ExcelType.BLANK)) {
            excelType = STANDARD_MAPPING.getOrDefault(cellHandlerList.get(0).getJavaType(), ExcelType.NONE);
        }
        for (CellHandler<?> cellHandler : cellHandlerList) {
            if (excelType.equals(cellHandler.getExcelType())) {
                winnerList.add(cellHandler);
            }
        }
        if (winnerList.size() > 0) {
            return (CellHandler<T>) winnerList.get(0);
        }

        for (CellHandler<?> cellHandler : cellHandlerList) {
            if (ExcelType.NONE.equals(cellHandler.getExcelType())) {
                winnerList.add(cellHandler);
            }
        }
        if (winnerList.size() > 0) {
            return (CellHandler<T>) winnerList.get(0);
        }

        throw new ExcelException("无对应的转换器。%s,%s", cellHandlerClazzList, excelType);
    }

    public <T> CellHandler<T> getCellHandler(Class<? extends CellHandler<T>> cellHandlerClazz) {
        CellHandler<?> cellHandler = allCellHandlerMap.get(cellHandlerClazz);
        if (cellHandler == null) {
            throw new ExcelException("无对应的转换器。%s", cellHandlerClazz);
        }
        //noinspection unchecked
        return (CellHandler<T>) cellHandler;
    }

    @SuppressWarnings("unchecked")
    public <T> CellHandler<T> getCellHandler(Class<T> clazz, ExcelType excelType) {
        Class<?> boxClazz = this.getBoxClazz(clazz);
        List<CellHandler<?>> cellHandlerList = cellHandlerListMap.get(boxClazz);
        if (cellHandlerList == null || cellHandlerList.isEmpty()) {
            throw new ExcelException("无对应的转换器。%s,%s", clazz, excelType);
        }

        if (cellHandlerList.size() == 1) {
            return (CellHandler<T>) cellHandlerList.get(0);
        }

        List<CellHandler<?>> winnerList = new ArrayList<>();
        for (CellHandler<?> cellHandler : cellHandlerList) {
            if (excelType.equals(cellHandler.getExcelType())) {
                winnerList.add(cellHandler);
            }
        }
        if (winnerList.size() > 0) {
            return (CellHandler<T>) winnerList.get(0);
        }

        if (excelType.equals(ExcelType.BLANK)) {
            excelType = STANDARD_MAPPING.getOrDefault(clazz, ExcelType.NONE);
        }
        for (CellHandler<?> cellHandler : cellHandlerList) {
            if (excelType.equals(cellHandler.getExcelType())) {
                winnerList.add(cellHandler);
            }
        }
        if (winnerList.size() > 0) {
            return (CellHandler<T>) winnerList.get(0);
        }

        for (CellHandler<?> cellHandler : cellHandlerList) {
            if (ExcelType.NONE.equals(cellHandler.getExcelType())) {
                winnerList.add(cellHandler);
            }
        }
        if (winnerList.size() > 0) {
            return (CellHandler<T>) winnerList.get(0);
        }
        throw new ExcelException("无对应的转换器。%s,%s", clazz, excelType);
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
}
