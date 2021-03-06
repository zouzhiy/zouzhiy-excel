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
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;

import java.util.List;

/**
 * sheet读取
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
public interface SheetWrite {

    /**
     * sheet配置上下文
     *
     * @return SheetContext
     */
    SheetContext getSheetContext();

    /**
     * 对象配置，即对象数据转成excel表格的描述定义
     *
     * @return ExcelClassConfig
     */
    ExcelClassConfig getExcelClassConfig();

    /**
     * 数据写入shhet中
     *
     * @param itemList 数据列表
     * @param <T>      对象类型
     */
    <T> void write(List<T> itemList);

}
