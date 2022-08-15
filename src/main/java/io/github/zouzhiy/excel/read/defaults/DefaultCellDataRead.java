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
import io.github.zouzhiy.excel.error.ErrorContext;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.CellSpan;
import io.github.zouzhiy.excel.metadata.MergedRegion;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;
import io.github.zouzhiy.excel.read.CellDataRead;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultCellDataRead implements CellDataRead {

    private final static DefaultCellDataRead DEFAULT_CELL_DATA_READ = new DefaultCellDataRead();

    private DefaultCellDataRead() {
    }

    public static DefaultCellDataRead getInstance() {
        return DEFAULT_CELL_DATA_READ;
    }

    @Override
    public CellResultSet read(SheetContext sheetContext, List<Row> rowList, int firstCol, int lastCol) {
        if (rowList.isEmpty()) {
            throw new ExcelException("rowList不能为空");
        }
        MergedRegion mergedRegion = sheetContext.getMergedRegion();
        FormulaEvaluator formulaEvaluator = sheetContext.getFormulaEvaluator();

        List<List<CellResult>> cellResultListList = new ArrayList<>();
        for (Row row : rowList) {
            int rowIndex = row.getRowNum();
            List<CellResult> cellResultList = new ArrayList<>();
            for (int columnIndex = firstCol; columnIndex <= lastCol; columnIndex++) {
                boolean inMergedRegionAndNotFirst = mergedRegion.isInMergedRegionAndNotFirst(rowIndex, columnIndex);
                if (inMergedRegionAndNotFirst) {
                    cellResultList.add(CellResult.none());
                    continue;
                }
                Cell cell = row.getCell(columnIndex);
                CellResult cellResult = this.read(mergedRegion, formulaEvaluator, cell);
                cellResultList.add(cellResult);
            }

            cellResultListList.add(cellResultList);
        }
        cellResultListList = mergedRegionCellResultListList(cellResultListList);

        return CellResultSet.newInstance(cellResultListList);
    }

    private CellResult read(MergedRegion mergedRegion, FormulaEvaluator formulaEvaluator, Cell cell) {
        if (cell == null) {
            return CellResult.none();
        }

        int rowIndex = cell.getRowIndex();
        int columnIndex = cell.getColumnIndex();
        CellSpan cellSpan = mergedRegion.getCellSpan(rowIndex, columnIndex);

        CellResult cellResult;
        CellType cellTye = cell.getCellType();
        switch (cellTye) {
            case NUMERIC:
                double numericCellValue = cell.getNumericCellValue();
                cellResult = CellResult.valueOf(cell, cellSpan, numericCellValue);
                break;
            case STRING:
                String stringCellValue = cell.getStringCellValue();
                cellResult = CellResult.valueOf(cell, cellSpan, stringCellValue);
                break;
            case BOOLEAN:
                boolean booleanCellValue = cell.getBooleanCellValue();
                cellResult = CellResult.valueOf(cell, cellSpan, booleanCellValue);
                break;
            case FORMULA:
                cellResult = formatCellValue(formulaEvaluator, cell, cellSpan);
                break;
            case BLANK:
            case _NONE:
                cellResult = CellResult.blank(cell, cellSpan);
                break;
            case ERROR:
            default:
                ErrorContext.instance().error(cell.getSheet().getSheetName(), cell.getRowIndex(), cell.getColumnIndex(), "读取单元格值失败");
                throw new ExcelException("读取单元格值失败。" + cell.getRowIndex() + cell.getColumnIndex());
        }

        return cellResult;
    }

    private CellResult formatCellValue(FormulaEvaluator formulaEvaluator, Cell cell, CellSpan cellSpan) {
        if (formulaEvaluator == null) {
            return CellResult.blank(cell, cellSpan);
        }

        CellResult cellResult;
        CellValue cellValue = formulaEvaluator.evaluate(cell);
        CellType cellValueCellType = cellValue.getCellType();
        switch (cellValueCellType) {
            case NUMERIC:
                double numericCellValue = cellValue.getNumberValue();
                cellResult = CellResult.valueOf(cell, cellSpan, numericCellValue);
                break;
            case STRING:
                String stringCellValue = cellValue.getStringValue();
                cellResult = CellResult.valueOf(cell, cellSpan, stringCellValue);
                break;
            case BOOLEAN:
                boolean booleanCellValue = cellValue.getBooleanValue();
                cellResult = CellResult.valueOf(cell, cellSpan, booleanCellValue);
                break;
            case BLANK:
            case _NONE:
                cellResult = CellResult.blank(cell, cellSpan);
                break;
            case FORMULA:
            case ERROR:
            default:
                throw new ExcelException("读取单元格值失败。" + cell.getRowIndex() + cell.getColumnIndex());
        }
        return cellResult;
    }

    /**
     * 合并数据，删除空行、删除空列
     */
    private List<List<CellResult>> mergedRegionCellResultListList(List<List<CellResult>> cellResultListList) {

        this.mergedRegionCellResultListListByRow(cellResultListList);

        cellResultListList = this.mergedRegionCellResultListListByColumn(cellResultListList);

        return cellResultListList;
    }

    /**
     * 删除空行数据
     */
    private void mergedRegionCellResultListListByRow(List<List<CellResult>> cellResultListList) {
        cellResultListList.removeIf(cellResultList -> {
            for (CellResult cellResult : cellResultList) {
                if (!cellResult.isNone()) {
                    return false;
                }
            }
            return true;
        });
    }

    private List<List<CellResult>> mergedRegionCellResultListListByColumn(List<List<CellResult>> cellResultListList) {
        int rowSize = cellResultListList.size();
        int colSize = cellResultListList.stream().mapToInt(List::size).max().orElse(1);

        List<List<CellResult>> newListList = new ArrayList<>(rowSize);
        for (int i = 0; i < rowSize; i++) {
            newListList.add(new ArrayList<>());
        }
        for (int i = 0; i < colSize; i++) {
            boolean isNone = true;
            for (List<CellResult> cellResultList : cellResultListList) {
                CellResult cellResult = cellResultList.get(i);
                isNone = cellResult == null || cellResult.isNone();
            }
            if (isNone) {
                continue;
            }

            for (int j = 0; j < rowSize; j++) {
                List<CellResult> cellResultList = cellResultListList.get(j);
                CellResult cellResult = cellResultList.get(i);

                List<CellResult> newList = newListList.get(j);
                newList.add(cellResult);
            }
        }

        return newListList;
    }

}
