/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #getText() text} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadText {

  /**
   * This method gets the <em>text</em> of this object. The text attribute is more generic (or less specific)
   * as {@link AttributeReadLabel#getLabel() label} or {@link AttributeReadStringTitle#getTitle() title}.
   * 
   * @return the text. Will be the empty string if no text has been set.
   */
  String getText();

}
