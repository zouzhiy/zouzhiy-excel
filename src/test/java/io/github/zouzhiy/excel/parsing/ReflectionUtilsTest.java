package io.github.zouzhiy.excel.parsing;

import org.junit.jupiter.api.Test;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
class ReflectionUtilsTest {

    @Test
    void listAllDeclaredField() {
    }


    @Test
    void test1() {
        Class<? super Object> superclass = Object.class.getSuperclass();
        assert superclass == null;
    }

    static class DemoSuper {
        private String nameSuper;
        private String name;
    }

    static class Demo extends DemoSuper {
        private String title;
        private String name;
    }
}
