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

import io.github.zouzhiy.excel.cellstyle.RowStyleRead;
import io.github.zouzhiy.excel.cellstyle.SheetCellStyleRead;
import io.github.zouzhiy.excel.cellstyle.registry.RowStyleReadRegistry;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.metadata.CellStyleResultSet;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultSheetCellStyleRead implements SheetCellStyleRead {

    private final SheetContext sheetContext;

    public DefaultSheetCellStyleRead(SheetContext sheetContext) {
        this.sheetContext = sheetContext;
    }

    @Override
    public SheetContext getSheetContext() {
        return sheetContext;
    }

    @Override
    public void read() {
        SheetContext sheetContext = this.getSheetContext();

        Integer titleRowIndex = this.sheetContext.getSheetParameter().getTitleRowIndex();
        if (titleRowIndex == -1) {
            return;
        }
        RowStyleRead rowStyleRead = this.getRowStyleRead();

        CellStyle titleCellStyle = rowStyleRead.readTitle(sheetContext);
        sheetContext.putTitleCellStyle(sheetContext.getExcelClassConfig(), titleCellStyle);

        CellStyleResultSet headCellStyleResultSet = rowStyleRead.readHead(sheetContext);
        sheetContext.putHeadCellStyle(headCellStyleResultSet);

        CellStyleResultSet dataCellStyleResultSet = rowStyleRead.readData(sheetContext);
        sheetContext.putDataCellStyle(dataCellStyleResultSet);

    }

    private RowStyleRead getRowStyleRead() {
        Class<? extends RowStyleRead> rowStyleReadClazz = this.sheetContext.getExcelClassConfig().getCellStyleRead();
        if (rowStyleReadClazz == null) {
            rowStyleReadClazz = RowStyleReadRegistry.DEFAULT_ROW_STYLE_READ_CLASS;
        }
        return this.getSheetContext().getConfiguration().getRowStyleReadRegistry().getMappingRowStyleRead(rowStyleReadClazz);
    }

}
