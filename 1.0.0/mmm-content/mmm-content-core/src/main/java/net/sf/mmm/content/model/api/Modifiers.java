/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

import java.io.Serializable;

/**
 * This is the interface for the modifiers of a
 * {@link net.sf.mmm.content.model.api.ContentReflectionObject}.
 * 
 * @see net.sf.mmm.content.model.api.FieldModifiers
 * @see net.sf.mmm.content.model.api.ClassModifiers
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract interface Modifiers extends Serializable {

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
   * order to work. Then it can NOT be modified or deleted by the user.<br>
   * For fields this flag is <code>true</code> if the declaring class is a
   * system class.
   * 
   * @return <code>true</code> if the class or field is required by the
   *         system.
   */
  boolean isSystem();

  /**
   * This method determines if the class or field is final. A final class can
   * NOT be extended (can NOT have sub-classes). A final field can NOT be
   * overridden in a sub-class.<br>
   * <b>ATTENTION:</b><br>
   * Do NOT get confused with
   * {@link java.lang.reflect.Modifier#isFinal(int) final}
   * {@link java.lang.reflect.Field fields} in the java language specification.
   * In this world, a {@link #isFinal() final} {@link ContentField field} can be
   * {@link net.sf.mmm.content.api.ContentObject#setValue(String, Object) modified}
   * any number of times as long as it is NOT
   * {@link FieldModifiers#isReadOnly() read-only}.
   * 
   * @return <code>true</code> if the class or field is final.
   */
  boolean isFinal();

}
