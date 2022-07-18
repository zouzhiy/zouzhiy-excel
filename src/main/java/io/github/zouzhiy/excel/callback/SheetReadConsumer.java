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
package io.github.zouzhiy.excel.callback;

import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;

import java.util.List;

/**
 * sheet读取回调
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
public interface SheetReadConsumer<T> {

    /**
     * 读取前
     *
     * @param sheetContext 上下文
     */
    default void beforeRead(SheetContext sheetContext) {
    }

    /**
     * 读取标题后
     *
     * @param sheetContext       上下文
     * @param titleCellResultSet 读取到的标题值
     */
    default void afterReadTitle(SheetContext sheetContext, CellResultSet titleCellResultSet) {
    }

    /**
     * 读取表头后
     *
     * @param sheetContext          上下文
     * @param headCellResultSetList 读取到的表头值
     */
    default void afterReadHead(SheetContext sheetContext, List<CellResultSet> headCellResultSetList) {
    }

    /**
     * 读取数据后
     *
     * @param sheetContext 上下文
     * @param dataList     读取到的数据列表
     */
    default void afterReadData(SheetContext sheetContext, List<T> dataList) {
    }

    /**
     * 读取表尾后
     *
     * @param sheetContext          上下文
     * @param footCellResultSetList 读取到的表尾值
     */
    default void afterReadFoot(SheetContext sheetContext, List<CellResultSet> footCellResultSetList) {
    }

    /**
     * 读取 Sheeth 后
     *
     * @param sheetContext          上下文
     * @param titleCellResultSet    标题值
     * @param headCellResultSetList 表头值
     * @param dataList              数据列表
     * @param footCellResultSetList 表尾值
     */
    default void afterRead(SheetContext sheetContext, CellResultSet titleCellResultSet, List<CellResultSet> headCellResultSetList, List<T> dataList, List<CellResultSet> footCellResultSetList) {
    }
}
