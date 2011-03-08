/* $Id: AbstractUIPanel.java 962 2011-02-24 23:15:27Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.composite;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.api.view.composite.UiMultiComposite;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.AbstractUiElement;

/**
 * This class is an abstract base implementation of the {@link UiMultiComposite}
 * interface using SWT as the UI toolkit. It is used for composites that have a
 * list of multiple child-components.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiMultiComposite<E extends AbstractUiElement> extends
    AbstractUiComposite<E> implements UiMultiComposite<E> {

  /** @see #getChild(int) */
  private final List<E> children;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiMultiComposite(UiFactorySwt uiFactory) {

    super(uiFactory);
    this.children = new ArrayList<E>();
  }

  /**
   * {@inheritDoc}
   */
  public int getChildCount() {

    return this.children.size();
  }

  /**
   * {@inheritDoc}
   */
  public E getChild(int index) {

    return this.children.get(index);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractUiElement removeChild(int index) {

    AbstractUiElement component = this.children.remove(index);
    component.setParent(null);
    return component;
  }

  /**
   * {@inheritDoc}
   */
  public boolean removeChild(AbstractUiElement component) {

    // return this.components.remove(component);
    int index = this.children.indexOf(component);
    if (index >= 0) {
      removeChild(index);
      return true;
    } else {
      return false;
    }
  }

  /**
   * This method adds the given <code>child</code> to this composite.
   * 
   * @param child is the child element to add.
   */
  protected void doAddChild(E child) {

    this.children.add(child);
    child.setParent(this);
  }

  /**
   * This method inserts the given <code>child</code> at the given
   * <code>index</code> to this composite.
   * 
   * @param child is the child element to add.
   * @param index is the {@link #getChild(int) child-index} where to insert the
   *        <code>child</code>.
   */
  protected void doInsertChild(E child, int index) {

    this.children.add(index, child);
    child.setParent(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setParent(UiNode newParent) {

    super.setParent(newParent);
  }

}
