/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.smartgwt.view.window;

import net.sf.mmm.ui.toolkit.base.gwt.view.window.AbstractUiWorkbenchGwt;
import net.sf.mmm.ui.toolkit.base.view.menu.AbstractUiMenuBar;
import net.sf.mmm.ui.toolkit.impl.smartgwt.UiFactorySmartGwt;

/**
 * This is the implementation of
 * {@link net.sf.mmm.ui.toolkit.api.view.window.UiWorkbench} using SmartGWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWorkbenchImpl extends AbstractUiWorkbenchGwt {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UiWorkbenchImpl(UiFactorySmartGwt uiFactory) {

    super(uiFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractUiMenuBar createMenuBar() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doIsInvisible() {

    // TODO Auto-generated method stub
    return false;
  }

}
