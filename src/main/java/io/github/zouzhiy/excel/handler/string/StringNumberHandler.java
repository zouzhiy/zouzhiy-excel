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
import io.github.zouzhiy.excel.metadata.CellResult;
import io.github.zouzhiy.excel.metadata.ExcelFieldConfig;
import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class StringNumberHandler extends AbstractCellHandler<String> {

    private final Map<String, DecimalFormat> decimalFormatMap = new ConcurrentHashMap<>(16);

    @Override
    protected String getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        BigDecimal numberValue = firstCellResult.getNumberValue();
        String javaFormat = this.getJavaFormat(excelFieldConfig);
        if (javaFormat.length() > 0) {
            DecimalFormat decimalFormat = decimalFormatMap.computeIfAbsent(javaFormat, DecimalFormat::new);
            return decimalFormat.format(numberValue);
        } else {
            return numberValue.toString();
        }
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
    public String getDefaultJavaFormat() {
        return "0.00";
    }

    @Override
    public String getDefaultExcelFormat() {
        return "0.00";
    }
}
