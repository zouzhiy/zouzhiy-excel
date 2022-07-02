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
package io.github.zouzhiy.excel.metadata;

import io.github.zouzhiy.excel.enums.ExcelType;
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
@Getter
@ToString
@EqualsAndHashCode
public class CellResult {

    private Cell cell;

    private Integer rowIndex;

    private Integer columnIndex;

    private Integer rowspan;

    private Integer colspan;

    private ExcelType excelType;

    private BigDecimal numberValue;

    private String stringValue;

    private Boolean booleanValue;

    private LocalDateTime dateValue;


    private CellResult() {
    }

    public static CellResult none() {
        CellResult cellResult = new CellResult();
        cellResult.rowIndex = -1;
        cellResult.columnIndex = -1;
        cellResult.rowspan = -1;
        cellResult.colspan = -1;
        cellResult.excelType = ExcelType.NONE;

        return cellResult;
    }

    public static CellResult blank(Cell cell, CellSpan cellSpan) {
        CellResult cellResult = new CellResult();
        cellResult.setCell(cell, cellSpan);
        cellResult.excelType = ExcelType.BLANK;

        return cellResult;
    }

    public static CellResult numberValue(Cell cell, CellSpan cellSpan, BigDecimal value) {
        CellResult cellResult = new CellResult();
        cellResult.setCell(cell, cellSpan);
        cellResult.numberValue = value;
        cellResult.excelType = ExcelType.NUMERIC;
        return cellResult;
    }

    public static CellResult stringValue(Cell cell, CellSpan cellSpan, String value) {
        CellResult cellResult = new CellResult();
        cellResult.setCell(cell, cellSpan);
        cellResult.stringValue = value;
        cellResult.excelType = ExcelType.STRING;
        return cellResult;
    }

    public static CellResult booleanValue(Cell cell, CellSpan cellSpan, Boolean value) {
        CellResult cellResult = new CellResult();
        cellResult.setCell(cell, cellSpan);
        cellResult.booleanValue = value;
        cellResult.excelType = ExcelType.BOOLEAN;
        return cellResult;
    }

    public static CellResult dataValue(Cell cell, CellSpan cellSpan, LocalDateTime value) {
        CellResult cellResult = new CellResult();
        cellResult.setCell(cell, cellSpan);
        cellResult.dateValue = value;
        cellResult.excelType = ExcelType.DATE;
        return cellResult;
    }

    private void setCell(Cell cell, CellSpan cellSpan) {
        this.cell = cell;
        this.rowIndex = cell.getRowIndex();
        this.columnIndex = cell.getColumnIndex();
        this.rowspan = cellSpan.getRowspan();
        this.colspan = cellSpan.getColspan();
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


}
