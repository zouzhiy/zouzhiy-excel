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
import io.github.zouzhiy.excel.handler.ints.IntegerBooleanHandler;
import io.github.zouzhiy.excel.handler.ints.IntegerNumberHandler;
import io.github.zouzhiy.excel.handler.ints.IntegerStringHandler;
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
        STANDARD_MAPPING.put(byte[].class, ExcelType.STRING);
        STANDARD_MAPPING.put(byte.class, ExcelType.NUMERIC);
        STANDARD_MAPPING.put(Byte.class, ExcelType.NUMERIC);
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
    }

    private final Configuration configuration;

    private final Map<Class<?>, Map<ExcelType, List<CellHandler<?>>>> cellHandlerListMapMap = new ConcurrentHashMap<>(32);

    private final Map<Class<? extends CellHandler<?>>, CellHandler<?>> cellHandlerMap = new ConcurrentHashMap<>(64);

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

        register(new ByteArrayBoxStringHandler());
        register(new ByteArrayStringHandler());

        register(new HeadStringHandler());
    }

    public void register(CellHandler<?> cellHandler) {
        Class<?> javaType = cellHandler.getJavaType();
        ExcelType excelType = cellHandler.getExcelType();
        Map<ExcelType, List<CellHandler<?>>> cellHandlerListMap = cellHandlerListMapMap.computeIfAbsent(javaType, key -> new ConcurrentHashMap<>(16));
        List<CellHandler<?>> cellHandlerList = cellHandlerListMap.computeIfAbsent(excelType, key -> Collections.synchronizedList(new ArrayList<>(5)));
        if (!cellHandlerList.contains(cellHandler)) {
            cellHandlerList.add(cellHandler);
        }
        //noinspection unchecked
        Class<? extends CellHandler<?>> cellHandlerClazz = (Class<? extends CellHandler<?>>) cellHandler.getClass();
        cellHandlerMap.put(cellHandlerClazz, cellHandler);
    }

    public CellHandler<?> getMappingCellHandler(Class<? extends CellHandler<?>> cellHandlerClazz) {
        CellHandler<?> cellHandler = cellHandlerMap.get(cellHandlerClazz);
        if (cellHandler == null) {
            throw new ExcelException("不存在的CellHandler");
        }
        return cellHandler;
    }

    public <T> CellHandler<T> getCellHandler(Class<T> javaType, ExcelType excelType) {
        Class<?> boxClazz = getBoxClazz(javaType);
        Map<ExcelType, List<CellHandler<?>>> cellHandlerListMap = cellHandlerListMapMap.get(boxClazz);
        if (cellHandlerListMap == null) {
            throw new ExcelException("不存在的CellHandler,%s,%s", javaType, excelType);
        }
        if (excelType == null || excelType.equals(ExcelType.NONE) || excelType.equals(ExcelType.BLANK)) {
            excelType = STANDARD_MAPPING.getOrDefault(javaType, ExcelType.STRING);
        }
        List<CellHandler<?>> cellHandlerList = cellHandlerListMap.get(excelType);
        if (cellHandlerList == null || cellHandlerList.isEmpty()) {
            throw new ExcelException("不存在的CellHandler,%s,%s", javaType, excelType);
        }

        //noinspection unchecked
        return (CellHandler<T>) cellHandlerList.get(0);
    }

    public <T> CellHandler<T> getCellHandler(Class<? extends CellHandler<?>> cellHandlerClazz, Class<T> javaType, ExcelType excelType) {
        if (cellHandlerClazz != null && !cellHandlerClazz.equals(NoneCellHandler.class)) {
            //noinspection unchecked
            return (CellHandler<T>) this.getMappingCellHandler(cellHandlerClazz);
        } else {
            return this.getCellHandler(javaType, excelType);
        }
    }


    private Class<?> getBoxClazz(Class<?> clazz) {
        if (!clazz.isPrimitive()) {
            return clazz;
        } else if (clazz.equals(boolean.class)) {
            return Boolean.class;
        } else if (clazz.equals(byte.class)) {
            return Byte.class;
        } else if (clazz.equals(short.class)) {
            return Short.class;
        } else if (clazz.equals(int.class)) {
            return Integer.class;
        } else if (clazz.equals(long.class)) {
            return Long.class;
        } else if (clazz.equals(float.class)) {
            return Float.class;
        } else if (clazz.equals(double.class)) {
            return Double.class;
        }
        return clazz;
    }
}
