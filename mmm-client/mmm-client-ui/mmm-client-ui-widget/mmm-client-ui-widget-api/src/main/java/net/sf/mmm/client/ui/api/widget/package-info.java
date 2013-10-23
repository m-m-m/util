/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains the API for widgets.
 * <a name="documentation"/><h2>Client UI API Widget</h2>
 * This package and its sub-packages contain the API for {@link net.sf.mmm.client.ui.api.widget.UiWidget widgets} to
 * build a user interface (UI). It abstracts from underlying native UI toolkits (such as Swing, GWT, etc.).<br/>
 * The main entry point is the {@link net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative} that allows to
 * {@link net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative#create(Class) create} arbitrary
 * {@link net.sf.mmm.client.ui.api.widget.UiWidget}s. Use {@link net.sf.mmm.client.ui.api.UiContext}
 * to get access.<br/>
 * <img src="{@docRoot}/doc-files/widgets.png"/>
 * Please consult {@link net.sf.mmm.client.ui.api Client UI API} for an introduction.
 */
package net.sf.mmm.client.ui.api.widget;

