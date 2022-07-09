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
package io.github.zouzhiy.excel.old.example.example5.handler;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.CellHandler;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;
import io.github.zouzhiy.excel.old.example.example5.matedata.Item;
import io.github.zouzhiy.excel.utils.RegionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/4
 */
public class ItemHandler implements CellHandler<Item> {


    @Override
    public Class<Item> getJavaType() {
        return Item.class;
    }

    @Override
    public ExcelType getExcelType() {
        return ExcelType.STRING;
    }

    @Override
    public Item read(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResultSet cellResultSet) {
        List<List<CellResult>> cellResultListList = cellResultSet.getCellResultListList();
        List<CellResult> cellResultList = cellResultListList.get(0);
        CellResult cellResultName = cellResultList.get(0);
        CellResult cellResultTel = cellResultList.get(1);

        return new Item(cellResultName.getStringValue(), cellResultTel.getStringValue());
    }

    @Override
    public void write(RowContext rowContext, Integer columnIndex, ExcelFieldConfig excelFieldConfig, Item value) {
        Row row = rowContext.getRowList().get(0);
        Cell cellName = row.createCell(columnIndex);
        Cell cellTel = row.createCell(columnIndex + 1);
        if (value != null) {
            cellName.setCellValue(value.getUsername());
            cellTel.setCellValue(value.getTel());
        }

        SheetContext sheetContext = rowContext.getSheetContext();
        CellStyle cellStyle = sheetContext.getDataCellStyle(excelFieldConfig, this.getDefaultExcelFormat());
        cellName.setCellStyle(cellStyle);
        cellTel.setCellStyle(cellStyle);

        int rowspan = rowContext.getRowspan();
        int rowIndex = row.getRowNum();
        RegionUtils.addMergedRegionIfPresent(sheetContext, cellStyle, rowIndex, rowIndex + rowspan - 1, columnIndex, columnIndex);
        RegionUtils.addMergedRegionIfPresent(sheetContext, cellStyle, rowIndex, rowIndex + rowspan - 1, columnIndex + 1, columnIndex + 1);
    }
}
