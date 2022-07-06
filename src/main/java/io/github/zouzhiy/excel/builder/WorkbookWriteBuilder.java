package io.github.zouzhiy.excel.builder;

import io.github.zouzhiy.excel.callback.SheetReadConsumer;
import io.github.zouzhiy.excel.callback.SheetWriteConsumer;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.write.WorkbookWrite;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/6
 */
public class WorkbookWriteBuilder {

    private final ZouzhiyExcelFactory zouzhiyExcelFactory;

    private final WorkbookParameter.WorkbookParameterBuilder workbookParameterBuilder;

    public WorkbookWriteBuilder(ZouzhiyExcelFactory zouzhiyExcelFactory) {
        this.zouzhiyExcelFactory = zouzhiyExcelFactory;
        workbookParameterBuilder = WorkbookParameter.builder();
    }

    public WorkbookWriteBuilder template(File templateFile) {
        workbookParameterBuilder.input(templateFile);
        return this;
    }

    public WorkbookWriteBuilder template(InputStream templateInputStream) {
        workbookParameterBuilder.input(templateInputStream);
        return this;
    }


    public WorkbookWriteBuilder output(File outputFile) {
        workbookParameterBuilder.output(outputFile);

        return this;
    }

    public WorkbookWriteBuilder output(OutputStream outputStream) {
        workbookParameterBuilder.output(outputStream);
        return this;
    }

    public WorkbookWriteBuilder xssf(boolean xssf) {
        workbookParameterBuilder.xssf(xssf);
        return this;
    }


    public WorkSheetWriteBuilder sheet() {
        return new WorkSheetWriteBuilder().sheet(0);
    }

    public WorkSheetWriteBuilder sheet(String sheetName) {
        return new WorkSheetWriteBuilder().sheet(sheetName);
    }

    public WorkSheetWriteBuilder sheet(Integer sheetIndex) {
        return new WorkSheetWriteBuilder().sheet(sheetIndex);
    }

    public class WorkSheetWriteBuilder {

        private final SheetParameter.SheetParameterBuilder sheetParameterBuilder;

        public WorkSheetWriteBuilder() {
            this.sheetParameterBuilder = SheetParameter.builder();
        }

        public WorkSheetWriteBuilder sheet(String sheetName) {
            sheetParameterBuilder.sheet(sheetName);
            return this;
        }

        public WorkSheetWriteBuilder sheet(Integer sheetIndex) {
            sheetParameterBuilder.sheet(sheetIndex);
            return this;
        }

        public WorkSheetWriteBuilder title(String title) {
            sheetParameterBuilder.sheet(title);
            return this;
        }

        public WorkSheetWriteBuilder titleRowStartIndex(Integer titleRowStartIndex) {
            sheetParameterBuilder.titleRowStartIndex(titleRowStartIndex);
            return this;
        }

        public WorkSheetWriteBuilder titleColumnStartIndex(Integer titleColumnStartIndex) {
            sheetParameterBuilder.titleRowStartIndex(titleColumnStartIndex);
            return this;
        }

        public WorkSheetWriteBuilder headRowStartIndex(Integer headRowStartIndex) {
            sheetParameterBuilder.titleRowStartIndex(headRowStartIndex);
            return this;
        }

        public WorkSheetWriteBuilder headColumnStartIndex(Integer headColumnStartIndex) {
            sheetParameterBuilder.headColumnStartIndex(headColumnStartIndex);
            return this;
        }

        public WorkSheetWriteBuilder dataRowStartIndex(Integer dataRowStartIndex) {
            sheetParameterBuilder.dataRowStartIndex(dataRowStartIndex);
            return this;
        }

        public WorkSheetWriteBuilder dataColumnStartIndex(Integer dataColumnStartIndex) {
            sheetParameterBuilder.dataColumnStartIndex(dataColumnStartIndex);
            return this;
        }

        public WorkSheetWriteBuilder sheetReadConsumer(SheetReadConsumer<?> sheetReadConsumer) {
            sheetParameterBuilder.sheetReadConsumer(sheetReadConsumer);
            return this;
        }

        public WorkSheetWriteBuilder sheetWriteConsumer(SheetWriteConsumer<?> sheetWriteConsumer) {
            sheetParameterBuilder.sheetWriteConsumer(sheetWriteConsumer);
            return this;
        }

        public <T> void write(List<T> itemList, Class<T> clazz) {
            WorkbookParameter workbookParameter = WorkbookWriteBuilder.this.workbookParameterBuilder
                    .sheetParameter(this.sheetParameterBuilder.build())
                    .build();

            WorkbookWrite workbookWrite = WorkbookWriteBuilder.this.zouzhiyExcelFactory.getWorkbookWrite(workbookParameter, clazz);
            workbookWrite.write(itemList);
        }

    }
}
