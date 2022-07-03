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
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;
import io.github.zouzhiy.excel.utils.RegionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public abstract class AbstractCellHandler<T> implements CellHandler<T> {

    private final Class<T> javaType;

    public AbstractCellHandler() {
        this.javaType = getSuperclassTypeParameter(this.getClass());
    }

    @Override
    public final Class<T> getJavaType() {
        return javaType;
    }

    @Override
    public final T read(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResultSet cellResultSet) {
        CellResult firstCellResult = cellResultSet.getFirstCellResult();
        if (firstCellResult.isBlank() || firstCellResult.isNone()) {
            if (!excelFieldConfig.getExcelType().equals(ExcelType.NONE)) {
                return null;
            }
        }
        return getCellValue(sheetContext, excelFieldConfig, firstCellResult);
    }

    @Override
    public final void write(RowContext rowContext, Integer columnIndex, ExcelFieldConfig excelFieldConfig, T value) {
        Row row = rowContext.getRowList().get(0);
        Cell cell = row.createCell(columnIndex);
        if (value != null) {
            this.setCellValue(rowContext, excelFieldConfig, cell, value);
        }

        SheetContext sheetContext = rowContext.getSheetContext();
        CellStyle cellStyle = sheetContext.getDataCellStyle(excelFieldConfig, this.getDefaultExcelFormat());
        cell.setCellStyle(cellStyle);

        int rowspan = rowContext.getRowspan();
        int colspan = excelFieldConfig.getColspan();
        int rowIndex = row.getRowNum();
        RegionUtils.addMergedRegionIfPresent(sheetContext, cellStyle, rowIndex, rowIndex + rowspan - 1, columnIndex, columnIndex + colspan - 1);
    }

    protected abstract T getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult);

    protected abstract void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, T value);

    private Class<T> getSuperclassTypeParameter(Class<?> clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            // try to climb up the hierarchy until meet something useful
            if (AbstractCellHandler.class != genericSuperclass) {
                return getSuperclassTypeParameter(clazz.getSuperclass());
            }

            throw new ExcelException("'" + getClass() + "' extends TypeReference but misses the type parameter. "
                    + "Remove the extension or add a type parameter to it.");
        }

        Type rawType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        // TODO remove this when Reflector is fixed to return Types
        if (rawType instanceof ParameterizedType) {
            rawType = ((ParameterizedType) rawType).getRawType();
        }

        //noinspection unchecked
        return (Class<T>) rawType;
    }

    protected String getJavaFormat(ExcelFieldConfig excelFieldConfig) {
        String javaFormat = excelFieldConfig.getJavaFormat();
        if (javaFormat.length() > 0) {
            return javaFormat;
        } else {
            return this.getDefaultJavaFormat();
        }
    }
}
