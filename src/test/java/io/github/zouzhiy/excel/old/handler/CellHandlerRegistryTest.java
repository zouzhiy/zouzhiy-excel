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
package io.github.zouzhiy.excel.old.handler;

import io.github.zouzhiy.excel.old.builder.Demo;
import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.handler.CellHandler;
import io.github.zouzhiy.excel.handler.CellHandlerRegistry;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;
import org.junit.jupiter.api.Test;

/**
 * @author zouzhiy
 * @since 2022/7/3
 */
class CellHandlerRegistryTest {

    private Configuration configuration = new Configuration();

    @Test
    void register() {

    }

    @Test
    void getMappingCellHandler() {
        CellHandlerRegistry cellHandlerRegistry = configuration.getCellHandlerRegistry();
        try {

            cellHandlerRegistry.getMappingCellHandler(new CellHandler<String>() {

                @Override
                public Class<String> getJavaType() {
                    return String.class;
                }

                @Override
                public ExcelType getExcelType() {
                    return ExcelType.STRING;
                }

                @Override
                public String read(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResultSet cellResultSet) {
                    return null;
                }

                @Override
                public void write(RowContext rowContext, Integer columnIndex, ExcelFieldConfig excelFieldConfig, String value) {

                }
            }.getClass());
            assert false;
        } catch (ExcelException e) {
            assert true;
        }
    }

    @Test
    void getCellHandler() {
        CellHandlerRegistry cellHandlerRegistry = configuration.getCellHandlerRegistry();
        try {
            cellHandlerRegistry.getCellHandler(Demo.class, ExcelType.STRING);
            assert false;
        } catch (ExcelException e) {
            assert true;
        }
    }

    @Test
    void testGetCellHandler() {
    }

    @Test
    void getConfiguration() {
    }
}