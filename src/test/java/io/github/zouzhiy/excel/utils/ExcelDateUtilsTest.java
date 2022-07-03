package io.github.zouzhiy.excel.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
class ExcelDateUtilsTest {

    @Test
    void parse() {
        String time = "2022-02-05";
        LocalDateTime localDateTime = ExcelDateUtils.parseDateTime(time, "yyyy-MM-dd");
        System.out.println(localDateTime);
        assert true;
    }

}