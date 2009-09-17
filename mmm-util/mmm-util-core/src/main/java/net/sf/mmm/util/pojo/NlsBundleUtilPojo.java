/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo;

import net.sf.mmm.util.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the POJO support.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleUtilPojo extends AbstractResourceBundle {

  /** @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException */
  public static final String ERR_PROPERTY_NOT_FOUND = "Property \"{property}\" not found in \"{type}\"!";

  /** @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException */
  public static final String ERR_PROPERTY_NOT_ACCESSABLE = "Property \"{property}\" not "
      + "accessible for \"{mode}\" in \"{type}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathUnsafeException */
  public static final String ERR_PATH_UNSAFE = "The pojo-path \"{path}\" "
      + "is unsafe for type \"{type}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathSegmentIsNullException */
  public static final String ERR_PATH_SEGMENT_IS_NULL = "The pojo-path \"{path}\" "
      + "for object \"{object}\" evaluates to null!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathCreationException */
  public static final String ERR_PATH_CREATION = "Failed to create the "
      + "object at the pojo-path \"{path}\" for object \"{object}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathAccessException */
  public static final String ERR_PATH_ACCESS = "Failed to access the pojo-path "
      + "\"{path}\" for current object of type \"{type}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.IllegalPojoPathException */
  public static final String ERR_PATH_ILLEGAL = "Illegal pojo-path \"{path}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.IllegalPojoPathException */
  public static final String ERR_FUNCTION_UNDEFINED = "Undefined function \"{function}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathFunctionUnsupportedOperationException */
  public static final String ERR_FUNCTION_UNSUPPORTED_OPERATION = "The function "
      + "\"{function}\" does NOT support the operation \"{operation}\"!";

  /** @see net.sf.mmm.util.pojo.path.base.PojoPathCachingDisabledException */
  public static final String ERR_PATH_CACHING_DISABLED = "Caching was required "
      + "for pojo-path \"{path}\" but is disabled!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathConversionException */
  public static final String ERR_PATH_CONVERSION = "Can NOT convert from \"{type}\""
      + " to \"{targetType}\" for pojo-path \"{path}\"!";

}
