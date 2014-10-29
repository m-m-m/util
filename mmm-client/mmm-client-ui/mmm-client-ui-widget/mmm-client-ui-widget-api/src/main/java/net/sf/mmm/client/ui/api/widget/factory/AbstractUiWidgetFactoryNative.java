/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.factory;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow;

/**
 * This is the abstract interface for a factory used to {@link #create(Class) create}
 * {@link net.sf.mmm.client.ui.api.widget.UiWidget}s. You will typically use
 * {@link net.sf.mmm.client.ui.api.widget.UiWidgetFactory} that extends this interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AbstractUiWidgetFactoryNative {

  /**
   * This method gets the application {@link UiWidgetMainWindow main window}. In case of a web application
   * this will be the browser window (respective the current tab). In case of a MDI (multiple document
   * interface) client this is the master window. Otherwise it will be a new window that should be
   * {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteVisible#setVisible(boolean) opened} initially.
   * 
   * @return the {@link UiWidgetMainWindow main window}.
   */
  UiWidgetMainWindow getMainWindow();

  /**
   * This method creates a new {@link net.sf.mmm.client.ui.api.widget.UiWidget} instance of the given type (
   * <code>widgetInterface</code>). <br>
   * <b>IMPORTANT:</b><br>
   * When creating a large (enterprise) application client you should NOT (directly) use this method to create
   * {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetField field widgets} such as
   * {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetLocalDateField} or
   * {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField}. Instead use
   * {@link UiWidgetFactoryDatatype#createForDatatype(Class)}. <br>
   * <b>ATTENTION:</b><br>
   * The {@link net.sf.mmm.util.component.api.Api API} of {@link net.sf.mmm.client.ui.api.widget.UiWidget}
   * defines base interfaces marks as <code>abstract</code>. These interfaces cannot be used as argument to
   * this method. Further some widget interfaces are NOT abstract but will not be created via this method but
   * from some other widget. To avoid confusion the widgets that can be created here are marked via the
   * interface {@link UiWidgetNative}. All these interfaces except for {@link UiWidgetNative} itself can be
   * created and should be supported as legal arguments to this method.
   * 
   * @param <WIDGET> is the generic type of the {@link net.sf.mmm.client.ui.api.widget.UiWidget} to create.
   * @param widgetInterface is the interface reflecting the {@link net.sf.mmm.client.ui.api.widget.UiWidget}
   *        to create. Must NOT be <code>abstract</code>.
   * @return the new {@link net.sf.mmm.client.ui.api.widget.UiWidget}.
   */
  <WIDGET extends UiWidgetNative> WIDGET create(Class<WIDGET> widgetInterface);

  /**
   * This method gets the underlying native widget of the given {@link UiWidgetRegular regular widget}.
   * 
   * @param widget is the {@link UiWidgetRegular regular widget} to unwrap. It has to be an instance
   *        {@link #create(Class) created} by this factory.
   * @return the native widget.
   */
  Object getNativeWidget(UiWidgetRegular widget);

}
