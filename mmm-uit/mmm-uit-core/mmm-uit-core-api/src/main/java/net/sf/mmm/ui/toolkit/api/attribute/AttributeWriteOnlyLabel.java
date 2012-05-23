/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives write access to the {@link #setLabel(String) label} of an object.
 * 
 * @see AttributeReadLabel
 * 
 * @author hohwille
 * @since 1.0.0
 */
public interface AttributeWriteOnlyLabel {

  /**
   * This method sets the {@link AttributeReadLabel#getLabel() label} of this object.
   * 
   * @param label is the new {@link AttributeReadLabel#getLabel() label} to set.
   */
  void setLabel(String label);

}
