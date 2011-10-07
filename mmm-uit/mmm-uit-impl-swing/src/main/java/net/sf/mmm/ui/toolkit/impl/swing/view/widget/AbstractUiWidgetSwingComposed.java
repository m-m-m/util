/* $Id$
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
 * @param <DELEGATE> is the generic type for the {@link #getAdapter() delegate}.
 * @param <TOP> is the generic type of the
 *        {@link UiWidgetAdapterComposed#getToplevelDelegate() top-level
 *        delegate}.
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetSwingComposed<DELEGATE extends JComponent, TOP extends JComponent>
    extends AbstractUiWidget<DELEGATE> {

  /** @see #getAdapter() */
  private final UiWidgetAdapterComposed<DELEGATE, TOP> adapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param delegate is the {@link UiWidgetAdapter#getDelegate() delegate}.
   * @param toplevelDelegate is the
   *        {@link UiWidgetAdapter#getToplevelDelegate() top-level delegate}.
   */
  public AbstractUiWidgetSwingComposed(UiFactorySwing uiFactory, DELEGATE delegate,
      TOP toplevelDelegate) {

    super(uiFactory);
    this.adapter = new UiWidgetAdapterComposed<DELEGATE, TOP>(this, delegate, toplevelDelegate);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetAdapterComposed<DELEGATE, TOP> getAdapter() {

    return this.adapter;
  }

}
