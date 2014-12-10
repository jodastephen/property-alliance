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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.stream.Stream;

/**
 * A meta-property defines aspects of a property, such as the type and name.
 * <p>
 * A property provides access to a single piece of information on a bean.
 * <p>
 * A {@code MetaProperty} is obtained from a {@link MetaBean}.
 * 
 * @param <P>  the type of the property content
 */
public interface MetaProperty<P> {

    /**
     * Gets the meta-bean that defines this meta-property.
     * <p>
     * Each meta-property is fully owned by a single meta-bean.
     * 
     * @return the meta-bean, not null
     */
    MetaBean metaBean();

    /**
     * Gets the property name.
     * <p>
     * The property name is intended to follow lowerCamelCase naming.
     * A traditional Java Bean style {@code getFoo()} and {@code setFoo()}
     * will lead to a property name of {@code foo}.
     * <p>
     * It is recommended to use property names matching the regular expression
     * '{@code [a-z][A-Za-z0-9_]*}'. Doing so allows the property name to be
     * used in other contexts, such as JSON or XML data formats.
     * 
     * @return the name of the property, not empty
     */
    String name();

    /**
     * Gets the type that declares the property, represented as a {@code Class}.
     * <p>
     * This is the type of the bean where the property is declared.
     * <p>
     * The default implementation returns the result of {@code metaBean().beanType()}.
     * 
     * @return the type declaring the property, not null
     */
    default Class<?> declaringClass() {
        return metaBean().beanType();
    }

    /**
     * Gets the type of the property represented as a {@code Class}.
     * <p>
     * This is the type of the property.
     * For example, the surname of a person would typically be a {@code String}.
     * 
     * @return the type of the property, not null
     */
    Class<P> propertyType();

    /**
     * Gets the generic types of the property.
     * <p>
     * This provides access to the generic type of the property.
     * This is primarily useful for determining the generic parameters of collections.
     * <p>
     * The default implementation returns the result of {@link #propertyType()}.
     * 
     * @return the full generic type of the property, unmodifiable, not null
     *      (question by nipa@codefx.org: In which sense is this unmodifiable?)
     */
    default Type propertyGenericType() {
        return propertyType();
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the stream of annotations associated with this property.
     * <p>
     * The annotations are queried from the property.
     * This is typically accomplished by querying the annotations of an underlying
     * {@link Field} or {@link Method} however any strategy is permitted.
     * <p>
     * If the implementation has a mutable set of annotations, then the result of
     * this method must stream over those annotations in existence when this method
     * is called to avoid concurrency issues.
     * 
     * @return the stream of annotations on the property, not null
     */
    Stream<Annotation> annotations();

    /**
     * Gets the stream of annotations associated with this property by type.
     * <p>
     * The annotations are queried from the property, filtering by type.
     * This is typically accomplished by querying the annotations of an underlying
     * {@link Field} or {@link Method} however any strategy is permitted.
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
    default <A extends Annotation> Stream<A> annotation(Class<A> annotationType) {
        return annotations()
                .filter(a -> a.annotationType() == annotationType)
                .map(a -> annotationType.cast(a));
    }

    //-----------------------------------------------------------------------
    /**
     * Checks whether this property is derived or not.
     * <p>
     * A derived property derives its value from one or more other properties.
     * As such, the value of a derived property is not part of the essential state of the bean.
     * Some tools, such as a serialization framework, may choose to omit derived properties
     * as they can be recreated by the client.
     * <p>
     * The default implementation returns false.
     * 
     * @return true if this property is derived
     */
    default boolean isDerived() {
        return false;
    }

    /**
     * Checks whether this property is buildable or not.
     * <p>
     * A buildable property can be passed into the {@link BeanBuilder} of the meta-bean.
     * If this method returns true then {@code BeanBuilder.set()} must succeed given valid arguments.
     * If this method returns false then {@code BeanBuilder.set()} must throw {@link UnsupportedOperationException}.
     * 
     * @return true if this property is buildable
     */
    boolean isBuildable();

    /**
     * Checks whether this property is mutable or not.
     * <p>
     * A mutable property allows the {@link #set(Object, Object)} method to be invoked.
     * If this method returns true then {@code set()} must succeed given valid arguments.
     * If this method returns false then {@code set()} must throw {@link UnsupportedOperationException}.
     * 
     * @return true if this property is mutable
     */
    boolean isMutable();

    /**
     * Gets the value of the property for the specified bean.
     * <p>
     * For a standard Java Bean, this is equivalent to calling {@code getFoo()} on the bean.
     * Alternate implementations may perform any logic to obtain the value.
     * 
     * @param bean  the bean to query, not null
     * @return the value of the property on the specified bean, may be null
     * @throws ClassCastException if the bean is of an incorrect type
     * @throws UnsupportedOperationException if the property is write-only
     */
    P get(Object bean);

    /**
     * Sets the value of the property on the specified bean.
     * <p>
     * The value must be of the correct type for the property.
     * For a standard Java Bean, this is equivalent to calling {@code setFoo()} on the bean.
     * Alternate implementations may perform any logic to change the value.
     * 
     * @param bean  the bean to update, not null
     * @param value  the value to set into the property on the specified bean, may be null
     * @throws ClassCastException if the bean is of an incorrect type or
     *  if the value is of an invalid type for the property
     * @throws UnsupportedOperationException if the property is read-only
     * @throws RuntimeException if the value is rejected by the property
     */
    void set(Object bean, Object value);

    //-----------------------------------------------------------------------
    /**
     * Checks if this meta-property equals another.
     * <p>
     * Two properties are equal if they provide access to the same value.
     * This is typically accomplished by comparing the property name and declaring class.
     * The value of the property must not be used.
     * 
     * @param obj  the other meta-property, null returns false
     * @return true if equal
     */
    @Override
    boolean equals(Object obj);

    /**
     * Returns a suitable hash code.
     * <p>
     * Returns a hash code compatible with the definition of {@link #equals(Object)}.
     * 
     * @return the hash code
     */
    @Override
    int hashCode();

}
