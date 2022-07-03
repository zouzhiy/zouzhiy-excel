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
package io.github.zouzhiy.excel.handler.localdate;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.AbstractWriteStringCellHandler;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import io.github.zouzhiy.excel.utils.ExcelDateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class LocalDateStringHandler extends AbstractWriteStringCellHandler<LocalDate> {

    private final Map<String, DateTimeFormatter> dateTimeFormatterMap = new ConcurrentHashMap<>(16);

    @Override
    protected LocalDate getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        String value = firstCellResult.getStringValue();
        LocalDateTime localDateTime = ExcelDateUtils.parseDateTime(value, this.getJavaFormat(excelFieldConfig));
        return localDateTime.toLocalDate();
    }

    @Override
    protected String format(RowContext rowContext, ExcelFieldConfig excelFieldConfig, LocalDate value) {
        String javaFormat = this.getJavaFormat(excelFieldConfig);
        if (javaFormat.length() > 0) {
            DateTimeFormatter dateTimeFormatter = dateTimeFormatterMap.computeIfAbsent(javaFormat, DateTimeFormatter::ofPattern);
            return value.format(dateTimeFormatter);
        } else {
            return value.toString();
        }
    }

    @Override
    public ExcelType getExcelType() {
        return ExcelType.STRING;
    }

    @Override
    public String getDefaultJavaFormat() {
        return "yyyy-MM-dd";
    }

}
