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

import io.github.zouzhiy.excel.enums.FontTypeOffset;
import org.apache.poi.common.usermodel.fonts.FontCharset;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontUnderline;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 字体样式。对应{@link org.apache.poi.ss.usermodel.Font}的属性字段
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelFont {

    /**
     * 字体名称
     */
    String fontName() default "宋体";

    /**
     * 字体大小。特殊单位
     */
    short fontHeight() default -1;

    /**
     * 字体大小。通常看到的字体大小
     */
    short fontHeightInPoints() default -1;

    /**
     * 斜体
     */
    boolean italic() default false;

    /**
     * 删除线
     */
    boolean strikeout() default false;

    /**
     * 字体颜色
     */
    short color() default Font.COLOR_NORMAL;

    /**
     *
     */
    FontTypeOffset typeOffset() default FontTypeOffset.NONE;

    /**
     * 下划线
     */
    FontUnderline underline() default FontUnderline.NONE;

    /**
     * 字符
     */
    FontCharset charset() default FontCharset.DEFAULT;

    /**
     * 加粗
     */
    boolean bold() default false;

}
