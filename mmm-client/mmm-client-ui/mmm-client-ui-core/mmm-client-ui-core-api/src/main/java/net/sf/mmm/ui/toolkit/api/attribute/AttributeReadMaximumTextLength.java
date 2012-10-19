/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #getMaximumTextLength() maximum text length} attribute of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadMaximumTextLength {

  /**
   * This method gets the <em>maximum text length</em> of this object. Will be {@link Integer#MAX_VALUE} if no
   * maximum is defined.
   * 
   * @return the maximum number of characters a user is allowed to enter into this object (field).
   */
  int getMaximumTextLength();

}
