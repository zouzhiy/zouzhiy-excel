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

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zouzhiy
 * @since 2022/7/10
 */
public class ExcelDateFormatUtils {

    private final static Map<String, SimpleDateFormat> STRING_SIMPLE_DATE_FORMAT_MAP = new ConcurrentHashMap<>(16);

    private final static Map<String, DateTimeFormatter> DATE_TIME_FORMATTER_MAP = new ConcurrentHashMap<>(16);

    private final static Object LOCK = new Object();

    public static String format(Calendar calendar, String format) {
        if (calendar == null) {
            return null;
        }
        return format(calendar.getTime(), format);
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return null;
        }
        String strValue;
        if (format.length() > 0) {
            SimpleDateFormat simpleDateFormat = STRING_SIMPLE_DATE_FORMAT_MAP.computeIfAbsent(format, SimpleDateFormat::new);
            synchronized (LOCK) {
                strValue = simpleDateFormat.format(date);
            }
        } else {
            strValue = date.toString();
        }

        return strValue;
    }

    public static String format(TemporalAccessor temporalAccessor, String format) {
        if (temporalAccessor == null) {
            return null;
        }
        String strValue;
        if (format.length() > 0) {
            DateTimeFormatter dateTimeFormatter = DATE_TIME_FORMATTER_MAP.computeIfAbsent(format, DateTimeFormatter::ofPattern);
            strValue = dateTimeFormatter.format(temporalAccessor);
        } else {
            return temporalAccessor.toString();
        }

        return strValue;
    }

}
