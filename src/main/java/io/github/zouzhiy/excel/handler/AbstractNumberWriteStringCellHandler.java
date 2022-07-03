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
package io.github.zouzhiy.excel.handler;

import io.github.zouzhiy.excel.context.RowContext;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public abstract class AbstractNumberWriteStringCellHandler<T extends Number> extends AbstractWriteStringCellHandler<T> {

    private final Map<String, DecimalFormat> decimalFormatMap = new ConcurrentHashMap<>(16);

    @Override
    protected final String format(RowContext rowContext, ExcelFieldConfig excelFieldConfig, T value) {
        String javaFormat = this.getJavaFormat(excelFieldConfig);
        if (javaFormat.length() > 0) {
            DecimalFormat decimalFormat = decimalFormatMap.computeIfAbsent(javaFormat, DecimalFormat::new);
            return decimalFormat.format(value);
        } else {
            return value.toString();
        }
    }
}
