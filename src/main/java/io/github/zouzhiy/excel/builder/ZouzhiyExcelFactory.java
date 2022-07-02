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
package io.github.zouzhiy.excel.builder;

import io.github.zouzhiy.excel.metadata.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.WorkbookParameter;
import io.github.zouzhiy.excel.read.WorkbookRead;
import io.github.zouzhiy.excel.write.WorkbookWrite;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public interface ZouzhiyExcelFactory {

    WorkbookRead getWorkbookRead(WorkbookParameter workbookParameter, Class<?> clazz);

    WorkbookRead getWorkbookRead(WorkbookParameter workbookParameter, ExcelClassConfig excelClassConfig);

    WorkbookWrite getWorkbookWrite(WorkbookParameter workbookParameter, Class<?> clazz);

    WorkbookWrite getWorkbookWrite(WorkbookParameter workbookParameter, ExcelClassConfig excelClassConfig);
}
