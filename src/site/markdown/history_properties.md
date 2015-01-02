## History of JavaBeans and Properties discussions

This document is an attempt to capture discussions about properties and beans.


## Blogs

Alex Miller

* [Java 7 links](http://tech.puredanger.com/java7#property)
* [Opinion](http://tech.puredanger.com/2007/01/26/java7-property/),
argues that public field access should be rewritten to be via getters thus creating properties,
comments include anti-mutable and anti-encapsulation,
comments include swing binding is bigger problem than getter/setter generation

Stephen Colebourne

* [Property terminology](http://blog.joda.org/2007/10/java-7-properties-terminology_6780.html),
type 1 = bean-independent (meta-property, per-class),
type 2 = bean-attached (stateless property, per-instance),
type 3 = stateful (property object)
* [Property objects](http://blog.joda.org/2007/01/java-7-property-objects_540.html), 2007, 20 comments,
proposes objects for properties (property and meta-property),
proposes generating `propertySurname()` alongside `getSurname()` and `setSurname()`,
focus on binding.
* [More on property objects](http://blog.joda.org/2007/01/more-detail-on-property-literals_3217.html), 2007, 5 comments,
uses person->surname to access property object and Person->surname to access meta-property,
discusses reflection vs property objects as fields.
comments discuss meta-property vs property and use of property for "isDirty" or "originalValue".
* [Bean keyword](http://blog.joda.org/2007/01/keyword_5910.html), 2007, 6 comments, 2007, 6 comments,
discusses using `bean` instead of `class` to allow syntax change within.
* [Update on properties](http://blog.joda.org/2007/09/java-7-update-on-properties_179.html), 2007, 10 comments
* [Beans and properties](http://blog.joda.org/2011/05/beans-and-properties_1963.html), 2011, 22 comments

Matt Hicks 

* [Discussion](http://www.sgine.org/2010/08/properties-discussion.html)
* [Beans should be deprecated](http://www.sgine.org/2010/09/beans-should-be-deprecated-future-of_08.html)
* [Death of beans](http://www.matthicks.com/2009/07/death-of-beans.html)

Javalobby

* [Typesafe property literals](http://java.dzone.com/articles/typesafe-property-literals)
"We found that once you have these literals, they just turn out to be useful in many cases."
* [Main discussion based on Ideas from Danny Coward](http://www.javalobby.org/java/forums/t88090.html?start=0), 2007, 136 comments
* [Why Java Properties are BAD, ugly and uncomfortable](http://www.javalobby.org/java/forums/t90756.html), 2007, 96 comments
* [Beans Binding (JSR 295) & Properties on JDK7 ](http://www.javalobby.org/java/forums/t101998.html), 2007, 14 comments
* [Pretty Please, we need Properties. Here's the Middle Ground](http://www.javalobby.org/java/forums/t90862.html), 2007, 59 comments
* [Bean-dependent or Bean-independent Properties?](http://www.javalobby.org/java/forums/t102115.html), 2007, 50 comments,
various discussions and confusions, little about syntax, lots about bean-properties,
reminder of importance of collections,

JavaPosse

* [Discussion 1](https://groups.google.com/forum/#!topic/javaposse/gA_YdNk2bfI) and
[Discussion 2(https://groups.google.com/forum/#!topic/javaposse/pJqu8Z0O_SE),
neither that interesting

Mike Heinrich 

* [Creating JavaFX properties](http://blog.netopyr.com/2011/05/19/creating-javafx-properties/)

Cay Horstmann

* [Arrows in the back](https://weblogs.java.net/blog/cayhorstmann/archive/2007/01/arrows_in_the_b.html),
discusses feedback to the properties topic
* [Altered javac](http://web.archive.org/web/20091023160115/http://geocities.com/adcalves/javaproperty/),
uses `String s = person.@surname`, `person.@surname = "Foo"` notation,
proposes `private String @surname` as possible declaration.
* [MSc Thesis](http://web.archive.org/web/20091027074331/http://geocities.com/adcalves/javaproperty/index_files/Thesis.pdf),
pdf,
`@Property(getter = "getSurname", setter = "setSurname")
* [Properties get no respect](https://weblogs.java.net/blog/cayhorstmann/archive/2007/12/properties_get_1.html),
bit of a rant with link to wiki
* [Wiki roundup of all properties links](http://web.archive.org/web/20080517034926/http://oslo.cs.sjsu.edu:8080/xwiki/bin/view/JavaProperties/),
TODO

Alex Buckley

* [Properties in Java](http://web.archive.org/web/20070423001723/http://blogs.sun.com/abuckley/entry/properties_in_java) -
Finds proposals unsatifactory (simple props too simple to generate, GUI props too hard)
* Related [Peter Ahe](http://web.archive.org/web/20070220031337/http://blogs.sun.com/ahe/entry/no_properties)
comments on properties

Hans Muller

* [Property syntax](http://web.archive.org/web/20070429210910/http://weblogs.java.net/blog/hansmuller/archive/2007/01/property_syntax.html) -
written from perspective of a GUI developer, argues for handling harder GUI scenarios, lots of comments both for and against

Mikael Grev

* [Proposal](http://migcalendar.com/properties/proposal.html) -
A proposal for properties in Java 7,
GUI focused, based on bound properties,
`person.surname.get()`, `person.surname.set("Foo")`, `person.children.add(child1)`,
optional syntax `String s = person#surname`, `person#surname = "Foo"`,
distinguishes simple/collection/indexed/mapped/subBean properties,
briefly mentions stateless (meta)properties but not suitable for non-framework use
* [Project](https://java.net/projects/properties) and [source](https://java.net/projects/properties/sources/svn/show/trunk/src/com/miginfocom/property?rev=34)

Evan Summers

* [Dot notation for access](https://weblogs.java.net/blog/evanx/archive/2008/04/first_class_jav_2.html),
toys with `person.surname.property`, has comments`
* [QBeanInfo](https://weblogs.java.net/blog/evanx/archive/2006/12/gooey_beans_inf.html),
a mechanism for declaring metaproperties called `QProperty`,
focussed on GUIs, includes date format pattern
* [Bind meta to bean](https://www.stagejava.net/blog/evanx/archive/2007/02/bound_gooey_bea.html),
allows `person.beanInfo.surname.firePropertyChanged()` where `person.beanInfo` is a public field accessing the
metabean-like `QBeanInfo`.
* [Proerties for SQL](https://weblogs.java.net/blog/evanx/archive/2007/07/a_short_note_ab.html),
emphasising refactoring in SQL queries

Fred Simon

* [Abstract enum blogs](http://freddy33.blogspot.co.uk/search/label/abstract%20enum),
includes entry showing abstract enum for properties
* [Extended enum home](http://web.archive.org/web/20110617153504/http://www.extended-enums.org/home)
* Kijaro abstract enum [Property class](https://java.net/projects/kijaro/sources/svn/content/branches/abstractenum/jdk/src/share/classes/java/lang/Property.java?rev=184)
with name, type and declaring type (type 1, bean independent)
* Wild beans [part one](http://freddy33.blogspot.co.uk/2008/01/wild-java-properties-road-so-far.html)
and [part two](http://freddy33.blogspot.co.uk/2008/01/wild-java-properties-wild-part.html), key summary of experiments in
Remi's property language change (for type 1 and 2),
lots of code demonstrating how abstract enum and standard JDK type 1 and 2 classes would satisfy the need,
uses `public property String surname;` syntax,
ultimate solution uses public property objects and `person.surname.get()` behind the scenes.

Yardena Meymann

* [Properies for Java](http://sensualjava.blogspot.co.uk/2007/11/properties-for-java.html) simple intro
* [Static yet dynamic](http://sensualjava.blogspot.co.uk/2007/11/properties-static-and-yet-dynamic.html)
using `public static final Property<Person,String> name = new Property<Person,String> ("name") {};`
to create a typed subclass of a general purpose `Property` (type 1, independent), but code can be in any
class, not just `Person`
* See BeanUtopia

Kevin Bourillion

* [Value objects](http://smallwig.blogspot.co.uk/2007/10/value-objects-wtf2.html), related though not classic beans,
problems with current state of the world

Danny Lagrouw

* [Proper properties](http://www.blog.dannynet.net/archives/81),
proposal that involves generating get/set methods for all fields on all objects,
additional control via annotations

Eliotte Rusty Harold

* [Opposes properties](http://cafe.elharo.com/blogroll/why-java-doesnt-need-properties-it-already-has-them/),
2007, 41 comments,
lots of comments focus on avoiding all language change in Java (no longer a relevant debate),
blog argues that properties add nothing really new and are incompatible with old code,
also argues against closures

Joe Nuxoll

* [Proposal blog](http://web.archive.org/web/20100808153044/http://blogs.sun.com/joe/entry/java_properties_and_events)
* [Proposal pdf](http://web.archive.org/web/20060216000543/http://blogs.sun.com/roller/resources/joe/java-properties-events.pdf),
2004,
uses `public String surname get set` to define property,
access via `person.surname = "Foo"` and so on,
lots of additional notes on events

Ricky Clarkson

* [Blog](http://rickyclarkson.blogspot.co.uk/2007/02/ricky-clarkson-properties-for-java.html),
discussion, no clear conclusions (prefers existing beans to carry on working)

Kirk Pepperdine

* [Blog](http://web.archive.org/web/20130129231712/http://kirk.blog-city.com/java_70_language_feature_considered_harmful.htm),
argues for OO solutions and properties are anti-encapsulation

Jesse Wilson

* [Proposal](https://publicobject.com/2007/01/extensible-properties-for-java-language.html),
wants extensible property objects with decorators (observable, not null, etc),
proposes `private @Property(ObservableProperty.class, NotNullProperty.class) String surname` declarations
* [Proposal](https://publicobject.com/2007/09/bean-independent-properties.html),
type 1 bean-independent properties,
proposal doc not currently accessible,
access by `String s = person#surname` and `person#surname = "Foo"`,
declare by `public property Property<Customer,String> city = new BasicPropertyBuilder(property("")).nonNull().observable(##propertyChangeSupport).build();`
or maybe `public Long #id`.


Richard Bair

* [Proposal](https://weblogs.java.net/blog/rbair/archive/2007/01/properties_in_j.html),
2007, IMPORTANT,
"The JavaBeans pattern is not about breaking the encapsulation of the Person object. It is about exposing configurable state.",
"there is a big difference between a property, and a field. A Property is a concept, a field is an implementation",
"Any proposal must be backwards compatible with the existing JavaBeans spec! Leverage existing APIs and existing patterns where possible. Introduce as little as possible to get the job done.",
"you define a property, and then call a method "getSurname()". Where was that method defined?" (but no better alternative),
argues for keeping getter/setter (allows covariant return type overloads),
argues for property metadata being `PropertyDescriptor`,
argues for bound properties (for GUIs) and states right code for them is hard,



## Property implementations and proposals

[XJava](http://xjava.googlecode.com/svn/trunk/src/org/xjava/property/) - Type 3 (stateful)

[Bean-properties](https://java.net/projects/bean-properties/)

[Majick-properties](https://code.google.com/p/majick-properties/)
with [user guide](https://code.google.com/p/majick-properties/wiki/UsageGuide)
and [sample class](https://code.google.com/p/majick-properties/source/browse/trunk/majick-properties/src/test/java/org/ndx/majick/properties/Experience.java),
motivated by GUI pain of listeners

[BeanUtopia](https://code.google.com/p/beanutopia/)

* Yardena Meymann
* [Pdf presentation](http://beanutopia.googlecode.com/svn/trunk/docs/Java%20Properties.ppt), nice picture slide 5,
"Conclusion: Java needs proper support for properties"
* [Property class](https://code.google.com/p/beanutopia/source/browse/trunk/src/org/beanutopia/Property.java)
* TODO

[Better bean binding](https://kenai.com/projects/betterbeansbinding/pages/Home)

Remi's [Bean binding and language support](https://weblogs.java.net/blog/forax/archive/2007/09/mixing_property_1.html)

