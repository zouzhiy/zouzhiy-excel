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
package io.github.zouzhiy.excel.cellstyle.registry;

import io.github.zouzhiy.excel.cellstyle.RowStyleRead;
import io.github.zouzhiy.excel.cellstyle.defaults.DefaultRowStyleRead;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class RowStyleReadRegistry {

    public final static Class<DefaultRowStyleRead> DEFAULT_ROW_STYLE_READ_CLASS = DefaultRowStyleRead.class;

    private final Configuration configuration;

    private final Map<Class<? extends RowStyleRead>, RowStyleRead> rowStyleReadMap = new ConcurrentHashMap<>(16);

    public RowStyleReadRegistry(Configuration configuration) {
        this.configuration = configuration;
        register(new DefaultRowStyleRead());
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void register(RowStyleRead rowStyleRead) {
        rowStyleReadMap.put(rowStyleRead.getClass(), rowStyleRead);
    }

    public RowStyleRead getMappingRowStyleRead(Class<? extends RowStyleRead> rowStyleReadClazz) {
        RowStyleRead rowStyleRead = rowStyleReadMap.get(rowStyleReadClazz);
        if (rowStyleRead == null) {
            throw new ExcelException("不存在的：CellStyleRead");
        }
        return rowStyleRead;
    }
}
