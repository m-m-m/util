/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

import net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.base.composite.AbstractLayoutManager;
import net.sf.mmm.ui.toolkit.base.composite.Rectangle;
import net.sf.mmm.ui.toolkit.base.composite.Size;

/**
 * This is the layout-manager that organizes the layout of SWT panels.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LayoutManager extends Layout {

  /**
   * The actual layout-manager where all work is delegated to.
   */
  private final Manager delegate;

  /**
   * The constructor.
   */
  public LayoutManager() {

    super();
    this.delegate = new Manager();
  }

  /**
   * @see org.eclipse.swt.widgets.Layout#computeSize(org.eclipse.swt.widgets.Composite,
   *      int, int, boolean)
   */
  @Override
  protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {

    return this.delegate.computeSize(composite, flushCache);
  }

  /**
   * @see org.eclipse.swt.widgets.Layout#layout(org.eclipse.swt.widgets.Composite,
   *      boolean)
   */
  @Override
  protected void layout(Composite composite, boolean flushCache) {

    this.delegate.layout(composite, flushCache);
  }

  /**
   * This method gets the layout orientation.
   * 
   * @return the layout orientation.
   */
  public Orientation getOrientation() {

    return this.delegate.getOrientation();
  }

  /**
   * This method sets the layout orientation.
   * 
   * @param orientation
   *        is the layout orientation to set.
   */
  public void setOrientation(Orientation orientation) {

    this.delegate.setOrientation(orientation);
  }

  /**
   * This inner class is the actual layout-manager.
   */
  private static class Manager extends AbstractLayoutManager {

    /** the actual panel */
    private Composite panel;

    /**
     * The constructor.
     */
    public Manager() {

      super();
      this.panel = null;
    }

    /**
     * This method refreshes the cached data as neccessary.
     * 
     * @param flushCache -
     *        <code>true</code> if all cached data must be refreshed,
     *        <code>false</code> otherwise.
     */
    protected void refreshCachedData(boolean flushCache) {

      Control[] children = this.panel.getChildren();
      if (children.length == 0) {
        // nothing to do...
        return;
      }

      // refresh parent area
      org.eclipse.swt.graphics.Rectangle parentRect = this.panel.getClientArea();
      this.parentArea.x = parentRect.x + 2;
      this.parentArea.y = parentRect.y + 2;
      this.parentArea.width = parentRect.width - 4;
      this.parentArea.height = parentRect.height - 4;
      if (this.layoutOrientation == Orientation.VERTICAL) {
        this.parentArea.swap();
      }

      int length = children.length;
      ensureCacheSize(length);

      for (int i = 0; i < length; i++) {
        this.constraints[i] = (LayoutConstraints) children[i].getLayoutData();
        // if (children[i].isVisible()) {
        // if (children[i].isEnabled()) {
        if (true) {
          Point childSize = children[i].computeSize(SWT.DEFAULT, SWT.DEFAULT, flushCache);
          // Dimension childSize = child.getPreferredSize();
          this.childSizes[i].width = childSize.x;
          this.childSizes[i].height = childSize.y;
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
     * @see LayoutManager#layout(org.eclipse.swt.widgets.Composite, boolean)
     * 
     * @param composite
     *        is the panel.
     * @param flushCache -
     *        <code>true</code> if all cached data must be refreshed,
     *        <code>false</code> otherwise.
     */
    protected void layout(Composite composite, boolean flushCache) {

      this.panel = composite;
      refreshCachedData(flushCache);
      calculateLayout();
      Control[] children = this.panel.getChildren();
      for (int i = 0; i < children.length; i++) {
        if (this.childSizes[i].width != 0) {
          if (this.layoutOrientation == Orientation.HORIZONTAL) {
            children[i].setBounds(this.childAreas[i].x, this.childAreas[i].y,
                this.childAreas[i].width, this.childAreas[i].height);
          } else {
            children[i].setBounds(this.childAreas[i].y, this.childAreas[i].x,
                this.childAreas[i].height, this.childAreas[i].width);
          }
        }
      }
    }

    /**
     * @see LayoutManager#computeSize(Composite, int, int, boolean)
     * 
     * @param composite
     *        is the panel.
     * @param flushCache -
     *        <code>true</code> if all cached data must be refreshed,
     *        <code>false</code> otherwise.
     * @return the compited size.
     */
    protected Point computeSize(Composite composite, boolean flushCache) {

      this.panel = composite;
      refreshCachedData(flushCache);
      if (this.childSizes == null) {
        return new Point(0, 0);
      } else {
        Size result = calculateSize();
        return new Point(result.width + 4, result.height + 4);
      }
    }

  }

}
