/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.zouzhiy.excel.old.builder;

import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactory;
import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactoryBuilder;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
class ZouzhiyExcelFactoryWriteTest {

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
    void writeXls() throws IllegalAccessException {

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

        Field[] fieldList = Demo.class.getDeclaredFields();

        for (int i = 0; i < demoList.size(); i++) {
            Demo demo = demoList.get(0);
            Demo demp = demoList1.get(0);

            for (Field field : fieldList) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (field.getName().contains("image")) {
                    continue;
                }
                field.setAccessible(true);
                Object value1 = field.get(demo);
                Object value2 = field.get(demp);

                Class<?> fieldType = field.getType();
                System.out.println(field.getName());
                try {
                    Assertions.assertEquals(value1, value2);
                } catch (AssertionFailedError error) {
                    if (Calendar.class.equals(fieldType)) {
                        Calendar calendar1 = (Calendar) value1;
                        Calendar calendar2 = (Calendar) value2;
                        Assertions.assertEquals(new SimpleDateFormat("yyyyMMddHHmmss").format(calendar1.getTime()),
                                new SimpleDateFormat("yyyyMMddHHmmss").format(calendar2.getTime())
                        );
                    } else if (Date.class.equals(fieldType) || Timestamp.class.equals(fieldType)) {
                        Date calendar1 = (Date) value1;
                        Date calendar2 = (Date) value2;
                        Assertions.assertEquals(new SimpleDateFormat("yyyyMMddHHmmss").format(calendar1.getTime()),
                                new SimpleDateFormat("yyyyMMddHHmmss").format(calendar2.getTime())
                        );
                    } else if (LocalTime.class.equals(fieldType)) {
                        String calendar1 = DateTimeFormatter.ofPattern("HHmmss").format((TemporalAccessor) value1);
                        String calendar2 = DateTimeFormatter.ofPattern("HHmmss").format((TemporalAccessor) value2);
                        Assertions.assertEquals(calendar1, calendar2);
                    } else if (TemporalAccessor.class.isAssignableFrom(fieldType)) {
                        String calendar1 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format((TemporalAccessor) value1);
                        String calendar2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format((TemporalAccessor) value2);
                        Assertions.assertEquals(calendar1, calendar2);
                    } else if (byte[].class.equals(fieldType)) {
                        String str1 = new String((byte[]) value1, StandardCharsets.UTF_8);
                        String str2 = new String((byte[]) value2, StandardCharsets.UTF_8);
                        Assertions.assertEquals(str1, str2);
                    } else if (Byte[].class.equals(fieldType)){
                        continue;
                    }

                    else {
                        System.out.printf("%s,%s,%s,%s%n", i, field.getName(), value1, value2);
                    }
                }
            }
//            Assertions.assertEquals(demo.toString(), demp.toString());
//            Assertions.assertEquals(demoList.get(0), demoList1.get(0));
//            Assertions.assertEquals(demoList.get(0), demoList.get(0));
        }

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