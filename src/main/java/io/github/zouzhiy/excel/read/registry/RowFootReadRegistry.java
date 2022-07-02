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
import io.github.zouzhiy.excel.read.RowFootRead;
import io.github.zouzhiy.excel.read.defaults.DefaultRowFootRead;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class RowFootReadRegistry {

    public final static Class<DefaultRowFootRead> DEFAULT_ROW_FOOT_READ_CLASS = DefaultRowFootRead.class;

    private final Configuration configuration;

    private final Map<Class<? extends RowFootRead>, RowFootRead> rowFootReadMap = new ConcurrentHashMap<>(16);

    public Configuration getConfiguration() {
        return configuration;
    }

    public RowFootReadRegistry(Configuration configuration) {
        this.configuration = configuration;
        register(new DefaultRowFootRead());
    }

    public void register(RowFootRead rowFootRead) {
        rowFootReadMap.put(rowFootRead.getClass(), rowFootRead);
    }

    public RowFootRead getMappingRowFootRead(Class<? extends RowFootRead> rowFootReadClazz) {
        RowFootRead rowFootRead = rowFootReadMap.get(rowFootReadClazz);
        if (rowFootRead == null) {
            throw new ExcelException("不存在的：RowFootRead");
        }
        return rowFootRead;
    }

}
