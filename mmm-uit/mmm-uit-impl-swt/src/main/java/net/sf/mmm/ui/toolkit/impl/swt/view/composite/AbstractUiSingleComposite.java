/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.composite;

import net.sf.mmm.ui.toolkit.api.view.composite.UiSingleComposite;
import net.sf.mmm.ui.toolkit.impl.swt.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.UiSwtNode;

/**
 * This is the abstract base implementation of {@link UiSingleComposite}.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiSingleComposite<E extends AbstractUiElement> extends
    AbstractUiComposite<E> implements UiSingleComposite<E> {

  /** @see #getChild() */
  private E child;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the {@link #getParent() parent} of this object (may
   *        be <code>null</code> ).
   */
  public AbstractUiSingleComposite(UiFactorySwt uiFactory, UiSwtNode parentObject) {

    super(uiFactory, parentObject);
  }

  /**
   * {@inheritDoc}
   */
  public E getChild() {

    return this.child;
  }

  /**
   * {@inheritDoc}
   */
  public E getChild(int index) throws IndexOutOfBoundsException {

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
  public void setChild(E child) {

    if (this.child == null) {
      setParent(child, null);
    }
    this.child = child;
  }

}
