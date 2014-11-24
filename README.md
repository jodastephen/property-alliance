Property-Alliance
=================

The *Property Alliance* project is an effort to create a simple library
to abstract access to properties on Java Beans and other data objects.


### Why is this project needed?

Oracle are working on the modularization of the JDK in project Jigsaw.
One of the key problems with modularizing the JDK is the `java.beans` package
used by many systems to provide access to properties. It is a problem because
depending on `java.beans` requires applications to depend on the whole of the
AWT and desktop, even if they just want to reflect on some beans.

The goal of this project is to define a simple alternative to `java.beans`
that could be included in the JDK.
Current users of `java.beans` would be encouraged to move accross to this new API.

The project aims to go slightly further than `java.beans` by defining a better
abstraction for the properties. A careful abstraction will permit external projects
to provide alternative mechanisms of deciding which pieces of data are properties
and which are not. The mechanism could also provide a way for value types to
be abstracted even when they may have no getters and setters.


### License
Licensed under Apache v2.


### Contributions
Please use GitHub issues and Pull Requests to contribute and provide feedback.
