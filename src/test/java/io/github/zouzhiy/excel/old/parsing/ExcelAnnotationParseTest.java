package io.github.zouzhiy.excel.old.parsing;/*
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

import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.old.write.WriteDemo;
import org.junit.jupiter.api.Test;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
class ExcelAnnotationParseTest {

    @Test
    void test() {
        Configuration configuration = new Configuration();
        ExcelClassConfig excelClassConfig = configuration.getExcelAnnotationParse().findForClass(WriteDemo.class);

        System.out.println(excelClassConfig);
        assert true;
    }

}
