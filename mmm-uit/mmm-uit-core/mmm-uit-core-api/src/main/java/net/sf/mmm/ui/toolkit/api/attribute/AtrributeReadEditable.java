/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #isEditable() editable-flag} of an object. Editable means
 * that the end-user can edit the data of the object (e.g. the text of a text-input field). If the
 * {@link #isEditable() editable} feature is turned off, the user can no longer edit the data.<br/>
 * This is closely related to {@link AttributeReadEnabled#isEnabled()}. However turning off the
 * {@link #isEditable() editable-flag} is weaker than {@link AttributeWriteEnabled#setEnabled(boolean)
 * disabling} a UI object. An {@link AttributeReadEnabled#isEnabled() enabled} but NOT {@link #isEditable()
 * editable} object may still allow user-interaction (e.g. a combobox that still allows selection out of a
 * predefined list but no editing of the text). Further the visualization is softer (no grey out of the entire
 * object).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AtrributeReadEditable {

  /**
   * This method gets the editable status.
   * 
   * @return <code>true</code> if this object is editable.
   */
  boolean isEditable();

}
