package io.github.zouzhiy.excel.builder;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * @author zouzhiy
 * @since 2022/7/6
 */
class ZouzhiyExcelFactoryTest {


    private final ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().build();

    @Test
    void read() {
        InputStream importInputStream = this.getClass().getClassLoader().getResourceAsStream("statics\\import2.xlsx");

        List<Demo1> demoList = zouzhiyExcelFactory.read(importInputStream).sheet().dataRowStartIndex(1).read(Demo1.class);

        System.out.println(demoList);
    }

    @Test
    void write() {
        InputStream importInputStream = this.getClass().getClassLoader().getResourceAsStream("statics\\import2.xlsx");

        List<Demo1> demoList = zouzhiyExcelFactory.read(importInputStream).sheet().dataRowStartIndex(1).read(Demo1.class);

        zouzhiyExcelFactory.write(this.getOutputFile()).sheet().dataRowStartIndex(1).write(demoList, Demo1.class);

        System.out.println(demoList);
    }

    private File getOutputFile() {
        String rootPath = this.getClass().getResource("/").getPath();

        String filePath = rootPath + File.separator + "write" + File.separator + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-ddHHmmss")) + ".xlsx";
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }
}