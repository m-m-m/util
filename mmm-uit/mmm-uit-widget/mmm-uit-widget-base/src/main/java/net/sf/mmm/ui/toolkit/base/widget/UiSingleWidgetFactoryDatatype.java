/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetField;

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
   * @see net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory#createForDatatype(Class)
   */
  @Override
  public UiWidgetField<VALUE> create(AbstractUiWidgetFactory<?> factory);

}
