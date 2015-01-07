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

import java.lang.annotation.Annotation;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A {@link MetaProperty} which delegates calls to functional interfaces
 * (provided during construction) to get/set values and access annotations.
 * 
 * @param <B> the type associated with the meta-bean that defines this meta-property 
 * @param <P> the type of the property content
 */
class FunctionalMetaProperty<B, P> extends AbstractMetaProperty<B, P> {

    private final Function<Object, P> getValue;
    private final BiConsumer<Object, P> setValue;
    private final Supplier<Stream<Annotation>> getAnnotations;

    /**
     * This constructor does not check these arguments.
     * It relies on the calling builder to do so.
     */
    FunctionalMetaProperty(
            MetaBean<B> metaBean, String name, Class<P> propertyTypeToken,
            boolean derived, boolean buildable,
            Function<Object, P> getValue, BiConsumer<Object, P> setValue,
            Supplier<Stream<Annotation>> getAnnotations) {

        super(metaBean, name, propertyTypeToken,
                derived, buildable, isReadable(getValue), isMutable(setValue));

        this.getValue = getValue;
        this.setValue = setValue;
        this.getAnnotations = getAnnotations;
    }

    //-----------------------------------------------------------------------
    private static boolean isReadable(Function<?, ?> getValue) {
        return getValue != null;
    }

    private static boolean isMutable(BiConsumer<?, ?> setValue) {
        return setValue != null;
    }

    //-----------------------------------------------------------------------
    @Override
    public Stream<Annotation> annotations() {
        return getAnnotations.get();
    }

    @Override
    protected P getFromBean(Object bean) {
        return getValue.apply(bean);
    }

    @Override
    protected void setToBean(Object bean, P value) {
        setValue.accept(bean, value);
    }

}
