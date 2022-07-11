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

import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.enums.StyleHorizontalAlignment;
import io.github.zouzhiy.excel.handler.CellHandler;

import java.lang.annotation.*;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelField {

    String title() default "";

    ExcelType excelType() default ExcelType.BLANK;

    Class<? extends CellHandler<?>>[] cellHandler() default {};

    int colspan() default 1;

    String headFormat() default CellHandler.DEFAULT_DATA_FORMAT_STRING;

    String javaFormat() default "";

    String excelFormat() default "";

    long sort() default 0;

    ExcelStyle headStyle() default @ExcelStyle(font = @ExcelFont(fontHeightInPoints = 13, bold = true), horizontalAlignment = StyleHorizontalAlignment.CENTER);

    ExcelStyle dataStyle() default @ExcelStyle();

    boolean ignore() default false;
}
