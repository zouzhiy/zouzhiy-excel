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
package io.github.zouzhiy.excel.example.example8.matedata;


import io.github.zouzhiy.excel.annotation.ExcelField;
import io.github.zouzhiy.excel.example.example8.handler.ItemListJoinHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
/**
 * @author zouzhiy
 * @since 2022/7/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Demo {

    @ExcelField(colspan = 2)
    private Item value = new Item();

    @ExcelField(colspan = 2, cellHandler = ItemListJoinHandler.class)
    private List<Item> value1List = new ArrayList<>();

    @ExcelField(colspan = 2, cellHandler = ItemListJoinHandler.class)
    private List<Item> value2List = new ArrayList<>();

    @ExcelField(colspan = 2, cellHandler = ItemListJoinHandler.class)
    private List<Item> value3List = new ArrayList<>();

}
