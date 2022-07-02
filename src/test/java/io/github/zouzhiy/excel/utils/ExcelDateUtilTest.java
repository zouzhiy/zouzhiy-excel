package io.github.zouzhiy.excel.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
class ExcelDateUtilTest {

    @Test
    void parse() {
        String time = "2022-02-05";
        LocalDateTime localDateTime = ExcelDateUtil.parseDateTime(time, "yyyy-MM-dd");
        System.out.println(localDateTime);
        assert true;
    }

}