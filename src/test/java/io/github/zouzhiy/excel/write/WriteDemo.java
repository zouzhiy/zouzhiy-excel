package io.github.zouzhiy.excel.write;

import io.github.zouzhiy.excel.annotation.ExcelClass;
import io.github.zouzhiy.excel.annotation.ExcelField;
import io.github.zouzhiy.excel.annotation.ExcelFont;
import io.github.zouzhiy.excel.annotation.ExcelStyle;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.enums.StyleHorizontalAlignment;
import io.github.zouzhiy.excel.type.ValueListStringHandler;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;

import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
@Data
@ExcelClass(titleStyle = @ExcelStyle(font = @ExcelFont(bold = true, fontHeightInPoints = 16), horizontalAlignment = StyleHorizontalAlignment.CENTER))
public class WriteDemo {

    @ExcelField(title = "name-覆盖")
    private String name;

    @ExcelField(title = "title-覆盖", colspan = 2)
    private String title;

    @ExcelField(title = "valueList-覆盖", colspan = 3, cellHandler = ValueListStringHandler.class)
    private List<String> valueList;

    @ExcelField(title = "boolean-覆盖", colspan = 4, dataStyle = @ExcelStyle(borderLeft = BorderStyle.DASHED, borderBottom = BorderStyle.DOUBLE), excelType = ExcelType.BOOLEAN)
    private String booleanStr;

}
