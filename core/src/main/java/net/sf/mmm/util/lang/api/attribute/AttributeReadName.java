/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This is the abstract interface for an object that has an {@link #getName() name}. It is quite similar or even
 * equivalent to a {@link AttributeReadTitle#getTitle() title}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.1.0
 */
public abstract interface AttributeReadName {

  /**
   * @return the name of this object or {@code null} if not set. The name is used to represent the object and might be
   *         displayed to end-users.
   */
  String getName();

}
