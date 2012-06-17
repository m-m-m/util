/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.window;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.base.view.window.AbstractUiWindow;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncShellAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;

/**
 * This class is the implementation of the UIWindow interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWindowSwt extends AbstractUiWindow<Shell> {

  /** the SWT shell (window) */
  private Shell shell;

  /** @see #getAdapter() */
  private final SyncShellAccess adapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   * @param parent is the
   *        {@link net.sf.mmm.ui.toolkit.api.view.UiNode#getParent() parent} of
   *        this object (may be <code>null</code>).
   * @param defaultStyle is the default style used for the SWT shell.
   * @param modal - if <code>true</code> all windows of the application are
   *        blocked until this window is closed.
   * @param resizable - if <code>true</code> the window will be
   *        {@link #isResizable() resizable}.
   */
  public AbstractUiWindowSwt(final UiFactorySwt uiFactory, final AbstractUiWindowSwt parent,
      int defaultStyle, boolean modal, boolean resizable) {

    super(uiFactory);
    int styleModifiers = 0;
    if (resizable) {
      styleModifiers |= SWT.RESIZE;
    }
    if (modal) {
      styleModifiers |= SWT.APPLICATION_MODAL;
    }

    final int style = uiFactory.adjustStyle(defaultStyle | styleModifiers);
    uiFactory.getDisplay().invokeSynchron(new Runnable() {

      public void run() {

        if (parent == null) {
          AbstractUiWindowSwt.this.shell = new Shell(uiFactory.getDisplay().getSwtDisplay(), style);
        } else {
          AbstractUiWindowSwt.this.shell = new Shell(parent.getSwtWindow(), style);
        }
        // TODO remove this?
        AbstractUiWindowSwt.this.shell.setLayout(new FillLayout());
      }
    });
    this.adapter = new SyncShellAccess(uiFactory, this, style, this.shell);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFactorySwt getFactory() {

    return (UiFactorySwt) super.getFactory();
  }

  /**
   * This method gets the unwrapped SWT shell (window object).
   * 
   * @return the unwrapped window.
   */
  public Shell getSwtWindow() {

    return this.shell;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    // TODO
    this.shell.addListener(SWT.Show, getAdapter());
    this.shell.addListener(SWT.Hide, getAdapter());
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public void setComposite(UiComposite<? extends UiElement> newComposite) {

    doSetComposite(newComposite);
    /*
     * final Control c = ((AbstractUIComposite)
     * newComposite).getSyncAccess().getWidget();
     * getFactory().invokeSynchron(new Runnable() {
     * 
     * public void run() {
     * 
     * c.setParent(getSwtWindow()); } });
     */
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncShellAccess getAdapter() {

    return this.adapter;
  }

}
