/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite} that can contain any
 * number of components.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUIMultiComposite extends AbstractUIComposite {

  /** the component list. */
  private final List<UiElement> components;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   */
  public AbstractUIMultiComposite(UIFactorySwing uiFactory, UiNode parentObject) {

    super(uiFactory, parentObject);
    this.components = new ArrayList<UiElement>();
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
  public UiElement getChild(int index) {

    return this.components.get(index);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.view.composite.UiPanel#addChild(UiElement)
   * 
   * @param component is the component to add.
   */
  public void doAddComponent(UiElement component) {

    this.components.add(component);
  }

  /**
   * This method adds (inserts) the <code>component</code> at the given
   * <code>index</code>.
   * 
   * @param index is the position where to add the component.
   * @param component is the component to add.
   */
  public void doAddComponent(int index, UiElement component) {

    this.components.add(index, component);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.view.composite.UiPanel#removeChild(int)
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
   * @return the index of the given <code>component</code> or <code>-1</code>
   *         if NOT found.
   */
  protected int indexOfComponent(UiElement component) {

    return this.components.indexOf(component);
  }

}
