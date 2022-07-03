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
package io.github.zouzhiy.excel.handler.bigdecimal;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.AbstractCellHandler;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class BigDecimalBooleanHandler extends AbstractCellHandler<BigDecimal> {

    private final BigDecimal FALSE_VALUE = BigDecimal.ZERO;
    private final BigDecimal TRUE_VALUE = BigDecimal.ONE;

    @Override
    protected BigDecimal getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        Boolean value = firstCellResult.getBooleanValue();
        return value ? TRUE_VALUE : FALSE_VALUE;
    }

    @Override
    protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, BigDecimal value) {
        cell.setCellValue(value.compareTo(TRUE_VALUE) == 0);
    }

    @Override
    public ExcelType getExcelType() {
        return ExcelType.BOOLEAN;
    }
}
