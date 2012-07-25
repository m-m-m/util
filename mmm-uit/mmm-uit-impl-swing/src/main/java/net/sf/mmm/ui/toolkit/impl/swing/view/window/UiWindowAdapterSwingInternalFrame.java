/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.window;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import net.sf.mmm.ui.toolkit.api.event.UiEventType;
import net.sf.mmm.ui.toolkit.base.view.window.AbstractUiWindow;
import net.sf.mmm.ui.toolkit.base.view.window.UiWindowAdapter;
import net.sf.mmm.ui.toolkit.impl.swing.view.UiNodeAdapterSwing;

/**
 * This is the implementation of the {@link net.sf.mmm.ui.toolkit.base.view.window.UiWindowAdapter} using
 * swing. It adapts an {@link JInternalFrame}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWindowAdapterSwingInternalFrame extends UiNodeAdapterSwing<JInternalFrame> implements
    UiWindowAdapter<JInternalFrame>, InternalFrameListener {

  /**
   * The constructor.
   * 
   * @param node is the owning {@link #getNode() node}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiWindowAdapterSwingInternalFrame(AbstractUiWindow<JInternalFrame> node, JInternalFrame delegate) {

    super(node, delegate);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    getDelegate().setTitle(title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return getDelegate().getTitle();
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
  public int getPositionX() {

    return getDelegate().getX();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPositionY() {

    return getDelegate().getY();
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
  public boolean isResizable() {

    return getDelegate().isResizable();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setResizable(boolean resizable) {

    getDelegate().setResizable(resizable);
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
  public void internalFrameOpened(InternalFrameEvent e) {

    getNode().sendEvent(UiEventType.SHOW);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void internalFrameClosing(InternalFrameEvent e) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void internalFrameClosed(InternalFrameEvent e) {

    // TODO: add close
    getNode().sendEvent(UiEventType.HIDE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void internalFrameIconified(InternalFrameEvent e) {

    getNode().sendEvent(UiEventType.ICONIFY);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void internalFrameDeiconified(InternalFrameEvent e) {

    getNode().sendEvent(UiEventType.DEICONIFY);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void internalFrameActivated(InternalFrameEvent e) {

    getNode().sendEvent(UiEventType.ACTIVATE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void internalFrameDeactivated(InternalFrameEvent e) {

    getNode().sendEvent(UiEventType.DEACTIVATE);
  }

}
