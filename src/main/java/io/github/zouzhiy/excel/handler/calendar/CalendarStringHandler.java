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
package io.github.zouzhiy.excel.handler.calendar;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.handler.AbstractWriteStringCellHandler;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import io.github.zouzhiy.excel.utils.ExcelDateUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class CalendarStringHandler extends AbstractWriteStringCellHandler<Calendar> {

    private final Object lock = new Object();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    @Override
    protected Calendar getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        String value = firstCellResult.getStringValue();
        LocalDateTime localDateTime = ExcelDateUtils.parseDateTime(value, this.getJavaFormat(excelFieldConfig));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        return calendar;
    }

    @Override
    protected String format(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Calendar value) {
        String javaFormat = this.getJavaFormat(excelFieldConfig);
        if (javaFormat.length() > 0) {
            synchronized (lock) {
                simpleDateFormat.applyPattern(javaFormat);
                return simpleDateFormat.format(value.getTime());
            }
        } else {
            return value.toString();
        }
    }

    @Override
    public String getDefaultJavaFormat() {
        return "yyyy-MM-dd HH:mm:ss";
    }

}
