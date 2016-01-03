/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This interface gives read and write access to the {@link #getName() name} of an object.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.1.0
 */
public abstract interface AttributeWriteName extends AttributeReadName {

  /**
   * @param name the new value of {@link #getName()}.
   */
  void setName(String name);

}
