/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.composite;

import net.sf.mmm.ui.toolkit.api.common.ScrollbarPolicy;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncScrolledCompositeAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Control;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel} interface
 * using SWT as the UI toolkit.
 * 
 * @param <CHILD> is the generic type of the {@link #getChild(int)
 *        child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiScrollPanelImpl<CHILD extends AbstractUiElement<? extends Control>> extends
    AbstractUiSingleCompositeSwt<ScrolledComposite, CHILD> implements UiScrollPanel<CHILD> {

  /** @see #getAdapter() */
  private final SyncScrolledCompositeAccess adapter;

  /** @see #getHorizontalScrollbarPolicy() */
  private ScrollbarPolicy horizontalScrollbarPolicy;

  /** @see #getVerticalScrollbarPolicy() */
  private ScrollbarPolicy verticalScrollbarPolicy;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UiScrollPanelImpl(UiFactorySwt uiFactory) {

    super(uiFactory);
    int style = SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;
    this.adapter = new SyncScrolledCompositeAccess(uiFactory, this, style);
  }

  /**
   * {@inheritDoc}
   */
  public ScrollbarPolicy getHorizontalScrollbarPolicy() {

    return this.horizontalScrollbarPolicy;
  }

  /**
   * {@inheritDoc}
   */
  public ScrollbarPolicy getVerticalScrollbarPolicy() {

    return this.verticalScrollbarPolicy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChild(CHILD child) {

    super.setChild(child);
    if (child != null) {
      Control childControl = child.getAdapter().getDelegate();
      if (childControl != null) {
        this.adapter.setContent(childControl);
      }
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
  public SyncScrolledCompositeAccess getAdapter() {

    return this.adapter;
  }
}
