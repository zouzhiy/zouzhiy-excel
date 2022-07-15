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
package io.github.zouzhiy.excel.metadata;

import io.github.zouzhiy.excel.cellstyle.registry.RowStyleReadRegistry;
import io.github.zouzhiy.excel.handler.CellHandlerRegistry;
import io.github.zouzhiy.excel.ibatis.reflection.DefaultReflectorFactory;
import io.github.zouzhiy.excel.ibatis.reflection.MetaClass;
import io.github.zouzhiy.excel.ibatis.reflection.MetaObject;
import io.github.zouzhiy.excel.ibatis.reflection.ReflectorFactory;
import io.github.zouzhiy.excel.ibatis.reflection.factory.DefaultObjectFactory;
import io.github.zouzhiy.excel.ibatis.reflection.factory.ObjectFactory;
import io.github.zouzhiy.excel.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import io.github.zouzhiy.excel.ibatis.reflection.wrapper.ObjectWrapperFactory;
import io.github.zouzhiy.excel.parsing.ExcelAnnotationParse;
import io.github.zouzhiy.excel.read.registry.RowFootReadRegistry;
import io.github.zouzhiy.excel.read.registry.RowHeadReadRegistry;
import io.github.zouzhiy.excel.read.registry.RowTitleReadRegistry;
import io.github.zouzhiy.excel.write.registry.RowFootWriteRegistry;
import io.github.zouzhiy.excel.write.registry.RowHeadWriteRegistry;
import io.github.zouzhiy.excel.write.registry.RowTitleWriteRegistry;
import lombok.Getter;
import lombok.Setter;

/**
 * 全局配置信息
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
public class Configuration {
    /**
     * {@link io.github.zouzhiy.excel.handler.CellHandler} 注册查询管理器
     */
    @Getter
    private final CellHandlerRegistry cellHandlerRegistry = new CellHandlerRegistry(this);
    /**
     * {@link io.github.zouzhiy.excel.write.RowTitleWrite} 注册查询管理器
     */
    @Getter
    private final RowTitleWriteRegistry rowTitleWriteRegistry = new RowTitleWriteRegistry(this);
    /**
     * {@link io.github.zouzhiy.excel.write.RowHeadWrite} 注册查询管理器
     */
    @Getter
    private final RowHeadWriteRegistry rowHeadWriteRegistry = new RowHeadWriteRegistry(this);
    /**
     * {@link io.github.zouzhiy.excel.write.RowFootWrite} 注册查询管理器
     */
    @Getter
    private final RowFootWriteRegistry rowFootWriteRegistry = new RowFootWriteRegistry(this);
    /**
     * {@link io.github.zouzhiy.excel.read.RowTitleRead} 注册查询管理器
     */
    @Getter
    private final RowTitleReadRegistry rowTitleReadRegistry = new RowTitleReadRegistry(this);
    /**
     * {@link io.github.zouzhiy.excel.read.RowHeadRead} 注册查询管理器
     */
    @Getter
    private final RowHeadReadRegistry rowHeadReadRegistry = new RowHeadReadRegistry(this);

    /**
     * {@link io.github.zouzhiy.excel.read.RowFootRead} 注册查询管理器
     */
    @Getter
    private final RowFootReadRegistry rowFootReadRegistry = new RowFootReadRegistry(this);
    /**
     * {@link io.github.zouzhiy.excel.cellstyle.RowStyleRead} 注册查询管理器
     */
    @Getter
    private final RowStyleReadRegistry rowStyleReadRegistry = new RowStyleReadRegistry(this);
    /**
     * 注解配置解析器
     */
    @Getter
    private final ExcelAnnotationParse excelAnnotationParse = new ExcelAnnotationParse(this);
    /**
     * {@link io.github.zouzhiy.excel.ibatis.reflection.Reflector} 创建工厂，提供缓存的能力
     */
    @Getter
    @Setter
    private ReflectorFactory reflectorFactory = new DefaultReflectorFactory();

    @Getter
    @Setter
    private ObjectFactory objectFactory = new DefaultObjectFactory();

    @Getter
    @Setter
    private ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

    public MetaObject newMetaObject(Object object) {
        return MetaObject.forObject(object, objectFactory, objectWrapperFactory, reflectorFactory);
    }

    public MetaClass newMetaClazz(Class<?> clazz) {
        return MetaClass.forClass(clazz, reflectorFactory);
    }

}
