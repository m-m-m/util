/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt;

import java.io.IOException;
import java.net.URL;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import net.sf.mmm.ui.toolkit.base.AbstractUIPicture;

/**
 * This is the implementation of the {@link net.sf.mmm.ui.toolkit.api.UIPicture}
 * interface using SWT as the underlying implementation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIPictureImpl extends AbstractUIPicture {

  /** the native SWT image object */
  private final Image image;

  /** the image data */
  private final ImageData data;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param imageUrl
   *        is the URL to the image data.
   * @throws IOException
   *         on I/O error opening or reading data from the given URL.
   */
  public UIPictureImpl(UIFactorySwt uiFactory, URL imageUrl) throws IOException {

    super(uiFactory);
    this.data = new ImageData(imageUrl.openStream());
    this.image = new Image(uiFactory.getDisplay().getSwtDisplay(), this.data);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSize#getPreferredHeight()
   * 
   */
  public int getPreferredHeight() {

    return this.data.height;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSize#getPreferredWidth()
   * 
   */
  public int getPreferredWidth() {

    return this.data.width;
  }

  /**
   * This method gets the native SWT picture.
   * 
   * @return the SWT image.
   */
  public Image getSwtImage() {

    return this.image;
  }

}
