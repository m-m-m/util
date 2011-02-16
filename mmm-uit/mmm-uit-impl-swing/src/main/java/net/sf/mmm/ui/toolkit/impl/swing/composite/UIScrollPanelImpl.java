/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIScrollPanelImpl extends AbstractUIComposite implements UiScrollPanel {

  /** the scroll-panel or <code>null</code> if this is a regular panel */
  private final JScrollPane scrollPanel;

  /** the child component */
  private AbstractUIComponent childComponent;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   */
  public UIScrollPanelImpl(UIFactorySwing uiFactory, UiNode parentObject) {

    super(uiFactory, parentObject);
    this.scrollPanel = new JScrollPane();
    this.childComponent = null;
    initialize();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JComponent getSwingComponent() {

    return this.scrollPanel;
  }

  /**
   * {@inheritDoc}
   */
  public void setComponent(UiComposite child) {

    if (this.childComponent != null) {
      setParent(this.childComponent, null);
    }
    this.childComponent = (AbstractUIComponent) child;
    if (this.childComponent != null) {
      this.scrollPanel.setViewportView(this.childComponent.getSwingComponent());
    }
  }

  /**
   * {@inheritDoc}
   */
  public int getComponentCount() {

    return 1;
  }

  /**
   * {@inheritDoc}
   */
  public UiElement getComponent(int index) {

    if (index == 0) {
      return this.childComponent;
    }
    throw new ArrayIndexOutOfBoundsException(index);
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(UIRefreshEvent event) {

    super.refresh(event);
    this.scrollPanel.invalidate();
    this.scrollPanel.updateUI();
    this.scrollPanel.repaint();
  }

}
