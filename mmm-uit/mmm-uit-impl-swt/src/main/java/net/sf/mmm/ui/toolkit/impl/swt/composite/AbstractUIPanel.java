/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.api.view.composite.UiPanel;
import net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;

/**
 * This class is an abstract base implementation of the {@link UiPanel}
 * interface using SWT as the UI toolkit. It is used for composites that have a
 * list of multiple child-components.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUIPanel extends AbstractUIComposite implements UiPanel {

  /** the component list */
  protected final List<AbstractUIComponent> components;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   * @param borderTitle is the title of the border or <code>null</code> for NO
   *        border.
   */
  public AbstractUIPanel(UIFactorySwt uiFactory, UISwtNode parentObject, String borderTitle) {

    super(uiFactory, parentObject, borderTitle);
    this.components = new ArrayList<AbstractUIComponent>();
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
  @Override
  public AbstractUIComponent getChild(int index) {

    return this.components.get(index);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractUIComponent removeChild(int index) {

    AbstractUIComponent component = this.components.remove(index);
    component.setParent(null);
    return component;
  }

  /**
   * {@inheritDoc}
   */
  public boolean removeChild(UiElement component) {

    // return this.components.remove(component);
    int index = this.components.indexOf(component);
    if (index >= 0) {
      removeChild(index);
      return true;
    } else {
      return false;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setParent(UiNode newParent) {

    super.setParent(newParent);
  }

}
