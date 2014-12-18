package org.joda.pa;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joda.pa.TestBean.AnyAnnotation;
import org.testng.annotations.Test;

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
        assertSame(metaProperty.metaBean(), metaBean);
    }

    @Test
    public final void declaringClass_compareWithBeanClass_same()
            throws Exception {
        MetaProperty<?> metaProperty = createObjectMetaProperty();
        assertSame(metaProperty.declaringType(), TestBean.class);
    }

    @Test
    public final void name_compareWithConstructionArgument_equals()
            throws Exception {
        String name = "thePropertyName";
        MetaProperty<?> metaProperty = createObjectMetaPropertyWithName(name);
        assertEquals(metaProperty.name(), name);
    }

    @Test
    public final void propertyType_compareWithIntendedType_same()
            throws Exception {
        MetaProperty<Object> objectMetaProperty = createObjectMetaProperty();
        assertSame(objectMetaProperty.propertyType(), Object.class);

        MetaProperty<String> stringMetaProperty = createStringMetaProperty();
        assertSame(stringMetaProperty.propertyType(), String.class);

        MetaProperty<Integer> primitiveIntegerMetaProperty =
                createPrimitiveIntegerMetaProperty();
        assertSame(primitiveIntegerMetaProperty.propertyType(), int.class);

        MetaProperty<Integer> integerMetaProperty = createIntegerMetaProperty();
        assertSame(integerMetaProperty.propertyType(), Integer.class);

        MetaProperty<List<Double>> doubleListMetaProperty =
                createDoubleListMetaProperty();
        assertSame(doubleListMetaProperty.propertyType(), List.class);
    }

    // TODO add tests for 'propertyGenericType'  

    // annotations ------------------------------------------------------------

    @Test
    public final void annotations_propertyWithoutAnnotations_reportsNoAnnotations()
            throws Exception {
        MetaProperty<?> notAnnotatedMetaProperty = createObjectMetaProperty();
        Stream<Annotation> annotations = notAnnotatedMetaProperty.annotations();
        assertEquals(annotations.count(), 0);
    }

    @Test
    public final void annotations_propertyWithAnnotations_reportsAnnotations()
            throws Exception {
        MetaProperty<?> annotatedMetaProperty = createStringMetaProperty();

        // reports at least one annotation
        Stream<Annotation> annotations = annotatedMetaProperty.annotations();
        assertTrue(annotations.count() >= 1);

        // reports only annotations of the correct type
        annotations = annotatedMetaProperty.annotations();
        Stream<Annotation> otherAnnotations =
                annotations.filter(a -> !(a instanceof AnyAnnotation));
        assertEquals(otherAnnotations.count(), 0);
    }

    @Test
    public final void annotationsFiltered_propertyWithoutAnnotations_reportsNoAnnotations()
            throws Exception {
        MetaProperty<?> notAnnotatedMetaProperty = createObjectMetaProperty();
        Stream<? extends Annotation> annotations =
                notAnnotatedMetaProperty.annotations(AnyAnnotation.class);
        assertEquals(annotations.count(), 0);
    }

    @Test
    public final void annotationsFiltered_propertyWithoutMatchingAnnotations_reportsNoAnnotations()
            throws Exception {
        MetaProperty<?> annotatedMetaProperty = createStringMetaProperty();
        Stream<? extends Annotation> annotations =
                annotatedMetaProperty.annotations(Retention.class);
        assertEquals(annotations.count(), 0);
    }

    @Test
    public final void annotationsFiltered_propertyWithMatchingAnnotations_reportsAnnotations()
            throws Exception {
        MetaProperty<?> annotatedMetaProperty = createStringMetaProperty();

        // reports at least one annotation
        Stream<AnyAnnotation> annotations =
                annotatedMetaProperty.annotations(AnyAnnotation.class);
        assertTrue(annotations.count() >= 1);
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

        assertSame(stringMetaProperty.get(bean), value);
    }

    @Test(expectedExceptions = ClassCastException.class)
    public final void get_incorrectBeanType_ClassCastException()
            throws Exception {
        MetaProperty<?> metaProperty = createObjectMetaProperty();
        metaProperty.get("this is no bean");
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
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
        assertEquals(bean.getInteger(), (Integer) 42);
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

        assertSame(bean.getDoubleList(), doubleList);
    }

    @Test(expectedExceptions = ClassCastException.class)
    public final void set_incorrectBeanType_ClassCastException()
            throws Exception {
        MetaProperty<?> metaProperty = createObjectMetaProperty();
        metaProperty.set("this is no bean", null);
    }

    @Test(expectedExceptions = ClassCastException.class)
    public final void set_incorrectValueType_ClassCastException()
            throws Exception {
        MetaProperty<Integer> integerMetaProperty = createIntegerMetaProperty();
        TestBean bean = createBean();
        integerMetaProperty.set(bean, "this is no integer");
    }

    @Test(expectedExceptions = RuntimeException.class)
    public final void set_rejectedValue_RuntimeException() throws Exception {
        MetaProperty<Integer> primitiveIntegerMetaProperty =
                createPrimitiveIntegerMetaProperty();

        TestBean bean = createBean();
        // a primitive int field rejects nulls
        Integer rejectedValue = null;
        primitiveIntegerMetaProperty.set(bean, rejectedValue);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
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
