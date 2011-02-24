/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Label;

import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;

/**
 * This class is used for synchronous access on a SWT
 * {@link org.eclipse.swt.widgets.Label}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncLabelAccess extends AbstractSyncControlAccess {

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Label#setText(String) text} of the label.
   */
  private static final String OPERATION_SET_TEXT = "setText";

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Label#setImage(org.eclipse.swt.graphics.Image) image}
   * of the label.
   */
  private static final String OPERATION_SET_IMAGE = "setImage";

  /** the label to access */
  private Label label;

  /** the text of this label */
  private String text;

  /** the icon */
  private Image image;

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param swtStyle is the
   *        {@link org.eclipse.swt.widgets.Widget#getStyle() style} of the
   *        label.
   */
  public SyncLabelAccess(UiFactorySwt uiFactory, int swtStyle) {

    super(uiFactory, swtStyle);
    this.label = null;
    this.text = null;
    this.image = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Label getSwtObject() {

    return this.label;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_SET_TEXT) {
      this.label.setText(this.text);
    } else if (operation == OPERATION_SET_IMAGE) {
      this.label.setImage(this.image);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createSynchron() {

    this.label = new Label(getParent(), getStyle());
    if (this.text != null) {
      this.label.setText(this.text);
    }
    if (this.image != null) {
      this.label.setImage(this.image);
    }
    super.createSynchron();
  }

  /**
   * This method gets the {@link org.eclipse.swt.widgets.Label#getText() text}
   * of the label.
   * 
   * @return the text of this label or <code>null</code> if no text is set.
   */
  public String getText() {

    return this.text;
  }

  /**
   * This method sets the
   * {@link org.eclipse.swt.widgets.Label#setText(String) text} of the label.
   * 
   * @param buttonText is the text to set.
   */
  public void setText(String buttonText) {

    assert (checkReady());
    this.text = buttonText;
    invoke(OPERATION_SET_TEXT);
  }

  /**
   * This method set the
   * {@link org.eclipse.swt.widgets.Label#setImage(org.eclipse.swt.graphics.Image) image}
   * of the label.
   * 
   * @param icon is the image to set.
   */
  public void setImage(Image icon) {

    assert (checkReady());
    this.image = icon;
    invoke(OPERATION_SET_IMAGE);
  }

  /**
   * This method gets the {@link org.eclipse.swt.widgets.Label#getImage() image}
   * of the label.
   * 
   * @return the image of the label or <code>null</code> if no image is set.
   */
  public Image getImage() {

    return this.image;
  }

}
