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
import io.github.zouzhiy.excel.metadata.CellResultSet;
import io.github.zouzhiy.excel.metadata.SheetParameter;
import io.github.zouzhiy.excel.read.CellDataRead;
import io.github.zouzhiy.excel.read.RowTitleRead;
import org.apache.poi.ss.usermodel.Row;

import java.util.Collections;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultRowTitleRead implements RowTitleRead {

    @Override
    public CellResultSet read(SheetContext sheetContext) {
        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        Integer titleRowIndex = sheetParameter.getTitleRowIndex();

        Row row = this.getRow(sheetContext, titleRowIndex);
        Integer titleColumnIndex = sheetParameter.getTitleColumnStartIndex();
        CellDataRead cellDataRead = DefaultCellDataRead.getInstance();
        return cellDataRead.read(sheetContext, Collections.singletonList(row), titleColumnIndex, titleColumnIndex);
    }

    private Row getRow(SheetContext sheetContext, int rowIndex) {
        return sheetContext.getSheet().getRow(rowIndex);
    }

}
