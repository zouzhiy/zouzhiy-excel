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
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class ImageFileCellHandler extends AbstractImageCellHandler<File> {

    @Override
    protected File getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        return null;
    }

    @Override
    protected byte[] toImageUrlResource(RowContext rowContext, ExcelFieldConfig excelFieldConfig, File value) {
        try {
            return IOUtils.toByteArray(new FileInputStream(value));
        } catch (IOException e) {
            throw new ExcelException("读取图片失败");
        }
    }

    @Override
    public final ExcelType getExcelType() {
        return ExcelType.NONE;
    }
}
