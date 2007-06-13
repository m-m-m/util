/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;

/**
 * This class is used for synchronous access on SWT
 * {@link org.eclipse.swt.widgets.Shell}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncShellAccess extends AbstractSyncCompositeAccess {

  /**
   * operation to create and set the
   * {@link org.eclipse.swt.widgets.Decorations#getMenuBar() menu-bar}
   */
  protected static final String OPERATION_CREATE_MENUBAR = "createMenuBar";

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Decorations#setMaximized(boolean) maximized}
   * status.
   */
  protected static final String OPERATION_SET_MAXIMIZED = "setMaximized";

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Decorations#setMinimized(boolean) minimized}
   * status.
   */
  protected static final String OPERATION_SET_MINIMIZED = "setMinimized";

  /**
   * operation to get the
   * {@link org.eclipse.swt.widgets.Decorations#getMaximized() maximized}
   * status.
   */
  protected static final String OPERATION_GET_MAXIMIZED = "getMaximized";

  /**
   * operation to get the
   * {@link org.eclipse.swt.widgets.Decorations#getMinimized() minimized}
   * status.
   */
  protected static final String OPERATION_GET_MINIMIZED = "getMinimized";

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Decorations#setText(String) title}.
   */
  protected static final String OPERATION_SET_TITLE = "setTitle";

  /** the control to get the bounds of */
  private final Shell shell;

  /** the menu-bar */
  private Menu menuBar;

  /** the maximized status */
  private boolean maximized;

  /** the minimized status */
  private boolean minimized;

  /** the window title */
  private String title;

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param swtStyle is the
   *        {@link org.eclipse.swt.widgets.Widget#getStyle() style} of the
   *        sash-form.
   * @param swtShell is the {@link Shell} to access synchronous.
   */
  public SyncShellAccess(UIFactorySwt uiFactory, int swtStyle, Shell swtShell) {

    super(uiFactory, swtStyle);
    this.shell = swtShell;
    this.menuBar = null;
    this.maximized = false;
    this.minimized = false;
    this.title = "";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Shell getSwtObject() {

    return this.shell;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_CREATE_MENUBAR) {
      this.menuBar = this.shell.getMenuBar();
      if (this.menuBar == null) {
        this.menuBar = new Menu(this.shell, SWT.BAR);
        this.shell.setMenuBar(this.menuBar);
      }
    } else if (operation == OPERATION_SET_MAXIMIZED) {
      this.shell.setMaximized(this.maximized);
    } else if (operation == OPERATION_SET_MINIMIZED) {
      this.shell.setMinimized(this.minimized);
    } else if (operation == OPERATION_GET_MAXIMIZED) {
      this.maximized = this.shell.getMaximized();
    } else if (operation == OPERATION_GET_MINIMIZED) {
      this.minimized = this.shell.getMinimized();
    } else if (operation == OPERATION_SET_TITLE) {
      this.shell.setText(this.title);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * This method creates the
   * {@link org.eclipse.swt.widgets.Decorations#getMenuBar() menu-bar} of the
   * decorations.
   * 
   * @return the menu-bar.
   */
  public Menu createMenuBar() {

    assert (checkReady());
    invoke(OPERATION_CREATE_MENUBAR);
    return this.menuBar;
  }

  /**
   * This method set the
   * {@link org.eclipse.swt.widgets.Decorations#setText(String) title} of the
   * decorations.
   * 
   * @param newTitel is the new title to set.
   */
  public void setTitel(String newTitel) {

    assert (checkReady());
    this.title = newTitel;
    invoke(OPERATION_SET_TITLE);
  }

  /**
   * This method get the
   * {@link org.eclipse.swt.widgets.Decorations#getText() title} of the
   * decorations.
   * 
   * @return the title.
   */
  public String getTitle() {

    // title can NOT be modified externally (e.g. by user-interaction).
    return this.title;
  }

  /**
   * This method sets the
   * {@link org.eclipse.swt.widgets.Decorations#setMaximized(boolean) maximized-state}
   * of the decorations.
   * 
   * @param doMaximize is the new maximized-state to set.
   */
  public void setMaximized(boolean doMaximize) {

    assert (checkReady());
    this.maximized = doMaximize;
    invoke(OPERATION_SET_MAXIMIZED);
  }

  /**
   * This method gets the
   * {@link org.eclipse.swt.widgets.Decorations#getMaximized() maximized-state}
   * of the decorations.
   * 
   * @return the maximized-state.
   */
  public boolean getMaximized() {

    assert (checkReady());
    invoke(OPERATION_GET_MAXIMIZED);
    return this.maximized;
  }

  /**
   * This method sets the
   * {@link org.eclipse.swt.widgets.Decorations#setMinimized(boolean) minimized-state}
   * of the decorations.
   * 
   * @param doMinimize is the new minimized-state to set.
   */
  public void setMinimized(boolean doMinimize) {

    assert (checkReady());
    this.minimized = doMinimize;
    invoke(OPERATION_SET_MINIMIZED);
  }

  /**
   * This method gets the
   * {@link org.eclipse.swt.widgets.Decorations#getMinimized() minimized-state}
   * of the decorations.
   * 
   * @return the minimized-state.
   */
  public boolean getMinimized() {

    assert (checkReady());
    invoke(OPERATION_GET_MINIMIZED);
    return this.minimized;
  }

}
