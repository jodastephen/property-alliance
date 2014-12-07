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
package org.joda.pa.convert;

/**
 * Manager for conversion to and from a {@code String}, acting as the main client interface.
 * <p>
 * Support is provided for conversions based on the {@link StringConverter} interface
 * or the {@link ToString} and {@link FromString} annotations.
 * <p>
 * This class is thread-safe with concurrent caches.
 */
public final class StringConvert {

    /**
     * An immutable global instance.
     * <p>
     * This instance cannot be added to using {@link #register}, however annotated classes
     * are picked up. To register your own converters, simply create an instance of this class.
     */
    public static final StringConvert INSTANCE = new StringConvert();

    //-----------------------------------------------------------------------
    /**
     * Creates a new conversion manager.
     */
    public StringConvert() {
    }

    //-----------------------------------------------------------------------
    /**
     * Converts the specified object to a {@code String}.
     * <p>
     * This uses {@link #findConverter} to provide the converter.
     * 
     * @param object  the object to convert, null returns null
     * @return the converted string, may be null
     * @throws RuntimeException (or subclass) if unable to convert
     */
    public String convertToString(Object object) {
        if (object == null) {
            return null;
        }
        Class<?> cls = object.getClass();
        return findConverter(cls).convertToString(object);
    }

    /**
     * Converts the specified object to a {@code String}.
     * <p>
     * This uses {@link #findConverter} to provide the converter.
     * The class can be provided to select a more specific converter.
     * 
     * @param cls  the class to convert from, not null
     * @param object  the object to convert, null returns null
     * @return the converted string, may be null
     * @throws RuntimeException (or subclass) if unable to convert
     */
    public String convertToString(Class<?> cls, Object object) {
        if (object == null) {
            return null;
        }
        return findConverter(cls).convertToString(object);
    }

    /**
     * Converts the specified object from a {@code String}.
     * <p>
     * This uses {@link #findConverter} to provide the converter.
     * 
     * @param cls  the class to convert to, not null
     * @param str  the string to convert, null returns null
     * @return the converted object, may be null
     * @throws RuntimeException (or subclass) if unable to convert
     */
    public Object convertFromString(Class<?> cls, String str) {
        if (str == null) {
            return null;
        }
        return findConverter(cls).convertFromString(cls, str);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if a suitable converter exists for the type.
     * <p>
     * This performs the same checks as the {@code findConverter} methods.
     * Calling this before {@code findConverter} will cache the converter.
     * <p>
     * Note that all exceptions, including developer errors are caught and hidden.
     * 
     * @param cls  the class to find a converter for, null returns false
     * @return true if convertible
     */
    public boolean isConvertible(Class<?> cls) {
        try {
            return cls != null && findConverterQuiet(cls) != null;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    /**
     * Finds a suitable converter for the type.
     * 
     * @param <T>  the type of the converter
     * @param cls  the class to find a converter for, not null
     * @return the converter, not null
     * @throws RuntimeException (or subclass) if no converter found
     */
    public StringConverter findConverter(Class<?> cls) {
        StringConverter conv = findConverterQuiet(cls);
        if (conv == null) {
            throw new IllegalStateException("No registered converter found: " + cls);
        }
        return conv;
    }

    /**
     * Finds a converter searching registered and annotated.
     * 
     * @param <T>  the type of the converter
     * @param cls  the class to find a method for, not null
     * @return the converter, null if no converter
     * @throws RuntimeException if invalid
     */
    private StringConverter findConverterQuiet(Class<?> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("Class must not be null");
        }
        return null; // TODO: implement registering of converters
    }

    //-----------------------------------------------------------------------
    /**
     * Registers a converter factory.
     * <p>
     * This will be registered ahead of all existing factories.
     * <p>
     * No new factories may be registered for the global singleton.
     * 
     * @param factory  the converter factory, not null
     * @throws IllegalStateException if trying to alter the global singleton
     */
    public void registerFactory(StringConverterFactory factory) {
        if (factory == null) {
            throw new IllegalArgumentException("Factory must not be null");
        }
        if (this == INSTANCE) {
            throw new IllegalStateException("Global singleton cannot be extended");
        }
        // TODO: register
    }

    /**
     * Registers a converter for a specific type.
     * <p>
     * The converter will be used for subclasses unless overidden.
     * <p>
     * No new converters may be registered for the global singleton.
     * 
     * @param <T>  the type of the converter
     * @param cls  the class to register a converter for, not null
     * @param converter  the String converter, not null
     * @throws IllegalArgumentException if the class or converter are null
     * @throws IllegalStateException if trying to alter the global singleton
     */
    public void register(StringConverter converter) {
        if (converter == null) {
            throw new IllegalArgumentException("StringConverter must not be null");
        }
        if (converter.getEffectiveType() == null) {
            throw new IllegalArgumentException("Effective type must not be null");
        }
        if (this == INSTANCE) {
            throw new IllegalStateException("Global singleton cannot be extended");
        }
        // TODO: register
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a simple string representation of the object.
     * 
     * @return the string representation, never null
     */
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
