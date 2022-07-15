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
 * 单个单元格值读取结果
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
@ToString
@EqualsAndHashCode
public class CellResult {

    private final static BigDecimal TRUE_VALUE_BIG_DECIMAL = BigDecimal.ONE;
    private final static BigDecimal FALSE_VALUE_BIG_DECIMAL = BigDecimal.ZERO;
    /**
     * 对应的单元格
     */
    @Getter
    private final Cell cell;
    /**
     * 所在行
     */
    @Getter
    private final Integer rowIndex;
    /**
     * 所在列
     */
    @Getter
    private final Integer columnIndex;
    /**
     * 纵向跨的行数。合并单元格时，值可能大于1
     */
    @Getter
    private final Integer rowspan;
    /**
     * 横向跨的列数。合并单元格时，值可能大于1
     */
    @Getter
    private final Integer colspan;
    /**
     * 单元格类型
     */
    @Getter
    private final ExcelType excelType;
    /**
     * 文本值
     */
    private String stringValue;
    /**
     * 是否已计算过stringValue
     * 当单元格类型不是{@link ExcelType#STRING}，需要转换
     */
    private boolean stringValueFlag;
    /**
     * 数值
     */
    private BigDecimal numberValue;
    /**
     * 是否已计算过numberValue
     * 当单元格类型不是{@link ExcelType#NUMERIC}，需要转换
     */
    private boolean numberValueFlag;
    /**
     * 布尔值
     */
    private Boolean booleanValue;
    /**
     * 是否已计算过booleanValue
     * 当单元格类型不是{@link ExcelType#BOOLEAN}，需要转换
     */
    private boolean booleanValueFlag;
    /**
     * 日期
     */
    private LocalDateTime dateValue;
    /**
     * 是否已计算过booleanValue
     * 日期可从numberValue或者StringValue转换而来
     */
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

    /**
     * 单元格为空
     *
     * @return NULL的CellResult
     */
    public static CellResult none() {
        return new CellResult(null, CellSpan.NONE_CELL_SPAN, ExcelType.NONE
                , null, true
                , null, true
                , null, true
                , null, true);
    }

    /**
     * {@link org.apache.poi.ss.usermodel.CellType#BLANK}
     * 单元格不为空，但是单元格值为空
     *
     * @param cell     单元格
     * @param cellSpan 合并信息
     * @return CellResult的值都为null
     */
    public static CellResult blank(Cell cell, CellSpan cellSpan) {
        return new CellResult(cell, cellSpan, ExcelType.BLANK
                , null, true
                , null, true
                , null, true
                , null, true);

    }

    /**
     * 数值型 {@link org.apache.poi.ss.usermodel.CellType#NUMERIC}
     *
     * @param cell        单元格
     * @param cellSpan    合并信息
     * @param numberValue 单元格值，为数值类型
     * @return 单元格值
     */
    public static CellResult valueOf(Cell cell, CellSpan cellSpan, double numberValue) {
        return new CellResult(cell, cellSpan, ExcelType.NUMERIC
                , null, false
                , BigDecimal.valueOf(numberValue), true
                , null, false
                , null, false);
    }

    /**
     * 布尔型 {@link org.apache.poi.ss.usermodel.CellType#BOOLEAN}
     *
     * @param cell         单元格
     * @param cellSpan     合并信息
     * @param booleanValue 单元格值，
     * @return 单元格值
     */
    public static CellResult valueOf(Cell cell, CellSpan cellSpan, boolean booleanValue) {
        return new CellResult(cell, cellSpan, ExcelType.BOOLEAN
                , null, false
                , null, false
                , booleanValue, true
                , null, false);
    }

    /**
     * 文本型 {@link org.apache.poi.ss.usermodel.CellType#BOOLEAN}
     *
     * @param cell        单元格
     * @param cellSpan    合并信息
     * @param stringValue 单元格值，
     * @return 单元格值
     */
    public static CellResult valueOf(Cell cell, CellSpan cellSpan, String stringValue) {
        return new CellResult(cell, cellSpan, ExcelType.STRING
                , stringValue, true
                , null, false
                , null, false
                , null, false);
    }

    /**
     * 空数据
     *
     * @return 是否为空值
     */
    public boolean isBlank() {
        return excelType.equals(ExcelType.BLANK);
    }

    /**
     * 单元格为空
     * 无效数据。一般属于合并单元格，但是不是第一个单元格的数据。即此数据无效
     *
     * @return 是否为null
     */
    public boolean isNone() {
        return excelType.equals(ExcelType.NONE);
    }

    /**
     * 获取字符型值。若非字符串型，则需转换为字符串
     *
     * @return 字符串结果
     */
    public String getStringValue() {
        if (!stringValueFlag) {
            stringValue = this.recalculateStringValue();
        }
        return stringValue;
    }

    /**
     * 若非数值型，则需转换
     *
     * @return 数值结果
     */
    public BigDecimal getNumberValue() {
        if (!numberValueFlag) {
            numberValue = this.recalculateNumberValue();
        }
        return numberValue;
    }

    /**
     * 若非布尔型，则需转换
     *
     * @return 布尔值结果
     */
    public Boolean getBooleanValue() {
        if (!booleanValueFlag) {
            booleanValue = this.recalculateBooleanValue();
        }
        return booleanValue;
    }

    /**
     * 时间类型。需要从单元格读取或者字符串转换而来
     *
     * @return 时间结果
     */
    public LocalDateTime getDateValue(String format) {
        if (!dateValueFlag) {
            dateValue = this.recalculateDateValue(format);
        }
        return dateValue;
    }

    /**
     * 字符串转换
     *
     * @return 结果
     */
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

    /**
     * 数值转换
     *
     * @return 结果
     */
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

    /**
     * 布尔值转换
     *
     * @return 结果
     */
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

    /**
     * 日期转换
     *
     * @param format 日期转换pattern,可以为null,为空字符串。外部配置传入
     * @return 结果
     */
    private LocalDateTime recalculateDateValue(String format) {
        LocalDateTime localDateTime;
        switch (excelType) {
            case NUMERIC:
                localDateTime = cell.getLocalDateTimeCellValue();
                break;
            case STRING:
                localDateTime = ExcelDateParseUtils.parseDateTime(stringValue, format);
                break;
            default:
                localDateTime = null;
                break;
        }
        dateValueFlag = true;
        return localDateTime;
    }
}
