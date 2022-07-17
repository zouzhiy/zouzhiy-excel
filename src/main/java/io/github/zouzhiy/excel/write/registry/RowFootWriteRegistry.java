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
import io.github.zouzhiy.excel.write.RowFootWrite;
import io.github.zouzhiy.excel.write.defaults.DefaultRowFootWrite;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class RowFootWriteRegistry {

    public final static Class<DefaultRowFootWrite> DEFAULT_ROW_FOOT_WRITE_CLASS = DefaultRowFootWrite.class;

    private final Configuration configuration;

    private final Map<Class<? extends RowFootWrite>, RowFootWrite> rowFootWriteMap = new ConcurrentHashMap<>(16);

    public RowFootWriteRegistry(Configuration configuration) {
        this.configuration = configuration;
        register(new DefaultRowFootWrite());
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
     * @param rowFootWrite 实例
     */
    public void register(RowFootWrite rowFootWrite) {
        rowFootWriteMap.put(rowFootWrite.getClass(), rowFootWrite);
    }

    /**
     * 根据 class 查找已注册的实例对象
     *
     * @param rowFootWriteClazz class
     * @return 返回已注册的实例对象
     */
    public RowFootWrite getMappingRowWrite(Class<? extends RowFootWrite> rowFootWriteClazz) {
        RowFootWrite rowFootWrite = rowFootWriteMap.get(rowFootWriteClazz);
        if (rowFootWrite == null) {
            throw new ExcelException("不存在的：RowFootWrite");
        }
        return rowFootWrite;
    }
}
