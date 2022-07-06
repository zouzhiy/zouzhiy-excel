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
package io.github.zouzhiy.excel.context.defualts;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultRowContext implements RowContext {

    private final SheetContext sheetContext;

    private final Object rowData;

    private final List<Row> rowList;

    private final int rowspan;


    public DefaultRowContext(SheetContext sheetContext, Object rowData, List<Row> rowList, int rowspan) {
        this.sheetContext = sheetContext;
        this.rowData = rowData;
        this.rowList = rowList;
        this.rowspan = rowspan;
    }

    @Override
    public SheetContext getSheetContext() {
        return sheetContext;
    }

    @Override
    public Object getRowData() {
        return rowData;
    }

    @Override
    public List<Row> getRowList() {
        return rowList;
    }

    @Override
    public int getRowspan() {
        return rowspan;
    }


}
