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