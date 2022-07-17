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

import io.github.zouzhiy.excel.context.WorkbookContext;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;

import java.util.List;

/**
 * workbook读取，
 * 从模板读取，或者创建新的{@link org.apache.poi.ss.usermodel.Workbook}
 * 并写入到输出流中
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
public interface WorkbookWrite extends AutoCloseable {

    /**
     * workbook参数及配置上下文
     *
     * @return WorkbookContext
     */
    WorkbookContext getWorkbookContext();

    /**
     * 对象配置，即对象数据转成excel表格的描述定义
     *
     * @return ExcelClassConfig
     */
    ExcelClassConfig getExcelClassConfig();

    /**
     * 数据写入workbook中
     * 此处写完后 直接将workbook写入到输出流并关闭workbook及相关的输入输出流
     *
     * @param itemList 数据列表
     * @param <T>      对象类型
     */
    <T> void write(List<T> itemList);

    /**
     * 提交
     * <pre>
     *     workbook写入输出流
     *     闭workbook及相关的输入输出流
     * </pre>
     */
    void commit();

}
