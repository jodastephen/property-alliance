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

/**
 * The Property Alliance project is an effort to create a simple library
 * to abstract access to properties on Java Beans and other data objects.
 * <p>
 * The "Joda" namespace is currently used as a placeholder.
 * <p>
 * This package provides an abstraction that allows simple objects to be converted
 * to and from standard format strings. For example, a {@link java.time.LocalDate}
 * would be represented as a string by the ISO-8601 date format.
 * To be a valid conversion, each unique state of the object must be able to be
 * represented as a unique string. This ensures that the process is fully round-trip.
 * The annotations are used to provide an easy mechanism to specify which methods
 * are to be used to convert to and from a standard format string.
 * The primary manager class is {@link org.joda.pa.convert.StringConvert}.
 */
package org.joda.pa.convert;
