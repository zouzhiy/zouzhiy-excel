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
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.StyleHorizontalAlignment;
import io.github.zouzhiy.excel.handler.CellHandler;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;
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
import java.util.List;

/**
 * 通过更改默认配置，可实现自定义的写入和读取
 * 配置标题的样式及单元格值
 * 如类上不加上此注解，则全部取默认值，无需显式注解
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ExcelClass {

    /**
     * 标题写入write,需要自定义复杂标题的可自定义实现{@link RowTitleWrite}
     *
     * @return RowTitleWrite对象的Class
     */
    Class<? extends RowTitleWrite> rowTitleWrite() default DefaultRowTitleWrite.class;

    /**
     * 表头写入write,需要自定义复杂表头的可自定义实现{@link RowHeadWrite}
     *
     * @return RowHeadWrite对象的Class
     */
    Class<? extends RowHeadWrite> rowHeadWrite() default DefaultRowHeadWrite.class;

    /**
     * 表尾写入write。默认的为空，即什么都不写。通过自定义实现{@link RowFootWrite}可写入表尾
     *
     * @return RowFootWrite对象的Class
     */
    Class<? extends RowFootWrite> rowFootWrite() default DefaultRowFootWrite.class;

    /**
     * 标题读取，复杂标题可通过实现{@link RowTitleRead}处理。
     * 读取结果可根据{@link io.github.zouzhiy.excel.callback.SheetReadConsumer#acceptReadTitle(SheetContext, CellResultSet)}回调获取
     *
     * @return RowTitleRead对象的Class
     */
    Class<? extends RowTitleRead> rowTitleRead() default DefaultRowTitleRead.class;

    /**
     * 表头读取，复杂标题可通过实现{@link RowHeadRead}处理。
     * 读取结果可根据{@link io.github.zouzhiy.excel.callback.SheetReadConsumer#acceptReadHead(SheetContext, List)}回调获取
     *
     * @return RowHeadRead对象的Class
     */
    Class<? extends RowHeadRead> rowHeadRead() default DefaultRowHeadRead.class;

    /**
     * 表尾读取，默认是空实现。需要可通过实现{@link RowFootRead}处理。
     * 读取结果可根据{@link io.github.zouzhiy.excel.callback.SheetReadConsumer#acceptReadFoot(SheetContext, List)}回调获取
     *
     * @return RowFootRead对象的Class
     */
    Class<? extends RowFootRead> rowFootRead() default DefaultRowFootRead.class;

    /**
     * 表格默认样式读取，主要用于导出模板单元格样式读取，然后应用到导出格式。
     *
     * @return RowStyleRead对象的Class
     */
    Class<? extends RowStyleRead> rowStyleRead() default DefaultRowStyleRead.class;

    /**
     * 标题数据格式
     *
     * @return 默认为字符串
     */
    String titleFormat() default CellHandler.DEFAULT_DATA_FORMAT_STRING;

    /**
     * 标题样式
     *
     * @return 定义的样式
     */
    ExcelStyle titleStyle() default @ExcelStyle(font = @ExcelFont(fontHeightInPoints = 16, bold = true), horizontalAlignment = StyleHorizontalAlignment.CENTER);

    /**
     * 是否自动调整列宽，优先级最高，包括比模板的优先级更高
     *
     * @return 是否自动调整列宽
     */
    boolean autoSizeColumn() default false;
}
