/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.view;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.base.gwt.AbstractUiFactoryGwt;
import net.sf.mmm.ui.toolkit.base.gwt.view.AbstractUiNodeGwt;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the abstract base implementation of {@link UiElement} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiElement extends AbstractUiNodeGwt implements UiElement {

  /** @see #getTooltip() */
  private String tooltip;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiElement(AbstractUiFactoryGwt uiFactory) {

    super(uiFactory);
  }

  /**
   * {@inheritDoc}
   */
  public void setTooltip(String tooltip) {

    this.tooltip = tooltip;
    getNativeUiObject().setTitle(tooltip);
  }

  /**
   * {@inheritDoc}
   */
  public String getTooltip() {

    return this.tooltip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetEnabled(boolean enabled) {

    // TODO ...
    super.doSetEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  public void setSize(int width, int height) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  public boolean isResizable() {

    // TODO Auto-generated method stub
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public int getWidth() {

    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public int getHeight() {

    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredWidth() {

    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredHeight() {

    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDisposed() {

    // TODO Auto-generated method stub
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget getNativeUiObject() {

    // TODO Auto-generated method stub
    return null;
  }

}
