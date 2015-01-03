## SJSU CS Wiki Home

A copy of the SJSU CS wiki curated by Cay Horstmann.
Retrieved from the Internet Archive.

Version 1.51 last modified by Cay Horstmann on 17/05/2008 at 08:31

---

Most modern object-oriented language support **properties**, object attributes  with a simple syntax for getting and setting their values. 

```
button.text = "Click me!"
```

Java is a notable exception. In Java, properties are implemented using the JavaBeans convention, as a pair of get/set methods. 

```
button.setText("Click me!")
```

Some people find this a nuisance and want to fix it.
Others find that it is not worth fixing, or even worse, a devilish plot to evict us from the object-oriented paradise.
Unfortunately, properties aren't as sexy as closures, so there hasn't been a lot of follow-through.
Quite a few bloggers bemoan the issue, come up with a draft of a solution, and move on to greener pastures.
I thought it would be helpful to have one place that gathers the collective wisdom, in the hope that a complete solution will emerge.

In these pages, Nikolay Botev, a computer science student at San Jose State University, collected a large number of links for use cases,
proposals, and issues. I did some editing, and hopefully, so will some of you. Please make an account and contribute. 

* [Use Cases](wiki_use_cases.html) - use cases for language properties
* [Proposals](wiki_proposals.html) - various proposals for native properties syntax & semantics collected from all over the web
* [Issues](wiki_issues.html) - issues to be aware of when dealing with native properties
* [Property related JSRs](wiki_related_jsrs.html) - JSRs that could be affected by the addition of native properties to the language
* [Properties in Other Languages](wiki_other_languages.html) - properties have been done many times before - this sections explores how other languages do native properties
* [Analysis of bean-properties project](wiki_bean_properties.html) - a well-known no language change project


## Comments

Comment: 23.02.2008 at 12:43 PM, Anonymous

why not just use a public attributes?

Comment: 02.03.2008 at 01:21 AM, Todd B.Musheno

I agree why not, the reason people use methods is for later injection of code (does that set need to be backed by a db, not now, but what about in 6 months?)
I think the real issue here is not in the syntax of the get/set, but in the fact that developers must implement a get set, and then the relationship between then goes away (on a compiler level).

Maybe:

```
public class Bla {
  @Property
  private String test = null;
}
```

...kind of thing that spits out getters & setters for the simple pojo, may be in order, but that would not change the `button.setText("Click me!")` syntax.
