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
    protected final MetaProperty<?, Object> createObjectMetaProperty()
            throws Exception {
        return createMetaProperty(null, Object.class, "object");
    }

    @Override
    protected final <B> MetaProperty<B, Object> createObjectMetaPropertyWithMetaBean(
            MetaBean<B> metaBean)
            throws Exception {
        return createMetaProperty(metaBean, Object.class, "object");
    }

    @Override
    protected final MetaProperty<?, Object> createObjectMetaPropertyWithName(
            String name)
            throws Exception {
        return createMetaProperty(
                null, name, Object.class,
                false, true, true, true, "object");
    }

    @Override
    protected final MetaProperty<?, Object> createReadOnlyObjectMetaProperty()
            throws Exception {
        return createMetaProperty(
                null, "object", Object.class,
                false, true, true, false, "object");
    }

    @Override
    protected final MetaProperty<?, Object> createWriteOnlyObjectMetaProperty()
            throws Exception {
        return createMetaProperty(
                null, "object", Object.class,
                false, true, false, true, "object");
    }

    @Override
    protected final MetaProperty<?, Object> createDerivedObjectMetaProperty()
            throws Exception {
        return createMetaProperty(
                null, "object", Object.class,
                true, true, true, false, "object");
    }

    @Override
    protected final MetaProperty<?, Object> createNotBuildableObjectMetaProperty()
            throws Exception {
        return createMetaProperty(
                null, "object", Object.class,
                false, false, true, false, "object");
    }

    @Override
    protected final MetaProperty<?, String> createStringMetaProperty()
            throws Exception {
        return createMetaProperty(null, String.class, "string");
    }

    @Override
    protected final MetaProperty<?, Integer> createPrimitiveIntegerMetaProperty()
            throws Exception {
        return createMetaProperty(null, int.class, "primitiveInteger");
    }

    @Override
    protected final MetaProperty<?, Integer> createIntegerMetaProperty()
            throws Exception {
        return createMetaProperty(null, Integer.class, "integer");
    }

    @Override
    protected final MetaProperty<?, List<Double>> createDoubleListMetaProperty()
            throws Exception {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Class<List<Double>> typeToken = ((Class) List.class);
        return createMetaProperty(null, typeToken, "doubleList");
    }

    private <B, P> MetaProperty<B, P> createMetaProperty(
            MetaBean<B> metaBean, Class<P> typeToken, String fieldName)
            throws Exception {

        return createMetaProperty(
                metaBean, fieldName, typeToken, false, true, true, true,
                fieldName);
    }

    protected abstract <B, P> MetaProperty<B, P> createMetaProperty(
            MetaBean<B> metaBean, String name, Class<P> typeToken,
            boolean derived, boolean buildable,
            boolean readable, boolean mutable,
            String fieldName)
            throws Exception;
}
