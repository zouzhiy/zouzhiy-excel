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

import io.github.zouzhiy.excel.exceptions.ExcelException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 合并单元格跨行数，跨列数
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CellSpan {

    public final static CellSpan DEFAULT_CELL_SPAN = new CellSpan(1, 1) {
        @Override
        public void setRowspan(int rowspan) {
            throw new ExcelException("不支持修改");
        }

        @Override
        public void setColspan(int colspan) {
            throw new ExcelException("不支持修改");
        }
    };

    public final static CellSpan NONE_CELL_SPAN = CellSpan.newInstance(-1, -1, -1, -1);

    /**
     * 横跨的行数
     */
    private int rowspan;

    /**
     * 横跨的列数
     */
    private int colspan;


    private CellSpan(int rowspan, int colspan) {
        this.rowspan = rowspan;
        this.colspan = colspan;
    }

    /**
     * 直接创建
     *
     * @param rowspan 纵跨的行数
     * @param colspan 横跨的列数
     * @return 对象
     */
    public static CellSpan newInstance(int rowspan, int colspan) {
        return new CellSpan(rowspan, colspan);
    }

    /**
     * 根据边界计算
     *
     * @param firstRow 起始行
     * @param lastRow  终止行
     * @param firstCol 起始列
     * @param lastCol  终止列
     * @return 合并单元格跨行数，跨列数
     */
    public static CellSpan newInstance(int firstRow, int lastRow, int firstCol, int lastCol) {
        return new CellSpan(lastRow - firstRow + 1, lastCol - firstCol + 1);
    }
}
