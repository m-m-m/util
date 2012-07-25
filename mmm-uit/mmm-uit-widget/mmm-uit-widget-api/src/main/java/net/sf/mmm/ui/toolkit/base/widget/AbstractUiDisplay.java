/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import net.sf.mmm.ui.toolkit.api.widget.UiDisplay;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiDisplay implements UiDisplay {

  /**
   * The constructor.
   */
  public AbstractUiDisplay() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getClass().getSimpleName();
  }

}
