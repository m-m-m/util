/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.view.widget.UiProgressBar;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.custom.MyProgressBar;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiProgressBar} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIProgressBarImpl extends AbstractUiWidget implements UiProgressBar {

  /** the native swing widget */
  private final JProgressBar progressBar;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param orientation is the {@link #getOrientation() orientation}.
   */
  public UIProgressBarImpl(UIFactorySwing uiFactory, Orientation orientation) {

    super(uiFactory);
    this.progressBar = new MyProgressBar(orientation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JComponent getSwingComponent() {

    return this.progressBar;
  }

  /**
   * {@inheritDoc}
   */
  public int getProgress() {

    return this.progressBar.getValue();
  }

  /**
   * {@inheritDoc}
   */
  public void setProgress(int newProgress) {

    this.progressBar.setValue(newProgress);
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

    if (this.progressBar.getOrientation() == SwingConstants.HORIZONTAL) {
      return Orientation.HORIZONTAL;
    } else {
      return Orientation.VERTICAL;
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isIndeterminate() {

    return this.progressBar.isIndeterminate();
  }

}
