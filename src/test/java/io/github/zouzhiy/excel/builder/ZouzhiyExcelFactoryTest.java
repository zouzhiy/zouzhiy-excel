package io.github.zouzhiy.excel.builder;

import io.github.zouzhiy.excel.metadata.SheetParameter;
import io.github.zouzhiy.excel.metadata.WorkbookParameter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
class ZouzhiyExcelFactoryTest {

    private final ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().build();

    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void writeXlsx() {

        List<Demo> demoList = this.demoList();
        WorkbookParameter writeWorkbookParameter = this.getXlsxWriteWorkbookParameter();
        zouzhiyExcelFactory.getWorkbookWrite(writeWorkbookParameter, Demo.class)
                .write(demoList);

        WorkbookParameter readWriteWorkbookParameter = WorkbookParameter.builder()
                .input(writeWorkbookParameter.getOutputFile())
                .sheetParameter(writeWorkbookParameter.getSheetParameter())
                .build();
        List<Demo> demoList1 = zouzhiyExcelFactory.getWorkbookRead(readWriteWorkbookParameter, Demo.class).read(Demo.class);


        WorkbookParameter writeWorkbookParameter2 = this.getXlsxWriteWorkbookParameter();
        zouzhiyExcelFactory.getWorkbookWrite(writeWorkbookParameter2, Demo.class)
                .write(demoList1);

    }

    @Test
    void writeXls() {

        List<Demo> demoList = this.demoList();
        WorkbookParameter writeWorkbookParameter = this.getXlsWriteWorkbookParameter();
        zouzhiyExcelFactory.getWorkbookWrite(writeWorkbookParameter, Demo.class)
                .write(demoList);

        WorkbookParameter readWriteWorkbookParameter = WorkbookParameter.builder()
                .input(writeWorkbookParameter.getOutputFile())
                .sheetParameter(writeWorkbookParameter.getSheetParameter())
                .build();
        List<Demo> demoList1 = zouzhiyExcelFactory.getWorkbookRead(readWriteWorkbookParameter, Demo.class).read(Demo.class);

        WorkbookParameter writeWorkbookParameter2 = this.getXlsWriteWorkbookParameter();
        zouzhiyExcelFactory.getWorkbookWrite(writeWorkbookParameter2, Demo.class)
                .write(demoList1);

    }

    private List<Demo> demoList() {
        List<Demo> demoList = new ArrayList<>();
        int size = random.nextInt(223);
        for (int i = 0; i < size; i++) {
            demoList.add(new Demo());
        }

        return demoList;
    }

    private WorkbookParameter getXlsxWriteWorkbookParameter() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        String outputFileName = simpleDateFormat.format(new Date()) + ".xlsx";
        URL url = this.getClass().getResource("/");

        assert url != null;
        String filePath = url.getFile();
        String outputFilePath = filePath + File.separator + "output" + File.separator + outputFileName;


        SheetParameter sheetParameter = SheetParameter.builder().sheet(0)
                .title("测试title-覆盖")
                .dataRowStartIndex(2).dataColumnStartIndex(0)
                .build();
        return WorkbookParameter.builder()
                .output(new File(outputFilePath))
                .sheetParameter(sheetParameter)
                .build();
    }

    private WorkbookParameter getXlsWriteWorkbookParameter() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        String outputFileName = simpleDateFormat.format(new Date()) + ".xls";
        URL url = this.getClass().getResource("/");

        assert url != null;
        String filePath = url.getFile();
        String outputFilePath = filePath + File.separator + "output" + File.separator + outputFileName;


        SheetParameter sheetParameter = SheetParameter.builder().sheet(0)
                .title("测试title-覆盖")
                .dataRowStartIndex(2).dataColumnStartIndex(0)
                .build();
        return WorkbookParameter.builder()
                .output(new File(outputFilePath))
                .sheetParameter(sheetParameter)
                .build();
    }


}