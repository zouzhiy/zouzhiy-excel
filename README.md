# zouzhiy-excel

## 1. 项目介绍

### 1.1 简介

zouzhiy-excel是一款Excel导入导出的轻量级开源组件。省略了繁琐的配置，几行代码快速实现Excel导入导出。

### 1.2 特性

1. 默认大于配置，不需要显式的标注注解。反向解析，不需要的字段或者需要自定义配置的字段才需要注解标注
2. 支持模板导出。可以预先设置好标题，表头，数据行格式。写入的数据自动继承模板的格式
3. 支持一对多导入导出。一个数据对象占据不固的多行多列。
4. 支持拆分写入不同列。如：用户信息作为一个对象，可通过自定义CellHandler,实现多列写入，一列显示姓名，一列显示通信方式等。
5. 支持自定义单元格格式，基本囊括了poi提供的style属性

## 2.快速开始

### 2.1 依赖引入

#### 2.1.1 单独引入

```xml

<dependency>
    <groupId>io.github.zouzhiy</groupId>
    <artifactId>zouzhiy-excel</artifactId>
    <version>1.0.0</version>
</dependency>
```

#### 2.1.2 spring-boot-starter

```xml

<dependency>
    <groupId>io.github.zouzhiy</groupId>
    <artifactId>zouzhiy-excel-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2.2 示例

#### 2.2.1 简单导入导出

```java

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoVo {

    private String name;

    private String title;

    public static List<DemoVo> getList() {
        Random random = new Random(System.currentTimeMillis());
        int size = random.nextInt(5000);
        List<DemoVo> demoVoList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            DemoVo demoVo = DemoVo.builder()
                    .name("name-" + random.nextInt(900))
                    .title("title-" + random.nextInt(111))
                    .build();
            demoVoList.add(demoVo);
        }
        return demoVoList;
    }
}


public class ExcelDemo {

    private final ZouzhiyExcelFactory zouzhiyExcelFactory = new DefaultZouzhiyExcelFactory(new Configuration());


    @Test
    void exportNoTemplate() {
        String rootPath = this.getClass().getResource("/").getPath();

        // 无标题，无表头
        zouzhiyExcelFactory.write(new File(rootPath + File.separator + System.currentTimeMillis() + ".xlsx"))
                .sheet()
                .dataRowStartIndex(0)
                .write(DemoVo.getList(), DemoVo.class);
        // 无标题，有表头
        zouzhiyExcelFactory.write(new File(rootPath + File.separator + System.currentTimeMillis() + ".xlsx"))
                .sheet()
                .dataRowStartIndex(1)
                .write(DemoVo.getList(), DemoVo.class);
        // 有标题，无表头
        zouzhiyExcelFactory.write(new File(rootPath + File.separator + System.currentTimeMillis() + ".xlsx"))
                .sheet()
                .title("有标题无表头")
                .titleRowStartIndex(0)
                .headRowStartIndex(-1)
                .dataRowStartIndex(1)
                .write(DemoVo.getList(), DemoVo.class);

        zouzhiyExcelFactory.write(new File(rootPath + File.separator + System.currentTimeMillis() + ".xlsx"))
                .sheet()
                .title("有标题有表头")
                .dataRowStartIndex(2)
                .write(DemoVo.getList(), DemoVo.class);
    }

    @Test
    void exportWithTemplate() {
        String exportTemplateFilePath = "template/export.xlsx";

        String rootPath = this.getClass().getResource("/").getPath();
        // 不覆盖标题，不覆盖标题表头
        zouzhiyExcelFactory.write(new File(rootPath + File.separator + System.currentTimeMillis() + ".xlsx"))
                .template(this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath))
                .sheet()
                .titleRowStartIndex(-1)
                .headRowStartIndex(-1)
                .dataRowStartIndex(2)
                .write(DemoVo.getList(), DemoVo.class);

        // 覆盖标题，不覆盖标题表头
        zouzhiyExcelFactory.write(new File(rootPath + File.separator + System.currentTimeMillis() + ".xlsx"))
                .template(this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath))
                .sheet()
                .title("覆盖标题")
                .titleRowStartIndex(0)
                .headRowStartIndex(-1)
                .dataRowStartIndex(2)
                .write(DemoVo.getList(), DemoVo.class);

        // 不覆盖标题，覆盖表头
        zouzhiyExcelFactory.write(new File(rootPath + File.separator + System.currentTimeMillis() + ".xlsx"))
                .template(this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath))
                .sheet()
                .titleRowStartIndex(-1)
                .headRowStartIndex(1)
                .dataRowStartIndex(2)
                .write(DemoVo.getList(), DemoVo.class);

        // 覆盖标题，覆盖标题表头
        zouzhiyExcelFactory.write(new File(rootPath + File.separator + System.currentTimeMillis() + ".xlsx"))
                .template(this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath))
                .sheet()
                .title("覆盖标题，覆盖表头")
                .dataRowStartIndex(2)
                .write(DemoVo.getList(), DemoVo.class);
    }

    @Test
    void importExcel() {
        String inputFilePath = "template/import.xlsx";

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(inputFilePath);

        List<DemoVo> demoVoList = zouzhiyExcelFactory.read(inputStream).sheet(0).dataRowStartIndex(2).read(DemoVo.class);
        for (DemoVo demoVo : demoVoList) {
            System.out.println(demoVo);
        }
    }
}
```

#### 2.2.2 spring-boot中使用
```java

@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    private ZouzhiyExcelFactory zouzhiyExcelFactory;

    @PostMapping("import")
    public List<DemoVo> importFile(MultipartFile multipartFile) throws IOException {
        return zouzhiyExcelFactory
                .read(multipartFile.getInputStream())
                .sheet()
                .dataRowStartIndex(2)
                .read(DemoVo.class);
    }

    @GetMapping("export/no-template1")
    public void exportDataByNoTemplate(HttpServletResponse response) throws IOException {
        List<DemoVo> demoVoList = Collections.emptyList();

        response.addHeader("Content-Disposition", "attachment; filename*=utf-8''" + "export.xlsx");
        zouzhiyExcelFactory
                .write(response.getOutputStream())
                .sheet()
                .title("export")
                .titleRowStartIndex(0)
                .dataRowStartIndex(2)
                .write(demoVoList, DemoVo.class);

    }
    @GetMapping("export/no-template2")
    public void exportDataByTemplate4(HttpServletResponse response) throws IOException {
        List<DemoVo> demoVoList = this.getList();

        response.addHeader("Content-Disposition", "attachment; filename*=utf-8''" + "export.xlsx");
        zouzhiyExcelFactory
                .write(response.getOutputStream())
                .sheet()
                .title("export")
                .titleRowStartIndex(0)
                .dataRowStartIndex(2)
                .write(demoVoList, DemoVo.class);

    }

    @GetMapping("export/template1")
    public void exportDataByTemplate1(HttpServletResponse response) throws IOException {
        List<DemoVo> demoVoList = this.getList();

        String exportTemplateFilePath = "template/export/export1.xlsx";
        InputStream exportTemplateInputStream = this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath);

        response.addHeader("Content-Disposition", "attachment; filename*=utf-8''" + "export.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        zouzhiyExcelFactory
                .write(response.getOutputStream())
                .template(exportTemplateInputStream)
                .sheet()
                .titleRowStartIndex(-1)
                .headRowStartIndex(-1)
                .dataRowStartIndex(2)
                .write(demoVoList, DemoVo.class);

    }

    @GetMapping("export/template2")
    public void exportDataByTemplate2(HttpServletResponse response) throws IOException {
        List<DemoVo> demoVoList = this.getList();

        String exportTemplateFilePath = "template/export/export2.xls";
        InputStream exportTemplateInputStream = this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath);

        response.addHeader("Content-Disposition", "attachment; filename*=utf-8''" + "import.xls");
        response.setContentType("application/vnd.ms-excel");
        zouzhiyExcelFactory
                .write(response.getOutputStream())
                .template(exportTemplateInputStream)
                .sheet()
                .titleRowStartIndex(-1)
                .headRowStartIndex(-1)
                .dataRowStartIndex(2)
                .write(demoVoList, DemoVo.class);

    }


    @GetMapping("export/template3")
    public void exportDataByTemplate3(HttpServletResponse response) throws IOException {
        List<DemoVo> demoVoList = Collections.emptyList();

        String exportTemplateFilePath = "template/export/export2.xls";
        InputStream exportTemplateInputStream = this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath);

        response.addHeader("Content-Disposition", "attachment; filename*=utf-8''" + "import.xls");
        response.setContentType("application/vnd.ms-excel");
        zouzhiyExcelFactory
                .write(response.getOutputStream())
                .template(exportTemplateInputStream)
                .sheet()
                .titleRowStartIndex(-1)
                .headRowStartIndex(-1)
                .dataRowStartIndex(2)
                .write(demoVoList, DemoVo.class);

    }



    private final Random random = new Random(System.currentTimeMillis());

    private List<DemoVo> getList() {
        int size = random.nextInt(5000);
        List<DemoVo> demoVoList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            DemoVo demoVo = DemoVo.builder()
                    .name("name-" + random.nextInt(900))
                    .title("title-" + random.nextInt(111))
                    .build();
            demoVoList.add(demoVo);
        }
        return demoVoList;
    }
}
```

#### 2.2.3 自定义CellHandler
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemVo {

    private final static Random RANDOM = new Random(System.currentTimeMillis());

    private String firstName;

    private String lastName;

    private Integer age;

    public static ItemVo newInstance() {
        return ItemVo
                .builder()
                .firstName(RANDOM.nextBoolean() ? null : "firstName-" + RANDOM.nextDouble())
                .lastName(RANDOM.nextBoolean() ? null : "lastName-" + RANDOM.nextDouble())
                .age(RANDOM.nextBoolean() ? null : RANDOM.nextInt(100))
                .build();
    }
}


public class ItemCellHandler extends AbstractCellHandler<ItemVo> {

    @Override
    protected ItemVo getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        String value = firstCellResult.getStringValue();
        String[] values = value.split(",", -1);

        return ItemVo.builder().firstName(values[0])
                .lastName(values[1])
                .age((values[2] == null || "null".equals(values[2]) || values[2].length()==0) ? null : new BigDecimal(values[2]).intValue())
                .build();
    }

    @Override
    protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, ItemVo value) {
        cell.setCellValue(String.format("%s,%s,%s", value.getFirstName(), value.getLastName(), value.getAge()));
    }

    @Override
    public ExcelType getExcelType() {
        return ExcelType.STRING;
    }

    @Override
    protected ItemVo getBlankValue() {
        return ItemVo.builder().build();
    }
}
```

#### 2.2.4 一对多，一条数据占据多行
```java

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoVo {

    private final static Random random = new Random(System.currentTimeMillis());

    private String name;

    private String title;

    @ExcelField(cellHandler = ListStringStringSplitHandler.class)
    private List<String> valueList;
}


public class ExcelDemo {

    private final ZouzhiyExcelFactory zouzhiyExcelFactory = new DefaultZouzhiyExcelFactory(new Configuration());

    @Test
    void exportNoTemplate() {
        String rootPath = this.getClass().getResource("/").getPath();

        // 无标题，无表头
        zouzhiyExcelFactory.write(new File(rootPath + File.separator + System.currentTimeMillis() + ".xlsx"))
                .sheet()
                .dataRowStartIndex(0)
                .write(DemoVo.getList(), DemoVo.class);
        // 无标题，有表头
        zouzhiyExcelFactory.write(new File(rootPath + File.separator + System.currentTimeMillis() + ".xlsx"))
                .sheet()
                .dataRowStartIndex(1)
                .write(DemoVo.getList(), DemoVo.class);
        // 有标题，无表头
        zouzhiyExcelFactory.write(new File(rootPath + File.separator + System.currentTimeMillis() + ".xlsx"))
                .sheet()
                .title("有标题无表头")
                .titleRowStartIndex(0)
                .headRowStartIndex(-1)
                .dataRowStartIndex(1)
                .write(DemoVo.getList(), DemoVo.class);

        zouzhiyExcelFactory.write(new File(rootPath + File.separator + System.currentTimeMillis() + ".xlsx"))
                .sheet()
                .title("有标题有表头")
                .dataRowStartIndex(2)
                .write(DemoVo.getList(), DemoVo.class);
    }

    @Test
    void exportWithTemplate() {
        String exportTemplateFilePath = "template/export.xlsx";

        String rootPath = this.getClass().getResource("/").getPath();
        // 不覆盖标题，不覆盖标题表头
        zouzhiyExcelFactory.write(new File(rootPath + File.separator + System.currentTimeMillis() + ".xlsx"))
                .template(this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath))
                .sheet()
                .titleRowStartIndex(-1)
                .headRowStartIndex(-1)
                .dataRowStartIndex(2)
                .write(DemoVo.getList(), DemoVo.class);

        // 覆盖标题，不覆盖标题表头
        zouzhiyExcelFactory.write(new File(rootPath + File.separator + System.currentTimeMillis() + ".xlsx"))
                .template(this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath))
                .sheet()
                .title("覆盖标题")
                .titleRowStartIndex(0)
                .headRowStartIndex(-1)
                .dataRowStartIndex(2)
                .write(DemoVo.getList(), DemoVo.class);

        // 不覆盖标题，覆盖表头
        zouzhiyExcelFactory.write(new File(rootPath + File.separator + System.currentTimeMillis() + ".xlsx"))
                .template(this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath))
                .sheet()
                .titleRowStartIndex(-1)
                .headRowStartIndex(1)
                .dataRowStartIndex(2)
                .write(DemoVo.getList(), DemoVo.class);

        // 覆盖标题，覆盖标题表头
        zouzhiyExcelFactory.write(new File(rootPath + File.separator + System.currentTimeMillis() + ".xlsx"))
                .template(this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath))
                .sheet()
                .title("覆盖标题，覆盖表头")
                .dataRowStartIndex(2)
                .write(DemoVo.getList(), DemoVo.class);
    }

    @Test
    void importExcel() {
        String inputFilePath = "template/import.xlsx";

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(inputFilePath);

        List<DemoVo> demoVoList = zouzhiyExcelFactory.read(inputStream).sheet(0).dataRowStartIndex(2).read(DemoVo.class);
        for (DemoVo demoVo : demoVoList) {
            System.out.println(demoVo);
        }
    }
}
```

#### 2.2.5 一对多，一条数据对应多列
```java

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemVo {

    private final static Random RANDOM = new Random(System.currentTimeMillis());

    private String firstName;

    private String lastName;

    private Integer age;

    public static ItemVo newInstance() {
        return ItemVo
                .builder()
                .firstName(RANDOM.nextBoolean() ? null : "firstName-" + RANDOM.nextDouble())
                .lastName(RANDOM.nextBoolean() ? null : "lastName-" + RANDOM.nextDouble())
                .age(RANDOM.nextBoolean() ? null : RANDOM.nextInt(100))
                .build();
    }
}


public class ItemCellHandler implements CellHandler<ItemVo> {


    @Override
    public Class<ItemVo> getJavaType() {
        return ItemVo.class;
    }

    @Override
    public ExcelType getExcelType() {
        return ExcelType.STRING;
    }

    @Override
    public ItemVo read(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResultSet cellResultSet) {
        if (cellResultSet == null) {
            return null;
        }

        if (cellResultSet.isNone()) {
            return null;
        }
        List<CellResult> cellResultList = cellResultSet.getCellResultListList().get(0);
        CellResult cellResultFirstName = cellResultList.size() > 0 ? cellResultList.get(0) : CellResult.none();
        CellResult cellResultSecondName = cellResultList.size() > 1 ? cellResultList.get(1) : CellResult.none();
        CellResult cellResultAge = cellResultList.size() > 2 ? cellResultList.get(2) : CellResult.none();

        CellHandler<String> stringStringHandler = sheetContext.getConfiguration().getCellHandlerRegistry().getCellHandler(StringStringHandler.class);

        String firstName = stringStringHandler.read(sheetContext, excelFieldConfig, CellResultSet.firstCellResult(cellResultFirstName));
        String secondName = stringStringHandler.read(sheetContext, excelFieldConfig, CellResultSet.firstCellResult(cellResultSecondName));
        CellHandler<Integer> integerCellHandler = sheetContext.getConfiguration().getCellHandlerRegistry().getCellHandler(IntegerNumberHandler.class);
        Integer age = integerCellHandler.read(sheetContext, excelFieldConfig, CellResultSet.firstCellResult(cellResultAge));

        return ItemVo.builder().firstName(firstName).lastName(secondName).age(age).build();
    }

    @Override
    public void write(RowContext rowContext, Integer columnIndex, ExcelFieldConfig excelFieldConfig, ItemVo value) {
        if (value == null) {
            return;
        }
        Row row = rowContext.getRowList().get(0);
        Cell cellFirstName = row.createCell(columnIndex);
        Cell cellLastName = row.createCell(columnIndex + 1);
        Cell cellAge = row.createCell(columnIndex + 2);
        if (value.getFirstName() != null    ){
            cellFirstName.setCellValue(value.getFirstName());
        }
        if (value.getLastName() != null){
            cellLastName.setCellValue(value.getLastName());
        }
        if (value.getAge() != null) {
            cellAge.setCellValue(value.getAge());
        }

        SheetContext sheetContext = rowContext.getSheetContext();
        CellStyle cellStyle = sheetContext.getDataCellStyle(excelFieldConfig, this.getDefaultExcelFormat());
        cellFirstName.setCellStyle(cellStyle);
        cellLastName.setCellStyle(cellStyle);
        cellAge.setCellStyle(cellStyle);

        int rowspan = rowContext.getRowspan();
        int rowIndex = row.getRowNum();
        RegionUtils.addMergedRegionIfPresent(sheetContext, cellStyle, rowIndex, rowIndex + rowspan - 1, columnIndex, columnIndex);
        RegionUtils.addMergedRegionIfPresent(sheetContext, cellStyle, rowIndex, rowIndex + rowspan - 1, columnIndex + 1, columnIndex + 1);
        RegionUtils.addMergedRegionIfPresent(sheetContext, cellStyle, rowIndex, rowIndex + rowspan - 1, columnIndex + 2, columnIndex + 2);
    }
}


```

#### 2.2.6 自定义单元格格式
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ExcelClass(titleStyle = @ExcelStyle(borderLeft = BorderStyle.THIN,rotation = 45, wrapText = true,font = @ExcelFont(fontHeightInPoints = 32, bold = true, italic = true)))
public class DemoVo {

    @ExcelField(dataStyle = @ExcelStyle(font = @ExcelFont(bold = true, italic = true)), headStyle = @ExcelStyle(font = @ExcelFont(italic = true)))
    private String name;

    @ExcelField(dataStyle = @ExcelStyle(font = @ExcelFont(bold = true, italic = true, color = Font.COLOR_RED)), headStyle = @ExcelStyle(font = @ExcelFont(italic = true)))
    private String title;

    public static List<DemoVo> getList() {
        Random random = new Random(System.currentTimeMillis());
        int size = random.nextInt(5000);
        List<DemoVo> demoVoList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            DemoVo demoVo = DemoVo.builder()
                    .name("name-" + random.nextInt(900))
                    .title("title-" + random.nextInt(111))
                    .build();
            demoVoList.add(demoVo);
        }
        return demoVoList;
    }
}
```

## 3. 参与贡献

非常欢迎你的加入！[提一个 Issue](https://github.com/zouzhiy/zouzhiy-excel/issues/new) 或者提交一个 Pull Request。

## 6. 联系作者

`QQ`：`546963897`  
`email`：`546963897@qq.com`

## 7. 开源协议

[Apache 2.0](LICENSE) © zouzhiy
