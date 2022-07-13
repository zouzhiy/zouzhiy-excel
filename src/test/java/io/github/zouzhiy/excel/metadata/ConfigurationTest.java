package io.github.zouzhiy.excel.metadata;

import io.github.zouzhiy.excel.ibatis.reflection.MetaClass;
import io.github.zouzhiy.excel.ibatis.reflection.MetaObject;
import io.github.zouzhiy.excel.support.metadata.DemoDefault;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


class ConfigurationTest {

    @Test
    void newMetaObject() {
        Configuration configuration = new Configuration();
        MockedStatic<MetaObject> metaObjectMockedStatic = Mockito.mockStatic(MetaObject.class);
        DemoDefault demoDefault = new DemoDefault();
        configuration.newMetaObject(demoDefault);

        metaObjectMockedStatic.verify(() -> MetaObject.forObject(demoDefault, configuration.getObjectFactory(), configuration.getObjectWrapperFactory(), configuration.getReflectorFactory()), Mockito.times(1));
        metaObjectMockedStatic.close();
    }

    @Test
    void newMetaClazz() {
        Configuration configuration = new Configuration();
        MockedStatic<MetaClass> metaObjectMockedStatic = Mockito.mockStatic(MetaClass.class);
        Class<DemoDefault> demoDefaultClass = DemoDefault.class;
        configuration.newMetaClazz(demoDefaultClass);

        metaObjectMockedStatic.verify(() -> MetaClass.forClass(demoDefaultClass, configuration.getReflectorFactory()), Mockito.times(1));
        metaObjectMockedStatic.close();
    }
}
