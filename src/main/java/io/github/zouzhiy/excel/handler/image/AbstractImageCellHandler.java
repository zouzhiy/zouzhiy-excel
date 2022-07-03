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
import io.github.zouzhiy.excel.handler.AbstractCellHandler;
import io.github.zouzhiy.excel.metadata.ExcelFieldConfig;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;

/**
 * @author zouzhiy
 * @since 2022/7/3
 */
public abstract class AbstractImageCellHandler<T> extends AbstractCellHandler<T> {
    @Override
    protected final void setCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, T value) {
        byte[] bytes = this.toImageUrlResource(rowContext, excelFieldConfig, value);
        addImageCellValue(rowContext, excelFieldConfig, cell, bytes);
    }

    protected abstract byte[] toImageUrlResource(RowContext rowContext, ExcelFieldConfig excelFieldConfig, T value);

    private void addImageCellValue(RowContext rowContext, ExcelFieldConfig excelFieldConfig, Cell cell, byte[] bytes) {
        Workbook workbook = rowContext.getSheetContext().getWorkbook();
        Drawing<?> drawing = rowContext.getSheetContext().getDrawing();

        CreationHelper creationHelper = workbook.getCreationHelper();
        ClientAnchor clientAnchor = creationHelper.createClientAnchor();
        clientAnchor.setRow1(cell.getRowIndex());
        clientAnchor.setRow2(cell.getRowIndex());
        clientAnchor.setCol1(cell.getColumnIndex());
        clientAnchor.setCol2(cell.getColumnIndex());
        int x1 = 0;
        int y1 = 0;

        int x2 = 10240;
        int y2 = 10240;
        if (clientAnchor instanceof XSSFClientAnchor) {
            x2 = x2 * 100;
            y2 = y2 * 100;
        }

        clientAnchor.setDx1(x1);
        clientAnchor.setDx2(x2);
        clientAnchor.setDy1(y1);
        clientAnchor.setDy2(y2);
        drawing.createPicture(clientAnchor, workbook.addPicture(bytes, Workbook.PICTURE_TYPE_EMF));
    }


}
