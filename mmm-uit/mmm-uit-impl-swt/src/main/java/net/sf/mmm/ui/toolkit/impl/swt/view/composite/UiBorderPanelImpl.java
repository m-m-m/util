/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.composite;

import net.sf.mmm.ui.toolkit.api.view.composite.UiBorderPanel;
import net.sf.mmm.ui.toolkit.impl.swt.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.UiSwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncGroupAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiBorderPanel} interface
 * using SWT as the UI toolkit.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiBorderPanelImpl<E extends AbstractUiElement> extends AbstractUiSingleComposite<E>
    implements UiBorderPanel<E> {

  /** @see #getSyncAccess() */
  private final SyncGroupAccess syncAccess;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the {@link #getParent() parent} of this object (may
   *        be <code>null</code>).
   */
  public UiBorderPanelImpl(UiFactorySwt uiFactory, UiSwtNode parentObject) {

    super(uiFactory, parentObject);
    this.syncAccess = new SyncGroupAccess(uiFactory, SWT.NONE);
    this.syncAccess.setLayout(new FillLayout());
    // this.syncGroupAccess.setText(borderTitle);
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return this.syncAccess.getText();
  }

  /**
   * {@inheritDoc}
   */
  public void setTitle(String title) {

    this.syncAccess.setText(title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChild(E child) {

    super.setChild(child);
    if (child != null) {
      child.setParent(this);
      // Control childControl = child.getSyncAccess().getSwtObject();
      // if (childControl != null) {
      // this.syncAccess.setContent(childControl);
      // update();
      // }
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncGroupAccess getActiveSyncAccess() {

    return this.syncAccess;
  }
}
