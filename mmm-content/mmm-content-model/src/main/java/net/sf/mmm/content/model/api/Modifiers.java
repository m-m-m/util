/* $ Id: $ */
package net.sf.mmm.content.model.api;

import net.sf.mmm.util.xml.api.XmlSerializable;

/**
 * This is the interface for the modifiers of a
 * {@link net.sf.mmm.content.model.api.ContentReflectionObject}.
 * 
 * @see net.sf.mmm.content.model.api.FieldModifiers
 * @see net.sf.mmm.content.model.api.ClassModifiers
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface Modifiers extends XmlSerializable {

  /**
   * the attribute for the {@link #isSystem() system-flag}.
   */
  String XML_ATR_ROOT_SYSTEM = "system";

  /**
   * the attribute for the {@link #isFinal() final-flag}.
   */
  String XML_ATR_ROOT_FINAL = "final";

  /**
   * This method determines if the class or field is required by the system in
   * order to work. Then it can NOT be modified or deleted by the user.
   * 
   * @return <code>true</code> if the class or field is required by the
   *         system.
   */
  boolean isSystem();

  /**
   * This method determines if the class or field is final. A final class can
   * NOT be extended (can NOT have sub-classes). A final field can NOT be
   * overriden in a sub-class.
   * 
   * @return <code>true</code> if the class or field is final.
   */
  boolean isFinal();

}
