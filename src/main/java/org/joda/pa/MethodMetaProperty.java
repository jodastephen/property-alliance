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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * A {@link MetaProperty} which reflects on {@link Method}s
 * (provided during construction) to get/set values and access annotations.
 * 
 * @param <B> the type associated with the meta-bean that defines this meta-property 
 * @param <P> the type of the property content
 */
class MethodMetaProperty<B, P> extends AbstractMetaProperty<B, P> {

    private final Method getValue;
    private final Method setValue;

    protected MethodMetaProperty(
            MetaBean<B> metaBean, String name, Class<P> propertyTypeToken,
            boolean derived, boolean buildable,
            Method getValue, Method setValue) {

        super(metaBean, name, propertyTypeToken,
                derived, buildable, isReadable(getValue), isMutable(setValue));

        this.getValue = getValue;
        this.setValue = setValue;
        makeAccessible(getValue, setValue);
    }

    //-----------------------------------------------------------------------
    private static boolean isReadable(Method getValue) {
        return getValue != null;
    }

    private static boolean isMutable(Method setValue) {
        return setValue != null;
    }

    //-----------------------------------------------------------------------
    private static void makeAccessible(Method getValue, Method setValue) {
        if (getValue != null) {
            getValue.setAccessible(true);
        }
        if (setValue != null) {
            setValue.setAccessible(true);
        }
    }

    @Override
    public Stream<Annotation> annotations() {
        Stream<Annotation> annotationsOnGetValue =
                Stream.of(getValue.getAnnotations());
        Stream<Annotation> annotationsOnSetValue =
                Stream.of(setValue.getAnnotations());
        return Stream.concat(annotationsOnGetValue, annotationsOnSetValue);
    }

    @Override
    public <A extends Annotation> Stream<A> annotations(Class<A> annotationType) {
        A annotationOnGetValue = getValue.getAnnotation(annotationType);
        A annotationOnSetValue = setValue.getAnnotation(annotationType);
        return Stream
                .of(annotationOnGetValue, annotationOnSetValue)
                .filter(element -> element != null);
    }

    @Override
    protected P getFromBean(Object bean) {
        Object untypedValue = invoke(bean, getValue, MethodPurpose.GET);
        return propertyType().cast(untypedValue);
    }

    @Override
    protected void setToBean(Object bean, P value) {
        invoke(bean, setValue, MethodPurpose.SET, value);
    }

    private static Object invoke(
            Object bean, Method method, MethodPurpose purpose, Object... args) {

        try {
            return method.invoke(bean, args);
        } catch (IllegalAccessException ex) {
            // because the backing field is made accessible during construction,
            // this exception should never occur
            String message = "The method used by this meta-property to "
                    + purpose.formated()
                    + " values is not accessible.";
            throw new RuntimeException(message, ex);
        } catch (IllegalArgumentException ex) {
            // this exception can only occur if the bean does not have the correct type;
            // this was already checked by the superclass so this should not happen
            String message = "The specified bean does not declare the method "
                    + "used by this meta-property to "
                    + purpose.formated()
                    + " values: "
                    + ex.getMessage();
            throw new ClassCastException(message);
        } catch (InvocationTargetException ex) {
            // the caught exception contains the actual exception thrown by the
            // method invocation; rethrow that exception if possible (i.e. it
            // is unchecked) or throw a new RuntimeException
            Throwable thrownByMethod = ex.getCause();
            if (thrownByMethod instanceof RuntimeException) {
                throw (RuntimeException) thrownByMethod;
            } else {
                String message = "Invocating the method used by this meta-property to "
                        + purpose.formated()
                        + " values caused an exception. "
                        + "That exception is the cause for this exception "
                        + "(i.e. can be accessed by calling 'getCause()').";
                throw new RuntimeException(message, thrownByMethod);
            }
        }
    }

    /**
     * Indicates which purpose the method passed to invoke has.
     */
    private static enum MethodPurpose {
        GET,
        SET;

        public String formated() {
            switch (this) {
                case GET:
                    return "get";
                case SET:
                    return "set";
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

}
