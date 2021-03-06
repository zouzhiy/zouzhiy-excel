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
package io.github.zouzhiy.excel.metadata.result;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
@Getter
@ToString
@EqualsAndHashCode
public class RowResultSet<T> {

    /**
     * 行数据
     */
    private final T data;
    /**
     * 行号
     */
    private final int rowIndex;
    /**
     * 纵向所跨的行数
     */
    private final int rowspan;

    private RowResultSet(T data, int rowIndex, int rowspan) {
        this.data = data;
        this.rowIndex = rowIndex;
        this.rowspan = rowspan;
    }

    public static <T> RowResultSet<T> newInstance(T data, int rowIndex, int rowspan) {
        return new RowResultSet<>(data, rowIndex, rowspan);
    }

}
