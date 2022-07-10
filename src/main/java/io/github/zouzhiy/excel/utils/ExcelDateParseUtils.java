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

import java.text.ParsePosition;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class ExcelDateParseUtils {

    private final static Map<String, DateTimeFormatter> DATE_TIME_FORMATTER_MAP = new ConcurrentHashMap<>(16);

    private final static String[] PATTERNS = new String[]{
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM-dd", "yyyy-MM"
            , "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH", "yyyy/MM/dd", "yyyy/MM"
            , "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM.dd HH", "yyyy.MM.dd", "yyyy.MM"
            , "yyyyMMdd HHmmss", "yyyyMMdd HHmm", "yyyyMMdd HH", "yyyyMMdd", "yyyyMM"
            , "yyyy年MM月dd日 HH时mm分ss秒", "yyyy年MM月dd日 HH时mm分", "yyyy年MM月dd日 HH时", "yyyy年MM月dd日", "yyyy年MM月"
            , "yyyy", "HH:mm:ss"

    };

    public static LocalDateTime parseDateTime(String str) {
        return parseDateTimeWithPosition(str.replaceAll("\\s+", " ").trim());
    }

    public static LocalDateTime parseDateTime(String str, String pattern) {
        if (pattern == null || pattern.trim().length() == 0) {
            return parseDateTime(str);
        }
        return parseDateTime(str.replaceAll("\\s+", " ").trim(), pattern, new ParsePosition(0));
    }

    private static LocalDateTime parseDateTimeWithPosition(String str) {
        ParsePosition parsePosition = new ParsePosition(0);
        for (String pattern : PATTERNS) {
            try {
                LocalDateTime localDateTime = parseDateTime(str, pattern, parsePosition);
                if (parsePosition.getIndex() == str.length()) {
                    return localDateTime;
                }
            } catch (ExcelException ignore) {
            }
            parsePosition.setIndex(0);
            parsePosition.setErrorIndex(-1);
        }

        throw new ExcelException("Unable to parse the date: " + str);
    }


    private static LocalDateTime parseDateTime(String str, String pattern, ParsePosition parsePosition) {

        try {
            DateTimeFormatter dateTimeFormatter = DATE_TIME_FORMATTER_MAP.computeIfAbsent(pattern, DateTimeFormatter::ofPattern);
            TemporalAccessor temporalAccessor = dateTimeFormatter.parse(str, parsePosition);

            LocalDateTime localDateTime = queryByDateAndTime(temporalAccessor);
            if (localDateTime == null) {
                localDateTime = queryByYearMonth(temporalAccessor);
            }
            if (localDateTime == null) {
                localDateTime = queryByYear(temporalAccessor);
            }
            if (localDateTime == null) {
                throw new ExcelException("日期转换失败：%s", str);
            }
            return localDateTime;
        } catch (ExcelException excelException) {
            throw excelException;
        } catch (Exception e) {
            throw new ExcelException(e, "日期转换失败：%s", str);
        }
    }

    private static LocalDateTime queryByDateAndTime(TemporalAccessor temporalAccessor) {
        LocalTime time = temporalAccessor.query(TemporalQueries.localTime());
        LocalDate date = temporalAccessor.query(TemporalQueries.localDate());
        if (time == null && date == null) {
            return null;
        } else if (date != null && time == null) {
            return LocalDateTime.of(date, LocalTime.MIN);
        } else if (date == null) {
            return LocalDateTime.of(LocalDate.of(1900, 1, 1), time);
        } else {
            return LocalDateTime.of(date, time);
        }
    }

    private static LocalDateTime queryByYearMonth(TemporalAccessor temporalAccessor) {
        YearMonth yearMonth = temporalAccessor.query(temporal -> {
            if (temporal.isSupported(MONTH_OF_YEAR)) {
                return YearMonth.from(temporal);
            } else {
                return null;
            }
        });
        if (yearMonth == null) {
            return null;
        } else {
            return LocalDateTime.of(yearMonth.atDay(1), LocalTime.MIN);
        }
    }

    private static LocalDateTime queryByYear(TemporalAccessor temporalAccessor) {
        Year year = temporalAccessor.query(temporal -> {
            if (temporal.isSupported(ChronoField.YEAR)) {
                return Year.from(temporal);
            } else {
                return null;
            }
        });
        if (year == null) {
            return null;
        } else {
            return LocalDateTime.of(year.atDay(1), LocalTime.MIN);
        }
    }


}
