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

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingyu
 * @date 2022/8/15
 */
@Getter
public class RowErrorContext {

    private int rowIndex;

    private List<CellErrorContext> errorList;

    private RowErrorContext() {
    }

    public static RowErrorContext newInstance(int rowIndex) {
        RowErrorContext rowErrorContext = new RowErrorContext();
        rowErrorContext.rowIndex = rowIndex;
        rowErrorContext.errorList = new ArrayList<>();
        return rowErrorContext;
    }

    public RowErrorContext error(int colIndex, String errorMessage) {
        errorList.add(CellErrorContext.newInstance(colIndex).error(errorMessage));
        return this;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("第").append(rowIndex + 1).append("行报错。");
        for (CellErrorContext cellErrorContext : errorList) {
            stringBuilder.append(cellErrorContext).append("、");
        }
        return stringBuilder.toString();
    }

}
