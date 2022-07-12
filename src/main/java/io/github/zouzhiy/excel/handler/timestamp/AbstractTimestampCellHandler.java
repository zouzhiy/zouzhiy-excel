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
package io.github.zouzhiy.excel.handler.timestamp;

import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.handler.AbstractCellHandler;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author zouzhiy
 * @since 2022/7/10
 */
public abstract class AbstractTimestampCellHandler extends AbstractCellHandler<Timestamp> {

    @Override
    protected final Timestamp getCellValue(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResult firstCellResult) {
        LocalDateTime localDateTime = firstCellResult.getDateValue(this.getJavaFormat(excelFieldConfig));
        return localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }

}
