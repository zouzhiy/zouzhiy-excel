package io.github.zouzhiy.excel.type;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
class CellHandlerTest {

    @Test
    void test() {
        List<String> valueList = new ArrayList<>();
        Class<List<String>> clazz = (Class<List<String>>) valueList.getClass();

        System.out.println(clazz);
        assert !List.class.equals(clazz);
    }

    @Test
    void test2() {
        Class<?> arrayListClass = new ArrayList<String>().getClass();
        Class<?>[] interfaces = arrayListClass.getInterfaces();
        int index = 0;
        for (int i = 0; i < interfaces.length; i++) {
            if (interfaces[i].equals(List.class)) {
                index = i;
                break;
            }
        }
        Class<List<String>> clazz = (Class<List<String>>) interfaces[index];
        assert List.class.equals(clazz);
    }

}
