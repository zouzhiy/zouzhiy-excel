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

import java.util.List;

/**
 * 表尾写入
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
public interface RowFootWrite extends RowWrite {

    /**
     * 表尾写入
     *
     * @param sheetContext sheet上下文信息
     * @param dataList     数据列表
     * @return 影响的行数，即表尾占用多少行
     */
    int write(SheetContext sheetContext, List<?> dataList);

}
