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
package io.github.zouzhiy.excel.builder;

import io.github.zouzhiy.excel.cellstyle.RowStyleRead;
import io.github.zouzhiy.excel.handler.CellHandler;
import io.github.zouzhiy.excel.ibatis.reflection.ReflectorFactory;
import io.github.zouzhiy.excel.ibatis.reflection.factory.ObjectFactory;
import io.github.zouzhiy.excel.ibatis.reflection.wrapper.ObjectWrapperFactory;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.read.RowFootRead;
import io.github.zouzhiy.excel.read.RowHeadRead;
import io.github.zouzhiy.excel.read.RowRead;
import io.github.zouzhiy.excel.read.RowTitleRead;
import io.github.zouzhiy.excel.write.RowFootWrite;
import io.github.zouzhiy.excel.write.RowHeadWrite;
import io.github.zouzhiy.excel.write.RowTitleWrite;
import io.github.zouzhiy.excel.write.RowWrite;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class ZouzhiyExcelFactoryBuilder {

    private final Configuration configuration;

    public static ZouzhiyExcelFactoryBuilder builder() {
        return builder(new Configuration());
    }

    public static ZouzhiyExcelFactoryBuilder builder(Configuration configuration) {
        return new ZouzhiyExcelFactoryBuilder(configuration);
    }

    private ZouzhiyExcelFactoryBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public ZouzhiyExcelFactoryBuilder register(CellHandler<?> cellHandler) {
        this.configuration.getCellHandlerRegistry().register(cellHandler);
        return this;
    }

    public ZouzhiyExcelFactoryBuilder register(RowWrite rowWrite) {
        if (rowWrite instanceof RowTitleWrite) {
            return this.register((RowTitleWrite) rowWrite);
        } else if (rowWrite instanceof RowHeadWrite) {
            return this.register((RowHeadWrite) rowWrite);
        } else if (rowWrite instanceof RowFootWrite) {
            return this.register((RowFootWrite) rowWrite);
        }
        return this;
    }

    public ZouzhiyExcelFactoryBuilder register(RowRead rowRead) {
        if (rowRead instanceof RowTitleRead) {
            return this.register((RowTitleRead) rowRead);
        } else if (rowRead instanceof RowHeadRead) {
            return this.register((RowHeadRead) rowRead);
        } else if (rowRead instanceof RowFootRead) {
            return this.register((RowFootRead) rowRead);
        }
        return this;
    }

    public ZouzhiyExcelFactoryBuilder register(RowStyleRead rowStyleRead) {
        this.configuration.getRowStyleReadRegistry().register(rowStyleRead);
        return this;
    }

    public ZouzhiyExcelFactoryBuilder setReflectorFactory(ReflectorFactory reflectorFactory) {
        this.configuration.setReflectorFactory(reflectorFactory);
        return this;
    }

    public ZouzhiyExcelFactoryBuilder setObjectFactory(ObjectFactory objectFactory) {
        this.configuration.setObjectFactory(objectFactory);
        return this;
    }

    public ZouzhiyExcelFactoryBuilder setObjectWrapperFactory(ObjectWrapperFactory objectWrapperFactory) {
        this.configuration.setObjectWrapperFactory(objectWrapperFactory);
        return this;
    }

    public ZouzhiyExcelFactoryBuilder setClassCacheEnabled(boolean classCacheEnabled) {
        this.configuration.getReflectorFactory().setClassCacheEnabled(classCacheEnabled);
        return this;
    }

    public ZouzhiyExcelFactoryBuilder setConfigCacheEnabled(boolean configCacheEnabled) {
        this.configuration.getExcelAnnotationParse().setConfigCacheEnabled(configCacheEnabled);
        return this;
    }

    public ZouzhiyExcelFactory build() {
        return new DefaultZouzhiyExcelFactory(configuration);
    }


    private ZouzhiyExcelFactoryBuilder register(RowTitleWrite rowTitleWrite) {
        this.configuration.getRowTitleWriteRegistry().register(rowTitleWrite);
        return this;
    }

    private ZouzhiyExcelFactoryBuilder register(RowHeadWrite rowHeadWrite) {
        this.configuration.getRowHeadWriteRegistry().register(rowHeadWrite);
        return this;
    }

    private ZouzhiyExcelFactoryBuilder register(RowFootWrite rowFootWrite) {
        this.configuration.getRowFootWriteRegistry().register(rowFootWrite);
        return this;
    }

    private ZouzhiyExcelFactoryBuilder register(RowTitleRead rowTitleRead) {
        this.configuration.getRowTitleReadRegistry().register(rowTitleRead);
        return this;
    }

    private ZouzhiyExcelFactoryBuilder register(RowHeadRead rowHeadRead) {
        this.configuration.getRowHeadReadRegistry().register(rowHeadRead);
        return this;
    }

    private ZouzhiyExcelFactoryBuilder register(RowFootRead rowFootRead) {
        this.configuration.getRowFootReadRegistry().register(rowFootRead);
        return this;
    }
}
