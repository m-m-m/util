/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.composite;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent;
import net.sf.mmm.ui.toolkit.api.attribute.UiReadSize;

/**
 * This inner class is the layout-manager that organizes the layout for this
 * {@link UIDecoratedComponent} implementation.
 */
public abstract class AbstractDecoratingLayoutManager {

  /** the indent for the decorator */
  private static final int INDENT = 2;

  /** 2 * INDENT */
  private static final int DOUBLE_INDENT = 2 * INDENT;

  /** the decorated-component to layout */
  private final UIDecoratedComponent<?, ?> decoratedComponent;

  /** the sizer for the decorators */
  private UiReadSize sizer;

  /**
   * The constructor.
   * 
   * @param decoratedComp TODO
   */
  public AbstractDecoratingLayoutManager(UIDecoratedComponent<?, ?> decoratedComp) {

    super();
    this.decoratedComponent = decoratedComp;
  }

  /**
   * This method sets the sizer used to override the size of the
   * {@link UIDecoratedComponent#getDecorator() decorator}.
   * 
   * @param sizer is the sizer to use or <code>null</code> to disable.
   */
  public void setSizer(UiReadSize sizer) {

    this.sizer = sizer;
  }

  /**
   * This method does the actual layout.
   * 
   * @param parentSize is the size of the
   *        {@link net.sf.mmm.ui.toolkit.api.composite.UIComposite composite} to
   *        layout.
   */
  protected void doLayout(Size parentSize) {

    if ((parentSize.width < 0) || (parentSize.height < 0)) {
      return;
    }
    UIComponent decorator = this.decoratedComponent.getDecorator();
    UIComponent component = this.decoratedComponent.getComponent();
    int width = 0;
    int height = 0;
    if (decorator != null) {
      width = decorator.getPreferredWidth();
      height = decorator.getPreferredHeight();
      if (this.sizer != null) {
        if (this.decoratedComponent.getOrientation() == Orientation.HORIZONTAL) {
          width = this.sizer.getWidth();
        } else {
          height = this.sizer.getHeight();
        }
      }
      if (this.decoratedComponent.getOrientation() == Orientation.HORIZONTAL) {
        int y = 0;
        if (component != null) {
          int componentHeigth = component.getPreferredHeight();
          if (componentHeigth > height) {
            y = (componentHeigth - height) >> 1;
          }
        }
        decorator.setPosition(INDENT, y);
      } else {
        int x = 0;
        if (component != null) {
          int componentWidth = component.getPreferredWidth();
          if (componentWidth > width) {
            x = (componentWidth - width) >> 1;
          }
        }
        decorator.setPosition(x, INDENT);
      }
      decorator.setSize(width, height);
    }

    if (component != null) {
      if (decorator == null) {
        component.setPosition(0, 0);
        component.setSize(parentSize.width, parentSize.height);
      } else {
        if (this.decoratedComponent.getOrientation() == Orientation.HORIZONTAL) {
          component.setPosition(width + DOUBLE_INDENT, 0);
          component.setSize(parentSize.width - width - DOUBLE_INDENT, parentSize.height);
        } else {
          component.setPosition(0, height + DOUBLE_INDENT);
          component.setSize(parentSize.width, parentSize.height - height - DOUBLE_INDENT);
        }
      }
    }
  }

  /**
   * This method calculates the preferred size for the layout.
   * 
   * @return the preferred size.
   */
  protected Size calculateSize() {

    Size size = null;
    UIComponent decorator = this.decoratedComponent.getDecorator();
    if (decorator != null) {
      size = new Size();
      size.width = decorator.getPreferredWidth();
      size.height = decorator.getPreferredHeight();
      if (this.decoratedComponent.getOrientation() == Orientation.HORIZONTAL) {
        size.width += DOUBLE_INDENT;
      } else {
        size.height += DOUBLE_INDENT;
      }
    }
    if (this.sizer != null) {
      if (size == null) {
        size = new Size(this.sizer.getWidth(), this.sizer.getHeight());
      } else {
        if (this.decoratedComponent.getOrientation() == Orientation.HORIZONTAL) {
          size.width = this.sizer.getWidth() + DOUBLE_INDENT;
        } else {
          size.height = this.sizer.getHeight() + DOUBLE_INDENT;
        }
      }
    }
    UIComponent component = this.decoratedComponent.getComponent();
    if (component != null) {
      int width = component.getPreferredWidth();
      int height = component.getPreferredHeight();
      if (size == null) {
        size = new Size(width, height);
      } else {
        if (this.decoratedComponent.getOrientation() == Orientation.HORIZONTAL) {
          size.width += width;
          if (size.height < height) {
            size.height = height;
          }
        } else {
          size.height += height;
          if (size.width < width) {
            size.width = width;
          }
        }
      }
    }
    if (size == null) {
      size = new Size();
    }
    return size;
  }
}
