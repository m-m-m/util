/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives write access to a {@link #setId(String) ID} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AttributeWriteOnlyHtmlId {

  /**
   * This method set the identifier of this object. Use this method to give the object a meaningful identifier
   * after creation.<br/>
   * <b>ATTENTION:</b><br>
   * See javadoc of {@link AttributeReadHtmlId#getId()} for the syntax constraints of IDs.
   * 
   * @param newId is the new identifier for the object.
   */
  void setId(String newId);

}
