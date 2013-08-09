/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #isTrimValue() trimValue} state of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadTrimValue {

  /**
   * This method determines if the {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue()
   * value} of this object should be automatically {@link String#trim() trimmed}. The default value is
   * <code>true</code>. E.g. if a text field widget supports this property and it is <code>true</code> the
   * value entered by the end-user will automatically be {@link String#trim() trimmed}. If you want prevent
   * this (e.g. if the end-user shall provide a separator, indentation, substitution variable, or the like)
   * you need to {@link AttributeWriteTrimValue#setTrimValue(boolean) set} it to <code>false</code>.
   * 
   * @return <code>true</code> if the {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue()
   *         value} of this object should be automatically {@link String#trim() trimmed}.
   */
  boolean isTrimValue();

}
