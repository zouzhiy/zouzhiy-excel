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

import lombok.Getter;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author zouzhiy
 * @since 2022/7/3
 */
public enum ExcelPictureType {
    /**
     * 图片格式
     */
    PICTURE_TYPE_EMF(Workbook.PICTURE_TYPE_EMF),
    PICTURE_TYPE_WMF(Workbook.PICTURE_TYPE_WMF),
    PICTURE_TYPE_PICT(Workbook.PICTURE_TYPE_PICT),
    PICTURE_TYPE_JPEG(Workbook.PICTURE_TYPE_JPEG),
    PICTURE_TYPE_PNG(Workbook.PICTURE_TYPE_PNG),
    PICTURE_TYPE_DIB(Workbook.PICTURE_TYPE_DIB);

    @Getter
    private final int value;

    ExcelPictureType(int value) {
        this.value = value;
    }
}
