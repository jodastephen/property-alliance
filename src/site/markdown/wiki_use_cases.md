## SJSU CS Wiki Use Cases

A copy of the SJSU CS wiki curated by Cay Horstmann.
Retrieved from the Internet Archive.

Version 1.99 last modified by Nikolay Botev on 01/04/2008 at 20:12

---

## Use Cases for Java Properties

### No more boring boilerplate

Write something like:

```
 /** The name of this widget */
 public property String name;
```

instead of:

```private String name;
 /**
  * Gets the name of this widget
  * @return the name of this widget
  */
 public String getName() { return name; }
 /**
  * Sets the name of this widget
  * @param name the new name of this widget
  */
 public void setName(String name) { this.name = name; }
```


### Nicer syntax for property access

Use short-hand field-like LHS and RHS syntax, such as:

```
 my.property = your.otherProperty;
```

instead of

```
 my.setProperty(your.getOtherProperty());
```

Other operators (such as `->` or `.@`) have been proposed to distinguish property access from field access.

This link discusses the readability issue further: [Bug 6180800](http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6180800).


### Javadoc should document properties

We think of properties as something higher-level than the get/set method implementation, and Javadoc should reflect that. We'd like

* Fields
* Properties
* Constructors
* Methods

See this [proposal](http://www.javalobby.org/java/forums/t88090.html?start=0) by Mikael Grev.


### Bound properties

Many bound property implementations are really boring. Set the new value, maybe check that it isn't null,
maybe check that it is different from the old value, then set it and fire the property change.

When you have lots of bound properties (like in Swing), you'd like to reduce this drudgery.

The difficulty is that not all bound property implementations are exactly identical.
Maybe a null value is ok. Should one test for equality?
Is this property an oddball case that cannot be handled mechanically?

Many solutions have been proposed. Some are based on annotations, others on generics, such as

```
 class Point {
   public property int x bound;
   public property int y bound;

   private <T> void propertyChange(Property<Point, T> prop, T oldValue, T newValue) {
     // do what you want here
    }
 }
```

[Proposed](http://weblogs.java.net/blog/forax/archive/2007/01/property_reload.html) by Remi Forax.


### Get list of properties using core reflection (not JavaBeans) API

Properties are design features, and the get/set patterns are just an implementation detail.
The design intent should be discoverable by runtime tools, in particular, core reflection.

This is [proposed](http://weblogs.java.net/blog/forax/archive/2007/01/property_reload.html) by Remi Forax.

```
 public class Class<T> {
   public Property<T, ?>[] getProperties() {}
   public Property<T, ?> getProperty(String name) {}
 }
```


### Avoid annotation ambiguity

In EJB3, you annotate persistent properties. But to annotate fields or getters, that is the question.

```
 @Id
 private int id;
```

or

```
 @Id
 public int getId() { ... }
```

Why should this be an issue? The developer just wants to annotate the property:

```@Id
 public property int id;
```


### Statically-verifiable property literals (for use in beans-binding and property reflection)

Expose Property/PropertyDescriptor objects through language syntax, which enables the compiler to
validate that the property actually exists.

Syntax proposals abound:

* [Bean.name](http://weblogs.java.net/blog/forax/archive/2007/01/property_reload.html)
* [Bean#name](http://weblogs.java.net/blog/rbair/archive/2007/01/remis_property.html)
also [here](http://www.javalobby.org/java/forums/t90862.html)
* [Bean@name](http://weblogs.java.net/blog/evanx/archive/2007/01/property_proble_1.html)
* [Bean->name](http://www.jroller.com/scolebourne/entry/more_detail_on_property_literals)
* [Bean:name](http://weblogs.java.net/blog/evanx/archive/2007/01/gotcha_property_1.html)

Consider this example which binds the currentPrice property of the visualControl object to the
value of the price property of the dataSource object.

```
binding = Bindings.createAutoBinding(
  AutoBinding.UpdateStrategy.READ_WRITE,
  dataSource,
  ELProperty.create("${price}"),
  updatedControl,
  BeanProperty.create("currentPrice"));
```

If the currentPrice property is renamed, the above code will break and refactoring tools will not be able to catch it. With property literal syntax the above code looks like this:

```
binding = Bindings.createAutoBinding(
  AutoBinding.UpdateStrategy.READ_WRITE,
  dataSource,
  ELProperty.create("${price}"),  // see ^ note below
  updatedControl#currentPrice);
```

This idea (paraphrased here in Beans Bindings 1.0 code) originally came from
[Stephen Colebourne](http://blog.joda.org/2007/01/java-7-property-objects_540.html).

^ Note that the price property reference within the Unified EL expression still suffers from
the same problem. Reifying this is an entirely different problem - support for lambda expressions
with a code-as-data view ala C# 3.0 would be required.


### Use a switch statement to select property in generic property (change) listeners

Remi Forax [proposal](http://docs.google.com/View?docid=dfhbvdfw_1f7mzf2).

```
 Bean bean=new Bean();
 bean.addPropertyListener(new PropertyListener<Bean>() {
   public <T> propertyChanged(Property<Bean, T> property, T oldValue, T newValue) {
     switch(property) {
       case name:
         frame.setTitle((String)newValue);
         break;
       case value:
         slider.setValue((Integer)newValue);
         break;
     }
   }
 }
```


### Ignore nulls in a chain of accessors

```
input.address.postcode; // returns null if input.address is null
```

mentioned [here](http://blog.joda.org/2007/01/property-access-is-it-expression_5885.html).


### Property Inspection during Debugging

Properties can be listed along with fields in a debug "Watch" or "Locals" view.

The Visual Studio 2008 Locals view for example shows by default only public members
(mostly properties) with non-public members hidden in a subtree, which can be expanded
to view those members if necessary.


## Comments

Comment: 29.12.2007 at 02:20 PM, Anonymous

I would have thought that the compatibility / encapsulation side of properties should be spelled out,
i.e. the reason why the getter/setter convention exists of just public exposed field.
I think [this link](http://tomayko.com/articles/2005/01/20/getters-setters-fuxors) discusses that topic reasonably (in a python vs java context)

