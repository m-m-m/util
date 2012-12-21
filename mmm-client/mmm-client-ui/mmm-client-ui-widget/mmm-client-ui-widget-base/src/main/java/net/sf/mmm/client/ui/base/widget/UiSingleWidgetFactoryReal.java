/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.widget.UiWidgetReal;
import net.sf.mmm.client.ui.base.AbstractUiContext;

/**
 * This is the interface for a factory of a single {@link net.sf.mmm.client.ui.api.widget.UiWidget} of a
 * particular {@link #getWidgetInterface() type}.
 * 
 * @see net.sf.mmm.client.ui.api.UiContext
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> the generic type of the {@link #getWidgetInterface() widget} to
 *        {@link #create(AbstractUiContext) create}. Should typically be a
 *        {@link net.sf.mmm.client.ui.api.widget.UiWidgetReal}.
 */
public interface UiSingleWidgetFactoryReal<WIDGET extends UiWidgetReal> extends UiSingleWidgetFactory<WIDGET> {

  /**
   * This method gets the interface of the widget interface handled by this factory.
   * 
   * @return the {@link net.sf.mmm.client.ui.api.widget.UiWidget} interface.
   */
  Class<WIDGET> getWidgetInterface();

  /**
   * {@inheritDoc}
   * 
   * @see net.sf.mmm.client.ui.api.widget.UiWidgetFactory#create(Class)
   */
  @Override
  WIDGET create(AbstractUiContext context);

}
