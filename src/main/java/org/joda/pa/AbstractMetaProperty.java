package org.joda.pa;

import java.util.Objects;

/**
 * Abstract superclass to {@link MetaProperty} implementations.
 * 
 * @param <P> the type of the property content
 */
abstract class AbstractMetaProperty<P> implements MetaProperty<P> {

    private final MetaBean metaBean;
    private final String name;
    private final Class<P> propertyTypeToken;
    private final boolean derived;
    private final boolean buildable;
    private final boolean readable;
    private final boolean mutable;

    /**
     * This constructor does not check these arguments.
     * It relies on the calling builder to do so.
     */
    protected AbstractMetaProperty(
            MetaBean metaBean, String name, Class<P> propertyTypeToken,
            boolean derived, boolean buildable,
            boolean readable, boolean mutable) {

        this.metaBean = metaBean;
        this.name = name;
        this.propertyTypeToken = propertyTypeToken;
        this.buildable = buildable;
        this.readable = readable;
        this.mutable = mutable;
        this.derived = derived;
    }

    // (partial) implementation of 'MetaProperty' -----------------------------

    @Override
    public final MetaBean metaBean() {
        return metaBean;
    }

    @Override
    public final String name() {
        return name;
    }

    @Override
    public final Class<P> propertyType() {
        return propertyTypeToken;
    }

    @Override
    public final boolean isBuildable() {
        return buildable;
    }

    @Override
    public final boolean isDerived() {
        return derived;
    }

    @Override
    public final boolean isMutable() {
        return mutable;
    }

    @Override
    public final P get(Object bean) {
        Objects.requireNonNull(bean, "The argument 'bean' must not be null.");

        ensurePropertyCanBeRead();
        ensureBeanHasCorrectType(bean);
        return getFromBean(bean);
    }

    private void ensurePropertyCanBeRead() {
        if (!readable) {
            String message = "This meta-property is write-only.";
            throw new UnsupportedOperationException(message);
        }
    }

    private void ensureBeanHasCorrectType(Object bean) {
        boolean beanHasIncorrectType = !declaringType().isInstance(bean);
        if (beanHasIncorrectType) {
            String message = "The specified bean " + bean + " is of type '"
                    + bean.getClass() + "' which is not assignment compatible"
                    + " with this meta-property's declaring type '"
                    + declaringType() + "'.";
            throw new ClassCastException(message);
        }
    }

    /**
     * Implemented by subclasses to actually get the value from the bean.
     * 
     * @param bean  the bean to query, is not null and of the correct type as returned by {@link #declaringClass()}
     * @return the value of the property on the specified bean, may be null
     */
    protected abstract P getFromBean(Object bean);

    @Override
    public final void set(Object bean, Object value) {
        Objects.requireNonNull(bean, "The argument 'bean' must not be null.");

        ensurePropertyCanBeWritten();
        ensureBeanHasCorrectType(bean);
        P typedValue = ensureValueHasCorrectType(value);
        setToBean(bean, typedValue);
    }

    private void ensurePropertyCanBeWritten() {
        if (!mutable) {
            String message = "This meta-property is read-only.";
            throw new UnsupportedOperationException(message);
        }
    }

    private P ensureValueHasCorrectType(Object value) {
        // TODO do we want a customized exception message?
        return propertyTypeToken.cast(value);
    }

    /**
     * Implemented by subclasses to actually set the value to the bean.
     * 
     * @param bean  the bean to modify, is not null and of the correct type as returned by {@link #declaringClass()}
     * @param value  the value to set to the bean, may be null
     */
    protected abstract void setToBean(Object bean, P value);

    // 'Object' methods -------------------------------------------------------

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((metaBean == null) ? 0 : metaBean.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MetaProperty)) {
            return false;
        }

        @SuppressWarnings("rawtypes")
        MetaProperty other = (MetaProperty) obj;
        if (!Objects.equals(declaringType(), other.declaringType())) {
            return false;
        }
        if (!Objects.equals(name(), other.name())) {
            return false;
        }

        return true;
    }

}
