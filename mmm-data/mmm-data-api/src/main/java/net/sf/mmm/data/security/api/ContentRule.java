/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.security.api;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.entity.security.ContentGroup;
import net.sf.mmm.data.api.entity.security.DataSecurityObject;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataField;

/**
 * This is the interface for a rule that determines if an {@link ContentAction
 * operation} is permitted or NOT. A rule has NO {@link #getTitle() name}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentRule extends DataSecurityObject {

  ContentGroup getParent();

  DataObject getFolder();

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
  ContentAction getAction();

  /**
   * This method gets the type of permission.
   * 
   * @return the permission type.
   */
  PermissionType getType();

}
