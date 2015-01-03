## Property-Alliance

This is the home page of a new effort to create a meta-model for Java.
The goal is to include the meta-model in Java SE, to replace `java.beans`.


## What do we mean by "meta-model"?

When we use the term meta-model, we mean providing a mechanism to access information about objects in Java.
The most common meta-model is [reflection](http://docs.oracle.com/javase/8/docs/api/java/lang/reflect/package-summary.html)
via `Class`, `Method` and `Field`.

The second common meta-model is the [JavaBeans specification](http://blog.joda.org/2014/11/the-javabeans-specification.html).
This defines the concept of a *bean* with a set of *properties*.

A bean with properties is a Java class that obeys certain rules. These include:

* a public no-argument constructor
* implementing `Serializable`
* public *getter* and *setter* pairs that provide access to each property on the object

The meta-model for beans is defined by the [`java.beans`](http://docs.oracle.com/javase/8/docs/api/java/beans/package-summary.html)
package in the JDK.
The package provides a way to *introspect* an object to determine what properties the bean has.

The meta-model for a bean is [`BeanDescriptor`](http://docs.oracle.com/javase/8/docs/api/java/beans/BeanDescriptor.html).
Each property is exposed using [`PropertyDescriptor`](http://docs.oracle.com/javase/8/docs/api/java/beans/PropertyDescriptor.html).
Additional information is also available on events, parameters and methods.

The bean meta-model is similar to reflection but sits at a higher level of abstraction.
Instead of the low-level fields and methods with their scopes (private, protected, public),
the bean meta-model exposes only public features.
For example, getter and setters are not treated as just methods but are combined into the higher level property concept.


## Why do we need a new meta-model?

The JavaBeans specification was written in 1997 and is hopelessly out of date,
despite parts of it being widely used.
However, being out of date is *not* the main driver of the project.
Instead the main driver is modularity in JDK 9.

In JDK 9 the `java.beans` package will be located within the "desktop" stack module.
As such, if your application depends on `java.beans`, you will pull in all the "desktop" GUI code.
For a server-side application, this is a huge unnecessary dependency.
By providing a new meta-model to replace `java.beans`, it can be designed to sit within a JDK module
outside the "desktop".

In addition, since this is a new meta-model, the restrictions of JavaBeans can be re-evaluated.
Effectively, this is an opportunity to create [version 2.0](http://blog.joda.org/2014/12/what-might-beans-v20-spec-contain.html)
of the JavaBeans specification.

Most notably, the new meta-model needs to support the wide range of objects we write today:

* handle both mutable and immutable objects
* handle potential future language changes, such as value types
* handle dynamic beans, where the properties are held in a `Map` rather than bytecode fields
* be independent of reflection
* provide access to annotations

Note that the current JavaBeans meta-model is typically only used by framework writers.
However, it is intended to make the new meta-model more developer friendly, so it can be used more widely.


## Source code

The project exists to develop the source code for a future JSR.

* View or checkout the [source code](https://github.com/jodastephen/property-alliance)
* View the [Javadoc](apidocs/index.html)


## Links

These links provide information to support the project.

* [Current uses](current_uses.html) - how beans are being used today in frameworks
* [Java FX properties](java_fx.html) - how Java FX properties work
* [Review of properties discussions](history_properties.html) - history of properties (langauge change attempts)
* [Restored SJSU CS wiki](wiki_home.html) - an important wiki of information on properties

See something missing? Send a [pull request](https://github.com/jodastephen/jodastephen.github.io)!


## Contribute

Join the [Google Group](https://groups.google.com/forum/#!forum/beans-2-meta-model) to help out.
Or contribute via GitHub.

Please note that [contributions](https://github.com/jodastephen/property-alliance/blob/master/CONTRIBUTING.md)
are intended for inclusion in a future JSR.

