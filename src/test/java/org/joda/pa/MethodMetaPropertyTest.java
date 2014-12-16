package org.joda.pa;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Method;

/**
 * Tests the class {@link FieldMetaProperty}.
 */
public class MethodMetaPropertyTest extends
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

        Method getValue = createGetMethod(readable, fieldName);
        Method setValue = createSetMethod(mutable, fieldName, typeToken);

        return new MethodMetaProperty<>(
                notNullMetaBean, name, typeToken,
                derived, buildable,
                getValue, setValue);
    }

    private static Method createGetMethod(boolean readable, String fieldName)
            throws NoSuchMethodException {
        if (!readable) {
            return null;
        }

        String getValueName = createMethodName("get", fieldName);
        return FieldBackedTestBean.class.getMethod(getValueName);
    }

    private static <T> Method createSetMethod(
            boolean mutable, String fieldName, Class<T> valueClass)
            throws NoSuchMethodException {

        if (!mutable) {
            return null;
        }

        String setValueName = createMethodName("set", fieldName);
        return FieldBackedTestBean.class.getMethod(setValueName, valueClass);
    }

    private static String createMethodName(String prefix, String fieldName) {
        return prefix
                + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
    }

}
