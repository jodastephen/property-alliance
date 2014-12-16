package org.joda.pa;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Tests the class {@link FieldMetaProperty}.
 */
public class FieldMetaPropertyTest extends AbstractMetaPropertyTest {

    @Override
    protected TestBean createBean() {
        return new FieldBackedTestBean();
    }

    @Override
    protected MetaProperty<Object> createObjectMetaProperty() throws Exception {
        return createMetaProperty(null, Object.class, "object");
    }

    @Override
    protected MetaProperty<Object> createObjectMetaPropertyWithMetaBean(
            MetaBean metaBean)
            throws Exception {
        return createMetaProperty(metaBean, Object.class, "object");
    }

    @Override
    protected MetaProperty<Object> createObjectMetaPropertyWithName(String name)
            throws Exception {
        return createMetaProperty(
                null, name, Object.class, false, true, true, true, "object");
    }

    @Override
    protected MetaProperty<Object> createReadOnlyObjectMetaProperty()
            throws Exception {
        return createMetaProperty(
                null, "object", Object.class,
                false, true, true, false, "object");
    }

    @Override
    protected MetaProperty<Object> createWriteOnlyObjectMetaProperty()
            throws Exception {
        return createMetaProperty(
                null, "object", Object.class,
                false, true, false, true, "object");
    }

    @Override
    protected MetaProperty<String> createStringMetaProperty() throws Exception {
        return createMetaProperty(null, String.class, "string");
    }

    @Override
    protected MetaProperty<Integer> createPrimitiveIntegerMetaProperty()
            throws Exception {
        return createMetaProperty(null, Integer.class, "primitiveInteger");
    }

    @Override
    protected MetaProperty<Integer> createIntegerMetaProperty()
            throws Exception {
        return createMetaProperty(null, Integer.class, "integer");
    }

    @Override
    protected MetaProperty<List<Double>> createDoubleListMetaProperty()
            throws Exception {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        // TODO is there a nicer way to do this?
        Class<List<Double>> typeToken = ((Class) List.class);
        return createMetaProperty(null, typeToken, "doubleList");
    }

    private static <P> MetaProperty<P> createMetaProperty(
            MetaBean metaBean, Class<P> typeToken, String fieldName)
            throws Exception {

        return createMetaProperty(
                metaBean, fieldName, typeToken, false, true, true, true,
                fieldName);
    }

    private static <P> MetaProperty<P> createMetaProperty(
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
