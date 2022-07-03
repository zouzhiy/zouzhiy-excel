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
package io.github.zouzhiy.excel.read.defaults;

import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;
import io.github.zouzhiy.excel.read.CellDataRead;
import io.github.zouzhiy.excel.read.RowHeadRead;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultRowHeadRead implements RowHeadRead {

    @Override
    public List<CellResultSet> read(SheetContext sheetContext) {
        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        Integer headRowIndex = sheetParameter.getHeadRowStartIndex();
        int headColumnIndex = sheetParameter.getHeadColumnStartIndex();

        Row row = this.getRow(sheetContext, headRowIndex);
        short lastCellNum = row.getLastCellNum();

        Integer headRowStartIndex = sheetParameter.getHeadRowStartIndex();
        int maxRowspan = sheetContext.getMaxRowspan(headRowIndex);
        List<Row> rowList = sheetContext.getRowList(headRowIndex, headRowStartIndex + maxRowspan - 1);

        CellDataRead cellDataRead = DefaultCellDataRead.getInstance();
        List<CellResultSet> headList = new ArrayList<>();
        for (int i = headColumnIndex; i < lastCellNum; i++) {
            CellResultSet cellResultSet = cellDataRead.read(sheetContext, rowList, i, i);
            headList.add(cellResultSet);
        }
        return headList;
    }

    private Row getRow(SheetContext sheetContext, int rowIndex) {
        return sheetContext.getSheet().getRow(rowIndex);
    }

}
