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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.joda.pa.TestBean.AnyAnnotation;
import org.joda.pa.TestBean.FieldAnnotation;
import org.joda.pa.TestBean.GetAnnotation;
import org.joda.pa.TestBean.SetAnnotation;
import org.testng.annotations.Test;

/**
 * Tests the class {@link FunctionalMetaProperty}.
 */
@SuppressWarnings("javadoc")
public class FunctionalMetaPropertyTest extends AbstractMetaPropertyTest {

    @Test
    public final void annotations_fieldAndMethodWithSameAnnotations_reportsAnnotations()
            throws Exception {
        MetaProperty<?, ?> annotatedMetaProperty = createStringMetaProperty();

        // report exactly three annotation
        long annotationsCount = annotatedMetaProperty
                .annotations()
                .count();
        assertEquals(annotationsCount, 3);

        // report annotations of the correct type
        boolean annotationsOfCorrectType = annotatedMetaProperty
                .annotations()
                .allMatch(AnyAnnotation.class::isInstance);
        assertTrue(annotationsOfCorrectType);
    }

    @Test
    public final void annotations_fieldAndMethodWithDistinctAnnotations_reportsAnnotations()
            throws Exception {
        MetaProperty<?, ?> annotatedMetaProperty = createIntegerMetaProperty();

        // report all three annotations
        Stream<Annotation> annotations = annotatedMetaProperty.annotations();
        assertEquals(annotations.count(), 3);

        // report annotations of the correct type
        long annotationsCountOnField = annotatedMetaProperty
                .annotations()
                .filter(FieldAnnotation.class::isInstance)
                .count();
        assertEquals(annotationsCountOnField, 1);
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
    public final void annotationsFiltered_fieldAndMethodWithDistinctAnnotations_reportsAnnotation()
            throws Exception {
        MetaProperty<?, ?> annotatedMetaProperty = createIntegerMetaProperty();

        // report exactly one field annotation
        long annotationsCountOnField = annotatedMetaProperty
                .annotations(FieldAnnotation.class)
                .count();
        assertEquals(annotationsCountOnField, 1);

        // report exactly one get annotation
        long annotationsCountOnGet = annotatedMetaProperty
                .annotations(GetAnnotation.class)
                .count();
        assertEquals(annotationsCountOnGet, 1);

        // report exactly one set annotation
        long annotationsCountOnSet = annotatedMetaProperty
                .annotations(SetAnnotation.class)
                .count();
        assertEquals(annotationsCountOnSet, 1);
    }

    // implementation of 'AbstractMetaPropertyTest' -------------

    @Override
    protected TestBean createBean() {
        return new FieldBackedTestBean();
    }

    @Override
    protected MetaProperty<?, Object> createObjectMetaProperty() {
        return createMetaProperty(
                "object", Object.class,
                TestBean::getObject, TestBean::setObject);
    }

    @Override
    protected <B> MetaProperty<B, Object> createObjectMetaPropertyWithMetaBean(
            MetaBean<B> metaBean) {
        return createMetaProperty(
                metaBean, "object", Object.class,
                TestBean::getObject, TestBean::setObject, null);
    }

    @Override
    protected MetaProperty<?, Object> createObjectMetaPropertyWithName(
            String name) {
        return createMetaProperty(
                name, Object.class, TestBean::getObject, TestBean::setObject);
    }

    @Override
    protected MetaProperty<?, Object> createReadOnlyObjectMetaProperty() {
        return createMetaProperty(
                "object", Object.class, TestBean::getObject, null);
    }

    @Override
    protected MetaProperty<?, Object> createWriteOnlyObjectMetaProperty() {
        return createMetaProperty(
                "object", Object.class, null, TestBean::setObject);
    }

    @Override
    protected MetaProperty<?, Object> createDerivedObjectMetaProperty()
            throws Exception {
        return createMetaProperty(
                null, "object", Object.class, true, true,
                TestBean::getObject, TestBean::setObject, null);
    }

    @Override
    protected MetaProperty<?, Object> createNotBuildableObjectMetaProperty()
            throws Exception {
        return createMetaProperty(
                null, "object", Object.class, false, false,
                TestBean::getObject, TestBean::setObject, null);
    }

    @Override
    protected MetaProperty<?, String> createStringMetaProperty() {
        Function<TestBean, String> getValue = TestBean::getString;
        BiConsumer<TestBean, String> setValue = TestBean::setString;
        Supplier<Stream<Annotation>> annotations =
                () -> getAnnotations(
                        "string", "getString", "setString", String.class);
        return createMetaProperty(
                null, "string", String.class, getValue, setValue, annotations);
    }

    private static Stream<Annotation> getAnnotations(
            String fieldName, String getMethodName, String setMethodName,
            Class<?> propertyType) {

        Stream<Annotation> methodAnnotations = Stream.concat(
                getMethodAnnotations(getMethodName),
                getMethodAnnotations(setMethodName, propertyType)
                );
        return Stream.concat(
                getFieldAnnotations(fieldName),
                methodAnnotations
                );
    }

    private static Stream<Annotation> getFieldAnnotations(String fieldName) {
        try {
            Field field = FieldBackedTestBean.class.getDeclaredField(fieldName);
            Annotation[] annots = field.getAnnotations();
            return Stream.of(annots);
        } catch (NoSuchFieldException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Stream<Annotation> getMethodAnnotations(
            String methodName, Class<?>... parameters) {

        try {
            Method method = FieldBackedTestBean.class
                    .getDeclaredMethod(methodName, parameters);
            Annotation[] annots = method.getAnnotations();
            return Stream.of(annots);
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected MetaProperty<?, Integer> createPrimitiveIntegerMetaProperty() {
        Function<TestBean, Integer> getValue = TestBean::getPrimitiveInteger;
        BiConsumer<TestBean, Integer> setValue = TestBean::setPrimitiveInteger;
        return createMetaProperty(
                "primitiveInteger", int.class,
                getValue, setValue);
    }

    @Override
    protected MetaProperty<?, Integer> createIntegerMetaProperty() {
        Function<TestBean, Integer> getValue = TestBean::getInteger;
        BiConsumer<TestBean, Integer> setValue = TestBean::setInteger;
        Supplier<Stream<Annotation>> annotations =
                () -> getAnnotations(
                        "integer", "getInteger", "setInteger", Integer.class);
        return createMetaProperty(
                null, "integer", Integer.class, getValue, setValue, annotations);
    }

    @Override
    protected MetaProperty<?, List<Double>> createDoubleListMetaProperty() {
        Function<TestBean, List<Double>> getValue = TestBean::getDoubleList;
        BiConsumer<TestBean, List<Double>> setValue = TestBean::setDoubleList;
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Class<List<Double>> typeToken = ((Class) List.class);
        return createMetaProperty("doubleList", typeToken, getValue, setValue);
    }

    private static <P> MetaProperty<?, P> createMetaProperty(
            String name, Class<P> typeToken,
            Function<TestBean, P> getValue, BiConsumer<TestBean, P> setValue) {

        return createMetaProperty(
                null, name, typeToken, getValue, setValue, null);
    }

    private static <B, P> MetaProperty<B, P> createMetaProperty(
            MetaBean<B> metaBean, String name, Class<P> typeToken,
            Function<TestBean, P> getValue, BiConsumer<TestBean, P> setValue,
            Supplier<Stream<Annotation>> annotations) {

        return createMetaProperty(
                metaBean, name, typeToken, false, true,
                getValue, setValue, annotations);
    }

    private static <B, P> MetaProperty<B, P> createMetaProperty(
            MetaBean<B> metaBean, String name, Class<P> typeToken,
            boolean derived, boolean buildable,
            Function<TestBean, P> getValue, BiConsumer<TestBean, P> setValue,
            Supplier<Stream<Annotation>> annotations) {

        MetaBean<B> notNullMetaBean = metaBean;
        if (notNullMetaBean == null) {
            notNullMetaBean = mock(MetaBean.class);
            doReturn(TestBean.class).when(notNullMetaBean).beanType();
        }

        Function<Object, P> get = getValue == null
                ? null
                : b -> getValue.apply((TestBean) b);
        BiConsumer<Object, P> set = setValue == null
                ? null
                : (b, value) -> setValue.accept((TestBean) b, value);

        Supplier<Stream<Annotation>> notNullAnnotations = annotations;
        if (notNullAnnotations == null) {
            notNullAnnotations = () -> Stream.empty();
        }

        return new FunctionalMetaProperty<>(
                notNullMetaBean, name, typeToken, derived, buildable,
                get, set, notNullAnnotations);
    }

}
