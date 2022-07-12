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
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.CellHandler;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zouzhiy
 * @since 2022/7/4
 */
public abstract class AbstractListJoinHandler<E> extends AbstractListHandler<E> {

    @Override
    public final ExcelType getExcelType() {
        return ExcelType.STRING;
    }

    @Override
    public List<E> read(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResultSet cellResultSet) {
        if (cellResultSet == null || cellResultSet.isNone()) {
            return null;
        }
        CellResult firstCellResult = cellResultSet.getFirstCellResult();
        if (firstCellResult.isNone()) {
            return null;
        } else if (firstCellResult.isBlank()) {
            return new ArrayList<>();
        }
        String[] values = firstCellResult.getStringValue().split(this.getDelimiter(), -1);
        return Arrays.stream(values).map(this::parse).collect(Collectors.toList());
    }

    @Override
    public void write(RowContext rowContext, Integer columnIndex, ExcelFieldConfig excelFieldConfig, List<E> valueList) {
        String value;
        if (valueList == null) {
            return;
        } else if (valueList.isEmpty()) {
            value = null;
        } else {
            value = valueList.stream().map(this::format).collect(Collectors.joining(this.getDelimiter()));
        }

        CellHandler<String> cellHandler = rowContext.getSheetContext()
                .getConfiguration()
                .getCellHandlerRegistry()
                .getCellHandler(String.class, this.getExcelType());
        cellHandler.write(rowContext, columnIndex, excelFieldConfig, value);
    }

    protected abstract String format(E item);

    protected abstract E parse(String text);

    protected String getDelimiter() {
        return ";";
    }


}

