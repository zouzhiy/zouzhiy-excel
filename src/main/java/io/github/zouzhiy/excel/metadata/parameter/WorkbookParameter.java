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
package io.github.zouzhiy.excel.metadata.parameter;

import io.github.zouzhiy.excel.callback.CellStyleConsumer;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import lombok.Getter;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
@Getter
public class WorkbookParameter {

    private final String inputFileName;
    private final String inputFilePath;
    private final File inputFile;

    private final InputStream inputStream;

    private final String outputFileName;
    private final String outputFilePath;
    private final File outputFile;
    private final OutputStream outputStream;

    private final boolean xssf;

    private final List<SheetParameter> sheetParameterList;

    private final List<CellStyleConsumer> cellStyleConsumerList;

    public SheetParameter getSheetParameter() {
        return this.sheetParameterList.get(0);
    }

    public static WorkbookParameterBuilder builder() {
        return new WorkbookParameterBuilder();
    }

    private WorkbookParameter(String inputFileName, String inputFilePath, File inputFile
            , InputStream inputStream
            , String outputFileName, String outputFilePath, File outputFile
            , OutputStream outputStream
            , Boolean xssf
            , List<SheetParameter> sheetParameterList
            , List<CellStyleConsumer> cellStyleConsumerList) {
        this.inputFileName = inputFileName;
        this.inputFilePath = inputFilePath;
        this.inputFile = inputFile;
        this.inputStream = inputStream;
        this.outputFileName = outputFileName;
        this.outputFilePath = outputFilePath;
        this.outputFile = outputFile;
        this.outputStream = outputStream;
        this.xssf = xssf;
        this.sheetParameterList = sheetParameterList;
        this.cellStyleConsumerList = cellStyleConsumerList;
    }

    public static class WorkbookParameterBuilder {
        private String inputFileName;
        private String inputFilePath;
        private File inputFile;

        private InputStream inputStream;

        private String outputFileName;
        private String outputFilePath;
        private File outputFile;
        private OutputStream outputStream;

        private Boolean xssf;

        private List<SheetParameter> sheetParameterList;

        private List<CellStyleConsumer> cellStyleConsumerList;


        public WorkbookParameterBuilder input(File templateFile) {
            this.inputFileName = templateFile.getName();
            this.inputFilePath = templateFile.getAbsolutePath();
            this.inputFile = templateFile;
            this.xssf(this.inputFileName);
            return this;
        }

        public WorkbookParameterBuilder input(InputStream templateInputStream) {
            this.inputStream = templateInputStream;
            return this;
        }

        public WorkbookParameterBuilder output(String outputFileName) {
            this.output(new File(outputFileName));
            return this;
        }

        public WorkbookParameterBuilder output(File outputFile) {
            this.outputFileName = outputFile.getName();
            this.outputFilePath = outputFile.getAbsolutePath();
            this.outputFile = outputFile;
            this.xssf(this.outputFileName);
            return this;
        }

        public WorkbookParameterBuilder output(OutputStream outputStream) {
            this.outputStream = outputStream;
            return this;
        }

        public WorkbookParameterBuilder xssf(boolean xssf) {
            this.xssf = xssf;
            return this;
        }

        public WorkbookParameterBuilder sheetParameter(SheetParameter sheetParameter) {
            if (sheetParameterList == null) {
                this.sheetParameterList = new ArrayList<>(5);
            }
            this.sheetParameterList.add(sheetParameter);

            return this;
        }

        public WorkbookParameterBuilder cellStyleConsumer(CellStyleConsumer cellStyleConsumer) {
            if (cellStyleConsumerList == null) {
                cellStyleConsumerList = new ArrayList<>();
            }
            if (!cellStyleConsumerList.contains(cellStyleConsumer)) {
                cellStyleConsumerList.add(cellStyleConsumer);
            }
            return this;
        }

        public WorkbookParameter build() {
            return new WorkbookParameter(inputFileName, inputFilePath, inputFile
                    , this.getInputStream()
                    , outputFileName, outputFilePath, outputFile
                    , this.getOutputStream()
                    , this.isXssf()
                    , this.getSheetParameterList()
                    , this.getCellStyleConsumerList());
        }

        private void xssf(String fileName) {
            boolean newXssf = fileName.endsWith(".xlsx");
            if (this.xssf == null) {
                this.xssf = newXssf;
            } else if (this.xssf != newXssf) {
                throw new ExcelException("输入与输出的文件类型不一致");
            }
        }

        private InputStream getInputStream() {
            if (inputStream == null) {
                this.inputStream = createInputStream();
            }
            return inputStream;
        }

        private InputStream createInputStream() {
            if (inputFile == null) {
                return null;
            }
            try {
                return new FileInputStream(inputFile);
            } catch (FileNotFoundException e) {
                throw new ExcelException("创建输入流失败");
            }
        }

        private OutputStream getOutputStream() {
            if (outputStream == null) {
                this.outputStream = createOutputStream();
            }
            return outputStream;
        }


        private OutputStream createOutputStream() {
            if (outputFile == null) {
                return null;
            }
            try {
                File parentFile = outputFile.getParentFile();
                if (!parentFile.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    parentFile.mkdirs();
                }
                return new FileOutputStream(outputFile);
            } catch (FileNotFoundException e) {
                throw new ExcelException("输出文件夹不存在，创建输出流失败");
            }
        }

        private boolean isXssf() {
            return xssf == null || xssf;
        }

        public List<SheetParameter> getSheetParameterList() {
            return sheetParameterList == null ? Collections.singletonList(SheetParameter.empty()) : sheetParameterList;
        }

        public List<CellStyleConsumer> getCellStyleConsumerList() {
            return cellStyleConsumerList == null ? Collections.emptyList() : cellStyleConsumerList;
        }
    }
}
