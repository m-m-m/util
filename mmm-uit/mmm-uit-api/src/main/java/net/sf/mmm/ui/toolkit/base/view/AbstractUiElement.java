/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;

/**
 * This is the abstract base implementation of
 * {@link net.sf.mmm.ui.toolkit.api.view.UiElement}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiElement extends AbstractUiNode implements UiElement {

  /** @see #getTooltip() */
  private String tooltip;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiElement(AbstractUiFactory uiFactory) {

    super(uiFactory);
  }

  /**
   * {@inheritDoc}
   */
  public void setTooltip(String tooltip) {

    this.tooltip = tooltip;
  }

  protected abstract void doSetTooltip(String tooltip);

  /**
   * {@inheritDoc}
   */
  public String getTooltip() {

    return this.tooltip;
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

}
