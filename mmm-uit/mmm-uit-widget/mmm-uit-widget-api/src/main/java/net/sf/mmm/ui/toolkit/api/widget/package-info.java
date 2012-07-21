/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains the API for widgets.
 * <a name="documentation"/><h2>UIT API Widget</h2>
 * This package and its sub-packages contain the API for {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget widgets} to
 * build a user interface. It abstracts from underlying native UI toolkits.<br/>
 * The main entry point is the {@link net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory} that allows to
 * {@link net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory#create(Class) create} arbitrary
 * {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget}s.<br/>
 * If you want to use this framework please consider also using <code>net.sf.mmm.ui.toolkit.api.element.UiElement</code>s
 * that builds a higher level of abstraction based on {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget}.
 * <h3>Why?</h3>
 * There are various features and advantages of this layer:
 * <ul>
 *   <li><b>Unit-testing</b><br/>Using a test implementation of this layer, you can easily test your dialogs and client logic.</li>
 *   <li><b>Web and native client</b><br/>Same client-code can run with all supported native toolkits</li>
 *   <li><b>Faster development</b><br/>GWT (Google Web Toolkit) is a great technology but development cycles are extremely slow
 *   as starting your client takes very long. This layer allows to test parts of your dialogs e.g. in Swing what in
 *   order to find initial bugs. After these have been fixed you can start testing with GWT in order to find web related
 *   problems.</li>
 *   <li><b>Clean API</b><br/>Simple, clear, well documented, and easy to use API.</li>
 *   <li><b>Consistent</b><br/>Hide strange and touchy behaviors of existing toolkits</li>
 * </ul>
 * <h3>Limitations</h3>
 * Please also note the following limitations before you make your choice:
 * <ul>
 *   <li><b>No gimmicks</b><br/>This approach is focusing on functionality and is limited to a common sense of features
 *   that are offered by all supported native UI toolkits. You will have a lot less flexibility if you are using this
 *   layer. If you want to offer a fancy or extraordinary UI you should consider using a single UI technology directly
 *   and stick to it. On the other hand you should also consider this as a feature as it helps you to build client
 *   applications using common UI patterns that have good usability and accessibility.</li>
 *   <li><b>No magic</b><br/>You should NOT expect that switching from a web-application to Swing or SWT/EclipseRCP can
 *   be done by a single line of code and the UI behaves and looks the same. However, this abstraction layer allows to
 *   switch from one toolkit to another or to support multiple native toolkits with a single codebase with reasonable
 *   effort.</li>
 * </ul>
 */
package net.sf.mmm.ui.toolkit.api.widget;

