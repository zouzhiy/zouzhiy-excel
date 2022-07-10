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
package io.github.zouzhiy.excel.utils;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zouzhiy
 * @since 2022/7/10
 */
public class ExcelNumberUtils {

    private static final Map<String, DecimalFormat> DECIMAL_FORMAT_MAP = new ConcurrentHashMap<>(16);


    public static String format(Number number, String format) {
        if (number == null) {
            return null;
        }
        String strValue;
        if (format.length() > 0) {
            DecimalFormat decimalFormat = DECIMAL_FORMAT_MAP.computeIfAbsent(format, DecimalFormat::new);
            strValue = decimalFormat.format(number);
        } else {
            strValue = number.toString();
        }

        return strValue;
    }


}
