/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

import net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent;
import net.sf.mmm.ui.toolkit.base.composite.AbstractDecoratingLayoutManager;
import net.sf.mmm.ui.toolkit.base.composite.Size;

/**
 * This is the layout-manager that organizes the layout for
 * {@link UIDecoratedComponent decorated components}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DecoratingLayoutManager extends Layout {

  /** The actual layout-manager where all work is delegated to. */
  private final Manager delegate;

  /**
   * The constructor.
   * 
   * @param decoratedComponent
   *        is the decorated component to layout.
   */
  public DecoratingLayoutManager(UIDecoratedComponent decoratedComponent) {

    super();
    this.delegate = new Manager(decoratedComponent);
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
   * This inner class is the actual layout-manager.
   */
  private static class Manager extends AbstractDecoratingLayoutManager {

    /**
     * The constructor.
     * 
     * @param decoratedComponent
     *        is the decorated component to layout.
     */
    public Manager(UIDecoratedComponent decoratedComponent) {

      super(decoratedComponent);
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

      Point size = composite.getSize();
      doLayout(new Size(size.x, size.y));
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

      Size result = calculateSize();
      return new Point(result.width + 4, result.height + 4);
    }

  }

}
