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
package io.github.zouzhiy.excel.parsing;

import io.github.zouzhiy.excel.annotation.ExcelClass;
import io.github.zouzhiy.excel.annotation.ExcelField;
import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.ExcelFieldConfig;
import io.github.zouzhiy.excel.metadata.ExcelStyleConfig;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class ExcelAnnotationParse {

    private final Configuration configuration;

    private boolean configCacheEnabled = true;

    private final Map<Class<?>, ExcelClassConfig> excelClassConfigMap = new ConcurrentHashMap<>(32);

    public ExcelAnnotationParse(Configuration configuration) {
        this.configuration = configuration;
    }

    public boolean isConfigCacheEnabled() {
        return configCacheEnabled;
    }

    public void setConfigCacheEnabled(boolean configCacheEnabled) {
        this.configCacheEnabled = configCacheEnabled;
    }

    public ExcelClassConfig findForClass(Class<?> clazz) {
        if (configCacheEnabled) {
            // synchronized (type) removed see issue #461
            return excelClassConfigMap.computeIfAbsent(clazz, this::parseForClass);
        } else {
            return this.parseForClass(clazz);
        }
    }

    private ExcelClassConfig parseForClass(Class<?> clazz) {

        ExcelClass excelClass = clazz.getAnnotation(ExcelClass.class);
        if (excelClass == null) {
            throw new ExcelException("未配置的类");
        }

        List<Field> fieldList = this.listAllDeclaredField(clazz);
        List<ExcelFieldConfig> excelFieldConfigList = fieldList
                .stream()
                .map(this::convert)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(ExcelFieldConfig::getSort))
                .collect(Collectors.toList());

        return ExcelClassConfig.builder()
                .rowTitleWrite(excelClass.rowTitleWrite())
                .rowHeadWrite(excelClass.rowHeadWrite())
                .rowFootWrite(excelClass.rowFootWrite())
                .rowTitleRead(excelClass.rowTitleRead())
                .rowHeadRead(excelClass.rowHeadRead())
                .rowFootRead(excelClass.rowFootRead())
                .titleStyle(ExcelStyleConfig.buildByExcelStyle(excelClass.titleStyle()))
                .cellStyleRead(excelClass.rowStyleRead())
                .titleFormat(excelClass.titleFormat())
                .itemList(excelFieldConfigList)
                .build();
    }


    private List<Field> listAllDeclaredField(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();

        Set<String> fieldNameSet = new TreeSet<>();

        Class<?> superClazz = clazz;
        while (superClazz != null) {
            Field[] fields = listDeclaredField(superClazz);

            for (int i = fields.length - 1; i >= 0; i--) {
                Field field = fields[i];
                String fieldName = field.getName();
                if (fieldNameSet.contains(fieldName)) {
                    continue;
                }
                fieldNameSet.add(fieldName);
                fieldList.add(0, field);
            }

            superClazz = superClazz.getSuperclass();
        }
        return fieldList;
    }

    private Field[] listDeclaredField(Class<?> clazz) {
        if (clazz.equals(Object.class)) {
            return new Field[0];
        }
        return clazz.getDeclaredFields();
    }


    private ExcelFieldConfig convert(Field field) {
        ExcelField excelField = field.getAnnotation(ExcelField.class);
        if (excelField == null) {
            return null;
        }

        String propertyName = field.getName();
        Class<?> javaType = field.getType();

        return ExcelFieldConfig
                .builder()
                .title(excelField.title())
                .propertyName(propertyName)
                .javaType(javaType)
                .excelType(excelField.excelType())
                .cellHandler(excelField.cellHandler())
                .colspan(excelField.colspan())
                .headFormat(excelField.headFormat())
                .dataFormat(excelField.dataFormat())
                .headStyle(ExcelStyleConfig.buildByExcelStyle(excelField.headStyle()))
                .dataStyle(ExcelStyleConfig.buildByExcelStyle(excelField.dataStyle()))
                .build();
    }


}
