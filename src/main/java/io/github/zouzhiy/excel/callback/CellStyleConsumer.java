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
package io.github.zouzhiy.excel.callback;

import io.github.zouzhiy.excel.metadata.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.ExcelFieldConfig;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public interface CellStyleConsumer {

    void afterCreateTitleCellStyle(ExcelClassConfig excelClassConfig, CellStyle titleCellStyle);

    void afterCreateHeadCellStyle(ExcelFieldConfig excelFieldConfig, CellStyle headCellStyle);

    void afterCreateDataCellStyle(ExcelFieldConfig excelFieldConfig, String defaultDataFormat, CellStyle dataCellStyle);
}
