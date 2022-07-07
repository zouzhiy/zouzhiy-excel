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
import lombok.Data;
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
@Data
@ExcelClass
public class Demo {

    static {
        ImageHttpServer.getInstance().start();
    }

    private final static Random random = new Random(System.currentTimeMillis());

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private BigDecimal bigDecimalBoolean = random.nextBoolean() ? null : random.nextBoolean() ? null : BigDecimal.valueOf(random.nextInt(1));
    @ExcelField(excelType = ExcelType.NUMERIC)
    private BigDecimal bigDecimalNumber = random.nextBoolean() ? null : random.nextBoolean() ? null : BigDecimal.valueOf(random.nextDouble());
    @ExcelField(excelType = ExcelType.STRING)
    private BigDecimal bigDecimalString = random.nextBoolean() ? null : random.nextBoolean() ? null : BigDecimal.valueOf(random.nextDouble());

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private BigInteger bigIntegerBoolean = random.nextBoolean() ? null : new BigInteger(String.valueOf(random.nextInt(1)));
    @ExcelField(excelType = ExcelType.NUMERIC)
    private BigInteger bigIntegerNumber = random.nextBoolean() ? null : new BigInteger(String.valueOf(random.nextInt(Integer.MAX_VALUE)));
    @ExcelField(excelType = ExcelType.STRING)
    private BigInteger bigIntegerString = random.nextBoolean() ? null : random.nextBoolean() ? null : new BigInteger(String.valueOf(random.nextInt()));

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private boolean unboxBooleanBoolean = random.nextBoolean() || random.nextBoolean();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private boolean unboxBooleanNumber = !random.nextBoolean() && random.nextBoolean();
    @ExcelField(excelType = ExcelType.STRING)
    private boolean unboxBooleanString = random.nextBoolean() || random.nextBoolean();

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private Boolean boxBooleanBoolean = random.nextBoolean() ? null : random.nextBoolean();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Boolean boxBooleanNumber = random.nextBoolean() ? null : random.nextBoolean();
    @ExcelField(excelType = ExcelType.STRING)
    private Boolean boxBooleanString = random.nextBoolean() ? null : random.nextBoolean();


    @ExcelField(excelType = ExcelType.STRING, cellHandler = ByteArrayBoxStringHandler.class)
    private Byte[] boxBytes;

    {
        byte[] bytes = "ttt".getBytes(StandardCharsets.UTF_8);
        Byte[] newBytes = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            newBytes[i] = bytes[i];
        }
        boxBytes = newBytes;
    }

    @ExcelField(excelType = ExcelType.STRING, cellHandler = ByteArrayStringHandler.class)
    private byte[] unboxBytes = "ttt".getBytes(StandardCharsets.UTF_8);

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private Byte byteBoolean = random.nextBoolean() ? null : random.nextBoolean() ? null : (byte) 1;
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Byte byteNumber = random.nextBoolean() ? null : (byte) 1;
    @ExcelField(excelType = ExcelType.STRING)
    private Byte byteString = random.nextBoolean() ? null : (byte) 1;

    @ExcelField(excelType = ExcelType.DATE)
    private Calendar calendarDate = random.nextBoolean() ? null : Calendar.getInstance();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Calendar calendarNumber = random.nextBoolean() ? null : Calendar.getInstance();
    @ExcelField(excelType = ExcelType.STRING)
    private Calendar calendarString = random.nextBoolean() ? null : Calendar.getInstance();


    @ExcelField(excelType = ExcelType.DATE)
    private Date dateDate = random.nextBoolean() ? null : Calendar.getInstance().getTime();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Date dateNumber = random.nextBoolean() ? null : Calendar.getInstance().getTime();
    @ExcelField(excelType = ExcelType.STRING)
    private Date dateString = random.nextBoolean() ? null : Calendar.getInstance().getTime();

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private double unboxDoubleBoolean = random.nextDouble();
    @ExcelField(excelType = ExcelType.DATE)
    private double unboxDoubleDate = DateUtil.getExcelDate(Calendar.getInstance().getTime());
    @ExcelField(excelType = ExcelType.NUMERIC)
    private double unboxDoubleNumber = random.nextDouble();
    @ExcelField(excelType = ExcelType.STRING)
    private double unboxDoubleString = random.nextDouble();

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private Double boxDoubleBoolean = random.nextBoolean() ? null : random.nextDouble();
    @ExcelField(excelType = ExcelType.DATE)
    private Double boxDoubleDate = random.nextBoolean() ? null : DateUtil.getExcelDate(Calendar.getInstance().getTime());
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Double boxDoubleNumber = random.nextBoolean() ? null : random.nextDouble();
    @ExcelField(excelType = ExcelType.STRING)
    private Double boxDoubleString = random.nextBoolean() ? null : random.nextDouble();


    @ExcelField(excelType = ExcelType.BOOLEAN)
    private float unboxFloatBoolean = random.nextFloat();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private float unboxFloatNumber = random.nextFloat();
    @ExcelField(excelType = ExcelType.STRING)
    private float unboxFloatString = random.nextFloat();

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private Float boxFloatBoolean = random.nextBoolean() ? null : random.nextFloat();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Float boxFloatNumber = random.nextBoolean() ? null : random.nextFloat();
    @ExcelField(excelType = ExcelType.STRING)
    private Float boxFloatString = random.nextBoolean() ? null : random.nextFloat();

    @ExcelField(cellHandler = ImageUrlCellHandler.class)
    private String imageUrl = "http://localhost:18080/jpg1.jpg";

    @ExcelField(excelType = ExcelType.NONE, cellHandler = ImageByteCellHandler.class)
    private byte[] imageByte;

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

    @ExcelField(cellHandler = ImageFileCellHandler.class)
    private File imageFile;

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

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private int unboxIntegerBoolean = random.nextInt();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private int unboxIntegerNumber = random.nextInt();
    @ExcelField(excelType = ExcelType.STRING)
    private int unboxIntegerString = random.nextInt();

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private Integer boxIntegerBoolean = random.nextBoolean() ? null : random.nextInt();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Integer boxIntegerNumber = random.nextBoolean() ? null : random.nextInt();
    @ExcelField(excelType = ExcelType.STRING)
    private Integer boxIntegerString = random.nextBoolean() ? null : random.nextInt();


    @ExcelField(excelType = ExcelType.DATE)
    private LocalDate localDateDate = random.nextBoolean() ? null : LocalDate.now();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private LocalDate localDateNumber = random.nextBoolean() ? null : LocalDate.now();
    @ExcelField(excelType = ExcelType.STRING)
    private LocalDate localDateString = random.nextBoolean() ? null : LocalDate.now();

    @ExcelField(excelType = ExcelType.DATE)
    private LocalDateTime localDateTimeDate = random.nextBoolean() ? null : LocalDateTime.now();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private LocalDateTime localDateTimeNumber = random.nextBoolean() ? null : LocalDateTime.now();
    @ExcelField(excelType = ExcelType.STRING)
    private LocalDateTime localDateTimeString = random.nextBoolean() ? null : LocalDateTime.now();

    @ExcelField(excelType = ExcelType.DATE)
    private LocalTime localTimeDate = random.nextBoolean() ? null : LocalTime.now();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private LocalTime localTimeNumber = random.nextBoolean() ? null : LocalTime.now();
    @ExcelField(excelType = ExcelType.STRING)
    private LocalTime localTimeString = random.nextBoolean() ? null : LocalTime.now();

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private long unboxLongBoolean = random.nextLong();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private long unboxLongNumber = random.nextLong();
    @ExcelField(excelType = ExcelType.STRING)
    private long unboxLongString = random.nextLong();

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private Long boxLongBoolean = random.nextBoolean() ? null : random.nextLong();
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Long boxLongNumber = random.nextBoolean() ? null : random.nextLong();
    @ExcelField(excelType = ExcelType.STRING)
    private Long boxLongString = random.nextBoolean() ? null : random.nextLong();


    @ExcelField(excelType = ExcelType.BOOLEAN)
    private short unboxShortBoolean = (short) random.nextInt(Short.MAX_VALUE);
    @ExcelField(excelType = ExcelType.NUMERIC)
    private short unboxShortNumber = (short) random.nextInt(Short.MAX_VALUE);
    @ExcelField(excelType = ExcelType.STRING)
    private short unboxShortString = (short) random.nextInt(Short.MAX_VALUE);

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private Short boxShortBoolean = random.nextBoolean() ? null : (short) random.nextInt(Short.MAX_VALUE);
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Short boxShortNumber = random.nextBoolean() ? null : (short) random.nextInt(Short.MAX_VALUE);
    @ExcelField(excelType = ExcelType.STRING)
    private Short boxShortString = random.nextBoolean() ? null : (short) random.nextInt(Short.MAX_VALUE);

    @ExcelField(excelType = ExcelType.BOOLEAN)
    private String stringBoolean = random.nextBoolean() ? null : String.valueOf(random.nextBoolean());
    @ExcelField(excelType = ExcelType.DATE)
    private String stringDate = random.nextBoolean() ? null : LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    @ExcelField(excelType = ExcelType.STRING, sort = 2)
    private String stringString = random.nextBoolean() ? null : "12334";
    @ExcelField(excelType = ExcelType.NUMERIC, sort = 1)
    private String stringNumber = random.nextBoolean() ? null : String.valueOf(random.nextDouble());

    @ExcelField(excelType = ExcelType.DATE)
    private Timestamp timestampDate = random.nextBoolean() ? null : new Timestamp(System.currentTimeMillis());
    @ExcelField(excelType = ExcelType.NUMERIC)
    private Timestamp timestampNumber = random.nextBoolean() ? null : new Timestamp(System.currentTimeMillis());
    @ExcelField(excelType = ExcelType.STRING)
    private Timestamp timestampString = random.nextBoolean() ? null : new Timestamp(System.currentTimeMillis());


}
