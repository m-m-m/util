/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.AbstractImage;

/**
 * This interface gives read and write access to the {@link #getImage() image} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <IMAGE> is the generic type of the {@link #getImage()}.
 */
public abstract interface AttributeWriteImage<IMAGE extends AbstractImage> extends AttributeReadImage<IMAGE> {

  /**
   * This method sets the {@link #getImage() image} of this object. A previously set image will be replaced.
   * 
   * @param image is the new {@link #getImage() image} or <code>null</code> to remove the image.
   */
  void setImage(IMAGE image);

}
