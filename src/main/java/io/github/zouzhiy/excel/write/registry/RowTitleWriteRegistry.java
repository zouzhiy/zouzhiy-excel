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
package io.github.zouzhiy.excel.write.registry;

import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.write.RowTitleWrite;
import io.github.zouzhiy.excel.write.defaults.DefaultRowTitleWrite;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class RowTitleWriteRegistry {

    public final static Class<DefaultRowTitleWrite> DEFAULT_ROW_TITLE_WRITE_CLASS = DefaultRowTitleWrite.class;

    private final Configuration configuration;

    private final Map<Class<? extends RowTitleWrite>, RowTitleWrite> rowTitleWriteMap = new ConcurrentHashMap<>(16);

    public Configuration getConfiguration() {
        return configuration;
    }

    public RowTitleWriteRegistry(Configuration configuration) {
        this.configuration = configuration;
        register(new DefaultRowTitleWrite());
    }

    public void register(RowTitleWrite rowTitleWrite) {
        rowTitleWriteMap.put(rowTitleWrite.getClass(), rowTitleWrite);
    }

    public RowTitleWrite getMappingRowTitleWrite(Class<? extends RowTitleWrite> rowTitleWriteClazz) {
        RowTitleWrite rowTitleWrite = rowTitleWriteMap.get(rowTitleWriteClazz);
        if (rowTitleWrite == null) {
            throw new ExcelException("不存在的：RowTitleWrite");
        }
        return rowTitleWrite;
    }
}
