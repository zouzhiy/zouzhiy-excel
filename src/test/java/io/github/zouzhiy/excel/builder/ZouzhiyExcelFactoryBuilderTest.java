package io.github.zouzhiy.excel.builder;

import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.write.RowHeadWrite;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
class ZouzhiyExcelFactoryBuilderTest {

    @Test
    void test() {
        ZouzhiyExcelFactoryBuilder.builder()
                .register(new RowHeadWrite() {
                    @Override
                    public int write(SheetContext sheetContext, List<?> dataList) {
                        return 0;
                    }
                });
    }

}