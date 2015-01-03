## SJSU CS Wiki Home

A copy of the SJSU CS wiki curated by Cay Horstmann.
Retrieved from the Internet Archive.

Version 1.1 last modified by Administrator on 28/10/2007 at 20:01

---

## Property-related JSRs

### JavaBeans (no JSR - not developed under the JCP)

[JavaBeans specification](http://download.oracle.com/otndocs/jcp/7224-javabeans-1.01-fr-spec-oth-JSpec/)

The default reflecting BeanInfo implementation (returned by Introspector.getBeanInfo()) will
have to be updated to include first-class properties.

Some additional suggestions follow.

#### Indexed properties

Forget about indexed properties for now.

#### Bound and Constrained properties

No language support for that. These still need to be coded in the setter.

PropertyChange and VetoableChange events can be defined easier using the first-class event syntax illustrated next.

#### Add support for events

```
 public event DataChangeListener dataChange;
```

becomes

```
 private ListenerList<DataChangeListener> &#36;dataChange;

 public void &#36;addDataChangeListener(DataChangeListener listener) { ... }
 public void &#36;removeDataChangeListener(DataChangeListener listener) { ... }
```

The following syntax is supported:

```
 my.dataChange += x; // addDataChangeListener(x);
 my.dataChange -= x; // removeDataChangeListener(x);
 my.dataChange.fire(EventObject /* or DataChangeEvent */ e); // Tricky to determine the data type of e
```

To enable use of first-class events in custom AWT/Swing components, the Component class will have to change.
See [this page for details](https://java.sun.com/products/javabeans/docs/getListeners.html):


#### JavaBeans Annotations (no more BeanInfo)

Something along those lines is already being worked on at
the [JBDT project](https://jbdt-spec-public.dev.java.net/javadoc/java/beans/package-summary.html)

```
 @Bean(customizerClass=MyBeanCustomizer.class, iconUrl="com/my/MyBean.ico")
 public class MyBean {

   @Editor(editorClass = MyPropEditor.class)
   public property String prop;

 }
```


### Unified Expression Language

The BeanELResolver uses the JavaBeans framework APIs. No changes are needed to the Unified EL framework
for it to support first-class properties.


### JSR 3: JavaTM Management Extensions (JMXTM) Specification

[JSR 3](http://jcp.org/en/jsr/detail?id=3)
Standard MBean attributes follow the standard property getter/setter naming convention.

The StandardMBean class will have to be updated to recognize first-class properties.


### JSR 57: Long-Term Persistence for JavaBeansTM Specification

[JSR 57](http://jcp.org/en/jsr/detail?id=57)
The java.beans.DefaultPersistenceDelegate uses the JavaBeans introspection API and BeanInfos.
As long as the default BeanInfo lists native properties, no changes would be needed to the
Long Term Persistence API for JavaBeans.


### JSR 199: JavaTM Compiler API

From Remi Forax's [spec](http://docs.google.com/View?docid=dfhbvdfw_1f7mzf2)
"JSR 199 and properties TBF (two new nodes Property and SharpAccess)"


### JSR 220: Enterprise JavaBeansTM 3.0

[JSR 220](http://jcp.org/en/jsr/detail?id=220)
The annotations that bind to properties (e.g. @Column) will have to be updated.


### JSR 273: Design-Time API for JavaBeansTM JBDT

[JSR 273](http://jcp.org/en/jsr/detail?id=273)
[java.net project](https://java.net/projects/jbdt-spec-public/)
This JSR will need to be extended to support first-class properties.

In particular the [Property annotation](https://jbdt-spec-public.dev.java.net/javadoc/java/beans/package-summary.html)
will need to be updated.


### JSR 295: Beans Binding

[JSR 295](http://jcp.org/en/jsr/detail?id=295)
[java.net project](https://java.net/projects/beansbinding/)
Will have to be updated to include an org.jdesktop.beansbinding.Property implementation
for native properties as demonstrated by Remi Forax
[here](http://weblogs.java.net/blog/forax/archive/2007/09/mixing_property_1.html).


### JSR 303: Bean Validation

[JSR 303](http://jcp.org/en/jsr/detail?id=303)
The spec is not ready and I could not find any useful information such as a java.net project RI or sample code.
It seems like the starting point for this could be the Spring framework validation apis.

The annotations that bind to properties will have to be updated.


## Comments

None
