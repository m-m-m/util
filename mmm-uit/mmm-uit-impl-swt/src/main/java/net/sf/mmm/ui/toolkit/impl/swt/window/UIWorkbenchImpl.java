/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.window;

import net.sf.mmm.ui.toolkit.api.window.UIWorkbench;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;

/**
 * This is the interface of the
 * {@link net.sf.mmm.ui.toolkit.api.window.UIWorkbench} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIWorkbenchImpl extends UIFrameImpl implements UIWorkbench {

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   */
  public UIWorkbenchImpl(UIFactorySwt uiFactory) {

    super(uiFactory, null, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return UIWorkbench.TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UIFrameImpl createFrame(String title, boolean resizeable) {

    // TODO
    return super.createFrame(title, resizeable);
  }

}
