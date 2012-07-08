/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains the API for UI widgets.
 * <a name="documentation"/><h2>UIT API Widget</h2> 
 * This package and its sub-packages contain the API for {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget widgets} to
 * build a user interface. It abstracts from underlying native UI toolkits.<br/>
 * You should NOT expect that switching from a web-application to Swing or SWT/EclipseRCP can be done by a single line
 * of code and the UI behaves and looks the same. However, this abstraction layer allows to switch from one toolkit to
 * another or to support multiple native toolkits with a single codebase with reasonable effort.<br/>
 * You should be aware that this approach is focusing on functionality and is limited to a common sense of features
 * that are offered by all supported native UI toolkits. If you want to offer a fancy or extraordinary UI you should
 * consider using a single UI technology directly and stick to it.<br/>
 * The main entry point is the {@link net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory} that allows to 
 * {@link net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory#create(Class) create} arbitrary 
 * {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget}s.<br/>
 * If you are using this framework please consider using <code>net.sf.mmm.ui.toolkit.api.element.UiElement</code>s that
 * build a higher level of abstraction based on {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget}.
 */
package net.sf.mmm.ui.toolkit.api.widget;

