package net.sf.mmm.ui.toolkit.impl.swing.composite;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;

import net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent;
import net.sf.mmm.ui.toolkit.base.composite.AbstractDecoratingLayoutManager;
import net.sf.mmm.ui.toolkit.base.composite.Size;

/**
 * This is the layout-manager that organizes the layout for
 * {@link UIDecoratedComponent decorated components}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
class DecoratingLayoutManager extends AbstractDecoratingLayoutManager implements LayoutManager2 {

  /**
   * The constructor.
   * 
   * @param decoratedComponent
   *        is the decorated component to layout.
   */
  public DecoratingLayoutManager(UIDecoratedComponent<?, ?> decoratedComponent) {

    super(decoratedComponent);
  }

  /**
   * {@inheritDoc}
   */
  public void layoutContainer(Container parent) {

    doLayout(new Size(parent.getWidth(), parent.getHeight()));
  }

  /**
   * {@inheritDoc}
   */
  public Dimension preferredLayoutSize(Container parent) {

    Size preferredSize = calculateSize();
    return new Dimension(preferredSize.width, preferredSize.height);
  }

  /**
   * {@inheritDoc}
   */
  public void addLayoutComponent(Component comp, Object constraints) {

  // nothing to do...
  }

  /**
   * {@inheritDoc}
   */
  public void addLayoutComponent(String name, Component comp) {

  // nothing to do...
  }

  /**
   * {@inheritDoc}
   */
  public void invalidateLayout(Container target) {

  // nothing to do...
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
  public Dimension minimumLayoutSize(Container parent) {

    return preferredLayoutSize(parent);
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

}
