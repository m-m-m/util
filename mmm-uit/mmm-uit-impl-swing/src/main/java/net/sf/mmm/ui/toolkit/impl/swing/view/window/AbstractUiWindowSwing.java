/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.window;

import java.awt.Window;

import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.base.view.window.AbstractUiWindow;

/**
 * This class is the implementation of the UIWindow interface using AWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type for the {@link #getAdapter() delegate}.
 * @since 1.0.0
 */
public abstract class AbstractUiWindowSwing<DELEGATE extends Window> extends
    AbstractUiWindow<DELEGATE> {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiWindowSwing(AbstractUiFactory uiFactory) {

    super(uiFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract AbstractUiWindowAdapterSwing<DELEGATE> getAdapter();

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    getDelegate().addWindowListener(getAdapter());
    return true;
  }

}
