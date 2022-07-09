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
package io.github.zouzhiy.excel.old.cellstyle;

import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactory;
import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactoryBuilder;
import io.github.zouzhiy.excel.cellstyle.CellStyleRead;
import io.github.zouzhiy.excel.cellstyle.defaults.DefaultCellStyleRead;
import io.github.zouzhiy.excel.context.defualts.DefaultSheetContext;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.old.builder.Demo;
import io.github.zouzhiy.excel.write.WorkbookWrite;
import io.github.zouzhiy.excel.write.defaults.DefaultSheetWrite;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

/**
 * @author zouzhiy
 * @since 2022/7/3
 */
class CellStyleReadTest {

    private final ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().build();

    @Test
    void read() {
        WorkbookParameter workbookParameter = WorkbookParameter.builder().build();
        WorkbookWrite workbookWrite = zouzhiyExcelFactory.getWorkbookWrite(workbookParameter, Demo.class);

        CellStyleRead cellStyleRead = DefaultCellStyleRead.getInstance();

        DefaultSheetWrite sheetWrite = new DefaultSheetWrite(
                new DefaultSheetContext(
                        workbookWrite.getWorkbookContext()
                        , workbookWrite.getExcelClassConfig()
                        , workbookParameter.getSheetParameter()), workbookWrite.getExcelClassConfig());
        CellStyle cellStyle1 = cellStyleRead.read(sheetWrite.getSheetContext(), Collections.emptyList(), 1, 1);
        assert cellStyle1 == null;

        CellStyle cellStyle2 = cellStyleRead.read(sheetWrite.getSheetContext(), Collections.singletonList(null), 1, 1);
        assert cellStyle2 == null;
        CellStyle cellStyle3 = cellStyleRead.read(sheetWrite.getSheetContext(), Collections.singletonList(sheetWrite.getSheetContext().getSheet().getRow(1)), 1, 1);
        assert cellStyle3 == null;

        sheetWrite.getSheetContext().getSheet().createRow(1);
        CellStyle cellStyle4 = cellStyleRead.read(sheetWrite.getSheetContext(), Collections.singletonList(sheetWrite.getSheetContext().getSheet().getRow(1)), 1, 1);
        assert cellStyle4 == null;

        Row row = sheetWrite.getSheetContext().getSheet().createRow(1);
        row.createCell(1);
        CellStyle cellStyle5 = cellStyleRead.read(sheetWrite.getSheetContext(), Collections.singletonList(sheetWrite.getSheetContext().getSheet().getRow(1)), 1, 1);
        assert cellStyle5 != null;

    }

    @Test
    void read2() {
        SheetParameter sheetParameter = SheetParameter.builder().titleRowStartIndex(-1).headRowStartIndex(-1).build();
        WorkbookParameter workbookParameter = WorkbookParameter.builder().output(this.getOutputFile()).sheetParameter(sheetParameter).build();
        WorkbookWrite workbookWrite = zouzhiyExcelFactory.getWorkbookWrite(workbookParameter, Demo.class);

        workbookWrite.write(Collections.emptyList());

        assert true;
    }

    @Test
    void read3() {
        WorkbookParameter workbookParameter = this.getWorkbookParameterWithTemplate1();
        ExcelClassConfig excelClassConfig = ExcelClassConfig
                .builder()
                .rowHeadRead(null)
                .rowFootRead(null)
                .rowTitleRead(null)
                .rowFootRead(null)
                .rowTitleWrite(null)
                .rowHeadWrite(null)
                .rowFootWrite(null)
                .rowStyleRead(null)
                .build();
        WorkbookWrite workbookWrite = zouzhiyExcelFactory.getWorkbookWrite(workbookParameter, excelClassConfig);

        workbookWrite.write(Collections.emptyList());

        assert true;
    }

    @Test
    void read4() {
        WorkbookParameter workbookParameter = this.getWorkbookParameterWithTemplate2();
        ExcelClassConfig excelClassConfig = ExcelClassConfig
                .builder()
                .rowHeadRead(null)
                .rowFootRead(null)
                .rowTitleRead(null)
                .rowFootRead(null)
                .rowTitleWrite(null)
                .rowHeadWrite(null)
                .rowFootWrite(null)
                .rowStyleRead(null)
                .build();
        WorkbookWrite workbookWrite = zouzhiyExcelFactory.getWorkbookWrite(workbookParameter, excelClassConfig);

        workbookWrite.write(Collections.emptyList());

        assert true;
    }

    @Test
    void read5() {
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .titleRowStartIndex(-1)
                .headRowStartIndex(-1)
                .build();
        WorkbookParameter workbookParameter = WorkbookParameter.builder().sheetParameter(sheetParameter).output(this.getOutputFile()).build();
        ExcelClassConfig excelClassConfig = ExcelClassConfig
                .builder()
                .rowHeadRead(null)
                .rowFootRead(null)
                .rowTitleRead(null)
                .rowFootRead(null)
                .rowTitleWrite(null)
                .rowHeadWrite(null)
                .rowFootWrite(null)
                .rowStyleRead(null)
                .build();
        WorkbookWrite workbookWrite = zouzhiyExcelFactory.getWorkbookWrite(workbookParameter, excelClassConfig);

        workbookWrite.write(Collections.emptyList());

        assert true;
    }


    private String getOutputFile() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        String outputFileName = simpleDateFormat.format(new Date()) + ".xls";
        URL url = this.getClass().getResource("/");

        assert url != null;
        String filePath = url.getFile();
        return filePath + File.separator + "output" + File.separator + outputFileName;
    }

    private WorkbookParameter getWorkbookParameterWithTemplate1() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        String outputFileName = simpleDateFormat.format(new Date()) + ".xlsx";
        URL url = this.getClass().getResource("/");

        assert url != null;
        String filePath = url.getFile();
        String outputFilePath = filePath + File.separator + "output" + File.separator + outputFileName;

        SheetParameter sheetParameter = SheetParameter.builder().sheet(0)
                .title("测试title-覆盖")
                .dataRowStartIndex(1).dataColumnStartIndex(0)
                .build();

        String exportTemplateFileName = "template1.xlsx";
        String exportTemplateFilePath = "statics/" + exportTemplateFileName;
        InputStream exportTemplateInputStream = this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath);

        return WorkbookParameter
                .builder()
                .input(exportTemplateInputStream)
                .output(new File(outputFilePath))
                .sheetParameter(sheetParameter)
                .build();
    }

    private WorkbookParameter getWorkbookParameterWithTemplate2() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        String outputFileName = simpleDateFormat.format(new Date()) + ".xlsx";
        URL url = this.getClass().getResource("/");

        assert url != null;
        String filePath = url.getFile();
        String outputFilePath = filePath + File.separator + "output" + File.separator + outputFileName;

        SheetParameter sheetParameter = SheetParameter.builder().sheet(0)
                .title("测试title-覆盖")
                .dataRowStartIndex(2).dataColumnStartIndex(0)
                .build();

        String exportTemplateFileName = "template1.xlsx";
        String exportTemplateFilePath = "statics/" + exportTemplateFileName;
        InputStream exportTemplateInputStream = this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath);

        return WorkbookParameter
                .builder()
                .input(exportTemplateInputStream)
                .output(new File(outputFilePath))
                .sheetParameter(sheetParameter)
                .build();
    }

}