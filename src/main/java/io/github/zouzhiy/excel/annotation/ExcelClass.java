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

import io.github.zouzhiy.excel.cellstyle.RowStyleRead;
import io.github.zouzhiy.excel.cellstyle.defaults.DefaultRowStyleRead;
import io.github.zouzhiy.excel.enums.StyleHorizontalAlignment;
import io.github.zouzhiy.excel.handler.CellHandler;
import io.github.zouzhiy.excel.read.RowFootRead;
import io.github.zouzhiy.excel.read.RowHeadRead;
import io.github.zouzhiy.excel.read.RowTitleRead;
import io.github.zouzhiy.excel.read.defaults.DefaultRowFootRead;
import io.github.zouzhiy.excel.read.defaults.DefaultRowHeadRead;
import io.github.zouzhiy.excel.read.defaults.DefaultRowTitleRead;
import io.github.zouzhiy.excel.write.RowFootWrite;
import io.github.zouzhiy.excel.write.RowHeadWrite;
import io.github.zouzhiy.excel.write.RowTitleWrite;
import io.github.zouzhiy.excel.write.defaults.DefaultRowFootWrite;
import io.github.zouzhiy.excel.write.defaults.DefaultRowHeadWrite;
import io.github.zouzhiy.excel.write.defaults.DefaultRowTitleWrite;

import java.lang.annotation.*;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ExcelClass {

    Class<? extends RowTitleWrite> rowTitleWrite() default DefaultRowTitleWrite.class;

    Class<? extends RowHeadWrite> rowHeadWrite() default DefaultRowHeadWrite.class;

    Class<? extends RowFootWrite> rowFootWrite() default DefaultRowFootWrite.class;

    Class<? extends RowTitleRead> rowTitleRead() default DefaultRowTitleRead.class;

    Class<? extends RowHeadRead> rowHeadRead() default DefaultRowHeadRead.class;

    Class<? extends RowFootRead> rowFootRead() default DefaultRowFootRead.class;

    Class<? extends RowStyleRead> rowStyleRead() default DefaultRowStyleRead.class;

    String titleFormat() default CellHandler.DEFAULT_DATA_FORMAT_STRING;

    ExcelStyle titleStyle() default @ExcelStyle(font = @ExcelFont(fontHeightInPoints = 16, bold = true), horizontalAlignment = StyleHorizontalAlignment.CENTER);

}
