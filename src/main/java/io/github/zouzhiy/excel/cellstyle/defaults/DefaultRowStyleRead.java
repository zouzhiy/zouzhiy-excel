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
package io.github.zouzhiy.excel.cellstyle.defaults;

import io.github.zouzhiy.excel.cellstyle.CellStyleRead;
import io.github.zouzhiy.excel.cellstyle.RowStyleRead;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.metadata.CellStyleResultSet;
import io.github.zouzhiy.excel.metadata.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.SheetParameter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.util.Collections;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultRowStyleRead implements RowStyleRead {

    @Override
    public CellStyle readTitle(SheetContext sheetContext) {
        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        Integer titleRowIndex = sheetParameter.getTitleRowIndex();

        Row row = sheetContext.getRow(titleRowIndex);
        Integer titleColumnIndex = sheetParameter.getTitleColumnStartIndex();
        CellStyleRead cellStyleRead = DefaultCellStyleRead.getInstance();

        return cellStyleRead.read(sheetContext, Collections.singletonList(row), titleColumnIndex, titleColumnIndex);
    }

    @Override
    public CellStyleResultSet readHead(SheetContext sheetContext) {
        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        return this.read(sheetContext, sheetParameter.getHeadRowStartIndex(), sheetParameter.getHeadColumnStartIndex());
    }

    @Override
    public CellStyleResultSet readData(SheetContext sheetContext) {
        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        return this.read(sheetContext, sheetParameter.getDataRowStartIndex(), sheetParameter.getDataColumnStartIndex());
    }


    private CellStyleResultSet read(SheetContext sheetContext, int rowStartIndex, int columnStartIndex) {
        CellStyleResultSet cellStyleResultSet = CellStyleResultSet.empty();

        int maxRowspan = sheetContext.getMaxRowspan(rowStartIndex);

        List<Row> rowList = sheetContext.getRowList(rowStartIndex, rowStartIndex + maxRowspan - 1);

        int curColumnIndex = columnStartIndex;
        ExcelClassConfig excelClassConfig = sheetContext.getExcelClassConfig();
        List<ExcelFieldConfig> itemList = excelClassConfig.getItemList();
        CellStyleRead cellStyleRead = DefaultCellStyleRead.getInstance();
        for (ExcelFieldConfig excelFieldConfig : itemList) {
            int colspan = excelFieldConfig.getColspan();
            CellStyle cellStyle = cellStyleRead.read(sheetContext, rowList, curColumnIndex, curColumnIndex + colspan - 1);
            if (cellStyle != null) {
                cellStyleResultSet.cellStyleResult(excelFieldConfig, cellStyle);
            }
            curColumnIndex += colspan;
        }

        return cellStyleResultSet;
    }
}
