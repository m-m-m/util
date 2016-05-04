/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This interface gives read and write access to the {@link #getId() ID} of an object.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 * @param <ID> is the generic type of the {@link #getId() ID}.
 */
public interface AttributeWriteId<ID> extends AttributeReadId<ID> {

  /**
   * This method sets the {@link #getId() ID} of this object. <br>
   * <b>ATTENTION:</b><br>
   * An ID should typically NOT be changed after it has been assigned once.
   *
   * @param id is the new {@link #getId() ID}
   */
  void setId(ID id);

}
