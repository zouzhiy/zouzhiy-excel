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
import org.apache.poi.ss.usermodel.Cell;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class StringDateHandler extends AbstractCellHandler<String> {

    private final Map<String, DateTimeFormatter> dateTimeFormatterMap = new ConcurrentHashMap<>(16);

    @Override
    protected String getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        LocalDateTime localDateTime = firstCellResult.getDateValue();
        String javaFormat = this.getJavaFormat(excelFieldConfig);
        DateTimeFormatter dateTimeFormatter = dateTimeFormatterMap.computeIfAbsent(javaFormat, DateTimeFormatter::ofPattern);
        return localDateTime.format(dateTimeFormatter);

    }

    @Override
    protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, String value) {
        String javaFormat = this.getJavaFormat(excelFieldConfig);
        DateTimeFormatter dateTimeFormatter = dateTimeFormatterMap.computeIfAbsent(javaFormat, DateTimeFormatter::ofPattern);
        LocalDateTime localDateTime = LocalDateTime.parse(value, dateTimeFormatter);
        cell.setCellValue(localDateTime);
    }

    @Override
    public ExcelType getExcelType() {
        return ExcelType.DATE;
    }

    @Override
    public String getDefaultJavaFormat() {
        return "yyyy-MM-dd HH:mm:ss";
    }

    @Override
    public String getDefaultExcelFormat() {
        return "yyyy-MM-dd HH:mm:ss";
    }
}
