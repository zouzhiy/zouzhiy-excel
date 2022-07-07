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
package io.github.zouzhiy.excel.old.context.defualts;

import io.github.zouzhiy.excel.old.builder.Demo;
import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactory;
import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactoryBuilder;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.read.WorkbookRead;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

/**
 * @author zouzhiy
 * @since 2022/7/3
 */
class DefaultWorkbookContextTest {

    @Test
    void getConfiguration() {
    }

    @Test
    void getWorkbookParameter() {
    }

    @Test
    void getWorkbook() {
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().build();
        try {
            String exportTemplateFileName = "jpg1.jpg";
            String exportTemplateFilePath = "statics/image/" + exportTemplateFileName;
            InputStream exportTemplateInputStream = this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath);

            WorkbookRead workbookRead = zouzhiyExcelFactory.getWorkbookRead(WorkbookParameter.builder().input(exportTemplateInputStream).build(), Demo.class);
            workbookRead.getWorkbookContext().getWorkbook();
            assert false;
        } catch (ExcelException e) {
            assert true;
        }
    }

    @Test
    void getFormulaEvaluator() {
    }

    @Test
    void putTitleCellStyle() {
    }

    @Test
    void putHeadCellStyle() {
    }

    @Test
    void putDataCellStyle() {
    }

    @Test
    void getTitleCellStyle() {
    }

    @Test
    void getHeadCellStyle() {
    }

    @Test
    void getDataCellStyle() {
    }
}