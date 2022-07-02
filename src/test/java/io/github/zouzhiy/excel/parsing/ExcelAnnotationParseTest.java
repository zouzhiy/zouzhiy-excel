package io.github.zouzhiy.excel.parsing;

import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.ExcelClassConfig;
import io.github.zouzhiy.excel.write.WriteDemo;
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
