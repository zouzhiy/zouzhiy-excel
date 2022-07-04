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

import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.read.WorkbookRead;
import io.github.zouzhiy.excel.write.WorkbookWrite;
import org.junit.jupiter.api.Test;

/**
 * @author zouzhiy
 * @since 2022/7/3
 */
class DefaultZouzhiyExcelFactoryTest {

    private final ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().build();

    @Test
    void getConfiguration() {
        Configuration configuration = zouzhiyExcelFactory.getConfiguration();

        assert configuration != null;
    }

    @Test
    void getWorkbookRead() {
        WorkbookParameter workbookParameter = WorkbookParameter.builder().build();
        WorkbookRead workbookRead = zouzhiyExcelFactory.getWorkbookRead(workbookParameter, Demo.class);
        assert workbookRead.getWorkbookContext().getWorkbookParameter().equals(workbookParameter);
    }

    @Test
    void testGetWorkbookRead() {
        WorkbookParameter workbookParameter = WorkbookParameter.builder().build();
        ExcelClassConfig excelClassConfig = zouzhiyExcelFactory.getConfiguration().getExcelAnnotationParse().findForClass(Demo.class);
        WorkbookRead workbookRead = zouzhiyExcelFactory.getWorkbookRead(workbookParameter, excelClassConfig);
        assert workbookRead.getWorkbookContext().getWorkbookParameter().equals(workbookParameter);
        assert workbookRead.getExcelClassConfig().equals(excelClassConfig);
    }

    @Test
    void getWorkbookWrite() {
        WorkbookParameter workbookParameter = WorkbookParameter.builder().build();
        WorkbookWrite workbookWrite = zouzhiyExcelFactory.getWorkbookWrite(workbookParameter, Demo.class);
        assert workbookWrite.getWorkbookContext().getWorkbookParameter().equals(workbookParameter);
    }

    @Test
    void testGetWorkbookWrite() {

        WorkbookParameter workbookParameter = WorkbookParameter.builder().build();
        ExcelClassConfig excelClassConfig = zouzhiyExcelFactory.getConfiguration().getExcelAnnotationParse().findForClass(Demo.class);
        WorkbookWrite workbookWrite = zouzhiyExcelFactory.getWorkbookWrite(workbookParameter, excelClassConfig);
        assert workbookWrite.getWorkbookContext().getWorkbookParameter().equals(workbookParameter);
        assert workbookWrite.getExcelClassConfig().equals(excelClassConfig);
    }
}