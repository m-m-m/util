/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view.composite;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiMultiComposite;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;

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
public abstract class AbstractUiMultiComposite<DELEGATE, CHILD extends AbstractUiElement<?>>
    extends AbstractUiComposite<DELEGATE, CHILD> implements UiMultiComposite<CHILD> {

  /** the component list. */
  private final List<CHILD> components;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiMultiComposite(AbstractUiFactory uiFactory) {

    super(uiFactory);
    this.components = new ArrayList<CHILD>();
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
  public CHILD getChild(int index) {

    return this.components.get(index);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel#addChild(UiElement)
   * 
   * @param child is the component to add.
   */
  protected void doAddChild(CHILD child) {

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
  protected void doInsertChild(CHILD child, int index) {

    this.components.add(index, child);
    child.setParent(this);
  }

  /**
   * This method sets the <code>child</code> at the given <code>index</code>. If
   * the given <code>index</code> is greater or equal to
   * {@link #getChildCount()}, according child entries will be allocated that
   * are initialized with <code>null</code>.
   * 
   * @param child is the child element to add.
   * @param index is the position where to insert the child.
   */
  protected void doSetChild(CHILD child, int index) {

    for (int i = (this.components.size() - index); i >= 0; i--) {
      this.components.add(null);
    }
    this.components.set(index, child);
    if (child != null) {
      child.setParent(this);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel#removeChild(int)
   * 
   * @param index is the position of the component to remove.
   * @return the removed component.
   */
  protected CHILD doRemoveChild(int index) {

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
  public CHILD removeChild(int index) {

    CHILD component = doRemoveChild(index);
    component.setParent(null);
    return component;
  }

  /**
   * {@inheritDoc}
   */
  public boolean removeChild(CHILD component) {

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
