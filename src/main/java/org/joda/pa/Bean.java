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

/**
 * Marks an object as a "bean" capable of providing properties.
 * <p>
 * An implementation of this interface may be any Java type representing any kind of data.
 * The classic example is a mutable Java Bean with getters and setters.
 * However the API is general enough to support an implementation based on a {@code Map}
 * or one that is immutable.
 * <p>
 * It is not a requirement that all beans implement this interface.
 * If a type does not implement the interface then reflection will be used to examine
 * getters and setters as per the original Java Bean specification.
 * 
 * @param <B> the type of the bean
 */
public interface Bean<B> {

    /**
     * Gets the meta-bean representing the parts of the bean that are
     * common across all instances, such as the set of meta-properties.
     * <p>
     * The meta-bean can be thought of as the equivalent of {@link Class} but for beans.
     * 
     * @return the meta-bean, not null
     */
    MetaBean<B> metaBean();

}
