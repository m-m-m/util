/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import javax.swing.JComponent;
import javax.swing.JProgressBar;

import net.sf.mmm.ui.toolkit.api.UINodeRenamed;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.widget.UIProgressBar;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.custom.MyProgressBar;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIProgressBar} interface using Swing
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIProgressBarImpl extends AbstractUIWidget implements UIProgressBar {

  /** the native swing widget */
  private final JProgressBar progressBar;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   * @param orientation
   */
  public UIProgressBarImpl(UIFactorySwing uiFactory, UINodeRenamed parentObject, Orientation orientation) {

    super(uiFactory, parentObject);
    this.progressBar = new MyProgressBar(orientation);
  }

  /**
   * {@inheritDoc}
   */
  public @Override
  JComponent getSwingComponent() {

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

    if (this.progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
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
