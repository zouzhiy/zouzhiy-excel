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
     * 满足两个条件，才返回true
     * <pre>
     *   1、属于合并单元格
     *   2、不是合并单元的左上角第一个单元格
     * </pre>
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
     * 判断该区域是否有合并单元格
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
