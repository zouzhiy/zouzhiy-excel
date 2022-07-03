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
package io.github.zouzhiy.excel.utils;

import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.metadata.MergedRegion;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class RegionUtils {

    public static void addMergedRegionIfPresent(SheetContext sheetContext, CellStyle cellStyle, int firstRow, int lastRow, int firstCol, int lastCol) {
        if (lastRow < 0 || lastCol < 0) {
            return;
        }
        if (lastRow - firstRow == 0 && lastCol - firstCol == 0) {
            return;
        }
        MergedRegion mergedRegion = sheetContext.getMergedRegion();
        if (mergedRegion.unHasMergedRegion(firstRow, lastRow, firstCol, lastCol)) {
            Sheet sheet = sheetContext.getSheet();
            CellRangeAddress cellAddresses = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
            RegionUtils.fillStyle(cellStyle, cellAddresses, sheet);
            sheet.addMergedRegion(cellAddresses);
        }

    }

    private static void fillStyle(CellStyle cellStyle, CellRangeAddress region, Sheet sheet) {
        RegionUtil.setBorderLeft(cellStyle.getBorderLeft(), region, sheet);
        RegionUtil.setLeftBorderColor(cellStyle.getBottomBorderColor(), region, sheet);
        RegionUtil.setBorderRight(cellStyle.getBorderLeft(), region, sheet);
        RegionUtil.setRightBorderColor(cellStyle.getBottomBorderColor(), region, sheet);
        RegionUtil.setBorderBottom(cellStyle.getBorderLeft(), region, sheet);
        RegionUtil.setBottomBorderColor(cellStyle.getBottomBorderColor(), region, sheet);
        RegionUtil.setBorderTop(cellStyle.getBorderLeft(), region, sheet);
        RegionUtil.setTopBorderColor(cellStyle.getBottomBorderColor(), region, sheet);
    }

}
