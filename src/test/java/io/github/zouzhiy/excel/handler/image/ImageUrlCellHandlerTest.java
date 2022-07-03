package io.github.zouzhiy.excel.handler.image;

import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import org.junit.jupiter.api.Test;

/**
 * @author zouzhiy
 * @since 2022/7/3
 */
class ImageUrlCellHandlerTest {

    @Test
    void getCellValue() {
        try {
            new ImageUrlCellHandler().getCellValue(null, null, null);
            assert false;
        } catch (ExcelException e) {
            assert true;
        }
    }

    @Test
    void getExcelType() {
        assert new ImageUrlCellHandler().getExcelType().equals(ExcelType.BLANK);
    }
}