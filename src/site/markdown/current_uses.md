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

[Genson](http://owlike.github.io/genson/)

* Uses getter/setter pattern and no-args constructors.
* Also uses fields.
* Non public getters/setters/fields can be used.
* Can use constructors, handling immutable objects, either by annotation or bytecode analysis. [source](https://github.com/owlike/genson/blob/master/genson/src/main/java/com/owlike/genson/reflect/BeanCreator.java)
* Can use factories, handling immutable objects, by annotation.
* Serialize/deserialize to/from bean [source](https://github.com/owlike/genson/blob/master/genson/src/main/java/com/owlike/genson/reflect/BeanDescriptor.java).
* Property [mutator](https://github.com/owlike/genson/blob/master/genson/src/main/java/com/owlike/genson/reflect/PropertyMutator.java)
and [accessor](https://github.com/owlike/genson/blob/master/genson/src/main/java/com/owlike/genson/reflect/PropertyAccessor.java).
* [Property](https://github.com/owlike/genson/blob/master/genson/src/main/java/com/owlike/genson/reflect/BeanProperty.java).

JSR-353

* Does not appear to use JavaBeans.

JAXB

* General purpose XML serialization.
* Getter/setter pattern.
* Direct field access can be used.
* TODO

[JIBX](http://jibx.sourceforge.net/)

* TODO

[BeanIO](http://beanio.org/)

* Support for XML, CSV, delimited and fixed length stream formats
* XML, Java annotations or builder API based field mapping
* General abstraction over beans and properties
* Property accessor works on `PropertyDescriptor`, constructor parameters, fields and maps

```
public interface Property {
  int type();  // simple, complex, array, collection, map, aggregation array/collection
  String getName();
  void clearValue(ParsingContext context);
  Object createValue(ParsingContext context);
  Object getValue(ParsingContext context);
  void setValue(ParsingContext context, Object value);
  boolean defines(Object value);
  boolean isIdentifier();
  void setIdentifier(boolean identifier);
  PropertyAccessor getAccessor();
  void setAccessor(PropertyAccessor accessor);
  Class<?> getType();
  void setType(Class<?> type);
}
```

```
public interface PropertyAccessor {
  Object getValue(Object bean);
  void setValue(Object bean, Object value);
  boolean isConstructorArgument();
  int getConstructorArgumentIndex();
}
```


## Database tools

Hibernate

* Tool to generate [JPA metamodel](http://hibernate.org/orm/tooling/)
* TODO

[UJORM](http://ujorm.org/)

* [Key-Value](http://ujorm.org/sample/key-value.html)
* TODO

JPA

DevWorks on [JPA metamodel](http://www.ibm.com/developerworks/java/library/j-typesafejpa/)


## Bean mapping tools

[MapStruct](http://mapstruct.org)

* General purpose mapper, operating by annotation processor.
* Uses getter/setter pattern and no-args constructors.
* Can use getters for collections as effective setters.
* Can use adders for collections as effective setters.
* Can match simple plural property names for collections.
* Additional coding handles immutable objects.

[Orika](http://orika-mapper.github.io/orika-docs/)

* General purpose mapper, operating by Javassist.
* Uses java.beans Introspector, with additional handling. [Code](https://code.google.com/p/orika/source/browse/trunk/core/src/main/java/ma/glasnost/orika/property/IntrospectorPropertyResolver.java?r=505).
* Has own [Property](https://code.google.com/p/orika/source/browse/trunk/core/src/main/java/ma/glasnost/orika/metadata/Property.java?r=505) class.
* Uses getter/setter pattern.
* Can be specified to use constructors, using Paranamer.
* Has plugin point for factories and unusual constructors.
* Can access list indices and map keys.
* Can use non-standard methods via stringly-typed syntax or builder.

[Dozer](http://dozer.sourceforge.net/)

* General purpose mapper, operating by Commons BeanUtils.
* Eclipse GUI for editing XML based mapping files, annotation/Java config too.
* Uses getter/setter pattern and no-args constructors.
* Support for factories, details [unclear](http://dozer.sourceforge.net/documentation/customCreateMethod.html).
* Can access private fields.
* Handles custom getter/setter names.
* Handles adder methods.
* Property access [source code](https://github.com/DozerMapper/dozer/tree/master/core/src/main/java/org/dozer/propertydescriptor)

[JMapper](https://code.google.com/p/jmapper-framework/)

* General purpose mapper, operating by Javassist.
* Uses getter/setter pattern and no-args constructors.
* Unable to find source code for introspecting.

[EZMorph](http://ezmorph.sourceforge.net/)

* General purpose mapper, operating by Commons BeanUtils.
* Handles DynaBeans.
* Uses getter/setter pattern and no-args constructors.
* Little documentation, last release 2008.
* Property access [source code](http://ezmorph.cvs.sourceforge.net/viewvc/ezmorph/ezmorph/src/main/java/net/sf/ezmorph/bean/BeanMorpher.java?revision&#61;1.5&#38;view&#61;markup)

[Morph](http://morph.sourceforge.net/)

* General purpose mapper, with abstraction of bean access.
* Uses getter/setter pattern and no-args constructors, and other approaches.
* Has a [BeanReflector](http://morph.sourceforge.net/xref/net/sf/morph/reflect/BeanReflector.html)
* Also has a [ContainerReflector](http://morph.sourceforge.net/xref/net/sf/morph/reflect/ContainerReflector.html)
* Many [reflector implementations](http://morph.sourceforge.net/xref/net/sf/morph/reflect/reflectors/package-summary.html)
* The [JavaBean reflector](http://morph.sourceforge.net/xref/net/sf/morph/reflect/reflectors/ObjectReflector.html)
* Simulates java.beans [introspector](http://morph.sourceforge.net/xref/net/sf/morph/reflect/support/ReflectionInfo.html)
* Reflector has similarities to Beans v2.0.
* Last release 2008.

[ModelMapper](http://modelmapper.org/)

* General purpose mapper.
* Uses getter/setter pattern and no-args constructors.
* Providers allow for other object creation approaches, but still needs setters.
* Can match non-public methods and fields.
* Can read data from non-beans using [ValueReader](https://github.com/jhalterman/modelmapper/blob/master/core/src/main/java/org/modelmapper/spi/ValueReader.java).
* Has [PropertyInfo](https://github.com/jhalterman/modelmapper/blob/master/core/src/main/java/org/modelmapper/spi/PropertyInfo.java).

[Transmorph](https://github.com/cchabanois/transmorph)

* General purpose mapper, operating by reflection.
* Uses getter/setter pattern and no-args constructors. [source](https://github.com/cchabanois/transmorph/blob/master/src/main/java/net/entropysoft/transmorph/converters/beans/BeanToBean.java)
* Simulates java.beans [introspector](https://github.com/cchabanois/transmorph/blob/master/src/main/java/net/entropysoft/transmorph/utils/BeanUtils.java)
* Can map bean to/from Map.
* Little documentation, last release 2013.

[OTOM](https://java.net/projects/otom)

* General purpose mapper.
* GUI tool that generates Java source code.
* Uses getter/setter pattern and no-args constructors.
* Uses java.beans [Introspector](https://java.net/projects/otom/sources/svn/content/trunk/src/java/otom/mapping/MappingUtils.java?rev=42).
* Little documentation, last commit 2005.

[Smooks](http://www.smooks.org/mediawiki/index.php?title=Main_Page)

* General purpose data event based data transformation.
* Uses getter/setter pattern and no-args constructors.
* Support for factory methods to create objects.
* Some support for setting fields directly.


## Web GUIs

[Apache ISIS](http://isis.apache.org/index.html)

* Beans to web GUI (naked objects)
* Supports standard [getters and setters](http://isis.apache.org/how-tos/how-to-01-030-How-to-add-a-property-to-a-domain-entity.html)
* Supports [collections and lists but not maps](http://isis.apache.org/how-tos/how-to-01-050-How-to-add-a-collection-to-a-domain-entity.html)
* Uses annotations to add some additional control of what is exposed

[FXForm2](https://github.com/dooApp/FXForm2)

* Beans to JavaFX GUI
* Relies on [reflection of Field instances](https://github.com/dooApp/FXForm2/blob/master/core/src/main/java/com/dooapp/fxform/reflection/ReflectionUtils.java)

[Vaadin](https://vaadin.com/home)

* [Property class](https://vaadin.com/api/7.3.6/com/vaadin/data/Property.html)
* interface over type 2 or 3, implementations provided to access bean via methods or to hold state

```
  public interface Property<T> {
    getType()
    getValue()
    isReadOnly()
    setReadOnly(boolean flag)
    setValue(T value) 
  }
```

[Tapestry](http://tapestry.apache.org/)

* Can [generate](http://jumpstart.doublenegative.com.au/jumpstart7/examples/lang/annotations) getter/setter by
[annotation](http://tapestry.apache.org/5.3/apidocs/org/apache/tapestry5/annotations/Property.html)

[Direct web remoting](http://directwebremoting.org/dwr/index.html)

* [Property interface](http://directwebremoting.org/dwr/javadoc/org/directwebremoting/extend/Property.html)
* interface of type 1
* implementations for getter/setter pair, field, one parameter (in a constructor or method), and a stateful one?
* also has type conversion
* located in an advanced package, not for general use

```
public interface Property {
  Property createChild(int index)  // access nested generic type info of List<Map<String, Integer>>
  String getName()
  Class<?> getPropertyType()
  Object getValue(Object bean)
  void setValue(Object bean, Object value)
}
```


## Templating tools

[Freemarker](http://freemarker.org/)

* Abstracts data access, one implementation is based on JavaBeans.
* Uses getters.
* Uses java.beans Introspector. [source](https://github.com/freemarker/freemarker/blob/2.3-gae/src/main/java/freemarker/ext/beans/ClassIntrospector.java#L304)

Velocity

* TODO


## Utilities

[Spring BeanUtils](http://docs.spring.io/spring/docs/2.5.x/api/org/springframework/beans/BeanUtils.html)

[Commons BeanUtils](http://commons.apache.org/proper/commons-beanutils/)

[JUEL](http://juel.sourceforge.net/)

[MVEL](http://mvel.codehaus.org/)

[OGNL](http://commons.apache.org/proper/commons-ognl/)

* Used internally by other projects, such as [Thymeleaf](http://www.thymeleaf.org/doc/standarddialect5minutes.html)

Bean validation

* Solution must work with bean validation

[JBeans](http://jbeans.sourceforge.net/index.html)

* Package to replace `java.beans`
* Includes type 1 property
* [Javadoc](http://jbeans.sourceforge.net/javadoc/index.html)


## Other

[Integrated data viewer](https://www.unidata.ucar.edu/software/idv/)

* type 2/3 property interface

```
public interface Property {
  void addPropertyChangeListener(PropertyChangeListener listener)
  void addVetoableChangeListener(VetoableChangeListener listener)
  void clearValue()
  String getName()
  Object getSourceBean()
  Object getValue()
  boolean isReporting()
  boolean isVetoable()
  void notifyListeners()
  void removePropertyChangeListener(PropertyChangeListener listener)
  void removeVetoableChangeListener(VetoableChangeListener listener)
  void setReporting(boolean reportChanges)
  void setValue(Object newValue)
  void setValueAndNotifyListeners(Object newValue)
}
```

[Atomikos](http://www.atomikos.com/Main/TransactionsEssentials)

* type 2/3 [property interface](http://www.atomikos.com/downloads/transactions-essentials/com/atomikos/AtomikosTransactionsEssentials/javadoc/3.7/com/atomikos/beans/Property.html)

```
public interface Property {
  String[] getAllowedValues()
  String getDescription()
  Editor getEditor()
  IndexedProperty getIndexedProperty()
  String getName()
  Class getType()
  Object getValue()
  boolean isExpert()
  boolean isHidden()
  boolean isPreferred()
  boolean isReadOnly()
  void setValue(Object arg) 
}
```

[Apache CXF](http://cxf.apache.org/)


## Programming languages

Java

* [Bug 5043025](http://bugs.java.com/bugdatabase/view_bug.do?bug_id=5043025), method/field literals

Groovy - TODO

[Gosu](http://gosu-lang.github.io/)

* [Feature literals](http://guidewiredevelopment.wordpress.com/2011/03/03/feature-literals/)
* [Feature literals 2](http://guidewiredevelopment.wordpress.com/2011/06/05/feature-literals-enhancements-blocks-win/)

