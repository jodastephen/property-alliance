package org.joda.pa;

import java.util.List;

/**
 * Acts as the bean class for all tests. 
 */
final class FieldBackedTestBean implements TestBean {

    // fields

    private Object object;

    @AnyAnnotation
    private String string;

    private int primitiveInteger;

    @FieldAnnotation
    private Integer integer;

    private List<Double> doubleList;

    // field access

    @Override
    public Object getObject() {
        return object;
    }

    @Override
    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    @AnyAnnotation
    public String getString() {
        return string;
    }

    @Override
    @AnyAnnotation
    public void setString(String string) {
        this.string = string;
    }

    @Override
    public int getPrimitiveInteger() {
        return primitiveInteger;
    }

    @Override
    public void setPrimitiveInteger(int primitiveInteger) {
        this.primitiveInteger = primitiveInteger;
    }

    @Override
    @GetAnnotation
    public Integer getInteger() {
        return integer;
    }

    @Override
    @SetAnnotation
    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    @Override
    public List<Double> getDoubleList() {
        return doubleList;
    }

    @Override
    public void setDoubleList(List<Double> doubleList) {
        this.doubleList = doubleList;
    }

}
