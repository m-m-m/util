/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This interface gives read access to the {@link #getId() ID} of an object.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 * @param <ID> is the generic type of the {@link #getId() ID}. The most common types are {@link Long} and {@link String}
 *        .
 */
public abstract interface AttributeReadId<ID> {

  /**
   * This method gets the ID used to identify this object. An ID should be unique.
   *
   * @return the object's ID.
   */
  ID getId();

}
