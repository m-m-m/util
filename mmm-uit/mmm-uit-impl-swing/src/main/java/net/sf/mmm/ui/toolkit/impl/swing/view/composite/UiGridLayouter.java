/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

/**
 * This is the interface for an aspect of the layout management for a
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiGridPanel}. Implementations
 * can use this interface for a divide and conquer strategy.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiGridLayouter {

  /**
   * This method calculates the layout of the grid.
   * 
   * @param layoutInfo is the layout information.
   */
  void calculateLayout(GridLayoutInfo layoutInfo);

  /**
   * This method applies the layout of the grid. It will be invoked after
   * {@link #calculateLayout(GridLayoutInfo)} using the same
   * <code>layoutInfo</code> object.
   * 
   * @param layoutInfo is the layout information.
   */
  void applyLayout(GridLayoutInfo layoutInfo);

}
