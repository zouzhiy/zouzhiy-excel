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
import lombok.Setter;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * 单元格样式结果集
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
@Setter
public class CellStyleResultSet {

    @Getter
    private List<CellStyleResult> cellStyleResultList = new ArrayList<>(32);

    private CellStyleResultSet() {
    }

    /**
     * 空
     *
     * @return 空结果集
     */
    public static CellStyleResultSet empty() {
        return new CellStyleResultSet();
    }

    /**
     * 新增结果
     *
     * @param excelFieldConfig 字段配置信息
     * @param cellStyle        单元格样式
     */
    public void cellStyleResult(ExcelFieldConfig excelFieldConfig, CellStyle cellStyle) {
        cellStyleResultList.add(CellStyleResult.newInstance(excelFieldConfig, cellStyle));
    }


}
