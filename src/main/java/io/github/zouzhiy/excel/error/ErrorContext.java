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

import io.github.zouzhiy.excel.exceptions.ExcelException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author xingyu
 * @date 2022/8/15
 */
@Getter
public class ErrorContext {

    private final static ThreadLocal<ErrorContext> ERROR_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    private final static LinkedBlockingDeque<ErrorContext> LIMITED_QUEUE = new LinkedBlockingDeque<>(10);

    private boolean hasError;

    private String threadName;

    private String workbookName;

    private String errorMessage;

    private List<SheetErrorContext> errorList;

    private ErrorContext() {
    }

    public static ErrorContext instance() {
        return store();
    }

    public static ErrorContext getLastErrorContext() {
        if (LIMITED_QUEUE.isEmpty()) {
            throw new ExcelException("不存在错误信息");
        }
        String threadName = Thread.currentThread().getName();

        Iterator<ErrorContext> errorContextIterator = LIMITED_QUEUE.descendingIterator();
        while (errorContextIterator.hasNext()) {
            ErrorContext errorContext = errorContextIterator.next();
            if (threadName.equals(errorContext.getThreadName())) {
                return errorContext;
            }
        }

        return LIMITED_QUEUE.peekLast();
    }

    public static ErrorContext store() {
        ErrorContext errorContext = ERROR_CONTEXT_THREAD_LOCAL.get();
        if (errorContext == null) {
            errorContext = ErrorContext.newInstance();
            ERROR_CONTEXT_THREAD_LOCAL.set(errorContext);
        }

        return errorContext;
    }

    public static void remove() {
        ERROR_CONTEXT_THREAD_LOCAL.remove();
    }

    private static void add(ErrorContext errorContext) {
        int maxSize = 10;
        if (LIMITED_QUEUE.size() == maxSize) {
            LIMITED_QUEUE.remove();
        }
        LIMITED_QUEUE.add(errorContext);
    }


    static ErrorContext newInstance() {
        ErrorContext errorContext = new ErrorContext();
        errorContext.hasError = false;
        errorContext.threadName = Thread.currentThread().getName();
        errorContext.errorList = new ArrayList<>();
        return errorContext;
    }


    public ErrorContext workbookName(String workbookName) {
        this.workbookName = workbookName;
        return this;
    }

    public ErrorContext errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }


    public ErrorContext error(String sheetName, int rowIndex, int colIndex, String errorMessage) {
        this.hasError = true;
        SheetErrorContext sheetErrorContext = errorList.stream().filter(item -> item.getSheetName().equals(sheetName)).findAny().orElse(null);
        if (sheetErrorContext == null) {
            sheetErrorContext = SheetErrorContext.newInstance(sheetName);
            errorList.add(sheetErrorContext);
        }
        sheetErrorContext.error(rowIndex, colIndex, errorMessage);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (workbookName != null) {
            stringBuilder.append("工作簿：").append(workbookName);
        }
        stringBuilder.append("错误信息详情：");
        if (errorMessage != null && errorMessage.trim().length() > 0) {
            stringBuilder.append(errorMessage);
        }
        for (SheetErrorContext sheetErrorContext : errorList) {
            stringBuilder.append(sheetErrorContext).append("。");
        }
        return stringBuilder.toString();
    }
}
