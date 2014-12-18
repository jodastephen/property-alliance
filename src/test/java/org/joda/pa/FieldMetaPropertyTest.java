package org.joda.pa;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.stream.Stream;

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
        MetaProperty<?> annotatedMetaProperty = createIntegerMetaProperty();

        // report exactly one annotation
        Stream<Annotation> annotations = annotatedMetaProperty.annotations();
        assertEquals(annotations.count(), 1);

        // report an annotation of the correct type
        long annotationsOnField = annotatedMetaProperty
                .annotations()
                .filter(FieldAnnotation.class::isInstance)
                .count();
        assertEquals(annotationsOnField, 1);
    }

    @Test
    public final void annotationsFiltered_fieldWithAnnotation_reportsAnnotation()
            throws Exception {
        MetaProperty<?> annotatedMetaProperty = createIntegerMetaProperty();

        // report exactly one annotation
        Stream<? extends Annotation> annotationsOnField =
                annotatedMetaProperty.annotations(FieldAnnotation.class);
        assertEquals(annotationsOnField.count(), 1);
    }

    // implementation of 'AbstractFieldNameBasedMetaPropertyTest' -------------

    @Override
    protected <P> MetaProperty<P> createMetaProperty(
            MetaBean metaBean, String name, Class<P> typeToken,
            boolean derived, boolean buildable,
            boolean readable, boolean mutable,
            String fieldName)
            throws Exception {

        MetaBean notNullMetaBean = metaBean;
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
