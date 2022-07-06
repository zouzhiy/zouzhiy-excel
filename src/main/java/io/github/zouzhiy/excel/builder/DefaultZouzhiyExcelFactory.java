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
package io.github.zouzhiy.excel.builder;

import io.github.zouzhiy.excel.metadata.Configuration;
import io.github.zouzhiy.excel.metadata.config.ExcelClassConfig;
import io.github.zouzhiy.excel.metadata.parameter.WorkbookParameter;
import io.github.zouzhiy.excel.read.WorkbookRead;
import io.github.zouzhiy.excel.read.defaults.DefaultWorkbookRead;
import io.github.zouzhiy.excel.write.WorkbookWrite;
import io.github.zouzhiy.excel.write.defaults.DefaultWorkbookWrite;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public class DefaultZouzhiyExcelFactory implements ZouzhiyExcelFactory {

    private final Configuration configuration;

    public DefaultZouzhiyExcelFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public WorkbookRead getWorkbookRead(WorkbookParameter workbookParameter, Class<?> clazz) {
        return new DefaultWorkbookRead(configuration, workbookParameter, configuration.getExcelAnnotationParse().findForClass(clazz));
    }

    @Override
    public WorkbookRead getWorkbookRead(WorkbookParameter workbookParameter, ExcelClassConfig excelClassConfig) {
        return new DefaultWorkbookRead(configuration, workbookParameter, excelClassConfig);
    }

    @Override
    public WorkbookWrite getWorkbookWrite(WorkbookParameter workbookParameter, Class<?> clazz) {
        return new DefaultWorkbookWrite(configuration, workbookParameter, configuration.getExcelAnnotationParse().findForClass(clazz));
    }

    @Override
    public WorkbookWrite getWorkbookWrite(WorkbookParameter workbookParameter, ExcelClassConfig excelClassConfig) {
        return new DefaultWorkbookWrite(configuration, workbookParameter, excelClassConfig);
    }

    @Override
    public WorkbookReadBuilder read() {
        return new WorkbookReadBuilder(this);
    }

    @Override
    public WorkbookReadBuilder read(File inputFile) {
        return new WorkbookReadBuilder(this).input(inputFile);
    }

    @Override
    public WorkbookReadBuilder read(InputStream inputStream) {
        return new WorkbookReadBuilder(this).input(inputStream);
    }

    @Override
    public WorkbookWriteBuilder write() {
        return new WorkbookWriteBuilder(this);
    }

    @Override
    public WorkbookWriteBuilder write(File outputFile) {
        return new WorkbookWriteBuilder(this).output(outputFile);
    }

    @Override
    public WorkbookWriteBuilder write(OutputStream outputStream) {
        return new WorkbookWriteBuilder(this).output(outputStream);
    }
}
