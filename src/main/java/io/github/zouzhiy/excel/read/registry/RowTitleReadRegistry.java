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
package io.github.zouzhiy.excel.read.registry;

import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.read.RowTitleRead;
import io.github.zouzhiy.excel.read.defaults.DefaultRowTitleRead;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class RowTitleReadRegistry {

    public final static Class<DefaultRowTitleRead> DEFAULT_ROW_TITLE_READ_CLASS = DefaultRowTitleRead.class;

    private final Configuration configuration;

    private final Map<Class<? extends RowTitleRead>, RowTitleRead> rowTitleReadMap = new ConcurrentHashMap<>(16);

    public RowTitleReadRegistry(Configuration configuration) {
        this.configuration = configuration;
        register(new DefaultRowTitleRead());
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void register(RowTitleRead rowTitleRead) {
        rowTitleReadMap.put(rowTitleRead.getClass(), rowTitleRead);
    }

    public RowTitleRead getMappingRowRead(Class<? extends RowTitleRead> rowTitleReadClazz) {
        RowTitleRead rowTitleRead = rowTitleReadMap.get(rowTitleReadClazz);
        if (rowTitleRead == null) {
            throw new ExcelException("不存在的：RowTitleRead");
        }
        return rowTitleRead;
    }
}
