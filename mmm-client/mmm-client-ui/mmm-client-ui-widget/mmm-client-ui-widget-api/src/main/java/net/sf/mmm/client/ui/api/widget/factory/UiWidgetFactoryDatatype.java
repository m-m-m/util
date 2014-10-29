/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.factory;

import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a factory to {@link #createForDatatype(Class) create widgets for particular
 * datatypes}. You will typically use {@link net.sf.mmm.client.ui.api.UiContext} that extends this interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface UiWidgetFactoryDatatype extends AbstractUiWidgetFactoryDatatype {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.client.ui.api.widget.UiWidgetFactoryDatatype";

  /**
   * This method creates a new instance of the {@link UiWidgetField field widget} to view and edit the given
   * <code>datatype</code>. <br>
   * <b>NOTE:</b><br>
   * Using this API is a key for creating a maintainable UI. Define {@link net.sf.mmm.util.lang.api.Datatype
   * custom datatypes} (e.g. <em>CustomerNumber</em>) and use them in your data-model to make it more
   * expressive. For each datatype you are using (including standard datatypes such as {@link String} as well
   * as complex datatypes that require a composition of multiple widgets) you can register a sub-factory for
   * each datatype and then consequently use this method in your presentation code to receive the proper
   * {@link UiWidgetField field widget}. The end-user will thank you for a consistent UI (where the
   * <em>CustomerNumber</em> is viewed and edited in exactly the same way in each dialog). Also you can stay
   * (a lot more) relaxed in case a change request comes up that <em>CustomerNumber</em> has to change its
   * format and validation rules in the entire application (with data-model and UI).
   * 
   * @param <VALUE> is the generic type of the {@link net.sf.mmm.util.lang.api.Datatype}.
   * @param datatype is the {@link net.sf.mmm.util.lang.api.Datatype}.
   * @return the new {@link UiWidgetField field widget} to display and edit the specified
   *         <code>datatype</code>.
   */
  @Override
  <VALUE> UiWidgetField<VALUE> createForDatatype(Class<VALUE> datatype);

  /**
   * This method determines if the given {@link Class} is supported by {@link #createForDatatype(Class)}.
   * 
   * @param type is the {@link Class} potentially reflecting a supported
   *        {@link net.sf.mmm.util.lang.api.Datatype}.
   * @return <code>true</code> if the given {@link Class} is supported, <code>false</code> otherwise.
   */
  boolean isDatatypeSupported(Class<?> type);

}
