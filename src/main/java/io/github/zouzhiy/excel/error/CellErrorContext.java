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
package io.github.zouzhiy.excel.error;

/**
 * @author xingyu
 * @date 2022/8/15
 */
public class CellErrorContext {


    private Integer colIndex;

    private String errorMessage;

    private CellErrorContext() {
    }

    public static CellErrorContext newInstance(int colIndex) {
        CellErrorContext cellErrorContext = new CellErrorContext();
        cellErrorContext.colIndex = colIndex;

        return cellErrorContext;
    }

    public CellErrorContext error(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    @Override
    public String toString() {
        return String.format("第%s列出现错误：%s", colIndex + 1, errorMessage);
    }
}
