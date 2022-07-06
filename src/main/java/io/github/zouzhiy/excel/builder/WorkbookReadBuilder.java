package io.github.zouzhiy.excel.builder;

import io.github.zouzhiy.excel.callback.SheetReadConsumer;
import io.github.zouzhiy.excel.callback.SheetWriteConsumer;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.read.WorkbookRead;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/6
 */
public class WorkbookReadBuilder {

    private final ZouzhiyExcelFactory zouzhiyExcelFactory;

    private final WorkbookParameter.WorkbookParameterBuilder workbookParameterBuilder;

    public WorkbookReadBuilder(ZouzhiyExcelFactory zouzhiyExcelFactory) {
        this.zouzhiyExcelFactory = zouzhiyExcelFactory;
        workbookParameterBuilder = WorkbookParameter.builder();
    }

    public WorkbookReadBuilder input(File inputFile) {
        workbookParameterBuilder.input(inputFile);
        return this;
    }

    public WorkbookReadBuilder input(InputStream inputStream) {
        workbookParameterBuilder.input(inputStream);
        return this;
    }

    public WorkSheetReadBuilder sheet() {
        return new WorkSheetReadBuilder().sheet(0);
    }

    public WorkSheetReadBuilder sheet(String sheetName) {
        return new WorkSheetReadBuilder().sheet(sheetName);
    }

    public WorkSheetReadBuilder sheet(Integer sheetIndex) {
        return new WorkSheetReadBuilder().sheet(sheetIndex);
    }

    public class WorkSheetReadBuilder {

        private final SheetParameter.SheetParameterBuilder sheetParameterBuilder;

        public WorkSheetReadBuilder() {
            this.sheetParameterBuilder = SheetParameter.builder();
        }

        public WorkSheetReadBuilder sheet(String sheetName) {
            sheetParameterBuilder.sheet(sheetName);
            return this;
        }

        public WorkSheetReadBuilder sheet(Integer sheetIndex) {
            sheetParameterBuilder.sheet(sheetIndex);
            return this;
        }

        public WorkSheetReadBuilder title(String title) {
            sheetParameterBuilder.sheet(title);
            return this;
        }

        public WorkSheetReadBuilder titleRowStartIndex(Integer titleRowStartIndex) {
            sheetParameterBuilder.titleRowStartIndex(titleRowStartIndex);
            return this;
        }

        public WorkSheetReadBuilder titleColumnStartIndex(Integer titleColumnStartIndex) {
            sheetParameterBuilder.titleRowStartIndex(titleColumnStartIndex);
            return this;
        }

        public WorkSheetReadBuilder headRowStartIndex(Integer headRowStartIndex) {
            sheetParameterBuilder.titleRowStartIndex(headRowStartIndex);
            return this;
        }

        public WorkSheetReadBuilder headColumnStartIndex(Integer headColumnStartIndex) {
            sheetParameterBuilder.headColumnStartIndex(headColumnStartIndex);
            return this;
        }

        public WorkSheetReadBuilder dataRowStartIndex(Integer dataRowStartIndex) {
            sheetParameterBuilder.dataRowStartIndex(dataRowStartIndex);
            return this;
        }

        public WorkSheetReadBuilder dataColumnStartIndex(Integer dataColumnStartIndex) {
            sheetParameterBuilder.dataColumnStartIndex(dataColumnStartIndex);
            return this;
        }

        public WorkSheetReadBuilder sheetReadConsumer(SheetReadConsumer<?> sheetReadConsumer) {
            sheetParameterBuilder.sheetReadConsumer(sheetReadConsumer);
            return this;
        }

        public WorkSheetReadBuilder sheetWriteConsumer(SheetWriteConsumer<?> sheetWriteConsumer) {
            sheetParameterBuilder.sheetWriteConsumer(sheetWriteConsumer);
            return this;
        }

        public <T> List<T> read(Class<T> clazz) {
            WorkbookParameter workbookParameter = WorkbookReadBuilder.this.workbookParameterBuilder
                    .sheetParameter(this.sheetParameterBuilder.build())
                    .build();
            WorkbookRead workbookRead = WorkbookReadBuilder.this.zouzhiyExcelFactory.getWorkbookRead(workbookParameter, clazz);
            return workbookRead.read(clazz);
        }

    }


}
