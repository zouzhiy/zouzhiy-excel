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

import io.github.zouzhiy.excel.annotation.ExcelFont;
import io.github.zouzhiy.excel.enums.FontTypeOffset;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.poi.common.usermodel.fonts.FontCharset;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontUnderline;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class ExcelFontConfig {

    /**
     * 字体名称
     */
    @Builder.Default
    private final String fontName = "宋体";

    /**
     * 字体大小。特殊单位
     */
    @Builder.Default
    private final short fontHeight = -1;

    /**
     * 字体大小。通常看到的字体大小
     */
    @Builder.Default
    private final short fontHeightInPoints = -1;

    /**
     * 斜体
     */
    @Builder.Default
    private final boolean italic = false;

    /**
     * 删除线
     */
    @Builder.Default
    private final boolean strikeout = false;

    /**
     * 字体颜色
     */
    @Builder.Default
    private final short color = Font.COLOR_NORMAL;

    /**
     *
     */
    @Builder.Default
    private final FontTypeOffset typeOffset = FontTypeOffset.NONE;

    /**
     * 下划线
     */
    @Builder.Default
    private final FontUnderline underline = FontUnderline.NONE;

    /**
     * 字符
     */
    @Builder.Default
    private final FontCharset charset = FontCharset.DEFAULT;

    /**
     * 加粗
     */
    @Builder.Default
    private final boolean bold = false;

    public static ExcelFontConfig getDefaultExcelFontConfigTitle() {
        return ExcelFontConfig.builder()
                .fontHeightInPoints((short) 16)
                .bold(true)
                .build();

    }

    public static ExcelFontConfig getDefaultExcelFontConfigHead() {
        return ExcelFontConfig.builder()
                .fontHeightInPoints((short) 13)
                .bold(true)
                .build();
    }

    public static ExcelFontConfig getDefaultExcelFontConfigData() {
        return ExcelFontConfig.builder()
                .fontHeightInPoints((short) 10)
                .build();
    }

    public static ExcelFontConfig buildByExcelFont(ExcelFont excelFont) {
        return ExcelFontConfig.builder()
                .fontName(excelFont.fontName())
                .fontHeight(excelFont.fontHeight())
                .fontHeightInPoints(excelFont.fontHeightInPoints())
                .italic(excelFont.italic())
                .strikeout(excelFont.strikeout())
                .color(excelFont.color())
                .typeOffset(excelFont.typeOffset())
                .underline(excelFont.underline())
                .charset(excelFont.charset())
                .bold(excelFont.bold())
                .build();
    }
}
