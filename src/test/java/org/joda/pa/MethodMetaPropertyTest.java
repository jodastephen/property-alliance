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

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

import java.lang.reflect.Method;

import org.joda.pa.TestBean.GetAnnotation;
import org.joda.pa.TestBean.SetAnnotation;
import org.testng.annotations.Test;

/**
 * Tests the class {@link FieldMetaProperty}.
 */
@SuppressWarnings("javadoc")
public class MethodMetaPropertyTest extends
        AbstractFieldNameBasedMetaPropertyTest {

    @Test
    public final void annotations_methodsWithTwoAnnotations_reportsAnnotations()
            throws Exception {
        MetaProperty<?, ?> annotatedMetaProperty = createIntegerMetaProperty();

        // report exactly two annotation
        long annotationsCount = annotatedMetaProperty
                .annotations()
                .count();
        assertEquals(annotationsCount, 2);

        // report annotations of the correct type
        long annotationsCountOnGet = annotatedMetaProperty
                .annotations()
                .filter(GetAnnotation.class::isInstance)
                .count();
        assertEquals(annotationsCountOnGet, 1);
        long annotationsCountOnSet = annotatedMetaProperty
                .annotations()
                .filter(SetAnnotation.class::isInstance)
                .count();
        assertEquals(annotationsCountOnSet, 1);
    }

    @Test
    public final void annotationsFilter_methodsWithTwoAnnotations_reportsAnnotation()
            throws Exception {
        MetaProperty<?, ?> annotatedMetaProperty = createIntegerMetaProperty();

        // report exactly one annotation on get
        long annotationsCountOnGet = annotatedMetaProperty
                .annotations(GetAnnotation.class)
                .count();
        assertEquals(annotationsCountOnGet, 1);

        // report exactly one annotation on set
        long annotationsCountOnSet = annotatedMetaProperty
                .annotations(SetAnnotation.class)
                .count();
        assertEquals(annotationsCountOnSet, 1);
    }

    // implementation of 'AbstractFieldNameBasedMetaPropertyTest' -------------

    @Override
    protected <B, P> MetaProperty<B, P> createMetaProperty(
            MetaBean<B> metaBean, String name, Class<P> typeToken,
            boolean derived, boolean buildable,
            boolean readable, boolean mutable,
            String fieldName)
            throws Exception {

        MetaBean<B> notNullMetaBean = metaBean;
        if (notNullMetaBean == null) {
            notNullMetaBean = mock(MetaBean.class);
            doReturn(TestBean.class).when(notNullMetaBean).beanType();
        }

        Method getValue = createGetMethod(readable, fieldName);
        Method setValue = createSetMethod(mutable, fieldName, typeToken);

        return new MethodMetaProperty<>(
                notNullMetaBean, name, typeToken,
                derived, buildable,
                getValue, setValue);
    }

    private static Method createGetMethod(boolean readable, String fieldName)
            throws NoSuchMethodException {
        if (!readable) {
            return null;
        }

        String getValueName = createMethodName("get", fieldName);
        return FieldBackedTestBean.class.getMethod(getValueName);
    }

    private static <T> Method createSetMethod(
            boolean mutable, String fieldName, Class<T> valueClass)
            throws NoSuchMethodException {

        if (!mutable) {
            return null;
        }

        String setValueName = createMethodName("set", fieldName);
        return FieldBackedTestBean.class.getMethod(setValueName, valueClass);
    }

    private static String createMethodName(String prefix, String fieldName) {
        return prefix
                + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
    }

}
