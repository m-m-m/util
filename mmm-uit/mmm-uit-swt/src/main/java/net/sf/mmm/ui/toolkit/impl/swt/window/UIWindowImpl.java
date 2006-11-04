/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.window;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuBar;
import net.sf.mmm.ui.toolkit.api.window.MessageType;
import net.sf.mmm.ui.toolkit.base.window.AbstractUIWindow;
import net.sf.mmm.ui.toolkit.impl.swt.SwtListenerAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.menu.UIMenuBarImpl;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncShellAccess;

/**
 * This class is the implementation of the UIWindow interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIWindowImpl extends AbstractUIWindow {

  /** the ui factory */
  private final UIFactorySwt factory;

  /** the SWT shell (window) */
  private Shell shell;

  /** to access the {@link org.eclipse.swt.widgets.Shell} properties */
  private final SyncShellAccess syncAccess;

  /** the {@link #isResizeable() resizeable-status} */
  private final boolean resizeableFlag;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the
   *        {@link net.sf.mmm.ui.toolkit.api.UIObject#getFactory() factory}
   *        instance.
   * @param parent
   *        is the {@link UINode#getParent() parent} of this object (may be
   *        <code>null</code>).
   * @param defaultStyle
   *        is the default style used for the SWT shell.
   * @param modal -
   *        if <code>true</code> all windows of the application are blocked
   *        until this window is closed.
   * @param resizeable -
   *        if <code>true</code> the window will be
   *        {@link #isResizeable() resizeable}.
   */
  public UIWindowImpl(final UIFactorySwt uiFactory, final UIWindowImpl parent, int defaultStyle,
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
    final int style = defaultStyle | styleModifiers;
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
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getFactory()
   * 
   */
  @Override
  public UIFactorySwt getFactory() {

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
   * @see net.sf.mmm.ui.toolkit.base.AbstractUINode#doInitializeListener()
   * 
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
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteVisible#isVisible()
   * 
   */
  public boolean isVisible() {

    return this.syncAccess.isVisible();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteVisible#setVisible(boolean)
   * 
   */
  public void setVisible(boolean visibleFlag) {

    this.syncAccess.setVisible(visibleFlag);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIWindow#pack()
   * 
   */
  public void pack() {

    this.syncAccess.pack();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTitle#getTitle()
   * 
   */
  public String getTitle() {

    return this.syncAccess.getTitle();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTitle#setTitle(java.lang.String)
   * 
   */
  public void setTitle(String newTitle) {

    this.syncAccess.setTitel(newTitle);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWritePosition#setPosition(int,
   *      int)
   * 
   */
  public void setPosition(int x, int y) {

    this.syncAccess.setLocation(x, y);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSize#setSize(int, int)
   * 
   */
  public void setSize(final int width, final int height) {

    this.syncAccess.setSize(width, height);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadPosition#getX()
   * 
   */
  public int getX() {

    return getBounds().x;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadPosition#getY()
   * 
   */
  public int getY() {

    return getBounds().y;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSize#getWidth()
   * 
   */
  public int getWidth() {

    return getBounds().width;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSize#getHeight()
   * 
   */
  public int getHeight() {

    return getBounds().height;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposed#dispose()
   * 
   */
  public void dispose() {

    this.syncAccess.dispose();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposed#isDisposed()
   * 
   */
  public boolean isDisposed() {

    return this.syncAccess.isDisposed();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIWindow#showMessage(java.lang.String,
   *      java.lang.String, net.sf.mmm.ui.toolkit.api.window.MessageType)
   * 
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
   * @see net.sf.mmm.ui.toolkit.api.window.UIWindow#showQuestion(java.lang.String,
   *      java.lang.String)
   * 
   */
  public boolean showQuestion(String question, String title) {

    MessageBox messageBox = new MessageBox(this.shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
    messageBox.setText(title);
    messageBox.setMessage(question);
    int result = messageBox.open();
    return (result == SWT.YES);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.window.AbstractUIWindow#createMenuBar()
   * 
   */
  @Override
  protected UIMenuBar createMenuBar() {

    Menu menuBar = this.syncAccess.createMenuBar();
    return new UIMenuBarImpl(getFactory(), this, menuBar);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIWindow#createDialog(java.lang.String,
   *      boolean, boolean)
   * 
   */
  public UIDialogImpl createDialog(String title, boolean modal, boolean resizeable) {

    UIDialogImpl dialog = new UIDialogImpl(getFactory(), this, modal, resizeable);
    dialog.setTitle(title);
    return dialog;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSize#isResizeable()
   * 
   */
  public boolean isResizeable() {

    return this.resizeableFlag;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIWindow#setComposite(net.sf.mmm.ui.toolkit.api.composite.UIComposite)
   * 
   */
  public void setComposite(final UIComposite newComposite) {

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
