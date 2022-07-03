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
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.xssf.usermodel.*;

import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class ImageByteCellHandler extends AbstractImageCellHandler<byte[]> {

    @Override
    protected byte[] getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        Cell cell = firstCellResult.getCell();
        PictureData pictureData = this.getPictureData(cell);
        if (pictureData == null) {
            return new byte[0];
        } else {
            return pictureData.getData();
        }
    }

    @Override
    protected byte[] toImageUrlResource(RowContext rowContext, ExcelFieldConfig excelFieldConfig, byte[] value) {
        return value;
    }

    @Override
    public final ExcelType getExcelType() {
        return ExcelType.NONE;
    }

    protected PictureData getPictureData(Cell cell) {
        if (cell instanceof HSSFCell) {
            return getPictureData((HSSFCell) cell);
        } else if (cell instanceof XSSFCell) {
            return getPictureData((XSSFCell) cell);
        }
        return null;
    }

    private PictureData getPictureData(HSSFCell hssfCell) {
        HSSFSheet sheet = hssfCell.getSheet();
        HSSFPatriarch hssfShapes = sheet.getDrawingPatriarch();
        List<HSSFShape> hssfShapeList = hssfShapes.getChildren();

        for (HSSFShape hssfShape : hssfShapeList) {
            if (!(hssfShape instanceof HSSFPicture)) {
                continue;
            }
            HSSFClientAnchor hssfAnchor = (HSSFClientAnchor) hssfShape.getAnchor();
            if (hssfAnchor.getRow1() == hssfCell.getRowIndex() && hssfAnchor.getCol1() == hssfCell.getColumnIndex()) {
                return ((HSSFPicture) hssfShape).getPictureData();
            }
        }
        return null;
    }

    private PictureData getPictureData(XSSFCell xssfCell) {
        XSSFSheet sheet = xssfCell.getSheet();
        List<POIXMLDocumentPart> relations = sheet.getRelations();

        for (POIXMLDocumentPart documentPart : relations) {
            if (!(documentPart instanceof XSSFDrawing)) {
                continue;
            }

            XSSFDrawing xssfDrawing = (XSSFDrawing) documentPart;
            List<XSSFShape> xssfShapeList = xssfDrawing.getShapes();
            for (XSSFShape xssfShape : xssfShapeList) {
                XSSFPicture xssfPicture = (XSSFPicture) xssfShape;
                XSSFClientAnchor xssfAnchor = (XSSFClientAnchor) xssfPicture.getAnchor();
                if (xssfAnchor.getRow1() == xssfCell.getRowIndex() && xssfAnchor.getCol1() == xssfCell.getColumnIndex()) {
                    return xssfPicture.getPictureData();
                }
            }
        }
        return null;
    }
}
