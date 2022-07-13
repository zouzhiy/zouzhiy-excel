package io.github.zouzhiy.excel.metadata.result;

import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.CellSpan;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CellResultTest {

    private final Random random = new Random(System.currentTimeMillis());

    @Spy
    private Cell cell;

    @Mock
    private CellSpan cellSpan;

    public CellResultTest() throws IOException {
        cell = Mockito.spy(WorkbookFactory.create(true).createSheet().createRow(0).createCell(0));
    }

    @BeforeEach
    void beforeEach() {
        Mockito.when(cell.getColumnIndex()).thenReturn(random.nextInt());
        Mockito.when(cell.getRowIndex()).thenReturn(random.nextInt());
        Mockito.when(cellSpan.getRowspan()).thenReturn(random.nextInt());
        Mockito.when(cellSpan.getColspan()).thenReturn(random.nextInt());
    }

    @Test
    void none() {
        CellResult cellResult = CellResult.none();

        assertNull(cellResult.getCell());
        assertEquals(cellResult.getRowIndex(), -1);
        assertEquals(cellResult.getColumnIndex(), -1);
        assertEquals(cellResult.getRowspan(), 1);
        assertEquals(cellResult.getColspan(), 1);

        assertNull(cellResult.getStringValue());
        assertNull(cellResult.getNumberValue());
        assertNull(cellResult.getBooleanValue());
        assertNull(cellResult.getDateValue(null));

        assertTrue(cellResult.isNone());
        assertFalse(cellResult.isBlank());

        CellResult.blank(cell, cellSpan);
    }

    @RepeatedTest(10)
    void blank() {
        CellResult cellResult = CellResult.blank(cell, cellSpan);

        assertEquals(cell, cellResult.getCell());
        assertEquals(cellResult.getRowIndex(), cell.getRowIndex());
        assertEquals(cellResult.getColumnIndex(), cell.getColumnIndex());
        assertEquals(cellResult.getRowspan(), cellSpan.getRowspan());
        assertEquals(cellResult.getColspan(), cellSpan.getColspan());
        assertNull(cellResult.getStringValue());
        assertNull(cellResult.getNumberValue());
        assertNull(cellResult.getBooleanValue());
        assertNull(cellResult.getDateValue(null));

        assertFalse(cellResult.isNone());
        assertTrue(cellResult.isBlank());
    }

    @RepeatedTest(20)
    void numberTest1() {
        double numberValue = random.nextDouble();
        CellResult cellResult = CellResult.valueOf(cell, cellSpan, numberValue);
        Mockito.doReturn(numberValue).when(cell).getNumericCellValue();
        Mockito.doReturn(CellType.NUMERIC).when(cell).getCellType();

        assertEquals(cell, cellResult.getCell());
        assertEquals(cellResult.getRowIndex(), cell.getRowIndex());
        assertEquals(cellResult.getColumnIndex(), cell.getColumnIndex());
        assertEquals(cellResult.getRowspan(), cellSpan.getRowspan());
        assertEquals(cellResult.getColspan(), cellSpan.getColspan());
        assertEquals(cellResult.getStringValue(), String.valueOf(numberValue));
        assertEquals(cellResult.getNumberValue(), BigDecimal.valueOf(numberValue));
        assertEquals(cellResult.getBooleanValue(), numberValue - 1 == 0);
        assertEquals(cellResult.getDateValue(null), cell.getLocalDateTimeCellValue());

        assertFalse(cellResult.isNone());
        assertFalse(cellResult.isBlank());

        assertEquals(cellResult.getStringValue(), cellResult.getStringValue());
        assertEquals(cellResult.getNumberValue(), cellResult.getNumberValue());
        assertEquals(cellResult.getBooleanValue(), cellResult.getBooleanValue());
        assertEquals(cellResult.getDateValue(null), cellResult.getDateValue(null));
    }

    @RepeatedTest(20)
    void numberTest2() {
        double numberValue = random.nextDouble() * -1;
        CellResult cellResult = CellResult.valueOf(cell, cellSpan, numberValue);
        Mockito.doReturn(numberValue).when(cell).getNumericCellValue();
        Mockito.doReturn(CellType.NUMERIC).when(cell).getCellType();

        assertEquals(cell, cellResult.getCell());
        assertEquals(cellResult.getRowIndex(), cell.getRowIndex());
        assertEquals(cellResult.getColumnIndex(), cell.getColumnIndex());
        assertEquals(cellResult.getRowspan(), cellSpan.getRowspan());
        assertEquals(cellResult.getColspan(), cellSpan.getColspan());
        assertEquals(cellResult.getStringValue(), String.valueOf(numberValue));
        assertEquals(cellResult.getNumberValue(), BigDecimal.valueOf(numberValue));
        assertEquals(cellResult.getBooleanValue(), numberValue - 1 == 0);
        assertNull(cellResult.getDateValue(null));

        assertFalse(cellResult.isNone());
        assertFalse(cellResult.isBlank());

        assertEquals(cellResult.getStringValue(), cellResult.getStringValue());
        assertEquals(cellResult.getNumberValue(), cellResult.getNumberValue());
        assertEquals(cellResult.getBooleanValue(), cellResult.getBooleanValue());
        assertEquals(cellResult.getDateValue(null), cellResult.getDateValue(null));
    }

    @RepeatedTest(20)
    void booleanTest() {
        boolean booleanValue = random.nextBoolean();

        CellResult cellResult = CellResult.valueOf(cell, cellSpan, booleanValue);
        assertEquals(cell, cellResult.getCell());
        assertEquals(cellResult.getRowIndex(), cell.getRowIndex());
        assertEquals(cellResult.getColumnIndex(), cell.getColumnIndex());
        assertEquals(cellResult.getRowspan(), cellSpan.getRowspan());
        assertEquals(cellResult.getColspan(), cellSpan.getColspan());
        assertEquals(cellResult.getStringValue(), String.valueOf(booleanValue));
        assertEquals(cellResult.getNumberValue(), booleanValue ? BigDecimal.ONE : BigDecimal.ZERO);
        assertEquals(cellResult.getBooleanValue(), booleanValue);
        assertNull(cellResult.getDateValue(null));

        assertFalse(cellResult.isNone());
        assertFalse(cellResult.isBlank());

        assertEquals(cellResult.getStringValue(), cellResult.getStringValue());
        assertEquals(cellResult.getNumberValue(), cellResult.getNumberValue());
        assertEquals(cellResult.getBooleanValue(), cellResult.getBooleanValue());
        assertNull(cellResult.getDateValue(null));
    }


    @Test
    void stringString() {
        String stringValue = "test" + random.nextInt();

        CellResult cellResult = CellResult.valueOf(cell, cellSpan, stringValue);
        assertEquals(cell, cellResult.getCell());
        assertEquals(cellResult.getRowIndex(), cell.getRowIndex());
        assertEquals(cellResult.getColumnIndex(), cell.getColumnIndex());
        assertEquals(cellResult.getRowspan(), cellSpan.getRowspan());
        assertEquals(cellResult.getColspan(), cellSpan.getColspan());

        assertEquals(cellResult.getStringValue(), stringValue);
        assertThrows(NumberFormatException.class, cellResult::getNumberValue);
        assertEquals(cellResult.getBooleanValue(), Boolean.parseBoolean(stringValue));
        assertThrows(ExcelException.class, () -> cellResult.getDateValue(null));

        assertFalse(cellResult.isNone());
        assertFalse(cellResult.isBlank());

        assertEquals(cellResult.getStringValue(), cellResult.getStringValue());
        assertEquals(cellResult.getBooleanValue(), cellResult.getBooleanValue());
    }

    @RepeatedTest(20)
    void stringNumber() {
        String stringValue = "" + random.nextInt();

        CellResult cellResult = CellResult.valueOf(cell, cellSpan, stringValue);
        assertEquals(cell, cellResult.getCell());
        assertEquals(cellResult.getRowIndex(), cell.getRowIndex());
        assertEquals(cellResult.getColumnIndex(), cell.getColumnIndex());
        assertEquals(cellResult.getRowspan(), cellSpan.getRowspan());
        assertEquals(cellResult.getColspan(), cellSpan.getColspan());

        assertEquals(cellResult.getStringValue(), stringValue);
        assertEquals(cellResult.getBooleanValue(), Boolean.parseBoolean(stringValue));
        assertEquals(cellResult.getNumberValue(), new BigDecimal(stringValue));
        assertThrows(ExcelException.class, () -> cellResult.getDateValue(null));

        assertFalse(cellResult.isNone());
        assertFalse(cellResult.isBlank());


        assertEquals(cellResult.getStringValue(), cellResult.getStringValue());
        assertEquals(cellResult.getNumberValue(), cellResult.getNumberValue());
        assertEquals(cellResult.getBooleanValue(), cellResult.getBooleanValue());
    }

    @Test
    void stringDate() {
        String stringValue = "2022,12,11,22,33,23";
        CellResult cellResult = CellResult.valueOf(cell, cellSpan, stringValue);

        LocalDateTime localDateTime = LocalDateTime.of(2022, 12, 11, 22, 33, 23);

        Assertions.assertEquals(localDateTime, cellResult.getDateValue("yyyy,MM,dd,HH,mm,ss"));
    }

}
