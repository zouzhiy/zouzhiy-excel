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
package io.github.zouzhiy.excel.callback;

import io.github.zouzhiy.excel.context.SheetContext;

import java.util.List;

/**
 * sheet 写入回调
 *
 * @author zouzhiy
 * @since 2022/7/2
 */
public interface SheetWriteConsumer<T> {

    /**
     * 写入前
     *
     * @param sheetContext 上下文
     * @param dataList
     */
    default void beforeWrite(SheetContext sheetContext, List<T> dataList) {
    }

    /**
     * 写标题前
     *
     * @param sheetContext 上下文
     * @param dataList
     */
    default void beforeWriteTitle(SheetContext sheetContext, List<T> dataList) {
    }

    /**
     * 写标题后
     *
     * @param sheetContext 上下文
     * @param dataList     数据列表
     */
    default void afterWriteTitle(SheetContext sheetContext, List<T> dataList) {
    }

    /**
     * 写表头前
     *
     * @param sheetContext 上下文
     * @param dataList     数据列表
     */
    default void beforeWriteHead(SheetContext sheetContext, List<T> dataList) {
    }

    /**
     * 写表头后
     *
     * @param sheetContext 上下文
     * @param dataList     数据列表
     */
    default void afterWriteHead(SheetContext sheetContext, List<T> dataList) {
    }

    /**
     * 写数据前
     *
     * @param sheetContext 上下文
     * @param dataList     数据列表
     */
    default void beforeWriteData(SheetContext sheetContext, List<T> dataList) {
    }

    /**
     * 写数据后
     *
     * @param sheetContext 上下文
     * @param dataList     数据列表
     */
    default void afterWriteData(SheetContext sheetContext, List<T> dataList) {
    }

    /**
     * 写表尾前
     *
     * @param sheetContext 上下文
     * @param dataList     数据列表
     */
    default void beforeWriteFoot(SheetContext sheetContext, List<T> dataList) {
    }

    /**
     * 写表尾后
     *
     * @param sheetContext 上下文
     * @param dataList     数据列表
     */
    default void afterWriteFoot(SheetContext sheetContext, List<T> dataList) {
    }

    /**
     * 写入后
     *
     * @param sheetContext 上下文
     * @param dataList     数据列表
     */

    default void afterWrite(SheetContext sheetContext, List<T> dataList) {
    }
}
