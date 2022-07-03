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

import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.metadata.result.CellStyleResultSet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public interface WorkbookContext {

    Configuration getConfiguration();

    WorkbookParameter getWorkbookParameter();

    Workbook getWorkbook();

    FormulaEvaluator getFormulaEvaluator();

    void putTitleCellStyle(ExcelClassConfig excelClassConfig, CellStyle cellStyle);

    void putHeadCellStyle(CellStyleResultSet headCellStyleResultSet);

    void putDataCellStyle(CellStyleResultSet dataCellStyleResultSet);

    CellStyle getTitleCellStyle(ExcelClassConfig excelClassConfig);

    CellStyle getHeadCellStyle(ExcelFieldConfig excelFieldConfig);

    CellStyle getDataCellStyle(ExcelFieldConfig excelFieldConfig, String defaultDataFormat);
}
