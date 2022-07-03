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

import io.github.zouzhiy.excel.callback.SheetWriteConsumer;
import io.github.zouzhiy.excel.cellstyle.SheetCellStyleRead;
import io.github.zouzhiy.excel.cellstyle.defaults.DefaultSheetCellStyleRead;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.write.*;
import io.github.zouzhiy.excel.write.registry.RowFootWriteRegistry;
import io.github.zouzhiy.excel.write.registry.RowHeadWriteRegistry;
import io.github.zouzhiy.excel.write.registry.RowTitleWriteRegistry;

import java.util.List;


/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultSheetWrite implements SheetWrite {

    private final SheetContext sheetContext;

    private final ExcelClassConfig excelClassConfig;

    private RowDataWrite rowDataWrite;

    private SheetCellStyleRead sheetCellStyleRead;

    public DefaultSheetWrite(SheetContext sheetContext, ExcelClassConfig excelClassConfig) {
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
    public <T> void write(List<T> itemList) {
        SheetContext sheetContext = this.getSheetContext();
        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        List<SheetWriteConsumer<?>> sheetWriteConsumerList = sheetParameter.getSheetWriteConsumerList();
        //noinspection unchecked
        sheetWriteConsumerList.forEach(item -> ((SheetWriteConsumer<T>) item).beforeWrite(sheetContext, itemList));

        this.readTemplate();
        this.writeTitle(itemList);
        this.writeHead(itemList);
        this.writeData(itemList);
        this.writeFoot(itemList);

        //noinspection unchecked
        sheetWriteConsumerList.forEach(item -> ((SheetWriteConsumer<T>) item).afterWrite(sheetContext, itemList));
    }

    private void readTemplate() {
        SheetCellStyleRead sheetCellStyleRead = this.getSheetCellStyleRead();
        if (sheetCellStyleRead == null) {
            return;
        }
        sheetCellStyleRead.read();
    }

    private RowDataWrite getRowDataWrite() {
        if (rowDataWrite == null) {
            this.rowDataWrite = new DefaultRowDataWrite();
        }
        return rowDataWrite;
    }

    private <T> void writeTitle(List<T> itemList) {
        SheetContext sheetContext = this.getSheetContext();
        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        List<SheetWriteConsumer<?>> sheetWriteConsumerList = sheetParameter.getSheetWriteConsumerList();
        //noinspection unchecked
        sheetWriteConsumerList.forEach(item -> ((SheetWriteConsumer<T>) item).beforeWriteTitle(sheetContext, itemList));

        Integer titleRowIndex = sheetParameter.getTitleRowStartIndex();
        if (titleRowIndex == -1) {
            //noinspection unchecked
            sheetWriteConsumerList.forEach(item -> ((SheetWriteConsumer<T>) item).afterWriteTitle(sheetContext, itemList));
            return;
        }
        Class<? extends RowTitleWrite> rowTitleWriteClazz = excelClassConfig.getRowTitleWrite();
        if (rowTitleWriteClazz == null) {
            rowTitleWriteClazz = RowTitleWriteRegistry.DEFAULT_ROW_TITLE_WRITE_CLASS;
        }
        RowTitleWrite rowTitleWrite = sheetContext.getConfiguration().getRowTitleWriteRegistry().getMappingRowWrite(rowTitleWriteClazz);
        rowTitleWrite.write(sheetContext, itemList);

        //noinspection unchecked
        sheetWriteConsumerList.forEach(item -> ((SheetWriteConsumer<T>) item).afterWriteTitle(sheetContext, itemList));
    }


    private <T> void writeHead(List<T> itemList) {
        SheetContext sheetContext = this.getSheetContext();
        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        List<SheetWriteConsumer<?>> sheetWriteConsumerList = sheetParameter.getSheetWriteConsumerList();
        //noinspection unchecked
        sheetWriteConsumerList.forEach(item -> ((SheetWriteConsumer<T>) item).beforeWriteHead(sheetContext, itemList));

        Integer headRowIndex = sheetParameter.getHeadRowStartIndex();
        if (headRowIndex == -1) {
            //noinspection unchecked
            sheetWriteConsumerList.forEach(item -> ((SheetWriteConsumer<T>) item).afterWriteHead(sheetContext, itemList));
            return;
        }
        Class<? extends RowHeadWrite> rowHeadWriteClazz = excelClassConfig.getRowHeadWrite();
        if (rowHeadWriteClazz == null) {
            rowHeadWriteClazz = RowHeadWriteRegistry.DEFAULT_ROW_HEAD_WRITE_CLASS;
        }
        RowHeadWrite rowHeadWrite = sheetContext.getConfiguration().getRowHeadWriteRegistry().getMappingRowWrite(rowHeadWriteClazz);
        rowHeadWrite.write(sheetContext, itemList);

        //noinspection unchecked
        sheetWriteConsumerList.forEach(item -> ((SheetWriteConsumer<T>) item).afterWriteHead(sheetContext, itemList));
    }

    private <T> void writeData(List<T> itemList) {
        SheetContext sheetContext = this.getSheetContext();
        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        List<SheetWriteConsumer<?>> sheetWriteConsumerList = sheetParameter.getSheetWriteConsumerList();
        //noinspection unchecked
        sheetWriteConsumerList.forEach(item -> ((SheetWriteConsumer<T>) item).beforeWriteData(sheetContext, itemList));

        RowDataWrite rowDataWrite = this.getRowDataWrite();
        int curRowIndex = sheetParameter.getDataRowStartIndex();
        for (T item : itemList) {
            int count = rowDataWrite.write(sheetContext, item, curRowIndex);
            curRowIndex += count;
        }

        //noinspection unchecked
        sheetWriteConsumerList.forEach(item -> ((SheetWriteConsumer<T>) item).afterWriteData(sheetContext, itemList));
    }

    private <T> void writeFoot(List<T> itemList) {
        SheetContext sheetContext = this.getSheetContext();
        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        List<SheetWriteConsumer<?>> sheetWriteConsumerList = sheetParameter.getSheetWriteConsumerList();
        //noinspection unchecked
        sheetWriteConsumerList.forEach(item -> ((SheetWriteConsumer<T>) item).beforeWriteFoot(sheetContext, itemList));

        Integer headRowIndex = sheetParameter.getHeadRowStartIndex();
        if (headRowIndex == -1) {
            //noinspection unchecked
            sheetWriteConsumerList.forEach(item -> ((SheetWriteConsumer<T>) item).afterWriteFoot(sheetContext, itemList));
            return;
        }
        Class<? extends RowFootWrite> rowFootWriteClazz = excelClassConfig.getRowFootWrite();
        if (rowFootWriteClazz == null) {
            rowFootWriteClazz = RowFootWriteRegistry.DEFAULT_ROW_FOOT_WRITE_CLASS;
        }
        RowFootWrite rowFootWrite = sheetContext.getConfiguration().getRowFootWriteRegistry().getMappingRowWrite(rowFootWriteClazz);
        rowFootWrite.write(sheetContext, itemList);

        //noinspection unchecked
        sheetWriteConsumerList.forEach(item -> ((SheetWriteConsumer<T>) item).afterWriteFoot(sheetContext, itemList));
    }

    private SheetCellStyleRead getSheetCellStyleRead() {
        SheetContext sheetContext = this.getSheetContext();
        if (!sheetContext.hasInputStream()) {
            return null;
        }
        if (sheetCellStyleRead == null) {
            sheetCellStyleRead = new DefaultSheetCellStyleRead(this.getSheetContext());
        }
        return sheetCellStyleRead;
    }
}
