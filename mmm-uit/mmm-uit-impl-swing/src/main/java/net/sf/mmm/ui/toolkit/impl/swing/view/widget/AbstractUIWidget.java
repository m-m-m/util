/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.widget.UiWidget;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiWidget} interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUIWidget extends AbstractUiElement implements UiWidget {

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   */
  public AbstractUIWidget(UIFactorySwing uiFactory, UiNode parentObject) {

    super(uiFactory, parentObject);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isWidget() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiComposite getParent() {

    return (UiComposite) super.getParent();
  }

}
