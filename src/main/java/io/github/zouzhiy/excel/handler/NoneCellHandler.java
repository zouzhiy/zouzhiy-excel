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
package io.github.zouzhiy.excel.handler;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.CellResultSet;
import io.github.zouzhiy.excel.metadata.ExcelFieldConfig;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class NoneCellHandler implements CellHandler<Object> {

    @Override
    public Class<Object> getJavaType() {
        throw new ExcelException("不支持的方法");
    }

    @Override
    public ExcelType getExcelType() {
        throw new ExcelException("不支持的方法");
    }

    @Override
    public Object read(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResultSet cellResultSet) {
        throw new ExcelException("不支持的方法");
    }


    @Override
    public void write(RowContext rowContext, Integer columnIndex, ExcelFieldConfig excelFieldConfig, Object value) {
        throw new ExcelException("不支持的方法");

    }
}
