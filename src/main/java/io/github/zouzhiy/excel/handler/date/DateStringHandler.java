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
package io.github.zouzhiy.excel.handler.date;

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
import java.util.Date;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DateStringHandler extends AbstractWriteStringCellHandler<Date> {

    private final Object lock = new Object();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    @Override
    protected Date getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        String value = firstCellResult.getStringValue();
        LocalDateTime localDateTime = ExcelDateUtils.parseDateTime(value, this.getJavaFormat(excelFieldConfig));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        return calendar.getTime();
    }

    @Override
    protected String format(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Date value) {
        String javaFormat = this.getJavaFormat(excelFieldConfig);
        if (javaFormat.length() > 0) {
            synchronized (lock) {
                simpleDateFormat.applyPattern(javaFormat);
                return simpleDateFormat.format(value);
            }
        } else {
            return value.toString();
        }
    }

    @Override
    public String getDefaultJavaFormat() {
        return "yyyy-MM-dd";
    }

}
