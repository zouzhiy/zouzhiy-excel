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
package io.github.zouzhiy.excel.example.example8.handler;

import io.github.zouzhiy.excel.example.example8.matedata.Item;
import io.github.zouzhiy.excel.handler.list.AbstractListJoinHandler;

import java.util.function.Supplier;
/**
 * @author zouzhiy
 * @since 2022/7/4
 */
public class ItemListJoinHandler extends AbstractListJoinHandler<Item> {

    @Override
    protected String format(Item item) {
        if (item.getUsername() == null && item.getTel() == null) {
            return "";
        }
        return String.format("%s,%s", item.getUsername() == null ? null : item.getUsername(), item.getTel() == null ? null : item.getTel());
    }

    @Override
    protected Item parse(String item) {
        if (item == null) {
            return null;
        } else if (item.length() == 0) {
            return new Item();
        }
        String[] strings = item.split(",", -1);
        if (strings.length == 0) {
            return new Item(null, null);
        }
        return new Item(strings[0].equals("null") ? null : strings[0], ((Supplier<String>) () -> {
            if (strings.length == 2) {
                return strings[1].equals("null") ? null : strings[1];
            }
            return null;
        }).get());
    }
}
