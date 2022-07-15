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
 * 字段配置
 * 如字段上不加上此注解，则全部取默认值，无需显式注解
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelField {

    /**
     * 表头
     *
     * @return 表头名
     */
    String title() default "";

    /**
     * 单元格类型。
     * <pre>
     *     {@link ExcelType#NONE} 一般指图片格式，不占用单元格内容，只是渲染在单元格这个位置
     *     {@link ExcelType#BLANK} 默认配置，程序根据java对象类型，自动匹配标准的单元格类型。{@link io.github.zouzhiy.excel.handler.ExcelStandardMapping}
     * </pre>
     *
     * @return 单元格类型
     */
    ExcelType excelType() default ExcelType.BLANK;

    /**
     * 单元格值到java值的转换。按顺序优先匹配。采取条件匹配模式，最符合的{@link io.github.zouzhiy.excel.handler.CellHandler}胜出。但并一定严格符合匹配条件。
     * 详见{@link io.github.zouzhiy.excel.handler.CellHandlerRegistry#getCellHandler(Class[], Class, ExcelType)}逻辑判断
     *
     * @return CellHandler列表
     */
    Class<? extends CellHandler<?>>[] cellHandler() default {};

    /**
     * 此字段占用的列数量
     *
     * @return 列数量
     */
    int colspan() default 1;

    /**
     * 表头数据格式。默认为文本型
     *
     * @return 表头数据格式
     */
    String headFormat() default CellHandler.DEFAULT_DATA_FORMAT_STRING;

    /**
     * 列表数据 java转成实际单元格值的格式化格式。如：0.00，则java值:3,表头值格式化成字符串后：3.00
     * 用于单元格为文本，但是java对象不为{@link String},需要将java对象格式化成{@link String}.一般用于{@link Number}、{@link java.util.Date} 格式化成字符串，再写入单元格
     *
     * @return java对象转字符串的格式化format
     */
    String javaFormat() default "";

    /**
     * 列表单元格数据格式。
     *
     * @return 单元格数据格式
     */
    String excelFormat() default "";

    /**
     * 排序号。根据排序号升序，确定字段在表格中的前后顺序
     *
     * @return 排序号
     */
    long sort() default 0;

    /**
     * 表头样式
     *
     * @return 自定义表头样式
     */
    ExcelStyle headStyle() default @ExcelStyle(font = @ExcelFont(fontHeightInPoints = 13, bold = true), horizontalAlignment = StyleHorizontalAlignment.CENTER);

    /**
     * 数据样式
     *
     * @return 自定义数据样式
     */
    ExcelStyle dataStyle() default @ExcelStyle();

    /**
     * 是否忽略。因约定字段都需要导入，故需要忽略的字段需要显式忽略
     * todo 后续需要加个单独的注解
     *
     * @return 是否忽略
     */
    boolean ignore() default false;
}
