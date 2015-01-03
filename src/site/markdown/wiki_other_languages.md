## SJSU CS Wiki Use Cases

A copy of the SJSU CS wiki curated by Cay Horstmann.
Retrieved from the Internet Archive.

Version 1.69 last modified by XWikiGuest on 08/04/2008 at 23:07

---

## Properties in Other Languages

### JavaFX Script

"bean properties and public fields of Java classes are reflected as JavaFX attributes"
- [JavaFX Script Programming Language Reference](https://openjfx.dev.java.net/JavaFX_Programming_Language.html).

All examples below are also taken from the JavaFX Reference document.

#### Attribute Declaration

```
 class X {
   attribute a: Number;
   attribute b: Number;
 }
```

#### Attribute Initialization (Object Literals)

```
 var chris = Person {
   name: "Chris"
   children:
   [Person {
      name: "Dee"
    },
    Person {
      name: "Candice"
    }]
   };
```

#### Attribute Access

```
 class X {
   attribute a: Number;
   attribute b: Number;
 }

 attribute X.a = 10; // Default Initial values
 attribute X.b = -1;

 var x = new X();
 System.out.println(x.a); // prints 10
 System.out.println(x.b); // prints -1
```

#### Beans Binding

Lazy binding means that the binding is not evaluated until the variable is first accessed.
Eager binding (the default) evaluates at the time of declaration.

```
 class X {
   attribute a: Number;
   attribute b: Number;
   attribute c: Number;
 }

 attribute X.a = 10;

 attribute X.b = bind a + 10;
 attribute X.c = bind lazy b + 10;


 var x = new X();
 System.out.println(x.a); // prints 10
 System.out.println(x.b); // prints 20
 System.out.println(x.c); // prints 30
 x.a = 5;
 System.out.println(x.a); // prints 5
 System.out.println(x.b); // prints 15
 System.out.println(x.c); // prints 25
```

#### Bound Properties (attribute triggers)

```
 class X {
   attribute a: Number;
   attribute b: Number;
 }

 trigger on X.b = newValue {
   System.out.println("X.b is now {newValue}");
 }
```

### Groovy

Examples from http://groovy.codehaus.org/Groovy+Beans .

#### Simple Example

```
 class Customer {
   // properties
   Integer id
   String name
   Date dob

   // sample code
   static void main(args) {
     def customer = new Customer(id:1, name:"Gromit", dob:new Date())
     println("Hello ${customer.name}")
   }
 }
```

#### Advanced Example

```
 class Foo {
   // read only property
   final String name = "John"

   // read only property with public getter and protected setter
   Integer amount
   protected void setAmount(Integer amount) { this.amount = amount }

   // dynamically typed property
   def cheese
 }
```

#### Even more advanced example

ExpandoMetaClass in action, available starting with Groovy 1.1.

From [ExpandoMetaClass](http://groovy.codehaus.org/ExpandoMetaClass+-+Properties)

```
 class Book {
   String title
 }
 Book.metaClass.getAuthor <;<; {-> "Stephen King" } // Class prototype allows dynamic introduction of property

 def b = new Book("The Stand")

 assert "Stephen King" == b.author
```

#### Custom Binding (for Swing)

From [SwingBuilder](http://groovy.codehaus.org/SwingBuilder.bind)

```
swing.frame() {
  panel {
    textField('Change Me!', id:'tf')
    button(text: bind(source:tf.document, sourceEvent:'undoableEditHappened', sourceValue:{tf.text}))
  }
}
```

### JavaScript

"An ECMAScript object is an unordered collection of properties"
 - ECMAScript: A general purpose,
cross-platform programming language http://www.mozilla.org/js/language/E262.pdf

Objects in JavaScript up to version 1.5 are like hashtables and as such do not have support for "real" properties with getters and setters. Properties with getters and setters are introduced in JavaScript 2.0 which is still under development at the time this document was written.

Properties are like entries in a hashtable and can be accessed with either a dot or array syntax.

```
 var object = new Object();
 object.myProperty = 23
 alert(object.myProperty + 5) // outputs 28

 object["prop2"] = 8
 alert(object["myProperty"] + object.prop2) // outputs 31
```

#### Object Initializers (aka Object Literals)

JavaScript 1.5 (ECMA-262 Edition 3, Dec 1999) introduces the object literal syntax:

```
var object = { myProperty: 23, prop2: 8 };
```

#### XML Object Initializers

E4X introduces an XML initializer syntax:
 - Example from http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Guide:Processing_XML_with_E4X

```
 var person = <;person id="123">
   <;name>Bob Smith<;/name>
   <;likes>
     <;os>Linux<;/os>
     <;browser>Firefox<;/browser>
     <;language>JavaScript<;/language>
     <;language>Python<;/language>
   <;/likes>
 <;/person>;

 alert(person.@id); // 123
 alert(person.name); // Bob Smith
 alert(person['name']); // Bob Smith
 alert(person.likes.browser); // Firefox
 alert(person['likes'].browser); // Firefox
```

#### JavaScript 2.0 (ECMAScript Edition 4, under development)

JavaScript 2.0 introduces "real" properties with getters and setters (along with support for
class-based vs prototype-based objects and what looks like a pluggable (optional) type system - type annotations).

Examples shown below are taken from http://www.ecmascript.org/es4/spec/overview.pdf

Virtual Properties:

```
 class C {
   function get x() { ... }
   function set x(value) { ... }
 }
```

"(A getter without a setter implements a read-only property.) Virtual properties can’t be added dynamically
even if their class is dynamic."
 - http://www.ecmascript.org/es4/spec/overview.pdf

Catch-all getters and setters:

Catch-all getters/setters handle references to dynamic properties only.
Dynamic properties are properties introduced in object instances at runtime, as opposed
to properties created as part of the class definition.

```
 class D {
   meta function get(name) { ... }
   meta function set(name, value) { ... }
 }
```

Dynamic property example:

```
 dynamic class C {
 }
 c = new C
 c.x = 37 // adds a property
 delete c.x // removes it again
```

### ActionScript 3.0

ActionScript 3.0 supports the following syntax for defining properties, which was adopted
for JavaScript 2.0 (described above):

```
 package {
   public class Team { 
     var teamName:String; 
     var teamCode:String; 
     var teamPlayers:Array = new Array(); 
     public function Team(param_name:String, param_code:String) { 
       teamName = param_name; 
       teamCode = param_code; 
     } 
     public function get name():String { 
       return teamName; 
     } 
     public function set name(param_name:String):void { 
       teamName = param_name; 
     }
   } 
 }

 var giants:Team = new Team("San Fran", "SFO"); 
 trace(giants.name);   // output: San Fran
 giants.name = "San Francisco"; 
 trace(giants.name);   // output: San Francisco
```

### Ruby

Properties in Ruby are called attributes.
Member fields are called instance variables and are always private. Access to instance variables is qualified with @.

#### Full Syntax

```
 class Person

   def initialize(name)
     @name = name
   end

   # getter
   def name
     @name
   end

   # setter (operator-overloading-like syntax)
   def name=(newName)
     @name = newName
   end

 end

 p = Person.new("Ivan")

 print p.name
 p.name = "John"
 print p.name
```

#### Short-hand Syntax

```
 class Person
   def initialize(name)
     @name = name
   end

   # getter shorthand syntax (macro-like method generation)
   attr_reader :name

   # setter shorthand syntax
   attr_writer :name
 end
```

#### Advanced features (property literals, dynamic properties)

```
 class DynamicPropertiesClass

   # Demonstrates property literals (qualified with colon /:/)
   # and dynamic property generation
   def method_missing(methodId, *args)
     name = methodId.id2name
     if name.index("=", -1) != nil && args.length == 1 then
       name = name[0, name.length-1]
       self.class.module_eval("attr_accessor :" + name)
       self.send(methodId, *args)
     else
       super
     end
   end

 end

 x = DynamicPropertiesClass.new
 y = DynamicPropertiesClass.new

x.name = "hello world" # property dynamically
print x.name # prints "hello world"
print y.name # error - property does not exist in class; 
             # it was only introduced in the instance pointed to by x
```

### Python

#### Limitations

Python does not support encapsulation (private members).
Both field (data) and method members of a class are defined as attributes and are public by default.

```
 class Person:   # simple class declaration
   name = ''     # attributes
   age = 0
 joe = Person() 
 joe.name = 'Ivan'
 joe.age = 25
 print joe.name, joe.age  # prints "Ivan 25"
 joe.height = 510         # dynamic introduction of attributes is possible
 print joe.height         # prints "510"
```

#### Property support regardless

Python has no language-level support for native properties.
Since version 2.3, however, within the framework of new-style classes, the language does
provide the machinery on top of which to build property support with data encapsulation and getter
and setter methods for access.

This is implemented in the built-in property() function. That function constructs a property
object very much like the property objects idiom in java proposed by some as an alternative
to language-level syntax in Java.

```
 >>> class Person(object):
   __name = ''
   def __get_name(self):
     print "getting name, which is " + self.__name
     return self.__name
   def __set_name(self, value):
     print "setting name to " + value
     self.__name = value
   name = property(__get_name, __set_name)

 >>> p = Person()
 >>> p.name = "hello"
 setting name to hello
 >>> p.name
 getting name, which is hello
 'hello'
```

#### Name Mangling

Name mangling can be used to achieve some degree of information hiding in Python.
Attribute names beginning with two underscores are internally
converted to _classnameattrname, but this is primarily intended to avoid name collisions
in class hierarchies (an inherited class defining a member by the same name as the member
of a parent class, but for a different purpose).

With name mangling we can apply the Java getter/setter Java idiom to Python:

```
 class Person:
   def __init__(self):
     self.__name = ''
   def getName(self):
     return self.__name
   def setName(self, value):
     self.__name = value
    
 p = Person()
 p.setName("Joe")
 print p.__name        # AttributeError: __name attribute does not exist, it was mangled
 print p._Person__name # Joe - mangled attribute __name is still accessible by its full name
 print p.getName()     # Joe
 p.__name = 'Ivan'     # new attribute created
 print p.getName()     # still Joe
 print p.__name        # Ivan (value of newly created attribute
```

#### Catch-all getters and setters

Python supports a mechanism for trapping attribute access via special catch-all methods:

```
>>> class CatchAll(object):
  def __getattribute__(self, name):
    print 'reading ' + name
    return object.__getattribute__(self, name)
  def __setattr__(self, name, value):
    print 'writing ' + name
    object.__setattr__(self, name, value)

    
>>> c = CatchAll()
>>> c.name = 'Joe'
writing name
>>> print c.name
reading name
Joe
>>>
```

The above example uses new-style class functionality available since Python 2.3.
An old-style class implementation is also possible but more involved.

#### Custom property support implementation

The property() builtin function is implemented on top of the concept of descriptors in Python.
Here is a sample implementation of a Property class which mimics the functionality provided by the property built-in.

```
>>> class Property(object):
  def __init__(self, getter, setter):
    self.__getter = getter
    self.__setter = setter
  def __get__(self, instance, owner=None):
    return self.__getter(instance)
  def __set__(self, instance, value):
    self.__setter(instance, value)

>>> class Person(object):
  __name = ''
  def __get_name(self):
    print "getter of name property invoked"
    return self.__name
  def __set_name(self, value):
    print "setter of name property invoked"
    self.__name = value
  name = Property(__get_name, __set_name)
>>> p = Person()
>>> p.name = "hello"
setter of name property invoked
>>> p.name
getter of name property invoked
'hello'
```

### Perl (version 5)

Perl 5 introduces an object-oriented programming model.
It has no language-level support for properties but instead adopts a convention where the getter and setter
accessors are combined within a single method.

```
package Person;

sub new {
  my $class = shift;
  my $self  = {
    NAME => shift
  };
  return bless($self);
}

# Combined getter + setter
sub name {
  my $self = shift;
  if (@_) { $self->{NAME} = shift }
  return $self->{NAME};
}

1;

package Main;

$p = Person->new("John");
print $p->name, "&#92;n";    # getter use (0 args)
$p->name("Doe");         # setter use (1 arg)
print $p->name, "&#92;n";
```

### PHP 5

As of version 5, PHP introduces an object-oriented syntax, but no language-level properties.
Properties can be simulated with getters and setters like in Java:

```
class Person {

  private $name;

  public function __construct($name) {
    $this->name = $name;
  }

  public function get_name() {
    return $this->name;
  }

  public function set_name($name) {
    $this->name = $name;
  }

}

$p = new Person("Joe");
print($p->get_name()."&#92;n");
$p->set_name("Jon");
print($p->get_name()."&#92;n");

print("Before: ".$p->value."&#92;n");  # Warning: unknown field
$p->value = 23;    # Fields can be introduced dynamically
print("After: ".$p->value."&#92;n");
```

The above outputs:

```
Joe
Jon
PHP Notice:  Undefined property:
Before:
After: 23
```

#### Overloading (catching access to missing fields)

Special functions __get and __set are called when access to missing properties is detected.
This can be used to implement dynamic classes that trap all field access.

```
class Dynamic {

  private $vars;

  public function __get($name) {
    print("Getting value of $name&#92;n");
    return $this->vars[$name];
  }
  
  public function __set($name, $value) {
    print("Setting value of $name to $value&#92;n");
    $this->vars[$name] = $value;
  }
  
}

$d = new Dynamic();

$d->name = "joe";
print("$d->name&#92;n");
```

The above code will output:

```
Setting value of name to joe
Getting value of name
joe
```

### C&#35;

#### Basic Example

```
class Person
    {

        private string name;

        public string Name
        {
            get
            {
                return this.name;
            }
            set
            {
                this.name = value;
            }
        }

        public static void Main(string[] args)
        {
            Person p = new Person();
            p.Name = "Peter";
            Console.Out.WriteLine(p.Name);
        }

    }
```

#### Auto properties

C# 3.0 introduces a short-hand syntax for declaring simple properties where the getter and setter
simply access a private field. The above example can be rewritten as follows:

```
class Person
    {

        public string Name
        {
            get; set;
        }

        public static void Main(string[] args)
        {
            Person p = new Person();
            p.Name = "Peter";
            Console.Out.WriteLine(p.Name);
        }

    }
```

#### Modifiers and Attributes

Attributes (aka Annotations in Java) can be applied both to the property and its get/set accessors.
Different visibility modifiers can be applied to the get and set accessors.
The visibility of accessors must be at least as narrow as the visibility of the property.

```
class Person
    {

        [PropertyAttribute]
        public string Name
        {
            [GetterAttribute] protected get;
            [SetterAttribute] private set;
        }

    }
```

### J&#35;

#### .NET Syntax

```
public class Person {

  private String name;

  /** @property */
  public String get_Name() {
    return this.name;
  }

  /** @property */
  public void set_Name(String name) {
    this.name = name;
  }

  public static void main(String[] args) {
    Person p = new Person();
    p.set_Name("Joe");
    System.out.println(p.get_Name());
  }
```

Requirements:

* The @property javadoc annotation is required.
* Both accessors must have the same visibility
* The return type of the getter and the argument type of the setter must be the same
* Attributes (annotations) are defined with javadoc tags to the accessor methods.
    * @attribute applies the attribute to the property
    * @attribute.method applies the attribute to the property accessor

Source: Defining and Using Properties http://msdn2.microsoft.com/en-us/library/d0c0we7e(VS.80).aspx

#### Bean-style Syntax

```
public class Person {

  private String name;

  /**@attribute Description("Attribute on the property")*/
  /**@attribute.method Description("Attribute on get accessor")*/
  /** @beanproperty */
  public String getName() {
    return this.name;
  }

  /** @beanproperty */
  public void setName(String name) {
    this.name = name;
  }

  public static void main(String[] args) {
    Person p = new Person();
    p.setName("Joe");
    System.out.println(p.getName());
  }
```

Source: Bean-style Properties http://msdn2.microsoft.com/en-us/library/f8yxzfy6(VS.80).aspx

### VisualBasic.NET

```
Public Class Person
    Private name As String

    Public Property Name() As String
        Get
            Return name
        End Get

        Set (ByVal Value As String)
            name = value
        End Set
    End Property
End Class

Dim p As Person = new Person()
Console.Out.WriteLine(p.Name)
```

#### Indexed Properties

```
Public Class Test

  ' Private storage for the objects in the Widgets
  ' collection (same for both implementations).
  Private mcol As New Collection

  Public Property Item(Index As Variant) As Widget
    Get
      Return mcol.Item(Index)
    End Get

    Set (Index As Variant, ByVal Value As String)
      mcol.Item(Index) = Value
    End Set
  End Property

End Class

' Property Access
Set Widgets.Item(4) = wdgMyNewWidget
```

### Object Pascal

Properties can be read-write, read-only or write-only. Property accessors can be either methods or fields.

```
type
  TPerson = class
    private
      FName: String;
      FID: Integer;

      function GetName: String;
      procedure SetName(Value: String);
    public
      
      // Simple property (read & write directly from field)
      property ID: Integer read FID write FID;
    published     // Published Properties appear in design environment

      // Read & write using methods (getter/setter)
      property Name: String read GetName write SetName;
  end;

implementation

function TPerson.GetName: String;
begin
  GetName := Self.FName;
end;

function TPerson.SetName(Value: String);
begin
  Self.FName := Value;
end;
```

#### Array (Indexed) Properties

```
type
  TPerson = class
    private
      function GetPhoneNumber(Index: Integer): String;
      procedure SetPhoneNumber(Index: Integer; Value: String);
    public
      property PhoneNumbers[Index: Integer]: String read GetPhoneNumber write SetPhoneNumber;
  end;
```

#### Index Specifiers

From the Delphi Language Guide (book): "Index specifiers allow several properties to share
the same access method while representing different values."

```
type
  TRectangle = class
    private
      FCoordinates: array[0..3] of Longint;
      function GetCoordinate(Index: Integer): Longint;
      procedure SetCoordinate(Index: Integer; Value: Longint);
    public
      property Left: Longint index 0 read GetCoordinate write SetCoordinate;
      property Top: Longint index 1 read GetCoordinate write SetCoordinate;
      property Right: Longint index 2 read GetCoordinate write SetCoordinate;
      property Bottom: Longint index 3 read GetCoordinate write SetCoordinate;
      property Coordinates[Index: Integer]: Longint read GetCoordinate write SetCoordinate;
  end;
```

### Visual Basic

```
'' Person.cls module file
'' Class modules are saved in files with the extension .cls

Option Explicit

Private sName As String

Private dDepartment As Department 'Department is a class

Public Property Get Name() As String
   Name = sName
End Property


' Let used for value types
Public Property Let Name(ByVal NewName As String)
   Name = NewName
End Property

Public Property Get Department() As String
   Department = dDepartment
End Property


' Set used for object types
Public Property Set Department(ByRef NewDepartment As Department)
   Set Department = NewDepartment
End Property
```

Using properties is easy:

```
Dim p As Person
   Set p = New Person
   ' Assign a new property value.
   p.Name = "John"
   ' Display the property value.
   MsgBox p.Name
```

#### Indexed Properties

```
' Private storage for the objects in the Widgets
' collection (same for both implementations).
Private mcol As New Collection

Public Property Get Item(Index As Variant) As Widget
   Set Item = mcol.Item(Index)
End Property

' Property Access
Set Widgets.Item(4) = wdgMyNewWidget
```

### VBScript

Since version 5 VBScript supports the same syntax for properties as the one for Visual Basic described above.

### Objective-C

```
@interface Person : NSObject
{
NSString *name;
NSString *email;
}
@property(copy, readwrite) NSString *name;
@property(copy, readwrite) NSString *email;
@end


@implementation Person
@synthesize value;
@dynamic email;
- (NSString *)email {
    return email;
}

- (void)setEmail:(NSString *)newEmail {
    if (newEmail != email) {
        email = [newEmail copy];
    }
}
@end


Person *p = [[Person alloc] init];

// Access using dot syntax
p.name = @"John";
NSLog(@"p.name = @", p.name);

// Access using method calls (messages
[p setEmail:@"my@yahoo.com"];
NSLog([p email];
```

#### Subclassing

Subclasses can redefine properties and add accessors (change from readonly to read-write for example,
reusing the inherited getter).

### C++ (98 and 0x)

C++ (neither C++98, nor the new [C++0x](http://en.wikipedia.org/wiki/C%2B%2B0x) does not support properties
as a language feature. However, due to the language's extensibility, property objects with native-like syntax
can, and have been implemented.

One example is [Implementing Properties In C++](http://www.codeproject.com/cpp/cppproperties.asp).
Here is how it works (examples copy-pasted from the article):

Declare class with properties:

```
class PropTest
{
public:
    PropTest()
    {
        Count.setContainer(this);
        Count.setter(&PropTest::setCount);
        Count.getter(&PropTest::getCount);
    }
    
    int getCount()
    {
        return m_nCount;
    }

    void setCount(int nCount)
    {
        m_nCount = nCount;
    }

    property<;PropTest,int,READ_WRITE> Count;

private:
    int m_nCount;
};
```

Use the class:

```
int i = 5,
    j;

PropTest test;

test.Count = i;    // call the set method --

j = test.Count;    //-- call the get method --
```

### D

From http://www.digitalmars.com/d/property.html#classproperties

"Properties are member functions that can be syntactically treated as if they were fields.
Properties can be read from or written to. A property is read by calling a method with no arguments;
a property is written by calling a method with its argument being the value it is set to."

```
struct Person
{
    string name() { return m_name; }  // read property

    string name(string value) { return m_name = name; } // write property

  private:
    string m_name;
}

// Access property
int test()
{
    Person p;

    p.name = "John";    // same as p.name("John");

    p.name += " Martoz";  // COMPILE ERROR!!! Not allowed.

    return p.name + " Martoz";  // same as return p.name() + " Martoz";
}
```

More from spec:

"The absence of a read method means that the property is write-only. The absence of a write method means that the property is read-only. Multiple write methods can exist; the correct one is selected using the usual function overloading rules.
In all the other respects, these methods are like any other methods. They can be static, have different linkages, be overloaded with methods with multiple parameters, have their address taken, etc.
Note: Properties currently cannot be the lvalue of an op=, ++, or -- operator. "

### Managed Extensions for C++ (.NET 1.0)

Example copied from http://msdn2.microsoft.com/en-us/library/z974bes2(VS.71).aspx

```
// keyword__property.cpp
// compile with: /clr
#using <;mscorlib.dll>
using namespace System;

__gc class MyClass
{
public:
   MyClass() : m_size(0) {}
   __property int get_Size() { return m_size; }
   __property void set_Size(int value) { m_size = value; }
   // compiler generates a pseudo data member called Size
protected:
   int m_size;
};

int main()
{
   MyClass* class1 = new MyClass;
   int curValue;

   Console::WriteLine(class1->Size);
   
   class1->Size = 4;        // calls the set_Size function with value==4
   Console::WriteLine(class1->Size);

   curValue = class1->Size;  // calls the get_Size function
   Console::WriteLine(curValue);
   return 0;
}
```

#### Indexed Properties

Indexed properties are also supported. Example copied from http://msdn2.microsoft.com/en-us/library/aa712773(VS.71).aspx

```
// __property3.cpp
// compile with: /clr
#using <;mscorlib.dll>
using namespace System;

public __gc
class X {
public:
   __property int get_Prop(int i, int j) {
      return m_val - i - j;
   }
   __property void set_Prop(int i, int j, int value) {
      m_val = value + i + j;
   }
   int m_val;
};
int main() {
   X* x = new X;
   x->Prop[1][2] = 3;
   Console::WriteLine(x->Prop[1][2]);
}
```

#### Constraints

```
int __gc* pi = &pI->Size;   // COMPILE ERROR: C2102: cannot get address of property member

   int g(IFC *pI) { int i = pI->Size = 10; }  // COMPILE ERROR: C2440: cannot use as lvalue and rvalue at the same time
```

### C++/CLI

#### Simple Properties

Scalar properties can be defined with implicit getter/setter and backing field like auto-properties in C# 3.0.

Example copied from http://msdn2.microsoft.com/en-us/library/2f1ec0b1(VS.80).aspx

```
// SimpleProperties.cpp
// compile with: /clr
using namespace System;

ref class C {
public:
   property int Size;
};

int main() {
   C^ c = gcnew C;
   c->Size = 111;
   Console::WriteLine("c->Size = {0}", c->Size);
}
```

#### Scalar Properties

```
// mcppv2_property.cpp
// compile with: /clr
using namespace System;
public ref class C {
   int MyInt;
public:

   // property block
   property int Property_Block {

      int get();

      void set(int value) {
         MyInt = value;
      }
   }
};

int C::Property_Block::get() {
   return MyInt;
}

int main() {
   C ^ MyC = gcnew C();

   MyC->Property_Block = 21;
   Console::WriteLine(MyC->Property_Block);
}
```

#### Indexed Properties

Example copied from http://msdn2.microsoft.com/en-us/library/52d21xwx(VS.80).aspx

```
// mcppv2_property_2.cpp
// compile with: /clr
using namespace System;
public ref class C {
   array<;int>^ MyArr;

public:
   C() {
      MyArr = gcnew array<;int>(5);
   }

   // user-defined indexer
   property int entry[int] {
      int get(int index) {
         return MyArr[index];
      }
      void set(int index, int value) {
         MyArr[index] = value;
      }
   }
};

int main() {
   C ^ MyC = gcnew C();

   // use the user-defined indexer
   Console::Write("[ ");
   for (int i = 0 ; i <; 5 ; i++) {
      MyC->entry[i] = i * 2;
      Console::Write("{0} ", MyC->entry[i]);
   }
   Console::WriteLine("]");
}
```

#### Overloaded Indexed Properties

Example copied from http://msdn2.microsoft.com/en-us/library/52d21xwx(VS.80).aspx

```
// mcppv2_property_6.cpp
// compile with: /clr
ref class X {
   double d;
public:
   X() : d(0.0) {}
   property double MyProp[int] {
      double get(int i) {
         return d;
      }

      double get(System::String ^ i) {
         return 2*d;
      }

      void set(int i, double l) {
         d = i * l;
      }
   }   // end MyProp definition
};

int main() {
   X ^ MyX = gcnew X();
   MyX->MyProp[2] = 1.7;
   System::Console::WriteLine(MyX->MyProp[1]);         // Outputs 3.4
   System::Console::WriteLine(MyX->MyProp["test"]);    // Outputs 6.8
}
```

### Scala

Every data member access in scala is a method call. Scala also supports operator overloading.
For these reasons, although properties are not a native feature of the language, the following idiom
for defining properties in terms of getter/setter pairs has been defined
http://www.scala-lang.org/docu/files/ScalaOverview.pdf

```
class Celsius {
private var d: int = 0
def degree: int = d
def degree_=(x: int): unit = if (x >= 273)
d = x
}

val c = new Celsius; c.degree = c.degree - 1 // Access with dot operator like a field
```

### F#

Property definition and access:

```
type Person =
  class
    // private fields
    val mutable name : string
    val age : int

    // constructor
    new(aname, aage) = { name = aname; age = aage }

    // long syntax: read-write property
    member x.Name
      with get() = x.name
      and set(v) = x.name <;- v

    // short syntax: a read-only property
    member x.Age = x.age
  end
;;

// Access property
let p: Person = new Person("John", 23)
System.Console.WriteLine(p.Name);;
p.Name <;- "Peter"
System.Console.WriteLine(p.Name);;
```

### Smalltalk

Objects in Smalltalk only expose methods (message selectors) as public members and all instance variables are private.
Properties are expressed as accessor methods for instance variables.

Class:

```
Object subclass: #Person
  instanceVariableNames: 'name'
  classVariableNames: ''
  poolDictionaries: ''
  category: 'My-Model'
```

Getter and Setter Methods:

```
name
  "Answer the value of name"

  ^ name

name: anObject
  "Set the value of name"

  name := anObject
```

Test Case:

```
TestCase subclass: #PersonTestCase
  instanceVariableNames: ''
  classVariableNames: ''
  poolDictionaries: ''
  category: 'My-Tests'

testPersonName

  | p |
  p := Person new.
  
  p name: 'John'.
  self should: p name = 'John'.
```

### Eiffel
### Ada
### COBOL
### ColdFusion
### Common Lisp
### Fortran
### GraphTalk
### Io
### Oberon
### Objective Caml
### Oz
### Tcl
### Visual Prolog
### Windows PowerShell
### XL



## Comments

Comment: 29.12.2007 at 02:20 PM, Anonymous

I would have thought that the compatibility / encapsulation side of properties should be spelled out,
i.e. the reason why the getter/setter convention exists of just public exposed field.
I think [this link](http://tomayko.com/articles/2005/01/20/getters-setters-fuxors) discusses that topic reasonably (in a python vs java context)

