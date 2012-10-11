/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetField;
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
   * This method gets the {@link UiConfiguration} for this factory. It is intended to be read by
   * {@link UiWidget} implementations to configure their look and feel.
   * 
   * @return the {@link UiConfiguration}.
   */
  UiConfiguration getConfiguration();

  /**
   * This method creates a new {@link UiWidget} instance of the given type (<code>widgetInterface</code>).<br/>
   * <b>IMPORTANT:</b><br/>
   * When creating a large (enterprise) application client you should NOT (directly) use this method to create
   * {@link UiWidgetField field widgets} such as
   * {@link net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetDateField} or
   * {@link net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetTextField}. Instead use
   * {@link #createForDatatype(Class)}.<br/>
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
   * This method creates a new instance of the {@link UiWidgetField field widget} to view and edit the given
   * <code>datatype</code>.<br/>
   * <b>NOTE:</b><br/>
   * Using this API is a key for creating a maintainable UI. Define {@link net.sf.mmm.util.lang.api.Datatype
   * custom datatypes} (e.g. <em>CustomerNumber</em>) and use them in your data-model to make it more
   * expressive. For each datatype you are using (including standard datatypes such as {@link String} as well
   * as complex datatypes that require a composition of multiple widgets) you can register a sub-factory for
   * each datatype and then consequently use this method in your presentation code to receive the proper
   * {@link UiWidgetField field widget}. The end-user will thank you for a consistent UI (where the
   * <em>CustomerNumber</em> is viewed and edited in exactly the same way in each dialog). Also you can stay
   * (a lot more) relaxed in case a change request comes up that <em>CustomerNumber</em> has to change its
   * format and validation rules in the entire data-model and UI.
   * 
   * @param <VALUE> is the generic type of the {@link net.sf.mmm.util.lang.api.Datatype}.
   * @param datatype is the {@link net.sf.mmm.util.lang.api.Datatype}.
   * @return the new {@link UiWidgetField field widget} to display and edit the specified
   *         <code>datatype</code>.
   */
  <VALUE> UiWidgetField<VALUE> createForDatatype(Class<VALUE> datatype);

  /**
   * This method gets the underlying native widget of the given {@link UiWidgetRegular regular widget}.
   * 
   * @param widget is the {@link UiWidgetRegular regular widget} to unwrap. It has to be an instance
   *        {@link #create(Class) created} by this factory.
   * @return the native widget.
   */
  NATIVE_WIDGET getNativeWidget(UiWidgetRegular widget);

}
