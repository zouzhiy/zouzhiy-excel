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

import io.github.zouzhiy.excel.context.WorkbookContext;
import io.github.zouzhiy.excel.context.defualts.DefaultSheetContext;
import io.github.zouzhiy.excel.context.defualts.DefaultWorkbookContext;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.read.SheetRead;
import io.github.zouzhiy.excel.read.WorkbookRead;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultWorkbookRead implements WorkbookRead {


    private final WorkbookContext workbookContext;

    private final ExcelClassConfig excelClassConfig;

    private SheetRead sheetRead;

    public DefaultWorkbookRead(Configuration configuration, WorkbookParameter workbookParameter, ExcelClassConfig excelClassConfig) {
        this.workbookContext = new DefaultWorkbookContext(configuration, workbookParameter);
        this.excelClassConfig = excelClassConfig;
    }

    @Override
    public WorkbookContext getWorkbookContext() {
        return workbookContext;
    }

    @Override
    public ExcelClassConfig getExcelClassConfig() {
        return excelClassConfig;
    }

    @Override
    public <T> List<T> read(Class<T> clazz) {
        return read(clazz, 0);
    }

    private <T> List<T> read(Class<T> clazz, @SuppressWarnings("SameParameterValue") int sheetIndex) {
        SheetRead sheetRead = this.getSheetRead(sheetIndex);
        return sheetRead.read(clazz);
    }

    private SheetRead getSheetRead(int sheetIndex) {
        if (sheetRead == null) {
            SheetParameter sheetParameter;
            WorkbookParameter workbookParameter = workbookContext.getWorkbookParameter();
            List<SheetParameter> sheetParameterList = workbookParameter.getSheetParameterList();
            if (sheetParameterList == null || sheetParameterList.isEmpty() || sheetIndex >= sheetParameterList.size()) {
                sheetParameter = SheetParameter.empty();
            } else {
                sheetParameter = sheetParameterList.get(sheetIndex);
            }
            this.sheetRead = new DefaultSheetRead(new DefaultSheetContext(this.getWorkbookContext(), excelClassConfig, sheetParameter), excelClassConfig);
        }
        return sheetRead;
    }


    @Override
    public void close() {
        try {
            this.workbookContext.getWorkbook().close();
            WorkbookParameter workbookParameter = this.workbookContext.getWorkbookParameter();
            workbookParameter.getInputStream().close();
            OutputStream outputStream = workbookParameter.getOutputStream();
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
