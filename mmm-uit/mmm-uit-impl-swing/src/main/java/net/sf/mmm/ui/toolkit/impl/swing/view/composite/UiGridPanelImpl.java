/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.sf.mmm.ui.toolkit.api.view.composite.UiGridPanel;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;

/**
 * This is the implementation of {@link UiGridPanel} using Swing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiGridPanelImpl
    extends
    AbstractUiMultiCompositeSwing<JPanel, UiGridRowImpl<? extends AbstractUiElement<? extends JComponent>>>
    implements UiGridPanel<UiGridRowImpl<? extends AbstractUiElement<? extends JComponent>>>,
    UiGridLayouter {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UiGridPanelImpl(UiFactorySwing uiFactory) {

    super(uiFactory, new JPanel());
    getDelegate().setLayout(new GridLayoutManager(this));
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
  public void addChild(UiGridRowImpl<? extends AbstractUiElement<? extends JComponent>> child) {

    doAddChild(child);
    getDelegate().add(child.getAdapter().getDelegate());
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(UiGridRowImpl<? extends AbstractUiElement<? extends JComponent>> child,
      int index) {

    doInsertChild(child, index);
    getDelegate().add(child.getAdapter().getDelegate(), index);
  }

  /**
   * {@inheritDoc}
   */
  public UiGridRowImpl<? extends AbstractUiElement<? extends JComponent>> createRow() {

    UiGridRowImpl<AbstractUiElement<? extends JComponent>> gridRow = new UiGridRowImpl<AbstractUiElement<? extends JComponent>>(
        getFactory(), new JPanel());
    return gridRow;
  }

  /**
   * {@inheritDoc}
   */
  public void calculateLayout(GridLayoutInfo layoutInfo) {

    // TODO: concurrency...
    layoutInfo.reset();
    for (int i = 0; i < this.getChildCount(); i++) {
      UiGridRowImpl<? extends AbstractUiElement<? extends JComponent>> child = getChild(i);
      if (child.isVisible()) {
        child.calculateLayout(layoutInfo);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void applyLayout(GridLayoutInfo layoutInfo) {

    for (int i = 0; i < this.getChildCount(); i++) {
      UiGridRowImpl<? extends AbstractUiElement<? extends JComponent>> child = getChild(i);
      if (child.isVisible()) {
        child.applyLayout(layoutInfo);
      }
    }
  }
}
