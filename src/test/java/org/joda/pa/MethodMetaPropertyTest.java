package org.joda.pa;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.stream.Stream;

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
    public final void annotations_propertyWithTwoAnnotations_reportsAnnotations()
            throws Exception {
        MetaProperty<?> annotatedMetaProperty = createIntegerMetaProperty();

        Stream<Annotation> annotations = annotatedMetaProperty.annotations();

        // report exactly two annotations
        assertEquals(annotations.count(), 2);

        // report annotations of the correct type
        long annotationsOnGet = annotatedMetaProperty
                .annotations()
                .filter(GetAnnotation.class::isInstance)
                .count();
        assertEquals(annotationsOnGet, 1);
        long annotationsOnSet = annotatedMetaProperty
                .annotations()
                .filter(SetAnnotation.class::isInstance)
                .count();
        assertEquals(annotationsOnSet, 1);
    }

    @Test
    public final void annotationsFilter_propertyWithTwoAnnotations_reportsAnnotation()
            throws Exception {
        MetaProperty<?> annotatedMetaProperty = createIntegerMetaProperty();

        // report exactly one annotation on get
        Stream<? extends Annotation> annotationsOnGet =
                annotatedMetaProperty.annotations(GetAnnotation.class);
        assertEquals(annotationsOnGet.count(), 1);

        // report exactly one annotation on set
        Stream<? extends Annotation> annotationsOnSet =
                annotatedMetaProperty.annotations(SetAnnotation.class);
        assertEquals(annotationsOnSet.count(), 1);
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
