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
package io.github.zouzhiy.excel.old.example.example3;

import io.github.zouzhiy.excel.old.example.example3.matedata.Demo;
import io.github.zouzhiy.excel.old.example.example3.matedata.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class ExcelTest {

    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void write3() {
        Excel excel = new Excel();
        excel.write(Collections.emptyList());

        List<Demo> demoList = new ArrayList<>();
        int maxIndex = random.nextInt(500);
        for (int i = 0; i < maxIndex; i++) {
            demoList.add(Demo.builder().value(this.getValue())
                    .value1List(this.getStringList())
                    .value2List(this.getStringList())
                    .value3List(this.getStringList())
                    .value4List(this.getStringList())
                    .build());
        }
        File outputFile = excel.write(demoList);

        List<Demo> demo1List = excel.read(outputFile);

        for (int i = 0; i < demo1List.size(); i++) {
            Assertions.assertEquals(demo1List.get(i), demoList.get(i));
        }
    }

    @Test
    void write4() {
        Excel excel = new Excel();

        List<Demo> demoList = new ArrayList<>();
        demoList.add(Demo.builder().value(this.getValue())
                .value1List(Collections.emptyList())
                .value2List(Collections.singletonList(this.getValue()))
                .value3List(Collections.singletonList(null))
                .value4List(Collections.emptyList())
                .build());
        File outputFile = excel.write(demoList);

        List<Demo> demo1List = excel.read(outputFile);

        for (int i = 0; i < demo1List.size(); i++) {
            Assertions.assertEquals(demo1List.get(i), demoList.get(i));
        }
    }

    private Item getValue() {
        return new Item(random.nextBoolean() ? "title" + random.nextInt(440) : "", random.nextBoolean() ? "name" + random.nextInt(440) : "");
    }

    private List<Item> getStringList() {
        int maxIndex = random.nextInt(10);
        List<Item> valueList = new ArrayList<>();
        for (int i = 0; i < maxIndex; i++) {
            valueList.add(this.getValue());
        }
        return valueList;
    }
}
