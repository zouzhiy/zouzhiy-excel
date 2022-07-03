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
package io.github.zouzhiy.excel.handler.image;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.AbstractCellHandler;
import io.github.zouzhiy.excel.metadata.CellResult;
import io.github.zouzhiy.excel.metadata.ExcelFieldConfig;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class ImageUrlCellHandler extends AbstractCellHandler<String> {

    private final static String DEFAULT_IMAGE_TEMPLATE = "<table><img src=%s height=%s width=%s></table>";

    private final static int EQUAL_INDEX = DEFAULT_IMAGE_TEMPLATE.indexOf("=");

    @Override
    protected String getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        String stringValue = firstCellResult.getStringValue();
        if (stringValue.length() <= EQUAL_INDEX) {
            return null;
        }
        int heightIndex = stringValue.indexOf(" height=");
        if (heightIndex == -1) {
            return null;
        }

        return stringValue.substring(EQUAL_INDEX + 1, heightIndex);
    }

    @Override
    protected void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, String value) {
        String cellValue = String.format(DEFAULT_IMAGE_TEMPLATE, value, 50, 50);
        cell.setCellValue(cellValue);
    }

    @Override
    public ExcelType getExcelType() {
        return ExcelType.STRING;
    }
}
