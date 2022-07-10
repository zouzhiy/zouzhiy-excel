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
package io.github.zouzhiy.excel.handler.bytes;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import org.apache.poi.ss.usermodel.Cell;


/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class ByteBooleanHandler extends AbstractByteCellHandler {

    private static final Byte TRUE_VALUE = (byte) 1;

    @Override
    protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, Byte value) {
        cell.setCellValue(value.compareTo(TRUE_VALUE) == 0);
    }

    @Override
    public ExcelType getExcelType() {
        return ExcelType.BOOLEAN;
    }
}
