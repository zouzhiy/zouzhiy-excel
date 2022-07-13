package io.github.zouzhiy.excel.metadata.parameter;

import io.github.zouzhiy.excel.callback.SheetReadConsumer;
import io.github.zouzhiy.excel.callback.SheetWriteConsumer;
import io.github.zouzhiy.excel.support.metadata.DemoDefault;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class SheetParameterTest {

    private final Random random = new Random(System.currentTimeMillis());

    @Test
    void titleRowStartIndex1() {
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .build();
        Assertions.assertEquals(sheetParameter.getTitleRowStartIndex(), -1);
    }

    @Test
    void titleRowStartIndex2() {
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .headRowStartIndex(1)
                .build();
        Assertions.assertEquals(sheetParameter.getTitleRowStartIndex(), 0);
    }

    @RepeatedTest(10)
    void titleRowStartIndex3() {
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .headRowStartIndex(1 + Math.abs(random.nextInt()))
                .build();
        Assertions.assertEquals(sheetParameter.getTitleRowStartIndex(), 0);
    }

    @Test
    void titleRowStartIndex4() {
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .dataRowStartIndex(0)
                .build();
        Assertions.assertEquals(sheetParameter.getTitleRowStartIndex(), -1);
    }

    @Test
    void titleRowStartIndex5() {
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .dataRowStartIndex(1)
                .build();
        Assertions.assertEquals(sheetParameter.getTitleRowStartIndex(), -1);
    }

    @Test
    void titleRowStartIndex6() {
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .dataRowStartIndex(2 + Math.abs(random.nextInt()))
                .build();
        Assertions.assertEquals(sheetParameter.getTitleRowStartIndex(), 0);
    }

    @Test
    void titleRowStartIndex7() {
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .headRowStartIndex(0)
                .dataRowStartIndex(2 + Math.abs(random.nextInt()))
                .build();
        Assertions.assertEquals(sheetParameter.getTitleRowStartIndex(), -1);
    }

    @Test
    void titleRowStartIndex8() {
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .headRowStartIndex(1)
                .dataRowStartIndex(2 + Math.abs(random.nextInt()))
                .build();
        Assertions.assertEquals(sheetParameter.getTitleRowStartIndex(), 0);
    }

    @Test
    void headRowStartIndex1() {
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .build();
        Assertions.assertEquals(sheetParameter.getTitleRowStartIndex(), -1);
    }

    @RepeatedTest(100)
    void headRowStartIndex2() {
        int rowIndex = random.nextInt();
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .headRowStartIndex(rowIndex)
                .build();
        Assertions.assertEquals(sheetParameter.getHeadRowStartIndex(), rowIndex);
    }

    @RepeatedTest(100)
    void headRowStartIndex3() {
        int rowIndex = Math.abs(random.nextInt());
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .dataRowStartIndex(rowIndex)
                .build();
        Assertions.assertEquals(sheetParameter.getHeadRowStartIndex(), rowIndex - 1);
    }

    @RepeatedTest(100)
    void headRowStartIndex4() {
        int rowIndexHead = Math.abs(random.nextInt());
        int rowIndex = Math.abs(random.nextInt());
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .headRowStartIndex(rowIndexHead)
                .dataRowStartIndex(rowIndex)
                .build();
        Assertions.assertEquals(sheetParameter.getHeadRowStartIndex(), rowIndexHead);
    }

    @Test
    void dataRowStartIndex1() {
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .build();
        Assertions.assertEquals(sheetParameter.getDataRowStartIndex(), 0);
    }

    @RepeatedTest(100)
    void dataRowStartIndex2() {
        int index = Math.abs(random.nextInt());
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .dataRowStartIndex(index)
                .build();
        Assertions.assertEquals(sheetParameter.getDataRowStartIndex(), index);
    }

    @Test
    void getTitleColumnStartIndex1() {
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .build();
        Assertions.assertEquals(sheetParameter.getTitleColumnStartIndex(), 0);
    }

    @RepeatedTest(10)
    void getTitleColumnStartIndex2() {
        int index = Math.abs(random.nextInt());
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .titleColumnStartIndex(index)
                .build();
        Assertions.assertEquals(sheetParameter.getTitleColumnStartIndex(), index);
    }

    @Test
    void getHeadColumnStartIndex1() {
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .build();
        Assertions.assertEquals(sheetParameter.getHeadColumnStartIndex(), 0);
    }

    @RepeatedTest(10)
    void getHeadColumnStartIndex2() {
        int index = Math.abs(random.nextInt());
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .headColumnStartIndex(index)
                .build();
        Assertions.assertEquals(sheetParameter.getHeadColumnStartIndex(), index);
    }


    @Test
    void getDataColumnStartIndex1() {
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .build();
        Assertions.assertEquals(sheetParameter.getDataColumnStartIndex(), 0);
    }

    @RepeatedTest(10)
    void getDataColumnStartIndex2() {
        int index = Math.abs(random.nextInt());
        SheetParameter sheetParameter = SheetParameter
                .builder()
                .dataColumnStartIndex(index)
                .build();
        Assertions.assertEquals(sheetParameter.getDataColumnStartIndex(), index);
    }

    @RepeatedTest(10)
    void sheet() {
        Integer sheetIndex = random.nextInt();
        String sheetName = "" + random.nextDouble();
        String title = "" + random.nextDouble();

        SheetParameter sheetParameter = SheetParameter.builder()
                .sheet(sheetIndex)
                .sheet(sheetName)
                .title(title)
                .build();

        Assertions.assertEquals(sheetParameter.getSheetIndex(), sheetIndex);
        Assertions.assertEquals(sheetParameter.getSheetName(), sheetName);
        Assertions.assertEquals(sheetParameter.getTitle(), title);
    }


    @Test
    void sheetReadConsumer1() {
        SheetParameter sheetParameter = SheetParameter.builder()
                .build();

        Assertions.assertEquals(sheetParameter.getSheetReadConsumerList().size(), 0);
    }

    @Test
    void sheetReadConsumer2() {
        SheetParameter sheetParameter = SheetParameter.builder()
                .sheetReadConsumer(new SheetReadConsumer<DemoDefault>() {
                })
                .sheetReadConsumer(new SheetReadConsumer<DemoDefault>() {
                })
                .sheetReadConsumer(new SheetReadConsumer<DemoDefault>() {
                })
                .build();

        Assertions.assertEquals(sheetParameter.getSheetReadConsumerList().size(), 3);
    }

    @Test
    void sheetWriteConsumer1() {
        SheetParameter sheetParameter = SheetParameter.builder()
                .build();

        Assertions.assertEquals(sheetParameter.getSheetWriteConsumerList().size(), 0);
    }

    @Test
    void sheetWriteConsumer2() {
        SheetParameter sheetParameter = SheetParameter.builder()
                .sheetWriteConsumer(new SheetWriteConsumer<DemoDefault>() {
                })
                .sheetWriteConsumer(new SheetWriteConsumer<DemoDefault>() {
                })
                .sheetWriteConsumer(new SheetWriteConsumer<DemoDefault>() {
                })
                .build();

        Assertions.assertEquals(sheetParameter.getSheetWriteConsumerList().size(), 3);
    }
}
