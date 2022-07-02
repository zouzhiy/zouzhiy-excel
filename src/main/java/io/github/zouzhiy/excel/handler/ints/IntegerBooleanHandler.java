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
package io.github.zouzhiy.excel.handler.ints;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.AbstractCellHandler;
import io.github.zouzhiy.excel.metadata.CellResult;
import io.github.zouzhiy.excel.metadata.ExcelFieldConfig;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class IntegerBooleanHandler extends AbstractCellHandler<Integer> {

    private final static Integer FALSE_VALUE = 0;
    private final static Integer TRUE_VALUE = 1;

    @Override
    protected Integer getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        Boolean value = firstCellResult.getBooleanValue();
        return value ? TRUE_VALUE : FALSE_VALUE;
    }

    @Override
    protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, Integer value) {
        cell.setCellValue(value.compareTo(TRUE_VALUE) == 0);
    }

    @Override
    public ExcelType getExcelType() {
        return ExcelType.BOOLEAN;
    }
}
