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
import java.lang.reflect.Field;
import java.util.stream.Stream;

/**
 * A {@link MetaProperty} which reflects on a {@link Field}
 * (provided during construction) to get/set values and access annotations.
 * 
 * @param <B> the type associated with the meta-bean that defines this meta-property 
 * @param <P> the type of the property content
 */
class FieldMetaProperty<B, P> extends AbstractMetaProperty<B, P> {

    private final Field backingField;

    /**
     * This constructor does not check these arguments.
     * It relies on the calling builder to do so.
     */
    FieldMetaProperty(
            MetaBean<B> metaBean, String name, Class<P> propertyTypeToken,
            boolean derived, boolean buildable,
            boolean readable, boolean mutable,
            Field backingField) {

        super(metaBean, name, propertyTypeToken,
                derived, buildable, readable, mutable);

        this.backingField = backingField;
        makeAccessible(backingField);
    }

    //-----------------------------------------------------------------------
    private static void makeAccessible(Field backingField) {
        backingField.setAccessible(true);
    }

    //-----------------------------------------------------------------------
    @Override
    public Stream<Annotation> annotations() {
        return Stream.of(backingField.getAnnotations());
    }

    @Override
    public <A extends Annotation> Stream<A> annotations(Class<A> annotationType) {
        A annotation = backingField.getAnnotation(annotationType);
        if (annotation == null) {
            return Stream.empty();
        } else {
            return Stream.of(annotation);
        }
    }

    @Override
    protected P getFromBean(Object bean) {
        try {
            Object untypedValue = backingField.get(bean);
            return propertyType().cast(untypedValue);
        } catch (IllegalAccessException ex) {
            // because the backing field is made accessible during construction,
            // this exception should never occur
            String message = "The field backing this meta-property is not accessible.";
            throw new RuntimeException(message, ex);
        } catch (IllegalArgumentException ex) {
            // this exception can only occur if the bean does not have the correct type;
            // this was already checked by the superclass so this should not happen
            String message = "The specified bean "
                    + "does not declare the field backing this meta-property: "
                    + ex.getMessage();
            throw new ClassCastException(message);
        }
    }

    @Override
    protected void setToBean(Object bean, P value) {
        try {
            backingField.set(bean, value);
        } catch (IllegalAccessException ex) {
            // because the backing field is made accessible during construction,
            // this exception should never occur
            String message = "The field backing this meta-property is not accessible.";
            throw new RuntimeException(message, ex);
        } catch (IllegalArgumentException ex) {
            // this exception can occur for two reasons:
            //  - if the bean does not have the correct type;
            //      (was already checked by the superclass so this should not happen)
            //  - the value's type does not match the field's type
            //      (was checked by the builder during construction so this should not happen)
            String message = "The specified bean "
                    + "does not declare the field backing this meta-property "
                    + "or the value is not assignable to the backing field "
                    + "because the types do not match: "
                    + ex.getMessage();
            throw new ClassCastException(message);
        }
    }
}
