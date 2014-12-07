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
 * This package provides the main abstraction for data objects, often known as beans.
 * The primary interface is {@link org.joda.pa.MetaBean} which provides access to
 * the {@link org.joda.pa.MetaProperty} instances. Objects are created using
 * {@link org.joda.pa.BeanBuilder}, allowing both mutable and immutable objects to be created.
 * Implementing the {@link org.joda.pa.Bean} interface is optional, but does provide
 * a hook for the manager to determine what the meta-bean and properties are for a type.
 */
package org.joda.pa;
