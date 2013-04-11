/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.factory;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidget;

/**
 * This is the interface for a factory of a single {@link net.sf.mmm.client.ui.api.widget.UiWidget}.
 * 
 * @see net.sf.mmm.client.ui.api.UiContext
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> the generic type of the {@link UiWidget} that is {@link #create(UiContext) created} by this
 *        factory.
 */
public interface UiSingleWidgetFactory<WIDGET extends UiWidget> {

  /**
   * This method creates a new {@link UiWidget} instance of the particular {@link UiWidget}-type managed by
   * this factory.
   * 
   * @param context is the instance of {@link UiContext}.
   * @return the new {@link UiWidget}.
   */
  WIDGET create(UiContext context);

}
