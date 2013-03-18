/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.factory;

import net.sf.mmm.client.ui.api.widget.UiWidgetReal;

/**
 * This is the abstract base implementation of the {@link UiSingleWidgetFactory} interface.
 * 
 * @param <WIDGET> the generic type of the {@link #getWidgetInterface() widget} to
 *        {@link #create(net.sf.mmm.client.ui.base.AbstractUiContext) create}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiSingleWidgetFactoryReal<WIDGET extends UiWidgetReal> implements
    UiSingleWidgetFactoryReal<WIDGET> {

  /** @see #getWidgetInterface() */
  private final Class<WIDGET> widgetInterface;

  /**
   * The constructor.
   * 
   * @param widgetInterface is the {@link #getWidgetInterface() widget interface}.
   */
  public AbstractUiSingleWidgetFactoryReal(Class<WIDGET> widgetInterface) {

    super();
    this.widgetInterface = widgetInterface;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<WIDGET> getWidgetInterface() {

    return this.widgetInterface;
  }

}
