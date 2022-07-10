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
package io.github.zouzhiy.excel.handler.timestamp;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.utils.ExcelDateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class TimestampStringHandler extends AbstractTimestampCellHandler {

    private final Map<String, DateTimeFormatter> dateTimeFormatterMap = new ConcurrentHashMap<>(16);


    @Override
    public ExcelType getExcelType() {
        return ExcelType.STRING;
    }

    @Override
    public String getDefaultJavaFormat() {
        return "yyyy-MM-dd HH:mm:ss";
    }

    @Override
    protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, Timestamp value) {
        String javaFormat = this.getJavaFormat(excelFieldConfig);
        String strValue = ExcelDateFormatUtils.format(value, javaFormat);
        cell.setCellValue(strValue);
    }
}
