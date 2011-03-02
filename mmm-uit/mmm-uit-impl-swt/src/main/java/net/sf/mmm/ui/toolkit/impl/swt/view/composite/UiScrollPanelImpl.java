/* $Id: UIScrollPanelImpl.java 962 2011-02-24 23:15:27Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.composite;

import net.sf.mmm.ui.toolkit.api.common.ScrollbarVisibility;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.impl.swt.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.UiSwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncScrolledCompositeAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel} interface
 * using SWT as the UI toolkit.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiScrollPanelImpl<E extends AbstractUiElement> extends AbstractUiSingleComposite<E>
    implements UiScrollPanel<E> {

  /** @see #getSyncAccess() */
  private final SyncScrolledCompositeAccess syncAccess;

  /** @see #getHorizontalScrollbarVisibility() */
  private ScrollbarVisibility horizontalScrollbarVisibility;

  /** @see #getVerticalScrollbarVisibility() */
  private ScrollbarVisibility verticalScrollbarVisibility;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the {@link #getParent() parent} of this object (may
   *        be <code>null</code>).
   */
  public UiScrollPanelImpl(UiFactorySwt uiFactory, UiSwtNode parentObject) {

    super(uiFactory, parentObject);
    int style = SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;
    this.syncAccess = new SyncScrolledCompositeAccess(uiFactory, style);
  }

  /**
   * {@inheritDoc}
   */
  public ScrollbarVisibility getHorizontalScrollbarVisibility() {

    return this.horizontalScrollbarVisibility;
  }

  /**
   * {@inheritDoc}
   */
  public ScrollbarVisibility getVerticalScrollbarVisibility() {

    return this.verticalScrollbarVisibility;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChild(E child) {

    super.setChild(child);
    if (child != null) {
      child.setParent(this);
      Control childControl = child.getSyncAccess().getSwtObject();
      if (childControl != null) {
        this.syncAccess.setContent(childControl);
        update();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void create() {

    super.create();
    E child = getChild();
    if (child != null) {
      this.syncAccess.setContent(child.getSyncAccess().getSwtObject());
      update();
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * This method updates the required size.
   */
  @Override
  public void update() {

    E child = getChild();
    if ((child != null) && (child.getSyncAccess().getSwtObject() != null)) {
      Point size = child.getSyncAccess().computeSize(SWT.DEFAULT, SWT.DEFAULT);
      this.syncAccess.setMinimumSize(size);
    }
    super.update();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncScrolledCompositeAccess getActiveSyncAccess() {

    return this.syncAccess;
  }
}
