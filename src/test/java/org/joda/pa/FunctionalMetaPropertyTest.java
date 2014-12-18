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
        MetaProperty<?> annotatedMetaProperty = createStringMetaProperty();

        // report all three annotations
        Stream<Annotation> annotations = annotatedMetaProperty.annotations();
        assertEquals(annotations.count(), 3);

        // report annotations of the correct type
        boolean allOfCorrectType = annotatedMetaProperty
                .annotations()
                .allMatch(AnyAnnotation.class::isInstance);
        assertTrue(allOfCorrectType);
    }

    @Test
    public final void annotations_fieldAndMethodWithDistinctAnnotations_reportsAnnotations()
            throws Exception {
        MetaProperty<?> annotatedMetaProperty = createIntegerMetaProperty();

        // report all three annotations
        Stream<Annotation> annotations = annotatedMetaProperty.annotations();
        assertEquals(annotations.count(), 3);

        // report annotations of the correct type
        long annotationsOnField = annotatedMetaProperty
                .annotations()
                .filter(FieldAnnotation.class::isInstance)
                .count();
        assertEquals(annotationsOnField, 1);
        long annotationsOnSet = annotatedMetaProperty
                .annotations()
                .filter(SetAnnotation.class::isInstance)
                .count();
        assertEquals(annotationsOnSet, 1);
        long annotationsOnGet = annotatedMetaProperty
                .annotations()
                .filter(GetAnnotation.class::isInstance)
                .count();
        assertEquals(annotationsOnGet, 1);
    }

    @Test
    public final void annotationsFiltered_fieldAndMethodWithDistinctAnnotations_reportsAnnotation()
            throws Exception {
        MetaProperty<?> annotatedMetaProperty = createIntegerMetaProperty();

        // report exactly one field annotation
        Stream<? extends Annotation> annotationsOnField =
                annotatedMetaProperty.annotations(FieldAnnotation.class);
        assertEquals(annotationsOnField.count(), 1);

        // report exactly one get annotation
        Stream<? extends Annotation> annotationsOnGet =
                annotatedMetaProperty.annotations(GetAnnotation.class);
        assertEquals(annotationsOnGet.count(), 1);

        // report exactly one set annotation
        Stream<? extends Annotation> annotationsOnSet =
                annotatedMetaProperty.annotations(SetAnnotation.class);
        assertEquals(annotationsOnSet.count(), 1);
    }

    // implementation of 'AbstractMetaPropertyTest' -------------

    @Override
    protected TestBean createBean() {
        return new FieldBackedTestBean();
    }

    @Override
    protected MetaProperty<Object> createObjectMetaProperty() {
        return createObjectMetaProperty(null, "object");
    }

    @Override
    protected MetaProperty<Object> createObjectMetaPropertyWithMetaBean(
            MetaBean metaBean) {
        return createObjectMetaProperty(metaBean, "object");
    }

    @Override
    protected MetaProperty<Object> createObjectMetaPropertyWithName(String name) {
        return createObjectMetaProperty(null, name);
    }

    @Override
    protected MetaProperty<Object> createReadOnlyObjectMetaProperty() {
        return createMetaProperty(
                null, "object", Object.class, TestBean::getObject, null, null);
    }

    @Override
    protected MetaProperty<Object> createWriteOnlyObjectMetaProperty() {
        return createMetaProperty(
                null, "object", Object.class, null, TestBean::setObject, null);
    }

    private static MetaProperty<Object> createObjectMetaProperty(
            MetaBean metaBean, String name) {
        return createMetaProperty(
                metaBean, name, Object.class,
                TestBean::getObject, TestBean::setObject, null);
    }

    @Override
    protected MetaProperty<String> createStringMetaProperty() {
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
    protected MetaProperty<Integer> createPrimitiveIntegerMetaProperty() {
        Function<TestBean, Integer> getValue = TestBean::getPrimitiveInteger;
        BiConsumer<TestBean, Integer> setValue = TestBean::setPrimitiveInteger;
        return createMetaProperty(
                null, "primitiveInteger", int.class,
                getValue, setValue, null);
    }

    @Override
    protected MetaProperty<Integer> createIntegerMetaProperty() {
        Function<TestBean, Integer> getValue = TestBean::getInteger;
        BiConsumer<TestBean, Integer> setValue = TestBean::setInteger;
        Supplier<Stream<Annotation>> annotations =
                () -> getAnnotations(
                        "integer", "getInteger", "setInteger", Integer.class);
        return createMetaProperty(
                null, "integer", Integer.class, getValue, setValue, annotations);
    }

    @Override
    protected MetaProperty<List<Double>> createDoubleListMetaProperty() {
        Function<TestBean, List<Double>> getValue = TestBean::getDoubleList;
        BiConsumer<TestBean, List<Double>> setValue = TestBean::setDoubleList;
        @SuppressWarnings({ "unchecked", "rawtypes" })
        // TODO is there a nicer way to do this?
        Class<List<Double>> typeToken = ((Class) List.class);
        return createMetaProperty(
                null, "doubleList", typeToken, getValue, setValue, null);
    }

    private static <P> MetaProperty<P> createMetaProperty(
            MetaBean metaBean, String name, Class<P> typeToken,
            Function<TestBean, P> getValue, BiConsumer<TestBean, P> setValue,
            Supplier<Stream<Annotation>> annotations) {

        MetaBean notNullMetaBean = metaBean;
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
                notNullMetaBean, name, typeToken, true, true,
                get, set, notNullAnnotations);
    }

}
