package org.joda.pa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

interface TestBean {

    Object getObject();

    void setObject(Object object);

    @AnyAnnotation
    String getString();

    @AnyAnnotation
    void setString(String string);

    int getPrimitiveInteger();

    void setPrimitiveInteger(int primitiveInteger);

    @GetAnnotation
    Integer getInteger();

    @SetAnnotation
    void setInteger(Integer integer);

    List<Double> getDoubleList();

    void setDoubleList(List<Double> doubleList);

    // annotations

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD, ElementType.METHOD })
    @Inherited
    static @interface AnyAnnotation {
        // no values needed
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Inherited
    static @interface FieldAnnotation {
        // no values needed
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Inherited
    static @interface GetAnnotation {
        // no values needed
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Inherited
    static @interface SetAnnotation {
        // no values needed
    }

}
