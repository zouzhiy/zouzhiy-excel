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
package io.github.zouzhiy.excel.old.type;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.CellHandler;
import io.github.zouzhiy.excel.handler.CellHandlerRegistry;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;
import io.github.zouzhiy.excel.utils.RegionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class ValueListStringHandler implements CellHandler<List<String>> {
    @Override
    @SuppressWarnings("InstantiatingObjectToGetClassObject")
    public Class<List<String>> getJavaType() {
        Class<?> arrayListClass = new ArrayList<String>().getClass();
        Class<?>[] interfaces = arrayListClass.getInterfaces();
        int index = 0;
        for (int i = 0; i < interfaces.length; i++) {
            if (interfaces[i].equals(List.class)) {
                index = i;
                break;
            }
        }
        //noinspection unchecked
        return (Class<List<String>>) interfaces[index];
    }

    @Override
    public ExcelType getExcelType() {
        return ExcelType.STRING;
    }

    @Override
    public List<String> read(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResultSet cellResultSet) {
        List<List<CellResult>> cellResultListList = cellResultSet.getCellResultListList();
        Class<String> javaType = String.class;
        List<String> dataList = new ArrayList<>();
        CellHandlerRegistry cellHandlerRegistry = sheetContext.getConfiguration().getCellHandlerRegistry();
        for (List<CellResult> cellResultList : cellResultListList) {
            String data = cellResultList.stream().map(cellResult -> {
                ExcelType excelType = cellResult.getExcelType();
                CellHandler<String> cellHandler = cellHandlerRegistry.getCellHandler(javaType, excelType);
                return cellHandler.read(sheetContext, excelFieldConfig, CellResultSet.firstCellResult(cellResult));
            }).collect(Collectors.joining(","));

            dataList.add(data);
        }
        return dataList;
    }


    @Override
    public void write(RowContext rowContext, Integer columnIndex, ExcelFieldConfig excelFieldConfig, List<String> valueList) {
        List<Row> rowList = rowContext.getRowList();

        SheetContext sheetContext = rowContext.getSheetContext();
        CellStyle cellStyle = sheetContext.getDataCellStyle(excelFieldConfig, DEFAULT_DATA_FORMAT_STRING);

        int valueListSize = valueList == null ? 0 : valueList.size();
        for (int i = 0; i < rowList.size(); i++) {
            Row row = rowList.get(i);
            String value;
            if (valueListSize <= i) {
                value = "";
            } else {
                value = valueList.get(i);
            }

            Cell cell = row.createCell(columnIndex);
            cell.setCellValue(value);

            cell.setCellStyle(cellStyle);

            int colspan = excelFieldConfig.getColspan();
            int rowIndex = row.getRowNum();
            RegionUtils.addMergedRegionIfPresent(sheetContext, cellStyle, rowIndex, rowIndex, columnIndex, columnIndex + colspan - 1);
        }
    }

    @Override
    public int getWriteRowspan(List<String> value) {
        return value == null ? 1 : value.size();
    }
}
