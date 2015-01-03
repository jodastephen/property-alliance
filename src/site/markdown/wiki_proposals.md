## SJSU CS Wiki Use Cases

A copy of the SJSU CS wiki curated by Cay Horstmann.
Retrieved from the Internet Archive.

Version 1.127 last modified by Administrator on 24/04/2008 at 19:06

---


### The Very First Proposal

Similar to other proposals, but deserves mention since it's the earliest proposal on record.

```
 public property String foo;
 a->Foo = b->Foo;
```

* http://blogs.sun.com/dannycoward/resource/Java7Overview_Prague_JUG.pdf


### Marking Properties 

#### A property Keyword

15 up votes, 1 down vote

We need to somehow tag properties.
The most commonly proposed choice of keyword is, duh, **property**.

Stephen Colebourne:

```
 public bean Person {
   // properties
   property String forename;
   property String surname;
   // normal fields and methods
   private String ordinaryField;
   public boolean validate() { ... }
 }
```

* http://blog.joda.org/2007/01/keyword_5910.html

#### Final properties

0 up votes, 8 down votes

Peter von der Ahe suggests that read-only properties can be marked with the **final** keyword.

```
 final property String name = "Name";
 property String address;
```

becomes

```
 final private String name = "Name";
 public String getName() { return name; }
 private String address;
 public String getAddress() { return address; }
 public void setAddress(String address) { this.address = address; }
```

* https://blogs.oracle.com/ahe/entry/java_se_7_wish_list

#### Context-sensitive keywords

5 up votes, 8 down votes

Remi Forax points out that **property** can be a "context-sensitive" keyword.
It is only taken as a keyword in a specific location.
If you have existing code that has a method or variable named "property", that code will continue to compile.

Similarly, **get** and **set** can be context-sensitive keywords for controlling the getter and setter generation.

```
 class MyBean {
   public property String name1; // read-write
   public property String name2 get; // read-only
   public property String name3 set; // write-only
   public property String name4 get set; // illegal
   public property String name5 get { return "hello"; } // read-only
   public property String name6 set(String name) { } // write-only
   private String _name7;
   public property String name7
     get { return _name7; }
     set(String name) { _name7 = name; firePropertyChange(name7); } // read-write
 }
```

* http://weblogs.java.net/blog/forax/archive/2007/01/a_property_prop_1.html
* http://weblogs.java.net/blog/forax/archive/2007/01/property_reload.html
* http://weblogs.java.net/blog/forax/archive/2007/05/java_property_d.html

#### Using a Token

Nobody likes the idea of introducing new keywords to the language.
Remember the pain when **enum** was added and you had to rename all your
variables that were so named? An alternative is to use some kind of token.

2 up votes, 13 down votes

```
 public class LineItem {
   public int @ quantity;
   ...
 }
```

* http://weblogs.java.net/blog/cayhorstmann/archive/2006/06/say_no_to_prope.html
* http://weblogs.java.net/blog/cayhorstmann/archive/2007/01/arrows_in_the_b.html

Here, the @ token is used to mark a property because the same proposal uses the .@ operator for property access. One would want to use a token that is similar to that of the property access operator.  For example, if you use :: for propery access
```
 person::name
```

then a property might be marked as

```
 public String ::name
```

#### Using the Presence of Getters and Setters

9 up votes, 4 down votes

```
 class MyBean {
   public String name1 get set; // read-write property
   public String name2 get; // read-only property
   public String name3 set; // write-only property
   public String name4; // public field
   public String name5 get { return "hello"; } // read-only, custom getter
   public String name6 get set { name6 = value; } // read-write, custom setter
 }
```

#### Using Braces

0 up votes, 13 down votes

This proposal marks properties simply with braces.

```
 // Trivial case:
 // type name lowercased is property name, public getter and setter, protected member variable
 public RuntimeOptions {}
 // protected getter and setter, static, nondefault name
 protected static RuntimeOptions options {}
 RuntimeOptions options {
   private value;      // member variable is private instead of protected
   public  getValue(); // streamlined syntax for the obvious trivial case
   public  setValue(); // streamlined syntax for the obvious trivial case
 }
 RuntimeOptions options {
   public getValue();
   public setValue(RuntimeOptions newValue) {
     value = newValue;
     sideEffects();
   }
 }
```

* http://yost.com/computers/java/property-style/index.html

#### Always Autogenerate Getters/Setters

0 up votes, 15 down votes


This proposal auto-generates getters and setters for all fields.

```
 public class User {
   private String name;
   private int age;
 }
 User u = new User();
 u.setName("fname lname");
 assertEquals("fname lname", u.getName());
```

* http://zeusville.wordpress.com/2007/07/25/java-getterssetters/

#### No Syntax for Declaring Properties

2 up votes, 10 down votes

This proposal suggests that that getters/setters are defined as usual, only access/mutation is enhanced.

```
 public class Test {
   private int val;
   private NotifyObject not;
   public int getVal() { return val; }
   public void setVal(int val) {
     this.val = val;
     not.valChanged(val);
   }
 }
 Test t = new Test();
 t.val = 6;
 System.out.println("Val: " + t.val);
```

* http://matthewbot.wordpress.com/2007/09/09/java-7/

### Variants of the Property Access Operator

A common complaint is that using the dot operator for properties makes it hard to
distinguish between member access and property access. Here are some of the various operators
that have been proposed as alternatives:

#### The Arrow Operator

0 up votes, 14 down votes

```
 struct FooBean { Foo foo; }
 struct Point { int x, y; }
 Point p = new Point();
 p->x = p->y = 0; // oh joy
```

* http://weblogs.java.net/blog/hansmuller/archive/2007/01/property_syntax.html

#### Double Colon

1 up votes, 11 down votes

The -> operator was, of course, lifted from C/C++. The other C++ operator worth considering would
be the :: (scope resolution) which you can consider a "fat dot".

```
 Point p = new Point(1, 2);
 p::x = p::y; // oh bliss
```

#### Double Wow

0 up votes, 14 down votes

```
 String foo = "bar";
 a !! foo = b !! "bar"
```

* http://mult.ifario.us/articles/2006/12/28/no-arrow-notation-in-java-please

#### Dot and @ Sign

3 up votes, 11 down votes

```
 student.@name = "Alex";
 student.@major = "CS";
 student.@classes = new String[] {"Database Systems", "Compilers"};
 System.out.println(student.@name + ", whose major is " + student.@major
   + ", is taking the following class: " + student.@classes&#91;1&#93;);
```

* http://www.geocities.com/adcalves/javaproperty/

#### No Property Access Operator

2 up votes, 13 down votes

Mikael Grev suggests to auto-generate the getters and setters, but not to provide any property access operator.

```
 @Property(bound = true)
 public property String donkeyName = "";
```

would still be accessed with

```
 String s = bean.getDonkeyName();
 bean.setDonkeyName(String s);
```

#### Come On, Use The Dot

16 up votes, 2 down votes

Lots of other languages do just fine using the dot for both member and property access. See jhook's comment in
[http://weblogs.java.net/blog/cayhorstmann/archive/2007/01/arrows_in_the_b.html#comments"](http://weblogs.java.net/blog/cayhorstmann/archive/2007/01/arrows_in_the_b.html#comments)



### Overriding Getters and Setters

Auto-generated getters and setters will work in many (most?) cases, but sometimes we need to override them.  

#### Add JavaBeans Style Getter/Setter

10 up votes, 3 down votes

The easiest approach is to simply add getFoo/setFoo methods when the default ones won't do the job.

Here, the getter is auto-generated, and the setter is explicitly supplied.

```
 public class Student
   public property name;
   public void setName(String value) { 
     if (value != null) name = value; 
   }
   ...    
 }
```

#### Add get/set Methods After Property

7 up votes, 5 down votes

In C#, the getter and setter methods are defined immediately after the property.
In C#, one must always supply them, but most proposals in Java use this as a mechanism
to override the default generation.

```
 public Color bgColor 
   get {
     return _bgColor;
   } 
   set { // implied 'value' param of type Color
     _bgColor = value;
     repaint(100);
   };
 // public property of type Color (default setter/getter)
 public Color fgColor get set;
```

* http://blogs.sun.com/joe/resource/java-properties-events.pdf

#### Access Property As default

When accessing a property in a getter or setter, you want to access the underlying field,
not make a recursive call. This proposal suggests to use the keyword **default** to eliminate any confusion.

2 up votes, 9 down votes

```
 public String name
   get { return default; }
   set(v/* type expression here is redundant */) {
     validate(v);
     default = v;
   }
   public int trivialRWProperty get set; // read-write property
   public Date justROProperty get { return new Date(); }; // read-only property
   public int x get; // compile-time error (?)
 }
```

* [Comment by Valery](http://weblogs.java.net/blog/cayhorstmann/archive/2007/01/arrows_in_the_b.html)



### Using Annotations

Note: It is tempting to use annotations, thereby avoiding the introduction of a new keyword.
However, it is against the spirit of annotations.
Annotations are not supposed to modify the bytecodes of the class that is being compiled.
In a managed environment, such as EJB3, code can be edited at load time, but that won't work for client-side Java. 

#### Alexandre Alves

```
 import javax.lang.Property;
 public class Student {
   /**
    * Property annotation generates getters and setters
    */
   @Property
   private String name;
   @Property
   private String major;
   @Property
   private String[] classes;
   public static void main(String args[]) {
     Student student = new Student();
     student.@name = "Alex";
     student.@major = "CS";
     student.@classes = new String[] {"Database Systems", "Compilers"};
     System.out.println(student.@name + ", whose major is " + student.@major
            + ", is taking the following class: " + student.@classes&#91;1&#93;);
   }
 }
```

Implementation available. (Alexandre modified the Java 5 compiler so that it generates the getters/setters
and deals with .@ expressions as lvalues and rvalues.)

* http://www.geocities.com/adcalves/javaproperty/

#### Tom Eugelink

```
 public class Student {
   @get String name;
   public void setName(name) { if (name != null) this.name = name; }
   ...
 }
```

Alternate forms:

```
 @get @set String name;
 @get @set @PropChange String name;
 @get @set @PropChange @VetoChange String name;
```

* http://www.theserverside.com/news/thread.tss?thread_id=29454#142668

#### Gamigin

```
 class MyClass {
   // defaults to providing both getter and setter.
   @PublicProperty
   protected int xyz;
 }
```

* http://forum.java.sun.com/thread.jspa?threadID=739931

#### Ramnivas Laddad

```
 public class Person {
   @Property(ReadOnly) private long id;
   @Property private String name;
 }
```

* http://ramnivas.com/blog/index.php?p=15

#### Jesse Wilson

```
 public class Person {
   private @Property(ObservableProperty.class) String surname;
 }
```

* http://publicobject.com/2007/01/extensible-properties-for-java-language.html

#### Danny Lagrouw

```
 public class Person {
   // Will generate getter and setter:
   private String lastname;
   // Will generate getter:
   @FieldReader protected String firstname;
   // Will generate setter:
   @FieldWriter public Integer age;
   ...
 }
 @FieldAccess(access=NO_ACCESS)
   public class Address {
   // Will not generate anything because of class-level annotation:
   private String street;
 }
```

* http://blog.dannynet.net/archives/81



### Property Objects

These are not proposals for extending the language with first-class support for properties but
rather variations on the theme of simulating properties with fields of a generic Property type.

Some of the shortcomings of properties as objects (identified and discussed by multiple people) are:

* Rely on discipline, not enforced by compiler
* Not compatible with standard & other java apis JavaBeans / JMX etc without byte-code instrumentation
* Not efficient – too many objects on heap – imagine a class with 100s of properties
* Not efficient – typecasting with generics or memorization of many new class names (IntProperty, BooleanProperty ...)

#### John McClean

* http://www.jroller.com/ie/entry/properties_in_java_without_modifying

2 proposals, first one is like the rest but the second one is interesting: host-aware property objects. Quote:

```
 public class Property<E,T>{
   private T value
   private E host;
   public Property(E host){ this.host =host; }
   public Property(E host,T value){
     set(value);
     this.host = host;
   }
   public T get(){
     return value;
   }
   public E set(T value){
     this.value = value;
     return host;
   }
 }
 public class Person {
   final public Property<Person,String> name = new Property<Person,String>(this);
   final public Property<Person,Integer> age = new Property<Person,Integer>(this);
 }
```

We can even chain setters

```
 Person p = new Person();
 p.name.set("bill").age.set(25);
```

#### Joseph Ottinger

* http://www.theserverside.com/news/thread.tss?thread_id=44804

```
 public class NewBean {
   public final Property<Integer> x = new PropertyImpl<Integer>();
 }
 NewBean b = new NewBean();
 b.x.set(5);
 b.x.get();
```

#### Christopher

* http://www.javalobby.org/java/forums/t74270.html?start=30#92026711

```
 class Employee extends Person {
   public final Address workAddress = new Address();
   public final USPhone workPhone = new USPhone();
 }
 /* usage */
 public class PersonTest {
   public static void main(String[] args) {
     Employee emp = new Employee();
     emp.firstName.setValue("christopher");
     emp.phone.setValue("5554443333");
     emp.address.street.setValue("  1234 mocking bird lane");
     // output -- note the proper capitalization from NameField
     System.out.println(emp.firstName); // Christopher
     System.out.println(emp.workPhoneName); // (555) 444-3333
     System.out.println(emp.address.street); // 1234 Mocking Bird Lane
   }
 }
```

#### JP Moresmau

* http://jpmoresmau.blogspot.com/2007/03/java-properties-with-no-language.html

```
 public class PersonBean {
   public final IFullProperty name=new NonNullProperty();
   public final IReadProperty id;
   public final IFullProperty email=new FullProperty(){
     @Override
     public void set(String value) {
       if (value.indexOf('@')==-1){
         throw new IllegalArgumentException("email does not contain @");
       }
       super.set(value);
     }
   };
   public PersonBean(int id,String name){
     this.name.set(name);
     this.id=new ReadOnlyProperty(id);
   }
 }
 bean.getName() -> bean.name.get()
 bean.setName(name) -> bean.name.set(name)
```

#### Joda

* http://joda.sourceforge.net/beans.html

```
 String surname = person.getSurname();      // JavaBeans style, or...
 String surname = person.surname().get();   // Joda style
```

#### Ricky Clarkson

* http://rickyclarkson.blogspot.com/2007/02/ricky-clarkson-properties-for-java.html

```
 class Person {
   public final Property<String> name=new DirectProperty<String>("unknown");
 }
```

#### Matthias Ernst

* http://www.mernst.org/blog/rss.xml#Arent-properties-objects

```
 public final RWProperty<String> name = new Slot<String>();
 mernst.name.get();
 mernst.name.set("matthias");
 mernst.name.attachListener(...);
 label.bind(mernst.name);
```

#### nicba

* [comment Posted by: nicba on January 20, 2007 at 10:31 AM](http://weblogs.java.net/blog/cayhorstmann/archive/2007/01/properties_are.html)

```
 public class Example3 {
   final Property name = new Property();
   public static void main(String[] args) {
     new Example3().runExample();
   }
   private void runExample() {
     System.out.println(name.get());
     name.set("John Smith");
   }
 }
```

#### bean-properties project (Shai Almog)

* https://bean-properties.dev.java.net/

```
 public class NewBean {
   public final Property<Integer> x = new PropertyImpl<Integer>();
 }
```

Which you use like this:

```
 NewBean b = new NewBean();
 b.x.set(5);
 b.x.get();
```

See [bean-properties project analysis](wiki_bean_properties.html) -
Nikolay Botev's analysis of the features of the bean-properties project and how they
compare to native property syntax in general.

#### Bean-Independent Properties (Jesse Wilson)

* http://publicobject.com/2007/09/bean-independent-properties.html

```
public property Property<Customer,String> city
      = new BasicPropertyBuilder(property(""))
          .nonNull()
          .observable(&#35;&#35;propertyChangeSupport)
          .build();
```

Which you use like this:

```
 Customer customer = ...
 String city = customer&#35;city;
 customer&#35;city = "San Francisco";
```


## Comments

Comment: 29.12.2007 at 05:58 PM, Anonymous

Why not just copy what C# 2, 3 and 3.5 is doing?

Declaring properties:

```
 // Default implementation with simple getter and (protected setter).  Creates an anonymous field to go with it.
 public property String name { get; protected set; }
 // Supply your implementation if you need it
 private int _age;
 public property int age { get { return _age; } set { _age = value; } }
```

Access. Just use a dot.
Deal with conflicts between identically named properties and fields the same way that c# does.
(the .net runtime does properties with get_name  and set_name methods internally, the c# compiler
just doesn't let you use them directly).

"Good artists copy, great artists steal"

