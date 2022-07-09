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
import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.CellHandler;
import io.github.zouzhiy.excel.ibatis.reflection.MetaObject;
import io.github.zouzhiy.excel.ibatis.reflection.Reflector;
import io.github.zouzhiy.excel.ibatis.reflection.ReflectorFactory;
import io.github.zouzhiy.excel.ibatis.reflection.factory.ObjectFactory;
import io.github.zouzhiy.excel.ibatis.reflection.wrapper.ObjectWrapper;
import io.github.zouzhiy.excel.ibatis.reflection.wrapper.ObjectWrapperFactory;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.result.CellResultSet;
import io.github.zouzhiy.excel.metadata.result.CellStyleResultSet;
import io.github.zouzhiy.excel.read.RowFootRead;
import io.github.zouzhiy.excel.read.RowHeadRead;
import io.github.zouzhiy.excel.read.RowRead;
import io.github.zouzhiy.excel.read.RowTitleRead;
import io.github.zouzhiy.excel.write.RowFootWrite;
import io.github.zouzhiy.excel.write.RowHeadWrite;
import io.github.zouzhiy.excel.write.RowTitleWrite;
import io.github.zouzhiy.excel.write.RowWrite;
import org.apache.poi.ss.usermodel.CellStyle;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
@SuppressWarnings("unchecked")
class ZouzhiyExcelFactoryBuilderTest {

    @Test
    void builder() {

        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder().build();
        assert zouzhiyExcelFactory != null;
    }

    @Test
    void builder1() {
        Configuration configuration = new Configuration();

        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder(configuration).build();
        assert zouzhiyExcelFactory.getConfiguration().equals(configuration);
    }


    @Test
    void registerCellHandler() {
        CellHandler<String> cellHandler = new CellHandler<String>() {
            @Override
            public Class<String> getJavaType() {
                return String.class;
            }

            @Override
            public ExcelType getExcelType() {
                return ExcelType.STRING;
            }

            @Override
            public String read(SheetContext sheetContext, ExcelFieldConfig excelFieldConfig, CellResultSet cellResultSet) {
                return null;
            }

            @Override
            public void write(RowContext rowContext, Integer columnIndex, ExcelFieldConfig excelFieldConfig, String value) {

            }
        };
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder()
                .register(cellHandler)
                .build();

        assert zouzhiyExcelFactory.getConfiguration().getCellHandlerRegistry()
                .getMappingCellHandler((Class<? extends CellHandler<?>>) cellHandler.getClass())
                .equals(cellHandler);

    }

    @Test
    void registerRowWrite() {
        RowWrite rowWrite = (RowHeadWrite) (sheetContext, dataList) -> 0;
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder()
                .register(rowWrite)
                .build();

        assert zouzhiyExcelFactory.getConfiguration().getRowHeadWriteRegistry()
                .getMappingRowWrite((Class<? extends RowHeadWrite>) rowWrite.getClass())
                .equals(rowWrite);
    }

    @Test
    void testRegisterRowTitleWrite() {
        RowWrite rowWrite = (RowTitleWrite) (sheetContext, dataList) -> 0;
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder()
                .register(rowWrite)
                .build();

        assert zouzhiyExcelFactory.getConfiguration().getRowTitleWriteRegistry()
                .getMappingRowWrite((Class<? extends RowTitleWrite>) rowWrite.getClass())
                .equals(rowWrite);
    }

    @Test
    void testRegister1() {
        RowWrite rowWrite = (RowTitleWrite) (sheetContext, dataList) -> 0;
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder()
                .register(rowWrite)
                .build();

        assert zouzhiyExcelFactory.getConfiguration().getRowTitleWriteRegistry()
                .getMappingRowWrite((Class<? extends RowTitleWrite>) rowWrite.getClass())
                .equals(rowWrite);
    }

    @Test
    void testRegisterRowFootWrite() {
        RowWrite rowWrite = (RowFootWrite) (sheetContext, dataList) -> 0;
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder()
                .register(rowWrite)
                .build();

        assert zouzhiyExcelFactory.getConfiguration().getRowFootWriteRegistry()
                .getMappingRowWrite((Class<? extends RowFootWrite>) rowWrite.getClass())
                .equals(rowWrite);
    }


    @Test
    void registerRowRead() {
        RowRead rowRead = (RowHeadRead) sheetContext -> null;
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder()
                .register(rowRead)
                .build();

        assert zouzhiyExcelFactory.getConfiguration().getRowHeadReadRegistry()
                .getMappingRowRead((Class<? extends RowHeadRead>) rowRead.getClass())
                .equals(rowRead);
    }

    @Test
    void testRegisterRowTitleRead() {
        RowRead rowRead = (RowTitleRead) sheetContext -> null;
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder()
                .register(rowRead)
                .build();

        assert zouzhiyExcelFactory.getConfiguration().getRowTitleReadRegistry()
                .getMappingRowRead((Class<? extends RowTitleRead>) rowRead.getClass())
                .equals(rowRead);
    }

    @Test
    void registerRowTitleRead() {
        RowRead rowRead = (RowTitleRead) sheetContext -> null;
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder()
                .register(rowRead)
                .build();

        assert zouzhiyExcelFactory.getConfiguration().getRowTitleReadRegistry()
                .getMappingRowRead((Class<? extends RowTitleRead>) rowRead.getClass())
                .equals(rowRead);
    }

    @Test
    void testRegisterRowFootRead() {
        RowRead rowRead = (RowFootRead) sheetContext -> null;
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder()
                .register(rowRead)
                .build();

        assert zouzhiyExcelFactory.getConfiguration().getRowFootReadRegistry()
                .getMappingRowRead((Class<? extends RowFootRead>) rowRead.getClass())
                .equals(rowRead);
    }

    @Test
    void registerRowStyleRead() {
        RowStyleRead rowStyleRead = new RowStyleRead() {

            @Override
            public CellStyle readTitle(SheetContext sheetContext) {
                return null;
            }

            @Override
            public CellStyleResultSet readHead(SheetContext sheetContext) {
                return null;
            }

            @Override
            public CellStyleResultSet readData(SheetContext sheetContext) {
                return null;
            }
        };
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder()
                .register(rowStyleRead)
                .build();

        assert zouzhiyExcelFactory.getConfiguration().getRowStyleReadRegistry()
                .getMappingRowStyleRead(rowStyleRead.getClass())
                .equals(rowStyleRead);
    }

    @Test
    void setReflectorFactory() {
        ReflectorFactory reflectorFactory = new ReflectorFactory() {
            @Override
            public boolean isClassCacheEnabled() {
                return false;
            }

            @Override
            public void setClassCacheEnabled(boolean classCacheEnabled) {

            }

            @Override
            public Reflector findForClass(Class<?> type) {
                return null;
            }
        };
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder()
                .setReflectorFactory(reflectorFactory)
                .build();

        assert reflectorFactory.equals(zouzhiyExcelFactory.getConfiguration().getReflectorFactory());
    }

    @Test
    void setObjectFactory() {
        ObjectFactory objectFactory = new ObjectFactory() {

            @Override
            public <T> T create(Class<T> type) {
                return null;
            }

            @Override
            public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
                return null;
            }

            @Override
            public <T> boolean isCollection(Class<T> type) {
                return false;
            }
        };
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder()
                .setObjectFactory(objectFactory)
                .build();

        assert objectFactory.equals(zouzhiyExcelFactory.getConfiguration().getObjectFactory());
    }

    @Test
    void setObjectWrapperFactory() {
        ObjectWrapperFactory objectWrapperFactory = new ObjectWrapperFactory() {
            @Override
            public boolean hasWrapperFor(Object object) {
                return false;
            }

            @Override
            public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
                return null;
            }
        };
        ZouzhiyExcelFactory zouzhiyExcelFactory = ZouzhiyExcelFactoryBuilder.builder()
                .setObjectWrapperFactory(objectWrapperFactory)
                .build();

        assert objectWrapperFactory.equals(zouzhiyExcelFactory.getConfiguration().getObjectWrapperFactory());
    }

    @Test
    void setClassCacheEnabled() {
        ZouzhiyExcelFactory zouzhiyExcelFactory1 = ZouzhiyExcelFactoryBuilder.builder()
                .setClassCacheEnabled(false)
                .build();
        assert !zouzhiyExcelFactory1.getConfiguration().getReflectorFactory().isClassCacheEnabled();

        ZouzhiyExcelFactory zouzhiyExcelFactory2 = ZouzhiyExcelFactoryBuilder.builder()
                .setClassCacheEnabled(true)
                .build();
        assert zouzhiyExcelFactory2.getConfiguration().getReflectorFactory().isClassCacheEnabled();
    }

    @Test
    void setConfigCacheEnabled() {

        ZouzhiyExcelFactory zouzhiyExcelFactory1 = ZouzhiyExcelFactoryBuilder.builder()
                .setConfigCacheEnabled(false)
                .build();
        assert !zouzhiyExcelFactory1.getConfiguration().getExcelAnnotationParse().isConfigCacheEnabled();

        ZouzhiyExcelFactory zouzhiyExcelFactory2 = ZouzhiyExcelFactoryBuilder.builder()
                .setConfigCacheEnabled(true)
                .build();
        assert zouzhiyExcelFactory2.getConfiguration().getExcelAnnotationParse().isConfigCacheEnabled();
    }


}