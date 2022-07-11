package io.github.zouzhiy.excel.support.utils;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TestFileUtils {

    public static InputStream getInputStream(String filePath) {
        return TestFileUtils.class.getClassLoader().getResourceAsStream(filePath);
    }


    public static File getTemplateFile(String filePath) {
        return writeXlsFile(getInputStream(filePath));
    }

    public static InputStream getTemplateInputStream(String filePath) {
        try {
            return new FileInputStream(writeXlsFile(getInputStream(filePath)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static File getEmptyXlsxFile() {
        URL rootUrl = TestFileUtils.class.getResource("/");
        assert rootUrl != null;
        String rootPath = rootUrl.getPath();

        String tmpFilePath = rootPath + File.separator + "tmp" + File.separator + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + ".xlsx";

        File tmpFile = new File(tmpFilePath);
        if (!tmpFile.getParentFile().exists()) {
            //noinspection ResultOfMethodCallIgnored
            tmpFile.getParentFile().mkdirs();
        }
        try {
            //noinspection ResultOfMethodCallIgnored
            tmpFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tmpFile;
    }

    public static File getEmptyXlsFile() {
        URL rootUrl = TestFileUtils.class.getResource("/");
        assert rootUrl != null;
        String rootPath = rootUrl.getPath();

        String tmpFilePath = rootPath + File.separator + "tmp" + File.separator + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + ".xls";

        File tmpFile = new File(tmpFilePath);
        if (!tmpFile.getParentFile().exists()) {
            //noinspection ResultOfMethodCallIgnored
            tmpFile.getParentFile().mkdirs();
        }
        try {
            //noinspection ResultOfMethodCallIgnored
            tmpFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tmpFile;
    }

    public static OutputStream getXlsxOutputStream() {
        try {
            return new FileOutputStream(getEmptyXlsxFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static File writeXlsxFile(InputStream inputStream) {
        URL rootUrl = TestFileUtils.class.getResource("/");
        assert rootUrl != null;
        String rootPath = rootUrl.getPath();

        String tmpFilePath = rootPath + File.separator + "tmp" + File.separator + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + ".xlsx";

        File tmpFile = new File(tmpFilePath);
        if (!tmpFile.getParentFile().exists()) {
            //noinspection ResultOfMethodCallIgnored
            tmpFile.getParentFile().mkdirs();
        }

        try {
            org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, tmpFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tmpFile;
    }

    public static File writeXlsFile(InputStream inputStream) {
        URL rootUrl = TestFileUtils.class.getResource("/");
        assert rootUrl != null;
        String rootPath = rootUrl.getPath();

        String tmpFilePath = rootPath + File.separator + "tmp" + File.separator + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + ".xls";

        File tmpFile = new File(tmpFilePath);
        if (!tmpFile.getParentFile().exists()) {
            //noinspection ResultOfMethodCallIgnored
            tmpFile.getParentFile().mkdirs();
        }

        try {
            org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, tmpFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tmpFile;
    }

    public static String getEmptyXlsxFilePath() {
        return "statics/import/empty.xlsx";
    }

}
