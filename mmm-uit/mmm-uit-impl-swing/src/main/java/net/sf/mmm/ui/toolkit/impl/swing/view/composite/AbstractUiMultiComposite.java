/* $Id: AbstractUIMultiComposite.java 957 2011-02-21 22:18:03Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite} that can contain
 * any number of components.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) children}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiMultiComposite<E extends UiElement> extends AbstractUiComposite<E> {

  /** the component list. */
  private final List<E> components;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>
   *        ).
   */
  public AbstractUiMultiComposite(UIFactorySwing uiFactory, UiNode parentObject) {

    super(uiFactory, parentObject);
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
   * @param component is the component to add.
   */
  public void doAddComponent(E component) {

    this.components.add(component);
  }

  /**
   * This method adds (inserts) the <code>component</code> at the given
   * <code>index</code>.
   * 
   * @param index is the position where to add the component.
   * @param component is the component to add.
   */
  public void doAddComponent(int index, E component) {

    this.components.add(index, component);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel#removeChild(int)
   * 
   * @param index is the position of the component to remove.
   * @return the removed component.
   */
  protected UiElement doRemoveComponent(int index) {

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

}
