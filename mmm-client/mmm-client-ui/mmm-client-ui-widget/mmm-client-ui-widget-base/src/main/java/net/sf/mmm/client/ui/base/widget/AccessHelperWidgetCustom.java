/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetReal;
import net.sf.mmm.client.ui.base.handler.event.ChangeEventSender;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom;

/**
 * This is an internal class to get access to the {@link #getDelegate() delegate} of a {@link UiWidgetCustom
 * custom widget}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
final class AccessHelperWidgetCustom extends UiWidgetCustom<Void, UiWidgetReal> {

  /**
   * Construction forbidden.
   */
  private AccessHelperWidgetCustom() {

    super(null, (UiWidgetReal) null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Void doGetValue() throws RuntimeException {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(Void value) {

    // nothing to do...
  }

  /**
   * Helper method to get access to the {@link #getDelegate() delegate} of a {@link UiWidgetCustom}.
   * 
   * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
   * 
   * @param customWidget is the custom widget.
   * @return the {@link #getDelegate() delegate} of the given widget.
   */
  static <DELEGATE extends UiWidget> DELEGATE getDelegateStatic(UiWidgetCustom<?, DELEGATE> customWidget) {

    return getDelegate(customWidget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ChangeEventSender<Void> createChangeEventSender() {

    return null;
  }

}
