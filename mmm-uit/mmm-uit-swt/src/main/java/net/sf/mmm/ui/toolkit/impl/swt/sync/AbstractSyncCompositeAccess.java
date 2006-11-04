/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Widget;

import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;

/**
 * This is the abstract base class used for synchron access on a SWT
 * {@link org.eclipse.swt.widgets.Composite}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSyncCompositeAccess extends AbstractSyncControlAccess {

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Composite#setLayout(org.eclipse.swt.widgets.Layout) layout}
   * of the composite.
   */
  private static final String OPERATION_SET_LAYOUT = "setLayout";

  /**
   * operation to perform a
   * {@link org.eclipse.swt.widgets.Composite#layout() layout} of the
   * composite.
   */
  private static final String OPERATION_LAYOUT = "layout";

  /** the layout manager to set */
  private Layout layout;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is used to do the synchonization.
   * @param swtStyle
   *        is the {@link Widget#getStyle() style} of the composite.
   */
  public AbstractSyncCompositeAccess(UIFactorySwt uiFactory, int swtStyle) {

    super(uiFactory, swtStyle);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#getSwtObject()
   * 
   */
  @Override
  public abstract Composite getSwtObject();

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#performSynchron(String)
   * 
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_SET_LAYOUT) {
      getSwtObject().setLayout(this.layout);
    } else if (operation == OPERATION_LAYOUT) {
      getSwtObject().layout();
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#createSynchron()
   * 
   */
  @Override
  protected void createSynchron() {

    if (this.layout != null) {
      getSwtObject().setLayout(this.layout);
    }
    super.createSynchron();
  }

  /**
   * This method sets the
   * {@link org.eclipse.swt.widgets.Composite#setLayout(org.eclipse.swt.widgets.Layout) layout}
   * of the composite.
   * 
   * @param layoutManager
   *        is the layout to set.
   */
  public void setLayout(Layout layoutManager) {

    assert (checkReady());
    this.layout = layoutManager;
    invoke(OPERATION_SET_LAYOUT);
  }

  /**
   * This method performs a
   * {@link org.eclipse.swt.widgets.Composite#layout() layout} of the
   * composite.
   */
  public void layout() {

    assert (checkReady());
    invoke(OPERATION_LAYOUT);
  }

}
