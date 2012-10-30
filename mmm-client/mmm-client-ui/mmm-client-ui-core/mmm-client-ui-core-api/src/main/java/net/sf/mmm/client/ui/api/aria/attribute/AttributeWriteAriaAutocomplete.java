/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaAutocomplete;

/**
 * This interface gives read and write access to the {@link #getAutocomplete() autocomplete} attribute of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaAutocomplete extends AttributeReadAriaAutocomplete {

  /**
   * This method sets the {@link #getAutocomplete() autocomplete} attribute of this object.
   * 
   * @param autocomplete is the new value of {@link #getAutocomplete()}. May be <code>null</code> to unset.
   */
  void setAutocomplete(AriaAutocomplete autocomplete);

}
