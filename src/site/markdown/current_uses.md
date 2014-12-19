## Current uses of JavaBeans

This document is an attempt to capture as many uses of JavaBeans v1.0 as possible.
If you know of an open source project (or an interesting closed source one) that makes use of the
JavaBeans v1.0 spec in some way, we'd love to have it listed here.


## Serialization tools

[Jackson](https://github.com/FasterXML/jackson).

* General purpose, widely used, serialization, originally JSON, but now many formats.
* Getter/setter pattern for basic [data binding](http://wiki.fasterxml.com/JacksonDataBinding).
* Annotations can be used for extra control.
* Can use getters for collections as effective setters.
* Direct field access can be used.
* Non public getters/setters can be used.
* Constructors/factories handled using annotations. See [creators](http://wiki.fasterxml.com/JacksonFeatureCreators).
* Can generate bytecode to avoid reflection. See [afterburner](https://github.com/FasterXML/jackson-module-afterburner).
* Has "property missing" support.

JAXB

* General purpose XML serialization.
* Getter/setter pattern.
* Direct field access can be used.
* TODO


## Database tools

Hibernate - TODO


## Bean mapping tools

[MapStruct](http://mapstruct.org)

* General purpose bean mapper, operating by annotation processor.
* Uses getter/setter pattern.
* Can use getters for collections as effective setters.
* Can use adders for collections as effective setters.
* Can match simple plural property names for collections.
* Additional coding handles immutable objects.

[Orika](http://orika-mapper.github.io/orika-docs/)

* General purpose bean mapper, operating by Javassist.
* Uses java.beans Introspector, with additional handling. [Code](https://code.google.com/p/orika/source/browse/trunk/core/src/main/java/ma/glasnost/orika/property/IntrospectorPropertyResolver.java?r=505).
* Has own [Property](https://code.google.com/p/orika/source/browse/trunk/core/src/main/java/ma/glasnost/orika/metadata/Property.java?r=505) class.
* Uses getter/setter pattern.
* Can be specified to use constructors, using Paranamer.
* Has plugin point for factories and unusual constructors.
* Can access list indices and map keys.
* Can use non-standard methods via stringly-typed syntax or builder.


## Programming languages

Groovy - TODO
