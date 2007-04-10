/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Widget;

import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;

/**
 * This class is used for synchronous access on a SWT
 * {@link org.eclipse.swt.widgets.ProgressBar}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncProgressBarAccess extends AbstractSyncControlAccess {

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.ProgressBar#setSelection(int) selection} of
   * the progress-bar.
   */
  private static final String OPERATION_SET_SELECTION = "setSelection";

  /**
   * operation to get the
   * {@link org.eclipse.swt.widgets.ProgressBar#getSelection() selection} of the
   * progress-bar.
   */
  private static final String OPERATION_GET_SELECTION = "getSelection";

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.ProgressBar#setMaximum(int) maximum} of the
   * progress-bar.
   */
  private static final String OPERATION_SET_MAXIMUM = "setMaximum";

  /** the progress-bar to access */
  private ProgressBar progressBar;

  /** the selection value */
  private int selection;

  /** the minimum value */
  private int minimum;

  /** the maximum value */
  private int maximum;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is used to do the synchronization.
   * @param swtStyle
   *        is the {@link Widget#getStyle() style} of the progress-bar.
   */
  public SyncProgressBarAccess(UIFactorySwt uiFactory, int swtStyle) {

    super(uiFactory, swtStyle);
    this.progressBar = null;
    this.selection = 0;
    this.minimum = 0;
    this.maximum = 100;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProgressBar getSwtObject() {

    return this.progressBar;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_GET_SELECTION) {
      this.selection = this.progressBar.getSelection();
    } else if (operation == OPERATION_SET_SELECTION) {
      this.progressBar.setSelection(this.selection);
    } else if (operation == OPERATION_SET_MAXIMUM) {
      this.progressBar.setMaximum(this.maximum);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createSynchron() {

    this.progressBar = new ProgressBar(getParent(), getStyle());
    this.progressBar.setMinimum(this.minimum);
    this.progressBar.setMaximum(this.maximum);
    this.progressBar.setSelection(this.selection);
    super.createSynchron();
  }

  /**
   * This method gets the
   * {@link org.eclipse.swt.widgets.ProgressBar#getSelection() selection} of the
   * progress-bar.
   * 
   * @return the selection.
   */
  public int getSelection() {

    return this.selection;
  }

  /**
   * This method sets the
   * {@link org.eclipse.swt.widgets.ProgressBar#setSelection(int) selection} of
   * the progress-bar.
   * 
   * @param selectionValue
   *        is the selection to set.
   */
  public void setSelection(int selectionValue) {

    assert (checkReady());
    this.selection = selectionValue;
    invoke(OPERATION_SET_SELECTION);
  }

  /**
   * This method sets the
   * {@link org.eclipse.swt.widgets.ProgressBar#setMaximum(int) maximum} of the
   * progress-bar.
   * 
   * @param newMaximum
   *        is the maximum to set.
   */
  public void setMaximum(int newMaximum) {

    assert (checkReady());
    this.maximum = newMaximum;
    invoke(OPERATION_SET_MAXIMUM);
  }

  /**
   * This method gets the
   * {@link org.eclipse.swt.widgets.ProgressBar#getMaximum() maximum} of the
   * progress-bar.
   * 
   * @return the maximum.
   */
  public int getMaximum() {

    return this.maximum;
  }

}
