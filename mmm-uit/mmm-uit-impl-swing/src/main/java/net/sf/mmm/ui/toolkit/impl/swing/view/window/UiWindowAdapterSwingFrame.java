/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.window;

import javax.swing.JFrame;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.base.view.window.UiWindowAdapter} using swing.
 * It adapts an {@link JFrame}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWindowAdapterSwingFrame extends AbstractUiWindowAdapterSwing<JFrame> {

  /**
   * The constructor.
   * 
   * @param node is the owning {@link #getNode() node}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiWindowAdapterSwingFrame(UiFrameImpl node, JFrame delegate) {

    super(node, delegate);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isResizable() {

    return getDelegate().isResizable();
  }

  /**
   * {@inheritDoc}
   */
  public void setTitle(String title) {

    getDelegate().setTitle(title);
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return getDelegate().getTitle();
  }

}
