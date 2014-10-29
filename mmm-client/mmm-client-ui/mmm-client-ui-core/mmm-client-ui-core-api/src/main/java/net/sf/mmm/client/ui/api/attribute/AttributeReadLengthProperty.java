/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.LengthProperty;

/**
 * This interface gives read access to the {@link #getLength(LengthProperty) length} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadLengthProperty {

  /**
   * This method gets the {@link Length} for the given {@link LengthProperty} of this object. <br>
   * E.g. the call of <code>{@link #getLength(LengthProperty) getLength}({@link LengthProperty#WIDTH})</code>
   * will return the same result as {@link AttributeReadSize#getWidth()}. Unlike {@link AttributeReadSize}
   * this method provides access to additional properties such as e.g. {@link LengthProperty#MIN_WIDTH} or
   * {@link LengthProperty#MAX_HEIGHT}.
   * 
   * @param property is the {@link LengthProperty} identifying the actual property to get.
   * @return the requested {@link Length}. Will be {@link Length#ZERO} if undefined (NOT set).
   */
  Length getLength(LengthProperty property);

}
