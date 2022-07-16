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
package io.github.zouzhiy.excel.old.builder;

import io.github.zouzhiy.excel.annotation.ExcelClass;
import io.github.zouzhiy.excel.annotation.ExcelField;
import io.github.zouzhiy.excel.enums.ExcelType;
import io.github.zouzhiy.excel.handler.bytes.ByteArrayBoxStringHandler;
import io.github.zouzhiy.excel.handler.bytes.ByteArrayStringHandler;
import io.github.zouzhiy.excel.handler.image.ImageByteCellHandler;
import io.github.zouzhiy.excel.handler.image.ImageFileCellHandler;
import io.github.zouzhiy.excel.handler.image.ImageUrlCellHandler;
import io.github.zouzhiy.excel.old.http.ImageHttpServer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DateUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
@Setter
@Getter
@EqualsAndHashCode(exclude = {"imageFile", "imageUrl", "imageByte"})
@ToString(exclude = {"imageFile", "imageUrl", "imageByte"})
@ExcelClass
public class Demo {

    private final static Random random = new Random(System.currentTimeMillis());

    static {
        ImageHttpServer.getInstance().start();
    }

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private BigDecimal bigDecimalBoolean = nextBoolean() ? null : nextBoolean() ? null : BigDecimal.valueOf(random.nextInt(1));
    @ExcelField(excelType = ExcelType.NUMERIC)
    private BigDecimal bigDecimalNumber = nextBoolean() ? null : nextBoolean() ? null : BigDecimal.valueOf(random.nextDouble());
    @ExcelField(excelType = ExcelType.STRING)
    private BigDecimal bigDecimalString = nextBoolean() ? null : nextBoolean() ? null : BigDecimal.valueOf(random.nextDouble());

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private BigInteger bigIntegerBoolean = nextBoolean() ? null : new BigInteger(String.valueOf(random.nextInt(1)));
    @ExcelField(excelType = ExcelType.NUMERIC)
    private BigInteger bigIntegerNumber = nextBoolean() ? null : new BigInteger(String.valueOf(random.nextInt(Integer.MAX_VALUE)));
    @ExcelField(excelType = ExcelType.STRING)
    private BigInteger bigIntegerString = nextBoolean() ? null : nextBoolean() ? null : new BigInteger(String.valueOf(random.nextInt()));

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private boolean unboxBooleanBoolean = nextBoolean() || nextBoolean();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private boolean unboxBooleanNumber = !nextBoolean() && nextBoolean();
    @ExcelField(excelType = ExcelType.STRING)
    private boolean unboxBooleanString = nextBoolean() || nextBoolean();

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private Boolean boxBooleanBoolean = nextBoolean() ? null : nextBoolean();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Boolean boxBooleanNumber = nextBoolean() ? null : nextBoolean();
    @ExcelField(excelType = ExcelType.STRING)
    private Boolean boxBooleanString = nextBoolean() ? null : nextBoolean();


    @ExcelField(excelType = ExcelType.STRING, cellHandler = ByteArrayBoxStringHandler.class)
    private Byte[] boxBytes;
    @ExcelField(excelType = ExcelType.STRING, cellHandler = ByteArrayStringHandler.class)
    private byte[] unboxBytes = "ttt".getBytes(StandardCharsets.UTF_8);
    @ExcelField(excelType = ExcelType.BOOLEAN)
    private Byte byteBoolean = nextBoolean() ? null : nextBoolean() ? null : (byte) 1;
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Byte byteNumber = nextBoolean() ? null : (byte) 1;
    @ExcelField(excelType = ExcelType.STRING)
    private Byte byteString = nextBoolean() ? null : (byte) 1;
    @ExcelField(excelType = ExcelType.DATE)
    private Calendar calendarDate = nextBoolean() ? null : Calendar.getInstance();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Calendar calendarNumber = nextBoolean() ? null : Calendar.getInstance();
    @ExcelField(excelType = ExcelType.STRING)
    private Calendar calendarString = nextBoolean() ? null : Calendar.getInstance();
    @ExcelField(excelType = ExcelType.DATE)
    private Date dateDate = nextBoolean() ? null : Calendar.getInstance().getTime();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Date dateNumber = nextBoolean() ? null : Calendar.getInstance().getTime();
    @ExcelField(excelType = ExcelType.STRING)
    private Date dateString = nextBoolean() ? null : Calendar.getInstance().getTime();
    @ExcelField(excelType = ExcelType.BOOLEAN)
    private double unboxDoubleBoolean = random.nextInt(1) * 1.0;
    @ExcelField(excelType = ExcelType.DATE)
    private double unboxDoubleDate = DateUtil.getExcelDate(Calendar.getInstance().getTime());
    @ExcelField(excelType = ExcelType.NUMERIC)
    private double unboxDoubleNumber = random.nextDouble();
    @ExcelField(excelType = ExcelType.STRING)
    private double unboxDoubleString = random.nextDouble();
    @ExcelField(excelType = ExcelType.BOOLEAN)
    private Double boxDoubleBoolean = nextBoolean() ? null : random.nextInt(1) * 1.0;
    @ExcelField(excelType = ExcelType.DATE)
    private Double boxDoubleDate = nextBoolean() ? null : DateUtil.getExcelDate(Calendar.getInstance().getTime());
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Double boxDoubleNumber = nextBoolean() ? null : random.nextDouble();
    @ExcelField(excelType = ExcelType.STRING)
    private Double boxDoubleString = nextBoolean() ? null : random.nextDouble();
    @ExcelField(excelType = ExcelType.BOOLEAN)
    private float unboxFloatBoolean = random.nextInt(1) * 1.0f;
    @ExcelField(excelType = ExcelType.NUMERIC)
    private float unboxFloatNumber = random.nextFloat();
    @ExcelField(excelType = ExcelType.STRING)
    private float unboxFloatString = random.nextFloat();
    @ExcelField(excelType = ExcelType.BOOLEAN)
    private Float boxFloatBoolean = nextBoolean() ? null : random.nextInt(1) * 1.0f;
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Float boxFloatNumber = nextBoolean() ? null : random.nextFloat();
    @ExcelField(excelType = ExcelType.STRING)
    private Float boxFloatString = nextBoolean() ? null : random.nextFloat();
    @ExcelField(cellHandler = ImageUrlCellHandler.class)
    private String imageUrl = "http://localhost:18080/jpg1.jpg";
    @ExcelField(excelType = ExcelType.NONE, cellHandler = ImageByteCellHandler.class)
    private byte[] imageByte;
    @ExcelField(cellHandler = ImageFileCellHandler.class)
    private File imageFile;
    @ExcelField(excelType = ExcelType.BOOLEAN)
    private int unboxIntegerBoolean = random.nextInt(1);
    @ExcelField(excelType = ExcelType.NUMERIC)
    private int unboxIntegerNumber = random.nextInt();
    @ExcelField(excelType = ExcelType.STRING)
    private int unboxIntegerString = random.nextInt();
    @ExcelField(excelType = ExcelType.BOOLEAN)
    private Integer boxIntegerBoolean = nextBoolean() ? null : random.nextInt(1);
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Integer boxIntegerNumber = nextBoolean() ? null : random.nextInt();
    @ExcelField(excelType = ExcelType.STRING)
    private Integer boxIntegerString = nextBoolean() ? null : random.nextInt();
    @ExcelField(excelType = ExcelType.DATE)
    private LocalDate localDateDate = nextBoolean() ? null : LocalDate.now();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private LocalDate localDateNumber = nextBoolean() ? null : LocalDate.now();
    @ExcelField(excelType = ExcelType.STRING)
    private LocalDate localDateString = nextBoolean() ? null : LocalDate.now();
    @ExcelField(excelType = ExcelType.DATE)
    private LocalDateTime localDateTimeDate = nextBoolean() ? null : LocalDateTime.now();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private LocalDateTime localDateTimeNumber = nextBoolean() ? null : LocalDateTime.now();
    @ExcelField(excelType = ExcelType.STRING)
    private LocalDateTime localDateTimeString = nextBoolean() ? null : LocalDateTime.now();
    @ExcelField(excelType = ExcelType.DATE)
    private LocalTime localTimeDate = nextBoolean() ? null : LocalTime.now();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private LocalTime localTimeNumber = nextBoolean() ? null : LocalTime.now();
    @ExcelField(excelType = ExcelType.STRING)
    private LocalTime localTimeString = nextBoolean() ? null : LocalTime.now();
    @ExcelField(excelType = ExcelType.BOOLEAN)
    private long unboxLongBoolean = random.nextInt(1) * 1L;
    @ExcelField(excelType = ExcelType.NUMERIC)
    private long unboxLongNumber = random.nextInt() * 1L;
    @ExcelField(excelType = ExcelType.STRING)
    private long unboxLongString = random.nextInt() * 1L;
    @ExcelField(excelType = ExcelType.BOOLEAN)
    private Long boxLongBoolean = nextBoolean() ? null : random.nextInt(1) * 1L;
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Long boxLongNumber = nextBoolean() ? null : random.nextInt() * 1L;
    @ExcelField(excelType = ExcelType.STRING)
    private Long boxLongString = nextBoolean() ? null : random.nextInt() * 1L;
    @ExcelField(excelType = ExcelType.BOOLEAN)
    private short unboxShortBoolean = (short) random.nextInt(1);
    @ExcelField(excelType = ExcelType.NUMERIC)
    private short unboxShortNumber = (short) random.nextInt(Short.MAX_VALUE);
    @ExcelField(excelType = ExcelType.STRING)
    private short unboxShortString = (short) random.nextInt(Short.MAX_VALUE);
    @ExcelField(excelType = ExcelType.BOOLEAN)
    private Short boxShortBoolean = nextBoolean() ? null : (short) random.nextInt(1);
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Short boxShortNumber = nextBoolean() ? null : (short) random.nextInt(Short.MAX_VALUE);
    @ExcelField(excelType = ExcelType.STRING)
    private Short boxShortString = nextBoolean() ? null : (short) random.nextInt(Short.MAX_VALUE);
    @ExcelField(excelType = ExcelType.BOOLEAN)
    private String stringBoolean = nextBoolean() ? null : String.valueOf(nextBoolean());
    @ExcelField(excelType = ExcelType.DATE)
    private String stringDate = nextBoolean() ? null : LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    @ExcelField(excelType = ExcelType.STRING, sort = 2)
    private String stringString = nextBoolean() ? null : "12334";
    @ExcelField(excelType = ExcelType.NUMERIC, sort = 1)
    private String stringNumber = nextBoolean() ? null : String.valueOf(random.nextDouble());
    @ExcelField(excelType = ExcelType.DATE)
    private Timestamp timestampDate = nextBoolean() ? null : new Timestamp(System.currentTimeMillis());
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Timestamp timestampNumber = nextBoolean() ? null : new Timestamp(System.currentTimeMillis());
    @ExcelField(excelType = ExcelType.STRING)
    private Timestamp timestampString = nextBoolean() ? null : new Timestamp(System.currentTimeMillis());

    {
        byte[] bytes = "ttt".getBytes(StandardCharsets.UTF_8);
        Byte[] newBytes = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            newBytes[i] = bytes[i];
        }
        boxBytes = newBytes;
    }

    {
        try {
            String exportTemplateFileName = "jpg1.jpg";
            String exportTemplateFilePath = "statics/image/" + exportTemplateFileName;
            InputStream exportTemplateInputStream = this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath);
            assert exportTemplateInputStream != null;
            imageByte = IOUtils.toByteArray(exportTemplateInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    {
        try {
            String exportTemplateFileName = "jpg1.jpg";
            String exportTemplateFilePath = "statics/image/" + exportTemplateFileName;

            InputStream exportTemplateInputStream = this.getClass().getClassLoader().getResourceAsStream(exportTemplateFilePath);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
            String outputFileName = simpleDateFormat.format(new Date()) + ".jpg";
            URL url = this.getClass().getResource("/");

            assert url != null;
            String filePath = url.getFile();
            String outputFilePath = filePath + File.separator + "output" + File.separator + "file" + File.separator + outputFileName;

            assert exportTemplateInputStream != null;
            imageFile = new File(outputFilePath);
            FileUtils.copyToFile(exportTemplateInputStream, imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean nextBoolean() {
        return false;
//        return random.nextBoolean();
    }
}
