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
import org.apache.poi.ss.usermodel.CellType;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public enum ExcelType {

    /**
     * 该类型下单元格的值都是none
     */
    NONE(CellType._NONE),

    NUMERIC(CellType.NUMERIC),

    STRING(CellType.STRING),

    DATE(CellType.NUMERIC),

    BOOLEAN(CellType.BOOLEAN),
    /**
     * 该类型下单元格的值存在，但是可能为空
     */
    BLANK(CellType.BLANK);

    @Getter
    private final CellType cellType;

    ExcelType(CellType cellType) {
        this.cellType = cellType;
    }

}
