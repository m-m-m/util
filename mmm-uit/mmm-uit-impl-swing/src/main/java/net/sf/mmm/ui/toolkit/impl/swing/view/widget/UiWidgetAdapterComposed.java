/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import javax.swing.JComponent;

import net.sf.mmm.ui.toolkit.base.view.widget.AbstractUiWidget;

/**
 * This is the implementation of
 * {@link net.sf.mmm.ui.toolkit.base.view.UiElementAdapter} for a swing widget
 * using a separate {@link #getToplevelDelegate() top-level delegate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * @param <TOP> is the generic type of the {@link #getToplevelDelegate()
 *        top-level delegate}.
 * @since 1.0.0
 */
public class UiWidgetAdapterComposed<DELEGATE extends JComponent, TOP extends JComponent> extends
    UiWidgetAdapter<DELEGATE> {

  /** @see #getToplevelDelegate() */
  private final TOP toplevelDelegate;

  /**
   * The constructor.
   * 
   * @param node is the owning {@link #getNode() node}.
   * @param delegate is the {@link #getDelegate() delegate}.
   * @param toplevelDelegate is the {@link #getToplevelDelegate() top-level
   *        delegate}.
   */
  public UiWidgetAdapterComposed(AbstractUiWidget<DELEGATE> node, DELEGATE delegate,
      TOP toplevelDelegate) {

    super(node, delegate);
    this.toplevelDelegate = toplevelDelegate;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TOP getToplevelDelegate() {

    return this.toplevelDelegate;
  }

}
