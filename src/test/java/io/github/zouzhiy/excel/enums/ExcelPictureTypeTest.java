package io.github.zouzhiy.excel.enums;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

/**
 * @author zouzhiy
 * @since 2022/7/3
 */
class ExcelPictureTypeTest {

    @Test
    void getValue() {
        assert ExcelPictureType.PICTURE_TYPE_EMF.getValue() == Workbook.PICTURE_TYPE_EMF;
        assert ExcelPictureType.PICTURE_TYPE_WMF.getValue() == Workbook.PICTURE_TYPE_WMF;
        assert ExcelPictureType.PICTURE_TYPE_PICT.getValue() == Workbook.PICTURE_TYPE_PICT;
        assert ExcelPictureType.PICTURE_TYPE_JPEG.getValue() == Workbook.PICTURE_TYPE_JPEG;
        assert ExcelPictureType.PICTURE_TYPE_PNG.getValue() == Workbook.PICTURE_TYPE_PNG;
        assert ExcelPictureType.PICTURE_TYPE_DIB.getValue() == Workbook.PICTURE_TYPE_DIB;
    }
}