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
import io.github.zouzhiy.excel.read.RowHeadRead;
import io.github.zouzhiy.excel.read.defaults.DefaultRowHeadRead;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class RowHeadReadRegistry {

    public final static Class<DefaultRowHeadRead> DEFAULT_ROW_HEAD_READ_CLASS = DefaultRowHeadRead.class;

    private final Configuration configuration;

    private final Map<Class<? extends RowHeadRead>, RowHeadRead> rowHeadReadMap = new ConcurrentHashMap<>(16);

    public RowHeadReadRegistry(Configuration configuration) {
        this.configuration = configuration;
        register(new DefaultRowHeadRead());
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void register(RowHeadRead rowHeadRead) {
        rowHeadReadMap.put(rowHeadRead.getClass(), rowHeadRead);
    }

    public RowHeadRead getMappingRowRead(Class<? extends RowHeadRead> rowHeadReadClazz) {
        RowHeadRead rowHeadRead = rowHeadReadMap.get(rowHeadReadClazz);
        if (rowHeadRead == null) {
            throw new ExcelException("不存在的：RowHeadRead");
        }
        return rowHeadRead;
    }

}
