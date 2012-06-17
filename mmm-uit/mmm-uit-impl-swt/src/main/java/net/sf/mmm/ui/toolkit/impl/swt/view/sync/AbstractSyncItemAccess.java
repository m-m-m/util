/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.sync;

import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Item;

/**
 * This is the abstract base class used for synchronous access on a SWT
 * {@link org.eclipse.swt.widgets.Item}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * @since 1.0.0
 */
public abstract class AbstractSyncItemAccess<DELEGATE extends Item> extends
    AbstractSyncWidgetAccess<DELEGATE> {

  /**
   * operation to set the {@link org.eclipse.swt.widgets.Item#setText(String)
   * text} of the item.
   */
  private static final String OPERATION_SET_TEXT = "setText";

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Item#setImage(org.eclipse.swt.graphics.Image)
   * image} of the item.
   */
  private static final String OPERATION_SET_IMAGE = "setImage";

  /** the text of this item */
  private String text;

  /** the icon */
  private Image image;

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param node is the owning {@link #getNode() node}.
   * @param swtStyle is the {@link org.eclipse.swt.widgets.Widget#getStyle()
   *        style} of the item.
   */
  public AbstractSyncItemAccess(UiFactorySwt uiFactory, UiNode node, int swtStyle) {

    super(uiFactory, node, swtStyle);
    this.text = null;
    this.image = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_SET_TEXT) {
      getDelegate().setText(this.text);
    } else if (operation == OPERATION_SET_IMAGE) {
      getDelegate().setImage(this.image);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createSynchron() {

    if (this.text != null) {
      getDelegate().setText(this.text);
    }
    if (this.image != null) {
      getDelegate().setImage(this.image);
    }
    super.createSynchron();
  }

  /**
   * This method gets the {@link org.eclipse.swt.widgets.Item#getText() text} of
   * the item.
   * 
   * @return the text of this item or <code>null</code> if no text is set.
   */
  public String getText() {

    return this.text;
  }

  /**
   * This method sets the {@link org.eclipse.swt.widgets.Item#setText(String)
   * text} of the item.
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
   * {@link org.eclipse.swt.widgets.Item#setImage(org.eclipse.swt.graphics.Image)
   * image} of the item.
   * 
   * @param icon is the image to set.
   */
  public void setImage(Image icon) {

    assert (checkReady());
    this.image = icon;
    invoke(OPERATION_SET_IMAGE);
  }

  /**
   * This method gets the {@link org.eclipse.swt.widgets.Item#getImage() image}
   * of the item.
   * 
   * @return the image of the item or <code>null</code> if no image is set.
   */
  public Image getImage() {

    return this.image;
  }

}
