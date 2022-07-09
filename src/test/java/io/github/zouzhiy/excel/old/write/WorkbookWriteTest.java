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
package io.github.zouzhiy.excel.old.write;

import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactory;
import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactoryBuilder;
import io.github.zouzhiy.excel.callback.CellStyleConsumer;
import io.github.zouzhiy.excel.callback.SheetReadConsumer;
import io.github.zouzhiy.excel.callback.SheetWriteConsumer;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;
import io.github.zouzhiy.excel.old.type.ValueListStringHandler;
import io.github.zouzhiy.excel.read.WorkbookRead;
import io.github.zouzhiy.excel.write.WorkbookWrite;
import org.apache.poi.ss.usermodel.CellStyle;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
class WorkbookWriteTest {

    @Test
    void write() {
        WorkbookParameter workbookParameter = this.getWorkbookParameterWithTemplate();
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().register(new ValueListStringHandler()).build();
        WorkbookWrite workbookWrite = zouzhiyExcelFactory.getWorkbookWrite(workbookParameter, WriteDemo.class);

        List<WriteDemo> writeDemoList = this.listWriteDemo();
        workbookWrite.write(writeDemoList);
        assert true;
    }

    @Test
    void write2() {
        WorkbookParameter workbookParameter = this.getWorkbookParameter();
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().register(new ValueListStringHandler()).build();
        WorkbookWrite workbookWrite = zouzhiyExcelFactory.getWorkbookWrite(workbookParameter, WriteDemo.class);

        List<WriteDemo> writeDemoList = this.listWriteDemo();
        workbookWrite.write(writeDemoList);
        assert true;
    }


    @Test
    void read() {
        WorkbookParameter workbookReadParameter = this.getWorkbookReadParameter();
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().register(new ValueListStringHandler()).build();
        WorkbookRead workbookRead = zouzhiyExcelFactory.getWorkbookRead(workbookReadParameter, WriteDemo.class);
        List<WriteDemo> writeDemoList = workbookRead.read(WriteDemo.class);
        System.out.println(writeDemoList);

        assert true;
    }


    private List<WriteDemo> listWriteDemo() {
        List<WriteDemo> writeDemoList = new ArrayList<>();
        WriteDemo writeDemo1 = new WriteDemo();
        writeDemo1.setName("name1");
        writeDemo1.setTitle("title1");
        writeDemo1.setValueList(Stream.of("value1", "value2").collect(Collectors.toList()));
        writeDemo1.setBooleanStr(Boolean.TRUE.toString());
        writeDemoList.add(writeDemo1);

        WriteDemo writeDemo2 = new WriteDemo();
        writeDemo2.setName("name2");
        writeDemo2.setTitle("title2");
        writeDemo2.setValueList(Stream.of("value1", "value2", "value3", "value4").collect(Collectors.toList()));
        writeDemo2.setBooleanStr(Boolean.FALSE.toString());
        writeDemoList.add(writeDemo2);

        WriteDemo writeDemo3 = new WriteDemo();
        writeDemo3.setName("name3");
        writeDemo3.setTitle("title3");
        writeDemo3.setValueList(Stream.of("value1", "value2", "value3").collect(Collectors.toList()));
        writeDemo3.setBooleanStr(Boolean.FALSE.toString());
        writeDemoList.add(writeDemo3);

        return writeDemoList;
    }

    private WorkbookParameter getWorkbookParameter() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        String outputFileName = simpleDateFormat.format(new Date()) + ".xlsx";
        URL url = this.getClass().getResource("/");

        assert url != null;
        String filePath = url.getFile();
        String outputFilePath = filePath + File.separator + "output" + File.separator + outputFileName;


        SheetParameter sheetParameter = SheetParameter.builder().sheet(0)
                .title("测试title-覆盖")
                .dataRowStartIndex(2).dataColumnStartIndex(0)
                .sheetWriteConsumer(new SheetWriteConsumer<WriteDemo>() {
                    @Override
                    public void beforeWrite(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("beforeWrite---------------------------------------");

                    }

                    @Override
                    public void beforeWriteTitle(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("beforeWriteTitle---------------------------------------");
                    }

                    @Override
                    public void afterWriteTitle(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("afterWriteTitle---------------------------------------");

                    }

                    @Override
                    public void beforeWriteHead(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("beforeWriteHead---------------------------------------");

                    }

                    @Override
                    public void afterWriteHead(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("afterWriteHead---------------------------------------");

                    }

                    @Override
                    public void beforeWriteData(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("beforeWriteData---------------------------------------");

                    }

                    @Override
                    public void afterWriteData(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("afterWriteData---------------------------------------");

                    }

                    @Override
                    public void beforeWriteFoot(SheetContext sheetContext, List<WriteDemo> dataList) {
                        dataList.add(new WriteDemo());
                        System.out.println("beforeWriteFoot---------------------------------------");

                    }

                    @Override
                    public void afterWriteFoot(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("afterWriteFoot---------------------------------------");

                    }

                    @Override
                    public void afterWrite(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("afterWrite---------------------------------------");
                    }
                })
                .build();
        return WorkbookParameter.builder()
                .output(new File(outputFilePath))
                .sheetParameter(sheetParameter)
                .cellStyleConsumer(new CellStyleConsumer() {
                    @Override
                    public void afterCreateTitleCellStyle(ExcelClassConfig excelClassConfig, CellStyle titleCellStyle) {
                        System.out.println("afterCreateTitleCellStyle-----------------------------");
                    }

                    @Override
                    public void afterCreateHeadCellStyle(ExcelFieldConfig excelFieldConfig, CellStyle headCellStyle) {
                        System.out.println("afterCreateHeadCellStyle-----------------------------");
                    }

                    @Override
                    public void afterCreateDataCellStyle(ExcelFieldConfig excelFieldConfig, String defaultDataFormat, CellStyle dataCellStyle) {
                        System.out.println("afterCreateDataCellStyle-----------------------------");
                    }
                })
                .build();
    }

    private WorkbookParameter getWorkbookParameterWithTemplate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        String outputFileName = simpleDateFormat.format(new Date()) + ".xlsx";
        URL url = this.getClass().getResource("/");

        assert url != null;
        String filePath = url.getFile();
        String outputFilePath = filePath + File.separator + "output" + File.separator + outputFileName;

        SheetParameter sheetParameter = SheetParameter.builder().sheet(0)
                .title("测试title-覆盖")
                .dataRowStartIndex(2).dataColumnStartIndex(0)
                .sheetWriteConsumer(new SheetWriteConsumer<WriteDemo>() {
                    @Override
                    public void beforeWrite(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("beforeWrite---------------------------------------");

                    }

                    @Override
                    public void beforeWriteTitle(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("beforeWriteTitle---------------------------------------");
                    }

                    @Override
                    public void afterWriteTitle(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("afterWriteTitle---------------------------------------");

                    }

                    @Override
                    public void beforeWriteHead(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("beforeWriteHead---------------------------------------");

                    }

                    @Override
                    public void afterWriteHead(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("afterWriteHead---------------------------------------");

                    }

                    @Override
                    public void beforeWriteData(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("beforeWriteData---------------------------------------");

                    }

                    @Override
                    public void afterWriteData(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("afterWriteData---------------------------------------");

                    }

                    @Override
                    public void beforeWriteFoot(SheetContext sheetContext, List<WriteDemo> dataList) {
                        dataList.add(new WriteDemo());
                        System.out.println("beforeWriteFoot---------------------------------------");

                    }

                    @Override
                    public void afterWriteFoot(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("afterWriteFoot---------------------------------------");

                    }

                    @Override
                    public void afterWrite(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("afterWrite---------------------------------------");
                    }
                }).build();

        String exportTemplateFileName = "template2.xlsx";
        String exportTemplateFilePath = "statics/" + exportTemplateFileName;
        InputStream exportTemplateInputStream = this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath);

        return WorkbookParameter
                .builder()
                .input(exportTemplateInputStream)
                .output(new File(outputFilePath))
                .sheetParameter(sheetParameter)
                .build();
    }

    private WorkbookParameter getWorkbookReadParameter() {

        String importFileName = "import1.xlsx";
        String importFilePath = "statics/" + importFileName;
        InputStream importFileInputStream = this.getClass().getClassLoader().getResourceAsStream(importFilePath);

        SheetParameter sheetParameter = SheetParameter.builder()
                .sheet(0)
                .title("测试title")
                .dataRowStartIndex(2)
                .dataColumnStartIndex(0)
                .sheetReadConsumer(new SheetReadConsumer<WriteDemo>() {

                    @Override
                    public void beforeRead(SheetContext sheetContext) {
                        System.out.println("beforeRead--------------------------------");
                    }

                    @Override
                    public void acceptReadTitle(SheetContext sheetContext, CellResultSet titleCellResultSet) {
                        System.out.println("acceptReadTitle--------------------------------");
                    }

                    @Override
                    public void acceptReadHead(SheetContext sheetContext, List<CellResultSet> headCellResultSetList) {
                        System.out.println("acceptReadHead--------------------------------");
                    }

                    @Override
                    public void acceptReadData(SheetContext sheetContext, List<WriteDemo> dataList) {
                        System.out.println("acceptReadData--------------------------------");
                    }

                    @Override
                    public void acceptReadFoot(SheetContext sheetContext, List<CellResultSet> footCellResultSetList) {
                        System.out.println("acceptReadFoot--------------------------------");
                    }

                    @Override
                    public void afterRead(SheetContext sheetContext, CellResultSet titleCellResultSet, List<CellResultSet> headCellResultSetList, List<WriteDemo> dataList, List<CellResultSet> footCellResultSetList) {
                        System.out.println("afterRead--------------------------------");
                    }
                }).build();

        return WorkbookParameter.builder()
                .sheetParameter(sheetParameter)
                .input(importFileInputStream)
                .build();
    }
}
