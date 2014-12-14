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

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A meta-bean, defining those aspects of a bean which are not specific
 * to a particular instance, such as the type and set of meta-properties.
 * <p>
 * This interface can be thought of as the equivalent of {@link Class} but for beans.
 * It provides access to three main elements:
 * <p>
 * <ul>
 * <li>a {@code Stream} of properties, allowing the state of the bean to be queried
 * <li>a {@code Stream} of annotations, allowing the annotations of the bean to be queried
 * <li>a {@code BeanBuilder} that allows a new bean to be created
 * </ul>
 * <p>
 * Implementations of this interface will typically represent the properties and annotations
 * of a fixed concrete class. However, implementations are permitted to be dynamic,
 * creating properties and/or annotations on demand.
 */
public interface MetaBean {

    /**
     * Obtains a meta-bean for a {@code Class}.
     * <p>
     * A {@code MetaBean} provides an abstraction on top of a {@code Class}.
     * 
     * @return the meta-bean associated with the class, not null
     */
    static MetaBean of(Class<?> cls) {
        return null;
    }

    //-------------------------------------------------------------------------
    /**
     * Gets the type of the bean, represented as a {@code Class}.
     * <p>
     * A {@code MetaBean} can be thought of as the equivalent of {@link Class} but for beans.
     * This method allows the actual {@code Class} instance of the bean to be obtained.
     * 
     * @return the type of the bean, not null
     */
    Class<?> beanType();

    /**
     * Checks whether this bean is buildable or not.
     * <p>
     * A buildable bean can be constructed using {@link #beanBuilder()}.
     * If this method returns true then {@code beanBuilder()} must return a valid builder.
     * If this method returns false then {@code beanBuilder()} must throw {@link UnsupportedOperationException}.
     * 
     * @return true if this bean is buildable
     */
    boolean isBuildable();

    /**
     * Creates a bean builder that can be used to create an instance of this bean.
     * <p>
     * The builder is used in two main ways.
     * The first is to allow immutable beans to be constructed.
     * The second is to enable automated tools like serialization/deserialization.
     * <p>
     * The builder can be thought of as a {@code Map} of {@link MetaProperty} to value.
     * Note that the implementation is not necessarily an actual map.
     * 
     * @return the bean builder, not null
     * @throws UnsupportedOperationException if the bean cannot be created
     */
    BeanBuilder<?> beanBuilder();

    //-----------------------------------------------------------------------
    /**
     * Gets the stream of properties associated with this bean.
     * <p>
     * The properties are queried from the bean.
     * This is typically accomplished by querying the properties of an underlying
     * {@link Class} however any strategy is permitted.
     * <p>
     * If the implementation has a mutable set of properties, then the result of
     * this method must stream over those properties in existence when this method
     * is called to avoid concurrency issues.
     * 
     * @return the stream of properties on the bean, not null
     */
    Stream<MetaProperty<?>> metaProperties();

    /**
     * Gets a single property by name.
     * <p>
     * The properties are queried from the bean.
     * This is typically accomplished by querying the properties of an underlying
     * {@link Class} however any strategy is permitted.
     * This method returns the property with the specified name,
     * or optional empty if the name is not known.
     * <p>
     * A dynamic implementation of this interface may create the property on demand
     * when this method is called.
     * <p>
     * The default implementation applies a filter to the result of {@link #metaProperties()}
     * and returns the first matching property.
     * 
     * @param propertyName  the property name to retrieve, null returns optional empty
     * @return the property, or optional empty if no such property
     */
    default Optional<MetaProperty<?>> metaProperty(String propertyName) {
        return metaProperties()
                .filter(mp -> mp.name().equals(propertyName))
                .findFirst();
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the stream of annotations associated with this bean.
     * <p>
     * The annotations are queried from the bean.
     * This is typically accomplished by querying the annotations of an underlying
     * {@link Class} however any strategy is permitted.
     * <p>
     * If the implementation has a mutable set of annotations, then the result of
     * this method must stream over those annotations in existence when this method
     * is called to avoid concurrency issues.
     * 
     * @return the stream of annotations on the property, not null
     */
    Stream<Annotation> annotations();

    /**
     * Gets the stream of annotations associated with this bean by type.
     * <p>
     * The annotations are queried from the bean.
     * This is typically accomplished by querying the annotations of an underlying
     * {@link Class} however any strategy is permitted.
     * <p>
     * Call {@link Stream#findFirst()} on the result to obtain the first matching
     * annotation if the type is not {@link Repeatable}.
     * <p>
     * If the implementation has a mutable set of annotations, then the result of
     * this method must stream over those annotations in existence when this method
     * is called to avoid concurrency issues.
     * <p>
     * The default implementation applies a filter and cast to the result of {@link #annotations()}.
     * 
     * @param <A>  the annotation type
     * @param annotationType  the annotation type to find, not null
     * @return the annotations matching the specified type, not null
     */
    default <A extends Annotation> Stream<A> annotations(Class<A> annotationType) {
        return annotations()
                .filter(a -> a.annotationType() == annotationType)
                .map(a -> annotationType.cast(a));
    }

}
