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
import io.github.zouzhiy.excel.write.RowHeadWrite;
import io.github.zouzhiy.excel.write.defaults.DefaultRowHeadWrite;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RowHeadWrite 注册管理
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
public class RowHeadWriteRegistry {

    public final static Class<DefaultRowHeadWrite> DEFAULT_ROW_HEAD_WRITE_CLASS = DefaultRowHeadWrite.class;

    private final Configuration configuration;

    private final Map<Class<? extends RowHeadWrite>, RowHeadWrite> rowHeadWriteMap = new ConcurrentHashMap<>(16);

    public RowHeadWriteRegistry(Configuration configuration) {
        this.configuration = configuration;
        register(new DefaultRowHeadWrite());
    }

    /**
     * 全家配置信息
     *
     * @return Configuration
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * 注册
     *
     * @param rowHeadWrite 实例
     */
    public void register(RowHeadWrite rowHeadWrite) {
        rowHeadWriteMap.put(rowHeadWrite.getClass(), rowHeadWrite);
    }

    /**
     * 根据 class 查找已注册的实例对象
     *
     * @param rowHeadWriteClazz class
     * @return 返回已注册的实例对象
     */
    public RowHeadWrite getMappingRowWrite(Class<? extends RowHeadWrite> rowHeadWriteClazz) {
        RowHeadWrite rowHeadWrite = rowHeadWriteMap.get(rowHeadWriteClazz);
        if (rowHeadWrite == null) {
            throw new ExcelException("不存在的：RowHeadWrite");
        }
        return rowHeadWrite;
    }

}
