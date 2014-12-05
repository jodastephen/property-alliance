/*
 *  Copyright 2014-present Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.joda.pa;

import java.util.Map;

/**
 * A builder for a bean, providing a standardised way to create it.
 * <p>
 * Different kinds of bean have different methods of construction, some
 * via constructors, some via factory methods, some via dedicated builders.
 * This interface abstracts the method of construction, allowing tools
 * to create the bean regardless of the underlying method.
 * <p>
 * A {@code BeanBuilder} is obtained from a {@link MetaBean}.
 * 
 * @param <T>  the type of the bean to be created
 */
public interface BeanBuilder<T> {

    /**
     * Gets the value of a single property previously added to the builder.
     * <p>
     * A builder is expected to provide read access to values that have previously been added.
     * 
     * @param property  the property to query, not null
     * @return the previously set value, null if none
     * @throws RuntimeException if the property or builder is invalid
     */
    Object get(MetaProperty<?> property);

    /**
     * Sets the value of a single property into the builder.
     * <p>
     * This will normally behave as per {@link Map#put(Object, Object)}, however
     * it may not and as a general rule callers should only set each property once.
     * 
     * @param property  the property to set, not null
     * @param value  the property value, may be null
     * @return {@code this}, for chaining, not null
     * @throws RuntimeException if the property or builder is invalid
     */
    BeanBuilder<T> set(MetaProperty<?> property, Object value);

    /**
     * Builds the bean from the properties previously set into the builder.
     * <p>
     * Once this method has been called, the builder is in an invalid state.
     * The effect of further method calls is undetermined.
     * 
     * @return the created bean, not null
     * @throws RuntimeException if the builder is invalid
     */
    T build();

}
