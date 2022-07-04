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
package io.github.zouzhiy.excel.handler.list;

import io.github.zouzhiy.excel.exceptions.ExcelException;
import io.github.zouzhiy.excel.handler.ListCellHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zouzhiy
 * @since 2022/7/4
 */
public abstract class AbstractListHandler<E> implements ListCellHandler<E> {

    private final Class<List<E>> javaType;

    private final Class<E> itemType;

    public AbstractListHandler() {
        this.javaType = this.getListType();
        this.itemType = this.getSuperclassTypeParameter(this.getClass());
    }

    @Override
    public final Class<List<E>> getJavaType() {
        return javaType;
    }

    @Override
    public final Class<E> getItemType() {
        return itemType;
    }

    private Class<List<E>> getListType() {
        //noinspection MismatchedQueryAndUpdateOfCollection
        List<E> arrayList = new ArrayList<>(0);
        //noinspection unchecked
        Class<List<E>> arrayListClass = (Class<List<E>>) arrayList.getClass();
        Class<?>[] interfaces = arrayListClass.getInterfaces();
        int index = 0;
        for (int i = 0; i < interfaces.length; i++) {
            if (interfaces[i].equals(List.class)) {
                index = i;
                break;
            }
        }
        //noinspection unchecked
        return (Class<List<E>>) interfaces[index];
    }

    private Class<E> getSuperclassTypeParameter(Class<?> clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            // try to climb up the hierarchy until meet something useful
            if (AbstractListHandler.class != genericSuperclass) {
                return getSuperclassTypeParameter(clazz.getSuperclass());
            }

            throw new ExcelException("'" + getClass() + "' extends TypeReference but misses the type parameter. "
                    + "Remove the extension or add a type parameter to it.");
        }

        Type rawType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        // TODO remove this when Reflector is fixed to return Types
        if (rawType instanceof ParameterizedType) {
            rawType = ((ParameterizedType) rawType).getRawType();
        }

        //noinspection unchecked
        return (Class<E>) rawType;
    }
}
