## History of JavaBeans and Properties discussions

This document is an attempt to capture discussions about properties and beans.


## Property types

[Property terminology](http://blog.joda.org/2007/10/java-7-properties-terminology_6780.html), Oct 2007

* Type 1 = bean-independent (per-class, meta-property) - stateless and mainly for tooling
* Type 2 = bean-attached (per-instance) - stateless property, value on the bean
* Type 3 = stateful (property object)
* Type 2 and 3 have same/similar API so good abstraction can get both


## Forums

Wikipedia

* [Properties in different languages](http://en.wikipedia.org/wiki/Property_%28programming%29)


Javalobby

* [JGoodies Binding](http://www.javalobby.org/java/forums/t17672),
Mar 2005,
  * not especially relevant, but [code](https://java.net/projects/binding) still around
* [Main discussion based on Ideas from Danny Coward](http://www.javalobby.org/java/forums/t88090.html?start=0),
Jan 2007, 136 comments, IMPORTANT,
immediate expansion to read-only, write-only, bound.
  * Danny Coward: "simplicity, readability and productivity are always key considerations to look at",
  * Ray Cromwell: "The issue isn't just typing the boilerplate, it's *reading it*.",
  * Ray Cromwell: why not get IDEs to do job rather than language change,
  * Marc Stock: getters/setters "should be like the default constructor...it's just there even though you can't see it",
  * Stephen Colebourne: misses opportunity of fixing stringly-typed property names in frameworks,




* [Why Java Properties are BAD, ugly and uncomfortable](http://www.javalobby.org/java/forums/t90756.html),
Feb 2007, 96 comments,
TODO
* [Pretty Please, we need Properties. Here's the Middle Ground](http://www.javalobby.org/java/forums/t90862.html),
Feb 2007, 59 comments,
TODO
* [Beans Binding (JSR 295) & Properties on JDK7 ](http://www.javalobby.org/java/forums/t101998.html),
Sep 2007, 14 comments,
  * author unclear of benefits of property language feature (re paradigm shift issue),
  * Richard Bair: "there will be a significant portion of properties (20%?) that will require custom code",
  * lots of consideration of lines of code, conceptual shift seems hard to grasp
* [Bean-dependent or Bean-independent Properties?](http://www.javalobby.org/java/forums/t102115.html),
Oct 2007, 50 comments,
  * various discussions and confusions, little about syntax, lots about bean-properties project,
  * reminder of importance of collections to property querying
* [Typesafe property literals](http://java.dzone.com/articles/typesafe-property-literals),
May 2014,
  * "We found that once you have these literals, they just turn out to be useful in many cases.",
  * uses Lombok to generate


JavaPosse

* [Discussion 1](https://groups.google.com/forum/#!topic/javaposse/gA_YdNk2bfI) and
[Discussion 2(https://groups.google.com/forum/#!topic/javaposse/pJqu8Z0O_SE),
  * neither that interesting
* [Episode 145](http://javaposse.com/java_posse_145_newscast_for_oct_4th_2007),
Oct 2007,
  * Joe Nuxoll defines language level property as (based on Pascal, C#, C++) statically typed member (like field)
  * with behaviour (ability to control getter/setter)
  * can have non-public properties but generally public
  * JavaBeans is a pattern/convention
  * language-level property would be part of reflection
  * write components not classes (properties and events, go together)
  * can be virtual/calculated
  * property is encapsulated, hides internals of storage of the value
  * Suggests beans-binding misusing word "properties", discusses how binding works
  * Two competing binding APIs in Sun (beans-binding and data-provider, ended up in NetBeans)
  * Claims component-based is a paradigm shift from regular object-oriented
  * meta-data critical to enable tooling
  * Emphasises can't really do properties without events and vice versa
  * Single location for Javadoc and annotations
  * Once had Javadoc version that merged getters and setters into properties section


Artima

* [No getters and setters](http://www.artima.com/forums/flat.jsp?forum=270&thread=247837),
Jan 2009,  Javier Paniza,
responding to no properties in Java 7,
proposes AOP to rewrite field access via getter/setter


## Blogs

Alex Miller

* [Java 7 property links](http://tech.puredanger.com/java7#property), lots of links
* [Opinion](http://tech.puredanger.com/2007/01/26/java7-property/),
Jan 2007,
argues that public field access should be rewritten to be via getters thus creating properties,
comments include anti-mutable and anti-encapsulation,
comments include swing binding is bigger problem than getter/setter generation


Stephen Colebourne

* [Property objects](http://blog.joda.org/2007/01/java-7-property-objects_540.html)
Jan 2007, 20 comments,
proposes objects for properties (property and meta-property),
proposes generating `propertySurname()` alongside `getSurname()` and `setSurname()`,
focus on binding.
* [More on property objects](http://blog.joda.org/2007/01/more-detail-on-property-literals_3217.html),
Jan 2007, 5 comments,
uses person->surname to access property object and Person->surname to access meta-property,
discusses reflection vs property objects as fields.
comments discuss meta-property vs property and use of property for "isDirty" or "originalValue".
* [Bean keyword](http://blog.joda.org/2007/01/keyword_5910.html),
Jan 2007, 6 comments,
discusses using `bean` instead of `class` to allow syntax change within.
* [Update on properties](http://blog.joda.org/2007/09/java-7-update-on-properties_179.html),
Sep 2007, 10 comments,
discusses beans-binding JSR and Remi's proprty compiler code
* [Beans and properties](http://blog.joda.org/2011/05/beans-and-properties_1963.html),
May 2011, 22 comments,
review of JavaFX properties,
comments include desire for multi-level propeties (`person.address.city`)


Matt Hicks 

* [Discussion](http://www.sgine.org/2010/08/properties-discussion.html),
Aug 2010,
suggests type 3 properties,
`public final surname = new Property<String>(null)`,
`person.surname.set("Foo")`, `String surname = person.surname.get()`,
lots about Scala,
"major reason to use the Property concept instead of methods ... is encapsulation"
* Beans should be deprecated
[part 1](http://www.sgine.org/2010/09/beans-should-be-deprecated-future-of.html) and
[part 2](http://www.sgine.org/2010/09/beans-should-be-deprecated-future-of_08.html),
Jul 2009, 9 comments,
outlines sgine property approach,
create paths of properties, binding, container for properties and dependent properties
* [Death of beans](http://www.matthicks.com/2009/07/death-of-beans.html),
Jul 2009, 7 comments,
argues for type 3 properties


Mike Heinrich 

* [Creating JavaFX properties](http://blog.netopyr.com/2011/05/19/creating-javafx-properties/),
May 2011,
JavaFX properties


Cay Horstmann

* [Arrows in the back](https://weblogs.java.net/blog/cayhorstmann/archive/2007/01/arrows_in_the_b.html),
Jan 2007,
discusses feedback to the properties topic
* [Properties are design features](https://weblogs.java.net/blog/cayhorstmann/archive/2007/01/properties_are.html),
Jan 2007,
argues that design intent is lost without properties (getter/setter don't have same intent when read)
* [](https://weblogs.java.net/blog/cayhorstmann/archive/2007/01/pie_in_the_sky.html),
Jan 2007, IMPORTANT
proposes new property access different from getters/setters (using a symbol of some kind),
has good table of considerations
* [Altered javac](http://web.archive.org/web/20091023160115/http://geocities.com/adcalves/javaproperty/),
Oct 2009,
uses `String s = person.@surname`, `person.@surname = "Foo"` notation,
proposes `private String @surname` as possible declaration.
* [MSc Thesis](http://web.archive.org/web/20091027074331/http://geocities.com/adcalves/javaproperty/index_files/Thesis.pdf),
Oct 2009,
pdf,
`@Property(getter = "getSurname", setter = "setSurname")`
* [Properties get no respect](https://weblogs.java.net/blog/cayhorstmann/archive/2007/12/properties_get_1.html),
bit of a rant with link to wiki
* [Wiki roundup of all properties links](http://web.archive.org/web/20080517034926/http://oslo.cs.sjsu.edu:8080/xwiki/bin/view/JavaProperties/),
TODO


Alex Buckley

* [Properties in Java](http://web.archive.org/web/20070423001723/http://blogs.sun.com/abuckley/entry/properties_in_java),
Apr 2007,
Finds proposals unsatifactory (simple props too simple to generate, GUI props too hard)
* Related [Peter Ahe](http://web.archive.org/web/20070220031337/http://blogs.sun.com/ahe/entry/no_properties)
comments on properties, Feb 2007


Hans Muller

* [Property syntax](http://web.archive.org/web/20070429210910/http://weblogs.java.net/blog/hansmuller/archive/2007/01/property_syntax.html),
Jan 2007, 27 comments,
written from perspective of a GUI developer, argues for handling harder GUI scenarios,
comments both for and against


Mikael Grev

* [Proposal](http://migcalendar.com/properties/proposal.html),
2007, A proposal for properties in Java 7,
GUI focused, based on bound properties,
`person.surname.get()`, `person.surname.set("Foo")`, `person.children.add(child1)`,
optional syntax `String s = person#surname`, `person#surname = "Foo"`,
distinguishes simple/collection/indexed/mapped/subBean properties,
briefly mentions stateless (meta)properties but not suitable for non-framework use
* [Project](https://java.net/projects/properties) and
Property class [source](https://java.net/projects/properties/sources/svn/show/trunk/src/com/miginfocom/property?rev=34)


Evan Summers

* [Dot notation for access](https://weblogs.java.net/blog/evanx/archive/2008/04/first_class_jav_2.html),
toys with `person.surname.property`, Apr 2008, 13 comments,
comments mostly favour dot over arrow for access to properties
* [QBeanInfo](https://weblogs.java.net/blog/evanx/archive/2006/12/gooey_beans_inf.html),
Dec 2006, 
a mechanism for declaring metaproperties called `QProperty`,
focussed on GUIs, includes date format pattern
* [Bind meta to bean](https://www.stagejava.net/blog/evanx/archive/2007/02/bound_gooey_bea.html),
Feb 2007,
allows `person.beanInfo.surname.firePropertyChanged()` where `person.beanInfo` is a public field accessing the
metabean-like `QBeanInfo`.
* [Properties for SQL](https://weblogs.java.net/blog/evanx/archive/2007/07/a_short_note_ab.html),
Jul 2007,
emphasising refactoring in SQL queries
* [Problems and solutions](https://weblogs.java.net/blog/evanx/archive/2007/01/property_proble_1.html),
Jan 2007,
list/table of problems, solutions and whether they need a language change,
argues that writing getters/setters not that big a deal but IDEs should help more,
argues that stringly typed property references should be better (lang change),
bound properties could be AOP injected
* [](https://weblogs.java.net/blog/evanx/archive/2007/01/gotcha_property.html),
Jan 2007,
discusses language change to obtain `PropertyDescriptor` (type 1) and `BoundPropertyDescriptor` (type 2)


Fred Simon

* [Abstract enum blogs](http://freddy33.blogspot.co.uk/search/label/abstract%20enum),
includes entry showing abstract enum for properties
* [Extended enum home](http://web.archive.org/web/20110617153504/http://www.extended-enums.org/home)
* Kijaro abstract enum [Property class](https://java.net/projects/kijaro/sources/svn/content/branches/abstractenum/jdk/src/share/classes/java/lang/Property.java?rev=184)
with name, type and declaring type (type 1, bean independent)
* Wild beans [part one](http://freddy33.blogspot.co.uk/2008/01/wild-java-properties-road-so-far.html)
and [part two](http://freddy33.blogspot.co.uk/2008/01/wild-java-properties-wild-part.html),
Jan 2008,
key summary of experiments in Remi's property language change (for type 1 and 2),
lots of code demonstrating how abstract enum and standard JDK type 1 and 2 classes would satisfy the need,
uses `public property String surname;` syntax,
ultimate solution uses public property objects and `person.surname.get()` behind the scenes.


Yardena Meymann

* [Properies for Java](http://sensualjava.blogspot.co.uk/2007/11/properties-for-java.html) simple intro,
Nov 2007
* [Static yet dynamic](http://sensualjava.blogspot.co.uk/2007/11/properties-static-and-yet-dynamic.html),
Nov 2007,
using `public static final Property<Person,String> name = new Property<Person,String> ("name") {};`
to create a typed subclass of a general purpose `Property` (type 1, independent), but code can be in any
class, not just `Person`
* See BeanUtopia


Kevin Bourillion

* [Value objects](http://smallwig.blogspot.co.uk/2007/10/value-objects-wtf2.html), related though not classic beans,
Oct 2007,
problems with current state of the world


Danny Lagrouw

* [Proper properties](http://www.blog.dannynet.net/archives/81),
Jan 2007
proposal that involves generating get/set methods for all fields on all objects,
additional control via annotations


Eliotte Rusty Harold

* [Opposes properties](http://cafe.elharo.com/blogroll/why-java-doesnt-need-properties-it-already-has-them/),
Jan 2007, 41 comments,
lots of comments focus on avoiding all language change in Java (no longer a relevant debate),
blog argues that properties add nothing really new and are incompatible with old code,
also argues against closures


Joe Nuxoll

* [Proposal pdf](http://web.archive.org/web/20060216000543/http://blogs.sun.com/roller/resources/joe/java-properties-events.pdf),
2004,
uses `public String surname get set` to define property,
access via `person.surname = "Foo"` and so on,
lots of additional notes on events
* [Proposal blog](http://web.archive.org/web/20100808153044/http://blogs.sun.com/joe/entry/java_properties_and_events), Aug 2010


Ricky Clarkson

* [Blog](http://rickyclarkson.blogspot.co.uk/2007/02/ricky-clarkson-properties-for-java.html),
Feb 2007,
discussion, no clear conclusions (prefers existing beans to carry on working)


Kirk Pepperdine

* [Blog](http://web.archive.org/web/20130129231712/http://kirk.blog-city.com/java_70_language_feature_considered_harmful.htm),
Dec 2006,
argues for OO solutions and properties are anti-encapsulation


Jesse Wilson

* [Proposal](https://publicobject.com/2007/01/extensible-properties-for-java-language.html),
Jan 2007,
wants extensible property objects with decorators (observable, not null, etc),
proposes `private @Property(ObservableProperty.class, NotNullProperty.class) String surname` declarations
* [Proposal](https://publicobject.com/2007/09/bean-independent-properties.html),
type 1 bean-independent properties,
proposal doc not currently accessible,
access by `String s = person#surname` and `person#surname = "Foo"`,
declare by `public property Property<Customer,String> city = new BasicPropertyBuilder(property("")).nonNull().observable(##propertyChangeSupport).build();`
or maybe `public Long #id`.
* From [Google Doc proposal](https://docs.google.com/document/d/1tJGcDSIpii5zODEngAWLCw9uPwdDNUADI4HWL3v-xWc/edit?usp=sharing),
"Properties are per-type, not per-instance. Anonymous classes are used for efficient property access. Properties are extensible using the decorator pattern",
propose to generate field and inner classes directly accessing field,
open question on whether interfaces expose properties.

    // Bean-independent Properties, direct access:
    Customer customer = ...
    String city = customer#city;
    customer#city = "San Francisco";

    // Bean-independent Properties, access via a Property object:
    Customer customer = ...
    Property<Customer,String> customerCityProperty = Customer##city;
    String city = customerCityProperty.get(customer);
    customerCityProperty.set(customer, "San Francisco");

    // declare (getter/setter for legacy and implementing interface)
    public Property<Customer,String> ##city = property("");
    public String getCity() { return #city; }
    public void setCity(String city) { #city = city; }

    // compilation (before and after
    String city = customer#city
    String city = Customer.$p_city.get(customer)

    // compilation (before and after
    Property<Customer,String> customerCityProperty = Customer##city
    Property<Customer,String> customerCityProperty = Customer.$p_city
    
    // interface
    public interface Property<B,V> {
      V get(B bean);
      V get(B bean, V defaultValue);
      V set(B bean, V value);
      String getName();
      Type<B> getDeclaringType();  // maybe?
      Type<V> getValueType();  // maybe?
    } 


Richard Bair

* [Proposal](https://weblogs.java.net/blog/rbair/archive/2007/01/properties_in_j.html),
Jan 2007, IMPORTANT,
"The JavaBeans pattern is not about breaking the encapsulation of the Person object. It is about exposing configurable state.",
"there is a big difference between a property, and a field. A Property is a concept, a field is an implementation",
"Any proposal must be backwards compatible with the existing JavaBeans spec! Leverage existing APIs and existing patterns where possible. Introduce as little as possible to get the job done.",
"you define a property, and then call a method "getSurname()". Where was that method defined?" (but no better alternative),
argues for keeping getter/setter (allows covariant return type overloads),
argues for property metadata being `PropertyDescriptor`,
argues for bound properties (for GUIs) and states right code for them is hard,
* [Reponse to Remi](https://weblogs.java.net/blog/rbair/archive/2007/01/remis_property.html),
Jan 2007,
reviews and tweaks Remi javac changes,
proposes no getter/setter generation (hidden magic code)

    String s = person.surname; // yields the value of the surname property
    Property p = person#surname; // returns the Property object for the surname property
    p = Person#surname; // also returns the Property object for the surname property
    Method m = Person#formatName(String); // holy toledo! Returns a Method object!


Remi Forax

* [Mixing beans binding and language change](https://weblogs.java.net/blog/forax/archive/2007/09/mixing_property_1.html),
Sep 2007,
combines beans-binding project and javac language change
* [Initial take](https://weblogs.java.net/blog/forax/archive/2007/01/property_and_in.html),
Jan 2007,
Discusses using `get*` and `set*` methods to define templates for getters and setters,
suggests interceptors instead of language support for bound
* [Property impl v1](https://weblogs.java.net/blog/forax/archive/2007/01/a_property_prop_1.html),
Jan 2007,
supports `property int x` declaration and `abstract property int x` (forces manual getter/setter),
access via `p.x`
* [Why not bound](https://weblogs.java.net/blog/forax/archive/2007/01/property_and_be.html),
Jan 2007,
lots of approaches to bound properties so not suitable for language change
* [Property reload](https://weblogs.java.net/blog/forax/archive/2007/01/property_reload.html),
Jan 2007, IMPORTANT,
allows `public property String surname` to compile, adjusted like C#,
"Unlike my first proposal, i think now that we must not have two ways to access to a property. So 'dot' can't be used to access/change the value of a property",
use getter/setter for value access,
gets `Person.surname` to translate to `Person.class.getProperty("surname")`,
thus `Property<Person, String> prop = Person.surname`,
properties are bound, usable in switch statement

   public class Property<B, T> {
      public Class<B> getDeclaringClass() {}
      public Class<T> getType() {}
      public String getName() {}
      public boolean isReadable() {}
      public boolean isWritable() {}
      public T get(Object bean) {}
      public void set(Object bean, Object value) {}
    }

    public class Class<T> {
      public Property<T, ?>[] getProperties() {}
      public Property<T, ?> getProperty(String name) {}
    }


Matthias Ernst

* [Note on type 3 properties](http://www.mernst.org/blog/rss.xml#Arent-properties-objects),
Jan 2007,
brief note


Shai Almog

* [Case against property keyword](http://jroller.com/vprise/entry/the_case_against_the_property),
Jul 2007, 5 comments,
argues for type 3 properties instead of getters/setters


## Property implementations

[Old Joda-Beans](http://joda.sourceforge.net/beans.html)

* 2000 - 2002,
* Type 3 property objects such as `StringProperty` and `BooleanProperty`
* Includes swing bindings, XML, XPath
* JavaFX follows many principles from here


[New Joda-Beans](http://www.joda.org/joda-beans/)

* 2010 onwards
* Type 1 and 2, focussed on meta-property and meta-bean
* Includes XML, JSON and binary serialization
* Integrates with freemarker and MongoDB
* Effective and efficient for server-side


[XJava](http://xjava.googlecode.com/svn/trunk/src/org/xjava/property/) - Type 3 (stateful)


[Bean-properties](https://java.net/projects/bean-properties/)


[Majick-properties](https://code.google.com/p/majick-properties/)

* [User guide](https://code.google.com/p/majick-properties/wiki/UsageGuide)
* [Sample class](https://code.google.com/p/majick-properties/source/browse/trunk/majick-properties/src/test/java/org/ndx/majick/properties/Experience.java)
* Motivated by GUI pain of listeners


[BeanUtopia](https://code.google.com/p/beanutopia/)

* Yardena Meymann
* [Pdf presentation](http://beanutopia.googlecode.com/svn/trunk/docs/Java%20Properties.ppt), nice picture slide 5,
"Conclusion: Java needs proper support for properties"
* [Property class](https://code.google.com/p/beanutopia/source/browse/trunk/src/org/beanutopia/Property.java)
* TODO


Bean binding JSR-295

* [Blog version 1.0](https://weblogs.java.net/blog/shan_man/archive/2007/09/beans_binding_1.html), Sep 2007
* [Blog version 1.1.1](https://weblogs.java.net/blog/shan_man/archive/2007/10/beans_binding_1.html), Oct 2007
* "abstract Property class, with two concrete implementations of interest: BeanProperty and ELProperty"
* "Binding is now an abstract class representing a binding between two Property instances"
* Type 1, with GUI bound elements, abstract classes instead of interfaces
* `Property surnameProp = BeanProperty.create("surname")`
* `surnameProp.getValue(person)`
* `surnameProp.setValue(person, "Foo")`
* `surnameProp.addPropertyStateListener(person, listener)`
* Abandoned, but clear precursor to JavaFX
* Project [home page](https://java.net/projects/beansbinding)
* [Source code](https://java.net/projects/beansbinding/sources/svn/show/trunk/beansbinding?rev=631)


[Better bean binding](https://kenai.com/projects/betterbeansbinding/pages/Home)


[Bean binding and language support](https://weblogs.java.net/blog/forax/archive/2007/09/mixing_property_1.html)

