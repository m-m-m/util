/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the abstract base implementation of {@link UiWidgetFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <NATIVE_WIDGET> is the generic top-level type of the underlying
 *        {@link #getNativeWidget(UiWidgetRegular) widgets}.
 */
public abstract class AbstractUiWidgetFactory<NATIVE_WIDGET> extends AbstractLoggableComponent implements
    UiWidgetFactory<NATIVE_WIDGET> {

  /**
   * The constructor.
   */
  public AbstractUiWidgetFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NATIVE_WIDGET getNativeWidget(UiWidgetRegular widget) {

    NlsNullPointerException.checkNotNull(UiWidgetRegular.class, widget);
    AbstractUiWidget<?> abstractWidget = (AbstractUiWidget<?>) widget;
    NATIVE_WIDGET nativeWidget = (NATIVE_WIDGET) abstractWidget.getWidgetAdapter().getWidget();
    return nativeWidget;
  }

}
