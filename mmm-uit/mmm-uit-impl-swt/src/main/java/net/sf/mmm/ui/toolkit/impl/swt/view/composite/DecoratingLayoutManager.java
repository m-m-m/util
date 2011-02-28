/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.composite;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

import net.sf.mmm.ui.toolkit.api.view.composite.UiDecoratedComponent;
import net.sf.mmm.ui.toolkit.base.view.composite.AbstractDecoratingLayoutManager;
import net.sf.mmm.ui.toolkit.base.view.composite.Size;

/**
 * This is the layout-manager that organizes the layout for
 * {@link UiDecoratedComponent decorated components}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DecoratingLayoutManager extends Layout {

  /** The actual layout-manager where all work is delegated to. */
  private final Manager delegate;

  /**
   * The constructor.
   * 
   * @param decoratedComponent is the decorated component to layout.
   */
  public DecoratingLayoutManager(UiDecoratedComponent decoratedComponent) {

    super();
    this.delegate = new Manager(decoratedComponent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {

    return this.delegate.computeSize(composite, flushCache);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void layout(Composite composite, boolean flushCache) {

    this.delegate.layout(composite, flushCache);
  }

  /**
   * This inner class is the actual layout-manager.
   */
  private static class Manager extends AbstractDecoratingLayoutManager {

    /**
     * The constructor.
     * 
     * @param decoratedComponent is the decorated component to layout.
     */
    public Manager(UiDecoratedComponent decoratedComponent) {

      super(decoratedComponent);
    }

    /**
     * @see LayoutManager#layout(org.eclipse.swt.widgets.Composite, boolean)
     * 
     * @param composite is the panel.
     * @param flushCache - <code>true</code> if all cached data must be
     *        refreshed, <code>false</code> otherwise.
     */
    protected void layout(Composite composite, boolean flushCache) {

      Point size = composite.getSize();
      doLayout(new Size(size.x, size.y));
    }

    /**
     * @see LayoutManager#computeSize(Composite, int, int, boolean)
     * 
     * @param composite is the panel.
     * @param flushCache - <code>true</code> if all cached data must be
     *        refreshed, <code>false</code> otherwise.
     * @return the compited size.
     */
    protected Point computeSize(Composite composite, boolean flushCache) {

      Size result = calculateSize();
      return new Point(result.width + 4, result.height + 4);
    }

  }

}
