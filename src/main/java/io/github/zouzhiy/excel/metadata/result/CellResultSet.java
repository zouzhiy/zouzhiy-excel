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
 * 多个单元格值读取结果
 * 为跨行跨列数据读取提供支持
 * * @author zouzhiy
 *
 * @since 2022/7/2
 */
@ToString
@EqualsAndHashCode
public class CellResultSet {

    /**
     * List<每一行的值List<同一行下不同列的值>>
     * row1col1, row2col2
     * row2col1, row2cole2
     * <p>
     * 每一行的col数量必须一致
     */
    @Getter
    List<List<CellResult>> cellResultListList;

    /**
     * 空值
     *
     * @return list长度为0
     */
    public static CellResultSet none() {
        CellResultSet cellResultSet = new CellResultSet();
        cellResultSet.cellResultListList = Collections.emptyList();
        return cellResultSet;
    }

    /**
     * 直接创建新对象
     *
     * @param cellResultListList 单元格结果二维列表
     * @return 多个单元格值读取结果
     */
    public static CellResultSet newInstance(List<List<CellResult>> cellResultListList) {
        CellResultSet.validated(cellResultListList);
        CellResultSet cellResultSet = new CellResultSet();
        cellResultSet.cellResultListList = cellResultListList;
        return cellResultSet;
    }

    /**
     * 一般情况下的结果，不存在合并单元格。则只有一行一列
     *
     * @param cellResult 单个单元格值
     * @return 包装后的多个结果值
     */
    public static CellResultSet firstCellResult(CellResult cellResult) {
        CellResultSet cellResultSet = new CellResultSet();
        List<List<CellResult>> cellResultListList = new ArrayList<>(1);
        List<CellResult> cellResultList = new ArrayList<>(1);
        cellResultList.add(cellResult);
        cellResultListList.add(cellResultList);
        cellResultSet.cellResultListList = cellResultListList;

        return cellResultSet;
    }


    /**
     * 校验结果集是否合法
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

    /**
     * 返回结果集的单元格类型。
     * 以第一个单元格类型为准。
     *
     * @return 单元格类型
     */
    public ExcelType getExcelType() {
        return this.getFirstCellResult().getExcelType();
    }

    /**
     * 结果为空 对应的是单元格为null
     *
     * @return 是否为空
     */
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

    /**
     * 获取结果集的第一个单元格值。
     * 若数据不跨行跨列，则此结果即为实际的读取值
     *
     * @return 单元格值
     */
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
     * 获取结果集中的第一个单元格行下标
     *
     * @return 第一个单元格行下标
     */
    public int getFirstRowIndex() {
        if (cellResultListList == null || cellResultListList.isEmpty()) {
            return -1;
        }
        List<CellResult> cellResultList = cellResultListList.get(0);
        if (cellResultList == null || cellResultList.isEmpty()) {
            return -1;
        }

        return cellResultList.get(0).getRowIndex();
    }

    /**
     * 获取结果集中的第一个单元格列下标
     *
     * @return 第一个单元格列下标
     */
    public int getFirstColIndex() {
        if (cellResultListList == null || cellResultListList.isEmpty()) {
            return -1;
        }
        List<CellResult> cellResultList = cellResultListList.get(0);
        if (cellResultList == null || cellResultList.isEmpty()) {
            return -1;
        }

        return cellResultList.get(0).getColumnIndex();
    }

}
