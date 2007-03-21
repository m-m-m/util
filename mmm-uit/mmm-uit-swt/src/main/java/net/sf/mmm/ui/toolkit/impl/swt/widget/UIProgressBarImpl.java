/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.widget.UIProgressBar;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncProgressBarAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIProgressBar} interface using SWT
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIProgressBarImpl extends AbstractUIWidget implements UIProgressBar {

  /** the native SWT widget */
  private final SyncProgressBarAccess syncAccess;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param orientation
   *        is the orientation of the progress-bar.
   * @param indeterminate
   *        if <code>true</code> the progress-bar will be
   *        {@link #isIndeterminate() indeterminate}.
   */
  public UIProgressBarImpl(UIFactorySwt uiFactory, UISwtNode parentObject, Orientation orientation,
      boolean indeterminate) {

    super(uiFactory, parentObject);
    int style;
    if (orientation == Orientation.HORIZONTAL) {
      style = SWT.HORIZONTAL;
    } else {
      style = SWT.VERTICAL;
    }
    if (indeterminate) {
      style |= SWT.INDETERMINATE;
    }
    this.syncAccess = new SyncProgressBarAccess(uiFactory, style);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncProgressBarAccess getSyncAccess() {

    return this.syncAccess;
  }

  /**
   * {@inheritDoc}
   */
  public int getProgress() {

    return this.syncAccess.getSelection();
  }

  /**
   * {@inheritDoc}
   */
  public void setProgress(int newProgress) {

    this.syncAccess.setSelection(newProgress);
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
  public boolean isIndeterminate() {

    return this.syncAccess.hasStyle(SWT.INDETERMINATE);
  }

  /**
   * {@inheritDoc}
   */
  public Orientation getOrientation() {

    if (this.syncAccess.hasStyle(SWT.HORIZONTAL)) {
      return Orientation.HORIZONTAL;
    } else {
      return Orientation.VERTICAL;
    }
  }

}
