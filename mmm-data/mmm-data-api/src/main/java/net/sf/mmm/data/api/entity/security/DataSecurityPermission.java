/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.security;

import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.security.api.PermissionType;

/**
 * This is the interface for a permission that determines if an
 * {@link DataSecurityOperation operation} is permitted or NOT. A rule has NO
 * {@link #getTitle() name}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataSecurityPermission extends DataSecurityObject {

  DataSecuityGroup getParent();

  boolean isInheritChildren();

  DataClass getAllowedClass();

  boolean isInheritSubClasses();

  /**
   * This method gets the field
   * 
   * @return
   */
  DataField getAllowedField();

  /**
   * 
   * @return
   */
  DataSecurityOperation getAction();

  /**
   * This method gets the type of permission.
   * 
   * @return the permission type.
   */
  PermissionType getType();

}
