/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.element;

import net.sf.mmm.ui.toolkit.api.adapter.UiAdapterFactory;

/**
 * This interface contains the configuration for the {@link net.sf.mmm.ui.toolkit.api.element.UiElement}s.
 * 
 * @param <WIDGET> is the type of the
 *        {@link UiAdapterFactory#getWidget(net.sf.mmm.ui.toolkit.api.adapter.UiAdapter) widgets}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiConfiguration<WIDGET> {

  /**
   * This method gets the {@link UiAdapterFactory}.
   * 
   * @return the {@link UiAdapterFactory}.
   */
  UiAdapterFactory<WIDGET> getAdapterFactory();

}
