/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetReal;

/**
 * This is the interface for a factory of a single {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget} of a
 * particular {@link #getWidgetInterface() type}.
 * 
 * @see net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> the generic type of the {@link #getWidgetInterface() widget} to
 *        {@link #create(AbstractUiWidgetFactory) create}.
 */
public interface UiSingleWidgetFactory<WIDGET extends UiWidgetReal> {

  /**
   * This method gets the interface of the widget interface handled by this factory.
   * 
   * @return the {@link UiWidgetReal} interface.
   */
  Class<WIDGET> getWidgetInterface();

  /**
   * This method creates a new {@link UiWidgetReal} instance of the {@link #getWidgetInterface() managed}
   * {@link UiWidgetReal}.
   * 
   * @see net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory#create(Class)
   * 
   * @param factory is the instance of the {@link AbstractUiWidgetFactory widget factory}.
   * @return the new {@link UiWidgetReal}.
   */
  WIDGET create(AbstractUiWidgetFactory<?> factory);

}
