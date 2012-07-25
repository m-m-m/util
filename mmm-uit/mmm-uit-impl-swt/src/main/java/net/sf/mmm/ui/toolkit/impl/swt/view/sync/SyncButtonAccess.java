/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.sync;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;

/**
 * This class is used for synchronous access on a SWT {@link org.eclipse.swt.widgets.Button}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncButtonAccess extends AbstractSyncControlAccess<Button> {

  /**
   * operation to set the {@link org.eclipse.swt.widgets.Button#setText(String) text} of the button.
   */
  private static final String OPERATION_SET_TEXT = "setText";

  /**
   * operation to set the {@link org.eclipse.swt.widgets.Button#setSelection(boolean) selection-status} of the
   * button.
   */
  private static final String OPERATION_SET_SELECTED = "setSelected";

  /**
   * operation to get the {@link org.eclipse.swt.widgets.Button#getSelection() selection-status} of the
   * button.
   */
  private static final String OPERATION_IS_SELECTED = "getSelected";

  /**
   * operation to set the {@link org.eclipse.swt.widgets.Button#setImage(org.eclipse.swt.graphics.Image)
   * image} of the button.
   */
  private static final String OPERATION_SET_IMAGE = "setImage";

  /** the button to access */
  private Button button;

  /** the text of this button */
  private String text;

  /** the selection status */
  private boolean selected;

  /** the icon */
  private Image image;

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param node is the owning {@link #getNode() node}.
   * @param swtStyle is the {@link org.eclipse.swt.widgets.Widget#getStyle() style} of the button.
   */
  public SyncButtonAccess(UiFactorySwt uiFactory, UiElement node, int swtStyle) {

    super(uiFactory, node, swtStyle);
    this.button = null;
    this.text = null;
    this.image = null;
    this.selected = false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Button getDelegate() {

    return this.button;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_IS_SELECTED) {
      this.selected = this.button.getSelection();
    } else if (operation == OPERATION_SET_SELECTED) {
      this.button.setSelection(this.selected);
    } else if (operation == OPERATION_SET_TEXT) {
      this.button.setText(this.text);
    } else if (operation == OPERATION_SET_IMAGE) {
      this.button.setImage(this.image);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createSynchron() {

    this.button = new Button(getParent(), getStyle());
    if (this.text != null) {
      this.button.setText(this.text);
    }
    if (this.selected) {
      this.button.setSelection(this.selected);
    }
    if (this.image != null) {
      this.button.setImage(this.image);
    }
    super.createSynchron();
  }

  /**
   * This method gets the {@link org.eclipse.swt.widgets.Button#getText() text} of the button.
   * 
   * @return the text of this button or <code>null</code> if no text is set.
   */
  public String getText() {

    return this.text;
  }

  /**
   * This method sets the {@link org.eclipse.swt.widgets.Button#setText(String) text} of the button.
   * 
   * @param buttonText is the text to set.
   */
  public void setText(String buttonText) {

    assert (checkReady());
    this.text = buttonText;
    invoke(OPERATION_SET_TEXT);
  }

  /**
   * This method gets the {@link org.eclipse.swt.widgets.Button#getSelection() selection-status} of the
   * button.
   * 
   * @return the selection-status.
   */
  public boolean isSelected() {

    assert (checkReady());
    invoke(OPERATION_IS_SELECTED);
    return this.selected;
  }

  /**
   * This method sets the {@link org.eclipse.swt.widgets.Button#setSelection(boolean) selection-status} of the
   * button.
   * 
   * @param selection is the selection-status to set.
   */
  public void setSelected(boolean selection) {

    assert (checkReady());
    this.selected = selection;
    invoke(OPERATION_SET_SELECTED);
  }

  /**
   * This method set the {@link org.eclipse.swt.widgets.Button#setImage(org.eclipse.swt.graphics.Image) image}
   * of the button.
   * 
   * @param icon is the image to set.
   */
  public void setImage(Image icon) {

    assert (checkReady());
    this.image = icon;
    invoke(OPERATION_SET_IMAGE);
  }

  /**
   * This method gets the {@link org.eclipse.swt.widgets.Button#getImage() image} of the button.
   * 
   * @return the image of the button or <code>null</code> if no image is set.
   */
  public Image getImage() {

    return this.image;
  }

}
