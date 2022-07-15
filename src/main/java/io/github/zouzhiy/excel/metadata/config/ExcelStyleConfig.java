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
package io.github.zouzhiy.excel.metadata.config;

import io.github.zouzhiy.excel.annotation.ExcelStyle;
import io.github.zouzhiy.excel.enums.StyleHorizontalAlignment;
import io.github.zouzhiy.excel.enums.StyleVerticalAlignment;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;

/**
 * 样式配置 {@link io.github.zouzhiy.excel.annotation.ExcelFont}
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class ExcelStyleConfig {

    @Builder.Default
    private final ExcelFontConfig font = ExcelFontConfig.getDefaultExcelFontConfigData();

    @Builder.Default
    private final boolean hidden = false;

    @Builder.Default
    private final boolean locked = false;

    @Builder.Default
    private final boolean quotePrefix = false;

    @Builder.Default
    private final StyleHorizontalAlignment horizontalAlignment = StyleHorizontalAlignment.GENERAL;

    @Builder.Default
    private final boolean wrapText = true;

    @Builder.Default
    private final StyleVerticalAlignment verticalAlignment = StyleVerticalAlignment.CENTER;

    @Builder.Default
    private final short rotation = 0;

    @Builder.Default
    private final short indent = -1;

    @Builder.Default
    private final BorderStyle borderLeft = BorderStyle.NONE;
    @Builder.Default
    private final BorderStyle borderRight = BorderStyle.NONE;
    @Builder.Default
    private final BorderStyle borderTop = BorderStyle.NONE;
    @Builder.Default
    private final BorderStyle borderBottom = BorderStyle.NONE;

    @Builder.Default
    private final short leftBorderColor = -1;
    @Builder.Default
    private final short rightBorderColor = -1;
    @Builder.Default
    private final short topBorderColor = -1;
    @Builder.Default
    private final short bottomBorderColor = -1;

    @Builder.Default
    private final FillPatternType fillPattern = FillPatternType.NO_FILL;

    @Builder.Default
    private final short fillBackgroundColor = -1;

    @Builder.Default
    private final short fillForegroundColor = -1;

    @Builder.Default
    private final boolean shrinkToFit = false;


    public static ExcelStyleConfig getDefaultExcelStyleConfigTitle() {

        return ExcelStyleConfig.builder()
                .font(ExcelFontConfig.getDefaultExcelFontConfigTitle())
                .horizontalAlignment(StyleHorizontalAlignment.CENTER)
                .verticalAlignment(StyleVerticalAlignment.CENTER)
                .build();
    }

    public static ExcelStyleConfig getDefaultExcelStyleConfigHead() {
        return ExcelStyleConfig.builder()
                .font(ExcelFontConfig.getDefaultExcelFontConfigHead())
                .horizontalAlignment(StyleHorizontalAlignment.CENTER)
                .verticalAlignment(StyleVerticalAlignment.CENTER)
                .build();
    }

    public static ExcelStyleConfig getDefaultExcelStyleConfigData() {
        return ExcelStyleConfig.builder()
                .font(ExcelFontConfig.getDefaultExcelFontConfigData())
                .horizontalAlignment(StyleHorizontalAlignment.GENERAL)
                .verticalAlignment(StyleVerticalAlignment.CENTER)
                .build();
    }

    public static ExcelStyleConfig buildByExcelStyle(ExcelStyle excelStyle) {
        return ExcelStyleConfig.builder()
                .font(ExcelFontConfig.buildByExcelFont(excelStyle.font()))
                .hidden(excelStyle.hidden())
                .locked(excelStyle.locked())
                .quotePrefix(excelStyle.quotePrefix())
                .horizontalAlignment(excelStyle.horizontalAlignment())
                .wrapText(excelStyle.wrapText())
                .verticalAlignment(excelStyle.verticalAlignment())
                .rotation(excelStyle.rotation())
                .indent(excelStyle.indent())
                .borderLeft(excelStyle.borderLeft())
                .borderRight(excelStyle.borderRight())
                .borderTop(excelStyle.borderTop())
                .borderBottom(excelStyle.borderBottom())
                .leftBorderColor(excelStyle.leftBorderColor())
                .rightBorderColor(excelStyle.rightBorderColor())
                .topBorderColor(excelStyle.topBorderColor())
                .bottomBorderColor(excelStyle.bottomBorderColor())
                .fillPattern(excelStyle.fillPattern())
                .fillBackgroundColor(excelStyle.fillBackgroundColor())
                .fillForegroundColor(excelStyle.fillForegroundColor())
                .shrinkToFit(excelStyle.shrinkToFit())
                .build();
    }

}
