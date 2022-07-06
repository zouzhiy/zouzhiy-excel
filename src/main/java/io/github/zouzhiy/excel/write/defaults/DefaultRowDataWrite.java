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
package io.github.zouzhiy.excel.write.defaults;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.context.defualts.DefaultRowContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.CellHandler;
import io.github.zouzhiy.excel.handler.CellHandlerRegistry;
import io.github.zouzhiy.excel.ibatis.reflection.MetaObject;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.write.RowDataWrite;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultRowDataWrite implements RowDataWrite {

    @Override
    public <T> int write(SheetContext sheetContext, T item, int rowIndex) {
        Configuration configuration = sheetContext.getConfiguration();
        ExcelClassConfig excelClassConfig = sheetContext.getExcelClassConfig();

        int rowspan = this.getRowspan(sheetContext, excelClassConfig, item);

        List<Row> rowList = new ArrayList<>();
        for (int i = 0; i < rowspan; i++) {
            Row row = this.createRow(sheetContext, rowIndex + i);
            rowList.add(row);
        }
        RowContext rowContext = new DefaultRowContext(sheetContext, item, rowList, rowspan);

        List<ExcelFieldConfig> writeMainConfigItemList = excelClassConfig.getItemList();
        int curColumnIndex = 0;
        for (ExcelFieldConfig excelFieldConfig : writeMainConfigItemList) {
            Class<? extends CellHandler<?>> cellHandlerClazz = excelFieldConfig.getCellHandler();
            Class<?> javaType = excelFieldConfig.getJavaType();
            ExcelType excelType = excelFieldConfig.getExcelType();
            String propertyName = excelFieldConfig.getPropertyName();

            MetaObject metaObject = configuration.newMetaObject(item);
            Object value = metaObject.getValue(propertyName);

            CellHandler<?> cellHandler = this.getCellHandler(sheetContext, cellHandlerClazz, javaType, excelType);
            this.write(cellHandler, rowContext, curColumnIndex, excelFieldConfig, value);
            curColumnIndex += excelFieldConfig.getColspan();
        }

        return rowspan;
    }

    private <E> void write(CellHandler<?> cellHandler, RowContext rowContext, int curColumnIndex, ExcelFieldConfig excelFieldConfig, E value) {
        //noinspection unchecked
        CellHandler<E> handler = (CellHandler<E>) cellHandler;
        handler.write(rowContext, curColumnIndex, excelFieldConfig, value);
    }

    private Row createRow(SheetContext sheetContext, int rowIndex) {
        return sheetContext.getSheet().createRow(rowIndex);
    }

    private CellHandler<?> getCellHandler(SheetContext sheetContext, Class<? extends CellHandler<?>> cellHandlerClazz, Class<?> javaType, ExcelType excelType) {
        CellHandlerRegistry cellHandlerRegistry = sheetContext.getConfiguration().getCellHandlerRegistry();
        return cellHandlerRegistry.getCellHandler(cellHandlerClazz, javaType, excelType);
    }

    private <T> int getRowspan(SheetContext sheetContext, ExcelClassConfig excelClassConfig, T item) {
        Configuration configuration = sheetContext.getConfiguration();
        List<ExcelFieldConfig> writeMainConfigItemList = excelClassConfig.getItemList();

        int maxRowspan = 1;
        for (ExcelFieldConfig excelFieldConfig : writeMainConfigItemList) {
            Class<? extends CellHandler<?>> cellHandlerClazz = excelFieldConfig.getCellHandler();
            Class<?> javaType = excelFieldConfig.getJavaType();
            ExcelType excelType = excelFieldConfig.getExcelType();
            String propertyName = excelFieldConfig.getPropertyName();

            MetaObject metaObject = configuration.newMetaObject(item);
            Object value = metaObject.getValue(propertyName);

            CellHandler<?> cellHandler = this.getCellHandler(sheetContext, cellHandlerClazz, javaType, excelType);

            int rowspan = this.getRowspan(cellHandler, value);
            maxRowspan = Math.max(maxRowspan, rowspan);
        }
        return maxRowspan;
    }

    private <E> int getRowspan(CellHandler<?> cellHandler, E value) {
        //noinspection unchecked
        CellHandler<E> handler = (CellHandler<E>) cellHandler;
        return handler.getWriteRowspan(value);

    }


}
