/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This interface gives read access to a {@link #getId() ID}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadHtmlId extends AttributeReadId<String> {

  /** Use this character to compose hierarchical {@link #getId() IDs}. */
  String ID_SEPARATOR = "-";

  /**
   * This method gets the unique identifier of this object.<br/>
   * <b>ATTENTION:</b><br>
   * In order to be compliant with all possible UI toolkit implementations, a valid ID has to be in the form
   * <code>[a-zA-Z][a-zA-Z0-9-_]*</code>. Colons and periods are not accepted to prevent you from later having
   * chaos with CSS selectors.
   * 
   * @see #ID_SEPARATOR
   * 
   * @return the ID of this object or <code>null</code> if NOT {@link AttributeWriteHtmlId#setId(String) set}.
   */
  @Override
  String getId();

}
