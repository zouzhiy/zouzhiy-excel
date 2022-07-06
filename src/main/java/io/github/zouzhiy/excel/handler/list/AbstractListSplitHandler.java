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
package io.github.zouzhiy.excel.handler.list;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.context.defualts.DefaultRowContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.handler.CellHandler;
import io.github.zouzhiy.excel.handler.CellHandlerRegistry;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author zouzhiy
 * @since 2022/7/4
 */
public abstract class AbstractListSplitHandler<E> extends AbstractListHandler<E> {

    @Override
    public List<E> read(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResultSet cellResultSet) {
        CellHandlerRegistry cellHandlerRegistry = sheetContext.getConfiguration().getCellHandlerRegistry();
        List<List<CellResult>> cellResultListList = cellResultSet.getCellResultListList();
        Class<E> javaType = this.getItemType();
        List<E> dataList = new ArrayList<>();
        for (List<CellResult> cellResultList : cellResultListList) {
            E data = cellResultList.stream()
                    .map(cellResult -> {
                        ExcelType excelType = cellResult.getExcelType();
                        CellHandler<E> cellHandler = cellHandlerRegistry.getCellHandler(javaType, excelType);
                        return cellHandler.read(sheetContext, excelFieldConfig, CellResultSet.newInstance(Collections.singletonList(cellResultList)));
                    })
                    .filter(Objects::nonNull)
                    .findAny()
                    .orElse(null);
            if (data == null) {
                continue;
            }
            dataList.add(data);
        }
        return dataList;
    }

    @Override
    public void write(RowContext rowContext, Integer columnIndex, ExcelFieldConfig excelFieldConfig, List<E> valueList) {
        SheetContext sheetContext = rowContext.getSheetContext();
        List<Row> rowList = rowContext.getRowList();
        if (rowList.size() < valueList.size()) {
            throw new ExcelException("rowList 与预期不符。%s,%s,%s", rowList.size(), valueList, valueList.size());
        }
        CellHandlerRegistry cellHandlerRegistry = sheetContext.getConfiguration().getCellHandlerRegistry();
        CellHandler<E> cellHandler = cellHandlerRegistry.getCellHandler(this.getItemType(), ExcelType.STRING);
        for (int i = 0; i < valueList.size(); i++) {
            E item = valueList.get(i);
            Row row = rowList.get(i);
            cellHandler.write(new DefaultRowContext(rowContext.getSheetContext(), rowContext.getRowData(), Collections.singletonList(row), 1), columnIndex, excelFieldConfig, item);
        }
    }

    @Override
    public int getWriteRowspan(List<E> valueList) {
        return valueList.size();
    }

}
