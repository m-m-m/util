/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Widget;

import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;

/**
 * This is the abstract base class used for synchron access on a SWT
 * {@link org.eclipse.swt.widgets.Item}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSyncItemAccess extends AbstractSyncWidgetAccess {

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Item#setText(String) text} of the item.
   */
  private static final String OPERATION_SET_TEXT = "setText";

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Item#setImage(org.eclipse.swt.graphics.Image) image}
   * of the item.
   */
  private static final String OPERATION_SET_IMAGE = "setImage";

  /** the text of this item */
  private String text;

  /** the icon */
  private Image image;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is used to do the synchonization.
   * @param swtStyle
   *        is the {@link Widget#getStyle() style} of the item.
   */
  public AbstractSyncItemAccess(UIFactorySwt uiFactory, int swtStyle) {

    super(uiFactory, swtStyle);
    this.text = null;
    this.image = null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncObjectAccess#performSynchron(java.lang.String)
   * 
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_SET_TEXT) {
      getSwtObject().setText(this.text);
    } else if (operation == OPERATION_SET_IMAGE) {
      getSwtObject().setImage(this.image);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncObjectAccess#createSynchron()
   * 
   */
  @Override
  protected void createSynchron() {

    if (this.text != null) {
      getSwtObject().setText(this.text);
    }
    if (this.image != null) {
      getSwtObject().setImage(this.image);
    }
    super.createSynchron();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#getSwtObject()
   * 
   */
  @Override
  public abstract Item getSwtObject();

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
   * This method sets the
   * {@link org.eclipse.swt.widgets.Item#setText(String) text} of the item.
   * 
   * @param buttonText
   *        is the text to set.
   */
  public void setText(String buttonText) {

    assert (checkReady());
    this.text = buttonText;
    invoke(OPERATION_SET_TEXT);
  }

  /**
   * This method set the
   * {@link org.eclipse.swt.widgets.Item#setImage(org.eclipse.swt.graphics.Image) image}
   * of the item.
   * 
   * @param icon
   *        is the image to set.
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
