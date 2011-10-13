/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.reflection.api;

import java.io.Serializable;

/**
 * This is the interface for the modifiers of a
 * {@link net.sf.mmm.data.reflection.api.ContentReflectionObject}.
 * 
 * @see net.sf.mmm.data.reflection.api.ContentFieldModifiers
 * @see net.sf.mmm.data.reflection.api.ContentClassModifiers
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface ContentModifiers extends Serializable {

  /**
   * the attribute for the {@link #isSystem() system-flag}.
   */
  String XML_ATR_SYSTEM = "system";

  /**
   * the attribute for the {@link #isFinal() final-flag}.
   */
  String XML_ATR_FINAL = "final";

  /**
   * This method determines if the class or field is required by the system in
   * order to work. Then it can NOT be modified or deleted by the user.<br>
   * For fields this flag is <code>true</code> if the declaring class is a
   * system class.
   * 
   * @return <code>true</code> if the class or field is required by the system.
   */
  boolean isSystem();

  /**
   * This method determines if the class or field is final. A final class can
   * NOT be extended (can NOT have sub-classes). A final field can NOT be
   * overridden in a sub-class.<br>
   * <b>ATTENTION:</b><br>
   * Do NOT get confused with {@link java.lang.reflect.Modifier#isFinal(int)
   * final} {@link java.lang.reflect.Field fields} in the java language
   * specification. In the world of <code>mmm-content</code>, a
   * {@link #isFinal() final} {@link ContentField field} can be
   * {@link ContentField#setFieldValue(Object, Object) modified} any number of
   * times as long as it is NOT {@link ContentFieldModifiers#isReadOnly()
   * read-only}.
   * 
   * @see ContentField
   * 
   * @return <code>true</code> if the class or field is final.
   */
  boolean isFinal();

}
