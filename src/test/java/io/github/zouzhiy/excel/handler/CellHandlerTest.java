package io.github.zouzhiy.excel.handler;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.metadata.CellSpan;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

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
