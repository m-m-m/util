/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.sync;

import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

/**
 * This is the abstract base class used for synchronous access on a SWT
 * {@link org.eclipse.swt.widgets.Composite}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * @since 1.0.0
 */
public abstract class AbstractSyncCompositeAccess<DELEGATE extends Composite> extends
    AbstractSyncControlAccess<DELEGATE> {

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Composite#setLayout(org.eclipse.swt.widgets.Layout)
   * layout} of the composite.
   */
  private static final String OPERATION_SET_LAYOUT = "setLayout";

  /**
   * operation to perform a {@link org.eclipse.swt.widgets.Composite#layout()
   * layout} of the composite.
   */
  private static final String OPERATION_LAYOUT = "layout";

  /** the layout manager to set */
  private Layout layout;

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param node is the owning {@link #getNode() node}.
   * @param swtStyle is the {@link Composite#getStyle() style} of the composite.
   */
  public AbstractSyncCompositeAccess(UiFactorySwt uiFactory, UiNode node, int swtStyle) {

    super(uiFactory, node, swtStyle);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_SET_LAYOUT) {
      getDelegate().setLayout(this.layout);
    } else if (operation == OPERATION_LAYOUT) {
      getDelegate().layout();
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createSynchron() {

    if (this.layout != null) {
      getDelegate().setLayout(this.layout);
    }
    super.createSynchron();
  }

  /**
   * This method sets the
   * {@link org.eclipse.swt.widgets.Composite#setLayout(org.eclipse.swt.widgets.Layout)
   * layout} of the composite.
   * 
   * @param layoutManager is the layout to set.
   */
  public void setLayout(Layout layoutManager) {

    this.layout = layoutManager;
    invoke(OPERATION_SET_LAYOUT);
  }

  /**
   * This method performs a {@link org.eclipse.swt.widgets.Composite#layout()
   * layout} of the composite.
   */
  public void layout() {

    invoke(OPERATION_LAYOUT);
  }

}
