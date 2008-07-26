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
public class NlsBundlePojo extends AbstractResourceBundle {

  /** @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException */
  public static final String ERR_PROPERTY_NOT_FOUND = "Property \"{0}\" not found in \"{1}\"!";

  /** @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException */
  public static final String ERR_PROPERTY_NOT_ACCESSABLE = "Property \"{0}\" not "
      + "accessible for \"{2}\" in \"{1}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathSegmentIsNullException */
  public static final String ERR_PATH_UNSAFE = "The pojo-path \"{0}\" "
      + "is unsafe for type \"{1}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathSegmentIsNullException */
  public static final String ERR_PATH_SEGMENT_IS_NULL = "The pojo-path \"{0}\" "
      + "for object \"{1}\" evaluates to null!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathCreationException */
  public static final String ERR_PATH_CREATION = "Failed to create the "
      + "object at the pojo-path \"{1}\" for object \"{0}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathAccessException */
  public static final String ERR_PATH_ACCESS = "Failed to access the pojo-path "
      + "\"{0}\" for current object of type \"{1}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.IllegalPojoPathException */
  public static final String ERR_PATH_ILLEGAL = "Illegal pojo-path \"{0}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.IllegalPojoPathException */
  public static final String ERR_FUNCTION_UNDEFINED = "Undefined function \"{0}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathFunctionUnsupportedOperationException */
  public static final String ERR_FUNCTION_UNSUPPORTED_OPERATION = "The function "
      + "\"{1}\" does NOT support the operation \"{0}\"!";

  /** @see net.sf.mmm.util.pojo.path.base.PojoPathCachingDisabledException */
  public static final String ERR_PATH_CACHING_DISABLED = "Caching was required "
      + "for pojo-path \"{0}\" but is disabled!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathConversionException */
  public static final String ERR_PATH_CONVERSION = "Can NOT convert from \"{1}\""
      + " to \"{2}\" for pojo-path \"{0}\"!";

}
