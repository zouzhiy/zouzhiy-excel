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
import io.github.zouzhiy.excel.exceptions.ExcelException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
@ToString
@EqualsAndHashCode
public class CellResultSet {

    /**
     * 第一层List<同一个row不同col>
     */
    @Getter
    List<List<CellResult>> cellResultListList;

    public static CellResultSet none() {
        CellResultSet cellResultSet = new CellResultSet();
        cellResultSet.cellResultListList = Collections.emptyList();
        return cellResultSet;
    }

    public static CellResultSet newInstance(List<List<CellResult>> cellResultListList) {
        CellResultSet.validated(cellResultListList);
        CellResultSet cellResultSet = new CellResultSet();
        cellResultSet.cellResultListList = cellResultListList;
        return cellResultSet;
    }

    public static CellResultSet firstCellResult(CellResult cellResult) {
        CellResultSet cellResultSet = new CellResultSet();
        List<List<CellResult>> cellResultListList = new ArrayList<>(1);
        List<CellResult> cellResultList = new ArrayList<>(1);
        cellResultList.add(cellResult);
        cellResultListList.add(cellResultList);
        cellResultSet.cellResultListList = cellResultListList;

        return cellResultSet;
    }

    public ExcelType getExcelType() {
        return this.getFirstCellResult().getExcelType();
    }

    public boolean isNone() {
        if (cellResultListList == null || cellResultListList.isEmpty()) {
            return true;
        }
        for (List<CellResult> cellResultList : cellResultListList) {
            if (cellResultList != null && !cellResultList.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public CellResult getFirstCellResult() {
        if (cellResultListList == null || cellResultListList.isEmpty()) {
            throw new ExcelException("cellResultListList 空");
        }
        List<CellResult> cellResultList = cellResultListList.get(0);
        if (cellResultList == null || cellResultList.isEmpty()) {
            throw new ExcelException("cellResultListList 空");
        }

        return cellResultList.get(0);
    }

    /**
     * 每一个row的col要相等
     */
    private static void validated(List<List<CellResult>> cellResultListList) {
        Integer preSize = null;
        for (List<CellResult> cellResultList : cellResultListList) {
            int size = cellResultList.size();
            if (preSize == null) {
                preSize = size;
            } else if (preSize != size) {
                throw new ExcelException("cellResultListList 数据不符合预期");
            }
        }
    }

}
