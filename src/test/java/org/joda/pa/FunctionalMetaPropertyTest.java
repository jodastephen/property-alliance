package org.joda.pa;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Tests the class {@link FunctionalMetaProperty}.
 */
public class FunctionalMetaPropertyTest extends AbstractMetaPropertyTest {

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
                null, "object", Object.class, TestBean::getString, null, null);
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
                FunctionalMetaPropertyTest::getStringFieldAnnotations;
        return createMetaProperty(
                null, "string", String.class, getValue, setValue, annotations);
    }

    private static Stream<Annotation> getStringFieldAnnotations() {
        try {
            Field stringField = FieldBackedTestBean.class
                    .getDeclaredField("string");
            Annotation[] annots = stringField.getAnnotations();
            return Stream.of(annots);
        } catch (NoSuchFieldException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected MetaProperty<Integer> createPrimitiveIntegerMetaProperty() {
        Function<TestBean, Integer> getValue = TestBean::getPrimitiveInteger;
        BiConsumer<TestBean, Integer> setValue = TestBean::setPrimitiveInteger;
        return createMetaProperty(
                null, "primitiveInteger", Integer.class,
                getValue, setValue, null);
    }

    @Override
    protected MetaProperty<Integer> createIntegerMetaProperty() {
        Function<TestBean, Integer> getValue = TestBean::getInteger;
        BiConsumer<TestBean, Integer> setValue = TestBean::setInteger;
        return createMetaProperty(
                null, "integer", Integer.class, getValue, setValue, null);
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
