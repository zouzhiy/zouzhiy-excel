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
package io.github.zouzhiy.excel.read.defaults;

import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.handler.CellHandler;
import io.github.zouzhiy.excel.handler.CellHandlerRegistry;
import io.github.zouzhiy.excel.ibatis.reflection.MetaClass;
import io.github.zouzhiy.excel.ibatis.reflection.invoker.Invoker;
import io.github.zouzhiy.excel.metadata.CellSpan;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.MergedRegion;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;
import io.github.zouzhiy.excel.metadata.result.RowResultSet;
import io.github.zouzhiy.excel.read.CellDataRead;
import io.github.zouzhiy.excel.read.RowDataRead;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultRowDataRead implements RowDataRead {


    @Override
    public <T> RowResultSet<T> read(SheetContext sheetContext, Class<T> clazz, int rowIndex) {
        Configuration configuration = sheetContext.getConfiguration();
        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        int dataColumnStartIndex = sheetParameter.getDataColumnStartIndex();


        ExcelClassConfig excelClassConfig = sheetContext.getExcelClassConfig();
        List<ExcelFieldConfig> itemList = excelClassConfig.getItemList();
        // 这一行的数据影响行数
        int maxRowspan = this.getDataRowMaxRowspan(sheetContext, rowIndex);

        List<Row> rowList = this.getRowList(sheetContext, rowIndex, rowIndex + maxRowspan - 1);

        int curColumnIndex = dataColumnStartIndex;

        T item = configuration.getObjectFactory().create(clazz);
        MetaClass metaClass = sheetContext.getConfiguration().newMetaClazz(clazz);

        CellDataRead cellDataRead = DefaultCellDataRead.getInstance();
        for (ExcelFieldConfig excelFieldConfig : itemList) {
            int colspan = excelFieldConfig.getColspan();
            CellResultSet cellResult = cellDataRead.read(sheetContext, rowList, curColumnIndex, curColumnIndex + colspan - 1);

            this.setValue(sheetContext, excelFieldConfig, item, metaClass, cellResult);

            curColumnIndex += colspan;
        }

        return RowResultSet.newInstance(item, maxRowspan);
    }

    private <T> void setValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, T item, MetaClass metaClass, CellResultSet cellResultSet) {
        Class<? extends CellHandler<?>>[] cellHandlerClazz = excelFieldConfig.getCellHandler();
        String propertyName = excelFieldConfig.getPropertyName();
        boolean hasSetter = metaClass.hasSetter(propertyName);
        if (!hasSetter) {
            throw new ExcelException("没有对应的set方法");
        }
        Invoker invoker = metaClass.getSetInvoker(propertyName);
        Class<?> javaType = invoker.getType();
        ExcelType excelType;
        if (cellResultSet != null && !cellResultSet.isNone() && excelFieldConfig.getExcelType().equals(ExcelType.BLANK)) {
            excelType = cellResultSet.getExcelType();
        } else {
            excelType = excelFieldConfig.getExcelType();
        }
        CellHandler<?> cellHandler = this.getCellHandler(sheetContext, cellHandlerClazz, javaType, excelType);

        Object object = cellHandler.read(sheetContext, excelFieldConfig, cellResultSet);
        try {
            invoker.invoke(item, new Object[]{object});
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ExcelException("set失败");
        }
    }

    private <T> CellHandler<T> getCellHandler(SheetContext sheetContext, Class<? extends CellHandler<?>>[] cellHandlerClazz, Class<T> javaType, ExcelType excelType) {
        CellHandlerRegistry cellHandlerRegistry = sheetContext.getConfiguration().getCellHandlerRegistry();
        //noinspection unchecked
        return cellHandlerRegistry.getCellHandler(cellHandlerClazz, javaType, excelType);
    }


    private int getDataRowMaxRowspan(SheetContext sheetContext, int rowIndex) {
        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        MergedRegion mergedRegion = sheetContext.getMergedRegion();
        short lastCellNum = sheetContext.getSheet().getRow(rowIndex).getLastCellNum();
        int curColumnIndex = sheetParameter.getDataColumnStartIndex();

        int maxRowspan = 1;
        while (curColumnIndex < lastCellNum) {
            CellSpan cellSpan = mergedRegion.getCellSpan(rowIndex, curColumnIndex);
            curColumnIndex += cellSpan.getColspan();
            maxRowspan = Math.max(maxRowspan, cellSpan.getRowspan());
        }
        return maxRowspan;
    }

    private List<Row> getRowList(SheetContext sheetContext, int firstRowIndex, int lastRowIndex) {
        Sheet sheet = sheetContext.getSheet();
        List<Row> rowList = new ArrayList<>();
        for (int i = firstRowIndex; i <= lastRowIndex; i++) {
            Row row = sheet.getRow(i);
            rowList.add(row);
        }

        return rowList;
    }
}
