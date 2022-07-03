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
package io.github.zouzhiy.excel.metadata.parameter;

import io.github.zouzhiy.excel.callback.SheetReadConsumer;
import io.github.zouzhiy.excel.callback.SheetWriteConsumer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SheetParameter {

    private Integer sheetIndex;

    private String sheetName;

    private String title;

    private Integer titleRowStartIndex;

    private Integer titleColumnStartIndex;

    private Integer headRowStartIndex;

    private Integer headColumnStartIndex;

    private Integer dataRowStartIndex;

    private Integer dataColumnStartIndex;

    private List<SheetReadConsumer<?>> sheetReadConsumerList;

    private List<SheetWriteConsumer<?>> sheetWriteConsumerList;

    private SheetParameter(Integer sheetIndex, String sheetName
            , String title, Integer titleRowStartIndex, Integer titleColumnStartIndex
            , Integer headRowStartIndex, Integer headColumnStartIndex
            , Integer dataRowStartIndex, Integer dataColumnStartIndex
            , List<SheetReadConsumer<?>> sheetReadConsumerList
            , List<SheetWriteConsumer<?>> sheetWriteConsumerList) {
        this.sheetIndex = sheetIndex;
        this.sheetName = sheetName;
        this.title = title;
        this.titleRowStartIndex = titleRowStartIndex;
        this.titleColumnStartIndex = titleColumnStartIndex;
        this.headRowStartIndex = headRowStartIndex;
        this.headColumnStartIndex = headColumnStartIndex;
        this.dataRowStartIndex = dataRowStartIndex;
        this.dataColumnStartIndex = dataColumnStartIndex;
        this.sheetReadConsumerList = sheetReadConsumerList;
        this.sheetWriteConsumerList = sheetWriteConsumerList;
    }

    public static SheetParameter empty() {
        return new SheetParameterBuilder().sheet(0).dataRowStartIndex(0).dataColumnStartIndex(0).build();
    }

    public static SheetParameterBuilder builder() {
        return new SheetParameterBuilder();
    }

    public static class SheetParameterBuilder {

        private Integer sheetIndex;

        private String sheetName;

        private String title;

        private Integer titleRowStartIndex;

        private Integer titleColumnStartIndex;

        private Integer headRowStartIndex;

        private Integer headColumnStartIndex;

        private Integer dataRowStartIndex;

        private Integer dataColumnStartIndex;

        private List<SheetReadConsumer<?>> sheetReadConsumerList;

        private List<SheetWriteConsumer<?>> sheetWriteConsumerList;

        public SheetParameterBuilder sheet(String sheetName) {
            this.sheetName = sheetName;
            return this;
        }

        public SheetParameterBuilder sheet(Integer sheetIndex) {
            this.sheetIndex = sheetIndex;
            return this;
        }

        public SheetParameterBuilder title(String title) {
            this.title = title;
            return this;
        }

        public SheetParameterBuilder titleRowStartIndex(Integer titleRowStartIndex) {
            this.titleRowStartIndex = titleRowStartIndex;
            return this;
        }

        public SheetParameterBuilder titleColumnStartIndex(Integer titleColumnStartIndex) {
            this.titleColumnStartIndex = titleColumnStartIndex;
            return this;
        }

        public SheetParameterBuilder headRowStartIndex(Integer headRowStartIndex) {
            this.headRowStartIndex = headRowStartIndex;
            return this;
        }

        public SheetParameterBuilder headColumnStartIndex(Integer headColumnStartIndex) {
            this.headColumnStartIndex = headColumnStartIndex;
            return this;
        }

        public SheetParameterBuilder dataRowStartIndex(Integer dataRowStartIndex) {
            this.dataRowStartIndex = dataRowStartIndex;
            return this;
        }

        public SheetParameterBuilder dataColumnStartIndex(Integer dataColumnStartIndex) {
            this.dataColumnStartIndex = dataColumnStartIndex;
            return this;
        }


        public SheetParameterBuilder sheetReadConsumer(SheetReadConsumer<?> sheetReadConsumer) {
            if (sheetReadConsumerList == null) {
                sheetReadConsumerList = new ArrayList<>();
            }
            if (!sheetReadConsumerList.contains(sheetReadConsumer)) {
                sheetReadConsumerList.add(sheetReadConsumer);
            }
            return this;
        }

        public SheetParameterBuilder sheetWriteConsumer(SheetWriteConsumer<?> sheetWriteConsumer) {
            if (sheetWriteConsumerList == null) {
                sheetWriteConsumerList = new ArrayList<>();
            }
            if (!sheetWriteConsumerList.contains(sheetWriteConsumer)) {
                sheetWriteConsumerList.add(sheetWriteConsumer);
            }
            return this;
        }

        public SheetParameter build() {
            return new SheetParameter(sheetIndex, sheetName, title
                    , this.getTitleRowStartIndex()
                    , this.getTitleColumnStartIndex()
                    , this.getHeadRowStartIndex()
                    , this.getHeadColumnStartIndex()
                    , this.getDataRowStartIndex()
                    , this.getDataColumnStartIndex()
                    , this.getSheetReadConsumerList()
                    , this.getSheetWriteConsumerList());
        }

        private Integer getTitleRowStartIndex() {
            if (titleRowStartIndex != null) {
                return titleRowStartIndex;
            } else if (this.getHeadRowStartIndex() >= 1) {
                return 0;
            } else {
                return -1;
            }
        }

        private Integer getTitleColumnStartIndex() {
            return titleColumnStartIndex == null ? 0 : titleColumnStartIndex;
        }

        private Integer getHeadRowStartIndex() {
            if (headRowStartIndex != null) {
                return headRowStartIndex;
            } else if (this.getDataRowStartIndex() >= 1) {
                return this.getDataRowStartIndex() - 1;
            } else {
                return -1;
            }
        }

        private Integer getHeadColumnStartIndex() {
            return headColumnStartIndex == null ? 0 : headColumnStartIndex;
        }

        private int getDataRowStartIndex() {
            return dataRowStartIndex == null ? 0 : dataRowStartIndex;
        }

        private int getDataColumnStartIndex() {
            return dataColumnStartIndex == null ? 0 : dataColumnStartIndex;
        }


        private List<SheetReadConsumer<?>> getSheetReadConsumerList() {
            return sheetReadConsumerList == null ? Collections.emptyList() : sheetReadConsumerList;
        }

        private List<SheetWriteConsumer<?>> getSheetWriteConsumerList() {
            return sheetWriteConsumerList == null ? Collections.emptyList() : sheetWriteConsumerList;
        }
    }

}
