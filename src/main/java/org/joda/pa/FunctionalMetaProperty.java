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
 * @param <P> the type of the property content
 */
class FunctionalMetaProperty<P> extends AbstractMetaProperty<P> {

    private final Function<Object, P> getValue;
    private final BiConsumer<Object, P> setValue;
    private final Supplier<Stream<Annotation>> getAnnotations;

    /**
     * This constructor does not check these arguments.
     * It relies on the calling builder to do so.
     */
    FunctionalMetaProperty(
            MetaBean metaBean, String name, Class<P> propertyTypeToken,
            boolean derived, boolean buildable,
            Function<Object, P> getValue, BiConsumer<Object, P> setValue,
            Supplier<Stream<Annotation>> getAnnotations) {

        super(metaBean, name, propertyTypeToken,
                derived, buildable, isReadable(getValue), isMutable(setValue));

        this.getValue = getValue;
        this.setValue = setValue;
        this.getAnnotations = getAnnotations;
    }

    private static boolean isReadable(Function<?, ?> getValue) {
        return getValue != null;
    }

    private static boolean isMutable(BiConsumer<?, ?> setValue) {
        return setValue != null;
    }

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
