/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.base.composite.AbstractLayoutManager;
import net.sf.mmm.ui.toolkit.base.composite.Rectangle;
import net.sf.mmm.ui.toolkit.base.composite.Size;

/**
 * This is the layout-manager that organizes the layout of swing panels.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LayoutManager extends AbstractLayoutManager implements LayoutManager2 {

  /**
   * mapps the component to its according layout constraints.
   */
  private final Map<Component, LayoutConstraints> component2constraintMap;

  /**
   * The swing panel associated with this layout-manager.
   */
  private Container panel;

  /**
   * The constructor.
   */
  public LayoutManager() {

    super();
    this.component2constraintMap = new HashMap<Component, LayoutConstraints>();
    this.panel = null;
  }

  /**
   * @see java.awt.LayoutManager2#addLayoutComponent(java.awt.Component,
   *      java.lang.Object)
   */
  public void addLayoutComponent(Component component, Object layoutConstraints) {

    try {
      this.component2constraintMap.put(component, (LayoutConstraints) layoutConstraints);
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("Constraints " + layoutConstraints
          + " are invalid - must be an instance of " + LayoutConstraints.class + "!");
    }
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

  /**
   * @see java.awt.LayoutManager2#invalidateLayout(java.awt.Container)
   */
  public void invalidateLayout(Container target) {

    this.panel = target;
  }

  /**
   * This method refreshes the cached data as neccessary.
   */
  protected void refreshCachedData() {

    if (this.panel == null) {
      return;
    }
    // refresh parent area
    Dimension parentSize = this.panel.getSize();
    Insets insets = this.panel.getInsets();
    this.parentArea.x = insets.left;
    this.parentArea.y = insets.top;
    if (this.parentArea.y > 10) {
      this.parentArea.y -= 4;
    }
    this.parentArea.width = parentSize.width - (this.parentArea.x + insets.right);
    this.parentArea.height = parentSize.height - (this.parentArea.y + insets.bottom);
    if (this.layoutOrientation == Orientation.VERTICAL) {
      this.parentArea.swap();
    }

    int length = this.panel.getComponentCount();
    ensureCacheSize(length);

    for (int i = 0; i < length; i++) {
      this.constraints[i] = this.component2constraintMap.get(this.panel.getComponent(i));
      Component child = this.panel.getComponent(i);
      if (child.isVisible()) {
        // Dimension childSize = child.getPreferredSize();
        Dimension childSize = child.getMinimumSize();
        this.childSizes[i].width = childSize.width;
        this.childSizes[i].height = childSize.height;
        overrideSize(this.constraints[i].size, this.childSizes[i]);
        if (this.layoutOrientation == Orientation.VERTICAL) {
          this.childSizes[i].swap();
        }
      } else {
        this.childSizes[i].width = 0;
      }
    }

    // refresh childAreas
    if ((this.childAreas == null) || (this.childAreas.length != length)) {
      this.childAreas = new Rectangle[length];
      for (int i = 0; i < length; i++) {
        this.childAreas[i] = new Rectangle();
      }
    }

  }

  /**
   * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
   *      java.awt.Component)
   */
  public void addLayoutComponent(String name, Component comp) {

  // Has no effect, since this layout manager does not use a per-component
  // string.

  }

  /**
   * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
   */
  public void removeLayoutComponent(Component component) {

    this.component2constraintMap.remove(component);
  }

  /**
   * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
   */
  public Dimension preferredLayoutSize(Container target) {

    this.panel = target;
    refreshCachedData();
    Size result = calculateSize();
    return new Dimension(result.width, result.height);
  }

  /**
   * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
   */
  public Dimension minimumLayoutSize(Container target) {

    return preferredLayoutSize(target);
  }

  /**
   * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
   */
  public void layoutContainer(Container target) {

    this.panel = target;
    refreshCachedData();
    calculateLayout();
    for (int i = 0; i < this.childAreas.length; i++) {
      if (this.childSizes[i].width != 0) {
        if (this.layoutOrientation == Orientation.HORIZONTAL) {
          this.panel.getComponent(i).setBounds(this.childAreas[i].x, this.childAreas[i].y,
              this.childAreas[i].width, this.childAreas[i].height);
        } else {
          this.panel.getComponent(i).setBounds(this.childAreas[i].y, this.childAreas[i].x,
              this.childAreas[i].height, this.childAreas[i].width);

        }
      }
    }
  }
}
