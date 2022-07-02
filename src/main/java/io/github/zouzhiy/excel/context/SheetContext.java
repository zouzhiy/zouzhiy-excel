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
package io.github.zouzhiy.excel.context;

import io.github.zouzhiy.excel.metadata.*;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public interface SheetContext {

    WorkbookContext getWorkbookContext();

    Configuration getConfiguration();

    Workbook getWorkbook();

    ExcelClassConfig getExcelClassConfig();

    SheetParameter getSheetParameter();

    boolean hasInputStream();

    Sheet getSheet();

    MergedRegion getMergedRegion();

    int getMaxRowspan(int rowIndex);

    Row getRow(int rowIndex);

    List<Row> getRowList(int firstRowIndex, int lastRowIndex);

    int getLasRowIndex();

    FormulaEvaluator getFormulaEvaluator();

    void putTitleCellStyle(ExcelClassConfig excelClassConfig, CellStyle cellStyle);

    void putHeadCellStyle(CellStyleResultSet headCellStyleResultSet);

    void putDataCellStyle(CellStyleResultSet dataCellStyleResultSet);

    CellStyle getTitleCellStyle(ExcelClassConfig excelClassConfig);

    CellStyle getHeadCellStyle(ExcelFieldConfig excelFieldConfig);

    CellStyle getDataCellStyle(ExcelFieldConfig excelFieldConfig, String defaultDataFormat);
}
