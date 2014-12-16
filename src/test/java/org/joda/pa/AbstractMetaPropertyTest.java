package org.joda.pa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joda.pa.TestBean.AnyAnnotation;
import org.junit.Test;

/**
 * Abstract superclass to tests of {@link MetaProperty} implementations.
 * <p>
 * Inheriting classes only need to implement various factory methods to have
 * their unit completely tested. 
 */
@SuppressWarnings("javadoc")
public abstract class AbstractMetaPropertyTest {

    //-------------------------------------------------------------------------

    @Test
    public final void metaBean_compareWithConstructionArgument_same()
            throws Exception {
        MetaBean metaBean = mock(MetaBean.class);
        MetaProperty<?> metaProperty = createObjectMetaPropertyWithMetaBean(metaBean);
        assertSame(metaBean, metaProperty.metaBean());
    }

    @Test
    public final void declaringClass_compareWithBeanClass_same()
            throws Exception {
        MetaProperty<?> metaProperty = createObjectMetaProperty();
        assertSame(TestBean.class, metaProperty.declaringClass());
    }

    @Test
    public final void name_compareWithConstructionArgument_equals()
            throws Exception {
        String name = "thePropertyName";
        MetaProperty<?> metaProperty = createObjectMetaPropertyWithName(name);
        assertEquals(name, metaProperty.name());
    }

    @Test
    public final void propertyType_compareWithIntendedType_same()
            throws Exception {
        MetaProperty<Object> objectMetaProperty = createObjectMetaProperty();
        assertSame(Object.class, objectMetaProperty.propertyType());

        MetaProperty<String> stringMetaProperty = createStringMetaProperty();
        assertSame(String.class, stringMetaProperty.propertyType());

        MetaProperty<Integer> primitiveIntegerMetaProperty =
                createPrimitiveIntegerMetaProperty();
        assertSame(int.class, primitiveIntegerMetaProperty.propertyType());

        MetaProperty<Integer> integerMetaProperty = createIntegerMetaProperty();
        assertSame(Integer.class, integerMetaProperty.propertyType());

        MetaProperty<List<Double>> doubleListMetaProperty =
                createDoubleListMetaProperty();
        assertSame(List.class, doubleListMetaProperty.propertyType());
    }

    // TODO add tests for 'propertyGenericType'  

    // annotations ------------------------------------------------------------

    @Test
    public final void annotations_propertyWithoutAnnotations_reportsNoAnnotations()
            throws Exception {
        MetaProperty<?> notAnnotatedMetaProperty = createObjectMetaProperty();
        Stream<Annotation> annotations = notAnnotatedMetaProperty.annotations();
        assertEquals(0, annotations.count());
    }

    @Test
    public final void annotations_propertyWithAnnotations_reportsCorrectAnnotations()
            throws Exception {
        MetaProperty<?> annotatedMetaProperty = createStringMetaProperty();

        // reports exactly 1 annotation
        Stream<Annotation> annotations = annotatedMetaProperty.annotations();
        assertEquals(1, annotations.count());

        // reports an annotation of the correct type
        annotations = annotatedMetaProperty.annotations();
        Stream<Annotation> anyAnnotations =
                annotations.filter(a -> a instanceof AnyAnnotation);
        assertEquals(1, anyAnnotations.count());
    }

    /*
     * More detailed annotation processing must be tested in subclasses because
     * its results differ depending on the strategy chosen to access the
     * property (e.g. reflection on fields vs. reflection on methods). 
     */

    // boolean indicators -----------------------------------------------------

    // TODO add tests for 'isDerived', 'isBuildable'

    @Test
    public final void isMutable_mutableProperty_true() throws Exception {
        MetaProperty<?> metaProperty = createObjectMetaProperty();
        assertTrue(metaProperty.isMutable());
    }

    @Test
    public final void isMutable_readOnlyProperty_false() throws Exception {
        MetaProperty<?> readOnlyMetaProperty = createReadOnlyObjectMetaProperty();
        assertFalse(readOnlyMetaProperty.isMutable());
    }

    // get & set --------------------------------------------------------------

    @Test
    public final void get_valueNull_null() throws Exception {
        MetaProperty<?> metaProperty = createObjectMetaProperty();
        TestBean bean = createBean();
        assertNull(metaProperty.get(bean));
    }

    @Test
    public final void get_existingValue_sameValue() throws Exception {
        MetaProperty<String> stringMetaProperty = createStringMetaProperty();

        TestBean bean = createBean();
        String value = "the not null value";
        bean.setString(value);

        assertSame(value, stringMetaProperty.get(bean));
    }

    @Test(expected = ClassCastException.class)
    public final void get_incorrectBeanType_ClassCastException()
            throws Exception {
        MetaProperty<?> metaProperty = createObjectMetaProperty();
        metaProperty.get("this is no bean");
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void get_writeOnlyMetaProperty_UnsupportedOperationException()
            throws Exception {
        MetaProperty<?> readOnlyMetaProperty = createWriteOnlyObjectMetaProperty();
        TestBean bean = createBean();
        readOnlyMetaProperty.get(bean);
    }

    @Test
    public final void set_nullForObject_valueIsNull() throws Exception {
        MetaProperty<Integer> integerMetaProperty = createIntegerMetaProperty();

        TestBean bean = createBean();
        bean.setInteger(42);
        assertEquals((Integer) 42, bean.getInteger());
        integerMetaProperty.set(bean, null);

        assertNull(bean.getInteger());
    }

    @Test
    public final void set_some_valueIsSame() throws Exception {
        MetaProperty<List<Double>> doubleListMetaProperty =
                createDoubleListMetaProperty();

        TestBean bean = createBean();
        List<Double> doubleList =
                Stream.of(1d, 2d, 8d, 42d, 63d).collect(Collectors.toList());
        doubleListMetaProperty.set(bean, doubleList);

        assertSame(doubleList, bean.getDoubleList());
    }

    @Test(expected = ClassCastException.class)
    public final void set_incorrectBeanType_ClassCastException()
            throws Exception {
        MetaProperty<?> metaProperty = createObjectMetaProperty();
        metaProperty.set("this is no bean", null);
    }

    @Test(expected = ClassCastException.class)
    public final void set_incorrectValueType_ClassCastException()
            throws Exception {
        MetaProperty<Integer> integerMetaProperty = createIntegerMetaProperty();
        TestBean bean = createBean();
        integerMetaProperty.set(bean, "this is no integer");
    }

    @Test(expected = RuntimeException.class)
    public final void set_rejectedValue_RuntimeException() throws Exception {
        MetaProperty<Integer> primitiveIntegerMetaProperty =
                createPrimitiveIntegerMetaProperty();

        TestBean bean = createBean();
        // a primitive int field rejects nulls
        Integer rejectedValue = null;
        primitiveIntegerMetaProperty.set(bean, rejectedValue);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void set_readOnlyMetaProperty_UnsupportedOperationException()
            throws Exception {
        MetaProperty<?> readOnlyMetaProperty = createReadOnlyObjectMetaProperty();
        TestBean bean = createBean();
        readOnlyMetaProperty.set(bean, "some Value");
    }

    // equals & hashCode-------------------------------------------------------

    // TODO add tests for 'equals', 'hashCode'

    /* 
     * abstract test methods --------------------------------------------------
     */

    protected abstract TestBean createBean();

    /*
     * All methods must return "fully functional" meta-properties.
     * This implies that each returned meta-property fulfills these conditions:
     *  - is a property for {@link TestBean}
     *  - its name is derived from the corresponding bean getter
     *      e.g. "getDoubleList()" -> "doubleList"
     *  - annotations (only tested superficially in this class):
     *     - reports no annotations on the object property
     *     - reports 'AnyAnnotation' on the string property
     *  - is not derived
     *  - is buildable 
     *  - is mutable, i.e. not read-only
     *  - accepts all values valid for the corresponding bean setter
     *  
     * Divergent behavior is specified by the factory method's name.
     */

    protected abstract MetaProperty<Object> createObjectMetaProperty()
            throws Exception;

    protected abstract MetaProperty<Object> createObjectMetaPropertyWithMetaBean(
            MetaBean metaBean)
            throws Exception;

    protected abstract MetaProperty<Object> createObjectMetaPropertyWithName(
            String name)
            throws Exception;

    protected abstract MetaProperty<Object> createReadOnlyObjectMetaProperty()
            throws Exception;

    protected abstract MetaProperty<Object> createWriteOnlyObjectMetaProperty()
            throws Exception;

    protected abstract MetaProperty<String> createStringMetaProperty()
            throws Exception;

    protected abstract MetaProperty<Integer> createPrimitiveIntegerMetaProperty()
            throws Exception;

    protected abstract MetaProperty<Integer> createIntegerMetaProperty()
            throws Exception;

    protected abstract MetaProperty<List<Double>> createDoubleListMetaProperty()
            throws Exception;

}
