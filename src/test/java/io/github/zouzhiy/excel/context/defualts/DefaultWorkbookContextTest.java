package io.github.zouzhiy.excel.context.defualts;

import io.github.zouzhiy.excel.builder.Demo;
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