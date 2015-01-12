package org.joda.pa;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.stream.Stream;

// TODO document
class InheritingMetaProperty<B, P> implements MetaProperty<B, P> {

    private final MetaProperty<? super B, P> inheritedProperty;
    private final MetaBean<B> metaBean;

    public InheritingMetaProperty(
            MetaProperty<? super B, P> inheritedProperty, MetaBean<B> metaBean) {

        this.inheritedProperty = inheritedProperty;
        this.metaBean = metaBean;
    }

    @Override
    public MetaBean<B> metaBean() {
        return metaBean;
    }

    @Override
    public String name() {
        return inheritedProperty.name();
    }

    @Override
    public Class<B> declaringType() {
        return metaBean.beanType();
    }

    @Override
    public Class<P> propertyType() {
        return inheritedProperty.propertyType();
    }

    @Override
    public Type propertyGenericType() {
        return inheritedProperty.propertyGenericType();
    }

    @Override
    public Stream<Annotation> annotations() {
        return inheritedProperty.annotations();
    }

    @Override
    public <A extends Annotation> Stream<A> annotations(Class<A> annotationType) {
        return inheritedProperty.annotations(annotationType);
    }

    @Override
    public boolean isDerived() {
        return inheritedProperty.isDerived();
    }

    @Override
    public boolean isBuildable() {
        return inheritedProperty.isBuildable();
    }

    @Override
    public boolean isMutable() {
        return inheritedProperty.isMutable();
    }

    @Override
    public P get(Object bean) {
        return inheritedProperty.get(bean);
    }

    @Override
    public void set(Object bean, Object value) {
        inheritedProperty.set(bean, value);
    }

}
