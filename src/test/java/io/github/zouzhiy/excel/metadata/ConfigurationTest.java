package io.github.zouzhiy.excel.metadata;

import org.junit.jupiter.api.Test;


/**
 * @author zouzhiy
 * @since 2022/7/3
 */
class ConfigurationTest {

    @Test
    void testGet() {
        Configuration configuration = new Configuration();

        assert configuration.getCellHandlerRegistry().getConfiguration().equals(configuration);
        assert configuration.getRowTitleWriteRegistry().getConfiguration().equals(configuration);
        assert configuration.getRowHeadWriteRegistry().getConfiguration().equals(configuration);
        assert configuration.getRowFootWriteRegistry().getConfiguration().equals(configuration);
        assert configuration.getRowTitleReadRegistry().getConfiguration().equals(configuration);
        assert configuration.getRowHeadReadRegistry().getConfiguration().equals(configuration);
        assert configuration.getRowFootReadRegistry().getConfiguration().equals(configuration);
        assert configuration.getRowStyleReadRegistry().getConfiguration().equals(configuration);
        assert configuration.getExcelAnnotationParse().getConfiguration().equals(configuration);
    }

}