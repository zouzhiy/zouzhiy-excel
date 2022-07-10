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
package io.github.zouzhiy.excel.old.utils;

import io.github.zouzhiy.excel.utils.ExcelDateParseUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
class ExcelDateParseUtilsTest {

    @Test
    void parse1() {
        String pattern = "yyyy-MM-dd";
        String time = "2022-02-05";
        LocalDateTime localDateTime = ExcelDateParseUtils.parseDateTime(time, pattern);
        assert localDateTime.format(DateTimeFormatter.ofPattern(pattern)).equals(time);
        System.out.println(localDateTime);
        assert true;
    }

    @Test
    void parse2() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        String time = "2022-02-05 22:33:11";
        LocalDateTime localDateTime = ExcelDateParseUtils.parseDateTime(time, pattern);
        assert localDateTime.format(DateTimeFormatter.ofPattern(pattern)).equals(time);
        System.out.println(localDateTime);
        assert true;
    }

    @Test
    void parse3() {
        String pattern = "HH:mm:ss";
        String time = "22:33:11";
        LocalDateTime localDateTime = ExcelDateParseUtils.parseDateTime(time, pattern);
        assert localDateTime.format(DateTimeFormatter.ofPattern(pattern)).equals(time);
        System.out.println(localDateTime);
        assert true;
    }

    @Test
    void parse4() {
        try {
            String pattern = "HH:mm:ss";
            String time = "55:22:33·";
            LocalDateTime localDateTime = ExcelDateParseUtils.parseDateTime(time, pattern);
            localDateTime.format(DateTimeFormatter.ofPattern(pattern)).equals(time);
            System.out.println(localDateTime);
            assert false;
        } catch (Exception e) {
            e.printStackTrace();
            assert true;
        }
    }

    @Test
    void parse5() {
        try {
            String pattern = "";
            String time = "2223";
            LocalDateTime localDateTime = ExcelDateParseUtils.parseDateTime(time, pattern);
            System.out.println(localDateTime);
        } catch (Exception e) {
            e.printStackTrace();
            assert true;
        }
    }

    @Test
    void parse6() {
        try {
            String pattern = "HH:mm:ss";
            String time = "";
            LocalDateTime localDateTime = ExcelDateParseUtils.parseDateTime(time, pattern);
            assert localDateTime.format(DateTimeFormatter.ofPattern(pattern)).equals(time);
            System.out.println(localDateTime);
            assert false;
        } catch (Exception e) {
            e.printStackTrace();
            assert true;
        }
    }

    @Test
    void parse() {

        LocalDateTime localDateTime11 = ExcelDateParseUtils.parseDateTime("2022-02-01 23:22:12");
        LocalDateTime localDateTime12 = ExcelDateParseUtils.parseDateTime("2022-02-01 23:22");
        LocalDateTime localDateTime13 = ExcelDateParseUtils.parseDateTime("2022-02-01 23");
        LocalDateTime localDateTime14 = ExcelDateParseUtils.parseDateTime("2022-02-01");
        LocalDateTime localDateTime15 = ExcelDateParseUtils.parseDateTime("2022-02");

        LocalDateTime localDateTime211 = ExcelDateParseUtils.parseDateTime("2022/02/01 23:22:12 ");
        LocalDateTime localDateTime21 = ExcelDateParseUtils.parseDateTime("2022/02/01 23:22:12");
        LocalDateTime localDateTime22 = ExcelDateParseUtils.parseDateTime("2022/02/01 23:22");
        LocalDateTime localDateTime23 = ExcelDateParseUtils.parseDateTime("2022/02/01 23");
        LocalDateTime localDateTime231 = ExcelDateParseUtils.parseDateTime("2022/02/01 23 ");
        LocalDateTime localDateTime24 = ExcelDateParseUtils.parseDateTime("2022/02/01 ");
        LocalDateTime localDateTime25 = ExcelDateParseUtils.parseDateTime("2022/02");

        LocalDateTime localDateTime31 = ExcelDateParseUtils.parseDateTime("2022.02.01 23:22:12");
        LocalDateTime localDateTime32 = ExcelDateParseUtils.parseDateTime("2022.02.01 23:22");
        LocalDateTime localDateTime33 = ExcelDateParseUtils.parseDateTime("2022.02.01 23");
        LocalDateTime localDateTime34 = ExcelDateParseUtils.parseDateTime("2022.02.01");
        LocalDateTime localDateTime35 = ExcelDateParseUtils.parseDateTime("2022.02");

        LocalDateTime localDateTime41 = ExcelDateParseUtils.parseDateTime("20220201 232212");
        LocalDateTime localDateTime42 = ExcelDateParseUtils.parseDateTime("20220201 2322");
        LocalDateTime localDateTime43 = ExcelDateParseUtils.parseDateTime("20220201 23");
        LocalDateTime localDateTime44 = ExcelDateParseUtils.parseDateTime("20220201 ");
        LocalDateTime localDateTime45 = ExcelDateParseUtils.parseDateTime("202202 ");

        LocalDateTime localDateTime5 = ExcelDateParseUtils.parseDateTime("2022 ");

        assert true;
    }

}