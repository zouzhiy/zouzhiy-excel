package io.github.zouzhiy.excel.autoconfigure.example;

import io.github.zouzhiy.excel.autoconfigure.service.DemoService;
import io.github.zouzhiy.excel.autoconfigure.vo.DemoVO;
import io.github.zouzhiy.excel.builder.ZouzhiyExcelFactory;
import io.github.zouzhiy.excel.metadata.parameter.SheetParameter;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/5
 */
@RestController
@RequestMapping("example1")
public class Example1Controller {

    @Resource
    private ZouzhiyExcelFactory zouzhiyExcelFactory;

    @Resource
    private DemoService demoService;


    @GetMapping("export")
    public void export1(HttpServletResponse response) throws IOException {

        List<DemoVO> demoVOList = demoService.list();
        SheetParameter sheetParameter = SheetParameter.builder()
                .title("测试导出")
                .dataRowStartIndex(2)
                .build();
        WorkbookParameter workbookParameter = WorkbookParameter.builder()
                .output(response.getOutputStream())
                .sheetParameter(sheetParameter)
                .build();

        response.addHeader("Content-Disposition", "attachment; filename*=utf-8'zh_cn'" + URLEncoder.encode("测试导出.xlsx", StandardCharsets.UTF_8.name()));

        zouzhiyExcelFactory.getWorkbookWrite(workbookParameter, DemoVO.class)
                .write(demoVOList);

    }

}
