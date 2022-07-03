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

import io.github.zouzhiy.excel.context.WorkbookContext;
import io.github.zouzhiy.excel.context.defualts.DefaultSheetContext;
import io.github.zouzhiy.excel.context.defualts.DefaultWorkbookContext;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.write.SheetWrite;
import io.github.zouzhiy.excel.write.WorkbookWrite;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultWorkbookWrite implements WorkbookWrite {

    private final WorkbookContext workbookContext;

    private final ExcelClassConfig excelClassConfig;

    private SheetWrite sheetWrite;

    public DefaultWorkbookWrite(Configuration configuration, WorkbookParameter workbookParameter, ExcelClassConfig excelClassConfig) {
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
    public <T> void write(List<T> itemList) {
        this.write(0, itemList);
        this.commit();
    }

    /**
     * 留扩展余地（多sheet写入）
     */
    private <T> void write(@SuppressWarnings("SameParameterValue") int sheetIndex, List<T> itemList) {
        SheetWrite sheetWrite = this.getSheetWrite(sheetIndex);
        sheetWrite.write(itemList);
    }

    @Override
    public void commit() {
        try {
            this.workbookContext.getWorkbook().write(workbookContext.getWorkbookParameter().getOutputStream());
        } catch (IOException e) {
            throw new ExcelException("工作簿写入失败");
        }
    }

    private SheetWrite getSheetWrite(int sheetIndex) {
        if (sheetWrite == null) {
            SheetParameter sheetParameter;
            WorkbookParameter workbookParameter = this.workbookContext.getWorkbookParameter();
            List<SheetParameter> sheetParameterList = workbookParameter.getSheetParameterList();
            if (sheetParameterList == null || sheetParameterList.isEmpty() || sheetIndex >= sheetParameterList.size()) {
                sheetParameter = SheetParameter.empty();
            } else {
                sheetParameter = sheetParameterList.get(sheetIndex);
            }
            this.sheetWrite = new DefaultSheetWrite(new DefaultSheetContext(this.getWorkbookContext(), excelClassConfig, sheetParameter), excelClassConfig);
        }
        return sheetWrite;
    }


    @Override
    public void close() {
        try {
            this.workbookContext.getWorkbook().close();
            WorkbookParameter workbookParameter = this.workbookContext.getWorkbookParameter();
            workbookParameter.getOutputStream().close();
            InputStream inputStream = workbookParameter.getInputStream();
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
