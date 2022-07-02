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
package io.github.zouzhiy.excel.annotation;

import io.github.zouzhiy.excel.enums.StyleHorizontalAlignment;
import io.github.zouzhiy.excel.enums.StyleVerticalAlignment;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelStyle {

    ExcelFont font() default @ExcelFont;

    boolean hidden() default false;

    boolean locked() default false;

    boolean quotePrefix() default false;

    StyleHorizontalAlignment horizontalAlignment() default StyleHorizontalAlignment.GENERAL;

    boolean wrapText() default true;

    StyleVerticalAlignment verticalAlignment() default StyleVerticalAlignment.CENTER;

    short rotation() default 0;

    short indent() default -1;

    BorderStyle borderLeft() default BorderStyle.NONE;

    BorderStyle borderRight() default BorderStyle.NONE;

    BorderStyle borderTop() default BorderStyle.NONE;

    BorderStyle borderBottom() default BorderStyle.NONE;

    short leftBorderColor() default -1;

    short rightBorderColor() default -1;

    short topBorderColor() default -1;

    short bottomBorderColor() default -1;

    FillPatternType fillPattern() default FillPatternType.NO_FILL;

    short fillBackgroundColor() default -1;

    short fillForegroundColor() default -1;

    boolean shrinkToFit() default false;
}
