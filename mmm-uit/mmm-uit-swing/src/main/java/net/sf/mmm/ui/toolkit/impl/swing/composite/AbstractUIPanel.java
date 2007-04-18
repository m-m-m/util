/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.UIPanel;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is an abstract base implementation of the {@link UIPanel}
 * interface using swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIPanel extends AbstractUIMultiComposite implements UIPanel {

  /**
   * The constructor. 
   * 
   * @see AbstractUIMultiComposite#AbstractUIMultiComposite(UIFactorySwing,
   *      UINode)
   */
  public AbstractUIPanel(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractUIComponent removeComponent(int index) {

    AbstractUIComponent component = (AbstractUIComponent) this.components.remove(index);
    component.setParent(null);
    return component;
  }

  /**
   * {@inheritDoc}
   */
  public boolean removeComponent(UIComponent component) {

    // return this.components.remove(component);
    int index = this.components.indexOf(component);
    if (index >= 0) {
      removeComponent(index);
      return true;
    } else {
      return false;
    }
  }

}
