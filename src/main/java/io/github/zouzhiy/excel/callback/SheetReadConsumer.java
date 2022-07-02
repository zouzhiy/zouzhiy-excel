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
import io.github.zouzhiy.excel.metadata.CellResultSet;

import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public interface SheetReadConsumer<T> {

    void beforeRead(SheetContext sheetContext);

    void acceptReadTitle(SheetContext sheetContext, CellResultSet titleCellResultSet);

    void acceptReadHead(SheetContext sheetContext, List<CellResultSet> headCellResultSetList);

    void acceptReadData(SheetContext sheetContext, List<T> dataList);

    void acceptReadFoot(SheetContext sheetContext, List<CellResultSet> footCellResultSetList);

    void afterRead(SheetContext sheetContext, CellResultSet titleCellResultSet, List<CellResultSet> headCellResultSetList, List<T> dataList, List<CellResultSet> footCellResultSetList);
}
