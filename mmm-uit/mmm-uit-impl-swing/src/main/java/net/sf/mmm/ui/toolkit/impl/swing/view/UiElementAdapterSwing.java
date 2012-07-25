/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view;

import javax.swing.JComponent;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.base.view.UiElementAdapter;

/**
 * This is the abstract base implementation of the {@link net.sf.mmm.ui.toolkit.base.view.UiElementAdapter}
 * using swing. It adapts a {@link JComponent}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * @since 1.0.0
 */
public class UiElementAdapterSwing<DELEGATE extends JComponent> extends UiNodeAdapterSwing<DELEGATE> implements
    UiElementAdapter<DELEGATE> {

  /**
   * The constructor.
   * 
   * @param node is the owning {@link #getNode() node}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiElementAdapterSwing(UiElement node, DELEGATE delegate) {

    super(node, delegate);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTooltip(String tooltip) {

    getDelegate().setToolTipText(tooltip);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTooltip() {

    return getDelegate().getToolTipText();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSizeInPixel(int width, int height) {

    getToplevelDelegate().setSize(width, height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInPixel(int height) {

    getToplevelDelegate().setSize(getWidthInPixel(), height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidthInPixel(int width) {

    getToplevelDelegate().setSize(width, getHeightInPixel());
  }

  /**
   * {@inheritDoc}
   */
  public boolean isResizable() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getWidthInPixel() {

    return getToplevelDelegate().getWidth();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInPixel() {

    return getToplevelDelegate().getHeight();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JComponent getToplevelDelegate() {

    return getDelegate();
  }

}
