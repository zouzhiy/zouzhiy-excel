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

import io.github.zouzhiy.excel.exceptions.ExcelException;
import org.apache.poi.util.LocaleUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class ExcelDateUtil {

    private final static Map<String, DateTimeFormatter> DATE_TIME_FORMATTER_MAP = new ConcurrentHashMap<>(16);

    private static final DateTimeFormatter DATE_TIME_FORMATS = new DateTimeFormatterBuilder()
//            .appendPattern("[dd MMM[ yyyy]][[ ]h:m[:s][.SSS] a][[ ]H:m[:s][.SSS]]")
            .appendPattern("[[yyyy ]dd-MMM[-yyyy]][[ ]h:m[:s][.SSS] a][[ ]H:m[:s][.SSS]]")
//            .appendPattern("[M/dd[/yyyy]][[ ]h:m[:s][.SSS] a][[ ]H:m[:s][.SSS]]")
//            .appendPattern("[[yyyy/]M/dd][[ ]h:m[:s][.SSS] a][[ ]H:m[:s][.SSS]]")
            .appendPattern("yyyy-MM-dd HH:mm:ss")
            .appendPattern("yyyy-MM-dd")
            .parseDefaulting(ChronoField.YEAR_OF_ERA, LocaleUtil.getLocaleCalendar().get(Calendar.YEAR))
            .toFormatter(LocaleUtil.getUserLocale());



    public static LocalDateTime parseDateTime(String str, String pattern) {
        if (pattern == null || pattern.length() == 0) {
            return parseDateTime(str);
        }
        DateTimeFormatter dateTimeFormatter = DATE_TIME_FORMATTER_MAP.computeIfAbsent(pattern, DateTimeFormatter::ofPattern);
        TemporalAccessor temporalAccessor = dateTimeFormatter.parse(str);
        LocalTime time = temporalAccessor.query(TemporalQueries.localTime());
        LocalDate date = temporalAccessor.query(TemporalQueries.localDate());
        if (time == null && date == null) {
            throw new ExcelException("%s转换为日期错误");
        } else if (date != null && time == null) {
            return LocalDateTime.of(date, LocalTime.MIN);
        } else if (date == null) {
            return LocalDateTime.of(LocalDate.of(1900, 1, 1), time);
        } else {
            return LocalDateTime.of(date, time);
        }
    }

    private static LocalDateTime parseDateTime(String str) {
        TemporalAccessor tmp = DATE_TIME_FORMATS.parse(str.replaceAll("\\s+", " "));
        LocalTime time = tmp.query(TemporalQueries.localTime());
        LocalDate date = tmp.query(TemporalQueries.localDate());
        if (time == null && date == null) {
            throw new ExcelException("%s转换为日期错误");
        } else if (date != null && time == null) {
            return LocalDateTime.of(date, LocalTime.MIN);
        } else if (date == null) {
            return LocalDateTime.of(LocalDate.of(1900, 1, 1), time);
        } else {
            return LocalDateTime.of(date, time);
        }
    }

}
