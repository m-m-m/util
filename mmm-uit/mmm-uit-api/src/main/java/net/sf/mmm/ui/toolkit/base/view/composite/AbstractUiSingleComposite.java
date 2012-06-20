/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view.composite;

import net.sf.mmm.ui.toolkit.api.view.composite.UiSingleComposite;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;

/**
 * This is the abstract base implementation of a {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite}
 * that has exactly one child (that may be <code>null</code>).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getAdapter() delegate}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 */
public abstract class AbstractUiSingleComposite<DELEGATE, CHILD extends AbstractUiElement<?>> extends
    AbstractUiComposite<DELEGATE, CHILD> implements UiSingleComposite<CHILD> {

  /** @see #getChild() */
  private CHILD child;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiSingleComposite(AbstractUiFactory uiFactory) {

    super(uiFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getChildCount() {

    if (this.child == null) {
      return 0;
    } else {
      return 1;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CHILD getChild() {

    return this.child;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CHILD getChild(int index) {

    if (index == 0) {
      if (this.child != null) {
        return this.child;
      }
    }
    throw new IndexOutOfBoundsException(Integer.toString(index));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChild(CHILD child) {

    if (this.child != null) {
      this.child.setParent(null);
    }
    this.child = child;
    if (this.child != null) {
      this.child.setParent(this);
    }
  }

}
