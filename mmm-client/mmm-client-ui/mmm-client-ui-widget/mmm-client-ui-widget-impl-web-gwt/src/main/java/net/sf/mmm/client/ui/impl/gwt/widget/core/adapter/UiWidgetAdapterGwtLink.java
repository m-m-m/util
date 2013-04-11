/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core.adapter;

import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterLink;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtClickable;

import com.google.gwt.user.client.ui.Anchor;

/**
 * This is the implementation of {@link UiWidgetAdapterLink} using GWT based on {@link Anchor}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtLink extends UiWidgetAdapterGwtClickable<Anchor> implements UiWidgetAdapterLink {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtLink() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Anchor createToplevelWidget() {

    return new Anchor();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    getToplevelWidget().setText(label);
  }

}
