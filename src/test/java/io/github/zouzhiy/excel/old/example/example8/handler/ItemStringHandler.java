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
package io.github.zouzhiy.excel.old.example.example8.handler;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.AbstractCellHandler;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import io.github.zouzhiy.excel.old.example.example8.matedata.Item;
import org.apache.poi.ss.usermodel.Cell;

import java.util.function.Supplier;

/**
 * @author zouzhiy
 * @since 2022/7/4
 */
public class ItemStringHandler extends AbstractCellHandler<Item> {

    @Override
    protected Item getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        String item = firstCellResult.getStringValue();
        if (item == null) {
            return null;
        } else if (item.length() == 0) {
            return new Item();
        }
        String[] strings = item.split(",", -1);
        if (strings.length == 0) {
            return new Item(null, null);
        }
        return new Item(strings[0].equals("null") ? null : strings[0], ((Supplier<String>) () -> {
            if (strings.length == 2) {
                return strings[1].equals("null") ? null : strings[1];
            }
            return null;
        }).get());
    }

    @Override
    protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, Item value) {
        cell.setCellValue(String.format("%s,%s", value.getUsername() == null ? null : value.getUsername(), value.getTel() == null ? null : value.getTel()));
    }

    @Override
    public ExcelType getExcelType() {
        return ExcelType.STRING;
    }
}
