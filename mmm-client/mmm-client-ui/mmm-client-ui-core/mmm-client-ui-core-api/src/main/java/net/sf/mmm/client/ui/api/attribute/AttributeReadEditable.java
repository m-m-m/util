/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #isEditable() editable} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadEditable {

  /**
   * This method gets the <em>editable</em> status of this object. Editable means that the end-user can edit
   * the data of the object if also in {@link net.sf.mmm.client.ui.api.common.UiMode#isEditable() edit mode}
   * (e.g. a data table that allows inline editing). If the {@link #isEditable() editable} feature is turned
   * off, the user can no longer edit the data. <br>
   * This is closely related to {@link AttributeReadEnabled#isEnabled()}. However turning off the
   * {@link #isEditable() editable-flag} is weaker than {@link AttributeWriteEnabled#setEnabled(boolean)
   * disabling} a UI object. An {@link AttributeReadEnabled#isEnabled() enabled} but NOT {@link #isEditable()
   * editable} object may still allow user-interaction (e.g. a combobox that still allows selection out of a
   * predefined list but no editing of the text). Further the visualization is softer (no grey out of the
   * entire object). <br>
   * <b>ATTENTION:</b><br>
   * There may be other aspects that have influence if the user can currently edit this object such as the
   * {@link AttributeReadMode#getMode() mode} or {@link AttributeReadEnabled#isEnabled() enabled flag}. This
   * method only returns the editable flag (as set via {@link AttributeWriteEditable#setEditable(boolean)})
   * and therefore only tells if the object is potentially editable.
   * 
   * @return <code>true</code> if this object is editable, <code>false</code> otherwise.
   */
  boolean isEditable();

}
