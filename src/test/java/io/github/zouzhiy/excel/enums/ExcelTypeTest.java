package io.github.zouzhiy.excel.enums;

import org.apache.poi.ss.usermodel.CellType;
import org.junit.jupiter.api.Test;

/**
 * @author zouzhiy
 * @since 2022/7/3
 */
class ExcelTypeTest {

    @Test
    void getCellType() {
        assert ExcelType.NONE.getCellType().equals(CellType._NONE);
        assert ExcelType.NUMERIC.getCellType().equals(CellType.NUMERIC);
        assert ExcelType.STRING.getCellType().equals(CellType.STRING);
        assert ExcelType.DATE.getCellType().equals(CellType.NUMERIC);
        assert ExcelType.BOOLEAN.getCellType().equals(CellType.BOOLEAN);
        assert ExcelType.BLANK.getCellType().equals(CellType.BLANK);
    }
}