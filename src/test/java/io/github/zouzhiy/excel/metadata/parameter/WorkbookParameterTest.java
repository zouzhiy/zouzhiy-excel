package io.github.zouzhiy.excel.metadata.parameter;

import io.github.zouzhiy.excel.exceptions.ExcelException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class WorkbookParameterTest {

    @Test
    void input1() {
        WorkbookParameter workbookParameter = WorkbookParameter
                .builder()
                .build();

        Assertions.assertNull(workbookParameter.getInputFileName());
        Assertions.assertNull(workbookParameter.getInputFilePath());
        Assertions.assertNull(workbookParameter.getInputFile());
        Assertions.assertNull(workbookParameter.getInputStream());

        Assertions.assertTrue(workbookParameter.isXssf());
    }

    @Test
    void input2() {
        String testPath = this.getClass().getResource("/").getPath();
        Assertions.assertThrows(ExcelException.class, () -> WorkbookParameter
                .builder()
                .input(new File(testPath))
                .build());
    }

    @Test
    void input3() throws IOException {
        String testPath = this.getClass().getResource("/").getPath();
        String tmpFile = System.currentTimeMillis() + ".xlsx";
        File file = new File(testPath + File.separator + tmpFile);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        }
        WorkbookParameter workbookParameter = WorkbookParameter
                .builder()
                .input(file)
                .build();

        Assertions.assertEquals(workbookParameter.getInputFileName(), file.getName());
        Assertions.assertEquals(workbookParameter.getInputFilePath(), file.getAbsolutePath());
        Assertions.assertEquals(workbookParameter.getInputFile(), file);
        Assertions.assertNotNull(workbookParameter.getInputStream());

        Assertions.assertTrue(workbookParameter.isXssf());
    }

    @Test
    void input4() throws IOException {
        String testPath = this.getClass().getResource("/").getPath();
        String tmpFile = System.currentTimeMillis() + ".xls";
        File file = new File(testPath + File.separator + tmpFile);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        }
        WorkbookParameter workbookParameter = WorkbookParameter
                .builder()
                .input(new FileInputStream(file))
                .build();

        Assertions.assertNull(workbookParameter.getInputFileName());
        Assertions.assertNull(workbookParameter.getInputFilePath());
        Assertions.assertNull(workbookParameter.getInputFile());
        Assertions.assertNotNull(workbookParameter.getInputStream());

        Assertions.assertTrue(workbookParameter.isXssf());
    }


    @Test
    void output1() {
        WorkbookParameter workbookParameter = WorkbookParameter
                .builder()
                .build();

        Assertions.assertNull(workbookParameter.getOutputFileName());
        Assertions.assertNull(workbookParameter.getOutputFilePath());
        Assertions.assertNull(workbookParameter.getOutputFile());
        Assertions.assertNull(workbookParameter.getOutputStream());

        Assertions.assertTrue(workbookParameter.isXssf());

    }

    @Test
    void output2() {
        String testPath = this.getClass().getResource("/").getPath();
        Assertions.assertThrows(ExcelException.class, () -> WorkbookParameter
                .builder()
                .output(new File(testPath))
                .build());
    }

    @Test
    void output3() throws IOException {
        String testPath = this.getClass().getResource("/").getPath();
        String tmpFile = System.currentTimeMillis() + ".xls";
        File file = new File(testPath + File.separator + tmpFile);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        }
        WorkbookParameter workbookParameter = WorkbookParameter
                .builder()
                .output(file)
                .build();

        Assertions.assertEquals(workbookParameter.getOutputFileName(), file.getName());
        Assertions.assertEquals(workbookParameter.getOutputFilePath(), file.getAbsolutePath());
        Assertions.assertEquals(workbookParameter.getOutputFile(), file);
        Assertions.assertNotNull(workbookParameter.getOutputStream());

        Assertions.assertFalse(workbookParameter.isXssf());
    }

    @Test
    void output4() throws IOException {
        String testPath = this.getClass().getResource("/").getPath();
        String tmpFile = System.currentTimeMillis() + ".xls";
        File file = new File(testPath + File.separator + tmpFile);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        }
        WorkbookParameter workbookParameter = WorkbookParameter
                .builder()
                .output(new FileOutputStream(file))
                .build();

        Assertions.assertNull(workbookParameter.getOutputFileName());
        Assertions.assertNull(workbookParameter.getOutputFilePath());
        Assertions.assertNull(workbookParameter.getOutputFile());
        Assertions.assertNotNull(workbookParameter.getOutputStream());

        Assertions.assertTrue(workbookParameter.isXssf());
    }

    @Test
    void xssf1() {
        WorkbookParameter workbookParameter = WorkbookParameter.builder().build();
        Assertions.assertTrue(workbookParameter.isXssf());
    }


    @Test
    void xssf2() throws IOException {
        String testPath = this.getClass().getResource("/").getPath();
        String tmpFile = System.currentTimeMillis() + ".xls";
        File file = new File(testPath + File.separator + tmpFile);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        }

        WorkbookParameter workbookParameter = WorkbookParameter.builder().input(file).build();
        Assertions.assertFalse(workbookParameter.isXssf());
    }

    @Test
    void xssf3() throws IOException {
        String testPath = this.getClass().getResource("/").getPath();
        String tmpFile = System.currentTimeMillis() + ".xlsx";
        File file = new File(testPath + File.separator + tmpFile);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        }

        WorkbookParameter workbookParameter = WorkbookParameter.builder().output(file).build();
        Assertions.assertTrue(workbookParameter.isXssf());
    }

    @Test
    void xssf4() throws IOException {
        String testPath = this.getClass().getResource("/").getPath();
        String tmpFile = System.currentTimeMillis() + ".xlsx";
        File file = new File(testPath + File.separator + tmpFile);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        }

        Assertions.assertThrows(ExcelException.class, () -> WorkbookParameter
                .builder()
                .input(file)
                .xssf(false)
                .build());
    }

    @Test
    void xssf5() throws IOException {
        String testPath = this.getClass().getResource("/").getPath();
        String tmpFile = System.currentTimeMillis() + ".xls";
        File file = new File(testPath + File.separator + tmpFile);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        }

        Assertions.assertThrows(ExcelException.class, () -> WorkbookParameter
                .builder()
                .input(file)
                .xssf(true)
                .build());
    }

    @Test
    void xssf6() throws IOException {
        String testPath = this.getClass().getResource("/").getPath();
        String tmpFile = System.currentTimeMillis() + ".xls";
        File file = new File(testPath + File.separator + tmpFile);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        }

        Assertions.assertThrows(ExcelException.class, () -> WorkbookParameter
                .builder()
                .output(file)
                .xssf(true)
                .build());
    }

    @Test
    void xssf7() throws IOException {
        String testPath = this.getClass().getResource("/").getPath();
        String tmpFile = System.currentTimeMillis() + ".xlsx";
        File file = new File(testPath + File.separator + tmpFile);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        }

        Assertions.assertThrows(ExcelException.class, () -> WorkbookParameter
                .builder()
                .output(file)
                .xssf(false)
                .build());
    }

    @Test
    void xssf8() throws IOException {
        String testPath = this.getClass().getResource("/").getPath();
        String inputFileName = System.currentTimeMillis() + ".xlsx";
        File inputFile = new File(testPath + File.separator + inputFileName);
        if (!inputFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            inputFile.createNewFile();
        }

        String outFileName = System.currentTimeMillis() + ".xls";
        File outputFile = new File(testPath + File.separator + outFileName);
        if (!outputFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            outputFile.createNewFile();
        }

        Assertions.assertThrows(ExcelException.class, () -> WorkbookParameter
                .builder()
                .input(inputFile)
                .output(outputFile)
                .build());

        Assertions.assertThrows(ExcelException.class, () -> WorkbookParameter
                .builder()
                .input(outputFile)
                .output(inputFile)
                .build());
    }
}
