/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #isAllowCustomInput() allowCustomInput} attribute of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAllowCustomInput {

  /**
   * This method determines if custom input is allowed by this object (widget). This is typically used in
   * combination with {@link AttributeReadOptions#getOptions() options}, that predefine values the user can
   * select from. Some widgets like a combo box allow the user to enter any text. If this method returns
   * <code>false</code> the entered text is only valid if it matches one of the predefined
   * {@link AttributeReadOptions#getOptions() options}. The default is <code>false</code>. You may change this
   * by {@link AttributeWriteAllowCustomInput#setAllowCustomInput(boolean) setting} this flat to
   * <code>true</code>. Please note that depending on the datatype this might not make sense. E.g. if you are
   * using an {@link Enum} as {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue() value}
   * and the user enters arbitrary text this can not be converted to a non existent enum constant and
   * therefore will only return <code>null</code> without causing a validation failure.
   * 
   * @return <code>true</code> if custom input is allowed and accepted, <code>false</code> otherwise.
   */
  boolean isAllowCustomInput();

}
