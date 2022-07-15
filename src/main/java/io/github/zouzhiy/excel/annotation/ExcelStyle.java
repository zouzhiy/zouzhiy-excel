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
 * 单元格样式
 * 对应{@link org.apache.poi.ss.usermodel.CellStyle}的样式属性
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelStyle {

    /**
     * @return 字体样式
     */
    ExcelFont font() default @ExcelFont;

    /**
     * @return 是否隐藏
     */
    boolean hidden() default false;

    /**
     * @return 单元格是否锁定
     */
    boolean locked() default false;

    /**
     * @return 单元格是否锁定
     */
    boolean quotePrefix() default false;

    /**
     * @return 水平对齐
     */
    StyleHorizontalAlignment horizontalAlignment() default StyleHorizontalAlignment.GENERAL;

    /**
     * @return 是否自动换行
     */
    boolean wrapText() default true;

    /**
     * @return 垂直对齐
     */
    StyleVerticalAlignment verticalAlignment() default StyleVerticalAlignment.CENTER;

    /**
     * 取 -90 到 90
     *
     * @return 文本旋转度数
     */
    short rotation() default 0;

    /**
     * 单元格文本缩进空格数
     *
     * @return 缩进空格数
     */
    short indent() default -1;

    /**
     * 左边框
     *
     * @return 边框类型
     */
    BorderStyle borderLeft() default BorderStyle.NONE;

    /**
     * 右边框
     *
     * @return 边框类型
     */
    BorderStyle borderRight() default BorderStyle.NONE;

    /**
     * 上边框
     *
     * @return 边框类型
     */
    BorderStyle borderTop() default BorderStyle.NONE;

    /**
     * 下边框
     *
     * @return 边框类型
     */
    BorderStyle borderBottom() default BorderStyle.NONE;

    /**
     * 左边框
     *
     * @return 边框颜色
     */
    short leftBorderColor() default -1;

    /**
     * 右边框
     *
     * @return 边框颜色
     */
    short rightBorderColor() default -1;

    /**
     * 上边框
     *
     * @return 边框颜色
     */
    short topBorderColor() default -1;

    /**
     * 下边框
     *
     * @return 边框颜色
     */
    short bottomBorderColor() default -1;

    /**
     * 前景色
     *
     * @return 类型
     */
    FillPatternType fillPattern() default FillPatternType.NO_FILL;

    /**
     * 背景色
     *
     * @return 颜色值
     */
    short fillBackgroundColor() default -1;

    /**
     * 前景色
     *
     * @return 颜色值
     */
    short fillForegroundColor() default -1;

    /**
     * 如果文本太长，控制单元格是否应自动调整大小以缩小以适应
     *
     * @return 是否自动缩小
     */
    boolean shrinkToFit() default false;
}
