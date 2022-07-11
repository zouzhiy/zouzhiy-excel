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
package io.github.zouzhiy.excel.metadata.result;

import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.metadata.CellSpan;
import io.github.zouzhiy.excel.utils.ExcelDateParseUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
@ToString
@EqualsAndHashCode
public class CellResult {

    private final static BigDecimal TRUE_VALUE_BIG_DECIMAL = BigDecimal.ONE;
    private final static BigDecimal FALSE_VALUE_BIG_DECIMAL = BigDecimal.ZERO;

    @Getter
    private final Cell cell;

    @Getter
    private final Integer rowIndex;

    @Getter
    private final Integer columnIndex;

    @Getter
    private final Integer rowspan;

    @Getter
    private final Integer colspan;

    @Getter
    private final ExcelType excelType;

    private String stringValue;

    private boolean stringValueFlag;

    private BigDecimal numberValue;

    private boolean numberValueFlag;

    private Boolean booleanValue;

    private boolean booleanValueFlag;

    private LocalDateTime dateValue;

    private boolean dateValueFlag;

    public CellResult(Cell cell, CellSpan cellSpan, ExcelType excelType
            , String stringValue, boolean stringValueFlag
            , BigDecimal numberValue, boolean numberValueFlag
            , Boolean booleanValue, boolean booleanValueFlag
            , LocalDateTime dateValue, boolean dateValueFlag) {
        this.cell = cell;
        this.rowIndex = cell == null ? -1 : cell.getRowIndex();
        this.columnIndex = cell == null ? -1 : cell.getColumnIndex();
        this.rowspan = cellSpan.getRowspan();
        this.colspan = cellSpan.getColspan();
        this.excelType = excelType;
        this.stringValue = stringValue;
        this.stringValueFlag = stringValueFlag;
        this.numberValue = numberValue;
        this.numberValueFlag = numberValueFlag;
        this.booleanValue = booleanValue;
        this.booleanValueFlag = booleanValueFlag;
        this.dateValue = dateValue;
        this.dateValueFlag = dateValueFlag;
    }

    public static CellResult none() {
        return new CellResult(null, CellSpan.NONE_CELL_SPAN, ExcelType.NONE
                , null, true
                , null, true
                , null, true
                , null, true);
    }

    public static CellResult blank(Cell cell, CellSpan cellSpan) {
        return new CellResult(cell, cellSpan, ExcelType.BLANK
                , null, true
                , null, true
                , null, true
                , null, true);

    }

    public static CellResult valueOf(Cell cell, CellSpan cellSpan, double numberValue) {
        return new CellResult(cell, cellSpan, ExcelType.NUMERIC
                , null, false
                , BigDecimal.valueOf(numberValue), true
                , null, false
                , null, false);
    }

    public static CellResult valueOf(Cell cell, CellSpan cellSpan, boolean booleanValue) {
        return new CellResult(cell, cellSpan, ExcelType.BOOLEAN
                , null, false
                , null, false
                , booleanValue, true
                , null, false);
    }

    public static CellResult valueOf(Cell cell, CellSpan cellSpan, String stringValue) {
        return new CellResult(cell, cellSpan, ExcelType.STRING
                , stringValue, true
                , null, false
                , null, false
                , null, false);
    }

    /**
     * 空数据
     */
    public boolean isBlank() {
        return excelType.equals(ExcelType.BLANK);
    }

    /**
     * 无效数据。一般属于合并单元格，但是不是第一个单元格的数据。即此数据无效
     */
    public boolean isNone() {
        return excelType.equals(ExcelType.NONE);
    }


    public String getStringValue() {
        if (!stringValueFlag) {
            stringValue = this.recalculateStringValue();
        }
        return stringValue;
    }

    public BigDecimal getNumberValue() {
        if (!numberValueFlag) {
            numberValue = this.recalculateNumberValue();
        }
        return numberValue;
    }


    public Boolean getBooleanValue() {
        if (!booleanValueFlag) {
            booleanValue = this.recalculateBooleanValue();
        }
        return booleanValue;
    }

    public LocalDateTime getDateValue() {
        if (!dateValueFlag) {
            dateValue = this.recalculateDateValue();
        }
        return dateValue;
    }

    private String recalculateStringValue() {
        String str;
        switch (excelType) {
            case NUMERIC:
                str = numberValue.toString();
                break;
            case BOOLEAN:
                str = Boolean.toString(booleanValue);
                break;
            default:
                str = null;
                break;
        }
        stringValueFlag = true;
        return str;
    }

    private BigDecimal recalculateNumberValue() {
        BigDecimal bigDecimal;
        switch (excelType) {
            case STRING:
                bigDecimal = stringValue.trim().length() == 0 ? null : new BigDecimal(stringValue);
                break;
            case BOOLEAN:
                bigDecimal = booleanValue ? TRUE_VALUE_BIG_DECIMAL : FALSE_VALUE_BIG_DECIMAL;
                break;
            default:
                bigDecimal = null;
                break;
        }
        numberValueFlag = true;
        return bigDecimal;
    }

    private Boolean recalculateBooleanValue() {
        Boolean booleanValue;
        switch (excelType) {
            case NUMERIC:
                booleanValue = TRUE_VALUE_BIG_DECIMAL.compareTo(numberValue) == 0;
                break;
            case STRING:
                booleanValue = Boolean.valueOf(stringValue);
                break;
            default:
                booleanValue = null;
                break;
        }
        booleanValueFlag = true;
        return booleanValue;
    }

    private LocalDateTime recalculateDateValue() {
        LocalDateTime localDateTime;
        switch (excelType) {
            case NUMERIC:
                localDateTime = cell.getLocalDateTimeCellValue();
                break;
            case STRING:
                localDateTime = ExcelDateParseUtils.parseDateTime(stringValue);
                break;
            default:
                localDateTime = null;
                break;
        }
        dateValueFlag = true;
        return localDateTime;
    }
}
