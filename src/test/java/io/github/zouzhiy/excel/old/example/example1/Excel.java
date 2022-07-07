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
package io.github.zouzhiy.excel.old.example.example1;

import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactory;
import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactoryBuilder;
import io.github.zouzhiy.excel.old.example.example1.matedata.Demo;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.read.WorkbookRead;
import io.github.zouzhiy.excel.write.WorkbookWrite;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/4
 */
public class Excel {

    private final ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder()
            .build();

    public List<Demo> read(File inputFile) {
        SheetParameter sheetParameter = SheetParameter.builder()
                .sheet(0)
                .build();
        WorkbookParameter workbookParameter = WorkbookParameter
                .builder()
                .input(inputFile)
                .sheetParameter(sheetParameter)
                .build();

        try (WorkbookRead workbookRead = zouzhiyExcelFactory.getWorkbookRead(workbookParameter, Demo.class)) {
            return workbookRead.read(Demo.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcelException("读取失败");
        }
    }


    public File write(List<Demo> demoList) {

        String writeFileName = this.getWritePath();

        SheetParameter sheetParameter = SheetParameter.builder()
                .build();
        WorkbookParameter workbookParameter = WorkbookParameter.builder()
                .sheetParameter(sheetParameter)
                .output(writeFileName)
                .build();

        try (WorkbookWrite workbookWrite = zouzhiyExcelFactory.getWorkbookWrite(workbookParameter, Demo.class)) {
            workbookWrite.write(demoList);
            return workbookParameter.getOutputFile();
        } catch (Exception e) {
            throw new ExcelException("写入失败");
        }

    }


    private String getWritePath() {
        URL url = this.getClass().getResource("/");

        assert url != null;
        String rootPath = url.getPath();

        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx";
        String writePath = rootPath + File.separator + "write" + File.separator + fileName;
        File file = new File(writePath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return writePath;

    }
}
