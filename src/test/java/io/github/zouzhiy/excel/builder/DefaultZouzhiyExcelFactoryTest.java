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