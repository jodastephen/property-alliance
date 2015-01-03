## SJSU CS Wiki Use Cases

A copy of the SJSU CS wiki curated by Cay Horstmann.
Retrieved from the Internet Archive.

Version 1.7 last modified by Cay Horstmann on 06/12/2007 at 21:19

---

## Issues related to Native Properties

### "Eclipse does it for me"

Indeed, all modern IDEs will generate getters and setters automatically. But you, the programmer, must still read them. 


### Properties are the Tool of the Devil

Allen Holub's well-known article [Why getter and setter methods are evil](http://www.javaworld.com/javaworld/jw-09-2003/jw-0905-toolbox.html)
argues that programmers should think twice before exposing fields with getters and setters.
Despite its inflammatory title, the article is quite nuanced and does *not* say that every getter and setter is evil.
What is evil is to provide them without thinking through the consequences.

A property exposes the same internal implementation as a getter/setter pair, making it equally "evil". 


### Bound Properties

A bound property fires an event to PropertyChangeListeners when its value changes.
Bound properties are commonly used in Swing.
They are even more boring and tedious to implement/read than plain properties. Can a language extension help?

Jan 10, 2007:

http://weblogs.java.net/blog/hansmuller/archive/2007/01/property_syntax.html
Hans Muller – property support is not useful if it only addresses the use case of shortcut field-backed property definition
is not enough (fails to recognize that properties address many other concerns).
Expresses strong desire for the bound properties use case to be addressed and not just the trivial case

http://blogs.sun.com/abuckley/entry/properties_in_java
Alex Buckley – convinced by Muller that he does not want properties
http://blogs.sun.com/ahe/entry/no_properties
Peter Ahe – Also convinced by Muller against properties.

Jan 11, 2007:

http://weblogs.java.net/blog/forax/archive/2007/01/property_and_in.html
Remi Forax – concedes that bound properties are not a good idea as a language feature but rather handled through
interceptors like in Ejb3. Then goes on to define language support for a per-class property interceptor. Weird.

Jan 23, 2007:

http://weblogs.java.net/blog/forax/archive/2007/01/property_reload.html
Remi Forax – Proposal for bound properties in response to Muller. Covers only trivial case of field-backed property
with auto-generated getter/setter – no support for customizing validation code for example, as pointed out by Muller.
Also, problem with generated code – do we use == or .equals() for comparison?

May 13, 2007:

http://weblogs.java.net/blog/forax/archive/2007/05/java_property_d.html
Remi Forax – java property spec draft 3 with description of bound properties again covering only trivial case.
Also, too tight restriction – “bound property must be overridden by bound property?”


### Overloaded setters

How do we handle this with properties:

```
 public void setFoo(Foo foo) {this.foo = foo;}
 public void setFoo(MyFooLikeThing fooLikeThing) {
   setFoo(fooLikeThing.asFoo());
 }
```

http://skizz.biz/blog/2003/09/12/whats-wrong-with-c-properties/


### Different Access Modifiers

Want to have a public getter and protected setter?

Mentioned http://blogs.sun.com/abuckley/en_US/entry/properties_in_java


### Override getter with covariant return type


```
 class Super {
   Number getValue();
   void setValue(Number value);
 }

 class Sub extends Super {
   Integer getValue();  // co-variant return type
 }
```

http://weblogs.java.net/blog/rbair/archive/2007/01/properties_in_j.html


### Breakpoints

Comment to http://weblogs.java.net/blog/cayhorstmann/archive/2007/01/arrows_in_the_b.html Posted by: alekd on January 09, 2007 at 09:41 AM

Raises the issue of breakpoints and synthesized methods.


### Java APIs That Need Updating

#### Annotations

* Target.PROPERTY

#### Reflection APIs

* java.lang.reflect.Property class
* java.lang.Class: add getProperties(), getProperty(String name) methods.

#### Property Introspection APIs in Java SE

This is a list of Java APIs that contain introspection code that looks up properties in a Java class by looking up get/set methods.
These might need to be extended in order to recognize first-class properties and allow for the intuitive operation of this new feature.

* JavaBeans Introspector - java.beans.Introspector
* JMX MBean Introspector - com.sun.jmx.mbeanserver.Introspector


### APIs that Bypass Introspection

Many other 3rd party libraries resort to [manual lookup of getter and setter methods](http://www.google.com/codesearch?hl=en&amp;lr=&amp;q=startsWith%5C%28%22set%22%5C%29&amp;btnG=Search).
Some of these libraries are implementations of Java specs such as J2EE.
Those libraries will need to change and when they are bound by specs the specs will need to change also to facilitate wide-scale support for first-class properties.



## Comments

None
