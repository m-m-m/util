/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Widget;

import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;

/**
 * This class is used for synchron access on a SWT
 * {@link org.eclipse.swt.widgets.Slider}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncSliderAccess extends AbstractSyncControlAccess {

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Slider#setSelection(int) selection} of the
   * slider.
   */
  private static final String OPERATION_SET_SELECTION = "setSelection";

  /**
   * operation to get the
   * {@link org.eclipse.swt.widgets.Slider#getSelection() selection} of the
   * slider.
   */
  private static final String OPERATION_GET_SELECTION = "getSelection";

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Slider#setMaximum(int) maximum} of the
   * slider.
   */
  private static final String OPERATION_SET_MAXIMUM = "setMaximum";

  /** the slider to access */
  private Slider slider;

  /** the selection value */
  private int selection;

  /** the minimum value */
  private int minimum;

  /** the maximum value */
  private int maximum;

  /** the increment */
  private int increment;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is used to do the synchonization.
   * @param swtStyle
   *        is the {@link Widget#getStyle() style} of the slider.
   */
  public SyncSliderAccess(UIFactorySwt uiFactory, int swtStyle) {

    super(uiFactory, swtStyle);
    this.slider = null;
    this.selection = 0;
    this.minimum = 0;
    this.maximum = 100;
    this.increment = 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Slider getSwtObject() {

    return this.slider;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_GET_SELECTION) {
      this.selection = this.slider.getSelection();
    } else if (operation == OPERATION_SET_SELECTION) {
      this.slider.setSelection(this.selection);
    } else if (operation == OPERATION_SET_MAXIMUM) {
      this.slider.setMaximum(this.maximum);
      this.slider.setThumb(1);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createSynchron() {

    this.slider = new Slider(getParent(), getStyle());
    this.slider.setMinimum(this.minimum);
    this.slider.setMaximum(this.maximum);
    System.out.println("Max:" + this.maximum);
    this.slider.setSelection(this.selection);
    this.slider.setIncrement(this.increment);
    this.slider.setThumb(1);
    this.slider.setPageIncrement(1);
    super.createSynchron();
  }

  /**
   * This method gets the
   * {@link org.eclipse.swt.widgets.Slider#getSelection() selection} of the
   * slider.
   * 
   * @return the selection.
   */
  public int getSelection() {

    assert (checkReady());
    invoke(OPERATION_GET_SELECTION);
    return this.selection;
  }

  /**
   * This method sets the
   * {@link org.eclipse.swt.widgets.Slider#setSelection(int) selection} of the
   * slider.
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
   * {@link org.eclipse.swt.widgets.Slider#setMaximum(int) maximum} of the
   * slider.
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
   * {@link org.eclipse.swt.widgets.Slider#getMaximum() maximum} of the
   * slider.
   * 
   * @return the maximum.
   */
  public int getMaximum() {

    return this.maximum;
  }

}
