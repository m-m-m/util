/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIComposite} that can contain any
 * number of components.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIMultiComposite extends AbstractUIComposite {

  /** the component list. */
  private final List<UIComponent> components;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public AbstractUIMultiComposite(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.components = new ArrayList<UIComponent>();
  }

  /**
   * {@inheritDoc}
   */
  public int getComponentCount() {

    return this.components.size();
  }

  /**
   * {@inheritDoc}
   */
  public UIComponent getComponent(int index) {

    return this.components.get(index);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIPanel#addComponent(UIComponent)
   * 
   * @param component
   *        is the component to add.
   */
  public void doAddComponent(UIComponent component) {

    this.components.add(component);
  }

  /**
   * This method adds (inserts) the <code>component</code> at the given
   * <code>index</code>.
   * 
   * @param index
   *        is the position where to add the component.
   * @param component
   *        is the component to add.
   */
  public void doAddComponent(int index, UIComponent component) {

    this.components.add(index, component);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIPanel#removeComponent(int)
   * 
   * @param index
   *        is the position of the component to remove.
   * @return the removed component.
   */
  protected UIComponent doRemoveComponent(int index) {

    return this.components.remove(index);
  }

  /**
   * @see List#indexOf(Object)
   * 
   * @param component
   *        is the component to lookup.
   * @return the index of the given <code>component</code> or <code>-1</code>
   *         if NOT found.
   */
  protected int indexOfComponent(UIComponent component) {

    return this.components.indexOf(component);
  }

}
