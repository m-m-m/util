/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.sync;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;

import org.eclipse.swt.widgets.Composite;

/**
 * This class is used for synchronous access on a SWT
 * {@link org.eclipse.swt.widgets.Composite}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncCompositeAccess extends AbstractSyncCompositeAccess<Composite> {

  /** the composite to access */
  private Composite composite;

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param node is the owning {@link #getNode() node}.
   * @param swtStyle is the {@link org.eclipse.swt.widgets.Widget#getStyle()
   *        style} of the menu.
   */
  public SyncCompositeAccess(UiFactorySwt uiFactory, UiElement node, int swtStyle) {

    this(uiFactory, node, swtStyle, null);
  }

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param node is the owning {@link #getNode() node}.
   * @param swtStyle is the {@link org.eclipse.swt.widgets.Widget#getStyle()
   *        style} of the composite.
   * @param swtComposite is the composite to access.
   */
  public SyncCompositeAccess(UiFactorySwt uiFactory, UiElement node, int swtStyle,
      Composite swtComposite) {

    super(uiFactory, node, swtStyle);
    this.composite = swtComposite;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createSynchron() {

    this.composite = new Composite(getParent(), getStyle());
    super.createSynchron();
  }

  /**
   * {@inheritDoc}
   */
  public Composite getDelegate() {

    return this.composite;
  }

}
