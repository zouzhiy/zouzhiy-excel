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
package io.github.zouzhiy.excel.handler.registry;

import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.handler.CellHandler;
import io.github.zouzhiy.excel.handler.NoneCellHandler;
import io.github.zouzhiy.excel.handler.head.HeadStringHandler;
import io.github.zouzhiy.excel.handler.string.StringBooleanHandler;
import io.github.zouzhiy.excel.handler.string.StringStringHandler;
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
        register(new StringBooleanHandler());
        register(new StringStringHandler());


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
        Map<ExcelType, List<CellHandler<?>>> cellHandlerListMap = cellHandlerListMapMap.get(javaType);
        if (cellHandlerListMap == null) {
            throw new ExcelException("不存在的CellHandler");
        }
        if (excelType == null || excelType.equals(ExcelType.NONE)) {
            excelType = STANDARD_MAPPING.getOrDefault(javaType, ExcelType.STRING);
        }
        List<CellHandler<?>> cellHandlerList = cellHandlerListMap.get(excelType);
        if (cellHandlerList == null || cellHandlerList.isEmpty()) {
            throw new ExcelException("不存在的CellHandler");
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

}
