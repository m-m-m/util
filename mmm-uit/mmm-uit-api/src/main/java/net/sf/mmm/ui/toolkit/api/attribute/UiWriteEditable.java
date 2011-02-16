/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This is the interface for an {@link net.sf.mmm.ui.toolkit.api.UiObject
 * object} of the UI toolkit that allows editing but this behavior can be
 * enabled or disabled. Editable means that the end-user can edit the data of
 * the object (e.g. the text of a text-input field).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWriteEditable extends UiReadEditable {

  /**
   * This method set the editable status.
   * 
   * @param editableFlag if <code>true</code> the object will become editable,
   *        if <code>false</code> the object will become uneditable.
   */
  void setEditable(boolean editableFlag);

}
