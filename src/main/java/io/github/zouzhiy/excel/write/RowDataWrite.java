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
package io.github.zouzhiy.excel.write;

import io.github.zouzhiy.excel.context.SheetContext;

/**
 * 数据行写入
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
public interface RowDataWrite extends RowWrite {


    /**
     * 数据行写入
     *
     * @param sheetContext sheet上下文信息
     * @param item         需要写入的数据
     * @param rowIndex     数据写入起始行。可能会占用多行
     * @param <T>          数据泛型
     * @return 影响的行数，即 item 占用了多少行。
     */
    <T> int write(SheetContext sheetContext, T item, int rowIndex);
}
