/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import net.sf.mmm.nls.base.AbstractResourceBundle;


/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleUtilMisc extends AbstractResourceBundle {

  /**
   * exception message if configuration value has wrong type.
   * 
   * @see net.sf.mmm.util.value.WrongValueTypeException
   */
  public static final String ERR_VALUE_WRONG_TYPE = "The value \"{0}\" with the type \"{1}\" can NOT be converted to the requested type \"{2}\"!";

  /**
   * exception message if configuration value has wrong type.
   * 
   * @see net.sf.mmm.util.value.WrongValueTypeException
   */
  public static final String ERR_VALUE_WRONG_TYPE_SOURCE = "The value \"{0}\" from \"{3}\" with the type \"{1}\" can NOT be converted to the requested type \"{2}\"!";

  /**
   * exception message if required value is NOT set.
   * 
   * @see net.sf.mmm.util.value.ValueNotSetException
   */
  public static final String ERR_VALUE_NOT_SET = "The requested value \"{0}\" is not set!";
  
}
