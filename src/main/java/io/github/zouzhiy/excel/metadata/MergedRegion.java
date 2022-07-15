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

import lombok.Getter;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sheet的合并单元格数据
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
public class MergedRegion {

    @Getter
    private final Map<Integer, List<CellRangeAddress>> cellRangeAddressListMap;

    public MergedRegion(List<CellRangeAddress> cellRangeAddressList) {
        cellRangeAddressListMap = new HashMap<>();
        for (CellRangeAddress cellRangeAddress : cellRangeAddressList) {
            int firstRow = cellRangeAddress.getFirstRow();
            int firstColumn = cellRangeAddress.getFirstColumn();
            Integer key = firstRow + firstColumn;
            List<CellRangeAddress> valueList = cellRangeAddressListMap.computeIfAbsent(key, integer -> new ArrayList<>(5));
            valueList.add(cellRangeAddress);
        }
    }

    /**
     * 若行列是合并单元格左上角位置，则返回单元格信息。否则返回null
     *
     * @param firstRow    单元格起始行
     * @param firstColumn 单元格起始列
     * @return 不符合条件则返回null
     */
    public CellRangeAddress getCellRangeAddress(int firstRow, int firstColumn) {
        Integer key = firstRow + firstColumn;
        List<CellRangeAddress> cellRangeAddressList = cellRangeAddressListMap.get(key);
        if (cellRangeAddressList == null || cellRangeAddressList.isEmpty()) {
            return null;
        }

        for (CellRangeAddress cellRangeAddress : cellRangeAddressList) {
            int row = cellRangeAddress.getFirstRow();
            int column = cellRangeAddress.getFirstColumn();

            if (firstRow == row && firstColumn == column) {
                return cellRangeAddress;
            }
        }

        return null;
    }

    /**
     * 若行列是合并单元格左上角位置，则返回合并单元格占用的行数和列数
     *
     * @param firstRow    单元格起始行
     * @param firstColumn 单元格起始列
     * @return 符合条件则返回合并单元格合并列数和行数信息。否则返回默认的一行一列。{@link CellSpan#DEFAULT_CELL_SPAN}
     */
    public CellSpan getCellSpan(int firstRow, int firstColumn) {
        CellRangeAddress cellRangeAddress = this.getCellRangeAddress(firstRow, firstColumn);
        if (cellRangeAddress == null) {
            return CellSpan.DEFAULT_CELL_SPAN;
        }
        int lastRow = cellRangeAddress.getLastRow();
        int lastColumn = cellRangeAddress.getLastColumn();

        return CellSpan.newInstance(firstRow, lastRow, firstColumn, lastColumn);
    }

    /**
     * 判断单元格是否属于某个合并单元格范围内并且不是首个
     * 满足两个条件，才返回true
     * <pre>
     *   1、属于合并单元格
     *   2、不是合并单元的左上角第一个单元格
     * </pre>
     *
     * @param rowIndex    单元格行下标
     * @param columnIndex 单元格列下标
     * @return 判断结果
     */
    public boolean isInMergedRegionAndNotFirst(int rowIndex, int columnIndex) {
        for (List<CellRangeAddress> cellRangeAddressList : cellRangeAddressListMap.values()) {
            for (CellRangeAddress cellRangeAddress : cellRangeAddressList) {
                int firstRow = cellRangeAddress.getFirstRow();
                int lastRow = cellRangeAddress.getLastRow();
                int firstColumn = cellRangeAddress.getFirstColumn();
                int lastColumn = cellRangeAddress.getLastColumn();
                if (rowIndex > firstRow && rowIndex <= lastRow) {
                    if (columnIndex > firstColumn && columnIndex <= lastColumn) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断单元格区域是否存在合并单元格
     *
     * @param firstRow 起始行
     * @param lastRow  终止行
     * @param firstCol 起始列
     * @param lastCol  终止列
     * @return 存在则为True, 不存在返回False
     */
    public boolean unHasMergedRegion(int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress cellRangeAddress = this.getCellRangeAddress(firstRow, lastRow);
        if (cellRangeAddress != null) {
            return false;
        }
        for (List<CellRangeAddress> cellRangeAddressList : cellRangeAddressListMap.values()) {
            for (CellRangeAddress item : cellRangeAddressList) {
                if (firstRow <= item.getLastRow() && lastRow >= item.getFirstRow()) {
                    if (firstCol <= item.getLastColumn() && lastCol >= item.getFirstColumn()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
