/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view;

import net.sf.mmm.ui.toolkit.api.attribute.UiReadPosition;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.composite.AbstractUiComposite;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.AbstractSyncCompositeAccess;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.view.window.AbstractUiWindowSwt;

import org.eclipse.swt.SWT;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.UiElement} interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiElement extends UiSwtNode implements UiElement, UiReadPosition {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiElement(UiFactorySwt uiFactory) {

    super(uiFactory);
  }

  /**
   * This method gets synchronous access on the SWT control.
   * 
   * @return the synchronous access.
   */
  public abstract AbstractSyncControlAccess getSyncAccess();

  /**
   * This method gets synchronous access on the control that represents the
   * active part of this component. This method is used by methods such as
   * {@link #setEnabled(boolean)} and {@link #setTooltip(String)}. It can be
   * overridden if the implemented component is build out of multiple SWT
   * controls and the top ancestor is not the active control (e.g. composite may
   * have an SWT group as top ancestor that represents a titled border).
   * 
   * @return the active unwrapped SWT control.
   */
  public AbstractSyncControlAccess getActiveSyncAccess() {

    return getSyncAccess();
  }

  /**
   * {@inheritDoc}
   */
  public String getTooltip() {

    return getActiveSyncAccess().getTooltip();
  }

  /**
   * {@inheritDoc}
   */
  public void setTooltip(String tooltip) {

    getActiveSyncAccess().setTooltip(tooltip);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setParent(UiNode newParent) {

    if (newParent == null) {
      System.out.println("AbstractUiElement: This should be kicked out!");
      // getSwtControl().setParent(getFactory().getDummyParent());
    } else {
      AbstractSyncCompositeAccess parentAccess;
      boolean autoAttach = true;
      if (newParent instanceof AbstractUiComposite) {
        @SuppressWarnings("unchecked")
        AbstractUiComposite<? extends UiElement> parentComposite = (AbstractUiComposite<? extends UiElement>) newParent;
        parentAccess = parentComposite.getActiveSyncAccess();
        autoAttach = parentComposite.isAttachToActiveAccess();
      } else {
        parentAccess = ((AbstractUiWindowSwt) newParent).getSyncAccess();
      }
      if (autoAttach) {
        getSyncAccess().setParentAccess(parentAccess);
      }
      if ((getSyncAccess().getSwtObject() == null) && (parentAccess.getSwtObject() != null)) {
        create();
      }
    }
    super.setParent(newParent);
    update();
  }

  /**
   * This method removes this component from its {@link #getParent() parent}.
   */
  public void removeFromParent() {

    UiNode parent = getParent();
    if (parent != null) {
      setParent(null);
      throw new IllegalArgumentException("Currently unsupported!");
    }
  }

  /**
   * This method creates the SWT control of this component.
   */
  public void create() {

    /*
     * if (getSyncAccess().getSwtObject() == null) { getSyncAccess().create();
     * if (getActiveSyncAccess().getSwtObject() == null) {
     * getActiveSyncAccess().create(); } }
     */
    getActiveSyncAccess().create();
  }

  /**
   * 
   */
  public void update() {

    if (getParent() instanceof AbstractUiElement) {
      AbstractUiElement p = (AbstractUiElement) getParent();
      p.update();
    }
    // nothing to do so far
  }

  /**
   * {@inheritDoc}
   */
  public void setEnabled(boolean enabled) {

    getActiveSyncAccess().setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEnabled() {

    return getActiveSyncAccess().isEnabled();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetVisible(boolean visible) {

    getSyncAccess().setVisible(visible);
  }

  /**
   * {@inheritDoc}
   */
  public int getHeight() {

    return getSyncAccess().getBounds().height;
  }

  /**
   * {@inheritDoc}
   */
  public int getWidth() {

    return getSyncAccess().getBounds().width;
  }

  /**
   * {@inheritDoc}
   */
  public int getX() {

    return getSyncAccess().getBounds().x;
  }

  /**
   * {@inheritDoc}
   */
  public int getY() {

    return getSyncAccess().getBounds().y;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isResizable() {

    // TODO
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public void setSize(int width, int height) {

    if (isResizable()) {

      getSyncAccess().setSize(width, height);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setPosition(int x, int y) {

    getSyncAccess().setLocation(x, y);
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredHeight() {

    return getSyncAccess().computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredWidth() {

    return getSyncAccess().computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
  }

  /**
   * {@inheritDoc}
   */
  public void dispose() {

    getSyncAccess().dispose();
    if (!getActiveSyncAccess().isDisposed()) {
      // should never happen but just to get sure...
      getActiveSyncAccess().dispose();
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDisposed() {

    return getSyncAccess().isDisposed();
  }

}
