/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view.menu;

import net.sf.mmm.ui.toolkit.api.view.menu.UiMenuItem;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiNode;

/**
 * This is the abstract base implementation of {@link UiMenuItem}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type for the {@link #getAdapter() delegate}.
 * @since 1.0.0
 */
public abstract class AbstractUiMenuItem<DELEGATE> extends AbstractUiNode<DELEGATE> implements UiMenuItem {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiMenuItem(AbstractUiFactory uiFactory) {

    super(uiFactory);
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

}
