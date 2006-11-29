package net.sf.mmm.ui.toolkit.impl.swing.composite;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;

import net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent;
import net.sf.mmm.ui.toolkit.base.composite.AbstractDecoratingLayoutManager;
import net.sf.mmm.ui.toolkit.base.composite.Size;

/**
 * This inner class is the layout-manager that organizes the layout for this
 * {@link UIDecoratedComponent} implementation.
 */
class DecoratingLayoutManager extends AbstractDecoratingLayoutManager implements LayoutManager2 {

  /**
   * The constructor.
   * 
   * @param decoratedComponent
   *        is the decorated component to layout.
   */
  public DecoratingLayoutManager(UIDecoratedComponent decoratedComponent) {

    super(decoratedComponent);
  }

  /**
   * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
   */
  public void layoutContainer(Container parent) {

    doLayout(new Size(parent.getWidth(), parent.getHeight()));
  }

  /**
   * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
   */
  public Dimension preferredLayoutSize(Container parent) {

    Size preferredSize = calculateSize();
    System.out.println(preferredSize.width + "*" + preferredSize.height);
    return new Dimension(preferredSize.width, preferredSize.height);
  }

  /**
   * @see java.awt.LayoutManager2#addLayoutComponent(java.awt.Component,
   *      java.lang.Object)
   */
  public void addLayoutComponent(Component comp, Object constraints) {

  // nothing to do...
  }

  /**
   * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
   *      java.awt.Component)
   */
  public void addLayoutComponent(String name, Component comp) {

  // nothing to do...
  }

  /**
   * @see java.awt.LayoutManager2#invalidateLayout(java.awt.Container)
   */
  public void invalidateLayout(Container target) {

  // nothing to do...
  }

  /**
   * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
   */
  public void removeLayoutComponent(Component comp) {

  // nothing to do...
  }

  /**
   * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
   */
  public Dimension minimumLayoutSize(Container parent) {

    return preferredLayoutSize(parent);
  }

  /**
   * @see java.awt.LayoutManager2#maximumLayoutSize(java.awt.Container)
   */
  public Dimension maximumLayoutSize(Container target) {

    return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
  }

  /**
   * @see java.awt.LayoutManager2#getLayoutAlignmentX(java.awt.Container)
   */
  public float getLayoutAlignmentX(Container target) {

    return 0.5F;
  }

  /**
   * @see java.awt.LayoutManager2#getLayoutAlignmentY(java.awt.Container)
   */
  public float getLayoutAlignmentY(Container target) {

    return 0.5F;
  }

}
