/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.LengthProperty;

/**
 * This interface gives read and write access to the
 * {@link #getLength(net.sf.mmm.client.ui.api.common.LengthProperty) length} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteLengthProperty extends AttributeReadLengthProperty {

  /**
   * This method sets the specified {@link #getLength(LengthProperty) length} of this object. <br>
   * E.g. the call of
   * <code>{@link #setLength(LengthProperty, Length) setLength}({@link LengthProperty#WIDTH}, value)</code>
   * will return the same result as <code>{@link AttributeWriteSize#setWidth(Length) setWidth}(value)</code>.
   * 
   * @param property is the {@link LengthProperty} identifying the actual property to set.
   * @param length is the new {@link #getLength(LengthProperty) length} to set.
   */
  void setLength(LengthProperty property, Length length);

}
