/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;

/**
 * This class is used for synchron access on a SWT
 * {@link org.eclipse.swt.widgets.Composite}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncCompositeAccess extends AbstractSyncCompositeAccess {

  /** the composite to access */
  private Composite composite;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is used to do the synchonization.
   * @param swtStyle
   *        is the {@link Widget#getStyle() style} of the menu.
   */
  public SyncCompositeAccess(UIFactorySwt uiFactory, int swtStyle) {

    this(uiFactory, swtStyle, null);
  }

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is used to do the synchonization.
   * @param swtStyle
   *        is the {@link Widget#getStyle() style} of the composite.
   * @param swtComposite
   *        is the composite to access.
   */
  public SyncCompositeAccess(UIFactorySwt uiFactory, int swtStyle, Composite swtComposite) {

    super(uiFactory, swtStyle);
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
  @Override
  public Composite getSwtObject() {

    return this.composite;
  }

}
