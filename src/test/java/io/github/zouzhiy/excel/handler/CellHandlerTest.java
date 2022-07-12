package io.github.zouzhiy.excel.handler;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.metadata.CellSpan;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;
import io.github.zouzhiy.excel.utils.RegionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@ExtendWith(MockitoExtension.class)
abstract class CellHandlerTest {

    protected final Random random = new Random(System.currentTimeMillis());

    @Mock
    protected SheetContext sheetContext;

    @Mock
    protected ExcelFieldConfig excelFieldConfig;

    @Mock
    protected CellResultSet cellResultSet;

    @Mock
    protected RowContext rowContext;

    @Mock
    protected Row row;

    @Mock
    protected Cell cell;

    @Mock
    protected CellStyle cellStyle;

    @Mock
    protected CellSpan cellSpan;

    protected static MockedStatic<RegionUtils> regionUtilsMockedStatic;

    @BeforeAll
    static void init() {
        regionUtilsMockedStatic = Mockito.mockStatic(RegionUtils.class);
    }

    @AfterAll
    static void close() {
        regionUtilsMockedStatic.close();
    }

    abstract void getJavaType();

    abstract void getExcelType();

    abstract void read();

    abstract void write();

    /**
     * 根据数据判断，写入excel影响的行数
     */
    abstract void getWriteRowspan();

    abstract void getDefaultJavaFormat();

    abstract void getDefaultExcelFormat();
}
