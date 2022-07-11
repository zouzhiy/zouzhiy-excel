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

import io.github.zouzhiy.excel.callback.SheetReadConsumer;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;
import io.github.zouzhiy.excel.metadata.result.RowResultSet;
import io.github.zouzhiy.excel.read.*;
import io.github.zouzhiy.excel.read.registry.RowFootReadRegistry;
import io.github.zouzhiy.excel.read.registry.RowHeadReadRegistry;
import io.github.zouzhiy.excel.read.registry.RowTitleReadRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultSheetRead implements SheetRead {

    private final SheetContext sheetContext;

    private final ExcelClassConfig excelClassConfig;

    private RowDataRead rowDataRead;

    public DefaultSheetRead(SheetContext sheetContext, ExcelClassConfig excelClassConfig) {
        this.sheetContext = sheetContext;
        this.excelClassConfig = excelClassConfig;
    }

    @Override
    public SheetContext getSheetContext() {
        return sheetContext;
    }

    @Override
    public ExcelClassConfig getExcelClassConfig() {
        return excelClassConfig;
    }

    @Override
    public <T> List<T> read(Class<T> clazz) {
        SheetContext sheetContext = this.getSheetContext();
        List<SheetReadConsumer<?>> sheetReadConsumerList = sheetContext.getSheetParameter().getSheetReadConsumerList();

        sheetReadConsumerList.forEach(item -> item.beforeRead(sheetContext));

        CellResultSet titleCellResultSet = this.readTitle();
        List<CellResultSet> headCellResultSetList = this.readHead();
        List<T> dataList = this.readData(clazz);
        List<CellResultSet> footCellResultSetList = this.readFoot();

        //noinspection unchecked
        sheetReadConsumerList.forEach(item -> ((SheetReadConsumer<T>) item).afterRead(sheetContext, titleCellResultSet, headCellResultSetList, dataList, footCellResultSetList));

        return dataList;
    }

    public CellResultSet readTitle() {
        SheetContext sheetContext = this.getSheetContext();
        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        List<SheetReadConsumer<?>> sheetReadConsumerList = sheetParameter.getSheetReadConsumerList();

        Integer titleRowIndex = sheetParameter.getTitleRowStartIndex();
        if (titleRowIndex == -1) {
            sheetReadConsumerList.forEach(item -> item.acceptReadTitle(sheetContext, CellResultSet.none()));
            return null;
        }
        Class<? extends RowTitleRead> rowTitleReadClazz = excelClassConfig.getRowTitleRead();
        if (rowTitleReadClazz == null) {
            rowTitleReadClazz = RowTitleReadRegistry.DEFAULT_ROW_TITLE_READ_CLASS;
        }
        RowTitleRead rowTitleRead = this.getSheetContext().getConfiguration().getRowTitleReadRegistry().getMappingRowRead(rowTitleReadClazz);
        CellResultSet titleCellResultSet = rowTitleRead.read(sheetContext);

        sheetReadConsumerList.forEach(item -> item.acceptReadTitle(sheetContext, titleCellResultSet));

        return titleCellResultSet;
    }

    public List<CellResultSet> readHead() {
        SheetContext sheetContext = this.getSheetContext();
        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        List<SheetReadConsumer<?>> sheetReadConsumerList = sheetParameter.getSheetReadConsumerList();

        Integer headRowIndex = sheetParameter.getHeadRowStartIndex();
        if (headRowIndex == -1) {
            return Collections.emptyList();
        }
        Class<? extends RowHeadRead> rowHeadReadClazz = excelClassConfig.getRowHeadRead();
        if (rowHeadReadClazz == null) {
            rowHeadReadClazz = RowHeadReadRegistry.DEFAULT_ROW_HEAD_READ_CLASS;
        }
        RowHeadRead rowHeadRead = this.getSheetContext().getConfiguration().getRowHeadReadRegistry().getMappingRowRead(rowHeadReadClazz);
        List<CellResultSet> headCellResultSetList = rowHeadRead.read(sheetContext);

        sheetReadConsumerList.forEach(item -> item.acceptReadHead(sheetContext, headCellResultSetList));

        return headCellResultSetList;
    }

    public <T> List<T> readData(Class<T> clazz) {
        SheetContext sheetContext = this.getSheetContext();
        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        List<SheetReadConsumer<?>> sheetReadConsumerList = sheetParameter.getSheetReadConsumerList();

        RowDataRead rowDataRead = this.getRowDataRead();

        int curRowIndex = sheetParameter.getDataRowStartIndex();
        int lasRowIndex = sheetContext.getLasRowIndex();

        List<T> dataList = new ArrayList<>();
        while (curRowIndex <= lasRowIndex) {
            RowResultSet<T> rowResultSet = rowDataRead.read(sheetContext, clazz, curRowIndex);
            curRowIndex += rowResultSet.getRowspan();
            if (rowResultSet.getData() != null) {
                dataList.add(rowResultSet.getData());
            }
        }

        //noinspection unchecked
        sheetReadConsumerList.forEach(item -> ((SheetReadConsumer<T>) item).acceptReadData(sheetContext, dataList));

        return dataList;
    }

    public List<CellResultSet> readFoot() {
        SheetContext sheetContext = this.getSheetContext();
        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        List<SheetReadConsumer<?>> sheetReadConsumerList = sheetParameter.getSheetReadConsumerList();

        Integer headRowIndex = sheetParameter.getHeadRowStartIndex();
        if (headRowIndex == -1) {
            sheetReadConsumerList.forEach(item -> item.acceptReadFoot(sheetContext, Collections.emptyList()));
            return Collections.emptyList();
        }
        Class<? extends RowFootRead> rowFootReadClazz = excelClassConfig.getRowFootRead();
        if (rowFootReadClazz == null) {
            rowFootReadClazz = RowFootReadRegistry.DEFAULT_ROW_FOOT_READ_CLASS;
        }
        RowFootRead rowFootRead = this.getSheetContext().getConfiguration().getRowFootReadRegistry().getMappingRowRead(rowFootReadClazz);
        List<CellResultSet> footCellResultSetList = rowFootRead.read(sheetContext);

        sheetReadConsumerList.forEach(item -> item.acceptReadFoot(sheetContext, footCellResultSetList));

        return footCellResultSetList;
    }

    private RowDataRead getRowDataRead() {
        if (rowDataRead == null) {
            this.rowDataRead = new DefaultRowDataRead();
        }
        return rowDataRead;
    }

}
