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
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.AbstractWriteStringCellHandler;
import io.github.zouzhiy.excel.metadata.CellResult;
import io.github.zouzhiy.excel.metadata.ExcelFieldConfig;

import java.nio.charset.StandardCharsets;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class ByteArrayStringHandler extends AbstractWriteStringCellHandler<byte[]> {

    @Override
    protected byte[] getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        String value = firstCellResult.getStringValue();
        return value.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected String format(RowContext rowContext, ExcelFieldConfig excelFieldConfig, byte[] value) {
        return new String(value, StandardCharsets.UTF_8);
    }

    @Override
    public ExcelType getExcelType() {
        return ExcelType.STRING;
    }
}
