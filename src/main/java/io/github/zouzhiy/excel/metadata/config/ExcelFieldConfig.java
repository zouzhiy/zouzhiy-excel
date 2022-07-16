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

import io.github.zouzhiy.excel.annotation.ExcelField;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.CellHandler;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


/**
 * 字段配置 {@link io.github.zouzhiy.excel.annotation.ExcelField}
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class ExcelFieldConfig {

    @Builder.Default
    private final String title = "";

    @Builder.Default
    private final double width = -1D;

    @Builder.Default
    private final String propertyName = "";

    @Builder.Default
    private final Class<?> javaType = Object.class;

    @Builder.Default
    private final ExcelType excelType = ExcelType.BLANK;

    @SuppressWarnings("unchecked")
    @Builder.Default
    private final Class<? extends CellHandler<?>>[] cellHandler = (Class<? extends CellHandler<?>>[]) new Class<?>[0];

    @Builder.Default
    private final int colspan = 1;

    @Builder.Default
    private final String headFormat = "@";

    @Builder.Default
    private final String javaFormat = "";

    @Builder.Default
    private final String excelFormat = "";

    @Builder.Default
    private final long sort = 0L;

    @Builder.Default
    private final ExcelStyleConfig headStyle = ExcelStyleConfig.getDefaultExcelStyleConfigHead();

    @Builder.Default
    private final ExcelStyleConfig dataStyle = ExcelStyleConfig.getDefaultExcelStyleConfigData();


    public static ExcelFieldConfig getDefaultExcelFieldConfig(String propertyName, Class<?> javaType) {
        return ExcelFieldConfig.builder()
                .title(propertyName)
                .propertyName(propertyName)
                .javaType(javaType)
                .build();
    }


    public static ExcelFieldConfig buildByExcelField(ExcelField excelField, String propertyName, Class<?> javaType) {
        return ExcelFieldConfig
                .builder()
                .title(excelField.title().length() == 0 ? propertyName : excelField.title())
                .width(excelField.width())
                .propertyName(propertyName)
                .javaType(javaType)
                .excelType(excelField.excelType())
                .cellHandler(excelField.cellHandler())
                .colspan(excelField.colspan())
                .headFormat(excelField.headFormat())
                .javaFormat(excelField.javaFormat())
                .excelFormat(excelField.excelFormat())
                .sort(excelField.sort())
                .headStyle(ExcelStyleConfig.buildByExcelStyle(excelField.headStyle()))
                .dataStyle(ExcelStyleConfig.buildByExcelStyle(excelField.dataStyle()))
                .build();
    }
}
