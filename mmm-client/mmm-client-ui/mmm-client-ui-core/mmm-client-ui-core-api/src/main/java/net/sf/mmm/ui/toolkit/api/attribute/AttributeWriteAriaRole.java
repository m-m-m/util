/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #getAriaRole() ARIA role} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaRole extends AttributeReadAriaRole {

  /**
   * This method sets the {@link #getAriaRole() ARIA role} of this object.<br/>
   * <b>NOTE:</b><br/>
   * If you are using the UI-Toolkit (UiWidget) the roles are automatically set for according widgets. There
   * is no need to e.g. manually set {@link #ARIA_ROLE_COMBOBOX} to a <code>UiWidgetComboBox</code>.
   * Therefore, you only need to set roles explicitly for specific custom features. If the underlying
   * technology does NOT properly support <em>WAI ARIA</em> (e.g. some native toolkit) the method will have no
   * effect. For appropriate accessibility please also take care of
   * {@link AttributeWriteAltText#setAltText(String) alt-texts}.<br/>
   * <b>ATTENTION:</b><br/>
   * Please use the available <code>ARIA_ROLE_*</code> constants (e.g. {@link #ARIA_ROLE_LINK}) where possible
   * and NEVER set abstract roles (<code>ARIA_ABSTRACT_ROLE_*</code>). Using {@link String} for this property
   * (instead of {@link Enum}) is for simplicity and design for extension.
   * 
   * @see #getAriaRole()
   * 
   * @param ariaRole is the new {@link #getAriaRole() ARIA role}.
   */
  void setAriaRole(String ariaRole);

}
