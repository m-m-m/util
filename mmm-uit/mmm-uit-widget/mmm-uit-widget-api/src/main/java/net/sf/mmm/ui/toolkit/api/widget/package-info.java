/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains the API for widgets.
 * <a name="documentation"/><h2>UIT API Widget</h2>
 * This package and its sub-packages contain the API for {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget widgets} to
 * build a user interface. It abstracts from underlying native UI toolkits and allows to build a portable
 * cross-technology client application.<br/>
 * The main entry point is the {@link net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory} that allows to
 * {@link net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory#create(Class) create} arbitrary
 * {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget}s.<br/>
 * <h3>Why?</h3>
 * There are various features and advantages of this layer:
 * <ul>
 *   <li><b>Unit-testing</b><br/>Using a test implementation of this layer, you can easily test your dialogs and client logic.</li>
 *   <li><b>Web and native client</b><br/>Same client-code can run with all supported native toolkits.</li>
 *   <li><b>Faster development</b><br/>GWT (Google Web Toolkit) is a great technology but development cycles are extremely slow
 *   as starting your client takes very long. This layer allows to test parts of your dialogs e.g. in Swing in
 *   order to find initial bugs. After these have been fixed you can start testing with GWT in order to find web related
 *   problems. This approach can save a lot of time.</li>
 *   <li><b>Clean API</b><br/>Simple, clear, well documented, and easy to use API. Native UI toolkits typically use
 *   java classes as API. Due to lack of multi-inheritance and due to implementation specific decisions the API often
 *   sucks. There are many inherited API methods in particular widgets that do NOT make sense there and will confuse
 *   the user. This API is entirely designed using interfaces. Every individual attribute or feature is defined by a
 *   central interface. This approach allows that each method is defined only in one place and documentation gets
 *   consistent and maintainable. Finally, we have analyzed various native UI toolkits with their API and internals.
 *   From this perspective and with the experience of building various large enterprise application clients this
 *   API has been designed. Please have a look.</li>
 *   <li><b>Consistent</b><br/>Hide strange and touchy behaviors of existing toolkits.</li>
 *   <li><b>Extensibility</b><br/>Even though we define widgets by interfaces and have support for multiple native UI
 *   toolkits, you can extend this code. Either with UI toolkit independent widgets by composing existing ones
 *   or by replacing or adding widgets for native toolkit support. We hope that the latter case will never be necessary
 *   or that you join this project in this case.</li>
 *   <li><b>Higher Level</b><br/>While the adoption of native UI toolkits takes place on a quite low level, there
 *   are many higher level features that are build on by composing lower level widgets. Examples are the integration of
 *   validation, dirty handling, switching a form between view (read-only) and edit mode, complex lists and trees,
 *   two-panel-selectors, two-list-selection, and much more. As implied above we also recommend you to do the same in
 *   order to build the widgets for your custom needs (e.g. the <code>UiWidgetMyCustomerNumberField</code> or the
 *   <code>UiWidgetMyCustomerForm</code>). You are free to pick and choose: If you only want to use the lower level
 *   abstraction you can still ignore the higher level stuff.</li>
 * </ul>
 * <h3>Limitations</h3>
 * Please also note the following limitations before you make your choice:
 * <ul>
 *   <li><b>No gimmicks</b><br/>This approach is focusing on functionality and is limited to a common sense of features
 *   that are offered by all supported native UI toolkits. You will have a less flexibility if you are using this layer.
 *   If you want to offer a very fancy or extraordinary UI you should consider using a single UI technology directly
 *   and stick to it. On the other hand you should also consider this as a feature as it helps you to build client
 *   applications using common UI patterns that have good usability and accessibility.</li>
 *   <li><b>No magic</b><br/>You should NOT expect that switching from a web-application to Swing or SWT/EclipseRCP can
 *   be done by a single line of code and the UI behaves and looks the same. However, this abstraction layer allows to
 *   switch from one toolkit to another or to support multiple native toolkits with a single codebase with reasonable
 *   effort.</li>
 * </ul>
 */
package net.sf.mmm.ui.toolkit.api.widget;

