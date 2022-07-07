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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 陆迪
 * @date 2022/7/7
 */
class WorkbookReadBuilderTest {

    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void input1() {
        File file = TestFileUtils.getEmptyXlsFile();

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
            assertTrue(workbookParameter.getCellStyleConsumerList().isEmpty());

            assertEquals(file.getName(), workbookParameter.getInputFileName());
            assertEquals(file.getAbsolutePath(), workbookParameter.getInputFilePath());
            assertEquals(file, workbookParameter.getInputFile());
            assertNotNull(workbookParameter.getInputStream());

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
        try {
            workbookReadBuilder.input(file).sheet().read(DemoDefault.class);
            assert false;
        } catch (Exception e) {
            assert true;
        }

    }

    @Test
    void input2() {
        File file = TestFileUtils.writeXlsxFile(TestFileUtils.getInputStream(this.getEmptyXlsxFilePath()));

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
            assertTrue(workbookParameter.getCellStyleConsumerList().isEmpty());

            assertEquals(file.getName(), workbookParameter.getInputFileName());
            assertEquals(file.getAbsolutePath(), workbookParameter.getInputFilePath());
            assertEquals(file, workbookParameter.getInputFile());
            assertNotNull(workbookParameter.getInputStream());

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
        workbookReadBuilder.input(file).sheet().read(DemoDefault.class);

        assert true;
    }

    @Test
    void input3() {
        InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
            assertNull(workbookParameter.getInputFileName());
            assertNull(workbookParameter.getInputFilePath());
            assertNull(workbookParameter.getInputFile());
            assertNotNull(workbookParameter.getInputStream());

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
        List<DemoDefault> demoDefaultList = workbookReadBuilder.input(inputStream).sheet().read(DemoDefault.class);
        assertTrue(demoDefaultList.isEmpty());
        assert true;
    }

    @Test
    void cellStyleConsumer1() {
        CellStyleConsumer cellStyleConsumer = new CellStyleConsumer() {
        };
        InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
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
        workbookReadBuilder.input(inputStream).cellStyleConsumer(cellStyleConsumer).sheet().read(DemoDefault.class);
    }

    @Test
    void cellStyleConsumer2() {
        List<CellStyleConsumer> cellStyleConsumerList = new ArrayList<>();
        for (int i = 0; i < random.nextInt(30); i++) {
            CellStyleConsumer cellStyleConsumer = new CellStyleConsumer() {
            };
            cellStyleConsumerList.add(cellStyleConsumer);
        }

        InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {

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
        workbookReadBuilder = workbookReadBuilder.input(inputStream);
        cellStyleConsumerList.forEach(workbookReadBuilder::cellStyleConsumer);
        List<DemoDefault> demoDefaultList = workbookReadBuilder.sheet().read(DemoDefault.class);
        assertTrue(demoDefaultList.isEmpty());
    }

    @Test
    void sheet1() {
        InputStream inputStream = TestFileUtils.getInputStream("statics/import/empty.xlsx");

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
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
        List<DemoDefault> demoDefaultList = workbookReadBuilder.input(inputStream).sheet().read(DemoDefault.class);
        assertTrue(demoDefaultList.isEmpty());
        assert true;
    }

    @Test
    void sheet2() {
        String sheetName = new Random(System.currentTimeMillis()).nextInt(999) + "name";
        InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
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
        List<DemoDefault> demoDefaultList = workbookReadBuilder.input(inputStream).sheet(sheetName).read(DemoDefault.class);
        assertTrue(demoDefaultList.isEmpty());
        assert true;
    }

    @Test
    void sheet3() {
        Integer sheetIndex = new Random(System.currentTimeMillis()).nextInt(999);
        InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
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
        List<DemoDefault> demoDefaultList = workbookReadBuilder.input(inputStream).sheet(sheetIndex).read(DemoDefault.class);
        assertTrue(demoDefaultList.isEmpty());
        assert true;
    }


    @Test
    void title() {
        String title = new Random(System.currentTimeMillis()).nextInt(999) + "title";

        InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
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
        List<DemoDefault> demoDefaultList = workbookReadBuilder.input(inputStream).sheet().title(title).read(DemoDefault.class);
        assertTrue(demoDefaultList.isEmpty());
        assert true;
    }

    @Test
    void titleRowStartIndex() {
        Integer titleRowStartIndex = random.nextInt(88);
        InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
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
            workbookReadBuilder.input(inputStream).sheet().titleRowStartIndex(titleRowStartIndex).read(DemoDefault.class);
        } catch (Exception ignore) {
        }
    }

    @Test
    void titleColumnStartIndex() {
        Integer titleColumnStartIndex = random.nextInt(88);
        InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
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
            workbookReadBuilder.input(inputStream).sheet().titleColumnStartIndex(titleColumnStartIndex).read(DemoDefault.class);
        } catch (Exception ignore) {
        }
    }

    @Test
    void headRowStartIndex() {
        Integer headRowStartIndex = random.nextInt(88);
        InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
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
            workbookReadBuilder.input(inputStream).sheet().headRowStartIndex(headRowStartIndex).read(DemoDefault.class);
        } catch (Exception ignore) {
        }
    }

    @Test
    void headColumnStartIndex() {
        Integer headColumnStartIndex = random.nextInt(88);
        InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
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
            workbookReadBuilder.input(inputStream).sheet().headColumnStartIndex(headColumnStartIndex).read(DemoDefault.class);
        } catch (Exception ignore) {
        }
    }

    @Test
    void dataRowStartIndex() {
        for (int i = 0; i < random.nextInt(20); i++) {
            final int dataRowStartIndex = i;
            InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());

            WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
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
                workbookReadBuilder.input(inputStream).sheet().dataRowStartIndex(dataRowStartIndex).read(DemoDefault.class);
            } catch (Exception ignore) {
            }
        }

    }

    @Test
    void dataColumnStartIndex() {
        for (int i = 0; i < random.nextInt(20); i++) {
            final int dataColumnStartIndex = i;
            InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());

            WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
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
            List<DemoDefault> demoDefaultList = workbookReadBuilder.input(inputStream).sheet().dataColumnStartIndex(dataColumnStartIndex).read(DemoDefault.class);
            assertTrue(demoDefaultList.isEmpty());
        }
    }

    @Test
    void sheetReadConsumer1() {

        SheetReadConsumer<DemoDefault> sheetReadConsumer = new SheetReadConsumer<DemoDefault>() {
        };
        InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
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
        List<DemoDefault> demoDefaultList = workbookReadBuilder.input(inputStream).sheet().sheetReadConsumer(sheetReadConsumer).read(DemoDefault.class);
        assertTrue(demoDefaultList.isEmpty());

    }

    @Test
    void sheetReadConsumer2() {

        List<SheetReadConsumer<DemoDefault>> sheetReadConsumerList = new ArrayList<>();
        for (int i = 0; i < random.nextInt(30); i++) {
            SheetReadConsumer<DemoDefault> sheetReadConsumer = new SheetReadConsumer<DemoDefault>() {
            };
            sheetReadConsumerList.add(sheetReadConsumer);
        }

        InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
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
        WorkbookReadBuilder.WorkSheetReadBuilder workSheetReadBuilder = workbookReadBuilder.input(inputStream).sheet();
        sheetReadConsumerList.forEach(workSheetReadBuilder::sheetReadConsumer);
        List<DemoDefault> demoDefaultList = workSheetReadBuilder.read(DemoDefault.class);
        assertTrue(demoDefaultList.isEmpty());
    }

    @Test
    void sheetWriteConsumer1() {
        SheetWriteConsumer<DemoDefault> sheetWriteConsumer = new SheetWriteConsumer<DemoDefault>() {
        };
        InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
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
        workbookReadBuilder.input(inputStream).sheet().sheetWriteConsumer(sheetWriteConsumer).read(DemoDefault.class);
    }

    @Test
    void sheetWriteConsumer2() {
        List<SheetWriteConsumer<DemoDefault>> sheetWriteConsumerList = new ArrayList<>();
        for (int i = 0; i < random.nextInt(30); i++) {
            SheetWriteConsumer<DemoDefault> sheetReadConsumer = new SheetWriteConsumer<DemoDefault>() {
            };
            sheetWriteConsumerList.add(sheetReadConsumer);
        }

        InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());

        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
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
        WorkbookReadBuilder.WorkSheetReadBuilder workSheetReadBuilder = workbookReadBuilder.input(inputStream).sheet();
        sheetWriteConsumerList.forEach(workSheetReadBuilder::sheetWriteConsumer);
        List<DemoDefault> demoDefaultList = workSheetReadBuilder.read(DemoDefault.class);
        assertTrue(demoDefaultList.isEmpty());
    }

    @Test
    void read() {
        InputStream inputStream = TestFileUtils.getInputStream(this.getEmptyXlsxFilePath());
        WorkbookReadBuilder workbookReadBuilder = this.getWorkbookReadBuilder((workbookParameter, aClass) -> {
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
        List<DemoDefault> demoDefaultList = workbookReadBuilder.input(inputStream).sheet().read(DemoDefault.class);
        assertTrue(demoDefaultList.isEmpty());
    }

    private WorkbookReadBuilder getWorkbookReadBuilder(BiConsumer<WorkbookParameter, Class<?>> biConsumer) {
        return new WorkbookReadBuilder(this.getDefaultZouzhiyExcelFactory(biConsumer));
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

    private String getEmptyXlsxFilePath() {
        return "statics/import/empty.xlsx";
    }

}
