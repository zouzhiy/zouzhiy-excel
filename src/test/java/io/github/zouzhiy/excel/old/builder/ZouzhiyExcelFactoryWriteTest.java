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
package io.github.zouzhiy.excel.old.builder;

import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactory;
import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactoryBuilder;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
class ZouzhiyExcelFactoryWriteTest {

    private final ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().build();

    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void writeXlsx() {

        List<Demo> demoList = this.demoList();
        WorkbookParameter writeWorkbookParameter = this.getXlsxWriteWorkbookParameter();
        zouzhiyExcelFactory.getWorkbookWrite(writeWorkbookParameter, Demo.class)
                .write(demoList);

        WorkbookParameter readWriteWorkbookParameter = WorkbookParameter.builder()
                .input(writeWorkbookParameter.getOutputFile())
                .sheetParameter(writeWorkbookParameter.getSheetParameter())
                .build();
        List<Demo> demoList1 = zouzhiyExcelFactory.getWorkbookRead(readWriteWorkbookParameter, Demo.class).read(Demo.class);


        WorkbookParameter writeWorkbookParameter2 = this.getXlsxWriteWorkbookParameter();
        zouzhiyExcelFactory.getWorkbookWrite(writeWorkbookParameter2, Demo.class)
                .write(demoList1);

    }

    @Test
    void writeXls() {

        List<Demo> demoList = this.demoList();
        WorkbookParameter writeWorkbookParameter = this.getXlsWriteWorkbookParameter();
        zouzhiyExcelFactory.getWorkbookWrite(writeWorkbookParameter, Demo.class)
                .write(demoList);

        WorkbookParameter readWriteWorkbookParameter = WorkbookParameter.builder()
                .input(writeWorkbookParameter.getOutputFile())
                .sheetParameter(writeWorkbookParameter.getSheetParameter())
                .build();
        List<Demo> demoList1 = zouzhiyExcelFactory.getWorkbookRead(readWriteWorkbookParameter, Demo.class).read(Demo.class);

        WorkbookParameter writeWorkbookParameter2 = this.getXlsWriteWorkbookParameter();
        zouzhiyExcelFactory.getWorkbookWrite(writeWorkbookParameter2, Demo.class)
                .write(demoList1);

    }

    private List<Demo> demoList() {
        List<Demo> demoList = new ArrayList<>();
        int size = random.nextInt(223);
        for (int i = 0; i < size; i++) {
            demoList.add(new Demo());
        }

        return demoList;
    }

    private WorkbookParameter getXlsxWriteWorkbookParameter() {
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
        return WorkbookParameter.builder()
                .output(new File(outputFilePath))
                .sheetParameter(sheetParameter)
                .build();
    }

    private WorkbookParameter getXlsWriteWorkbookParameter() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        String outputFileName = simpleDateFormat.format(new Date()) + ".xls";
        URL url = this.getClass().getResource("/");

        assert url != null;
        String filePath = url.getFile();
        String outputFilePath = filePath + File.separator + "output" + File.separator + outputFileName;


        SheetParameter sheetParameter = SheetParameter.builder().sheet(0)
                .title("测试title-覆盖")
                .dataRowStartIndex(2).dataColumnStartIndex(0)
                .build();
        return WorkbookParameter.builder()
                .output(new File(outputFilePath))
                .sheetParameter(sheetParameter)
                .build();
    }


}