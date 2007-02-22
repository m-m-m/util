/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.awt;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.window.UIDialog;
import net.sf.mmm.ui.toolkit.base.window.AbstractUIWindow;

/**
 * This class is the implementation of the UIWindow interface using AWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIWindowImpl extends AbstractUIWindow {

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the
   *        {@link net.sf.mmm.ui.toolkit.api.UIObject#getFactory() factory}
   *        instance.
   * @param parentObject
   *        is the {@link net.sf.mmm.ui.toolkit.api.UINode#getParent() parent}
   *        that created this object. It may be <code>null</code>.
   */
  public UIWindowImpl(UIFactory uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
  }

  /**
   * This method gets the unwrapped window.
   * 
   * @return the unwrapped window object.
   */
  protected abstract Window getAwtWindow();

  /**
   * @see net.sf.mmm.ui.toolkit.base.AbstractUINode#doInitializeListener()
   */
  @Override
  protected boolean doInitializeListener() {

    WindowListener listener = new WindowListener() {

      /**
       * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
       */
      public void windowActivated(WindowEvent e) {

        invoke(ActionType.ACTIVATE);
      }

      /**
       * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
       */
      public void windowClosed(WindowEvent e) {

        invoke(ActionType.HIDE);
      }

      /**
       * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
       */
      public void windowClosing(WindowEvent e) {

      // currently ignored...
      }

      /**
       * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
       */
      public void windowDeactivated(WindowEvent e) {

        invoke(ActionType.DEACTIVATE);
      }

      /**
       * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
       */
      public void windowDeiconified(WindowEvent e) {

        invoke(ActionType.DEICONIFY);
      }

      /**
       * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
       */
      public void windowIconified(WindowEvent e) {

        invoke(ActionType.DEACTIVATE);
      }

      /**
       * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
       */
      public void windowOpened(WindowEvent e) {

        invoke(ActionType.SHOW);
      }

    };
    getAwtWindow().addWindowListener(listener);
    return true;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteVisible#isVisible()
   */
  public boolean isVisible() {

    return getAwtWindow().isVisible();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteVisible#setVisible(boolean)
   */
  public void setVisible(boolean visible) {

    getAwtWindow().setVisible(visible);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIWindow#pack()
   */
  public void pack() {

    getAwtWindow().pack();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWritePosition#setPosition(int,
   *      int)
   */
  public void setPosition(int x, int y) {

    getAwtWindow().setLocation(x, y);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSize#setSize(int, int)
   */
  public void setSize(int width, int height) {

    getAwtWindow().setSize(width, height);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadPosition#getX()
   */
  public int getX() {

    return getAwtWindow().getX();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadPosition#getY()
   */
  public int getY() {

    return getAwtWindow().getY();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSize#getWidth()
   */
  public int getWidth() {

    return getAwtWindow().getWidth();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSize#getHeight()
   */
  public int getHeight() {

    return getAwtWindow().getHeight();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposed#dispose()
   */
  public void dispose() {

    getAwtWindow().dispose();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposed#isDisposed()
   */
  public boolean isDisposed() {

    // TODO this is not corret in all situations, is it?
    return getAwtWindow().isDisplayable();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSize#isResizeable()
   */
  public boolean isResizeable() {

    if (getType() == UIDialog.TYPE) {
      return ((Dialog) getAwtWindow()).isResizable();
    } else {
      return ((Frame) getAwtWindow()).isResizable();
    }
  }

}
