/* $Id: AbstractUIComposite.java 957 2011-02-21 22:18:03Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiNode;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.AbstractUiElement;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.UiElement} interface using Swing as the
 * UI toolkit.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) children}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiComposite<E extends AbstractUiElement> extends AbstractUiElement
    implements UiComposite<E> {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiComposite(UIFactorySwing uiFactory) {

    super(uiFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    super.setEnabled(enabled);
    for (int i = 0; i < getChildCount(); i++) {
      getChild(i).setEnabled(enabled);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(UIRefreshEvent event) {

    super.refresh(event);
    // if (event.isOrientationModified()) {
    // setBorderJustification();
    // }
    int count = getChildCount();
    for (int componentIndex = 0; componentIndex < count; componentIndex++) {
      ((AbstractUiNode) getChild(componentIndex)).refresh(event);
    }
  }
}
