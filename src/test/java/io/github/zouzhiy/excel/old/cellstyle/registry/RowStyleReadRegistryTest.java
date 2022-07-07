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
package io.github.zouzhiy.excel.old.cellstyle.registry;

import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactory;
import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactoryBuilder;
import io.github.zouzhiy.excel.cellstyle.RowStyleRead;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.result.CellStyleResultSet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.junit.jupiter.api.Test;

/**
 * @author zouzhiy
 * @since 2022/7/3
 */
class RowStyleReadRegistryTest {
    private final ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().build();

    @Test
    void getConfiguration() {
        Configuration configuration = zouzhiyExcelFactory.getConfiguration();
        assert configuration.equals(configuration.getRowStyleReadRegistry().getConfiguration());
    }

    @Test
    void register() {
    }

    @Test
    void getMappingRowStyleRead() {
        try {
            RowStyleRead rowStyleRead = zouzhiyExcelFactory.getConfiguration().getRowStyleReadRegistry().getMappingRowStyleRead(null);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    void testGetMappingRowStyleRead() {
        try {
            RowStyleRead rowStyleRead = zouzhiyExcelFactory.getConfiguration().getRowStyleReadRegistry().getMappingRowStyleRead(new RowStyleRead() {
                @Override
                public CellStyle readTitle(SheetContext sheetContext) {
                    return null;
                }

                @Override
                public CellStyleResultSet readHead(SheetContext sheetContext) {
                    return null;
                }

                @Override
                public CellStyleResultSet readData(SheetContext sheetContext) {
                    return null;
                }
            }.getClass());
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }
}