/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.UINodeRenamed;
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
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   */
  public AbstractUIPanel(UIFactorySwing uiFactory, UINodeRenamed parentObject) {

    super(uiFactory, parentObject);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractUIComponent removeComponent(int index) {

    AbstractUIComponent component = (AbstractUIComponent) doRemoveComponent(index);
    component.removeFromParent();
    return component;
  }

  /**
   * {@inheritDoc}
   */
  public boolean removeComponent(UiElement component) {

    // return this.components.remove(component);
    int index = indexOfComponent(component);
    if (index >= 0) {
      removeComponent(index);
      return true;
    } else {
      return false;
    }
  }

}
