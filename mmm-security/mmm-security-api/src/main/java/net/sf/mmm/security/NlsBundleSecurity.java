/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.security;

import net.sf.mmm.util.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for this module.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsBundleSecurity extends AbstractResourceBundle {

  /** @see net.sf.mmm.security.api.PermissionDeniedException */
  public static final String ERR_PERMISSION_DENIED_ON_OBJECT = "Permission denied for \"{user}\" performing \"{operation}\" on \"{object}\"!";

  /** @see net.sf.mmm.security.api.PermissionDeniedException */
  public static final String ERR_PERMISSION_DENIED = "Permission denied for \"{user}\" performing \"{operation}\"!";

}
