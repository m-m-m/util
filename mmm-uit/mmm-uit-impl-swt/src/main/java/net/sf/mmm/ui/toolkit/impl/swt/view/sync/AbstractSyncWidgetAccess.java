/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.sync;

import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;

import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

/**
 * This is the abstract base class used for synchronous access on a SWT
 * {@link org.eclipse.swt.widgets.Widget}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * @since 1.0.0
 */
public abstract class AbstractSyncWidgetAccess<DELEGATE extends Widget> extends
    AbstractSyncObjectAccess<DELEGATE> {

  /**
   * operation to
   * {@link org.eclipse.swt.widgets.Widget#addListener(int, org.eclipse.swt.widgets.Listener)
   * add} a listener to the widget.
   */
  protected static final String OPERATION_ADD_LISTENER = "addListener";

  /**
   * operation to set the {@link #setEnabled(boolean) enabled flag}.
   */
  protected static final String OPERATION_SET_ENABLED = "setEnabled";

  /**
   * operation to set the {@link #setVisible(boolean) visible flag}.
   */
  protected static final String OPERATION_SET_VISIBLE = "setVisible";

  /** the event type for the listener to add */
  private int eventType;

  /** the listener to add */
  private Listener listener;

  /** @see #isEnabled() */
  private boolean enabled;

  /** @see #isVisible() */
  private boolean visible;

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param node is the owning {@link #getNode() node}.
   * @param swtStyle is the {@link Widget#getStyle() style} of the widget.
   */
  public AbstractSyncWidgetAccess(UiFactorySwt uiFactory, UiNode node, int swtStyle) {

    super(uiFactory, node, swtStyle);
    this.eventType = 0;
    this.listener = null;
    this.visible = true;
    this.enabled = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean isDisposedSynchron() {

    return getDelegate().isDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void disposeSynchron() {

    getDelegate().dispose();
    super.disposeSynchron();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_ADD_LISTENER) {
      getDelegate().addListener(this.eventType, this.listener);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * This method is called for the create operation.
   */
  @Override
  protected void createSynchron() {

    if (this.listener != null) {
      getDelegate().addListener(this.eventType, this.listener);
    }
  }

  /**
   * This method
   * {@link org.eclipse.swt.widgets.Widget#addListener(int, org.eclipse.swt.widgets.Listener)
   * adds} a listener to the widget.<br>
   * ATTENTION: This implementation expects that this method is NOT called more
   * than once before {@link #create() creation} is performed.
   * 
   * @param type is the event type to listen to.
   * @param handler is the handler that will receive the events.
   */
  public void addListener(int type, Listener handler) {

    assert (checkReady());
    this.eventType = type;
    this.listener = handler;
    invoke(OPERATION_ADD_LISTENER);
  }

  /**
   * {@inheritDoc}
   */
  public Widget getToplevelDelegate() {

    return getDelegate();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEnabled() {

    return this.enabled;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    assert (checkReady());
    this.enabled = enabled;
    invoke(OPERATION_SET_ENABLED);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isVisible() {

    return this.visible;
  }

  /**
   * This method gets the visible flag as set by {@link #setVisible(boolean)}.
   * Unlike {@link #isVisible()} that may be overridden it will not invoke
   * synchronous determination of the controls visibility.
   * 
   * @see #isVisible()
   * 
   * @return the visible flag as set by {@link #setVisible(boolean)}.
   */
  protected final boolean doIsVisible() {

    return this.visible;
  }

  /**
   * {@inheritDoc}
   */
  public void setVisible(boolean visible) {

    assert (checkReady());
    this.visible = visible;
    invoke(OPERATION_SET_VISIBLE);
  }

  /**
   * This method sets the raw visible flag.
   * 
   * @param newVisible - the {@link #isVisible() visible flag}.
   */
  protected void doSetVisible(boolean newVisible) {

    this.visible = newVisible;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void handleDisposed() {

    super.handleDisposed();
    this.visible = false;
    this.enabled = false;
  }

}
