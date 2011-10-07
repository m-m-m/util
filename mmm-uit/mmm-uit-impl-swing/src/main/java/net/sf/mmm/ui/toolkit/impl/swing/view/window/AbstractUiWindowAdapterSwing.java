/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.window;

import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import net.sf.mmm.ui.toolkit.api.event.UiEventType;
import net.sf.mmm.ui.toolkit.api.view.window.UiWindow;
import net.sf.mmm.ui.toolkit.base.view.window.UiWindowAdapter;
import net.sf.mmm.ui.toolkit.impl.swing.view.UiNodeAdapterSwing;

/**
 * This is the abstract base implementation of the {@link UiWindowAdapter} using
 * swing. It adapts an AWT {@link Window}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * @since 1.0.0
 */
public abstract class AbstractUiWindowAdapterSwing<DELEGATE extends Window> extends
    UiNodeAdapterSwing<DELEGATE> implements UiWindowAdapter<DELEGATE>, WindowListener {

  /**
   * The constructor.
   * 
   * @param node is the owning {@link #getNode() node}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public AbstractUiWindowAdapterSwing(UiWindow node, DELEGATE delegate) {

    super(node, delegate);
  }

  /**
   * {@inheritDoc}
   */
  public void setSize(int width, int height) {

    getDelegate().setSize(width, height);
  }

  /**
   * {@inheritDoc}
   */
  public int getWidth() {

    return getDelegate().getWidth();
  }

  /**
   * {@inheritDoc}
   */
  public int getHeight() {

    return getDelegate().getHeight();
  }

  /**
   * {@inheritDoc}
   */
  public int getX() {

    return getDelegate().getX();
  }

  /**
   * {@inheritDoc}
   */
  public int getY() {

    return getDelegate().getX();
  }

  /**
   * {@inheritDoc}
   */
  public void setPosition(int x, int y) {

    getDelegate().setLocation(x, y);
  }

  /**
   * {@inheritDoc}
   */
  public void pack() {

    getDelegate().pack();
  }

  /**
   * {@inheritDoc}
   */
  public void windowOpened(WindowEvent e) {

    getNode().sendEvent(UiEventType.SHOW);
  }

  /**
   * {@inheritDoc}
   */
  public void windowClosing(WindowEvent e) {

  }

  /**
   * {@inheritDoc}
   */
  public void windowClosed(WindowEvent e) {

    // TODO Close is NOT hide
    getNode().sendEvent(UiEventType.HIDE);
  }

  /**
   * {@inheritDoc}
   */
  public void windowIconified(WindowEvent e) {

    getNode().sendEvent(UiEventType.ICONIFY);
  }

  /**
   * {@inheritDoc}
   */
  public void windowDeiconified(WindowEvent e) {

    getNode().sendEvent(UiEventType.DEICONIFY);
  }

  /**
   * {@inheritDoc}
   */
  public void windowActivated(WindowEvent e) {

    getNode().sendEvent(UiEventType.ACTIVATE);
  }

  /**
   * {@inheritDoc}
   */
  public void windowDeactivated(WindowEvent e) {

    getNode().sendEvent(UiEventType.DEACTIVATE);
  }

}
