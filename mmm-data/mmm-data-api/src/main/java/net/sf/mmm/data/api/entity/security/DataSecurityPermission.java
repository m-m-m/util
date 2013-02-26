/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.security;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataField;

/**
 * This is the interface for a permission that determines if an {@link DataSecurityOperation operation} is
 * permitted or NOT. A permissions has NO {@link #getTitle() name}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataSecurityPermission extends DataSecurityObject {

  /**
   * TODO: javadoc.
   * 
   * @return TODO
   */
  DataSecuityGroup getParent();

  /**
   * TODO: javadoc.
   * 
   * @return TODO
   */
  boolean isInheritChildren();

  /**
   * TODO: javadoc.
   * 
   * @return TODO
   */
  DataClass<? extends DataObject> getAllowedClass();

  /**
   * TODO: javadoc.
   * 
   * @return TODO
   */
  boolean isInheritSubClasses();

  /**
   * TODO: javadoc.
   * 
   * @return TODO
   */
  DataField<? extends DataObject, ?> getAllowedField();

  /**
   * TODO: javadoc.
   * 
   * @return TODO
   */
  DataSecurityOperation getAction();

}
