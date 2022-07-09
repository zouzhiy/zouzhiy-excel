package io.github.zouzhiy.excel.builder;

import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.read.WorkbookRead;
import io.github.zouzhiy.excel.support.metadata.DemoDefault;
import io.github.zouzhiy.excel.support.utils.TestFileUtils;
import io.github.zouzhiy.excel.write.WorkbookWrite;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zouzhiy
 * @since 2022/7/9
 */
class DefaultZouzhiyExcelFactoryTest {

    @Test
    void getConfiguration() {
        Configuration configuration = new Configuration();
        DefaultZouzhiyExcelFactory defaultZouzhiyExcelFactory = new DefaultZouzhiyExcelFactory(configuration);
        assertEquals(configuration, defaultZouzhiyExcelFactory.getConfiguration());
    }

    @Test
    void getWorkbookRead() {
        DefaultZouzhiyExcelFactory defaultZouzhiyExcelFactory = new DefaultZouzhiyExcelFactory(new Configuration());
        WorkbookParameter workbookParameter = WorkbookParameter.builder().build();
        WorkbookRead workbookRead = defaultZouzhiyExcelFactory.getWorkbookRead(workbookParameter, DemoDefault.class);

        assertEquals(workbookParameter, workbookRead.getWorkbookContext().getWorkbookParameter());
    }

    @Test
    void getWorkbookRead2() {
        DefaultZouzhiyExcelFactory defaultZouzhiyExcelFactory = new DefaultZouzhiyExcelFactory(new Configuration());
        WorkbookParameter workbookParameter = WorkbookParameter.builder().build();
        WorkbookRead workbookRead = defaultZouzhiyExcelFactory.getWorkbookRead(workbookParameter, defaultZouzhiyExcelFactory.getConfiguration().getExcelAnnotationParse().findForClass(DemoDefault.class));

        assertEquals(workbookParameter, workbookRead.getWorkbookContext().getWorkbookParameter());
    }

    @Test
    void getWorkbookRead3() {
        DefaultZouzhiyExcelFactory defaultZouzhiyExcelFactory = new DefaultZouzhiyExcelFactory(new Configuration());
        WorkbookParameter workbookParameter = WorkbookParameter.builder().build();
        WorkbookRead workbookRead1 = defaultZouzhiyExcelFactory.getWorkbookRead(workbookParameter, DemoDefault.class);
        WorkbookRead workbookRead2 = defaultZouzhiyExcelFactory.getWorkbookRead(workbookParameter, defaultZouzhiyExcelFactory.getConfiguration().getExcelAnnotationParse().findForClass(DemoDefault.class));

        ExcelClassConfig excelClassConfig1 = workbookRead1.getExcelClassConfig();
        ExcelClassConfig excelClassConfig2 = workbookRead2.getExcelClassConfig();

        assertEquals(excelClassConfig1, excelClassConfig2);
    }

    @Test
    void getWorkbookWrite1() {
        DefaultZouzhiyExcelFactory defaultZouzhiyExcelFactory = new DefaultZouzhiyExcelFactory(new Configuration());
        WorkbookParameter workbookParameter = WorkbookParameter.builder().build();
        WorkbookWrite workbookWrite = defaultZouzhiyExcelFactory.getWorkbookWrite(workbookParameter, DemoDefault.class);

        assertEquals(workbookParameter, workbookWrite.getWorkbookContext().getWorkbookParameter());
    }

    @Test
    void getWorkbookWrite2() {

        DefaultZouzhiyExcelFactory defaultZouzhiyExcelFactory = new DefaultZouzhiyExcelFactory(new Configuration());
        WorkbookParameter workbookParameter = WorkbookParameter.builder().build();
        WorkbookWrite workbookWrite = defaultZouzhiyExcelFactory.getWorkbookWrite(workbookParameter, defaultZouzhiyExcelFactory.getConfiguration().getExcelAnnotationParse().findForClass(DemoDefault.class));

        assertEquals(workbookParameter, workbookWrite.getWorkbookContext().getWorkbookParameter());
    }

    @Test
    void getWorkbookWrite3() {
        DefaultZouzhiyExcelFactory defaultZouzhiyExcelFactory = new DefaultZouzhiyExcelFactory(new Configuration());
        WorkbookParameter workbookParameter = WorkbookParameter.builder().build();
        WorkbookWrite workbookWrite1 = defaultZouzhiyExcelFactory.getWorkbookWrite(workbookParameter, DemoDefault.class);
        WorkbookWrite workbookWrite2 = defaultZouzhiyExcelFactory.getWorkbookWrite(workbookParameter, defaultZouzhiyExcelFactory.getConfiguration().getExcelAnnotationParse().findForClass(DemoDefault.class));

        assertEquals(workbookWrite1.getExcelClassConfig(), workbookWrite2.getExcelClassConfig());
    }

    @Test
    void read1() {
        DefaultZouzhiyExcelFactory defaultZouzhiyExcelFactory = new DefaultZouzhiyExcelFactory(new Configuration()) {
            @Override
            public WorkbookRead getWorkbookRead(WorkbookParameter workbookParameter, Class<?> excelClassConfig) {
                assertNull(workbookParameter.getInputFileName());
                assertNull(workbookParameter.getInputFilePath());
                assertNull(workbookParameter.getInputFile());
                assertNull(workbookParameter.getInputStream());
                return super.getWorkbookRead(workbookParameter, excelClassConfig);
            }
        };
        List<DemoDefault> demoDefaultList = defaultZouzhiyExcelFactory.read().sheet().read(DemoDefault.class);

        assertTrue(demoDefaultList.isEmpty());
    }

    @Test
    void read2() {
        File writeXlsxFile = TestFileUtils.writeXlsxFile(TestFileUtils.getInputStream(TestFileUtils.getEmptyXlsxFilePath()));
        DefaultZouzhiyExcelFactory defaultZouzhiyExcelFactory = new DefaultZouzhiyExcelFactory(new Configuration()) {
            @Override
            public WorkbookRead getWorkbookRead(WorkbookParameter workbookParameter, Class<?> clazz) {
                assertNotNull(workbookParameter.getInputFileName());
                assertNotNull(workbookParameter.getInputFilePath());
                assertNotNull(workbookParameter.getInputFile());
                assertNotNull(workbookParameter.getInputStream());
                assertEquals(workbookParameter.getInputFileName(), writeXlsxFile.getName());
                assertEquals(workbookParameter.getInputFilePath(), writeXlsxFile.getAbsolutePath());
                assertEquals(writeXlsxFile, workbookParameter.getInputFile());
                return super.getWorkbookRead(workbookParameter, clazz);
            }
        };

        List<DemoDefault> demoDefaultList = defaultZouzhiyExcelFactory.read(writeXlsxFile).sheet().read(DemoDefault.class);
        assertTrue(demoDefaultList.isEmpty());
    }

    @Test
    void read3() {
        InputStream inputStream = TestFileUtils.getInputStream(TestFileUtils.getEmptyXlsxFilePath());
        DefaultZouzhiyExcelFactory defaultZouzhiyExcelFactory = new DefaultZouzhiyExcelFactory(new Configuration()) {
            @Override
            public WorkbookRead getWorkbookRead(WorkbookParameter workbookParameter, Class<?> clazz) {
                assertNull(workbookParameter.getInputFileName());
                assertNull(workbookParameter.getInputFilePath());
                assertNull(workbookParameter.getInputFile());
                assertNotNull(workbookParameter.getInputStream());
                assertEquals(inputStream, workbookParameter.getInputStream());
                return super.getWorkbookRead(workbookParameter, clazz);
            }
        };
        List<DemoDefault> demoDefaultList = defaultZouzhiyExcelFactory.read(inputStream).sheet().read(DemoDefault.class);
        assertTrue(demoDefaultList.isEmpty());
    }


    @Test
    void write1() {
        DefaultZouzhiyExcelFactory defaultZouzhiyExcelFactory = new DefaultZouzhiyExcelFactory(new Configuration()) {
            @Override
            public WorkbookWrite getWorkbookWrite(WorkbookParameter workbookParameter, Class<?> excelClassConfig) {
                assertNull(workbookParameter.getOutputFileName());
                assertNull(workbookParameter.getOutputFilePath());
                assertNull(workbookParameter.getOutputFile());
                assertNull(workbookParameter.getOutputStream());
                return super.getWorkbookWrite(workbookParameter, excelClassConfig);
            }

        };
        try {
            defaultZouzhiyExcelFactory.write().sheet().write(Collections.emptyList(), DemoDefault.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert true;
    }

    @Test
    void write2() {
        File file = TestFileUtils.getEmptyXlsxFile();
        DefaultZouzhiyExcelFactory defaultZouzhiyExcelFactory = new DefaultZouzhiyExcelFactory(new Configuration()) {
            @Override
            public WorkbookWrite getWorkbookWrite(WorkbookParameter workbookParameter, Class<?> excelClassConfig) {
                assertNotNull(workbookParameter.getOutputFileName());
                assertNotNull(workbookParameter.getOutputFilePath());
                assertNotNull(workbookParameter.getOutputFile());
                assertNotNull(workbookParameter.getOutputStream());
                return super.getWorkbookWrite(workbookParameter, excelClassConfig);
            }

        };
        defaultZouzhiyExcelFactory.write(file).sheet().write(Collections.emptyList(), DemoDefault.class);

        assert true;
    }

    @Test
    void write3() throws FileNotFoundException {
        File file = TestFileUtils.getEmptyXlsFile();
        OutputStream outputStream = new FileOutputStream(file);
        DefaultZouzhiyExcelFactory defaultZouzhiyExcelFactory = new DefaultZouzhiyExcelFactory(new Configuration()) {
            @Override
            public WorkbookWrite getWorkbookWrite(WorkbookParameter workbookParameter, Class<?> excelClassConfig) {
                assertNull(workbookParameter.getOutputFileName());
                assertNull(workbookParameter.getOutputFilePath());
                assertNull(workbookParameter.getOutputFile());
                assertNotNull(workbookParameter.getOutputStream());
                return super.getWorkbookWrite(workbookParameter, excelClassConfig);
            }

        };
        defaultZouzhiyExcelFactory.write(outputStream).sheet().write(Collections.emptyList(), DemoDefault.class);

        assert true;
    }
}
