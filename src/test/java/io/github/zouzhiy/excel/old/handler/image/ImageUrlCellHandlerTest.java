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
package io.github.zouzhiy.excel.old.handler.image;

import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.image.ImageUrlCellHandler;
import org.junit.jupiter.api.Test;

/**
 * @author zouzhiy
 * @since 2022/7/3
 */
class ImageUrlCellHandlerTest {

    @Test
    void getCellValue() {

    }

    @Test
    void getExcelType() {
        assert new ImageUrlCellHandler().getExcelType().equals(ExcelType.BLANK);
    }
}