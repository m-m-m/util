/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import javax.swing.JComponent;

import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.base.view.composite.AbstractUiSingleComposite;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.UiElementAdapterSwing;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite} that has exactly
 * one child (that may be <code>null</code>).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getAdapter() delegate}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 */
public abstract class AbstractUiSingleCompositeSwing<DELEGATE extends JComponent, CHILD extends AbstractUiElement<? extends JComponent>>
    extends AbstractUiSingleComposite<DELEGATE, CHILD> {

  /** @see #getAdapter() */
  private final UiElementAdapterSwing<DELEGATE> adapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public AbstractUiSingleCompositeSwing(UiFactorySwing uiFactory, DELEGATE delegate) {

    super(uiFactory);
    this.adapter = new UiElementAdapterSwing<DELEGATE>(this, delegate);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiElementAdapterSwing<DELEGATE> getAdapter() {

    return this.adapter;
  }

}
