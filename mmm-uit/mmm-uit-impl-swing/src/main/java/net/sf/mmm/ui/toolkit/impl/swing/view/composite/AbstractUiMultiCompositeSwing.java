/* $Id: AbstractUIMultiComposite.java 957 2011-02-21 22:18:03Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import javax.swing.JComponent;

import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.base.view.composite.AbstractUiMultiComposite;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.UiElementAdapterSwing;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite} that can contain
 * any number of components.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getAdapter() delegate}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 */
public abstract class AbstractUiMultiCompositeSwing<DELEGATE extends JComponent, CHILD extends AbstractUiElement<? extends JComponent>>
    extends AbstractUiMultiComposite<DELEGATE, CHILD> {

  /** @see #getAdapter() */
  private final UiElementAdapterSwing<DELEGATE> adapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public AbstractUiMultiCompositeSwing(UiFactorySwing uiFactory, DELEGATE delegate) {

    super(uiFactory);
    this.adapter = new UiElementAdapterSwing<DELEGATE>(this, delegate);
  }

  /**
   * This method gets the {@link UiElementAdapterSwing
   * <DELEGATE,AbstractUiMultiCompositeSwing<DELEGATE>>}.
   * 
   * @return the {@link UiElementAdapterSwing
   *         <DELEGATE,AbstractUiMultiCompositeSwing<DELEGATE>>}.
   */
  @Override
  public UiElementAdapterSwing<DELEGATE> getAdapter() {

    return this.adapter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFactorySwing getFactory() {

    return (UiFactorySwing) super.getFactory();
  }

}
