package io.github.zouzhiy.excel.support.metadata;

import io.github.zouzhiy.excel.annotation.ExcelClass;
import io.github.zouzhiy.excel.annotation.ExcelField;
import lombok.Data;


@Data
@ExcelClass
public class DemoDefault {

    @ExcelField
    private String name;

    @ExcelField
    private String title;
}
