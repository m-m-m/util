/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #getLabel() label} attribute of an object.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public interface AttributeReadLabel {

  /**
   * This method gets the <em>label</em> of an object. A label is some text that is permanently shown to the
   * user in the UI in order to describe the meaning of the object and in particular its value. For elements
   * that allow to display or edit a value the label is typically shown beside the edit/display widget. For
   * action elements such as buttons or links the label is the actual text (lettering).
   * 
   * @return the label text. Will be the empty string if no label has been set.
   */
  String getLabel();

}
