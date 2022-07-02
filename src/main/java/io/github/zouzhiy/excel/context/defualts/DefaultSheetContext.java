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

import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.context.WorkbookContext;
import io.github.zouzhiy.excel.metadata.*;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultSheetContext implements SheetContext {

    private final WorkbookContext workbookContext;

    private final ExcelClassConfig excelClassConfig;

    private final SheetParameter sheetParameter;

    private Sheet sheet;

    private MergedRegion mergedRegion;

    private Drawing<?> drawing;


    public DefaultSheetContext(WorkbookContext workbookContext, ExcelClassConfig excelClassConfig, SheetParameter sheetParameter) {
        this.workbookContext = workbookContext;
        this.excelClassConfig = excelClassConfig;
        this.sheetParameter = sheetParameter;
    }

    @Override
    public WorkbookContext getWorkbookContext() {
        return workbookContext;
    }

    @Override
    public Configuration getConfiguration() {
        return this.getWorkbookContext().getConfiguration();
    }

    @Override
    public Workbook getWorkbook() {
        return this.getWorkbookContext().getWorkbook();
    }

    @Override
    public ExcelClassConfig getExcelClassConfig() {
        return excelClassConfig;
    }

    @Override
    public SheetParameter getSheetParameter() {
        return sheetParameter;
    }

    @Override
    public boolean hasInputStream() {
        return this.workbookContext.getWorkbookParameter().getInputStream() != null;
    }

    @Override
    public Sheet getSheet() {
        Sheet sheet = this.sheet;
        if (sheet != null) {
            return sheet;
        }

        Workbook workbook = workbookContext.getWorkbook();
        String sheetName = sheetParameter.getSheetName();
        if (isNotEmpty(sheetName)) {
            sheet = workbook.getSheet(sheetName);
        }

        Integer sheetIndex = sheetParameter.getSheetIndex();
        int numberOfSheets = workbook.getNumberOfSheets();
        if (sheet == null && sheetIndex != null && sheetIndex < numberOfSheets) {
            sheet = workbook.getSheetAt(sheetIndex);
        }

        String title = sheetParameter.getTitle();
        if (sheet == null && isNotEmpty(title)) {
            sheet = workbook.getSheet(title);
        }

        if (sheet == null) {
            sheet = createSheet(workbook);
        }

        this.sheet = sheet;

        return sheet;
    }


    @Override
    public MergedRegion getMergedRegion() {
        if (mergedRegion == null) {
            mergedRegion = new MergedRegion(this.getSheet().getMergedRegions());
        }
        return mergedRegion;
    }


    @Override
    public int getMaxRowspan(int rowIndex) {
        SheetParameter sheetParameter = this.getSheetParameter();
        MergedRegion mergedRegion = this.getMergedRegion();
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            return 1;
        }
        short lastCellNum = row.getLastCellNum();
        int curColumnIndex = sheetParameter.getDataColumnStartIndex();

        int maxRowspan = 1;
        while (curColumnIndex < lastCellNum) {
            CellSpan cellSpan = mergedRegion.getCellSpan(rowIndex, curColumnIndex);
            curColumnIndex += cellSpan.getColspan();
            maxRowspan = Math.max(maxRowspan, cellSpan.getRowspan());
        }
        return maxRowspan;
    }

    @Override
    public Drawing<?> getDrawing() {
        if (drawing == null) {
            drawing = this.sheet.createDrawingPatriarch();
        }
        return drawing;
    }

    @Override
    public Row getRow(int rowIndex) {
        return this.getSheet().getRow(rowIndex);
    }

    @Override
    public List<Row> getRowList(int firstRowIndex, int lastRowIndex) {
        List<Row> rowList = new ArrayList<>();
        for (int i = firstRowIndex; i <= lastRowIndex; i++) {
            Row row = sheet.getRow(i);
            rowList.add(row);
        }

        return rowList;
    }

    @Override
    public int getLasRowIndex() {
        return this.getSheet().getLastRowNum();
    }

    @Override
    public FormulaEvaluator getFormulaEvaluator() {
        return workbookContext.getFormulaEvaluator();
    }

    @Override
    public void putTitleCellStyle(ExcelClassConfig excelClassConfig, CellStyle cellStyle) {
        workbookContext.putTitleCellStyle(excelClassConfig, cellStyle);
    }

    @Override
    public void putHeadCellStyle(CellStyleResultSet headCellStyleResultSet) {
        workbookContext.putHeadCellStyle(headCellStyleResultSet);
    }

    @Override
    public void putDataCellStyle(CellStyleResultSet dataCellStyleResultSet) {
        workbookContext.putDataCellStyle(dataCellStyleResultSet);
    }

    @Override
    public CellStyle getTitleCellStyle(ExcelClassConfig excelClassConfig) {
        return workbookContext.getTitleCellStyle(excelClassConfig);
    }

    @Override
    public CellStyle getHeadCellStyle(ExcelFieldConfig excelFieldConfig) {
        return workbookContext.getHeadCellStyle(excelFieldConfig);
    }

    @Override
    public CellStyle getDataCellStyle(ExcelFieldConfig excelFieldConfig, String defaultDataFormat) {
        return workbookContext.getDataCellStyle(excelFieldConfig, defaultDataFormat);
    }

    private Sheet createSheet(Workbook workbook) {
        String realSheetName = null;
        String sheetName = sheetParameter.getSheetName();
        if (isNotEmpty(sheetName)) {
            realSheetName = sheetName;
        }
        String title = sheetParameter.getTitle();
        if (realSheetName == null && isNotEmpty(title)) {
            realSheetName = title;
        }

        Integer sheetIndex = sheetParameter.getSheetIndex();
        if (realSheetName == null && sheetIndex != null) {
            realSheetName = "Sheet" + (sheetIndex + 1);
        }
        if (realSheetName == null) {
            int numberOfSheets = workbook.getNumberOfSheets();
            realSheetName = "Sheet" + (numberOfSheets + 1);
            while (workbook.getSheet(realSheetName) != null) {
                numberOfSheets += 1;
                realSheetName = "Sheet" + (numberOfSheets + 1);
            }
        }

        return workbook.createSheet(realSheetName);
    }

    private boolean isNotEmpty(String str) {
        return str != null && str.trim().length() != 0;
    }
}
