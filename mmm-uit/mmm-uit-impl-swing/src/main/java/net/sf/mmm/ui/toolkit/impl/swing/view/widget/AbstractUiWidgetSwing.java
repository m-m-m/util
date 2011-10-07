/* $Id: AbstractUIWidget.java 967 2011-02-25 21:03:27Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import javax.swing.JComponent;

import net.sf.mmm.ui.toolkit.base.view.widget.AbstractUiWidget;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;

/**
 * This class is the implementation of
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiWidget} using Swing as the UI
 * toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type for the {@link #getAdapter() adapter}.
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetSwing<DELEGATE extends JComponent> extends
    AbstractUiWidget<DELEGATE> {

  /** @see #getAdapter() */
  private final UiWidgetAdapter<DELEGATE> adapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param delegate is the {@link UiWidgetAdapter#getDelegate() delegate}.
   */
  public AbstractUiWidgetSwing(UiFactorySwing uiFactory, DELEGATE delegate) {

    super(uiFactory);
    this.adapter = new UiWidgetAdapter<DELEGATE>(this, delegate);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetAdapter<DELEGATE> getAdapter() {

    return this.adapter;
  }

}
