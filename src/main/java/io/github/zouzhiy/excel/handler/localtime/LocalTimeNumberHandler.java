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
package io.github.zouzhiy.excel.handler.localtime;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.AbstractCellHandler;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class LocalTimeNumberHandler extends AbstractCellHandler<LocalTime> {

    @Override
    protected LocalTime getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        BigDecimal numberValue = firstCellResult.getNumberValue();
        return DateUtil.getLocalDateTime(numberValue.doubleValue()).toLocalTime();
    }

    @Override
    protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, LocalTime value) {
        LocalDateTime localDateTime = DateUtil.getLocalDateTime(0)
                .plusDays(1)
                .withHour(value.getHour())
                .withMinute(value.getMinute())
                .withSecond(value.getSecond())
                .withNano(value.getNano());
        cell.setCellValue(DateUtil.getExcelDate(localDateTime));
    }

    @Override
    public ExcelType getExcelType() {
        return ExcelType.NUMERIC;
    }
}
