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
package io.github.zouzhiy.excel.handler.string;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.AbstractCellHandler;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import io.github.zouzhiy.excel.utils.ExcelNumberUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class StringNumberHandler extends AbstractCellHandler<String> {

    @Override
    protected String getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        BigDecimal bigDecimal = firstCellResult.getNumberValue();
        String javaFormat = this.getJavaFormat(excelFieldConfig);
        return ExcelNumberUtils.format(bigDecimal, javaFormat);
    }

    @Override
    protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, String value) {
        cell.setCellValue(new BigDecimal(value).doubleValue());
    }

    @Override
    public ExcelType getExcelType() {
        return ExcelType.NUMERIC;
    }


    @Override
    public String getDefaultExcelFormat() {
        return "0.00";
    }
}
