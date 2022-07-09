package io.github.zouzhiy.excel.utils;

import io.github.zouzhiy.excel.exceptions.ExcelException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @author zouzhiy
 * @since 2022/7/9
 */
class ExcelDateUtilsTest {

    @Test
    void parseDateTime1() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 7, 8, 19, 30, 30);
        String str1 = "2022-07-08 19:30:30";
        String str2 = "2022/07/08 19:30:30";
        String str3 = "2022.07.08 19:30:30";
        String str4 = "20220708 193030";
        String str5 = "2022年07月08日 19时30分30秒";

        LocalDateTime localDateTime1 = ExcelDateUtils.parseDateTime(str1);
        LocalDateTime localDateTime2 = ExcelDateUtils.parseDateTime(str2);
        LocalDateTime localDateTime3 = ExcelDateUtils.parseDateTime(str3);
        LocalDateTime localDateTime4 = ExcelDateUtils.parseDateTime(str4);
        LocalDateTime localDateTime5 = ExcelDateUtils.parseDateTime(str5);

        Assertions.assertEquals(localDateTime, localDateTime1);
        Assertions.assertEquals(localDateTime, localDateTime2);
        Assertions.assertEquals(localDateTime, localDateTime3);
        Assertions.assertEquals(localDateTime, localDateTime4);
        Assertions.assertEquals(localDateTime, localDateTime5);

        String str6 = "2022-0708 19:30:30";
        String str7 = "202207-08 19:30:30";
        String str8 = "2022-07-08 1930:30";
        String str9 = "2022-07--08 19:30:30";

        try {
            ExcelDateUtils.parseDateTime(str6);
            assert false;
        } catch (ExcelException e) {
            assert true;
        } catch (Exception e) {
            assert false;
        }

        try {
            ExcelDateUtils.parseDateTime(str7);
            assert false;
        } catch (ExcelException e) {
            assert true;
        } catch (Exception e) {
            assert false;
        }

        try {
            ExcelDateUtils.parseDateTime(str8);
            assert false;
        } catch (ExcelException e) {
            assert true;
        } catch (Exception e) {
            assert false;
        }

        try {
            ExcelDateUtils.parseDateTime(str9);
            assert false;
        } catch (ExcelException e) {
            assert true;
        } catch (Exception e) {
            assert false;
        }

    }

    @Test
    void parseDateTime2() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 7, 8, 19, 30, 0);
        String str1 = "2022-07-08 19:30";
        String str2 = "2022/07/08 19:30";
        String str3 = "2022.07.08 19:30";
        String str4 = "20220708 1930";
        String str5 = "2022年07月08日 19时30分";

        LocalDateTime localDateTime1 = ExcelDateUtils.parseDateTime(str1);
        LocalDateTime localDateTime2 = ExcelDateUtils.parseDateTime(str2);
        LocalDateTime localDateTime3 = ExcelDateUtils.parseDateTime(str3);
        LocalDateTime localDateTime4 = ExcelDateUtils.parseDateTime(str4);
        LocalDateTime localDateTime5 = ExcelDateUtils.parseDateTime(str5);

        Assertions.assertEquals(localDateTime, localDateTime1);
        Assertions.assertEquals(localDateTime, localDateTime2);
        Assertions.assertEquals(localDateTime, localDateTime3);
        Assertions.assertEquals(localDateTime, localDateTime4);
        Assertions.assertEquals(localDateTime, localDateTime5);
    }

    @Test
    void parseDateTime3() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 7, 8, 19, 0, 0);
        String str1 = "2022-07-08 19";
        String str2 = "2022/07/08 19";
        String str3 = "2022.07.08 19";
        String str4 = "20220708 19";
        String str5 = "2022年07月08日 19时";

        LocalDateTime localDateTime1 = ExcelDateUtils.parseDateTime(str1);
        LocalDateTime localDateTime2 = ExcelDateUtils.parseDateTime(str2);
        LocalDateTime localDateTime3 = ExcelDateUtils.parseDateTime(str3);
        LocalDateTime localDateTime4 = ExcelDateUtils.parseDateTime(str4);
        LocalDateTime localDateTime5 = ExcelDateUtils.parseDateTime(str5);

        Assertions.assertEquals(localDateTime, localDateTime1);
        Assertions.assertEquals(localDateTime, localDateTime2);
        Assertions.assertEquals(localDateTime, localDateTime3);
        Assertions.assertEquals(localDateTime, localDateTime4);
        Assertions.assertEquals(localDateTime, localDateTime5);
    }

    @Test
    void parseDateTime4() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 7, 8, 0, 0, 0);
        String str1 = "2022-07-08 ";
        String str2 = "2022/07/08 ";
        String str3 = "2022.07.08 ";
        String str4 = "20220708 ";
        String str5 = "2022年07月08日 ";

        LocalDateTime localDateTime1 = ExcelDateUtils.parseDateTime(str1);
        LocalDateTime localDateTime2 = ExcelDateUtils.parseDateTime(str2);
        LocalDateTime localDateTime3 = ExcelDateUtils.parseDateTime(str3);
        LocalDateTime localDateTime4 = ExcelDateUtils.parseDateTime(str4);
        LocalDateTime localDateTime5 = ExcelDateUtils.parseDateTime(str5);

        Assertions.assertEquals(localDateTime, localDateTime1);
        Assertions.assertEquals(localDateTime, localDateTime2);
        Assertions.assertEquals(localDateTime, localDateTime3);
        Assertions.assertEquals(localDateTime, localDateTime4);
        Assertions.assertEquals(localDateTime, localDateTime5);
    }

    @Test
    void parseDateTime5() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 7, 1, 0, 0, 0);
        String str1 = "2022-07 ";
        String str2 = "2022/07 ";
        String str3 = "2022.07 ";
        String str4 = "202207 ";
        String str5 = "2022年07月 ";

        LocalDateTime localDateTime1 = ExcelDateUtils.parseDateTime(str1);
        LocalDateTime localDateTime2 = ExcelDateUtils.parseDateTime(str2);
        LocalDateTime localDateTime3 = ExcelDateUtils.parseDateTime(str3);
        LocalDateTime localDateTime4 = ExcelDateUtils.parseDateTime(str4);
        LocalDateTime localDateTime5 = ExcelDateUtils.parseDateTime(str5);

        Assertions.assertEquals(localDateTime, localDateTime1);
        Assertions.assertEquals(localDateTime, localDateTime2);
        Assertions.assertEquals(localDateTime, localDateTime3);
        Assertions.assertEquals(localDateTime, localDateTime4);
        Assertions.assertEquals(localDateTime, localDateTime5);
    }

    @Test
    void parseDateTime6() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 1, 0, 0, 0);
        String str1 = "2022 ";

        LocalDateTime localDateTime1 = ExcelDateUtils.parseDateTime(str1);

        Assertions.assertEquals(localDateTime, localDateTime1);

    }

    @Test
    void parseDateTime7() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 7, 8, 19, 30, 22);
        String str1 = "2022-07-08 19:30:22";

        LocalDateTime localDateTime1 = ExcelDateUtils.parseDateTime(str1, "");
        LocalDateTime localDateTime2 = ExcelDateUtils.parseDateTime(str1, "yyyy-MM-dd HH:mm:ss");

        Assertions.assertEquals(localDateTime, localDateTime1);

        Assertions.assertEquals(localDateTime, localDateTime2);

        try {
            LocalDateTime localDateTime3 = ExcelDateUtils.parseDateTime(str1, "yyyy-MMdd HH:mm:ss");
            assert false;

        } catch (ExcelException e) {
            assert true;
        } catch (Exception e) {
            assert false;
        }

    }

    @Test
    void parseDateTime8() {
        String str1 = "1";


        try {
            LocalDateTime localDateTime2 = ExcelDateUtils.parseDateTime(str1);
            assert false;
        } catch (ExcelException e) {
            assert true;
        } catch (Exception e) {
            assert false;
        }

    }
}
