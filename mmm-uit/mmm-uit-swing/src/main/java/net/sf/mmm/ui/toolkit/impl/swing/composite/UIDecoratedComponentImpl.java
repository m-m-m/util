/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent;
import net.sf.mmm.ui.toolkit.api.state.UIReadSize;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent} interface
 * using Swing as the UI toolkit.
 * 
 * @param <D>
 *        is the templated type of the
 *        {@link #getDecorator() decorating component}.
 * @param <C>
 *        is the templated type of the {@link #getComponent() main component}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDecoratedComponentImpl<D extends UIComponent, C extends UIComponent> extends
    AbstractUIComposite implements UIDecoratedComponent<D, C> {

  /** @see #getSwingComponent() */
  private final JPanel panel;

  /** @see #getComponent() */
  private C component;

  /** @see #getDecorator() */
  private D decorator;

  /** @see #setDecoratorSizer(UIReadSize) */
  private UIReadSize decoratorSizer;

  /**
   * @param uiFactory
   * @param parentObject
   */
  public UIDecoratedComponentImpl(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.panel = new JPanel(new DecoratingLayoutManager());
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   */
  @Override
  public JComponent getSwingComponent() {

    return this.panel;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.composite.AbstractUIComposite#getOrientation()
   */
  @Override
  public Orientation getOrientation() {

    Orientation orientation = Orientation.HORIZONTAL;
    UINode parent = getParent();
    if ((parent != null) && (parent instanceof UIComposite)) {
      orientation = ((UIComposite) parent).getOrientation().getMirrored();
    }
    return orientation;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent#getComponent()
   */
  public C getComponent() {

    return this.component;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent#getDecorator()
   */
  public D getDecorator() {

    return this.decorator;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIComposite#getComponent(int)
   */
  public UIComponent getComponent(int index) {

    if (index == 0) {
      return getDecorator();
    } else if (index == 1) {
      return getComponent();
    } else {
      throw new IndexOutOfBoundsException("Illegal index (" + index + ") must be 0 or 1!");
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent#setDecorator(net.sf.mmm.ui.toolkit.api.UIComponent)
   */
  public void setDecorator(D newDecorator) {

    AbstractUIComponent abstractComponent = (AbstractUIComponent) newDecorator;
    if (this.decorator != null) {
      AbstractUIComponent oldComponent = (AbstractUIComponent) this.decorator;
      setParent(oldComponent, null);
      this.panel.remove(oldComponent.getSwingComponent());
    }
    if (abstractComponent.getParent() != null) {
      abstractComponent.removeFromParent();
    }
    this.decorator = newDecorator;
    JComponent swingComponent = abstractComponent.getSwingComponent();
    this.panel.add(swingComponent);
  }

  /**
   * This method sets the sizer used to override the size of the
   * {@link #getDecorator() decorator}.
   * 
   * @param sizer
   *        is the sizer to use or <code>null</code> to disable.
   */
  public void setDecoratorSizer(UIReadSize sizer) {

    this.decoratorSizer = sizer;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent#setComponent(net.sf.mmm.ui.toolkit.api.UIComponent)
   */
  public void setComponent(C newComponent) {

    AbstractUIComponent abstractComponent = (AbstractUIComponent) newComponent;
    if (this.component != null) {
      AbstractUIComponent oldComponent = (AbstractUIComponent) this.component;
      setParent(oldComponent, null);
      this.panel.remove(oldComponent.getSwingComponent());
    }
    this.component = newComponent;
    if (abstractComponent.getParent() != null) {
      abstractComponent.removeFromParent();
    }
    this.component = newComponent;
    JComponent swingComponent = abstractComponent.getSwingComponent();
    this.panel.add(swingComponent);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIComposite#getComponentCount()
   */
  public int getComponentCount() {

    return 2;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * This inner class is the layout-manager that organizes the layout for this
   * {@link UIDecoratedComponent} implementation.
   */
  private class DecoratingLayoutManager implements LayoutManager2 {

    /** the indent for the decorator */
    private static final int INDENT = 2;

    /** 2 * INDENT */
    private static final int DOUBLE_INDENT = 2 * INDENT;

    /**
     * The constructor.
     */
    public DecoratingLayoutManager() {

      super();
    }

    /**
     * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
     */
    public void layoutContainer(Container parent) {

      Dimension parentSize = parent.getSize();

      Dimension decoratorSize = null;
      if (UIDecoratedComponentImpl.this.decorator != null) {
        AbstractUIComponent abstractComponent = (AbstractUIComponent) UIDecoratedComponentImpl.this.decorator;
        JComponent swingComponent = abstractComponent.getSwingComponent();
        decoratorSize = swingComponent.getMinimumSize();
        UIReadSize sizer = UIDecoratedComponentImpl.this.decoratorSizer;
        if (sizer != null) {
          if (getOrientation() == Orientation.HORIZONTAL) {
            decoratorSize.width = sizer.getWidth();
          } else {
            decoratorSize.height = sizer.getHeight();
          }
        }
        if (getOrientation() == Orientation.HORIZONTAL) {
          swingComponent.setBounds(INDENT, 0, decoratorSize.width, decoratorSize.height);
        } else {
          swingComponent.setBounds(0, INDENT, decoratorSize.width, decoratorSize.height);
        }
      }

      if (UIDecoratedComponentImpl.this.component != null) {
        AbstractUIComponent abstractComponent = (AbstractUIComponent) UIDecoratedComponentImpl.this.component;
        JComponent swingComponent = abstractComponent.getSwingComponent();
        if (decoratorSize == null) {
          swingComponent.setBounds(0, 0, parentSize.width, parentSize.height);
        } else {
          if (getOrientation() == Orientation.HORIZONTAL) {
            swingComponent.setBounds(decoratorSize.width + DOUBLE_INDENT, 0, parentSize.width
                - decoratorSize.width - DOUBLE_INDENT, parentSize.height);
          } else {
            swingComponent.setBounds(0, decoratorSize.height + DOUBLE_INDENT, parentSize.width,
                parentSize.height - decoratorSize.height - DOUBLE_INDENT);
          }
        }
      }
    }

    /**
     * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
     */
    public Dimension preferredLayoutSize(Container parent) {

      Dimension preferredSize = null;
      if (UIDecoratedComponentImpl.this.decorator != null) {
        AbstractUIComponent abstractComponent = (AbstractUIComponent) UIDecoratedComponentImpl.this.decorator;
        JComponent swingComponent = abstractComponent.getSwingComponent();
        preferredSize = swingComponent.getMinimumSize();
        if (getOrientation() == Orientation.HORIZONTAL) {
          preferredSize.width += DOUBLE_INDENT;
        } else {
          preferredSize.height += DOUBLE_INDENT;
        }
      }
      UIReadSize sizer = UIDecoratedComponentImpl.this.decoratorSizer;
      if (sizer != null) {
        if (preferredSize == null) {
          preferredSize = new Dimension(sizer.getWidth(), sizer.getHeight());
        } else {
          if (getOrientation() == Orientation.HORIZONTAL) {
            preferredSize.width = sizer.getWidth() + DOUBLE_INDENT;
          } else {
            preferredSize.height = sizer.getHeight() + DOUBLE_INDENT;
          }
        }
      }
      if (UIDecoratedComponentImpl.this.component != null) {
        AbstractUIComponent abstractComponent = (AbstractUIComponent) UIDecoratedComponentImpl.this.component;
        JComponent swingComponent = abstractComponent.getSwingComponent();
        Dimension size = swingComponent.getPreferredSize();
        if (preferredSize == null) {
          preferredSize = size;
        } else {
          if (getOrientation() == Orientation.HORIZONTAL) {
            preferredSize.width += size.width;
            if (preferredSize.height < size.height) {
              preferredSize.height = size.height;
            }
          } else {
            preferredSize.height += size.height;
            if (preferredSize.width < size.width) {
              preferredSize.width = size.width;
            }
          }
        }
      }
      if (preferredSize == null) {
        return new Dimension();
      } else {
        return preferredSize;
      }
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

}
