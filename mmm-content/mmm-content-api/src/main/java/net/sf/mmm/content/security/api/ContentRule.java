/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.security.api;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.reflection.api.ContentClass;
import net.sf.mmm.content.reflection.api.ContentField;

/**
 * This is the interface for a rule that determines if an {@link ContentAction
 * operation} is permitted or NOT. A rule has NO {@link #getTitle() name}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentRule extends ContentSecurityObject {

  ContentGroup getParent();

  ContentObject getFolder();

  boolean isInheritChildren();

  ContentClass getAllowedClass();

  boolean isInheritSubClasses();

  /**
   * This method gets the field
   * 
   * @return
   */
  ContentField getAllowedField();

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
