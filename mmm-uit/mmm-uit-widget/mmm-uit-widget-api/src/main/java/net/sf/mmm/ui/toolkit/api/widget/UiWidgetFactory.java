/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.widget.window.UiWidgetMainWindow;

/**
 * This is the interface for a factory used to #create create {@link UiWidget}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <NATIVE_WIDGET> is the generic top-level type of the underlying
 *        {@link #getNativeWidget(UiWidgetRegular) widgets}.
 */
public interface UiWidgetFactory<NATIVE_WIDGET> {

  /**
   * This method gets the {@link UiDisplay} e.g. to read the current screen resolution.
   * 
   * @return the {@link UiDisplay}.
   */
  UiDisplay getDisplay();

  /**
   * @return the {@link UiDispatcher}.
   */
  UiDispatcher getDispatcher();

  /**
   * This method gets the application {@link UiWidgetMainWindow main window}. In case of a web application
   * this will be the browser window (respective the current tab). In case of a MDI (multiple document
   * interface) client this is the master window. Otherwise it will be a new window that should be
   * {@link net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteVisible#setVisible(boolean) opened} initially.
   * 
   * @return the {@link UiWidgetMainWindow main window}.
   */
  UiWidgetMainWindow getMainWindow();

  /**
   * This method creates a new {@link UiWidget} instance of the given type (<code>widgetInterface</code>).<br/>
   * <b>ATTENTION:</b><br/>
   * The API of the {@link UiWidget}s defines base interfaces marks as <code>abstract</code>. These interfaces
   * cannot be used as argument to this method. Further some widget interfaces are NOT abstract but will not
   * be created via this method but from some other widget. To avoid confusion the widgets that can be created
   * here are marked via the interface {@link UiWidgetReal}. All these interfaces except for
   * {@link UiWidgetReal} itself can be created and should be supported as legal arguments to this method.
   * 
   * @param <WIDGET> is the generic type of the {@link UiWidget} to create.
   * @param widgetInterface is the interface reflecting the {@link UiWidget} to create. Must NOT be
   *        <code>abstract</code>.
   * @return the new {@link UiWidget}.
   */
  <WIDGET extends UiWidgetReal> WIDGET create(Class<WIDGET> widgetInterface);

  /**
   * This method gets the underlying native widget of the given {@link UiWidgetRegular regular widget}.
   * 
   * @param widget is the {@link UiWidgetRegular regular widget} to unwrap. It has to be an instance
   *        {@link #create(Class) created} by this factory.
   * @return the native widget.
   */
  NATIVE_WIDGET getNativeWidget(UiWidgetRegular widget);

}
