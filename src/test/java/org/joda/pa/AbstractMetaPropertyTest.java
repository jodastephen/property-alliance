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
        long annotationsCount = notAnnotatedMetaProperty
                .annotations()
                .count();
        assertEquals(annotationsCount, 0);
    }

    @Test
    public final void annotations_propertyWithAnnotations_reportsAnnotations()
            throws Exception {
        MetaProperty<?> annotatedMetaProperty = createStringMetaProperty();

        // reports at least one annotation
        long annotationsCount = annotatedMetaProperty
                .annotations()
                .count();
        assertTrue(annotationsCount >= 1);

        // reports only annotations of the correct type
        boolean allAnnotationsOfCorrectType = annotatedMetaProperty
                .annotations()
                .allMatch(AnyAnnotation.class::isInstance);
        assertTrue(allAnnotationsOfCorrectType);
    }

    @Test
    public final void annotationsFiltered_propertyWithoutAnnotations_reportsNoAnnotations()
            throws Exception {
        MetaProperty<?> notAnnotatedMetaProperty = createObjectMetaProperty();
        long annotationsCount = notAnnotatedMetaProperty
                .annotations()
                .count();
        assertEquals(annotationsCount, 0);
    }

    @Test
    public final void annotationsFiltered_propertyWithoutMatchingAnnotations_reportsNoAnnotations()
            throws Exception {
        MetaProperty<?> annotatedMetaProperty = createStringMetaProperty();
        long notExistingAnnotationsCount = annotatedMetaProperty
                .annotations(Retention.class)
                .count();
        assertEquals(notExistingAnnotationsCount, 0);
    }

    @Test
    public final void annotationsFiltered_propertyWithMatchingAnnotations_reportsAnnotations()
            throws Exception {
        MetaProperty<?> annotatedMetaProperty = createStringMetaProperty();

        // reports at least one annotation
        long annotationsCount = annotatedMetaProperty
                .annotations(AnyAnnotation.class)
                .count();
        assertTrue(annotationsCount >= 1);
    }

    /*
     * More detailed annotation processing must be tested in subclasses because
     * its results differ depending on the strategy chosen to access the
     * property (e.g. reflection on fields vs. reflection on methods). 
     */

    // boolean indicators -----------------------------------------------------

    @Test
    public final void isDerived_derivedProperty_true() throws Exception {
        MetaProperty<?> metaProperty = createDerivedObjectMetaProperty();
        assertTrue(metaProperty.isDerived());
    }

    @Test
    public final void isDerived_notDerivedProperty_false() throws Exception {
        MetaProperty<?> readOnlyMetaProperty = createObjectMetaProperty();
        assertFalse(readOnlyMetaProperty.isDerived());
    }

    @Test
    public final void isBuildable_buildableProperty_true() throws Exception {
        MetaProperty<?> metaProperty = createObjectMetaProperty();
        assertTrue(metaProperty.isBuildable());
    }

    @Test
    public final void isBuildable_notBuildableProperty_false() throws Exception {
        MetaProperty<?> readOnlyMetaProperty = createNotBuildableObjectMetaProperty();
        assertFalse(readOnlyMetaProperty.isBuildable());
    }

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

    @Test
    public final void equals_otherBeanOtherName_false() throws Exception {
        MetaProperty<?> objectMetaPropertyOnTestBean = createObjectMetaProperty();
        MetaProperty<?> otherMetaPropertyOnSomeBean =
                new ComparisonMetaProperty(
                        AbstractMetaPropertyTest.class, "somePropertyName");

        assertFalse(objectMetaPropertyOnTestBean
                .equals(otherMetaPropertyOnSomeBean));
    }

    @Test
    public final void equals_otherBeanSameName_false() throws Exception {
        MetaProperty<?> objectMetaPropertyOnTestBean = createObjectMetaProperty();
        MetaProperty<?> objectMetaPropertyOnSomeBean =
                new ComparisonMetaProperty(
                        AbstractMetaPropertyTest.class, "object");

        assertFalse(objectMetaPropertyOnTestBean
                .equals(objectMetaPropertyOnSomeBean));
    }

    @Test
    public final void equals_sameBeanOtherName_false() throws Exception {
        MetaProperty<?> objectMetaPropertyOnTestBean = createObjectMetaProperty();
        MetaProperty<?> otherMetaPropertyOnTestBean =
                new ComparisonMetaProperty(TestBean.class, "somePropertyName");

        assertFalse(objectMetaPropertyOnTestBean
                .equals(otherMetaPropertyOnTestBean));
    }

    @Test
    public final void equals_sameBeanSameName_true() throws Exception {
        MetaProperty<?> objectMetaPropertyOnTestBean = createObjectMetaProperty();
        MetaProperty<?> equalMetaProperty =
                new ComparisonMetaProperty(TestBean.class, "object");

        assertTrue(objectMetaPropertyOnTestBean.equals(equalMetaProperty));
    }

    // TODO add tests for 'hashCode'

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
     *     - (other property annotations are not tested)
     *  - is not derived
     *  - is buildable 
     *  - is mutable, i.e. not read-only
     *  - accepts all values, which are valid for the corresponding bean setter
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

    protected abstract MetaProperty<Object> createDerivedObjectMetaProperty()
            throws Exception;

    protected abstract MetaProperty<Object> createNotBuildableObjectMetaProperty()
            throws Exception;

    protected abstract MetaProperty<String> createStringMetaProperty()
            throws Exception;

    protected abstract MetaProperty<Integer> createPrimitiveIntegerMetaProperty()
            throws Exception;

    protected abstract MetaProperty<Integer> createIntegerMetaProperty()
            throws Exception;

    protected abstract MetaProperty<List<Double>> createDoubleListMetaProperty()
            throws Exception;

    /* 
     * inner classes ----------------------------------------------------------
     */

    private static class ComparisonMetaProperty implements MetaProperty<Void> {

        private final Class<?> declaringType;
        private final String name;

        public ComparisonMetaProperty(Class<?> declaringType, String name) {
            this.declaringType = declaringType;
            this.name = name;
        }

        @Override
        public MetaBean metaBean() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public Class<Void> propertyType() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Class<?> declaringType() {
            return declaringType;
        }

        @Override
        public Stream<Annotation> annotations() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isBuildable() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isMutable() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Void get(Object bean) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(Object bean, Object value) {
            throw new UnsupportedOperationException();
        }

    }

}
