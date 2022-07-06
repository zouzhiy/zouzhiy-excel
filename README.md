# excel导入导出工具

## 快速开始

### 单独引入

```xml

<dependency>
    <groupId>io.github.zouzhiy</groupId>
    <artifactId>zouzhiy-excel</artifactId>
    <version>1.0.0</version>
</dependency>
```

### spring boot 引入

```xml

<dependency>
    <groupId>io.github.zouzhiy</groupId>
    <artifactId>zouzhiy-excel-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 简单使用

```java

@Data
@ExcelClass(titleStyle = @ExcelStyle(font = @ExcelFont(bold = true, fontHeightInPoints = 16), horizontalAlignment = StyleHorizontalAlignment.CENTER))
public class WriteDemo {

    @ExcelField(title = "name-覆盖")
    private String name;

    @ExcelField(title = "title-覆盖", colspan = 2)
    private String title;

    @ExcelField(title = "valueList-覆盖", colspan = 3, cellHandler = ValueListStringHandler.class)
    private List<String> valueList;

    @ExcelField(title = "boolean-覆盖", colspan = 4, dataStyle = @ExcelStyle(borderLeft = BorderStyle.DASHED, borderBottom = BorderStyle.DOUBLE), excelType = ExcelType.BOOLEAN)
    private String booleanStr;

}
```

```java
import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactory;

import javax.annotation.Resource;

class ZouzhiyExcelFactoryTest {
    // 直接引入
    private final ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().build();

    // spring boot
    @Resource
    private final ZouzhiyExcelFactory zouzhiyExcelFactory;

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
        //noinspection ConstantConditions
        String rootPath = this.getClass().getResource("/").getPath();

        String filePath = rootPath + File.separator + "write" + File.separator + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-ddHHmmss")) + ".xlsx";
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }
}
```

## 单元测试覆盖率
|包|类|方法|行|
|----|----|----|----|
| Package                                       | Class, %       | Method, %       | Line, %           |
|-----------------------------------------------|----------------|-----------------|-------------------|
| all classes                                   | 100% (131/131) | 90.2% (726/805) | 90.1% (2235/2481) |
|                                               |                |                 |                   |
| Coverage Breakdown                            |                |                 |                   |
| Package                                       | Class, %       | Method, %       | Line, %           |
| io.github.zouzhiy.excel.builder               | 100% (6/6)     | 58.1% (43/74)   | 64.2% (97/151)    |
| io.github.zouzhiy.excel.cellstyle.defaults    | 100% (3/3)     | 100% (13/13)    | 100% (59/59)      |
| io.github.zouzhiy.excel.cellstyle.registry    | 100% (1/1)     | 100% (5/5)      | 100% (13/13)      |
| io.github.zouzhiy.excel.context.defualts      | 100% (3/3)     | 100% (48/48)    | 98% (199/203)     |
| io.github.zouzhiy.excel.enums                 | 100% (5/5)     | 100% (20/20)    | 100% (55/55)      |
| io.github.zouzhiy.excel.exceptions            | 100% (1/1)     | 85.7% (6/7)     | 85.7% (12/14)     |
| io.github.zouzhiy.excel.handler               | 100% (6/6)     | 100% (29/29)    | 94.3% (183/194)   |
| io.github.zouzhiy.excel.handler.bigdecimal    | 100% (3/3)     | 100% (10/10)    | 100% (16/16)      |
| io.github.zouzhiy.excel.handler.biginteger    | 100% (3/3)     | 100% (11/11)    | 100% (17/17)      |
| io.github.zouzhiy.excel.handler.booleans      | 100% (3/3)     | 100% (12/12)    | 100% (17/17)      |
| io.github.zouzhiy.excel.handler.bytes         | 100% (5/5)     | 100% (20/20)    | 100% (36/36)      |
| io.github.zouzhiy.excel.handler.calendar      | 100% (3/3)     | 100% (13/13)    | 94.3% (33/35)     |
| io.github.zouzhiy.excel.handler.date          | 100% (3/3)     | 100% (14/14)    | 93.8% (30/32)     |
| io.github.zouzhiy.excel.handler.doubles       | 100% (4/4)     | 100% (16/16)    | 100% (24/24)      |
| io.github.zouzhiy.excel.handler.floats        | 100% (3/3)     | 100% (11/11)    | 100% (17/17)      |
| io.github.zouzhiy.excel.handler.head          | 100% (1/1)     | 60% (3/5)       | 86.7% (13/15)     |
| io.github.zouzhiy.excel.handler.image         | 100% (4/4)     | 72.2% (13/18)   | 46.1% (35/76)     |
| io.github.zouzhiy.excel.handler.ints          | 100% (3/3)     | 100% (11/11)    | 100% (17/17)      |
| io.github.zouzhiy.excel.handler.list          | 100% (5/5)     | 100% (20/20)    | 88.5% (69/78)     |
| io.github.zouzhiy.excel.handler.localdate     | 100% (3/3)     | 100% (13/13)    | 96% (24/25)       |
| io.github.zouzhiy.excel.handler.localdatetime | 100% (3/3)     | 100% (13/13)    | 95.7% (22/23)     |
| io.github.zouzhiy.excel.handler.localtime     | 100% (3/3)     | 100% (13/13)    | 97.1% (34/35)     |
| io.github.zouzhiy.excel.handler.longs         | 100% (3/3)     | 100% (11/11)    | 100% (17/17)      |
| io.github.zouzhiy.excel.handler.shorts        | 100% (3/3)     | 100% (11/11)    | 100% (17/17)      |
| io.github.zouzhiy.excel.handler.string        | 100% (4/4)     | 100% (20/20)    | 97.4% (37/38)     |
| io.github.zouzhiy.excel.handler.timestamp     | 100% (3/3)     | 100% (13/13)    | 95.7% (22/23)     |
| io.github.zouzhiy.excel.metadata              | 100% (4/4)     | 75% (18/24)     | 92.9% (78/84)     |
| io.github.zouzhiy.excel.metadata.config       | 100% (8/8)     | 96.4% (80/83)   | 97.9% (139/142)   |
| io.github.zouzhiy.excel.metadata.parameter    | 100% (4/4)     | 81.9% (59/72)   | 85.5% (148/173)   |
| io.github.zouzhiy.excel.metadata.result       | 100% (5/5)     | 72.3% (34/47)   | 82.9% (97/117)    |
| io.github.zouzhiy.excel.parsing               | 100% (1/1)     | 100% (9/9)      | 96.1% (73/76)     |
| io.github.zouzhiy.excel.read.defaults         | 100% (8/8)     | 95.1% (39/41)   | 86.8% (249/287)   |
| io.github.zouzhiy.excel.read.registry         | 100% (3/3)     | 100% (15/15)    | 92.3% (36/39)     |
| io.github.zouzhiy.excel.utils                 | 100% (2/2)     | 85.7% (12/14)   | 90.1% (64/71)     |
| io.github.zouzhiy.excel.write.defaults        | 100% (6/6)     | 97.1% (33/34)   | 97.1% (200/206)   |
| io.github.zouzhiy.excel.write.registry        | 100% (3/3)     | 100% (15/15)    | 92.3% (36/39)     |
