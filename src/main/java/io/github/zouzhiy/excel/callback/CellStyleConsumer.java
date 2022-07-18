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
package io.github.zouzhiy.excel.callback;

import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 单元格样式埋点回调
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
public interface CellStyleConsumer {

    /**
     * 创建标题样式后回调
     * 可通过改变 titleCellStyle 更新自定义标题样式
     *
     * @param excelClassConfig 配置信息
     * @param titleCellStyle   标题样式
     */
    default void afterCreateTitleCellStyle(ExcelClassConfig excelClassConfig, CellStyle titleCellStyle) {
    }

    /**
     * 表头样式回调
     *
     * @param excelFieldConfig 字段配置
     * @param headCellStyle    对应的字段表头样式
     */
    default void afterCreateHeadCellStyle(ExcelFieldConfig excelFieldConfig, CellStyle headCellStyle) {
    }

    /**
     * 单元格样式回调
     *
     * @param excelFieldConfig  字段配置
     * @param defaultDataFormat 默认的单元格数字格式
     * @param dataCellStyle     单元格样式
     */
    default void afterCreateDataCellStyle(ExcelFieldConfig excelFieldConfig, String defaultDataFormat, CellStyle dataCellStyle) {
    }
}
