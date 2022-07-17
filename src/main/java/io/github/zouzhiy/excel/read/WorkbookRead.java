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

import io.github.zouzhiy.excel.context.WorkbookContext;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;

import java.util.List;

/**
 * workbook读取
 * 从输入流读取到{@link org.apache.poi.ss.usermodel.Workbook}
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
public interface WorkbookRead extends AutoCloseable {
    /**
     * workbook读取参数及配置上下文
     *
     * @return WorkbookContext
     */
    WorkbookContext getWorkbookContext();

    /**
     * 对象配置，即对象数据与excel表格对应关系的描述
     *
     * @return ExcelClassConfig
     */
    ExcelClassConfig getExcelClassConfig();

    /**
     * 读取数据
     *
     * @param clazz 目标对象class
     * @param <T>   对象泛型
     * @return 读取的的数据列表
     */
    <T> List<T> read(Class<T> clazz);

}
