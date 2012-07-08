/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.AbstractImage;

/**
 * This interface gives read access to the {@link #getImage() image} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <IMAGE> is the generic type of the {@link #getImage()}.
 */
public abstract interface AttributeReadImage<IMAGE extends AbstractImage> {

  /**
   * This method gets the <em>{@link AbstractImage image}</em> of this object. If the image is attached to an
   * object with arbitrary other features and has a smaller size, it is typically called <em>icon</em>.
   * 
   * @return the image or <code>null</code> if no image is defined.
   */
  IMAGE getImage();

}
