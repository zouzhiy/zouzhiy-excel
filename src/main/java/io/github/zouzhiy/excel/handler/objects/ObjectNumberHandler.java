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
package io.github.zouzhiy.excel.handler.objects;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.handler.AbstractCellHandler;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zouzhiy
 * @since 2022/7/19
 */
public class ObjectNumberHandler extends AbstractCellHandler<Object> {

    @Override
    protected Object getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        Cell cell = firstCellResult.getCell();
        if (DateUtil.isCellDateFormatted(cell)) {
            return firstCellResult.getDateValue(this.getJavaFormat(excelFieldConfig));
        } else {
            return firstCellResult.getNumberValue();
        }
    }

    @Override
    protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, Object value) {
        if (value instanceof LocalDateTime) {
            cell.setCellValue((LocalDateTime) value);
        } else if (value instanceof LocalDate) {
            cell.setCellValue((LocalDate) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else if (value instanceof Calendar) {
            cell.setCellValue(((Calendar) value).getTime());
        } else if (value instanceof LocalTime) {
            LocalDateTime localDateTime = DateUtil.getLocalDateTime(0)
                    .plusDays(1)
                    .withHour(((LocalTime) value).getHour())
                    .withMinute(((LocalTime) value).getMinute())
                    .withSecond(((LocalTime) value).getSecond())
                    .withNano(((LocalTime) value).getNano());
            cell.setCellValue(localDateTime);
        } else if (value instanceof Boolean) {
            cell.setCellValue(((Boolean) value) ? BigDecimal.ONE.doubleValue() : BigDecimal.ZERO.doubleValue());
        } else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else {
            try {
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(value));
                cell.setCellValue(bigDecimal.doubleValue());

            } catch (NumberFormatException e) {
                throw new ExcelException("非数字格式，无法转化。%s", value);
            }
        }
    }

    @Override
    public ExcelType getExcelType() {
        return ExcelType.NUMERIC;
    }
}
