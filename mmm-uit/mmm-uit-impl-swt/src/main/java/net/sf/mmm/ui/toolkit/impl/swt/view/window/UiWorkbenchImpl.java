/* $Id: UIWorkbenchImpl.java 971 2011-02-28 21:55:00Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.window;

import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.api.view.window.UiWorkbench;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;

/**
 * This is the interface of the
 * {@link net.sf.mmm.ui.toolkit.api.view.window.UiWorkbench} interface using SWT
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWorkbenchImpl extends UiFrameImpl implements UiWorkbench {

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   */
  public UiWorkbenchImpl(UiFactorySwt uiFactory) {

    super(uiFactory, null, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return UiWorkbench.TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFrame createFrame(String title, boolean resizable) {

    // TODO
    return super.createFrame(title, resizable);
  }

}
