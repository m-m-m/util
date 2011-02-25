/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.window;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import net.sf.mmm.ui.toolkit.api.common.MessageType;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.base.window.AbstractUiWindow;
import net.sf.mmm.ui.toolkit.impl.swt.SwtListenerAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.menu.UIMenuBarImpl;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncShellAccess;

/**
 * This class is the implementation of the UIWindow interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UIWindowImpl extends AbstractUiWindow {

  /** the ui factory */
  private final UiFactorySwt factory;

  /** the SWT shell (window) */
  private Shell shell;

  /** to access the {@link org.eclipse.swt.widgets.Shell} properties */
  private final SyncShellAccess syncAccess;

  /** the {@link #isResizable() resizeable-status} */
  private final boolean resizeableFlag;

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   * @param parent is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiNode#getParent() parent} of this
   *        object (may be <code>null</code>).
   * @param defaultStyle is the default style used for the SWT shell.
   * @param modal - if <code>true</code> all windows of the application are
   *        blocked until this window is closed.
   * @param resizeable - if <code>true</code> the window will be
   *        {@link #isResizable() resizeable}.
   */
  public UIWindowImpl(final UiFactorySwt uiFactory, final UIWindowImpl parent, int defaultStyle,
      boolean modal, boolean resizeable) {

    super(uiFactory, parent);
    this.factory = uiFactory;
    this.resizeableFlag = resizeable;
    int styleModifiers = 0;
    if (this.resizeableFlag) {
      styleModifiers |= SWT.RESIZE;
    }
    if (modal) {
      styleModifiers |= SWT.APPLICATION_MODAL;
    }

    final int style = uiFactory.adjustStyle(defaultStyle | styleModifiers);
    uiFactory.getDisplay().invokeSynchron(new Runnable() {

      public void run() {

        if (parent == null) {
          UIWindowImpl.this.shell = new Shell(uiFactory.getDisplay().getSwtDisplay(), style);
        } else {
          UIWindowImpl.this.shell = new Shell(parent.getSwtWindow(), style);
        }
        // TODO remove this?
        UIWindowImpl.this.shell.setLayout(new FillLayout());
      };
    });
    this.syncAccess = new SyncShellAccess(uiFactory, style, this.shell);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFactorySwt getFactory() {

    return this.factory;
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

    SwtListenerAdapter listenerAdaptor = new SwtListenerAdapter(this);
    // TODO
    this.shell.addListener(SWT.Show, listenerAdaptor);
    this.shell.addListener(SWT.Hide, listenerAdaptor);
    return true;
  }

  /**
   * This method gets the current bounds of the shell.
   * 
   * @return the bounds.
   */
  protected Rectangle getBounds() {

    return this.syncAccess.getBounds();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isVisible() {

    return this.syncAccess.isVisible();
  }

  /**
   * {@inheritDoc}
   */
  public void setVisible(boolean visibleFlag) {

    this.syncAccess.setVisible(visibleFlag);
  }

  /**
   * {@inheritDoc}
   */
  public void pack() {

    this.syncAccess.pack();
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return this.syncAccess.getTitle();
  }

  /**
   * {@inheritDoc}
   */
  public void setTitle(String newTitle) {

    this.syncAccess.setTitel(newTitle);
  }

  /**
   * {@inheritDoc}
   */
  public void setPosition(int x, int y) {

    this.syncAccess.setLocation(x, y);
  }

  /**
   * {@inheritDoc}
   */
  public void setSize(final int width, final int height) {

    this.syncAccess.setSize(width, height);
  }

  /**
   * {@inheritDoc}
   */
  public int getX() {

    return getBounds().x;
  }

  /**
   * {@inheritDoc}
   */
  public int getY() {

    return getBounds().y;
  }

  /**
   * {@inheritDoc}
   */
  public int getWidth() {

    return getBounds().width;
  }

  /**
   * {@inheritDoc}
   */
  public int getHeight() {

    return getBounds().height;
  }

  /**
   * {@inheritDoc}
   */
  public void dispose() {

    super.dispose();
    this.syncAccess.dispose();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDisposed() {

    return this.syncAccess.isDisposed();
  }

  /**
   * {@inheritDoc}
   */
  public void showMessage(final String message, final String title, MessageType messageType) {

    int style = SWT.ICON_INFORMATION;
    if (messageType == MessageType.ERROR) {
      style = SWT.ICON_ERROR;
    } else if (messageType == MessageType.WARNING) {
      style = SWT.ICON_WARNING;
    } else if (messageType == MessageType.INFO) {
      style = SWT.ICON_INFORMATION;
    }
    style |= SWT.OK;
    final int swtStyle = style;
    getFactory().getDisplay().invokeSynchron(new Runnable() {

      public void run() {

        MessageBox messageBox = new MessageBox(UIWindowImpl.this.shell, swtStyle);
        messageBox.setText(title);
        messageBox.setMessage(message);
        messageBox.open();
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  public boolean showQuestion(String question, String title) {

    MessageBox messageBox = new MessageBox(this.shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
    messageBox.setText(title);
    messageBox.setMessage(question);
    int result = messageBox.open();
    return (result == SWT.YES);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UIMenuBarImpl createMenuBar() {

    Menu menuBar = this.syncAccess.createMenuBar();
    return new UIMenuBarImpl(getFactory(), this, menuBar);
  }

  /**
   * {@inheritDoc}
   */
  public UIDialogImpl createDialog(String title, boolean modal, boolean resizeable) {

    UIDialogImpl dialog = new UIDialogImpl(getFactory(), this, modal, resizeable);
    dialog.setTitle(title);
    getFactory().addWindow(dialog);
    return dialog;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isResizable() {

    return this.resizeableFlag;
  }

  /**
   * {@inheritDoc}
   */
  public void setComposite(final UiComposite newComposite) {

    registerComposite(newComposite);
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
   * This method gets synchron access on the SWT window (shell).
   * 
   * @return sync access to the sell.
   */
  public SyncShellAccess getSyncAccess() {

    return this.syncAccess;
  }

}
