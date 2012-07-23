/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.util.lang.api.attribute.AttributeWriteId;

/**
 * This interface gives read and write access to the {@link #getId() ID} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteHtmlId extends AttributeReadHtmlId, AttributeWriteId<String> {

  /**
   * This method set the {@link #getId() ID} of this object. Use this method to give the object a meaningful
   * identifier after creation.<br/>
   * <b>ATTENTION:</b><br>
   * See javadoc of {@link #getId()} for the syntax constraints of IDs.
   * 
   * @param id is the new {@link #getId() ID} for the object.
   */
  void setId(String id);

}
