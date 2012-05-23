/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.gwt.view;

import net.sf.mmm.ui.toolkit.base.gwt.AbstractUiFactoryGwt;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiNode;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.ui.toolkit.api.view.UiNode} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiNodeGwt extends AbstractUiNode {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiNodeGwt(AbstractUiFactoryGwt uiFactory) {

    super(uiFactory);
  }

  /**
   * This method gets the native GWT {@link Widget} of this node.
   * 
   * @return the {@link Widget}.
   */
  protected abstract Widget getNativeUiObject();

  /**
   * {@inheritDoc}
   */
  @Override
  public void setStyles(String styles) {

    getNativeUiObject().setStyleName(styles);
    super.setStyles(styles);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addStyle(String style) {

    super.addStyle(style);
    getNativeUiObject().setStyleName(style, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeStyle(String style) {

    getNativeUiObject().setStyleName(style, false);
    return super.removeStyle(style);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    // TODO: add handler adapter here...
    return super.doInitializeListener();
  }

}
