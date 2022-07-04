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
