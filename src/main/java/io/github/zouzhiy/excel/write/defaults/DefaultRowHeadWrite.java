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
package io.github.zouzhiy.excel.write.defaults;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.context.defualts.DefaultRowContext;
import io.github.zouzhiy.excel.handler.CellHandler;
import io.github.zouzhiy.excel.handler.head.HeadStringHandler;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.write.RowHeadWrite;
import org.apache.poi.ss.usermodel.Row;

import java.util.Collections;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultRowHeadWrite implements RowHeadWrite {

    @Override
    public int write(SheetContext sheetContext, List<?> dataList) {
        ExcelClassConfig excelClassConfig = sheetContext.getExcelClassConfig();
        List<ExcelFieldConfig> itemList = excelClassConfig.getItemList();

        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        int titleRowIndex = sheetParameter.getHeadRowStartIndex();
        int headColumnIndex = sheetParameter.getHeadColumnStartIndex();
        Row row = this.createRow(sheetContext, titleRowIndex);

        RowContext rowContext = new DefaultRowContext(sheetContext, dataList, Collections.singletonList(row), 1);
        CellHandler<String> headHandler = sheetContext.getConfiguration()
                .getCellHandlerRegistry()
                .getCellHandler(HeadStringHandler.class);

        int curColumnIndex = headColumnIndex;
        for (ExcelFieldConfig excelFieldConfig : itemList) {
            headHandler.write(rowContext, curColumnIndex, excelFieldConfig, excelFieldConfig.getTitle());
            curColumnIndex += excelFieldConfig.getColspan();
        }
        return 1;
    }


    private Row createRow(SheetContext sheetContext, int rowIndex) {
        return sheetContext.getSheet().createRow(rowIndex);
    }

}
