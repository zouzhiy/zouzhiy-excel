package io.github.zouzhiy.excel.builder;

import io.github.zouzhiy.excel.callback.CellStyleConsumer;
import io.github.zouzhiy.excel.callback.SheetReadConsumer;
import io.github.zouzhiy.excel.callback.SheetWriteConsumer;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.read.WorkbookRead;
import io.github.zouzhiy.excel.support.metadata.DemoDefault;
import io.github.zouzhiy.excel.support.utils.TestFileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 陆迪
 * @date 2022/7/7
 */
class WorkbookWriteBuilderTest {

    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void template1() {
        File templateFile = TestFileUtils.getTemplateFile(TestFileUtils.getEmptyXlsxFilePath());
        WorkbookWriteBuilder workbookWriteBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertNotNull(workbookParameter.getInputFileName());
            assertNotNull(workbookParameter.getInputFilePath());
            assertNotNull(workbookParameter.getInputFile());
            assertNotNull(workbookParameter.getInputStream());

            assertEquals(templateFile.getName(), workbookParameter.getInputFileName());
            assertEquals(templateFile.getAbsolutePath(), workbookParameter.getInputFilePath());
            assertEquals(templateFile, workbookParameter.getInputFile());

            assertTrue(workbookParameter.isXssf());

        });
        workbookWriteBuilder.template(templateFile).output(TestFileUtils.getOutputStream()).sheet().write(Collections.emptyList(), DemoDefault.class);
    }

    @Test
    void template2() {
        InputStream templateInputStream = TestFileUtils.getTemplateInputStream(TestFileUtils.getEmptyXlsxFilePath());
        WorkbookWriteBuilder workbookWriteBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertNull(workbookParameter.getInputFileName());
            assertNull(workbookParameter.getInputFilePath());
            assertNull(workbookParameter.getInputFile());
            assertNotNull(workbookParameter.getInputStream());

            assertTrue(workbookParameter.isXssf());

        });
        workbookWriteBuilder.template(templateInputStream).output(TestFileUtils.getOutputStream()).sheet().write(Collections.emptyList(), DemoDefault.class);
    }

    @Test
    void output1() {

        WorkbookWriteBuilder workbookWriteBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertNull(workbookParameter.getInputFileName());
            assertNull(workbookParameter.getInputFilePath());
            assertNull(workbookParameter.getInputFile());
            assertNull(workbookParameter.getInputStream());

            assertTrue(workbookParameter.getCellStyleConsumerList().isEmpty());

            assertNull(workbookParameter.getOutputFileName());
            assertNull(workbookParameter.getOutputFilePath());
            assertNull(workbookParameter.getOutputFile());
            assertNull(workbookParameter.getOutputStream());

        });
        try {
            workbookWriteBuilder.sheet().write(Collections.emptyList(), DemoDefault.class);
            assert false;
        } catch (ExcelException e) {
            assert true;
        }
    }

    @Test
    void output2() {
        File outputFile = TestFileUtils.getEmptyXlsFile();

        WorkbookWriteBuilder workbookWriteBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertNull(workbookParameter.getInputFileName());
            assertNull(workbookParameter.getInputFilePath());
            assertNull(workbookParameter.getInputFile());
            assertNull(workbookParameter.getInputStream());

            assertTrue(workbookParameter.getCellStyleConsumerList().isEmpty());

            assertEquals(outputFile.getName(), workbookParameter.getOutputFileName());
            assertEquals(outputFile.getAbsolutePath(), workbookParameter.getOutputFilePath());
            assertEquals(outputFile, workbookParameter.getOutputFile());
            assertNotNull(workbookParameter.getOutputStream());

        });
        workbookWriteBuilder.output(outputFile).sheet().write(Collections.emptyList(), DemoDefault.class);
    }

    @Test
    void output3() {
        OutputStream outputStream = TestFileUtils.getOutputStream();

        WorkbookWriteBuilder workbookWriteBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertNull(workbookParameter.getInputFileName());
            assertNull(workbookParameter.getInputFilePath());
            assertNull(workbookParameter.getInputFile());
            assertNull(workbookParameter.getInputStream());


            assertNull(workbookParameter.getOutputFileName());
            assertNull(workbookParameter.getOutputFilePath());
            assertNull(workbookParameter.getOutputFile());
            assertNotNull(workbookParameter.getOutputStream());

            assertTrue(workbookParameter.getCellStyleConsumerList().isEmpty());

        });
        workbookWriteBuilder.output(outputStream).sheet().write(Collections.emptyList(), DemoDefault.class);
    }


    @Test
    void cellStyleConsumer1() {
        CellStyleConsumer cellStyleConsumer = new CellStyleConsumer() {
        };
        OutputStream outputStream = TestFileUtils.getOutputStream();

        WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertEquals(workbookParameter.getSheetParameter().getSheetIndex(), 0);
            assertNull(workbookParameter.getSheetParameter().getSheetName());
            assertNull(workbookParameter.getSheetParameter().getTitle());

            assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), 0);

            assertTrue(workbookParameter.getSheetParameter().getSheetReadConsumerList().isEmpty());
            assertTrue(workbookParameter.getSheetParameter().getSheetWriteConsumerList().isEmpty());

            assertEquals(cellStyleConsumer, workbookParameter.getCellStyleConsumerList().get(0));

            assert DemoDefault.class.equals(aClass);

        });
        workbookReadBuilder.output(outputStream).cellStyleConsumer(cellStyleConsumer).sheet().write(Collections.emptyList(), DemoDefault.class);
    }

    @Test
    void cellStyleConsumer2() {
        List<CellStyleConsumer> cellStyleConsumerList = new ArrayList<>();
        for (int i = 0; i < random.nextInt(30); i++) {
            CellStyleConsumer cellStyleConsumer = new CellStyleConsumer() {
            };
            cellStyleConsumerList.add(cellStyleConsumer);
        }

        OutputStream outputStream = TestFileUtils.getOutputStream();

        WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {

            assertEquals(workbookParameter.getSheetParameter().getSheetIndex(), 0);
            assertNull(workbookParameter.getSheetParameter().getSheetName());
            assertNull(workbookParameter.getSheetParameter().getTitle());

            assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), 0);

            assertTrue(workbookParameter.getSheetParameter().getSheetReadConsumerList().isEmpty());
            assertTrue(workbookParameter.getSheetParameter().getSheetWriteConsumerList().isEmpty());
            assert DemoDefault.class.equals(aClass);
        });
        workbookReadBuilder = workbookReadBuilder.output(outputStream);
        cellStyleConsumerList.forEach(workbookReadBuilder::cellStyleConsumer);
        workbookReadBuilder.sheet().write(Collections.emptyList(), DemoDefault.class);
        assert true;
    }

    @Test
    void sheet1() {
        OutputStream outputStream = TestFileUtils.getOutputStream();

        WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertEquals(workbookParameter.getSheetParameter().getSheetIndex(), 0);
            assertNull(workbookParameter.getSheetParameter().getSheetName());

            assertNull(workbookParameter.getSheetParameter().getTitle());
            assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), 0);

            assertTrue(workbookParameter.getSheetParameter().getSheetReadConsumerList().isEmpty());
            assertTrue(workbookParameter.getSheetParameter().getSheetWriteConsumerList().isEmpty());
        });
        workbookReadBuilder.output(outputStream).sheet().write(Collections.emptyList(), DemoDefault.class);
        assert true;
    }

    @Test
    void sheet2() {
        String sheetName = new Random(System.currentTimeMillis()).nextInt(999) + "name";
        OutputStream outputStream = TestFileUtils.getOutputStream();

        WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertNull(workbookParameter.getSheetParameter().getSheetIndex());
            assertEquals(workbookParameter.getSheetParameter().getSheetName(), sheetName);

            assertNull(workbookParameter.getSheetParameter().getTitle());
            assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), 0);

            assertTrue(workbookParameter.getSheetParameter().getSheetReadConsumerList().isEmpty());
            assertTrue(workbookParameter.getSheetParameter().getSheetWriteConsumerList().isEmpty());
        });
        workbookReadBuilder.output(outputStream).sheet(sheetName).write(Collections.emptyList(), DemoDefault.class);
        assert true;
    }

    @Test
    void sheet3() {
        Integer sheetIndex = new Random(System.currentTimeMillis()).nextInt(999);
        OutputStream outputStream = TestFileUtils.getOutputStream();

        WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertEquals(workbookParameter.getSheetParameter().getSheetIndex(), sheetIndex);
            assertNull(workbookParameter.getSheetParameter().getSheetName());
            assertNull(workbookParameter.getSheetParameter().getTitle());
            assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), 0);

            assertTrue(workbookParameter.getSheetParameter().getSheetReadConsumerList().isEmpty());
            assertTrue(workbookParameter.getSheetParameter().getSheetWriteConsumerList().isEmpty());
        });
        workbookReadBuilder.output(outputStream).sheet(sheetIndex).write(Collections.emptyList(), DemoDefault.class);
        assert true;
    }


    @Test
    void title() {
        String title = new Random(System.currentTimeMillis()).nextInt(999) + "title";

        OutputStream outputStream = TestFileUtils.getOutputStream();

        WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertEquals(workbookParameter.getSheetParameter().getSheetIndex(), 0);
            assertNull(workbookParameter.getSheetParameter().getSheetName());

            assertEquals(workbookParameter.getSheetParameter().getTitle(), title);

            assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), 0);

            assertTrue(workbookParameter.getSheetParameter().getSheetReadConsumerList().isEmpty());
            assertTrue(workbookParameter.getSheetParameter().getSheetWriteConsumerList().isEmpty());
        });
        workbookReadBuilder.output(outputStream).sheet().title(title).write(Collections.emptyList(), DemoDefault.class);
        assert true;
    }

    @Test
    void titleRowStartIndex() {
        Integer titleRowStartIndex = random.nextInt(88);
        OutputStream outputStream = TestFileUtils.getOutputStream();

        WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertEquals(workbookParameter.getSheetParameter().getSheetIndex(), 0);
            assertNull(workbookParameter.getSheetParameter().getSheetName());
            assertNull(workbookParameter.getSheetParameter().getTitle());

            assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), titleRowStartIndex);

            assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), 0);

            assertTrue(workbookParameter.getSheetParameter().getSheetReadConsumerList().isEmpty());
            assertTrue(workbookParameter.getSheetParameter().getSheetWriteConsumerList().isEmpty());
        });
        try {
            workbookReadBuilder.output(outputStream).sheet().titleRowStartIndex(titleRowStartIndex).write(Collections.emptyList(), DemoDefault.class);
        } catch (Exception ignore) {
        }
    }

    @Test
    void titleColumnStartIndex() {
        Integer titleColumnStartIndex = random.nextInt(88);
        OutputStream outputStream = TestFileUtils.getOutputStream();

        WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertEquals(workbookParameter.getSheetParameter().getSheetIndex(), 0);
            assertNull(workbookParameter.getSheetParameter().getSheetName());
            assertNull(workbookParameter.getSheetParameter().getTitle());

            assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), -1);

            assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), titleColumnStartIndex);

            assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), 0);

            assertTrue(workbookParameter.getSheetParameter().getSheetReadConsumerList().isEmpty());
            assertTrue(workbookParameter.getSheetParameter().getSheetWriteConsumerList().isEmpty());
        });
        try {
            workbookReadBuilder.output(outputStream).sheet().titleColumnStartIndex(titleColumnStartIndex).write(Collections.emptyList(), DemoDefault.class);
        } catch (Exception ignore) {
        }
    }

    @Test
    void headRowStartIndex() {
        Integer headRowStartIndex = random.nextInt(88);
        OutputStream outputStream = TestFileUtils.getOutputStream();

        WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertEquals(workbookParameter.getSheetParameter().getSheetIndex(), 0);
            assertNull(workbookParameter.getSheetParameter().getSheetName());
            assertNull(workbookParameter.getSheetParameter().getTitle());

            assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), headRowStartIndex > 0 ? 0 : -1);
            assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), headRowStartIndex);
            assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), 0);

            assertTrue(workbookParameter.getSheetParameter().getSheetReadConsumerList().isEmpty());
            assertTrue(workbookParameter.getSheetParameter().getSheetWriteConsumerList().isEmpty());
        });
        try {
            workbookReadBuilder.output(outputStream).sheet().headRowStartIndex(headRowStartIndex).write(Collections.emptyList(), DemoDefault.class);
        } catch (Exception ignore) {
        }
    }

    @Test
    void headColumnStartIndex() {
        Integer headColumnStartIndex = random.nextInt(88);
        OutputStream outputStream = TestFileUtils.getOutputStream();

        WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertEquals(workbookParameter.getSheetParameter().getSheetIndex(), 0);
            assertNull(workbookParameter.getSheetParameter().getSheetName());
            assertNull(workbookParameter.getSheetParameter().getTitle());

            assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), headColumnStartIndex);
            assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), 0);

            assertTrue(workbookParameter.getSheetParameter().getSheetReadConsumerList().isEmpty());
            assertTrue(workbookParameter.getSheetParameter().getSheetWriteConsumerList().isEmpty());
        });
        try {
            workbookReadBuilder.output(outputStream).sheet().headColumnStartIndex(headColumnStartIndex).write(Collections.emptyList(), DemoDefault.class);
        } catch (Exception ignore) {
        }
    }

    @Test
    void dataRowStartIndex() {
        for (int i = 0; i < random.nextInt(20); i++) {
            final int dataRowStartIndex = i;
            OutputStream outputStream = TestFileUtils.getOutputStream();

            WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
                assertEquals(workbookParameter.getSheetParameter().getSheetIndex(), 0);
                assertNull(workbookParameter.getSheetParameter().getSheetName());
                assertNull(workbookParameter.getSheetParameter().getTitle());

                assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), dataRowStartIndex > 1 ? 0 : -1);
                assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), 0);

                assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), dataRowStartIndex - 1);
                assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), 0);

                assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), dataRowStartIndex);
                assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), 0);

                assertTrue(workbookParameter.getSheetParameter().getSheetReadConsumerList().isEmpty());
                assertTrue(workbookParameter.getSheetParameter().getSheetWriteConsumerList().isEmpty());
            });
            try {
                workbookReadBuilder.output(outputStream).sheet().dataRowStartIndex(dataRowStartIndex).write(Collections.emptyList(), DemoDefault.class);
            } catch (Exception ignore) {
            }
        }

    }

    @Test
    void dataColumnStartIndex() {
        for (int i = 0; i < random.nextInt(20); i++) {
            final int dataColumnStartIndex = i;
            OutputStream outputStream = TestFileUtils.getOutputStream();

            WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
                assertEquals(workbookParameter.getSheetParameter().getSheetIndex(), 0);
                assertNull(workbookParameter.getSheetParameter().getSheetName());
                assertNull(workbookParameter.getSheetParameter().getTitle());

                assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), -1);
                assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), 0);

                assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), -1);
                assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), 0);

                assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), 0);
                assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), dataColumnStartIndex);

                assertTrue(workbookParameter.getSheetParameter().getSheetReadConsumerList().isEmpty());
                assertTrue(workbookParameter.getSheetParameter().getSheetWriteConsumerList().isEmpty());
                assert DemoDefault.class.equals(aClass);
            });
            workbookReadBuilder.output(outputStream).sheet().dataColumnStartIndex(dataColumnStartIndex).write(Collections.emptyList(), DemoDefault.class);
            assert true;
        }
    }

    @Test
    void sheetReadConsumer1() {

        SheetReadConsumer<DemoDefault> sheetReadConsumer = new SheetReadConsumer<DemoDefault>() {
        };
        OutputStream outputStream = TestFileUtils.getOutputStream();

        WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertEquals(workbookParameter.getSheetParameter().getSheetIndex(), 0);
            assertNull(workbookParameter.getSheetParameter().getSheetName());
            assertNull(workbookParameter.getSheetParameter().getTitle());

            assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), 0);

            assertEquals(sheetReadConsumer, workbookParameter.getSheetParameter().getSheetReadConsumerList().get(0));
            assertEquals(workbookParameter.getSheetParameter().getSheetReadConsumerList().size(), 1);
            assertTrue(workbookParameter.getSheetParameter().getSheetWriteConsumerList().isEmpty());
            assert DemoDefault.class.equals(aClass);
        });
        workbookReadBuilder.output(outputStream).sheet().sheetReadConsumer(sheetReadConsumer).write(Collections.emptyList(), DemoDefault.class);
        assert true;

    }

    @Test
    void sheetReadConsumer2() {

        List<SheetReadConsumer<DemoDefault>> sheetReadConsumerList = new ArrayList<>();
        for (int i = 0; i < random.nextInt(30); i++) {
            SheetReadConsumer<DemoDefault> sheetReadConsumer = new SheetReadConsumer<DemoDefault>() {
            };
            sheetReadConsumerList.add(sheetReadConsumer);
        }

        OutputStream outputStream = TestFileUtils.getOutputStream();

        WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertEquals(workbookParameter.getSheetParameter().getSheetIndex(), 0);
            assertNull(workbookParameter.getSheetParameter().getSheetName());
            assertNull(workbookParameter.getSheetParameter().getTitle());

            assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getSheetReadConsumerList().size(), sheetReadConsumerList.size());
            assertTrue(workbookParameter.getSheetParameter().getSheetWriteConsumerList().isEmpty());
            assert DemoDefault.class.equals(aClass);
        });
        WorkbookWriteBuilder.WorkSheetWriteBuilder workSheetReadBuilder = workbookReadBuilder.output(outputStream).sheet();
        sheetReadConsumerList.forEach(workSheetReadBuilder::sheetReadConsumer);
        workSheetReadBuilder.write(Collections.emptyList(), DemoDefault.class);
        assert true;
    }

    @Test
    void sheetWriteConsumer1() {
        SheetWriteConsumer<DemoDefault> sheetWriteConsumer = new SheetWriteConsumer<DemoDefault>() {
        };
        OutputStream outputStream = TestFileUtils.getOutputStream();

        WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertEquals(workbookParameter.getSheetParameter().getSheetIndex(), 0);
            assertNull(workbookParameter.getSheetParameter().getSheetName());
            assertNull(workbookParameter.getSheetParameter().getTitle());

            assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), 0);

            assertTrue(workbookParameter.getSheetParameter().getSheetReadConsumerList().isEmpty());
            assertEquals(workbookParameter.getSheetParameter().getSheetWriteConsumerList().size(), 1);

            assertEquals(sheetWriteConsumer, workbookParameter.getSheetParameter().getSheetWriteConsumerList().get(0));

            assert DemoDefault.class.equals(aClass);

        });
        workbookReadBuilder.output(outputStream).sheet().sheetWriteConsumer(sheetWriteConsumer).write(Collections.emptyList(), DemoDefault.class);
    }

    @Test
    void sheetWriteConsumer2() {
        List<SheetWriteConsumer<DemoDefault>> sheetWriteConsumerList = new ArrayList<>();
        for (int i = 0; i < random.nextInt(30); i++) {
            SheetWriteConsumer<DemoDefault> sheetReadConsumer = new SheetWriteConsumer<DemoDefault>() {
            };
            sheetWriteConsumerList.add(sheetReadConsumer);
        }

        OutputStream outputStream = TestFileUtils.getOutputStream();

        WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertEquals(workbookParameter.getSheetParameter().getSheetIndex(), 0);
            assertNull(workbookParameter.getSheetParameter().getSheetName());
            assertNull(workbookParameter.getSheetParameter().getTitle());

            assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), 0);

            assertTrue(workbookParameter.getSheetParameter().getSheetReadConsumerList().isEmpty());
            assertEquals(workbookParameter.getSheetParameter().getSheetWriteConsumerList().size(), sheetWriteConsumerList.size());

            assert DemoDefault.class.equals(aClass);
        });
        WorkbookWriteBuilder.WorkSheetWriteBuilder workSheetReadBuilder = workbookReadBuilder.output(outputStream).sheet();
        sheetWriteConsumerList.forEach(workSheetReadBuilder::sheetWriteConsumer);
        workSheetReadBuilder.write(Collections.emptyList(), DemoDefault.class);
        assert true;
    }

    @Test
    void write() {
        OutputStream outputStream = TestFileUtils.getOutputStream();
        WorkbookWriteBuilder workbookReadBuilder = this.getWorkbookWriteBuilder((workbookParameter, aClass) -> {
            assertEquals(workbookParameter.getSheetParameter().getSheetIndex(), 0);
            assertNull(workbookParameter.getSheetParameter().getSheetName());
            assertNull(workbookParameter.getSheetParameter().getTitle());

            assertEquals(workbookParameter.getSheetParameter().getTitleRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getTitleColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getHeadRowStartIndex(), -1);
            assertEquals(workbookParameter.getSheetParameter().getHeadColumnStartIndex(), 0);

            assertEquals(workbookParameter.getSheetParameter().getDataRowStartIndex(), 0);
            assertEquals(workbookParameter.getSheetParameter().getDataColumnStartIndex(), 0);

            assertTrue(workbookParameter.getSheetParameter().getSheetReadConsumerList().isEmpty());
            assertTrue(workbookParameter.getSheetParameter().getSheetWriteConsumerList().isEmpty());

            assert DemoDefault.class.equals(aClass);
        });
        workbookReadBuilder.output(outputStream).sheet().write(Collections.emptyList(), DemoDefault.class);
        assert true;
    }

    private WorkbookWriteBuilder getWorkbookWriteBuilder(BiConsumer<WorkbookParameter, Class<?>> biConsumer) {
        return new WorkbookWriteBuilder(this.getDefaultZouzhiyExcelFactory(biConsumer));
    }

    private DefaultZouzhiyExcelFactory getDefaultZouzhiyExcelFactory(BiConsumer<WorkbookParameter, Class<?>>
                                                                             biConsumer) {
        return new DefaultZouzhiyExcelFactory(new Configuration()) {
            @Override
            public WorkbookRead getWorkbookRead(WorkbookParameter workbookParameter, Class<?> clazz) {
                biConsumer.accept(workbookParameter, clazz);
                return super.getWorkbookRead(workbookParameter, clazz);
            }
        };
    }


}
