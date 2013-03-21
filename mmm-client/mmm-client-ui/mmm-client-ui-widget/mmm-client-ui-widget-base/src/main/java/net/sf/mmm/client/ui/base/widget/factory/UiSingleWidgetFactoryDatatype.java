/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.factory;

import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.base.AbstractUiContext;

/**
 * This is the interface for a {@link UiSingleWidgetFactory} to create a {@link UiWidgetField field widget}
 * for a particular {@link #getDatatype() datatype}.
 * 
 * @param <VALUE> is the generic type of the {@link #getDatatype() datatype}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiSingleWidgetFactoryDatatype<VALUE> extends UiSingleWidgetFactory<UiWidgetField<VALUE>> {

  /**
   * @return the class reflecting the {@link net.sf.mmm.util.lang.api.Datatype}. May also be {@link String},
   *         {@link Long}, or any other common type. For custom types you should implement
   *         {@link net.sf.mmm.util.lang.api.Datatype}.
   */
  Class<VALUE> getDatatype();

  /**
   * {@inheritDoc}
   * 
   * @see net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryDatatype#createForDatatype(Class)
   */
  @Override
  UiWidgetField<VALUE> create(AbstractUiContext context);

}
