/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.gwt.view.window;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.window.UiWindow;
import net.sf.mmm.ui.toolkit.base.gwt.AbstractUiFactoryGwt;
import net.sf.mmm.ui.toolkit.base.view.window.AbstractUiWindow;

/**
 * This is the abstract base implementation of {@link UiWindow} using GWT as
 * underlying UI technology.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWindowGwt extends AbstractUiWindow {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param parentObject is the {@link #getParent() parent} of this object. It
   *        may be <code>null</code>.
   */
  public AbstractUiWindowGwt(AbstractUiFactoryGwt uiFactory, UiWindow parentObject) {

    super(uiFactory);
  }

  /**
   * {@inheritDoc}
   */
  public void pack() {

    // do nothing by default...
  }

  /**
   * {@inheritDoc}
   */
  public void setComposite(UiComposite<? extends UiElement> newComposite) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDisposed() {

    return false;
  }

}
