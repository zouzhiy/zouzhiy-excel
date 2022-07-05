package io.github.zouzhiy.excel.autoconfigure.vo;

import lombok.Builder;
import lombok.Data;
/**
 * @author zouzhiy
 * @since 2022/7/5
 */
@Data
@Builder
public class DemoVO {

    private Integer sno;

    private String name;

    private String position;
}
