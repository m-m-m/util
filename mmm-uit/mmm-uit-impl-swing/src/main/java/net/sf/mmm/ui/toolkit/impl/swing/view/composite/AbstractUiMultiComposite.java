/* $Id: AbstractUIMultiComposite.java 957 2011-02-21 22:18:03Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiMultiComposite;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.AbstractUiElement;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite} that can contain
 * any number of components.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) children}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiMultiComposite<E extends AbstractUiElement> extends
    AbstractUiComposite<E> implements UiMultiComposite<E> {

  /** the component list. */
  private final List<E> components;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiMultiComposite(UIFactorySwing uiFactory) {

    super(uiFactory);
    this.components = new ArrayList<E>();
  }

  /**
   * {@inheritDoc}
   */
  public int getChildCount() {

    return this.components.size();
  }

  /**
   * {@inheritDoc}
   */
  public E getChild(int index) {

    return this.components.get(index);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel#addChild(UiElement)
   * 
   * @param child is the component to add.
   */
  protected void doAddChild(E child) {

    this.components.add(child);
    child.setParent(this);
  }

  /**
   * This method adds (inserts) the <code>child</code> at the given
   * <code>index</code>.
   * 
   * @param child is the child element to add.
   * @param index is the position where to insert the child.
   */
  protected void doInsertChild(E child, int index) {

    this.components.add(index, child);
    child.setParent(this);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel#removeChild(int)
   * 
   * @param index is the position of the component to remove.
   * @return the removed component.
   */
  protected E doRemoveChild(int index) {

    return this.components.remove(index);
  }

  /**
   * @see List#indexOf(Object)
   * 
   * @param component is the component to lookup.
   * @return the index of the given <code>component</code> or <code>-1</code> if
   *         NOT found.
   */
  protected int indexOfComponent(UiElement component) {

    return this.components.indexOf(component);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractUiElement removeChild(int index) {

    AbstractUiElement component = doRemoveChild(index);
    component.removeFromParent();
    return component;
  }

  /**
   * {@inheritDoc}
   */
  public boolean removeChild(E component) {

    // return this.components.remove(component);
    int index = indexOfComponent(component);
    if (index >= 0) {
      removeChild(index);
      return true;
    } else {
      return false;
    }
  }

}
