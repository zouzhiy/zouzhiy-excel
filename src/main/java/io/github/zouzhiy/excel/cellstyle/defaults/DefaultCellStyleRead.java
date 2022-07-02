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
import io.github.zouzhiy.excel.context.SheetContext;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultCellStyleRead implements CellStyleRead {


    private final static DefaultCellStyleRead DEFAULT_CELL_STYLE_READ = new DefaultCellStyleRead();

    private DefaultCellStyleRead() {
    }

    public static DefaultCellStyleRead getInstance() {
        return DEFAULT_CELL_STYLE_READ;
    }

    @Override
    public CellStyle read(SheetContext sheetContext, List<Row> rowList, int firstCol, int lastCol) {
        if (rowList == null || rowList.isEmpty()) {
            return null;
        }
        Row row = rowList.get(0);
        if (row == null) {
            return null;
        }
        Cell cell = row.getCell(firstCol);
        if (cell == null) {
            return null;
        }
        return cell.getCellStyle();
    }
}
