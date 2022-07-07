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
package io.github.zouzhiy.excel.old.enums;

import io.github.zouzhiy.excel.enums.ExcelType;
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