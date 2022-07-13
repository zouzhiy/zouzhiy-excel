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

import io.github.zouzhiy.excel.annotation.ExcelClass;
import io.github.zouzhiy.excel.cellstyle.RowStyleRead;
import io.github.zouzhiy.excel.cellstyle.defaults.DefaultRowStyleRead;
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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class ExcelClassConfig {

    @Builder.Default
    private final Class<? extends RowTitleWrite> rowTitleWrite = DefaultRowTitleWrite.class;

    @Builder.Default
    private final Class<? extends RowHeadWrite> rowHeadWrite = DefaultRowHeadWrite.class;

    @Builder.Default
    private final Class<? extends RowFootWrite> rowFootWrite = DefaultRowFootWrite.class;

    @Builder.Default
    private final Class<? extends RowTitleRead> rowTitleRead = DefaultRowTitleRead.class;

    @Builder.Default
    private final Class<? extends RowHeadRead> rowHeadRead = DefaultRowHeadRead.class;

    @Builder.Default
    private final Class<? extends RowFootRead> rowFootRead = DefaultRowFootRead.class;

    @Builder.Default
    private final String titleFormat = "@";

    @Builder.Default
    private final ExcelStyleConfig titleStyle = ExcelStyleConfig.getDefaultExcelStyleConfigTitle();

    @Builder.Default
    private final Class<? extends RowStyleRead> rowStyleRead = DefaultRowStyleRead.class;

    @Builder.Default
    private final List<ExcelFieldConfig> itemList = Collections.emptyList();

    public static ExcelClassConfig getDefaultExcelClassConfig(List<ExcelFieldConfig> itemList) {
        return ExcelClassConfig.builder()
                .itemList(itemList)
                .build();
    }

    public static ExcelClassConfig buildByExcelClass(ExcelClass excelClass, List<ExcelFieldConfig> excelFieldConfigList) {
        return ExcelClassConfig.builder()
                .rowTitleWrite(excelClass.rowTitleWrite())
                .rowHeadWrite(excelClass.rowHeadWrite())
                .rowFootWrite(excelClass.rowFootWrite())
                .rowTitleRead(excelClass.rowTitleRead())
                .rowHeadRead(excelClass.rowHeadRead())
                .rowFootRead(excelClass.rowFootRead())
                .titleStyle(ExcelStyleConfig.buildByExcelStyle(excelClass.titleStyle()))
                .rowStyleRead(excelClass.rowStyleRead())
                .titleFormat(excelClass.titleFormat())
                .itemList(excelFieldConfigList)
                .build();
    }
}
