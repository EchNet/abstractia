# abstractia

A weekend rampage inspired by Endeca Commerce

Demo Features
-------------

* Guided nav... just enough for show.
    + Randomization of data.
    + Breadcrumbs.
    + Refinement links.
        - Isolate "state" from record results.
        - Create class UrlFormatter.
        - Add list of available refinements to result.
    + Bin counts.

Demo Features - TODO
--------------------

* Core merchandising features.
    + Supplemental content triggering.
    + Presentation triggering.
    + Record boost/bury.
    + Dimval boost/bury.
    + Precedence rules by means of triggering.

* AJAX support
    + Round out the paging record list.
    + JSON output
    + Work with or without JS.

* Pseudo text search.
    + Search terms that match dimension values (or are close) only.
    + Really just an OR filter.
    + Forget relevancy ranking.
    + Reflected in crumblies.


Architecture
------------

* The reshapable page.
    + Generalization of the landing page concept.
    + There is really only one web page of which all pages are just variations.
    + Some components are always there (defined by config).
    + What is a landing page in this context?  
        - The Category template page might be more telling.
    + Cross-cutting page attributes.
        - e.g. Stylesheet
        - e.g. Title
        - Are these components?
        - If so, what does that imply for page templates?
        - what level of triggering based on page structure?

* Merch control over Component features.
    + Default configuration given by system template.
    + ANYTHING may be overridden by trigger.
    + override may reference the super override, or the system base.
    + Independent of the component itself.  A component is associated with
          its configuration node dynamically.
    + Thought experiment: boost/bury (x2).
    + How about... level of detail on a record list?

* Content structure.
    + A complex feature of the page component!
    + List insertion mechanism.
    + A layout question.  Into which column?  At the top, bottom, or don't care?
    + Elements are addressable.

* Support for renderer as plug-in.
    + Web
    + PHP (or any non-Java) renderer tier.
    + Mobile web

* Support for multiple client modes.
    + Bare web
    + AJAXifiable / Flashifiable.
    + Mobile web.
    + Mobile app.


Other
-----

Support for special characters in data.
Support for special characters in metadata.
