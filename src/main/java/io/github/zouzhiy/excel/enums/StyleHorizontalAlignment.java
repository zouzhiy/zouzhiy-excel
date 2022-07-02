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
package io.github.zouzhiy.excel.enums;

import lombok.Getter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * @author zouzhiy
 * @since 2022/7/2
 */
public enum StyleHorizontalAlignment {
    /**
     * 水平对齐方式
     */
    NONE(null),
    GENERAL(HorizontalAlignment.GENERAL),
    LEFT(HorizontalAlignment.LEFT),
    CENTER(HorizontalAlignment.CENTER),
    RIGHT(HorizontalAlignment.RIGHT),
    FILL(HorizontalAlignment.FILL),
    JUSTIFY(HorizontalAlignment.JUSTIFY),
    CENTER_SELECTION(HorizontalAlignment.CENTER_SELECTION),
    DISTRIBUTED(HorizontalAlignment.DISTRIBUTED);

    @Getter
    private final HorizontalAlignment value;

    StyleHorizontalAlignment(HorizontalAlignment value) {
        this.value = value;
    }
}
