/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.view.widget.UiProgressBar;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.custom.MyProgressBar;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiProgressBar} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiProgressBarImpl extends AbstractUiWidgetSwing<JProgressBar> implements UiProgressBar {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param orientation is the {@link #getOrientation() orientation}.
   */
  public UiProgressBarImpl(UiFactorySwing uiFactory, Orientation orientation) {

    super(uiFactory, new MyProgressBar(orientation));
  }

  /**
   * {@inheritDoc}
   */
  public int getProgress() {

    return getAdapter().getDelegate().getValue();
  }

  /**
   * {@inheritDoc}
   */
  public void setProgress(int newProgress) {

    getDelegate().setValue(newProgress);
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public Orientation getOrientation() {

    if (getDelegate().getOrientation() == SwingConstants.HORIZONTAL) {
      return Orientation.HORIZONTAL;
    } else {
      return Orientation.VERTICAL;
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isIndeterminate() {

    return getDelegate().isIndeterminate();
  }

}
