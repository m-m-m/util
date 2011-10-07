/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.composite;

import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.base.view.composite.AbstractUiSingleComposite;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

/**
 * This is the abstract base implementation of
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiSingleComposite} using SWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getAdapter() delegate}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 */
public abstract class AbstractUiSingleCompositeSwt<DELEGATE extends Composite, CHILD extends AbstractUiElement<? extends Widget>>
    extends AbstractUiSingleComposite<DELEGATE, CHILD> {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiSingleCompositeSwt(UiFactorySwt uiFactory) {

    super(uiFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFactorySwt getFactory() {

    return (UiFactorySwt) super.getFactory();
  }

}
