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
package io.github.zouzhiy.excel.autoconfigure;

import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactory;
import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactoryBuilder;
import io.github.zouzhiy.excel.cellstyle.RowStyleRead;
import io.github.zouzhiy.excel.handler.CellHandler;
import io.github.zouzhiy.excel.ibatis.reflection.ReflectorFactory;
import io.github.zouzhiy.excel.ibatis.reflection.factory.ObjectFactory;
import io.github.zouzhiy.excel.ibatis.reflection.wrapper.ObjectWrapperFactory;
import io.github.zouzhiy.excel.read.RowRead;
import io.github.zouzhiy.excel.write.RowWrite;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zouzhiy
 * @since 2022/7/5
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ZouzhiyExcelProperties.class)
public class ZouzhiyExcelAutoConfiguration {

    private final ZouzhiyExcelProperties zouzhiyExcelProperties;

    private final List<CellHandler<?>> cellHandlerList;
    private final List<RowWrite> rowWriteList;
    private final List<RowRead> rowReadList;
    private final List<RowStyleRead> rowStyleReadList;
    private final ReflectorFactory reflectorFactory;
    private final ObjectFactory objectFactory;
    private final ObjectWrapperFactory objectWrapperFactory;

    public ZouzhiyExcelAutoConfiguration(
            ZouzhiyExcelProperties zouzhiyExcelProperties
            , ObjectProvider<CellHandler<?>> cellHandlerObjectProvider
            , ObjectProvider<RowWrite> rowWriteObjectProvider
            , ObjectProvider<RowRead> rowReadObjectProvider
            , ObjectProvider<RowStyleRead> rowStyleReadObjectProvider
            , ObjectProvider<ReflectorFactory> reflectorFactoryObjectProvider
            , ObjectProvider<ObjectFactory> objectFactoryObjectProvider
            , ObjectProvider<ObjectWrapperFactory> objectWrapperFactoryObjectProvider) {
        this.zouzhiyExcelProperties = zouzhiyExcelProperties;
        this.cellHandlerList = cellHandlerObjectProvider.orderedStream().collect(Collectors.toList());
        this.rowWriteList = rowWriteObjectProvider.orderedStream().collect(Collectors.toList());
        this.rowReadList = rowReadObjectProvider.orderedStream().collect(Collectors.toList());
        this.rowStyleReadList = rowStyleReadObjectProvider.orderedStream().collect(Collectors.toList());
        this.reflectorFactory = reflectorFactoryObjectProvider.getIfAvailable();
        this.objectFactory = objectFactoryObjectProvider.getIfAvailable();
        this.objectWrapperFactory = objectWrapperFactoryObjectProvider.getIfAvailable();
    }

    @Bean
    @ConditionalOnMissingBean
    public ZouzhiyExcelFactory zouzhiyExcelFactory() {
        ZouzhiyExcelFactoryBuilder zouzhiyExcelFactoryBuilder = ZouzhiyExcelFactoryBuilder.builder();

        cellHandlerList.forEach(zouzhiyExcelFactoryBuilder::register);
        rowWriteList.forEach(zouzhiyExcelFactoryBuilder::register);
        rowReadList.forEach(zouzhiyExcelFactoryBuilder::register);
        rowStyleReadList.forEach(zouzhiyExcelFactoryBuilder::register);
        if (reflectorFactory != null) {
            zouzhiyExcelFactoryBuilder.setReflectorFactory(reflectorFactory);
        }

        if (objectFactory != null) {
            zouzhiyExcelFactoryBuilder.setObjectFactory(objectFactory);
        }

        if (objectWrapperFactory != null) {
            zouzhiyExcelFactoryBuilder.setObjectWrapperFactory(objectWrapperFactory);
        }

        zouzhiyExcelFactoryBuilder.setClassCacheEnabled(zouzhiyExcelProperties.isClassCacheEnabled());
        zouzhiyExcelFactoryBuilder.setConfigCacheEnabled(zouzhiyExcelProperties.isConfigCacheEnabled());

        return zouzhiyExcelFactoryBuilder.build();
    }
}
