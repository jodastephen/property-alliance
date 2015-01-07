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
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Field;

import org.joda.pa.TestBean.FieldAnnotation;
import org.testng.annotations.Test;

/**
 * Tests the class {@link FieldMetaProperty}.
 */
@SuppressWarnings("javadoc")
public class FieldMetaPropertyTest extends
        AbstractFieldNameBasedMetaPropertyTest {

    @Test
    public final void annotations_fieldWithAnnotation_reportsAnnotation()
            throws Exception {
        MetaProperty<?, ?> annotatedMetaProperty = createIntegerMetaProperty();

        // report exactly one annotation
        long annotationsCount = annotatedMetaProperty
                .annotations()
                .count();
        assertEquals(annotationsCount, 1);

        // report an annotation of the correct type
        boolean annotationOfCorrectType = annotatedMetaProperty
                .annotations()
                .allMatch(FieldAnnotation.class::isInstance);
        assertTrue(annotationOfCorrectType);
    }

    @Test
    public final void annotationsFiltered_fieldWithAnnotation_reportsAnnotation()
            throws Exception {
        MetaProperty<?, ?> annotatedMetaProperty = createIntegerMetaProperty();

        // report exactly one annotation
        long annotationsCount = annotatedMetaProperty
                .annotations(FieldAnnotation.class)
                .count();
        assertEquals(annotationsCount, 1);
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
        Field backingField =
                FieldBackedTestBean.class.getDeclaredField(fieldName);

        return new FieldMetaProperty<>(
                notNullMetaBean, name, typeToken,
                derived, buildable, readable, mutable,
                backingField);
    }
}
