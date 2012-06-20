/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.element;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory;

/**
 * This interface contains the configuration for the {@link net.sf.mmm.ui.toolkit.api.element.UiElement}s.
 * 
 * @param <WIDGET> is the type of the
 *        {@link UiWidgetFactory#getNativeWidget(net.sf.mmm.ui.toolkit.api.widget.UiAdapter) widgets}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiConfiguration<WIDGET> {

  /**
   * This method gets the {@link UiWidgetFactory}.
   * 
   * @return the {@link UiWidgetFactory}.
   */
  UiWidgetFactory<WIDGET> getAdapterFactory();

}
