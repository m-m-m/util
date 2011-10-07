/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.sf.mmm.ui.toolkit.api.common.GridCellInfo;
import net.sf.mmm.ui.toolkit.api.view.composite.UiGridRow;
import net.sf.mmm.ui.toolkit.base.common.GridCellInfoBean;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.GridLayoutInfo.ColumnLayoutInfo;

/**
 * This is the implementation of {@link UiGridRow} using Swing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 */
public class UiGridRowImpl<CHILD extends AbstractUiElement<? extends JComponent>> extends
    AbstractUiMultiCompositeSwing<JPanel, CHILD> implements UiGridRow<CHILD>, UiGridLayouter {

  /** @see #getCellInfo(int) */
  private final List<GridCellInfoBean> cellInfos;

  /**
   * The {@link #calculateLayout(GridLayoutInfo) calculated} height of this row.
   */
  private int height;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiGridRowImpl(UiFactorySwing uiFactory, JPanel delegate) {

    super(uiFactory, delegate);
    this.cellInfos = new ArrayList<GridCellInfoBean>();
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
  public CHILD removeChild(int index) {

    this.cellInfos.remove(index);
    CHILD child = super.removeChild(index);
    getDelegate().remove(index);
    return child;
  }

  /**
   * {@inheritDoc}
   */
  public void addChild(CHILD child) {

    this.cellInfos.add(new GridCellInfoBean());
    doAddChild(child);
    JComponent toplevelDelegate = (JComponent) child.getAdapter().getToplevelDelegate();
    getDelegate().add(toplevelDelegate);
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(CHILD child, int index) {

    this.cellInfos.add(index, new GridCellInfoBean());
    doInsertChild(child, index);
    getDelegate().add((JComponent) child.getAdapter().getToplevelDelegate(), index);
  }

  /**
   * {@inheritDoc}
   */
  public void setChild(CHILD child, int column) {

    doSetChild(child, column);
  }

  /**
   * {@inheritDoc}
   */
  public GridCellInfo getCellInfo(int column) {

    return this.cellInfos.get(column);
  }

  /**
   * {@inheritDoc}
   */
  public void calculateLayout(GridLayoutInfo layoutInfo) {

    int size = getChildCount();
    int maxHeight = 0;
    int columnIndex = 0;
    for (int i = 0; i < size; i++) {
      CHILD child = getChild(i);
      GridCellInfo cellInfo = getCellInfo(i);
      ColumnLayoutInfo columnInfo = layoutInfo.getOrCreateColumnInfo(columnIndex);
      columnIndex = columnIndex + cellInfo.getColumnSpan();
      JComponent delegate = (JComponent) child.getAdapter().getToplevelDelegate();
      Dimension preferredSize = delegate.getPreferredSize();
      // TODO: row-span
      if (preferredSize.height > maxHeight) {
        maxHeight = preferredSize.height;
      }
      if (cellInfo.getColumnSpan() == 1) {
        int columnWidth = columnInfo.getWidth();
        if (columnWidth < preferredSize.width) {
          columnInfo.setWidth(preferredSize.width);
        }
      } else {
        // 2 pass calculation?
        // spread remaining with in colspans over the columns...
      }
    }
    this.height = maxHeight;
    layoutInfo.setTotalHeight(layoutInfo.getTotalHeight() + this.height);
  }

  /**
   * {@inheritDoc}
   */
  public void applyLayout(GridLayoutInfo layoutInfo) {

    getDelegate().setSize(layoutInfo.getTotalWidth(), this.height);
    int size = getChildCount();
    int x = 0;
    int y = layoutInfo.getCurrentY();
    for (int i = 0; i < size; i++) {
      CHILD child = getChild(i);
      GridCellInfo cellInfo = getCellInfo(i);
      int h = this.height;
      // TODO: row-span!
      JComponent delegate = (JComponent) child.getAdapter().getToplevelDelegate();
      delegate.setLocation(x, y);
      int width = layoutInfo.getWidth(i, cellInfo.getColumnSpan());
      x = x + width;
      delegate.setSize(width, h);
    }
    layoutInfo.setCurrentY(y + this.height);
  }
}
