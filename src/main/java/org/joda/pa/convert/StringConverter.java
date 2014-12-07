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
 * Interface defining conversion to and from a standard format {@code String}.
 * <p>
 * The purpose of this package is to provide round-trip conversion to and from a string.
 * No mechanism is provided for controlling the format of the output string.
 * <p>
 * This is an interface and must be implemented with care.
 * Implementations must be immutable and thread-safe.
 */
public interface StringConverter {

    /**
     * Converts the specified object to a {@code String}.
     * <p>
     * Each unique state of the object must be represented by a unique string.
     * This permits the conversion to be fully round-trip.
     * 
     * @param object  the object to convert, not null
     * @return the converted string, may be null but generally not
     */
    String convertToString(Object object);

    /**
     * Converts the specified object from a {@code String}.
     * <p>
     * Each standard format string corresponds to a unique state of the object.
     * This permits the conversion to be fully round-trip.
     * 
     * @param cls  the class to convert to, not null
     * @param str  the string to convert, not null
     * @return the converted object, may be null but generally not
     */
    Object convertFromString(Class<?> cls, String str);

    /**
     * Gets the effective type that the converter works on.
     * <p>
     * For example, if a class declares both {@code @FromString} and  {@code @ToString}
     * then the effective type of the converter is that class. If a subclass is
     * queried for a converter, then the effective type is that of the superclass.
     * 
     * @return the effective type
     */
    Class<?> getEffectiveType();

}
