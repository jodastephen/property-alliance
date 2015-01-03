## Review of JavaFX properties

This document is an attempt to quickly review JavaFX properties.

The main hierarchy in JavaFX is large.
It fully integrates properties, observability and binding.

Full Oracle [architecture discussion](https://wiki.openjdk.java.net/display/OpenJFX/JavaFX+Property+Architecture).
Discusses how and why of JavaFX properties.
A related Oracle [pdf](http://www.oracle.com/us/technologies/java/javafx-2-prog-model-1524061.pdf).

---

## Types

`Observable`

* `addListener(InvalidationListener)`
* `removeListener(InvalidationListener)`
* handles invalidation (aim for few invalidation events for performance)


`ObservableValue<T> extends Observable`

* `addListener(ChangeListener<? super T>)`
* `removeListener(ChangeListener<? super T>)`
* `getValue()`
* a basic value that can change over time


Observable types

* `ObservableBooleanValue extends Observable`
* `ObservableNumberValue extends Observable`
* `ObservableIntegerValue extends ObservableNumberValue` (and double/float/long)
* `ObservableObjectValue extends Observable` (sub types for collection elements)
* `ObservableStringValue extends ObservableObjectValue`


Observable Collections

* `ObservableArray<T> extends Observable`
* `ObservableList<T> extends Observable`
* `ObservableSet<T> extends Observable`
* `ObservableMap<T> extends Observable`


`ReadOnlyProperty<T> extends ObservableValue<T>`

* `getBean()` - may return null if N/A
* `getName()` - may return "" if N/A
* base for all properties


`Property<T> extends ReadOnlyProperty<T>, WritableValue<T>`

* `bind(ObservableValue<? extends T>)`
* `unbind()`
* `isBound()`
* `bindBidirectional(Property<T>)`
* `unbindBidirectional(Property<T>)`
* get and set allows full binding capability


`ReadOnlyJavaBeanProperty<T> extends ReadOnlyProperty<T>`

* `fireValueChangedEvent()`
* `dispose()`
* connects to JavaBeans style properties
* see `ReadOnlyJavaBeanBooleanProperty` and friends for implementation (not using java.beans)

`JavaBeanProperty<T> extends ReadOnlyJavaBeanProperty<T>, Property<T>`

* no additional methods


`WritableValue<T>`

* `getValue()`
* `setValue(T value)`
* provides getter and setter independent of main property framework
* subtypes for boolean/numbers/object


`StyleableProperty<T> extends WritableValue<T>`

* `applyStyle(StyleOrigin, T)`
* `getStyleOrigin()`
* `getCssMetaData()`
* allows CSS to interact with properties


`NumberExpression`

* methods to do maths on observables
* used in `NumberBinding`


---

Basic pattern (from [tutorial](http://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm)):

    class Bill {
        // Define a variable to store the property
        private DoubleProperty amountDue = new SimpleDoubleProperty();
        // Define a getter for the property's value
        public final double getAmountDue(){return amountDue.get();}
        // Define a setter for the property's value
        public final void setAmountDue(double value){amountDue.set(value);}
         // Define a getter for the property itself
        public DoubleProperty amountDueProperty() {return amountDue;}
    }

Some typical code (from TextField).
Note three public methods property/getter/setter.
Note that the state is in the property object (type 3).
Note use of `ObjectProperty` not `Property` as the return type.
Note that the property is created on demand (for memory/performance/gc reasons).

    public final ObjectProperty<Pos> alignmentProperty() {
        if (alignment == null) {
            alignment = new StyleableObjectProperty<Pos>(Pos.CENTER_LEFT) {
                ...
                @Override
                public String getName() {
                    return "alignment";
                }
            };
        }
        return alignment;
    }
    private ObjectProperty<Pos> alignment;
    public final void setAlignment(Pos value) { alignmentProperty().set(value); }
    public final Pos getAlignment() { return alignment == null ? Pos.CENTER_LEFT : alignment.get(); }

Variation where property always present:

    private ObjectProperty<EventHandler<ActionEvent>> onAction = new ObjectPropertyBase<EventHandler<ActionEvent>>() {
        ...
        @Override
        public String getName() {
            return "onAction";
        }
    };
    public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty() { return onAction; }
    public final EventHandler<ActionEvent> getOnAction() { return onActionProperty().get(); }
    public final void setOnAction(EventHandler<ActionEvent> value) { onActionProperty().set(value); }

Another variant:

    private StringProperty promptText = new SimpleStringProperty(this, "promptText", "") {
        ...
    };
    public final StringProperty promptTextProperty() { return promptText; }
    public final String getPromptText() { return promptText.get(); }
    public final void setPromptText(String value) { promptText.set(value); }


---

## Blogs

* [One bean to bind all](http://www.marshall.edu/genomicjava/2014/05/09/one-bean-to-bind-them-all/), 2014,
suggests its OK to use JavaFX properties on server side in limited contexts.
* [JPA and JavaFX](http://asipofjava.blogspot.co.uk/2013/05/javafx-properties-in-jpa-entity-classes.html), 2013,
describes using "property access" rather than "field access" in JPA to enable JavaFX property support.





