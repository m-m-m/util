/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;

/**
 * This is the abstract base implementation of {@link UiElement}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type for the {@link #getAdapter() adapter}.
 * @since 1.0.0
 */
public abstract class AbstractUiElement<DELEGATE> extends AbstractUiNode<DELEGATE> implements UiElement {

  /** @see #getTooltip() */
  private String tooltip;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiElement(AbstractUiFactory uiFactory) {

    super(uiFactory);
    this.tooltip = "";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract UiElementAdapter<DELEGATE> getAdapter();

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTooltip(String tooltip) {

    getAdapter().setTooltip(tooltip);
    this.tooltip = tooltip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTooltip() {

    return this.tooltip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getWidthInPixel() {

    return getAdapter().getWidthInPixel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInPixel() {

    return getAdapter().getHeightInPixel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSizeInPixel(int width, int height) {

    getAdapter().setSizeInPixel(width, height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPrimaryStyle(String primaryStyle) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPrimaryStyle() {

    // TODO Auto-generated method stub
    return null;
  }

}
