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
package io.github.zouzhiy.excel.example.example3.handler;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.example.example3.matedata.Item;
import io.github.zouzhiy.excel.handler.AbstractWriteStringCellHandler;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;

import java.util.function.Supplier;
/**
 * @author zouzhiy
 * @since 2022/7/4
 */
public class ItemStringHandler extends AbstractWriteStringCellHandler<Item> {

    @Override
    protected Item getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        String stringValue = firstCellResult.getStringValue();
        if (stringValue == null || stringValue.length() == 0) {
            return null;
        }
        String[] strings = stringValue.split(",", -1);
        if (strings.length == 0) {
            return new Item("", "");
        }
        return new Item(strings[0], ((Supplier<String>) () -> {
            if (strings.length == 2) {
                return strings[1];
            }
            return "";
        }).get());
    }


    @Override
    protected String format(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Item value) {
        return String.format("%s,%s", value.getTitle() == null ? "" : value.getTitle(), value.getName() == null ? "" : value.getName());
    }
}
