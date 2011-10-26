/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.security.api;

import net.sf.mmm.data.api.entity.security.DataSecurityPermission;

/**
 * This is the enum with the available types of a {@link DataSecurityPermission}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum PermissionType {

  /**
   * This is the type of a permission that denies access for a specific
   * operation.
   */
  DENY,

  /**
   * This is the type of a permission that grants access for a specific
   * operation.
   */
  GRANT,

}
