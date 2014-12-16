package org.joda.pa;

import java.util.List;

/**
 * Abstract superclass to tests of {@link MetaProperty} implementations, which
 * use reflection to access values.
 * <p>
 * This is just a convenience superclass for {@link FieldMetaPropertyTest} and
 * {@link MethodMetaPropertyTest} because both only differ in how exactly they
 * reflect on the field name. Either by directly accessing the field or by
 * deducing the beans getter/setter names for the field and reflecting on those
 * methods. 
 */
abstract class AbstractFieldNameBasedMetaPropertyTest extends
        AbstractMetaPropertyTest {

    @Override
    protected final TestBean createBean() {
        return new FieldBackedTestBean();
    }

    @Override
    protected final MetaProperty<Object> createObjectMetaProperty()
            throws Exception {
        return createMetaProperty(null, Object.class, "object");
    }

    @Override
    protected final MetaProperty<Object> createObjectMetaPropertyWithMetaBean(
            MetaBean metaBean)
            throws Exception {
        return createMetaProperty(metaBean, Object.class, "object");
    }

    @Override
    protected final MetaProperty<Object> createObjectMetaPropertyWithName(
            String name)
            throws Exception {
        return createMetaProperty(
                null, name, Object.class,
                false, true, true, true, "object");
    }

    @Override
    protected final MetaProperty<Object> createReadOnlyObjectMetaProperty()
            throws Exception {
        return createMetaProperty(
                null, "object", Object.class,
                false, true, true, false, "object");
    }

    @Override
    protected final MetaProperty<Object> createWriteOnlyObjectMetaProperty()
            throws Exception {
        return createMetaProperty(
                null, "object", Object.class,
                false, true, false, true, "object");
    }

    @Override
    protected final MetaProperty<String> createStringMetaProperty()
            throws Exception {
        return createMetaProperty(null, String.class, "string");
    }

    @Override
    protected final MetaProperty<Integer> createPrimitiveIntegerMetaProperty()
            throws Exception {
        return createMetaProperty(null, int.class, "primitiveInteger");
    }

    @Override
    protected final MetaProperty<Integer> createIntegerMetaProperty()
            throws Exception {
        return createMetaProperty(null, Integer.class, "integer");
    }

    @Override
    protected final MetaProperty<List<Double>> createDoubleListMetaProperty()
            throws Exception {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        // TODO is there a nicer way to do this?
        Class<List<Double>> typeToken = ((Class) List.class);
        return createMetaProperty(null, typeToken, "doubleList");
    }

    private <P> MetaProperty<P> createMetaProperty(
            MetaBean metaBean, Class<P> typeToken, String fieldName)
            throws Exception {

        return createMetaProperty(
                metaBean, fieldName, typeToken, false, true, true, true,
                fieldName);
    }

    protected abstract <P> MetaProperty<P> createMetaProperty(
            MetaBean metaBean, String name, Class<P> typeToken,
            boolean derived, boolean buildable,
            boolean readable, boolean mutable,
            String fieldName)
            throws Exception;
}
