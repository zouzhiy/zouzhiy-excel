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
package io.github.zouzhiy.excel.read;

import io.github.zouzhiy.excel.context.SheetContext;
import io.github.zouzhiy.excel.metadata.result.RowResultSet;

/**
 * 数据行读取
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
public interface RowDataRead extends RowRead {

    /**
     * @param sheetContext sheet上下文
     * @param clazz        目标对象
     * @param rowIndex     读取行起始位置
     * @param <T>          对象类型
     * @return 单行数据对象
     */
    <T> RowResultSet<T> read(SheetContext sheetContext, Class<T> clazz, int rowIndex);

}
