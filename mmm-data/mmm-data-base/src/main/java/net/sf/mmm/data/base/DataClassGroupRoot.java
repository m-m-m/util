/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base;

/**
 * This type is a simple collection of constants for the top-level
 * {@link net.sf.mmm.data.api.reflection.DataClassGroupVersion}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataClassGroupRoot {

  /**
   * The root
   * {@link net.sf.mmm.data.api.reflection.DataClassGroupVersion#getGroupId()
   * group ID}.
   */
  String GROUP_ID = "net.sf.mmm.data";

  /**
   * The current
   * {@link net.sf.mmm.data.api.reflection.DataClassGroupVersion#getGroupVersion()
   * version} of this group.
   */
  String GROUP_VERSION = "1.0.0";

}
