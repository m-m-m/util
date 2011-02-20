/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.composite;

import net.sf.mmm.ui.toolkit.api.UIFactoryRenamed;
import net.sf.mmm.ui.toolkit.api.types.Alignment;
import net.sf.mmm.ui.toolkit.api.types.Filling;
import net.sf.mmm.ui.toolkit.api.types.Orientation;
import net.sf.mmm.ui.toolkit.api.view.composite.Insets;
import net.sf.mmm.ui.toolkit.api.view.composite.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.attribute.UiReadSize;
import net.sf.mmm.ui.toolkit.base.AbstractUIFactory;

/**
 * This is the abstract base implementation of a manager for the layout of a
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel panel}.<br>
 * The implementation assumes that for each panel an own layout-manager is
 * created and is therefore not thread-safe.
 * 
 * @see net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel#addChild(net.sf.mmm.ui.toolkit.api.UIComponent,
 *      LayoutConstraints)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractLayoutManager {

  /** The number of spare entries added if the cache size is increased. */
  private static final int CACHE_SPARE_SIZE = 3;

  /** @see #getOrientation() */
  protected Orientation layoutOrientation;

  /** @see #getFactory() */
  private AbstractUIFactory factory;

  /**
   * The layout constraints of the components. For a given array position the
   * constraint, childSize and childArea must belong to the same component.
   */
  protected LayoutConstraints[] constraints;

  /**
   * The preferred child sizes. If the {@link #layoutOrientation layout} is
   * {@link Orientation#VERTICAL}, {@link Size#width} and {@link Size#height}
   * must be swapped.
   * 
   * @see #constraints
   */
  protected Size[] childSizes;

  /**
   * The child areas calculated for layout. If the
   * {@link #layoutOrientation layout} is {@link Orientation#VERTICAL},
   * {@link Rectangle#x} and {@link Rectangle#y} as well as
   * {@link Rectangle#width} and {@link Rectangle#height} must be swapped.
   * 
   * @see #constraints
   */
  protected Rectangle[] childAreas;

  /**
   * The area of the panel to layout.
   */
  protected Rectangle parentArea;

  /** The calculated size for the panel. */
  protected Size size;

  /** the number of child-components currently cached */
  protected int childCount;

  /**
   * The constructor.
   * 
   * @param factory is the owning UI-factory.
   */
  public AbstractLayoutManager(AbstractUIFactory factory) {

    super();
    this.factory = factory;
    this.layoutOrientation = Orientation.HORIZONTAL;
    this.parentArea = new Rectangle();
    this.size = new Size();
    this.constraints = new LayoutConstraints[0];
    this.childSizes = null;
    this.childAreas = null;
    this.childCount = 0;
  }

  /**
   * This method gets the UI-factory that created this manager.
   * 
   * @return the factory
   */
  public UIFactoryRenamed getFactory() {

    return this.factory;
  }

  /**
   * This method calculates the size of a
   * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel panel}.
   * 
   * @return the calculated size for the panel.
   */
  public Size calculateSize() {

    boolean horizontal = isHorizontal();
    boolean flipHorizontal = this.factory.isFlipHorizontal();
    this.size.width = 0;
    this.size.height = 0;
    for (int i = 0; i < this.childCount; i++) {
      int childIndex;
      if (flipHorizontal) {
        childIndex = this.childCount - i - 1;
      } else {
        childIndex = i;
      }
      // if with is 0, the component is NOT visible
      if (this.childSizes[childIndex].width != 0) {
        if (this.childSizes[childIndex].height > this.size.height) {
          this.size.height = this.childSizes[childIndex].height;
        }
        this.size.width += this.childSizes[childIndex].width;
        if (horizontal) {
          this.size.width += this.constraints[childIndex].insets.getHorizontalSpace();
        } else {
          this.size.width += this.constraints[childIndex].insets.getVerticalSpace();
        }
      }
    }
    if (!horizontal) {
      this.size.swap();
    }
    return this.size;
  }

  /**
   * This method determines if the layout orientation is horizontal or vertical.
   * 
   * @see #getOrientation()
   * @see UIFactoryRenamed#getScriptOrientation()
   * @see net.sf.mmm.ui.toolkit.api.types.ScriptOrientation#isHorizontal()
   * 
   * @return <code>true</code> if the layout is horizontal, <code>false</code>
   *         if vertical.
   */
  protected boolean isHorizontal() {

    boolean horizontal = (this.layoutOrientation == Orientation.HORIZONTAL);
    // was the GUI designed with an horizontally inverted orientation?
    if (this.factory.isFlipVertical()) {
      horizontal = !horizontal;
    }
    return horizontal;
  }

  /**
   * This method calculates the layout.
   */
  public void calculateLayout() {

    if (this.constraints.length == 0) {
      // nothing to do...
      return;
    }
    boolean horizontal = isHorizontal();
    boolean flipHorizontal = this.factory.isFlipHorizontal();
    int axisFixed = 0;
    double axisDynamic = 0;
    // pass 1
    for (int i = 0; i < this.childCount; i++) {
      int childIndex;
      if (flipHorizontal) {
        childIndex = this.childCount - i - 1;
      } else {
        childIndex = i;
      }
      // if width is 0, the component is NOT visible
      if (this.childSizes[childIndex].width != 0) {
        // add insets as fixed space
        if (horizontal) {
          axisFixed += this.constraints[childIndex].insets.getHorizontalSpace();
        } else {
          axisFixed += this.constraints[childIndex].insets.getVerticalSpace();
        }
        // Filling axisFilling =
        // this.constraints[i].filling.getPart(this.layoutOrientation);
        // if ((this.constraints[i].weight == 0) || (Filling.NONE ==
        // axisFilling)) {
        if (this.constraints[childIndex].weight == 0) {
          axisFixed += this.childSizes[childIndex].width;
        } else {
          axisDynamic += this.childSizes[childIndex].width * this.constraints[childIndex].weight;
        }
      }
    }

    // evaluate strategy
    int axisAvailable = this.parentArea.width;
    double fixscale = 1;
    double scale = 1;
    double axisPrefered = axisFixed + axisDynamic;
    if (axisPrefered == 0) {
      return;
    }
    if (axisDynamic == 0) {
      if (axisAvailable < axisFixed) {
        // fixscale = axisAvailable / (double) axisFixed;
      }
    } else {
      scale = (axisAvailable - axisFixed) / axisDynamic;
    }

    // pass 2
    int x = this.parentArea.x;
    int y = this.parentArea.y;
    int childWidth;
    int width; // = parentArea.width;
    int height = this.parentArea.height;
    for (int i = 0; i < this.childCount; i++) {
      int childIndex;
      if (flipHorizontal) {
        childIndex = this.childCount - i - 1;
      } else {
        childIndex = i;
      }
      // if with is 0, the component is NOT visible
      if (this.childSizes[childIndex].width != 0) {
        Filling filling = this.constraints[childIndex].filling;
        Insets insets = this.constraints[childIndex].insets;
        Alignment alignment = this.constraints[childIndex].alignment;
        if (!horizontal) {
          filling = filling.getMirrored();
          insets = insets.getSwapped();
          alignment = alignment.getMirrored();
        }
        boolean axisFill = (this.constraints[childIndex].weight != 0);
        double w;
        // if (axisFill && ((filling == Filling.HORIZONTAL) || (filling ==
        // Filling.BOTH))) {
        if (axisFill) {
          w = this.constraints[childIndex].weight * scale;
        } else {
          w = fixscale;
        }
        childWidth = this.childSizes[childIndex].width + insets.getHorizontalSpace();
        width = (int) (childWidth * w);
        this.childAreas[childIndex].x = x;
        this.childAreas[childIndex].y = y + insets.top;

        // TODO: reduce insets if (width < clientAreas[i].width) ||
        // (height < clientAreas[i].height) ???

        int insetsLeft;
        if (flipHorizontal) {
          insetsLeft = insets.right;
        } else {
          insetsLeft = insets.left;
        }
        // horizontal filling?
        if (width < childWidth) {
          if (width > this.childAreas[childIndex].width) {
            int space = width - this.childSizes[childIndex].width;
            this.childAreas[childIndex].x += (int) (insetsLeft * ((double) space / insets
                .getHorizontalSpace()));
          }
          this.childAreas[childIndex].width = width;
        } else if ((filling == Filling.HORIZONTAL) || (filling == Filling.BOTH)) {
          this.childAreas[childIndex].x += insetsLeft;
          this.childAreas[childIndex].width = width - insets.getHorizontalSpace();
        } else {
          this.childAreas[childIndex].width = this.childSizes[childIndex].width;
          Alignment hAlignment = alignment.getHorizontalPart();
          int xSpace = width - this.childSizes[childIndex].width;
          if (hAlignment == Alignment.CENTER) {
            this.childAreas[childIndex].x += xSpace / 2;
          } else if (hAlignment == Alignment.RIGHT) {
            this.childAreas[childIndex].x += xSpace;
          }
        }

        // vertical filling?
        if ((height < this.childAreas[childIndex].height) || (filling == Filling.VERTICAL)
            || (filling == Filling.BOTH)) {
          this.childAreas[childIndex].height = height - insets.getVerticalSpace();
        } else {
          this.childAreas[childIndex].height = this.childSizes[childIndex].height;
          Alignment vAlignment = alignment.getVerticalPart();
          int ySpace = height - this.childSizes[childIndex].height;
          if (vAlignment == Alignment.CENTER) {
            this.childAreas[childIndex].y += ySpace / 2;
          } else if (vAlignment == Alignment.BOTTOM) {
            this.childAreas[childIndex].y += ySpace;
          }
        }
        x += width;
      }
    }
  }

  /**
   * This method gets the orientation for this layout.
   * 
   * @return the orientation.
   */
  public Orientation getOrientation() {

    return this.layoutOrientation;
  }

  /**
   * This method sets the orientation for this layout.
   * 
   * @param orientation is the layoutOrientation to set.
   */
  public void setOrientation(Orientation orientation) {

    this.layoutOrientation = orientation;
  }

  /**
   * This method ensures that the cache has at least the given size.
   * 
   * @param componentCount is the required cache size.
   */
  protected void ensureCacheSize(int componentCount) {

    // refresh constraints
    if ((this.constraints == null) || (this.constraints.length < componentCount)) {
      this.constraints = new LayoutConstraints[componentCount + CACHE_SPARE_SIZE];
    }

    // refresh childSizes
    if ((this.childSizes == null) || (this.childSizes.length < componentCount)) {
      Size[] oldSizes = this.childSizes;
      this.childSizes = new Size[componentCount + CACHE_SPARE_SIZE];
      if (oldSizes != null) {
        System.arraycopy(oldSizes, 0, this.childSizes, 0, this.childCount);
      }
    }
    for (int i = this.childCount; i < componentCount; i++) {
      this.childSizes[i] = new Size();
    }
    this.childCount = componentCount;
  }

  /**
   * This method handles the size overriding. The width and/or height of the
   * given component size is overridden according to the given
   * {@link LayoutConstraints#size}.
   * 
   * @param constraintsSize is the {@link LayoutConstraints#size} that may
   *        override width/height.
   * @param componentSize is the size of the component. It will be manipulated
   *        as necessary.
   */
  public static void overrideSize(UiReadSize constraintsSize, Size componentSize) {

    int w = constraintsSize.getWidth();
    if (w > 0) {
      componentSize.width = w;
    }
    int h = constraintsSize.getHeight();
    if (h > 0) {
      componentSize.height = h;
    }
  }

}
