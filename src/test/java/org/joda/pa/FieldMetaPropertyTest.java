package org.joda.pa;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;

/**
 * Tests the class {@link FieldMetaProperty}.
 */
public class FieldMetaPropertyTest extends
        AbstractFieldNameBasedMetaPropertyTest {

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
        Field backingField =
                FieldBackedTestBean.class.getDeclaredField(fieldName);

        return new FieldMetaProperty<>(
                notNullMetaBean, name, typeToken,
                derived, buildable, readable, mutable,
                backingField);
    }
}
