/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the abstract base implementation of {@link UiWidgetFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <NATIVE_WIDGET> is the generic top-level type of the underlying {@link #getNativeWidget(UiWidget)
 *        widgets}.
 */
public abstract class AbstractUiWidgetFactory<NATIVE_WIDGET> extends AbstractLoggableComponent implements
    UiWidgetFactory<NATIVE_WIDGET> {

  /**
   * The constructor.
   */
  public AbstractUiWidgetFactory() {

    super();
  }

}
