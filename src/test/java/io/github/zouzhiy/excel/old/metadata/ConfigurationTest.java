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
package io.github.zouzhiy.excel.old.metadata;

import io.github.zouzhiy.excel.metadata.Configuration;
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