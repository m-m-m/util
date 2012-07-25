/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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
 * This is the abstract base implementation of the {@link UiWindowAdapter} using swing. It adapts an AWT
 * {@link Window}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * @since 1.0.0
 */
public abstract class AbstractUiWindowAdapterSwing<DELEGATE extends Window> extends UiNodeAdapterSwing<DELEGATE>
    implements UiWindowAdapter<DELEGATE>, WindowListener {

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
  @Override
  public void setSizeInPixel(int width, int height) {

    getDelegate().setSize(width, height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidthInPixel(int width) {

    getDelegate().setSize(width, getHeightInPixel());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInPixel(int height) {

    getDelegate().setSize(getWidthInPixel(), height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getWidthInPixel() {

    return getDelegate().getWidth();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInPixel() {

    return getDelegate().getHeight();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPositionX() {

    return getDelegate().getX();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPositionY() {

    return getDelegate().getX();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPosition(int x, int y) {

    getDelegate().setLocation(x, y);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void pack() {

    getDelegate().pack();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void windowOpened(WindowEvent e) {

    getNode().sendEvent(UiEventType.SHOW);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void windowClosing(WindowEvent e) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void windowClosed(WindowEvent e) {

    // TODO Close is NOT hide
    getNode().sendEvent(UiEventType.HIDE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void windowIconified(WindowEvent e) {

    getNode().sendEvent(UiEventType.ICONIFY);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void windowDeiconified(WindowEvent e) {

    getNode().sendEvent(UiEventType.DEICONIFY);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void windowActivated(WindowEvent e) {

    getNode().sendEvent(UiEventType.ACTIVATE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void windowDeactivated(WindowEvent e) {

    getNode().sendEvent(UiEventType.DEACTIVATE);
  }

}
