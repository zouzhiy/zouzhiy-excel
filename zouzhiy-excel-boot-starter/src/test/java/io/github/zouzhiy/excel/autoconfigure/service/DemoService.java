package io.github.zouzhiy.excel.autoconfigure.service;

import io.github.zouzhiy.excel.autoconfigure.vo.DemoVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
/**
 * @author zouzhiy
 * @since 2022/7/5
 */
@Service
public class DemoService {

    private final Random random = new Random(System.currentTimeMillis());

    public List<DemoVO> list() {
        int maxSize = random.nextInt(700);
        return IntStream.rangeClosed(1, maxSize).mapToObj(this::getItem).collect(Collectors.toList());
    }

    private DemoVO getItem(int sno) {
        return DemoVO.builder()
                .sno(sno)
                .name(random.nextBoolean() ? random.nextBoolean() ? null : "" : "name_" + random.nextInt(555))
                .position(random.nextBoolean() ? random.nextBoolean() ? null : "" : "position_" + random.nextInt(555))
                .build();

    }
}
