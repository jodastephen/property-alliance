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
* Uses getter/setter pattern and no-args constructors.
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

[Dozer](http://dozer.sourceforge.net/)

* General purpose bean mapper, operating by Commons BeanUtils.
* Eclipse GUI for editing XML based mapping files, annotation/Java config too.
* Uses getter/setter pattern and no-args constructors.
* Support for factories, details [unclear](http://dozer.sourceforge.net/documentation/customCreateMethod.html).
* Can access private fields.
* Handles custom getter/setter names.
* Handles adder methods.
* Property access [source code](https://github.com/DozerMapper/dozer/tree/master/core/src/main/java/org/dozer/propertydescriptor)

[JMapper](https://code.google.com/p/jmapper-framework/)

* General purpose bean mapper, operating by Javassist.
* Uses getter/setter pattern and no-args constructors.
* Unable to find source code for introspecting.

[EZMorph](http://ezmorph.sourceforge.net/)

* General purpose bean mapper, operating by Commons BeanUtils.
* Handles DynaBeans.
* Uses getter/setter pattern and no-args constructors.
* Little documentation, last release 2008.
* Property access [source code](http://ezmorph.cvs.sourceforge.net/viewvc/ezmorph/ezmorph/src/main/java/net/sf/ezmorph/bean/BeanMorpher.java?revision=1.5&view=markup)

[Morph](http://morph.sourceforge.net/)

* General purpose bean mapper, with abstraction of bean access.
* Uses getter/setter pattern and no-args constructors, and other approaches.
* Has a [BeanReflector](http://morph.sourceforge.net/xref/net/sf/morph/reflect/BeanReflector.html)
* Also has a [ContainerReflector](http://morph.sourceforge.net/xref/net/sf/morph/reflect/ContainerReflector.html)
* Many [reflector implementations](http://morph.sourceforge.net/xref/net/sf/morph/reflect/reflectors/package-summary.html)
* The [JavaBean reflector](http://morph.sourceforge.net/xref/net/sf/morph/reflect/reflectors/ObjectReflector.html)
* Simulates java.beans [introspector](http://morph.sourceforge.net/xref/net/sf/morph/reflect/support/ReflectionInfo.html)
* Reflector has similarities to Beans v2.0.
* Last release 2008.

[ModelMapper](http://modelmapper.org/)

* General purpose bean mapper.
* Uses getter/setter pattern and no-args constructors.
* Providers allow for other object creation approaches, but still needs setters.
* Can match non-public methods and fields.
* Can read data from non-beans using [ValueReader](https://github.com/jhalterman/modelmapper/blob/master/core/src/main/java/org/modelmapper/spi/ValueReader.java).
* Has [PropertyInfo](https://github.com/jhalterman/modelmapper/blob/master/core/src/main/java/org/modelmapper/spi/PropertyInfo.java).

[Transmorph](https://github.com/cchabanois/transmorph)

[OTOM](https://java.net/projects/otom)

[Smooks](http://www.smooks.org/mediawiki/index.php?title=Main_Page)


## Utilities

[Spring BeanUtils](http://docs.spring.io/spring/docs/2.5.x/api/org/springframework/beans/BeanUtils.html)

[Commons BeanUtils](http://commons.apache.org/proper/commons-beanutils/)

[JUEL](http://juel.sourceforge.net/)


## Programming languages

Groovy - TODO
