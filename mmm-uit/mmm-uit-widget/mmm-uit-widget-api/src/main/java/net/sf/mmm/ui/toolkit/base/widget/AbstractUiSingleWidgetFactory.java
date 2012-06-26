/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;

/**
 * This is the abstract base implementation of the {@link UiSingleWidgetFactory} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> the generic type of the {@link #getWidgetInterface() widget} to {@link #create() create}.
 */
public abstract class AbstractUiSingleWidgetFactory<WIDGET extends UiWidget<?>> implements
    UiSingleWidgetFactory<WIDGET> {

  /** @see #getWidgetInterface() */
  private final Class<WIDGET> widgetInterface;

  /**
   * The constructor.
   * 
   * @param widgetInterface is the {@link #getWidgetInterface() widget interface}.
   */
  public AbstractUiSingleWidgetFactory(Class<WIDGET> widgetInterface) {

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
