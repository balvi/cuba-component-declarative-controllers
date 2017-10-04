[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[![Build Status](https://travis-ci.org/balvi/cuba-component-declarative-controllers.svg?branch=master)](https://travis-ci.org/balvi/cuba-component-declarative-controllers)

# CUBA Platform Component - Declarative Controllers

CUBA component that allows to write generic features for a Controller and use them in a declarative way.


## Installation

1. Add the following maven repository `https://dl.bintray.com/balvi/cuba-components` to the build.gradle of your CUBA application:


    buildscript {
        
        //...
        
        repositories {
        
            // ...
        
            maven {
                url  "https://dl.bintray.com/balvi/cuba-components"
            }
        }
        
        // ...
    }

2. Select a version of the add-on which is compatible with the platform version used in your project:

| Platform Version | Add-on Version |
| ---------------- | -------------- |
| 6.5.7            | 0.2.x - 0.3.x  |
| 6.5.x            | 0.1.x          |

The latest version is: `0.3.4`

Add custom application component to your project:

* Artifact group: `de.balvi.cuba.declarativecontrollers`
* Artifact name: `declarative-controllers-global`
* Version: *add-on version*

### Motivation & Example usage: Comments feature

So what does that mean? Here's an example [balvi/cuba-example-declarative-comments](https://github.com/balvi/cuba-example-declarative-comments):

Let's say you want to add a generic "comments-feature" on entities (in our case you can comment on "Customer" as well as "Product") and you already got everything setup on the entity layer.

Now, as you want to present a default screen for comments on every browser that shows Customers or Products. 
How do you not duplicate the UI logic, where you add a section on the side of the browser to show the comments on a selected item?

The default answer to this would be to create a Superclass called `CommentsBrowser` which extends `AbstractLookup`.
In this controller in the ready method you would probably hook into the `ready` callback and do your stuff.

Now you set `CustomerBrowse extends CommentsBrowser` and `ProductBrowse extends CommentsBrowser` and you are ready to go - no code shared, so what is the problem?

#### Inheritance works exactly once

The problem occurs when you want to implement another feature. Let's imagine, we want to have another generic feature like that it is possible to
assign one or more Documents to entities. When we try to do it as above, we have the problem that `ProductBrowse` already extends `CommentsBrowse`.

How do we resolve this? Creating another subclass `CommentsBrowserWithDocuments`? Would work, but what if i only want the Documents feature?
This would require two classes: `DocumentsBrowser` for the case where we want only the documents feature and the `CommentsBrowserWithDocuments` class for
the case of both features.

#### Delegation to the rescue
As this does not scale at all (having five distinct features would end up in 2^5 = 32 classes), we need to do Delegation - and this is where this app-component comes into play.


## Declarative definition of features through Annotations

This application component brings not only a way of dealing with the inheritance problem. 
It also makes it possible to, instead of programmatically define that certain features should be used, activate these generic features
in a declarative way through the Usage of Annotations.

Here is how the ProductBrowse looks with the declarative definition of the Comments feature:

````
@Commentable(listComponent = "productsTable")
@HasDocuments(listComponent = "productsTable", createDocument = true)
public class ProductBrowse extends AnnotatableAbstractLookup {

}
````

The CustomerBrowse looks like this:

````
@Commentable(listComponent = "productsTable")
public class CustomerBrowse extends AnnotatableAbstractLookup {

}
````

You just pick and choose your features and the implementation gets injected into your controllers. The only requirement is that your Controller
extends from `AnnotatableAbstractLookup` instead of `AbstractLookup` directly (or `AnnotatableAbstractEditor` for editor controllers). But this
is the only superclass you need to extend from. Not for every feature another superclass.


## Defining Annotations 


Generally, there is another example repository, which shows the usage of this application component: [balvi/cuba-example-declarative-comments](https://github.com/balvi/cuba-example-declarative-comments) 
with the exact example of the `@Commentable`.

The application component allows two kinds of Annotations. A class-based Annotation and a field-based Annotation.

### Class-based Annotation: @Commentable

To create custom feature like `@Commentable` you have to do the following:

1. Create a Annotation in your web module like this:


````
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Commentable {
    String listComponent() default "";
    String buttonId() default "commentBtn";
    String buttonsPanel() default "buttonsPanel";
}
````

When you want to parameterise your Annotation, you can define these settings as well as default values within the Annotation.

2. Create a spring bean (in the web module) that implements either `BrowseAnnotationExecutor` or `EditorAnnotationExecutor` for Browse screens or Editors
which contains the logic that sholuld get injected into the controllers:


````
@Component
class CommentableBrowseAnnotationExecutor implements BrowseAnnotationExecutor<Commentable> {


    @Override
    boolean supports(Annotation annotation) {
        annotation instanceof Commentable
    }
    
    @Override
    void init(Commentable annotation, Window.Lookup browse, Map<String, Object> params) {
        // do your logic to add a button to the browse screen...
    }


    @Override
    void ready(Commentable annotation, Window.Lookup browse, Map<String, Object> params) {

    }
}
````

The `supports` method declares for which Annotation this class is responsible.
Besides that it contains Hook methods for the corresponding Controllers (just like in the regular controllers).

For Browse screens these are:

* `init`
* `ready`

For editors the hook methods are:

* `init`
* `postInit`

Those hook methods have the same semantics as the standard CUBA controller hook methods. For more information
you can take a look at the corresponding CUBA docs for 
[AbstractLookup](https://doc.cuba-platform.com/manual-6.6/abstractLookup.html) and [AbstractEditor](https://doc.cuba-platform.com/manual-6.6/abstractEditor.html).


### Field-based Annotations

Field based annotations are useful if you want to enhance a particular Component on the page.

You can find the example for this in the example-project: [@IconCommentedEntities](https://github.com/balvi/cuba-example-declarative-comments/blob/master/modules/web/src/de/balvi/cuba/example/declarativecomments/web/iconcommented/IconCommentedEntities.groovy)


That's it. 

With this you have a single place where you can put your UI logic that is accessible for different screens.

You can easily take this and create [CUBA studio templates](https://www.cuba-platform.com/blog/whats-new-in-cuba-platform-6-4#studio-templates) that will add your Annotations to your screens,
so that you have a UI where you have a couple of checkboxes enable all of your generic features.
