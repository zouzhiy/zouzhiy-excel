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

import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.utils.RegionUtils;
import io.github.zouzhiy.excel.write.RowTitleWrite;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultRowTitleWrite implements RowTitleWrite {

    @Override
    public int write(SheetContext sheetContext, List<?> dataList) {
        ExcelClassConfig excelClassConfig = sheetContext.getExcelClassConfig();
        List<ExcelFieldConfig> itemList = excelClassConfig.getItemList();

        SheetParameter sheetParameter = sheetContext.getSheetParameter();
        int titleRowIndex = sheetParameter.getTitleRowStartIndex();
        int titleColumnIndex = sheetParameter.getTitleColumnStartIndex();

        Row row = this.createRow(sheetContext, titleRowIndex);
        Cell cell = row.createCell(titleColumnIndex);

        CellStyle cellStyle = sheetContext.getTitleCellStyle(excelClassConfig);
        cell.setCellStyle(cellStyle);

        String title = sheetParameter.getTitle();
        cell.setCellValue(title);

        int colspan = itemList.stream().mapToInt(ExcelFieldConfig::getColspan).sum();
        RegionUtils.addMergedRegionIfPresent(sheetContext, cellStyle, titleRowIndex, titleRowIndex, titleColumnIndex, titleColumnIndex + colspan - 1);

        return 1;
    }


    private Row createRow(SheetContext sheetContext, int rowIndex) {
        return sheetContext.getSheet().createRow(rowIndex);
    }
}
