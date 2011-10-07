/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;

import net.sf.mmm.util.nls.api.NlsIllegalStateException;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GridLayoutManager implements LayoutManager2 {

  /** @see #getGridPanel() */
  private final UiGridPanelImpl gridPanel;

  /** @see #getLayoutInfo() */
  private final GridLayoutInfo layoutInfo;

  /**
   * The constructor.
   * 
   * @param gridPanel is the {@link #getGridPanel() grid panel}.
   */
  public GridLayoutManager(UiGridPanelImpl gridPanel) {

    super();
    this.gridPanel = gridPanel;
    this.layoutInfo = new GridLayoutInfo();
  }

  /**
   * This method gets the {@link UiGridPanelImpl}.
   * 
   * @return the {@link UiGridPanelImpl}.
   */
  public UiGridPanelImpl getGridPanel() {

    return this.gridPanel;
  }

  /**
   * This method gets the {@link GridLayoutInfo}.
   * 
   * @return the {@link GridLayoutInfo}.
   */
  public GridLayoutInfo getLayoutInfo() {

    return this.layoutInfo;
  }

  /**
   * {@inheritDoc}
   */
  public void addLayoutComponent(String name, Component comp) {

    throw new NlsIllegalStateException();
  }

  /**
   * {@inheritDoc}
   */
  public void removeLayoutComponent(Component comp) {

    // nothing to do...
  }

  /**
   * {@inheritDoc}
   */
  public Dimension preferredLayoutSize(Container parent) {

    this.gridPanel.calculateLayout(this.layoutInfo);
    return new Dimension(this.layoutInfo.getTotalWidth(), this.layoutInfo.getTotalHeight());
  }

  /**
   * {@inheritDoc}
   */
  public Dimension minimumLayoutSize(Container parent) {

    return preferredLayoutSize(parent);
  }

  /**
   * {@inheritDoc}
   */
  public void layoutContainer(Container parent) {

    this.gridPanel.calculateLayout(this.layoutInfo);
    this.gridPanel.applyLayout(this.layoutInfo);
  }

  /**
   * {@inheritDoc}
   */
  public void addLayoutComponent(Component comp, Object constraints) {

    // no constraints required/supported...
    if (constraints != null) {
      throw new NlsIllegalStateException();
    }
  }

  /**
   * {@inheritDoc}
   */
  public Dimension maximumLayoutSize(Container target) {

    return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
  }

  /**
   * {@inheritDoc}
   */
  public float getLayoutAlignmentX(Container target) {

    return 0.5F;
  }

  /**
   * {@inheritDoc}
   */
  public float getLayoutAlignmentY(Container target) {

    return 0.5F;
  }

  /**
   * {@inheritDoc}
   */
  public void invalidateLayout(Container target) {

    // nothing to do...

  }

}
