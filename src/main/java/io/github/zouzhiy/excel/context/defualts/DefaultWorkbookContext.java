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
package io.github.zouzhiy.excel.context.defualts;

import io.github.zouzhiy.excel.context.WorkbookContext;
import io.github.zouzhiy.excel.enums.StyleHorizontalAlignment;
import io.github.zouzhiy.excel.enums.StyleVerticalAlignment;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.config.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.config.ExcelFontConfig;
import io.github.zouzhiy.excel.metadata.config.ExcelStyleConfig;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.metadata.result.CellStyleResultSet;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultWorkbookContext implements WorkbookContext {

    private final Configuration configuration;

    private final WorkbookParameter workbookParameter;

    private Workbook workbook;

    private FormulaEvaluator formulaEvaluator;

    private DataFormat dataFormat;

    private CellStyle titleCellStyle = null;

    private final Map<String, CellStyle> headCellStyleMap = new HashMap<>(32);

    private final Map<String, CellStyle> dataCellStyleMap = new HashMap<>(32);

    public DefaultWorkbookContext(Configuration configuration, WorkbookParameter workbookParameter) {
        this.configuration = configuration;
        this.workbookParameter = workbookParameter;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public WorkbookParameter getWorkbookParameter() {
        return workbookParameter;
    }

    @Override
    public Workbook getWorkbook() {
        if (workbook == null) {
            this.workbook = this.createWorkbook();
        }
        return workbook;
    }

    @Override
    public FormulaEvaluator getFormulaEvaluator() {
        if (formulaEvaluator == null) {
            formulaEvaluator = this.getWorkbook().getCreationHelper().createFormulaEvaluator();
        }
        return formulaEvaluator;
    }

    @Override
    public void putTitleCellStyle(ExcelClassConfig excelClassConfig, CellStyle titleCellStyle) {
        this.titleCellStyle = titleCellStyle;
    }

    @Override
    public void putHeadCellStyle(CellStyleResultSet headCellStyleResultSet) {
        headCellStyleResultSet.getCellStyleResultList().forEach(item -> headCellStyleMap.put(item.getExcelFieldConfig().getPropertyName(), item.getCellStyle()));
    }

    @Override
    public void putDataCellStyle(CellStyleResultSet dataCellStyleResultSet) {
        dataCellStyleResultSet.getCellStyleResultList().forEach(item -> dataCellStyleMap.put(item.getExcelFieldConfig().getPropertyName(), item.getCellStyle()));
    }

    @Override
    public CellStyle getTitleCellStyle(ExcelClassConfig excelClassConfig) {
        if (titleCellStyle == null) {
            titleCellStyle = this.createTitleCellStyle(excelClassConfig);
        }
        return titleCellStyle;
    }

    @Override
    public CellStyle getHeadCellStyle(ExcelFieldConfig excelFieldConfig) {
        String propertyName = excelFieldConfig.getPropertyName();
        return headCellStyleMap.computeIfAbsent(propertyName, s -> createHeadCellStyle(excelFieldConfig));
    }

    @Override
    public CellStyle getDataCellStyle(ExcelFieldConfig excelFieldConfig, String defaultFormat) {
        String propertyName = excelFieldConfig.getPropertyName();
        return dataCellStyleMap.computeIfAbsent(propertyName, s -> createDataCellStyle(excelFieldConfig, defaultFormat));
    }

    private CellStyle createTitleCellStyle(ExcelClassConfig excelClassConfig) {
        CellStyle cellStyle = this.getWorkbook().createCellStyle();
        ExcelStyleConfig titleStyle = excelClassConfig.getTitleStyle();

        DataFormat dataFormat = this.getDataFormat();
        String titleFormat = excelClassConfig.getTitleFormat();
        this.setValue(titleFormat != null && titleFormat.trim().length() > 0, cellStyle::setDataFormat, () -> dataFormat.getFormat(titleFormat));

        this.setCellStyle(cellStyle, titleStyle);

        workbookParameter.getCellStyleConsumerList().forEach(item -> item.afterCreateTitleCellStyle(excelClassConfig, cellStyle));

        return cellStyle;
    }

    private CellStyle createHeadCellStyle(ExcelFieldConfig excelFieldConfig) {
        CellStyle cellStyle = this.getWorkbook().createCellStyle();
        ExcelStyleConfig headStyle = excelFieldConfig.getHeadStyle();

        DataFormat dataFormat = this.getDataFormat();
        String headFormat = excelFieldConfig.getHeadFormat();
        this.setValue(headFormat != null && headFormat.trim().length() > 0, cellStyle::setDataFormat, () -> dataFormat.getFormat(headFormat));

        this.setCellStyle(cellStyle, headStyle);

        workbookParameter.getCellStyleConsumerList().forEach(item -> item.afterCreateHeadCellStyle(excelFieldConfig, cellStyle));

        return cellStyle;
    }

    private CellStyle createDataCellStyle(ExcelFieldConfig excelFieldConfig, String defaultDataFormat) {
        CellStyle cellStyle = this.getWorkbook().createCellStyle();

        String excelFormat = excelFieldConfig.getExcelFormat();
        String realFormat = excelFormat != null && excelFormat.trim().length() > 0 ? excelFormat.trim() : defaultDataFormat.trim();
        if (realFormat.length() > 0) {
            DataFormat dataFormat = this.getDataFormat();
            short dataFormatFormat = dataFormat.getFormat(realFormat);
            cellStyle.setDataFormat(dataFormatFormat);
        }

        ExcelStyleConfig dataStyle = excelFieldConfig.getDataStyle();
        this.setCellStyle(cellStyle, dataStyle);

        workbookParameter.getCellStyleConsumerList().forEach(item -> item.afterCreateDataCellStyle(excelFieldConfig, defaultDataFormat, cellStyle));

        return cellStyle;
    }

    private Workbook createWorkbook() {
        InputStream inputStream = workbookParameter.getInputStream();
        Workbook workbook;
        try {
            if (inputStream != null) {
                workbook = WorkbookFactory.create(inputStream);
            } else {
                workbook = WorkbookFactory.create(workbookParameter.isXssf());
            }
        } catch (IOException ioException) {
            throw new ExcelException(ioException, "创建工作簿失败");
        }

        return workbook;
    }

    private DataFormat getDataFormat() {
        if (dataFormat == null) {
            dataFormat = this.getWorkbook().createDataFormat();
        }
        return dataFormat;
    }

    private void setCellStyle(CellStyle cellStyle, ExcelStyleConfig excelStyleConfig) {

        this.setValue(cellStyle::setHidden, excelStyleConfig::isHidden);
        this.setValue(cellStyle::setLocked, excelStyleConfig::isLocked);
        this.setValue(cellStyle::setQuotePrefixed, excelStyleConfig::isQuotePrefix);
        this.setValue(!excelStyleConfig.getHorizontalAlignment().equals(StyleHorizontalAlignment.NONE), cellStyle::setAlignment, () -> excelStyleConfig.getHorizontalAlignment().getValue());
        this.setValue(cellStyle::setWrapText, excelStyleConfig::isWrapText);
        this.setValue(!excelStyleConfig.getVerticalAlignment().equals(StyleVerticalAlignment.NONE), cellStyle::setVerticalAlignment, () -> excelStyleConfig.getVerticalAlignment().getValue());
        if (this.workbookParameter.isXssf()) {
            this.setValue(cellStyle::setRotation, excelStyleConfig::getRotation);
        } else {
            this.setValue(cellStyle::setRotation, () -> {
                short rotation = excelStyleConfig.getRotation();
                return rotation > 100 ? (short) (90 - rotation) : rotation;
            });
        }
        this.setValue(excelStyleConfig.getIndent() != -1, cellStyle::setIndention, excelStyleConfig::getIndent);
        this.setValue(cellStyle::setBorderLeft, excelStyleConfig::getBorderLeft);
        this.setValue(cellStyle::setBorderRight, excelStyleConfig::getBorderRight);
        this.setValue(cellStyle::setBorderTop, excelStyleConfig::getBorderTop);
        this.setValue(cellStyle::setBorderBottom, excelStyleConfig::getBorderBottom);
        this.setValue(excelStyleConfig.getLeftBorderColor() != -1, cellStyle::setLeftBorderColor, excelStyleConfig::getLeftBorderColor);
        this.setValue(excelStyleConfig.getRightBorderColor() != -1, cellStyle::setRightBorderColor, excelStyleConfig::getRightBorderColor);
        this.setValue(excelStyleConfig.getTopBorderColor() != -1, cellStyle::setTopBorderColor, excelStyleConfig::getTopBorderColor);
        this.setValue(excelStyleConfig.getBottomBorderColor() != -1, cellStyle::setBottomBorderColor, excelStyleConfig::getBottomBorderColor);
        this.setValue(cellStyle::setFillPattern, excelStyleConfig::getFillPattern);
        this.setValue(excelStyleConfig.getFillBackgroundColor() != -1, cellStyle::setFillBackgroundColor, excelStyleConfig::getFillBackgroundColor);
        this.setValue(excelStyleConfig.getFillForegroundColor() != -1, cellStyle::setFillForegroundColor, excelStyleConfig::getFillForegroundColor);
        this.setValue(cellStyle::setShrinkToFit, excelStyleConfig::isShrinkToFit);

        ExcelFontConfig excelFontConfig = excelStyleConfig.getFont();
        Font font = this.workbook.createFont();
        this.setValue(excelFontConfig.getFontName() != null && excelFontConfig.getFontName().trim().length() > 0, font::setFontName, excelFontConfig::getFontName);
        this.setValue(excelFontConfig.getFontHeight() != -1, font::setFontHeight, excelFontConfig::getFontHeight);
        this.setValue(excelFontConfig.getFontHeightInPoints() != -1, font::setFontHeightInPoints, excelFontConfig::getFontHeightInPoints);
        this.setValue(font::setItalic, excelFontConfig::isItalic);
        this.setValue(font::setStrikeout, excelFontConfig::isStrikeout);
        this.setValue(excelFontConfig.getColor() != -1, font::setColor, excelFontConfig::getColor);
        this.setValue(font::setTypeOffset, () -> excelFontConfig.getTypeOffset().getValue());
        this.setValue(font::setUnderline, () -> excelFontConfig.getUnderline().getByteValue());
        this.setValue(font::setCharSet, () -> excelFontConfig.getCharset().getNativeId());
        this.setValue(font::setBold, excelFontConfig::isBold);
        cellStyle.setFont(font);
    }

    private <T> void setValue(Consumer<T> consumer, Supplier<T> supplier) {
        this.setValue(true, consumer, supplier);
    }

    private <T> void setValue(boolean condition, Consumer<T> consumer, Supplier<T> supplier) {
        if (condition) {
            consumer.accept(supplier.get());
        }
    }
}
