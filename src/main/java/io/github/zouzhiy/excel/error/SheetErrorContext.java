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
public class SheetErrorContext {

    private String sheetName;

    private List<RowErrorContext> errorList;

    private SheetErrorContext() {
    }

    public static SheetErrorContext newInstance(String sheetName) {
        SheetErrorContext sheetErrorContext = new SheetErrorContext();
        sheetErrorContext.sheetName = sheetName;
        sheetErrorContext.errorList = new ArrayList<>();
        return sheetErrorContext;
    }


    public SheetErrorContext error(int rowIndex, int colIndex, String errorMessage) {
        RowErrorContext rowErrorContext = errorList.stream().filter(item -> item.getRowIndex() == rowIndex).findAny().orElse(null);
        if (rowErrorContext == null) {
            rowErrorContext = RowErrorContext.newInstance(rowIndex);
            this.errorList.add(rowErrorContext);
        }
        rowErrorContext.error(colIndex, errorMessage);
        return this;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("工作表：").append(sheetName).append("错误信息详情:");
        for (RowErrorContext cellErrorContext : errorList) {
            stringBuilder.append(cellErrorContext).append("；");
        }
        return stringBuilder.toString();
    }
}
