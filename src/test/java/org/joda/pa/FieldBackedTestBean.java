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
