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
package io.github.zouzhiy.excel.metadata.result;

import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import lombok.Getter;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 单元格样式信息
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
@Getter
public class CellStyleResult {
    /**
     * 单元格对应的字段
     */
    private final ExcelFieldConfig excelFieldConfig;
    /**
     * 单元格样式信息
     */
    private final CellStyle cellStyle;

    private CellStyleResult(ExcelFieldConfig excelFieldConfig, CellStyle cellStyle) {
        this.excelFieldConfig = excelFieldConfig;
        this.cellStyle = cellStyle;
    }

    /**
     * @param excelFieldConfig 字段配置信息
     * @param cellStyle        单元格样式
     * @return 样式信息对象
     */
    public static CellStyleResult newInstance(ExcelFieldConfig excelFieldConfig, CellStyle cellStyle) {
        return new CellStyleResult(excelFieldConfig, cellStyle);
    }
}
